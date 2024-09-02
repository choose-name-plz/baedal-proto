package com.nameplz.baedal.domain.notice.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "is_public")
    private boolean isPublic;

    public static Notice create(String content) {

        Notice notice = new Notice();

        notice.content = content;
        notice.isPublic = true;

        return notice;
    }

    // 상태 변경
    public void changeStatus() {
        isPublic = !isPublic;
    }
}
