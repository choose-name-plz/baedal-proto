package com.nameplz.baedal.global.common.security;

import com.nameplz.baedal.domain.user.domain.User;
import com.nameplz.baedal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j(topic = "UserDetailsServiceImpl 실행")
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

        log.info("조회한 user " + user.toString());
        return new UserDetailsImpl(user);
    }
}
