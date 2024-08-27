package com.nameplz.baedal.domain.user.service;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.dto.request.UserLoginRequestDto;
import com.nameplz.baedal.domain.user.dto.request.UserSignupRequestDto;
import com.nameplz.baedal.domain.user.dto.request.UserUpdateRequestDto;
import com.nameplz.baedal.domain.user.dto.request.UserUpdateRoleRequestDto;
import com.nameplz.baedal.domain.user.dto.response.UserLoginResponseDto;
import com.nameplz.baedal.domain.user.dto.response.UserSignupResponseDto;
import com.nameplz.baedal.domain.user.dto.response.UserUpdateResponseDto;
import com.nameplz.baedal.domain.user.mapper.UserMapper;
import com.nameplz.baedal.domain.user.repository.UserRepository;
import com.nameplz.baedal.global.common.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserSignupResponseDto signup(@Valid UserSignupRequestDto request) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.password());

        // 유저 entity 생성 (유저의 이름, 패스워드를 전달 받아 회원가입을 진행)
        User user = User.create(
                request.username(),
                encodedPassword
        );

        // 회원가입 완료
        userRepository.save(user);

        return new UserSignupResponseDto(user.getUsername());
    }

    public UserLoginResponseDto login(UserLoginRequestDto request) {
        // 로그인
        User user = userRepository.findById(request.username())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // 로그인 유저 정보를 기반으로 authentication 객체를 만들어 SecurityContextHolder 에 넣음
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new UserLoginResponseDto(user.getUsername());
    }

    public UserUpdateResponseDto getUserByUsername(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userToUserUpdateResponseDto(user);
    }

    @Transactional
    public UserUpdateResponseDto updateUser(String username, UserUpdateRequestDto request) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.updateNickname(request.nickname());
        user.updateEmail(request.email());
        if (request.password() != null && !request.password().isEmpty()) {
            user.updateEncodedPassword(passwordEncoder.encode(request.password()));
        }
        user.updateIsPublic(request.isPublic());

        userRepository.save(user);

        return userMapper.userToUserUpdateResponseDto(user);
    }

    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    // TODO : ava 17에서는 .collect(Collectors.toList()); 부분을 toList();로 줄여서 사용가능합니다!
    public List<UserUpdateResponseDto> getAllUsers(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return userRepository.findAll(pageRequest).stream()
                .map(userMapper::userToUserUpdateResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserUpdateResponseDto updateUserRole(String username, UserUpdateRoleRequestDto request) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // TODO : 수정필요
        //user.setRole(User.RoleType.valueOf(request.getRole().toUpperCase()));

        userRepository.save(user);
        return userMapper.userToUserUpdateResponseDto(user);
    }
}