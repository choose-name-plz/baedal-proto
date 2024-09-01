package com.nameplz.baedal.domain.AiRequest.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Getter
@Table(name = "p_ai_request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AiRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "request_content")
    private String requestContent;

    @Column(name = "response_content")
    private String responseContent;

    public static AiRequest create(String requestText, User user) {
        AiRequest aiRequest = new AiRequest();

        // request 데이터가 있으면 Entity 에 저장
        if (!requestText.isEmpty()) {
            aiRequest.requestContent = requestText;
        }
        aiRequest.user = user;

        return aiRequest;
    }

    public static AiRequest updateResponseContent(AiRequest aiRequest, String responseContent) {
        aiRequest.responseContent = responseContent;
        return aiRequest;
    }
}

