package com.nameplz.baedal.domain.user.domain.controller;

import com.nameplz.baedal.domain.user.domain.dto.request.UserLoginRequest;
import com.nameplz.baedal.domain.user.domain.dto.request.UserSignupRequest;
import com.nameplz.baedal.domain.user.domain.dto.request.UserUpdateRequest;
import com.nameplz.baedal.domain.user.domain.dto.request.UserUpdateRoleRequest;
import com.nameplz.baedal.domain.user.domain.dto.response.UserLoginResponse;
import com.nameplz.baedal.domain.user.domain.dto.response.UserResponse;
import com.nameplz.baedal.domain.user.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok("User successfully registered");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        UserLoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // 로그아웃 (Spring Security에서 기본적으로 Stateless 설정으로 인해 로그아웃 기능은 필요하지 않지만, 추가적으로 구현할 수 있습니다.)
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Successfully logged out");
    }

    // 회원정보 단일검색
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        UserResponse userResponse = userService.getUserByUsername(username);
        if (userResponse != null) {
            return ResponseEntity.ok(userResponse);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    // 회원정보 수정
    @PutMapping("/user/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserUpdateRequest updateUserRequest) {
        try {
            UserResponse userResponse = userService.updateUser(username, updateUserRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 회원 탈퇴
    @DeleteMapping("/user/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        try {
            userService.deleteUser(username);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // 회원정보 전체검색 (페이징 포함) - 관리자기능
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "createdDate") String sortBy) {
        List<UserResponse> users = userService.getAllUsers(page, size, sortBy);
        return ResponseEntity.ok(users);
    }

    // 회원 권한 변경 - 관리자기능
    @PatchMapping("/user/{username}/role")
    public ResponseEntity<?> updateRole(@PathVariable String username, @RequestBody UserUpdateRoleRequest updateRoleRequest) {
        try {
            UserResponse userResponse = userService.updateUserRole(username, updateRoleRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
