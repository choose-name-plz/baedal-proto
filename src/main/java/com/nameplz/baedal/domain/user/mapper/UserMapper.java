package com.nameplz.baedal.domain.user.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.dto.response.UserUpdateResponseDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = SPRING)
public interface UserMapper {

    // entity to Dto
    UserUpdateResponseDto userToUserUpdateResponseDto(User user);
}