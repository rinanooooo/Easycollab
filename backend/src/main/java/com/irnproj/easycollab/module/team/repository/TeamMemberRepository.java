package com.irnproj.easycollab.module.team.repository;

import com.irnproj.easycollab.module.team.entity.Team;
import com.irnproj.easycollab.module.team.entity.TeamMember;
import com.irnproj.easycollab.module.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    @EntityGraph(attributePaths = {"team", "team.teamMembers", "team.owner", "role"})
    List<TeamMember> findByUser(User user);
    Optional<TeamMember> findByTeamAndUser(Team team, User user);
    List<TeamMember> findByTeam(Team team);

}