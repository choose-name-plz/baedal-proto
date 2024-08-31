package com.nameplz.baedal.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class Address {

    @Column(name = "road_address")
    @Schema(description = "도로명 주소", example = "서울특별시 강남구 테헤란로 427")
    private String roadAddress;

    @Column(name = "detail_address")
    @Schema(description = "상세 주소", example = "와르르맨션 202호")
    private String detailAddress;

    @Column(name = "zipcode", length = 100)
    @Schema(description = "우편번호", example = "06159")
    private String zipcode;
}
