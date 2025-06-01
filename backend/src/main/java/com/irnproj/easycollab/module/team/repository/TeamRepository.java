package com.irnproj.easycollab.module.team.repository;

import com.irnproj.easycollab.module.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
  List<Team> findByNameContainingIgnoreCase(String name);
}