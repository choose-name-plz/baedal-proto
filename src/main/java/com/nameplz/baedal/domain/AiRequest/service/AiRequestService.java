package com.nameplz.baedal.domain.AiRequest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nameplz.baedal.domain.AiRequest.domain.AiRequest;
import com.nameplz.baedal.domain.AiRequest.dto.request.AiRequestDto;
import com.nameplz.baedal.domain.AiRequest.dto.response.AiResponseDto;
import com.nameplz.baedal.domain.AiRequest.mapper.AiRequestMapper;
import com.nameplz.baedal.domain.AiRequest.repository.AiRequestRepository;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.repository.UserRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AiRequestService {

    private final UserRepository userRepository;
    private final AiRequestRepository aiRequestRepository;
    private final AiRequestMapper aiRequestMapper;
    private final RestTemplate restTemplate;

    // restTemplate 은 RestTemplateBuilder 인스턴스를 주입해야 되어서... @RequiredArgsConstructor 못씀
    public AiRequestService(RestTemplateBuilder builder, AiRequestRepository aiRequestRepository,
        AiRequestMapper aiRequestMapper, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.restTemplate = builder.build();
        this.aiRequestRepository = aiRequestRepository;
        this.aiRequestMapper = aiRequestMapper;
    }

    /**
     * AI를 통한 상품 설명 자동생성
     */
    public AiResponseDto createAiRequest(AiRequestDto aiRequestDto, String username)
        throws JsonProcessingException {

        log.info("createAIRequest Service 실행");

        // 요청 유저와 요청 데이터 text 를 담아 Entity 생성
        User user = userRepository.findById(username)
            .orElseThrow(() -> new GlobalException(ResultCase.USER_NOT_FOUND));
        AiRequest aiRequest = AiRequest.create(
            aiRequestDto.contents().get(0).parts().get(0).text(),
            user
        );

        // 응답 데이터 text 를 담아 Entiy 업데이트
        String responseContent = callAiApi(aiRequestDto);
        AiRequest updateResponse = AiRequest.updateResponseContent(aiRequest, responseContent);

        // Entity를 DB에 저장
        AiRequest savedRequest = aiRequestRepository.save(updateResponse);

        // 저장한 요청, 응답 데이터를 dto로 변환해 response
        return aiRequestMapper.aiRequestToAiResponseDto(savedRequest);
    }

    /**
     * AI 상품 설명 요청 기록 조회
     */
    public List<AiResponseDto> getAiRequest(String username) {
        List<AiRequest> aiResponseList = aiRequestRepository.findAllByUser_Username(username);
        if (aiResponseList.isEmpty()) {
            throw new GlobalException(ResultCase.AIREQUEST_NOT_FOUND);
        }
        return aiResponseList.stream()
            .map(aiRequestMapper::aiRequestToAiResponseDto)
            .collect(Collectors.toList()); // List<AiRequest>를 List<AiReponseDto>로 변경하는 로직
    }

    /**
     * RestTemplate 이용 API 요청메소드
     */
    public String callAiApi(AiRequestDto aiRequestDto) throws JsonProcessingException {

        log.info("callAiApi 실행");
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=AIzaSyBxYMu3a8C3XOW_5hgaJz91UFZ0wtltDL0";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<AiRequestDto> requestEntity = new HttpEntity<>(aiRequestDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            requestEntity,
            String.class
        );

        log.info(response.getBody());

        return extractTextFromResponse(response.getBody());
    }

    /**
     * API response 에서 text 추출하는 메소드
     */
    public String extractTextFromResponse(String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode candidatesNode = rootNode.path("candidates");

        if (candidatesNode.isArray() && !candidatesNode.isEmpty()) {
            JsonNode contentNode = candidatesNode.get(0).path("content");
            JsonNode partsNode = contentNode.path("parts");

            if (partsNode.isArray() && !partsNode.isEmpty()) {
                JsonNode partNode = partsNode.get(0);
                String text = partNode.path("text").asText();
                return text;
            }
        }
        return null;
    }
}
