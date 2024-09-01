package com.nameplz.baedal.global.config.event;

import com.nameplz.baedal.global.event.BaseEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 전역으로 이벤트를 발행할 수 있는 클래스
 */
public class Events {

    private static ApplicationEventPublisher publisher;

    /**
     * package-private 접근제어자로, 같은 패키지 내에서 EventConfig 클래스에서 ApplicationEventPublisher 주입
     */
    static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    /**
     * 이벤트 발행 시 호출.
     * 모든 이벤트는 BaseEvent 클래스를 상속받도록 강제함.
     */
    public static void raise(BaseEvent event) {
        if (event != null) {
            publisher.publishEvent(event);
        }
    }
}
