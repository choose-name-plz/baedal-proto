package com.nameplz.baedal.infra.payment;

import com.nameplz.baedal.domain.payment.domain.Payment;
import com.nameplz.baedal.domain.payment.dto.response.ExternalPaymentResponseDto;
import com.nameplz.baedal.domain.payment.service.PaymentRequestService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 토스 결제 요청 서비스
 * 실제로 토스 결제 요청 로직을 수행한다고 가정
 * 외부 서비스 이므로, infra 패키지에 위치
 * domain 패키지의 PaymentRequestService 도메인 서비스에 의존
 */
@Service
public class TossPaymentRequestService implements PaymentRequestService {

    @Override
    public void requestPayment(Payment payment) {

        // toss 결제 요청 로직 했다고 가정

        ExternalPaymentResponseDto response = requestExternalPaymentModule(payment);

        if (!response.isSuccess()) {
            payment.fail();
        }

        payment.success(response.paymentKey());
    }

    private ExternalPaymentResponseDto requestExternalPaymentModule(Payment payment) {
        // 외부 결제 모듈에 결제 요청하는 로직
        boolean isSuccess = Math.random() >= 0.5;

        if (!isSuccess) {
            return new ExternalPaymentResponseDto(false, null);
        }

        return new ExternalPaymentResponseDto(true, UUID.randomUUID().toString());
    }
}
