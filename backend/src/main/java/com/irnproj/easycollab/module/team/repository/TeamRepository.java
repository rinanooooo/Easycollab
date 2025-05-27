package com.irnproj.easycollab.module.team.repository;

import com.irnproj.easycollab.module.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
  Optional<Team> findByName(String name);
}