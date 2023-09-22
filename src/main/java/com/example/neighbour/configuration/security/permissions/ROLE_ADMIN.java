package com.example.neighbour.configuration.security.permissions;

import com.example.neighbour.utils.Authority;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize(Authority.ADMIN)
public @interface ROLE_ADMIN {
}
