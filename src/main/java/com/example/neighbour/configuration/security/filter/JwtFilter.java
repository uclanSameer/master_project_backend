package com.example.neighbour.configuration.security.filter;

import com.example.neighbour.configuration.security.authentication.JwtAuthentication;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.utils.ApiConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;

import static com.example.neighbour.utils.ErrorUtils.convertObjectToJson;

@Slf4j
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final ProviderManager authenticationManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {
            if (SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                String jwt = parseJwt(request);

                JwtAuthentication jwtAuthentication = new JwtAuthentication(null, null, jwt);
                jwtAuthentication = (JwtAuthentication) authenticationManager
                        .authenticate(jwtAuthentication);

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(jwtAuthentication);
            }

            filterChain.doFilter(request, response);
        } catch (ResponseStatusException e) {
            ResponseDto<String> errorResponse = ResponseDto.failure(e.getMessage());
            response.setStatus(e.getStatusCode().value());
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(convertObjectToJson(errorResponse));
        } catch (Exception e) {
            ResponseDto<String> errorResponse = ResponseDto.failure(e.getMessage());
            response.setStatus(500);
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(convertObjectToJson(errorResponse));
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        boolean shouldNotFilter =
                Arrays
                        .stream(ApiConstants.AUTH_WHITELIST)
                        .anyMatch(url -> request.getServletPath().startsWith(url));

        return request.getServletPath().startsWith(ApiConstants.API_VERSION_1 + "authenticate")
                || request.getServletPath().startsWith(ApiConstants.API_VERSION_1 + "user")
                || request.getServletPath().startsWith(ApiConstants.API_VERSION_1 + "webhook")
                || request.getServletPath().startsWith(ApiConstants.API_VERSION_1 + "address")
                || request.getServletPath().startsWith(ApiConstants.API_VERSION_1 + "user/verify/")
                || request.getServletPath().equals(ApiConstants.API_VERSION_1 + "user/verification-token")
                || request.getServletPath().startsWith("/swagger-ui")
                || request.getServletPath().startsWith("/v2/api-docs")
                || shouldNotFilter
                ;

    }

    private String parseJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        checkHeaderForBearerToken(authHeader);

        return authHeader.substring(7);
    }

    private void checkHeaderForBearerToken(String header) {
        boolean hasToken = StringUtils
                .hasText(header) &&
                header.startsWith("Bearer ");

        if (!hasToken) {
            log.error("While checking for Bearer in authorization header , found {}", header);
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.UNAUTHORIZED,
                    "Authorization header must be provided and must start with Bearer"
            );
        }
    }
}
