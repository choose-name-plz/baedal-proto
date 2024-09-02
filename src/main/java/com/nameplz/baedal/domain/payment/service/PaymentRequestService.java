package com.nameplz.baedal.domain.payment.service;

import com.nameplz.baedal.domain.payment.domain.Payment;

/**
 * 외부 결제 모듈에 결제 요청하는 도메인 서비스
 * 도메인 서비스 : 도메인 영역에서 한 애그리거트에 넣기 복잡한 로직을 다루거나, 여러 애그리거트가 필요한 경우 사용
 * 또는 외부 서비스를 호출하는 로직을 처리할 때 사용 (이때는 인터페이스로 분리하여 구현체를 infra 패키지에 위치)
 */
public interface PaymentRequestService {

    void requestPayment(Payment payment);
}
