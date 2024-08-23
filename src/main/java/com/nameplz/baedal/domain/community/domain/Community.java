package com.nameplz.baedal.domain.community.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "p_community")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "report_content", length = 1000)
    private String reportContent;

    @Column(name = "answer_content", length = 1000)
    private String answerContent;

    public static Community createCommunity(String reportContent, String answerContent) {
        Community community = new Community();

        community.reportContent = reportContent;
        community.answerContent = answerContent;

        return community;
    }
}
