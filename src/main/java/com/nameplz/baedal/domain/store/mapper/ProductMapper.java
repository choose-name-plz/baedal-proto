package com.nameplz.baedal.domain.store.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.nameplz.baedal.domain.store.domain.Product;
import com.nameplz.baedal.domain.store.dto.response.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface ProductMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(expression = "java(product.getStore().getId().toString())", target = "storeId")
    @Mapping(expression = "java(product.isPublic())", target = "isPublic")
    ProductResponseDto productToDto(Product product);

}
