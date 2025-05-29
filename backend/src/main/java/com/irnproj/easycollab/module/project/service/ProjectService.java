package com.irnproj.easycollab.module.project.service;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import com.irnproj.easycollab.module.project.dto.ProjectRequestDto;
import com.irnproj.easycollab.module.project.dto.ProjectResponseDto;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.project.repository.ProjectRepository;
import com.irnproj.easycollab.module.team.entity.Team;
import com.irnproj.easycollab.module.team.repository.TeamMemberRepository;
import com.irnproj.easycollab.module.team.repository.TeamRepository;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import com.irnproj.easycollab.security.UserPrincipal;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;
  private final TeamMemberRepository teamMemberRepository;
  private final ComCodeRepository comCodeRepository;

  // 프로젝트 생성
  @Transactional
  public ProjectResponseDto createProject(ProjectRequestDto requestDto, UserPrincipal userPrincipal) {
    Team team = teamRepository.findById(requestDto.getTeamId())
        .orElseThrow(() -> new EntityNotFoundException("해당 팀이 존재하지 않았습니다."));

    User user = userRepository.findById(userPrincipal.getId())
        .orElseThrow(() -> new EntityNotFoundException("유저 정보를 찾을 수 없습니다."));

    // 상태 코드 설정 (중요)
    ComCode statusCode = comCodeRepository.findByCodeTypeAndCode("ISSUE_STATUS", requestDto.getStatusCode())
        .orElseThrow(() -> new IllegalArgumentException("상태 코드 없음"));

    // 프로젝트 생성 및 저장
    Project project = Project.builder()
        .team(team)
        .name(requestDto.getName())
        .description(requestDto.getDescription())
        .createdBy(user)
        .status(statusCode)
        .startDate(requestDto.getStartDate())
        .endDate(requestDto.getEndDate())
        .build();

    Project saved = projectRepository.save(project);

    // 응답 DTO 반환
    return ProjectResponseDto.fromEntity(saved);
  }

  // 전체 프로젝트 조회
  @Transactional(readOnly = true)
  public List<ProjectResponseDto> getAllProjects() {
    return projectRepository.findAll().stream()
        .map(ProjectResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 팀 프로젝트 조회
  @Transactional(readOnly = true)
  public List<ProjectResponseDto> getProjectsByTeam(Long teamId) {
    return projectRepository.findAllByTeamId(teamId).stream()
        .map(ProjectResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 사용자 프로젝트 조회
  @Transactional(readOnly = true)
  public List<ProjectResponseDto> getMyProjects(Long userId) {
    List<Long> teamIds = teamMemberRepository.findAllByUserId(userId).stream()
        .map(tm -> tm.getTeam().getId())
        .toList();

    return projectRepository.findAllByTeamIdIn(teamIds).stream()
        .map(ProjectResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 단건 조회
  @Transactional(readOnly = true)
  public ProjectResponseDto getProjectById(Long id) {
    Project project = projectRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));
    return ProjectResponseDto.fromEntity(project);
  }
}