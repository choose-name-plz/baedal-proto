package com.nameplz.baedal.domain.web.admin.dto.response;

import com.nameplz.baedal.domain.model.Address;
import com.nameplz.baedal.domain.order.domain.OrderStatus;
import java.time.LocalDateTime;

public record OrderAdminResponseDto(
    String id,

    OrderStatus status,
    Address address,

    LocalDateTime deletedAt,
    LocalDateTime createdAt
) {

}
