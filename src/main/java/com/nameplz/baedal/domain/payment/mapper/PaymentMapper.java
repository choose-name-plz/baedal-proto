package com.nameplz.baedal.domain.payment.mapper;

import com.nameplz.baedal.domain.payment.domain.Payment;
import com.nameplz.baedal.domain.payment.dto.response.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PaymentMapper {

    @Mapping(target = "amount", source = "payment.amount.amount")
    @Mapping(target = "paymentId", source = "payment.id")
    PaymentResponseDto toPaymentResponseDto(Payment payment);
}
