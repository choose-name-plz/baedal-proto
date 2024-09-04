package com.nameplz.baedal.global.common.redis;

import com.nameplz.baedal.domain.user.domain.UserRole;
import com.nameplz.baedal.global.common.redis.dto.UserAuthDto;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class RedisUtilsTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void 레디스_저장_읽기_테스트() {

        // given
        String username = "yahoo";
        UserRole role = UserRole.MASTER;
        UserAuthDto userAuthDto = UserAuthDto.of(username, role, LocalDateTime.now());

        // when
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(username, userAuthDto, 1000 * 60, TimeUnit.MILLISECONDS);
        UserAuthDto output = (UserAuthDto) ops.get(username);

        // then
        Assertions.assertThat(output.username()).isEqualTo(username);
        Assertions.assertThat(output.role()).isEqualTo(UserRole.MASTER);

    }

    @Test
    void 레디스_삭제_테스트() {

        String username = "yahoo";
        UserRole role = UserRole.MASTER;
        UserAuthDto userAuthDto = UserAuthDto.of(username, role, LocalDateTime.now());
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set(username, userAuthDto, 1000 * 60 * 60, TimeUnit.MILLISECONDS);

        // when
        redisTemplate.delete(username);

        // then
        UserAuthDto output = (UserAuthDto) ops.get(username);
        Assertions.assertThat(output).isNull();
    }

}