package com.nameplz.baedal.global.common.redis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nameplz.baedal.domain.user.domain.UserRole;
import java.time.LocalDateTime;

public record UserAuthDto(String username, UserRole role,
                          @JsonSerialize(using = LocalDateTimeSerializer.class)
                          @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                          @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                          LocalDateTime createTime) {

    public static UserAuthDto of(String username, UserRole role,
        LocalDateTime createTime) {

        return new UserAuthDto(username, role, createTime);

    }

}
