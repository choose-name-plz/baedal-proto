package com.nameplz.baedal.global.common.jwt;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.domain.UserRole;
import com.nameplz.baedal.global.common.redis.RedisUtils;
import com.nameplz.baedal.global.common.redis.dto.UserAuthDto;
import com.nameplz.baedal.global.common.security.UserDetailsImpl;
import com.nameplz.baedal.global.common.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JwtAuthorizationFilter - JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RedisUtils redisUtils;

    // 로그인을 제외한 요청마다 필터실행. jwt에 담긴 정보를 추출해 authentication 객체에 저장
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
        FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getTokenFromRequest(req);

        if (req.getRequestURI().equals("/users/logout")) {
            filterChain.doFilter(req, res);
        }

        if (StringUtils.hasText(tokenValue)) {
            // JWT 토큰 substring (Bearer 빼고 순수한토큰)
            tokenValue = jwtUtil.substringToken(tokenValue);
            log.info(tokenValue);

            if (!jwtUtil.validateToken(tokenValue)) {
                log.error("Token Error");
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            try {
                setAuthentication(info.getSubject(),
                    UserRole.valueOf((String) info.get(JwtUtil.AUTHORIZATION_KEY)));
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(req, res);

    }

    // 인증 처리
    public void setAuthentication(String username, UserRole role) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username, role);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username, UserRole role) {
        //Redis에 User 정보가 있는지 확인한다.
        UserAuthDto userAuthDto = redisUtils.findUserData(username);
        if (userAuthDto != null) {
            // 여기서 UserDetailsPrincipal 생성할 때 객체를 User 말고 다른 걸 사용하는 게 좋아보입니다.
            UserDetailsImpl userDetails = new UserDetailsImpl(
                User.createForPrincipal(username, role));
            return new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        }

        // User 정보가 없으면 DB에서 갖고 온다.
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 인증에 성공하였으면 redis에 저장
        redisUtils.setUserData(username, ((UserDetailsImpl) userDetails).getUser().getRole());
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }
}