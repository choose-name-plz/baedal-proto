package com.nameplz.baedal.domain.notice.service;

import com.nameplz.baedal.domain.notice.domain.Notice;
import com.nameplz.baedal.domain.notice.dto.response.NoticeResponseDto;
import com.nameplz.baedal.domain.notice.repository.NoticeRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /*
        공지 생성
     */
    @Transactional
    public String createNotice(String content) {
        Notice notice = noticeRepository.save(Notice.create(content));
        return notice.getId().toString();
    }

    @Transactional
    public void changeNoticeStatus(UUID noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new GlobalException(
            ResultCase.NOT_FOUND));

        notice.changeStatus();
    }

    /*
        모든 공지 목록 조회(admin 용)
     */
    public List<NoticeResponseDto> findAllNoticeList(Pageable pageable) {
        Page<Notice> noticeList = noticeRepository.findAll(pageable);
        return noticeList.stream().map(NoticeResponseDto::noticeToDto).toList();
    }

    /*
        공개된 공지 목록 조회
     */
    public List<NoticeResponseDto> findPublicNoticeList(Pageable pageable) {
        Page<Notice> noticeList = noticeRepository.findAllByIsPublicIsTrue(pageable);
        return noticeList.stream().map(NoticeResponseDto::noticeToDto).toList();
    }

}
