package com.nameplz.baedal.domain.review.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponseDto(
        UUID id,
        UUID orderId,
        String username,
        String content,
        Integer rating,
        boolean isReported,
        LocalDateTime createdAt
) {
}
