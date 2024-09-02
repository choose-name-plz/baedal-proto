package com.nameplz.baedal.domain.web.admin.controller;

import com.nameplz.baedal.domain.notice.dto.response.NoticeResponseDto;
import com.nameplz.baedal.domain.notice.service.NoticeService;
import com.nameplz.baedal.domain.web.admin.dto.request.NoticeCreateRequestDto;
import com.nameplz.baedal.global.common.security.annotation.IsMaster;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@IsMaster
@Controller
public class AdminNoticeController {

    private final NoticeService noticeService;

    /*
        전체 조회
     */
    @GetMapping("/admin/notice")
    public String noticeList(Model model) {
        PageRequest pageable = getPageRequestInfo();
        List<NoticeResponseDto> list = noticeService.findAllNoticeList(pageable);
        model.addAttribute("list", list);

        return "page/notice";
    }

    /*
        추가
     */
    @PostMapping("/admin/notice")
    public String createNotice(@RequestBody NoticeCreateRequestDto requestDto) {
        noticeService.createNotice(requestDto.content());
        return "redirect:/admin/notice";
    }

    /*
        상태 변경
     */
    @PatchMapping("/admin/notice/{id}/status")
    public String changeNotice(
        @PathVariable("id") UUID noticeId
    ) {
        noticeService.changeNoticeStatus(noticeId);
        return "redirect:/admin/notice";
    }


    private PageRequest getPageRequestInfo() {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        return PageRequest.of(0, 100, sort);
    }
}
