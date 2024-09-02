package com.nameplz.baedal.global.event;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 모든 이벤트는 BaseEvent 클래스를 상속받도록 강제함.
 */
@Getter
@ToString
public abstract class BaseEvent {

    protected final UUID id = UUID.randomUUID(); // UUID 자동 할당
    protected final LocalDateTime createdAt = LocalDateTime.now(); // 이벤트 발생 시간 자동 할당
}
