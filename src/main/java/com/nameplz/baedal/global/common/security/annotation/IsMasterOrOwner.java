package com.nameplz.baedal.global.common.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.nameplz.baedal.domain.user.domain.UserRole.Authority.MASTER;
import static com.nameplz.baedal.domain.user.domain.UserRole.Authority.OWNER;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("isAuthenticated() and ( hasRole('" + OWNER + "') or hasRole('" + MASTER + "') )")
public @interface IsMasterOrOwner {
}
