package com.nameplz.baedal.domain.user.domain.service;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.domain.dto.request.UserLoginRequest;
import com.nameplz.baedal.domain.user.domain.dto.response.UserLoginResponse;
import com.nameplz.baedal.domain.user.domain.dto.UserLoginRequest;
import com.nameplz.baedal.domain.user.domain.dto.request.UserSignupRequest;
import com.nameplz.baedal.domain.user.domain.dto.response.UserSignupResponse;
import com.nameplz.baedal.domain.user.domain.repository.UserRepository;
import com.nameplz.baedal.global.error.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserSignupResponse signup(@Valid UserSignupRequest request) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 유저 entity 생성
        User user = User.create(
                request.getUsername(),
                request.getNickname(),
                request.getEmail(),
                encodedPassword,
                role,
                request.getAddress()
        );

        // 회원가입 완료
        userRepository.save(user);

        return new UserSignupResponse(user.getUsername());
    }

    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException);
    }
}