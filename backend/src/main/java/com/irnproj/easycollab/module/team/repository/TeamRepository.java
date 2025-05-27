package com.irnproj.easycollab.module.team.repository;

import com.irnproj.easycollab.module.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
//  List<Team> findByOwnerId(Long ownerId);
}
