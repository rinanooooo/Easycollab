package com.irnproj.easycollab.module.project.service;

import com.irnproj.easycollab.module.project.dto.ProjectRequestDto;
import com.irnproj.easycollab.module.project.dto.ProjectResponseDto;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.project.repository.ProjectRepository;
import com.irnproj.easycollab.module.team.entity.Team;
import com.irnproj.easycollab.module.team.repository.TeamMemberRepository;
import com.irnproj.easycollab.module.team.repository.TeamRepository;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final TeamRepository teamRepository;
  private final TeamMemberRepository teamMemberRepository;
  private final UserRepository userRepository;

  // 프로젝트 생성
  @Transactional
  public ProjectResponseDto createProject(Long teamId, ProjectRequestDto requestDto, Long userId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new EntityNotFoundException("해당 팀이 존재하지 않습니다."));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("유저 정보를 찾을 수 없습니다."));

    Project project = Project.builder()
        .team(team)
        .name(requestDto.getName())
        .description(requestDto.getDescription())
        .createdBy(user)
        .build();

    Project saved = projectRepository.save(project);

    return new ProjectResponseDto(
        saved.getId(),
        saved.getName(),
        saved.getDescription(),
        project.getCreatedBy().getNickname(),
        saved.getTeam().getId()
    );
  }

  // 전체 프로젝트 조회
  @Transactional(readOnly = true)
  public List<ProjectResponseDto> getAllProjects() {
    return projectRepository.findAll().stream()
        .map(project -> new ProjectResponseDto(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getCreatedBy().getNickname(),
            project.getTeam().getId()
        ))
        .toList();
  }

  // 팀 프로젝트 조회
  @Transactional(readOnly = true)
  public List<ProjectResponseDto> getProjectsByTeam(Long teamId) {
    return projectRepository.findAllByTeamId(teamId).stream()
        .map(project -> new ProjectResponseDto(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getCreatedBy().getNickname(),
            project.getTeam().getId()
        ))
        .toList();
  }

  // 사용자 프로젝트 조회
  @Transactional(readOnly = true)
  public List<ProjectResponseDto> getMyProjects(Long userId) {
    List<Long> teamIds = teamMemberRepository.findAllByUserId(userId).stream()
        .map(tm -> tm.getTeam().getId())
        .toList();

    return projectRepository.findAllByTeamIdIn(teamIds).stream()
        .map(project -> new ProjectResponseDto(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getCreatedBy().getNickname(),
            project.getTeam().getId()
        ))
        .toList();
  }
}