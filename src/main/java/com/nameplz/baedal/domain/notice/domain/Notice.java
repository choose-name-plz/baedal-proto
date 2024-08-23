package com.nameplz.baedal.domain.notice.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

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
}
