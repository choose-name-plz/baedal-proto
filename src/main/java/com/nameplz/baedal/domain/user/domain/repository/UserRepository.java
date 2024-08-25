package com.nameplz.baedal.domain.user.domain.repository;

import com.nameplz.baedal.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
