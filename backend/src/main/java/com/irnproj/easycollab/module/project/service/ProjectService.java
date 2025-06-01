package com.irnproj.easycollab.module.project.service;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import com.irnproj.easycollab.module.notification.service.NotificationService;
import com.irnproj.easycollab.module.project.dto.ProjectRequestDto;
import com.irnproj.easycollab.module.project.dto.ProjectResponseDto;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.project.repository.ProjectRepository;
import com.irnproj.easycollab.module.team.entity.Team;
import com.irnproj.easycollab.module.team.entity.TeamMember;
import com.irnproj.easycollab.module.team.repository.TeamMemberRepository;
import com.irnproj.easycollab.module.team.repository.TeamRepository;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final TeamRepository teamRepository;
  private final ComCodeRepository comCodeRepository;
  private final UserRepository userRepository;
  private final TeamMemberRepository teamMemberRepository;
  private final NotificationService notificationService;

  // 프로젝트 생성
  public ProjectResponseDto createProject(Long teamId, ProjectRequestDto requestDto, Long userId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

    ComCode status = comCodeRepository.findByCodeTypeAndCode("PROJECT_STATUS", requestDto.getStatusCode())
        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 상태 코드입니다."));

    Project project = Project.builder()
        .name(requestDto.getName())
        .description(requestDto.getDescription())
        .startDate(requestDto.getStartDate())
        .endDate(requestDto.getEndDate())
        .status(status)
        .team(team)
        .build();

    projectRepository.save(project);

    // 프로젝트 생성 후 알림 전송
    List<TeamMember> members = teamMemberRepository.findByTeam(project.getTeam());

    for (TeamMember member : members) {
      if (!member.getUser().getId().equals(userId)) { // 생성자 제외
        notificationService.createNotification(
            member.getUser().getId(),
            "신규 프로젝트가 생성됐습니다. '" + project.getName() + "'",
            "/projects/" + project.getId()
        );
      }
    }

    return ProjectResponseDto.builder()
        .id(project.getId())
        .name(project.getName())
        .description(project.getDescription())
        .teamName(team.getName())
        .isMyProject(true)
        .myRole("생성자") // 나중에 로직에 따라 변경 가능
        .createdAt(project.getCreatedAt())
        .updatedAt(project.getUpdatedAt())
        .startDate(project.getStartDate())
        .endDate(project.getEndDate())
        .statusCode(status.getCode())
        .statusName(status.getName())
        .build();
  }


  // 전체 프로젝트 목록 조회
  public List<ProjectResponseDto> getAllProjects() {
    List<Project> projects = projectRepository.findAll();

    return projects.stream()
        .map(project -> ProjectResponseDto.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .teamName(project.getTeam().getName())
            .isMyProject(false)
            .myRole(null)
            .createdAt(project.getCreatedAt())
            .updatedAt(project.getUpdatedAt())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .statusCode(getCode(project.getStatus()))
            .statusName(getName(project.getStatus()))
            .build())
        .collect(Collectors.toList());
  }

  // 프로젝트명 검색
  public List<ProjectResponseDto> searchProjectsByName(String keyword) {
    List<Project> projects = projectRepository.findByNameContainingIgnoreCase(keyword);

    return projects.stream()
        .map(project -> ProjectResponseDto.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .teamName(project.getTeam().getName())
            .isMyProject(false)
            .myRole(null)
            .createdAt(project.getCreatedAt())
            .updatedAt(project.getUpdatedAt())
            .startDate(project.getStartDate())
            .endDate(project.getEndDate())
            .statusCode(project.getStatus() != null ? project.getStatus().getCode() : null)
            .statusName(project.getStatus() != null ? project.getStatus().getName() : null)
            .build())
        .collect(Collectors.toList());
  }

  // 단일 프로젝트 상세 조회 (누구나 조회 가능)
  public ProjectResponseDto getProjectById(Long projectId, Long userId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

    boolean isMyProject = false;
    String myRole = null;

    // 유저가 로그인한 경우 → 팀 멤버 여부 확인
    if (userId != null) {
      User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

      Optional<TeamMember> memberOpt = teamMemberRepository.findByTeamAndUser(project.getTeam(), user);
      if (memberOpt.isPresent()) {
        isMyProject = true;
        myRole = memberOpt.get().getRole().getName(); // ex: "팀장", "팀원"
      }
    }

    return ProjectResponseDto.builder()
        .id(project.getId())
        .name(project.getName())
        .description(project.getDescription())
        .teamName(project.getTeam().getName())
        .isMyProject(isMyProject)
        .myRole(myRole)
        .createdAt(project.getCreatedAt())
        .updatedAt(project.getUpdatedAt())
        .startDate(project.getStartDate())
        .endDate(project.getEndDate())
        .statusCode(project.getStatus() != null ? project.getStatus().getCode() : null)
        .statusName(project.getStatus() != null ? project.getStatus().getName() : null)
        .build();
  }

  // 내 프로젝트 목록 조회
  public List<ProjectResponseDto> getMyProjects(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

    List<TeamMember> memberships = teamMemberRepository.findByUser(user);

    return memberships.stream()
        .flatMap(tm -> tm.getTeam().getProjects().stream()
            .map(project -> ProjectResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .teamName(project.getTeam().getName())
                .isMyProject(true)
                .myRole(tm.getRole().getName())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .statusCode(project.getStatus() != null ? project.getStatus().getCode() : null)
                .statusName(project.getStatus() != null ? project.getStatus().getName() : null)
                .build()))
        .collect(Collectors.toList());
  }

  // 프로젝트 수정
  public ProjectResponseDto updateProject(Long projectId, ProjectRequestDto requestDto, Long userId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

    // 팀장만 수정 가능
    if (!project.getTeam().getOwner().getId().equals(userId)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "팀장만 수정할 수 있습니다.");
    }

    ComCode status = comCodeRepository.findByCodeTypeAndCode("PROJECT_STATUS", requestDto.getStatusCode())
        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 상태 코드입니다."));

    project.update(
        requestDto.getName(),
        requestDto.getDescription(),
        requestDto.getStartDate(),
        requestDto.getEndDate(),
        status
    );

    return ProjectResponseDto.builder()
        .id(project.getId())
        .name(project.getName())
        .description(project.getDescription())
        .teamName(project.getTeam().getName())
        .isMyProject(true)
        .myRole("팀장")
        .createdAt(project.getCreatedAt())
        .updatedAt(project.getUpdatedAt())
        .startDate(project.getStartDate())
        .endDate(project.getEndDate())
        .statusCode(status.getCode())
        .statusName(status.getName())
        .build();
  }

  // 프로젝트 삭제
  public void deleteProject(Long projectId, Long userId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

    // 팀장인지 확인
    if (!project.getTeam().getOwner().getId().equals(userId)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "팀장만 삭제할 수 있습니다.");
    }

    projectRepository.delete(project);
  }

  private String getCode(ComCode code) {
    return code != null ? code.getCode() : null;
  }

  private String getName(ComCode code) {
    return code != null ? code.getName() : null;
  }
}