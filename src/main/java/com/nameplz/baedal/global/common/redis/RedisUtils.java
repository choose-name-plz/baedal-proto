package com.nameplz.baedal.global.common.redis;

import com.nameplz.baedal.domain.user.domain.UserRole;
import com.nameplz.baedal.global.common.redis.dto.UserAuthDto;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisUtils {


    private static final Long expiredTime = 1000 * 60 * 60L;
    private final RedisTemplate<String, UserAuthDto> redisTemplate;

    /**
     * Redis에 유저 정보 저장
     */
    public void setUserData(String username, UserRole role) {
        UserAuthDto userAuthDto = UserAuthDto.of(username, role, LocalDateTime.now());

        ValueOperations<String, UserAuthDto> ops = redisTemplate.opsForValue();
        ops.set(username, userAuthDto, expiredTime, TimeUnit.MILLISECONDS);
    }

    /***
     *
     */
    public UserAuthDto findUserData(String username) {
        ValueOperations<String, UserAuthDto> ops = redisTemplate.opsForValue();
        return ops.get(username);
    }

    public void deleteUserData(String username) {
        redisTemplate.delete(username);
    }

}
