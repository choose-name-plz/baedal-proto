package com.nameplz.baedal.domain.AiRequest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.nameplz.baedal.global.config.AiProperty;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiRequestService {

    private final UserRepository userRepository;
    private final AiRequestRepository aiRequestRepository;
    private final AiRequestMapper aiRequestMapper;
    private final RestTemplate restTemplate;
    private final AiProperty aiProperty;

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
        aiRequest.updateResponseContent(responseContent);

        // Entity를 DB에 저장
        AiRequest savedRequest = aiRequestRepository.save(aiRequest);

        // 저장한 요청, 응답 데이터를 dto로 변환해 response
        return aiRequestMapper.aiRequestToAiResponseDto(savedRequest);
    }

    /**
     * AI 상품 설명 요청 기록 조회
     */
    public List<AiResponseDto> getAiRequest(String username) {
        List<AiRequest> aiResponseList = aiRequestRepository.findAllByUser_Username(username);

        // 응답이 없으면 빈 List 반환
        return aiResponseList.stream()
            .map(aiRequestMapper::aiRequestToAiResponseDto)
            .toList(); // List<AiRequest>를 List<AiReponseDto>로 변경하는 로직
    }

    /**
     * RestTemplate 이용 API 요청메소드
     */
    public String callAiApi(AiRequestDto aiRequestDto) throws JsonProcessingException {

        log.info("callAiApi 실행");
        String url = aiProperty.getUrlWithKey();
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

        return objectMapper.readTree(responseBody)
            .path("candidates")
            .path(0)
            .path("content")
            .path("parts")
            .path(0)
            .path("text")
            .asText("");
    }
}
