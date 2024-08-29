package com.nameplz.baedal.domain.payment.controller;

import com.nameplz.baedal.domain.payment.dto.request.ChangePaymentStatusRequestDto;
import com.nameplz.baedal.domain.payment.dto.request.CreatePaymentRequestDto;
import com.nameplz.baedal.domain.payment.dto.response.PaymentResponseDto;
import com.nameplz.baedal.domain.payment.service.PaymentService;
import com.nameplz.baedal.global.common.response.CommonResponse;
import com.nameplz.baedal.global.common.response.EmptyResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 조회
     */
    @GetMapping("/{paymentId}")
    public CommonResponse<PaymentResponseDto> getPayment(@PathVariable UUID paymentId) {

        PaymentResponseDto response = paymentService.getPayment(paymentId);

        return CommonResponse.success(response);
    }

    /**
     * 결제 리스트 조회
     */
    @GetMapping
    public CommonResponse<List<PaymentResponseDto>> getPaymentList(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        List<PaymentResponseDto> response = paymentService.getPaymentList(pageable);

        return CommonResponse.success(response);
    }

    /**
     * 결제 생성
     */
    @PostMapping
    public CommonResponse<PaymentResponseDto> createPayment(@Valid @RequestBody CreatePaymentRequestDto request) {

        PaymentResponseDto response = paymentService.createPayment(request);

        return CommonResponse.success(response);
    }

    /**
     * 결제 상태 변경
     */
    @PatchMapping("/{paymentId}/status")
    public CommonResponse<EmptyResponseDto> updatePaymentStatus(
            @PathVariable UUID paymentId,
            @RequestBody ChangePaymentStatusRequestDto request
    ) {

        paymentService.changePaymentStatus(paymentId, request);

        return CommonResponse.success();
    }

    /**
     * 결제 삭제
     */
    @DeleteMapping("/{paymentId}")
    public CommonResponse<EmptyResponseDto> deletePayment(@PathVariable UUID paymentId) {

        paymentService.deletePayment(paymentId);

        return CommonResponse.success();
    }
}
