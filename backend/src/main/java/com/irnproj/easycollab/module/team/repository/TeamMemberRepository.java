package com.irnproj.easycollab.module.team.repository;

import com.irnproj.easycollab.module.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findByTeamId(Long teamId);
    List<TeamMember> findByUserId(Long userId);
    boolean existsByTeamIdAndUserId(Long teamId, Long userId);
    List<TeamMember> findAllByUserId(Long userId);
}