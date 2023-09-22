package com.example.neighbour.configuration.security.provider;

import com.example.neighbour.configuration.security.authentication.JwtAuthentication;
import com.example.neighbour.configuration.security.jwt.JwtUtils;
import com.example.neighbour.data.User;

import com.example.neighbour.exception.ErrorResponseException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@AllArgsConstructor
@Slf4j
public class JwtAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userService;
    private final JwtUtils jwtUtils;
    private final PreAuthenticationCheck preAuthenticationCheck;

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return JwtAuthentication.class.equals(authenticationClass);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        String jwt = jwtAuthentication.getJwt();
        boolean isValidated = jwtUtils.validateJwtToken(jwt);
        String email = jwtUtils.getSubjectFromJwtToken(jwt);

        if (isValidated) {
            try {
                log.debug(" jwt is validated for user with email {}.", email);
                User user = (User) loadUserByUsername(email);
                preAuthenticationCheck.check(user);

                return new JwtAuthentication(user, null, user.getAuthorities(), jwt);
            } catch (MalformedJwtException e) {
                log.error("The jwt token is malformed .");
                throw new ResponseStatusException(BAD_REQUEST, "The jwt token is malformed .");
            }
        }

        log.error(" Bad credential for {}", email);
        throw new ErrorResponseException(401, "Either email or password is not right");
    }

    private UserDetails loadUserByUsername(String email) {
        try {
            return userService
                    .loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            log.error("The user with email {} doesn't exist.", email);
            throw new ErrorResponseException(401, "Either email or password is incorrect.");
        }

    }

}
