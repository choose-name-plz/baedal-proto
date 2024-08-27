package com.nameplz.baedal.domain.store.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.nameplz.baedal.domain.store.domain.Store;
import com.nameplz.baedal.domain.store.dto.response.StoreResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface StoreMapper {


    StoreResponseDto storeToDto(Store store, String categoryName);


}
