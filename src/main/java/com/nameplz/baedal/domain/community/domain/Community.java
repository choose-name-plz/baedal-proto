package com.nameplz.baedal.domain.community.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.domain.UserRole;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Table(name = "p_community")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Community extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_user_id")
    private User reportUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_user_id")
    private User commentUser;

    @Column(name = "report_content", length = 1000)
    private String reportContent;

    @Column(name = "answer_content", length = 1000)
    private String answerContent;

    public static Community createCommunity(String reportContent, User reportUser) {
        Community community = new Community();
        community.reportContent = reportContent;
        community.reportUser = reportUser;
        return community;
    }

    /**
     * 답글을 달아주는 메서드
     */
    public void addComment(String comment, User commentUser) {
        // 입력자가 마스터가 아니면 에러
        if (!StringUtils.hasText(reportContent) && !commentUser.getRole().equals(UserRole.MASTER)) {
            throw new GlobalException(ResultCase.INVALID_INPUT);
        }

        this.commentUser = commentUser;
        this.answerContent = comment;
    }
}
