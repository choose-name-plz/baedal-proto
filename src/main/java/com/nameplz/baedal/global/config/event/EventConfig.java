package com.nameplz.baedal.global.config.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventConfig {

    private final ApplicationContext context;

    /**
     * 스프링 실행 시 Events 클래스에 ApplicationEventPublisher 주입
     */
    @Bean
    public InitializingBean eventsInitializer() {
        return () -> Events.setPublisher(context);
    }
}
