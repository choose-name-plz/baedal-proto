package com.nameplz.baedal.domain.user.repository;

import com.nameplz.baedal.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
}
