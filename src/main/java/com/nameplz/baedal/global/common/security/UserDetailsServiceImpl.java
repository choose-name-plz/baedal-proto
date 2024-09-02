package com.nameplz.baedal.global.common.security;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.repository.UserRepository;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j(topic = "UserDetailsServiceImpl 실행")
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * redis 에서 캐싱하여 User 정보를 가져오거나 존재하지 않는다면 RDBMS에서 가져와 redis에 저장
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // redis 에서 해당 유저 조회
        UserDetailsImpl userDetails = (UserDetailsImpl) redisTemplate.opsForValue().get(username);
        log.info("인메모리 에서 조회한 user " + userDetails.toString());

        if (userDetails == null) {
            // redis 에 없다면 DB에서 조회
            User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

            log.info("RDBMS 에서 조회한 user " + user.toString());

            // UserDetailsImpl 객체 생성
            userDetails = new UserDetailsImpl(user);

            // Redis에 캐싱, 만료 시간 1시간 설정
            redisTemplate.opsForValue().set(username, userDetails, 1, TimeUnit.HOURS);
        }

        return userDetails;
    }

    /**
     * 권한 업데이트시 캐시 삭제 (UserDetailService 에서 처리하여 캐시 관리 책임을 집중화시킴)
     */
    public void removeUserFromCache(String username) {
        redisTemplate.delete(username);
    }
}
