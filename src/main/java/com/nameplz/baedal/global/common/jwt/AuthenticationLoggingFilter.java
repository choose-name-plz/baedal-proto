package com.nameplz.baedal.global.common.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "AuhtenticationLogginFilter - 권한 로깅")
public class AuthenticationLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        log.debug("권한체크 시작");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // TODO : 권한 체크는 warn 보다는 아예 안 적거나 debug가 맞는 것 같아요!!
            log.warn("[loggingFilter] Authentication object: {}", authentication);
            log.warn("[loggingFilter] Username: {}", authentication.getName());
            log.warn("[loggingFilter] Authorities: {}", authentication.getAuthorities());
        }
        filterChain.doFilter(request, response);
    }
}