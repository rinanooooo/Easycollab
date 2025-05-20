package com.irnproj.easycollab.module.user.repository;

import com.irnproj.easycollab.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByNickname(String username);
  Optional<User> findByEmail(String username);
}