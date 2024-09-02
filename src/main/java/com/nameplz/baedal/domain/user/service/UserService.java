package com.nameplz.baedal.domain.user.service;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.dto.request.UserSignupRequestDto;
import com.nameplz.baedal.domain.user.dto.request.UserUpdateRequestDto;
import com.nameplz.baedal.domain.user.dto.request.UserUpdateRoleRequestDto;
import com.nameplz.baedal.domain.user.dto.response.UserSignupResponseDto;
import com.nameplz.baedal.domain.user.dto.response.UserUpdateResponseDto;
import com.nameplz.baedal.domain.user.mapper.UserMapper;
import com.nameplz.baedal.domain.user.repository.UserRepository;
import com.nameplz.baedal.global.common.exception.GlobalException;
import com.nameplz.baedal.global.common.response.ResultCase;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /**
     * 회원가입
     */
    // TODO : @Valid와 같은 요청 유효성 검증은 컨트롤러 단에서 하면 좋을 것 같습니다! 서비스 레벨에서는 비즈니스 로직에 집중하는 것이 좋을 것 같아요.
    // TODO : 기존에 unique 인 필드가 이미 있는지 검증하는 로직이 필요해보여요!
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

    /**
     * 회원정보 단일검색
     */
    public UserUpdateResponseDto getUserByUsername(String username) {
        User user = userRepository.findById(username)
            .orElseThrow(() -> new GlobalException(ResultCase.USER_NOT_FOUND));
        return userMapper.userToUserUpdateResponseDto(user);
    }

    /**
     * 회원정보 수정
     */
    // TODO : Get 요청인데 응답 DTO 이름은 Update를 사용하고 있어요.
    //제일 이상적인 케이스는 API 마다 요청/응답 DTO를 만드는 것이지만, 그만큼 관리해야할 객체가 늘어나서 중복될 수 있는 것은 재사용하는 것도 좋습니다.
    //다만 이름으로 유추할 수 없는 기능과 매칭되어 있어서, 조회를 유추할 수 있는 이름으로 변경하는 게 좋을 것 같아요.
    //그리고 현재는 동일한 필드를 응답한다고 해도, 추후 다른 시점에서 다른 방향으로 변경될 여지가 있다면 DTO를 분리하는 것이 좋습니다. 지금은 그래도 수정한 유저의 전체 정보를 반환하는 것이니 굳이 분리하진 않아도 될 것 같습니다.
    @Transactional
    public UserUpdateResponseDto updateUser(String username, UserUpdateRequestDto request) {
        User user = userRepository.findById(username)
            .orElseThrow(() -> new GlobalException(ResultCase.USER_NOT_FOUND));

        // 비밀번호 인코딩 처리 (엔티티 내에서 처리)
        if (request.password() != null && !request.password().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(request.password());
            // DTO의 비밀번호를 인코딩된 비밀번호로 직접 변경하는 것은 불가능하므로,
            // 엔티티의 업데이트 메서드에서 비밀번호를 처리하도록 함
            user.updatePassword(encodedPassword);
        }

        user.update(request); // 여기서는 비밀번호 인코딩이 완료된 DTO를 그대로 사용
        userRepository.save(user);

        return userMapper.userToUserUpdateResponseDto(user);
    }

    /**
     * 회원탈퇴
     */
    // TODO : SoftDelete로 해야해서 실제로 삭제하기보다 BaseEntity의 삭제 메서드를 이용하거나, deleted_at 필드에 현재 시간 넣어주면 될 것 같습니다! 그리고 리포지토리를 이용하기보다 user.delete()로 유저 엔티티가 책임을 다할 수 있도록 유저 엔티티 내부에 넣어주는 것이 좋아보입니다!
    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findById(username)
            .orElseThrow(() -> new GlobalException(ResultCase.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    /**
     * 회원정보 전체검색
     */
    // TODO : java 17에서는 .collect(Collectors.toList()); 부분을 toList();로 줄여서 사용가능합니다!
    // TODO : 컨트롤러 단에서 Pageable 을 받아서 바로 처리할 수 있습니다!
    // import org.springframework.data.domain.Pageable; 입니다!
    public List<UserUpdateResponseDto> getAllUsers(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return userRepository.findAll(pageRequest).stream()
            .map(userMapper::userToUserUpdateResponseDto)
            .collect(Collectors.toList());
    }

    /**
     * 회원 권한 변경
     */
    @Transactional
    public UserUpdateResponseDto updateUserRole(String username, UserUpdateRoleRequestDto request) {
        User user = userRepository.findById(username)
            .orElseThrow(() -> new GlobalException(ResultCase.USER_NOT_FOUND));
        // TODO : 수정필요
        //user.setRole(User.RoleType.valueOf(request.getRole().toUpperCase()));

        userRepository.save(user);
        return userMapper.userToUserUpdateResponseDto(user);
    }
}