package com.nameplz.baedal.global.common.exception;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.validation.FieldError;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING) // 빈으로 주입받을 수 있음
public interface InvalidInputMapper {

    // FieldError 의 defaultMessage -> message 이름 변경
    @Mapping(source = "defaultMessage", target = "message")
    InvalidInputResponseDto toInvalidInputResponseDto(FieldError fieldError);
}
