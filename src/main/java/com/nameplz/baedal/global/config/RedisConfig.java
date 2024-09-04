package com.nameplz.baedal.global.config;

import com.nameplz.baedal.global.common.redis.RedisProperty;
import com.nameplz.baedal.global.common.redis.dto.UserAuthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RedisConfig {


    private final RedisProperty redisProperty;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName(redisProperty.host());
        config.setPort(redisProperty.port());
        config.setPassword(redisProperty.password());

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());

        // Serializer 설정
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());

        // Hash Serializer 설정
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        return redisTemplate;
    }

    /**
     * UserAuth용 Redis Template
     */
    @Bean
    public RedisTemplate<String, UserAuthDto> userAuthDtoRedisTemplate() {
        RedisTemplate<String, UserAuthDto> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());

        // Serializer 설정
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());

        // Hash Serializer 설정
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }
}
