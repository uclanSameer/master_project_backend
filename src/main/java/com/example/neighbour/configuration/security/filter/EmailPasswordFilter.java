package com.example.neighbour.configuration.security.filter;

import com.example.neighbour.configuration.security.authentication.EmailPasswordAuthentication;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.utils.ApiConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static com.example.neighbour.utils.ErrorUtils.convertObjectToJson;

@Slf4j
@AllArgsConstructor
public class EmailPasswordFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;


    /**
     * It checks if the request should be filtered or not.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        boolean shouldFilter = request.
                getServletPath()
                .startsWith(ApiConstants.API_VERSION_1 + "user/authenticate");
        return !shouldFilter;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException {

        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                String email = request.getHeader("email");
                String password = request.getHeader("password");

                Authentication authentication = new EmailPasswordAuthentication(email, password);

                authentication = authenticationManager.authenticate(authentication);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (ResponseStatusException e) {
            log.error("Error occurred while authenticating the user : {}", e.getMessage(), e);
            ResponseDto<String> errorResponse = ResponseDto.failure(e.getMessage());
            response.setStatus(e.getStatusCode().value());
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(convertObjectToJson(errorResponse));
        } catch (Exception e) {
            log.error("Error occurred while authenticating the user : {}", e.getMessage(), e);
            ResponseDto<String> errorResponse = ResponseDto.failure(e.getMessage());
            response.setStatus(400);
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(convertObjectToJson(errorResponse));
        }
    }

}
