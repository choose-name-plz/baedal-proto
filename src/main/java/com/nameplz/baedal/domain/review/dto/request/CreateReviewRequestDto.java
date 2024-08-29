package com.nameplz.baedal.domain.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public record CreateReviewRequestDto(

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        @Length(max = 1000, message = "리뷰 내용은 1000자 이하여야 합니다.")
        String content,

        @Range(min = 1, max = 5, message = "평점은 1~5 사이어야 합니다.")
        Integer rating,

        @NotNull(message = "신고 여부는 필수입니다.")
        Boolean isReported
) {
}
