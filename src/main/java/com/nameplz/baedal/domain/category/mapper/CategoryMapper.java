package com.nameplz.baedal.domain.category.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.nameplz.baedal.domain.category.domain.Category;
import com.nameplz.baedal.domain.category.dto.response.CategoryResponseDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface CategoryMapper {

    // Category목록을 ResponseDto에 맞게 수정
    List<CategoryResponseDto> categoryToResponseDto(List<Category> category);

}
