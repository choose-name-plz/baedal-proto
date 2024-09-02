package com.nameplz.baedal.domain.notice.dto.response;

import com.nameplz.baedal.domain.notice.domain.Notice;
import java.time.LocalDateTime;

public record NoticeResponseDto(String id, String content, LocalDateTime createdAt,
                                Boolean checkPublic) {

    public static NoticeResponseDto noticeToDto(Notice notice) {
        return new NoticeResponseDto(notice.getId().toString(), notice.getContent(),
            notice.getCreatedAt(), notice.isPublic());
    }

}
