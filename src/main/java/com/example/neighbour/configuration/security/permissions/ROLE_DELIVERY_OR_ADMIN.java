package com.example.neighbour.configuration.security.permissions;

import com.example.neighbour.utils.Authority;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize(Authority.DELIVERY_OR_ADMIN)
public @interface ROLE_DELIVERY_OR_ADMIN {
}
