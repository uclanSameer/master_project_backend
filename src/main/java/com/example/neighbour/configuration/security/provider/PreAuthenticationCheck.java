package com.example.neighbour.configuration.security.provider;

import com.example.neighbour.data.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.SpringSecurityMessageSource;

@Slf4j
public class PreAuthenticationCheck {

    protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public void check(User user) {

        log.info("Starting pre-authentication check for user {}", user.getEmail());

        /* TODO: Check if user email is verified */
        checkUserAccountIsLocked(user);
        checkUseAccountIsEnabled(user);
        checkUserAccountIsExpired(user);
        checkUserCredentialIsExpired(user);

    }

    private void checkUserCredentialIsExpired(User user) {
        if (!user.isCredentialsNonExpired()) {
            log.error("Failed to authenticate since user account credentials have expired");
            throw new CredentialsExpiredException(this.messages
                    .getMessage("AccountStatusUserDetailsChecker.credentialsExpired", "User credentials have expired"));
        }
    }

    private void checkUserAccountIsExpired(User user) {
        if (!user.isAccountNonExpired()) {
            log.error("Failed to authenticate since user account is expired");
            throw new AccountExpiredException(
                    this.messages.getMessage("AccountStatusUserDetailsChecker.expired", "User account has expired"));
        }
    }

    private void checkUseAccountIsEnabled(User user) {
        if (!user.isEnabled()) {
            log.error("Failed to authenticate since user account is disabled");
            throw new DisabledException(
                    this.messages.getMessage("AccountStatusUserDetailsChecker.disabled", "User is disabled"));
        }
    }

    private void checkUserAccountIsLocked(User user) {
        if (!user.isAccountNonLocked()) {
            log.error("Failed to authenticate since user account is locked");
            throw new LockedException(
                    this.messages.getMessage("AccountStatusUserDetailsChecker.locked", "User account is locked"));
        }
    }
}
