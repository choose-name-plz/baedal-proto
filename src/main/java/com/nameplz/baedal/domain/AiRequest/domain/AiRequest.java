package com.nameplz.baedal.domain.AiRequest.domain;

import com.nameplz.baedal.domain.model.BaseEntity;
import com.nameplz.baedal.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY) // user Entity 는 바로 사용할 일 없으므로 LAZY Loading
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "request_content", length = 255)
    @Size(max = 255, message = "AI 요청은 255자를 넘을 수 없습니다.") // 요청 데이터는 길이 제한
    private String requestContent;

    @Column(name = "response_content", columnDefinition = "TEXT") // 응답 데이터는 길이 제한 없는 TEXT 타입으로 저장
    private String responseContent;

    public static AiRequest create(String requestText, User user) {
        AiRequest aiRequest = new AiRequest();

        // request 데이터가 있고 공백이 아니면 Entity 에 저장
        if (!requestText.isBlank()) {
            aiRequest.requestContent = requestText;
        }
        aiRequest.user = user;

        return aiRequest;
    }

    public void updateResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }
}

