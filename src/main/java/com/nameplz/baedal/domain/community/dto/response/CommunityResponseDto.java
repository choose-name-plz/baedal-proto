package com.nameplz.baedal.domain.community.dto.response;

import com.nameplz.baedal.domain.community.domain.Community;

public record CommunityResponseDto(String communityId, String reportUser, String commentUser,
                                   String reportComment,
                                   String answerComment) {

    public static CommunityResponseDto CommunityToResponseDto(Community community) {
        return new CommunityResponseDto(community.getId().toString(),
            community.getReportUser().getUsername(),
            community.getCommentUser().getUsername(), community.getReportContent(),
            community.getAnswerContent());
    }

}
