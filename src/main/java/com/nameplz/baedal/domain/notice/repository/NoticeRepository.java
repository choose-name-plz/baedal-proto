package com.nameplz.baedal.domain.notice.repository;

import com.nameplz.baedal.domain.notice.domain.Notice;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, UUID> {

    Page<Notice> findAllByIsPublicIsTrue(Pageable pageable);
}
