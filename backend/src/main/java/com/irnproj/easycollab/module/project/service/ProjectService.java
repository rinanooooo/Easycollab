package com.irnproj.easycollab.module.project.service;

import com.irnproj.easycollab.module.project.dto.ProjectRequestDto;
import com.irnproj.easycollab.module.project.dto.ProjectResponseDto;
import com.irnproj.easycollab.module.project.dto.ProjectTreeResponseDto;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.project.repository.ProjectRepository;
import com.irnproj.easycollab.module.team.entity.Team;
import com.irnproj.easycollab.module.team.repository.TeamRepository;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import com.irnproj.easycollab.common.exception.CustomException;
import com.irnproj.easycollab.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;

  /**
   * 전체 프로젝트 목록 조회
   */
  public List<ProjectResponseDto> getAllProjects() {
    return projectRepository.findAll().stream()
        .map(ProjectResponseDto::from)
        .collect(Collectors.toList());
  }

  /**
   * 프로젝트 검색
   */
  public List<ProjectResponseDto> searchProjectsByName(String name) {
    return projectRepository.findByNameContaining(name).stream()
        .map(ProjectResponseDto::from)
        .collect(Collectors.toList());
  }

  /**
   * 단일 프로젝트 조회
   * @throws CustomException PROJECT_NOT_FOUND
   */
  public ProjectResponseDto getProjectById(Long projectId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));
    return ProjectResponseDto.from(project);
  }

  /**
   * 내 프로젝트 조회
   */
  public List<ProjectResponseDto> getProjectsByUserId(Long userId) {
    return projectRepository.findByMemberUserId(userId).stream()
        .map(ProjectResponseDto::from)
        .collect(Collectors.toList());
  }

  /**
   * 프로젝트-이슈 트리 조회
   */
  @Transactional(readOnly = true)
  public List<ProjectTreeResponseDto> getProjectIssueTree() {
    List<Project> projects = projectRepository.findAllWithIssues();
    return projects.stream()
        .map(ProjectTreeResponseDto::from)
        .collect(Collectors.toList());
  }


  /**
   * 프로젝트 생성
   * @throws CustomException TEAM_NOT_FOUND, USER_NOT_FOUND
   */
  @Transactional
  public ProjectResponseDto createProject(ProjectRequestDto requestDto) {
    Team team = teamRepository.findById(requestDto.getTeamId())
        .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

    User owner = userRepository.findById(requestDto.getOwnerId())
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    Project project = Project.builder()
        .name(requestDto.getName())
        .description(requestDto.getDescription())
        .team(team)
        .owner(owner)
        .status(requestDto.getStatus())
        .build();

    Project saved = projectRepository.save(project);
    return ProjectResponseDto.from(saved);
  }

  /**
   * 프로젝트 수정
   * @throws CustomException PROJECT_NOT_FOUND
   */
  @Transactional
  public ProjectResponseDto updateProject(Long projectId, ProjectRequestDto requestDto) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

    project.update(
        requestDto.getName(),
        requestDto.getDescription(),
        requestDto.getStatus()
    );
    return ProjectResponseDto.from(project);
  }

  /**
   * 프로젝트 삭제
   * @throws CustomException PROJECT_NOT_FOUND
   */
  @Transactional
  public void deleteProject(Long projectId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));
    projectRepository.delete(project);
  }
}