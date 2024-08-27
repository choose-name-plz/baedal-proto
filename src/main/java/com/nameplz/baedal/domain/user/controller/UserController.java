package com.nameplz.baedal.domain.user.controller;

import com.nameplz.baedal.domain.user.dto.request.UserSignupRequestDto;
import com.nameplz.baedal.domain.user.dto.request.UserUpdateRequestDto;
import com.nameplz.baedal.domain.user.dto.request.UserUpdateRoleRequestDto;
import com.nameplz.baedal.domain.user.dto.response.UserUpdateResponseDto;
import com.nameplz.baedal.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequestDto request) {
        userService.signup(request);
        return ResponseEntity.ok("User successfully registered");
    }

//    // 로그인은 jwt 필터에서 처리
//    @PostMapping("/sign-in")
//    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto request) {
//        UserLoginResponseDto response = userService.login(request);
//        return ResponseEntity.ok(response);
//    }

    // 로그아웃 (Spring Security에서 기본적으로 Stateless 설정으로 인해 로그아웃 기능은 필요하지 않지만, 추가적으로 구현할 수 있다?)
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Successfully logged out");
    }

    // 회원정보 단일검색
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        UserUpdateResponseDto userUpdateResponseDto = userService.getUserByUsername(username);
        if (userUpdateResponseDto != null) {
            return ResponseEntity.ok(userUpdateResponseDto);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    // 회원정보 수정
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserUpdateRequestDto updateUserRequest) {
        try {
            UserUpdateResponseDto userUpdateResponseDto = userService.updateUser(username, updateUserRequest);
            return ResponseEntity.ok(userUpdateResponseDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 탈퇴
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        try {
            userService.deleteUser(username);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 회원정보 전체검색 (페이징 포함) - 관리자기능
    @Secured("ROLE_MASTER")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "createdAt") String sortBy) {
        List<UserUpdateResponseDto> users = userService.getAllUsers(page, size, sortBy);
        return ResponseEntity.ok(users);
    }

    // 회원 권한 변경 - 관리자기능
    @Secured("ROLE_MASTER")
    @PatchMapping("/{username}/status")
    public ResponseEntity<?> updateRole(@PathVariable String username, @RequestBody UserUpdateRoleRequestDto updateRoleRequest) {
        try {
            UserUpdateResponseDto userUpdateResponseDto = userService.updateUserRole(username, updateRoleRequest);
            return ResponseEntity.ok(userUpdateResponseDto);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}