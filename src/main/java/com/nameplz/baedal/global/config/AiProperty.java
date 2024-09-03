package com.nameplz.baedal.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("ai.api")
public record AiProperty(
    String url,
    String key
) {

    public String getUrlWithKey() {
        return url + "?key=" + key;
    }
}
