package com.nameplz.baedal.global.common.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.nameplz.baedal.domain.user.domain.UserRole.Authority.MASTER;

/**
 * 권한이 Master 이거나, 유저 본인일 경우 허용
 * 매개변수 속 username 이나 user.username 이 principal.username 과 같은지 확인
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("isAuthenticated() and ( " +
              "hasRole('" + MASTER + "') " +
              "or #username == principal.username " +
              "or #user.username == principal.username " +
              ")")
public @interface IsMasterOrSelf {
}
