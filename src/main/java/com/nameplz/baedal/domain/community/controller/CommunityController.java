package com.nameplz.baedal.domain.community.controller;

import com.nameplz.baedal.domain.community.dto.request.CommunityAddAnswerRequestBody;
import com.nameplz.baedal.domain.community.dto.request.CommunityCreateRequestDto;
import com.nameplz.baedal.domain.community.dto.response.CommunityIdResponseDto;
import com.nameplz.baedal.domain.community.dto.response.CommunityListResponseDto;
import com.nameplz.baedal.domain.community.dto.response.CommunityResponseDto;
import com.nameplz.baedal.domain.community.service.CommunityService;
import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.global.common.response.CommonResponse;
import com.nameplz.baedal.global.common.security.annotation.IsMaster;
import com.nameplz.baedal.global.common.security.annotation.IsMasterOrCustomer;
import com.nameplz.baedal.global.common.security.annotation.LoginUser;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "고객센터")
@RequiredArgsConstructor
@RequestMapping("community")
@RestController
public class CommunityController {

    private final CommunityService communityService;

    /**
     * 신고 접수
     */
    @IsMasterOrCustomer
    @PostMapping
    public CommonResponse<CommunityIdResponseDto> createCommunity(
        @RequestBody CommunityCreateRequestDto requestDto,
        @LoginUser User loginUser
    ) {

        String communityId = communityService.createCommunity(loginUser, requestDto.report());
        return CommonResponse.success(new CommunityIdResponseDto(communityId));
    }

    /**
     * 신고에 답변 추가
     */
    @IsMaster
    @PatchMapping("/{id}/comment")
    public CommonResponse<CommunityResponseDto> commentCommunity(
        @RequestBody CommunityAddAnswerRequestBody requestBody,
        @PathVariable("id") UUID communityId,
        @LoginUser User user
    ) {
        CommunityResponseDto communityResponseDto = communityService.addAnswerToCommunity(user,
            requestBody.answer(), communityId);
        return CommonResponse.success(communityResponseDto);
    }

    @IsMasterOrCustomer
    @GetMapping("/{id}")
    public CommonResponse<CommunityResponseDto> findCommunityById(
        @PathVariable("id") UUID communityId,
        @LoginUser User user
    ) {
        CommunityResponseDto communityById = communityService.findCommunityById(user, communityId);
        return CommonResponse.success(communityById);
    }

    @IsMaster
    @GetMapping
    public CommonResponse<CommunityListResponseDto> findAllCommunity(
        @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        @LoginUser User user
    ) {
        List<CommunityResponseDto> communityList = communityService.findCommunityList(
            pageable);
        return CommonResponse.success(new CommunityListResponseDto(communityList));
    }

}
