package com.irnproj.easycollab.module.team.repository;

import com.irnproj.easycollab.module.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

  @Query("SELECT t FROM Team t LEFT JOIN FETCH t.teamMembers")
  List<Team> findAllWithMembers();
}