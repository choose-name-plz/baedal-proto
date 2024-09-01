package com.nameplz.baedal.domain.user.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.dto.response.UserUpdateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = SPRING)
public interface UserMapper {

    // TODO : 그리고 @Mapper(componentModel = "spring") 로 스프링 빈으로 등록한 만큼 UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);는 특별한 이유가 있지 않는 이상 없어도 될 것 같습니다!
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // entity to Dto
    UserUpdateResponseDto userToUserUpdateResponseDto(User user);
}