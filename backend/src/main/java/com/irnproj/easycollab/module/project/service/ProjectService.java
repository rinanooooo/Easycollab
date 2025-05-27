package com.irnproj.easycollab.module.project.service;

import com.irnproj.easycollab.module.project.dto.ProjectRequestDto;
import com.irnproj.easycollab.module.project.dto.ProjectResponseDto;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.project.repository.ProjectRepository;
import com.irnproj.easycollab.module.team.entity.Team;
import com.irnproj.easycollab.module.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final TeamRepository teamRepository;

  @Transactional
  public ProjectResponseDto createProject(Long teamId, ProjectRequestDto request) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

    Project project = Project.builder()
        .name(request.getName())
        .description(request.getDescription())
        .team(team)
        .build();

    projectRepository.save(project);

    return new ProjectResponseDto(
        project.getId(),
        project.getName(),
        project.getDescription(),
        team.getId()
    );
  }

  @Transactional(readOnly = true)
  public List<ProjectResponseDto> getProjectsByTeamId(Long teamId) {
    return projectRepository.findByTeamId(teamId).stream()
        .map(project -> new ProjectResponseDto(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getTeam().getId()
        ))
        .toList();
  }
}