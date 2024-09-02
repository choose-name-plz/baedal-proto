package com.nameplz.baedal.domain.notice.controller;

import com.nameplz.baedal.domain.notice.dto.response.NoticeListResponseDto;
import com.nameplz.baedal.domain.notice.dto.response.NoticeResponseDto;
import com.nameplz.baedal.domain.notice.service.NoticeService;
import com.nameplz.baedal.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공지사항")
@RequiredArgsConstructor
@RequestMapping("notice")
@RestController
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 열린 공지사항 접속
     */
    @GetMapping
    public CommonResponse<NoticeListResponseDto> findNoticeListOnPublic(
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {

        List<NoticeResponseDto> publicNoticeList = noticeService.findPublicNoticeList(pageable);

        return CommonResponse.success(new NoticeListResponseDto(publicNoticeList));
    }

}
