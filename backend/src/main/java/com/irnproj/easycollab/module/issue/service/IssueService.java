package com.irnproj.easycollab.module.issue.service;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import com.irnproj.easycollab.module.issue.dto.IssueRequestDto;
import com.irnproj.easycollab.module.issue.dto.IssueResponseDto;
import com.irnproj.easycollab.module.issue.entity.Issue;
import com.irnproj.easycollab.module.issue.repository.IssueRepository;
import com.irnproj.easycollab.module.notification.service.NotificationService;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.project.repository.ProjectRepository;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

  private final IssueRepository issueRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final ComCodeRepository comCodeRepository;
  private final NotificationService notificationService;

  // 프로젝트 생성
  public IssueResponseDto createIssue(IssueRequestDto dto, Long userId) {
    User reporter = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("작성자 정보를 찾을 수 없습니다."));

    Project project = projectRepository.findById(dto.getProjectId())
        .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

    ComCode status = comCodeRepository.findByCodeTypeAndCode("ISSUE_STATUS", dto.getStatusCode())
        .orElseThrow(() -> new IllegalArgumentException("상태 코드를 찾을 수 없습니다."));

    User assignee = null;
    if (dto.getAssigneeId() != null) {
      assignee = userRepository.findById(dto.getAssigneeId())
          .orElseThrow(() -> new IllegalArgumentException("담당자 정보를 찾을 수 없습니다."));
    }

    Issue issue = Issue.builder()
        .title(dto.getTitle())
        .content(dto.getContent())
        .project(project)
        .status(status)
        .reporter(reporter)
        .assignee(assignee)
        .startDate(dto.getStartDate())
        .endDate(dto.getEndDate())
        .build();

    Issue saved = issueRepository.save(issue);

    // 알림: 담당자에게 알림
    if (assignee != null && !assignee.getId().equals(userId)) {
      notificationService.createNotification(
          assignee.getId(),
          "'" + project.getName() + "'의 새로운 이슈가 생성되었습니다. '" + issue.getTitle() + "'",
          "/projects/" + project.getId()
      );
    }

    return IssueResponseDto.builder()
        .id(saved.getId())
        .title(saved.getTitle())
        .content(saved.getContent())
        .statusCode(status.getCode())
        .statusName(status.getName())
        .reporterName(reporter.getNickname())
        .assigneeName(assignee != null ? assignee.getNickname() : null)
        .projectName(project.getName())
        .startDate(saved.getStartDate())
        .endDate(saved.getEndDate())
        .createdAt(saved.getCreatedAt())
        .updatedAt(saved.getUpdatedAt())
        .build();
  }


  // 특정 프로젝트의 이슈 목록 조회
  public List<IssueResponseDto> getIssuesByProject(Long projectId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

    List<Issue> issues = issueRepository.findByProject(project);

    return issues.stream()
        .map(issue -> IssueResponseDto.builder()
            .id(issue.getId())
            .title(issue.getTitle())
            .content(issue.getContent())
            .statusCode(issue.getStatus() != null ? issue.getStatus().getCode() : null)
            .statusName(issue.getStatus() != null ? issue.getStatus().getName() : null)
            .reporterName(issue.getReporter().getNickname())
            .assigneeName(issue.getAssignee() != null ? issue.getAssignee().getNickname() : null)
            .projectName(project.getName())
            .createdAt(issue.getCreatedAt())
            .updatedAt(issue.getUpdatedAt())
            .build())
        .collect(Collectors.toList());
  }

  // 단일 이슈 상세 조회
  public IssueResponseDto getIssueById(Long issueId) {
    Issue issue = issueRepository.findById(issueId)
        .orElseThrow(() -> new IllegalArgumentException("이슈를 찾을 수 없습니다."));

    return IssueResponseDto.builder()
        .id(issue.getId())
        .title(issue.getTitle())
        .content(issue.getContent())
        .statusCode(issue.getStatus() != null ? issue.getStatus().getCode() : null)
        .statusName(issue.getStatus() != null ? issue.getStatus().getName() : null)
        .reporterName(issue.getReporter().getNickname())
        .assigneeName(issue.getAssignee() != null ? issue.getAssignee().getNickname() : null)
        .projectName(issue.getProject().getName())
        .startDate(issue.getStartDate())
        .endDate(issue.getEndDate())
        .createdAt(issue.getCreatedAt())
        .updatedAt(issue.getUpdatedAt())
        .build();
  }

  // 이슈 수정
  @Transactional
  public IssueResponseDto updateIssue(Long id, IssueRequestDto requestDto) {
    Issue issue = issueRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("이슈를 찾을 수 없습니다."));

    Project project = projectRepository.findById(requestDto.getProjectId())
        .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

    User reporter = issue.getReporter();

    ComCode status = comCodeRepository.findByCodeTypeAndCode("ISSUE_STATUS", requestDto.getStatusCode())
        .orElseThrow(() -> new IllegalArgumentException("상태 코드를 찾을 수 없습니다."));

    User assignee = null;
    if (requestDto.getAssigneeId() != null) {
      assignee = userRepository.findById(requestDto.getAssigneeId())
          .orElseThrow(() -> new IllegalArgumentException("담당자를 찾을 수 없습니다."));
    }

    issue.update(
        requestDto.getTitle(),
        requestDto.getContent(),
        project,
        status,
        assignee,
        requestDto.getStartDate(),
        requestDto.getEndDate()
    );

    return IssueResponseDto.builder()
        .id(issue.getId())
        .title(issue.getTitle())
        .content(issue.getContent())
        .statusCode(status.getCode())
        .statusName(status.getName())
        .reporterName(reporter.getNickname())
        .assigneeName(assignee != null ? assignee.getNickname() : null)
        .projectName(project.getName())
        .startDate(issue.getStartDate())
        .endDate(issue.getEndDate())
        .createdAt(issue.getCreatedAt())
        .updatedAt(issue.getUpdatedAt())
        .build();
  }

  // 이슈 삭제
  @Transactional
  public void deleteIssue(Long id) {
    Issue issue = issueRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("이슈를 찾을 수 없습니다."));
    issueRepository.delete(issue);
  }
}