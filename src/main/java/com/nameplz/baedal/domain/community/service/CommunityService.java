package com.nameplz.baedal.domain.community.service;

import com.nameplz.baedal.domain.community.domain.Community;
import com.nameplz.baedal.domain.community.dto.response.CommunityResponseDto;
import com.nameplz.baedal.domain.community.repository.CommunityRepository;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.domain.UserRole;
import com.nameplz.baedal.domain.user.repository.UserRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;


    /**
     * 커뮤니티 생성
     */
    @Transactional
    public String createCommunity(User reportUser, String reportContent) {
        Community community = Community.createCommunity(reportContent, reportUser);
        Community com = communityRepository.save(community);
        return com.getId().toString();
    }

    /**
     * 답변 추가
     */
    @Transactional
    public CommunityResponseDto addAnswerToCommunity(User answerUser, String answerComment,
        UUID communityId) {
        Community community = findCommunityByIdAndCheck(communityId);
        community.addComment(answerComment, answerUser);
        return CommunityResponseDto.CommunityToResponseDto(community);
    }

    public CommunityResponseDto findCommunityById(User user, UUID communityId) {
        Community community = findCommunityByIdAndCheck(communityId);

        // 신청자가 같거나 머스터인 경우
        if (user.getRole().equals(UserRole.MASTER) || community.getReportUser().getUsername()
            .equals(user.getUsername())) {
            return CommunityResponseDto.CommunityToResponseDto(community);

        }

        throw new GlobalException(ResultCase.INVALID_INPUT);
    }

    /**
     * 고객센터 목록 조회
     */
    public List<CommunityResponseDto> findCommunityList(Pageable pageable) {
        List<Community> communityList = communityRepository.findAllByDeletedAtIsNull(
            pageable);

        return communityList.stream().map(
            CommunityResponseDto::CommunityToResponseDto
        ).toList();
    }

    /**
     * Id 별로 있는지 확인 후 불러오는 함수 모음
     */
    private Community findCommunityByIdAndCheck(UUID communityId) {
        return communityRepository.findByIdAndDeletedAtIsNull(communityId)
            .orElseThrow(() -> new GlobalException(ResultCase.INVALID_INPUT));
    }


}
