package com.nameplz.baedal.domain.user.domain.service;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.domain.dto.request.UserLoginRequest;
import com.nameplz.baedal.domain.user.domain.dto.request.UserUpdateRequest;
import com.nameplz.baedal.domain.user.domain.dto.request.UserUpdateRoleRequest;
import com.nameplz.baedal.domain.user.domain.dto.response.UserLoginResponse;
import com.nameplz.baedal.domain.user.domain.dto.request.UserSignupRequest;
import com.nameplz.baedal.domain.user.domain.dto.response.UserResponse;
import com.nameplz.baedal.domain.user.domain.dto.response.UserSignupResponse;
import com.nameplz.baedal.domain.user.domain.repository.UserRepository;
import com.nameplz.baedal.global.error.UserNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public UserSignupResponse signup(@Valid UserSignupRequest request) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 유저 entity 생성 (유저의 이름, 패스워드를 전달 받아 회원가입을 진행)
        User user = User.create(
                request.getUsername(),
                encodedPassword
        );

        // 회원가입 완료
        userRepository.save(user);

        return new UserSignupResponse(user.getUsername());
    }

    public UserLoginResponse login(UserLoginRequest request) {
        // 로그인
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // 로그인 유저 정보를 기반으로 authentication 객체를 만들어 SecurityContextHolder 에 넣음
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new UserLoginResponse(user.getUsername());
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(String username, UserUpdateRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setIsPublic(request.getIsPublic());

        userRepository.save(user);

        return new UserResponse(user);
    }

    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public List<UserResponse> getAllUsers(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return userRepository.findAll(pageRequest).stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUserRole(String username, UserUpdateRoleRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        //수정필요
        //user.setRole(User.RoleType.valueOf(request.getRole().toUpperCase()));

        userRepository.save(user);
        return new UserResponse(user);
    }
}