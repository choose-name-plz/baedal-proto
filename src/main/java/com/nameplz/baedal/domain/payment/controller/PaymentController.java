package com.nameplz.baedal.domain.payment.controller;

import com.nameplz.baedal.domain.payment.dto.request.ChangePaymentStatusRequestDto;
import com.nameplz.baedal.domain.payment.dto.request.CreatePaymentRequestDto;
import com.nameplz.baedal.domain.payment.dto.response.PaymentResponseDto;
import com.nameplz.baedal.domain.payment.service.PaymentService;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.global.common.response.CommonResponse;
import com.nameplz.baedal.global.common.response.EmptyResponseDto;
import com.nameplz.baedal.global.common.security.annotation.IsMaster;
import com.nameplz.baedal.global.common.security.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@IsMaster
@Tag(name = "결제")
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 결제 조회
     */
    @GetMapping("/{paymentId}")
    @Operation(summary = "결제 조회")
    public CommonResponse<PaymentResponseDto> getPayment(@PathVariable UUID paymentId) {

        PaymentResponseDto response = paymentService.getPayment(paymentId);

        return CommonResponse.success(response);
    }

    /**
     * 결제 리스트 조회
     */
    @GetMapping
    @Operation(summary = "결제 리스트 조회")
    public CommonResponse<List<PaymentResponseDto>> getPaymentList(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        List<PaymentResponseDto> response = paymentService.getPaymentList(pageable);

        return CommonResponse.success(response);
    }

    /**
     * 결제 처리
     */
    @PostMapping
    @Operation(summary = "결제 처리")
    public CommonResponse<PaymentResponseDto> createPayment(@Valid @RequestBody CreatePaymentRequestDto request) {

        PaymentResponseDto response = paymentService.createPayment(request);

        return CommonResponse.success(response);
    }

    /**
     * 결제 상태 변경
     */
    @PatchMapping("/{paymentId}/status")
    @Operation(summary = "결제 상태 변경")
    public CommonResponse<EmptyResponseDto> updatePaymentStatus(
            @PathVariable UUID paymentId,
            @RequestBody ChangePaymentStatusRequestDto request,
            @LoginUser User user
    ) {

        paymentService.changePaymentStatus(paymentId, request, user);

        return CommonResponse.success();
    }

    /**
     * 결제 삭제
     */
    @DeleteMapping("/{paymentId}")
    @Operation(summary = "결제 삭제")
    public CommonResponse<EmptyResponseDto> deletePayment(
            @PathVariable UUID paymentId,
            @LoginUser User user
    ) {

        paymentService.deletePayment(paymentId, user);

        return CommonResponse.success();
    }
}
