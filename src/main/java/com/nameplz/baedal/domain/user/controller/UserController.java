package com.nameplz.baedal.domain.user.controller;

import com.nameplz.baedal.domain.user.domain.UserRole;
import com.nameplz.baedal.domain.user.dto.request.UserSignupRequestDto;
import com.nameplz.baedal.domain.user.dto.request.UserUpdateRequestDto;
import com.nameplz.baedal.domain.user.dto.request.UserUpdateRoleRequestDto;
import com.nameplz.baedal.domain.user.dto.response.UserUpdateResponseDto;
import com.nameplz.baedal.domain.user.service.UserService;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.CommonResponse;
import com.nameplz.baedal.global.common.response.EmptyResponseDto;
import com.nameplz.baedal.global.common.response.ResultCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j(topic = "UserController")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    public CommonResponse<EmptyResponseDto> signup(@RequestBody UserSignupRequestDto request) {
        userService.signup(request);
        return CommonResponse.success();
    }

    /**
     * 로그아웃
     */
    // TODO :로그아웃 (Spring Security에서 기본적으로 Stateless 설정으로 인해 로그아웃 기능은 필요하지 않지만, 추가적으로 구현할 수 있다?)
    // TODO : JWT 특성상 서버에서 제어권이 없기 때문에 로그아웃을 구현할 수 없지만, 서버에 로그아웃된 JWT를 블랙리스트로 저장해두고 이후 동일한 토큰으로 요청 시 로그인이 필요하다고 응답하는 식으로 구현하면 됩니다. 로그인 시에는 블랙리스트가 있는지 확인 후 있으면 삭제해주는 식으로요!
    @PostMapping("/logout")
    public CommonResponse<EmptyResponseDto> logout() {
        SecurityContextHolder.clearContext();
        return CommonResponse.success();
    }

    /**
     * 회원정보 단일검색
     */
    @GetMapping("/{username}")
    public CommonResponse<UserUpdateResponseDto> getUser(@PathVariable String username) {
        UserUpdateResponseDto userUpdateResponseDto = userService.getUserByUsername(username);

        return CommonResponse.success(userUpdateResponseDto);
    }


    /**
     * 회원정보 수정
     */
    // TODO : 에러 반환 시 try catch 안 쓰셔도 됩니다. throw new GlobalException 하면 GlobalExceptionHandler가 잡아줍니다
    // TODO : 추가로 SYSTEM_ERROR는 상태코드가 500 이라서 최후의 보루로 쓰셔야 합니다. 회원정보 수정이면 요청자가 잘못하여 예외가 발생할 확률이 매우 높으니 구체적으로 400대 에러를 반환해야합니다.
    @PutMapping("/{username}")
    public CommonResponse<UserUpdateResponseDto> updateUser(@PathVariable String username,
        @RequestBody UserUpdateRequestDto updateUserRequest) {
        try {
            UserUpdateResponseDto userUpdateResponseDto = userService.updateUser(username,
                updateUserRequest);
            return CommonResponse.success(userUpdateResponseDto);
        } catch (Exception e) {
            throw new GlobalException(ResultCase.SYSTEM_ERROR);
        }
    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping("/{username}")
    public CommonResponse<EmptyResponseDto> deleteUser(@PathVariable String username) {
        try {
            userService.deleteUser(username);
            return CommonResponse.success();
        } catch (Exception e) {
            throw new GlobalException(ResultCase.SYSTEM_ERROR);
        }
    }

    /**
     * 회원정보 전체검색 (페이징 포함) - 관리자기능
     */
    @Secured(UserRole.Authority.MASTER)
    @GetMapping("/all")
    public CommonResponse<List<UserUpdateResponseDto>> getAllUsers(
        // TODO : Pageable 객체를 바로 받는 것은 어떠신가요
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt") String sortBy) {

        // TODO : log.info("메소드 getAllUsers 실행 권한 : {}", UserRole.Authority.MASTER); 로 동일하게 작성할 수 있습니다.
        //문자열끼리 + 연산자로 하면 자바에서는 문자열이 불변이므로 새로 문자열을 만들게 됩니다. 그리고 info(...) 메서드의 파라미터로 값을 넣기 위해서 + 연산으로 문자열을 계속 새로 만들게 되어 불필요한 연산을 하게 됩니다.
        //따라서 가변적으로 넣을 부분에는 {} 로 작성하시고, {} 순서대로 매개변수로 계속 넣으시면 됩니다.
        log.info("메소드 getAllUsers 실행 권한 : " + UserRole.Authority.MASTER);

        List<UserUpdateResponseDto> users = userService.getAllUsers(page, size, sortBy);
        return CommonResponse.success(users);
    }

    /**
     * 회원 권한 변경 - 관리자기능
     */
    @Secured(UserRole.Authority.MASTER)
    @PatchMapping("/{username}/status")
    public CommonResponse<UserUpdateResponseDto> updateRole(@PathVariable String username,
        @RequestBody UserUpdateRoleRequestDto updateRoleRequest) {

        log.info("메소드 updateRole 실행 권한 : " + UserRole.Authority.MASTER);

        try {
            UserUpdateResponseDto userUpdateResponseDto = userService.updateUserRole(username,
                updateRoleRequest);
            return CommonResponse.success(userUpdateResponseDto);
        } catch (Exception e) {
            throw new GlobalException(ResultCase.SYSTEM_ERROR);
        }
    }


}