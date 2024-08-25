package com.nameplz.baedal.domain.user.domain.repository;

import com.nameplz.baedal.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUsername(String username);
}
