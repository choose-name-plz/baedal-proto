package com.nameplz.baedal.domain.payment.service;

import com.nameplz.baedal.domain.model.Money;
import com.nameplz.baedal.domain.payment.domain.Payment;
import com.nameplz.baedal.domain.payment.dto.request.ChangePaymentStatusRequestDto;
import com.nameplz.baedal.domain.payment.dto.request.CreatePaymentRequestDto;
import com.nameplz.baedal.domain.payment.dto.response.PaymentResponseDto;
import com.nameplz.baedal.domain.payment.mapper.PaymentMapper;
import com.nameplz.baedal.domain.payment.repository.PaymentRepository;
import com.nameplz.baedal.domain.user.domain.User;
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
     * 마스터 권한이 있거나 응답 리스트의 각 요소의 username이 현재 사용자의 username과 일치하는 요소만 포함
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
        String paymentKey = UUID.randomUUID().toString();

        payment.success(paymentKey);
        Payment savedPayment = paymentRepository.save(payment);

        return mapper.toPaymentResponseDto(savedPayment);
    }

    /**
     * 결제 상태 변경
     */
    @Transactional
    public void changePaymentStatus(UUID paymentId, ChangePaymentStatusRequestDto request, User user) {
        Payment payment = findPayment(paymentId);
        payment.changePaymentStatus(request.status(), user.getUsername());
    }

    /**
     * 결제 삭제
     */
    @Transactional
    public void deletePayment(UUID paymentId, User user) {
        Payment payment = findPayment(paymentId);
        payment.deleteEntity(user.getUsername());
    }

    private Payment findPayment(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new GlobalException(ResultCase.PAYMENT_NOT_FOUND));
    }
}
