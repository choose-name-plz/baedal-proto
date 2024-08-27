package com.nameplz.baedal.domain.user.mapper;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.dto.response.UserUpdateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Entity to Dto
    UserUpdateResponseDto userToUserUpdateResponseDto(User user);
}