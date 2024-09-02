package com.nameplz.baedal.domain.order.dto.request;

import com.nameplz.baedal.domain.model.Address;
import com.nameplz.baedal.domain.order.domain.OrderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Schema(description = "주문 생성 요청")
public record CreateOrderRequestDto(

        @Schema(description = "주문자 (username)", example = "johndoe")
        @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "주문자는 소문자 알파벳과 숫자로 4자 이상 10자 이하로 입력해주세요.")
        String orderer,

        @Schema(description = "가게 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        @NotNull(message = "가게 ID는 필수입니다.")
        UUID storeId,

        @Schema(description = "주문 타입", example = "ONLINE")
        OrderType orderType,

        @Schema(description = "주문 요청 사항", example = "2인분 같은 1인분으로 부탁드려요.")
        String comment,

        @Schema(description = "주소 정보")
        @NotNull(message = "주소 정보는 필수입니다.")
        Address address,

        @Schema(description = "주문 상품 목록")
        @Size(min = 1, message = "주문 상품은 최소 1개 이상이어야 합니다.")
        List<CreateOrderLineRequestDto> orderLineList,

        @Schema(description = "결제 정보")
        PaymentRequestDto paymentInfo
) {
}
