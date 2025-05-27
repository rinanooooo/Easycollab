package com.irnproj.easycollab.module.user.repository;

import com.irnproj.easycollab.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUuid(String uuid);
  Optional<User> findByLoginId(String loginId);
  boolean existsByEmail(String email);
  boolean existsByNickname(String nickname);
}