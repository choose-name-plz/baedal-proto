package com.nameplz.baedal.global.common.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.data.redis")
public record RedisProperty(
    String host,
    int port,
    String password
) {

}
