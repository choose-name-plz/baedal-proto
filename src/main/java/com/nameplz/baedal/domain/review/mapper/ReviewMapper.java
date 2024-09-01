package com.nameplz.baedal.domain.review.mapper;

import com.nameplz.baedal.domain.review.domain.Review;
import com.nameplz.baedal.domain.review.dto.response.ReviewResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ReviewMapper {

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "rating", source = "rating.score")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "isReported", source = "reported")
    ReviewResponseDto toReviewResponseDto(Review review);
}
