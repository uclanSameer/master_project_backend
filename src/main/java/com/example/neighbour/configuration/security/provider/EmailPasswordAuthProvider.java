package com.example.neighbour.configuration.security.provider;

import com.example.neighbour.configuration.security.authentication.EmailPasswordAuthentication;
import com.example.neighbour.data.User;
import com.example.neighbour.exception.ErrorResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
@Slf4j
public class EmailPasswordAuthProvider implements AuthenticationProvider {

    private final UserDetailsService userService;
    private final PasswordEncoder passwordEncoder;
    private final PreAuthenticationCheck preAuthenticationCheck;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = (String) authentication.getCredentials();
        log.info("Authenticating the user with email {}", email);

        User user = (User) userService.loadUserByUsername(email);

        if (passwordEncoder.matches(password, user.getPassword())) {

            preAuthenticationCheck.check(user);
            return new EmailPasswordAuthentication(user, password, user.getAuthorities());
        }

        log.error("Could not authenticate the user {} ", email);
        throw new ErrorResponseException(401, "Either email or password is incorrect.");
    }

    // This method is called by the AuthenticationManager to check
    // if this AuthenticationProvider supports the passed Authentication object.
    @Override
    public boolean supports(Class<?> authenticationClass) {
        return EmailPasswordAuthentication.class.equals(authenticationClass);
    }


}
