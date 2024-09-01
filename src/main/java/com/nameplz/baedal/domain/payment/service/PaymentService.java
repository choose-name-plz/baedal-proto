package com.nameplz.baedal.domain.payment.service;

import com.nameplz.baedal.domain.model.Money;
import com.nameplz.baedal.domain.payment.domain.Payment;
import com.nameplz.baedal.domain.payment.dto.request.ChangePaymentStatusRequestDto;
import com.nameplz.baedal.domain.payment.dto.request.CreatePaymentRequestDto;
import com.nameplz.baedal.domain.payment.dto.response.PaymentResponseDto;
import com.nameplz.baedal.domain.payment.mapper.PaymentMapper;
import com.nameplz.baedal.domain.payment.repository.PaymentRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentMapper mapper;
    private final PaymentRepository paymentRepository;

    /**
     * 결제 조회
     */
    public PaymentResponseDto getPayment(UUID paymentId) {
        Payment payment = findPayment(paymentId);
        return mapper.toPaymentResponseDto(payment);
    }

    /**
     * 결제 리스트 조회
     */
    public List<PaymentResponseDto> getPaymentList(Pageable pageable) {

        return paymentRepository.findAllByDeletedAtIsNull(pageable)
                .stream()
                .map(mapper::toPaymentResponseDto)
                .toList();
    }

    /**
     * 결제 생성
     */
    @Transactional
    public PaymentResponseDto createPayment(CreatePaymentRequestDto request) {

        Payment payment = Payment.create(new Money(request.amount()), request.method(), request.username());

        // 외부 결제 모듈에 결제 요청

        payment.success();
        Payment savedPayment = paymentRepository.save(payment);

        return mapper.toPaymentResponseDto(savedPayment);
    }

    /**
     * 결제 상태 변경
     */
    @Transactional
    public void changePaymentStatus(UUID paymentId, ChangePaymentStatusRequestDto request) {
        Payment payment = findPayment(paymentId);
        payment.changePaymentStatus(request.status());
    }

    /**
     * 결제 삭제
     */
    @Transactional
    public void deletePayment(UUID paymentId) {
        Payment payment = findPayment(paymentId);
        // TODO : 삭제한 사용자 정보를 넘겨줘야함
        payment.deleteEntity(null);
    }

    private Payment findPayment(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new GlobalException(ResultCase.PAYMENT_NOT_FOUND));
    }
}
