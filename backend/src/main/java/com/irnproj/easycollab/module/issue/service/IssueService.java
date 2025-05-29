package com.irnproj.easycollab.module.issue.service;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import com.irnproj.easycollab.module.issue.dto.IssueRequestDto;
import com.irnproj.easycollab.module.issue.dto.IssueResponseDto;
import com.irnproj.easycollab.module.issue.entity.Issue;
import com.irnproj.easycollab.module.issue.repository.IssueRepository;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.project.repository.ProjectRepository;
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
public class IssueService {

  private final IssueRepository issueRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final ComCodeRepository comCodeRepository;

  // 이슈 생성
  @Transactional
  public IssueResponseDto createIssue(IssueRequestDto requestDto, UserPrincipal userPrincipal) {
    Project project = getProject(requestDto.getProjectId());
    User reporter = getUser(requestDto.getReporterId());

    // assignee는 선택값
    User assignee = null;
    if (requestDto.getAssigneeId() != null) {
      assignee = getUser(requestDto.getAssigneeId());
    }

    ComCode status = getStatusCode(requestDto.getStatusCode());

    Issue issue = Issue.builder()
        .title(requestDto.getTitle())
        .content(requestDto.getContent())
        .project(project)
        .reporter(reporter)
        .assignee(assignee)
        .status(status)
        .build();

    return IssueResponseDto.fromEntity(issueRepository.save(issue));
  }

  // 전체 이슈 조회
  @Transactional(readOnly = true)
  public List<IssueResponseDto> getAllIssues() {
    return issueRepository.findAll().stream()
        .map(IssueResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 프로젝트별 이슈 조회
  @Transactional(readOnly = true)
  public List<IssueResponseDto> getIssuesByProject(Long projectId) {
    return issueRepository.findAllByProjectId(projectId).stream()
        .map(IssueResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 단일 이슈 조회
  @Transactional(readOnly = true)
  public IssueResponseDto getIssueById(Long id) {
    Issue issue = issueRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("이슈를 찾을 수 없습니다."));
    return IssueResponseDto.fromEntity(issue);
  }

  // 이슈 수정
  @Transactional
  public IssueResponseDto updateIssue(Long id, IssueRequestDto requestDto) {
    Issue issue = issueRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("이슈를 찾을 수 없습니다."));

    issue.setTitle(requestDto.getTitle());
    issue.setContent(requestDto.getContent());
    issue.setStatus(getStatusCode(requestDto.getStatusCode()));
    issue.setProject(getProject(requestDto.getProjectId()));
    issue.setReporter(getUser(requestDto.getReporterId()));

    if (requestDto.getAssigneeId() != null) {
      issue.setAssignee(getUser(requestDto.getAssigneeId()));
    } else {
      issue.setAssignee(null); // assignee 해제
    }

    return IssueResponseDto.fromEntity(issue);
  }

  // 이슈 삭제
  @Transactional
  public void deleteIssue(Long id) {
    Issue issue = issueRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("이슈를 찾을 수 없습니다."));
    issueRepository.delete(issue);
  }

  // ====== 내부 메서드 ======
  private Project getProject(Long projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new EntityNotFoundException("프로젝트를 찾을 수 없습니다."));
  }

  private User getUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));
  }

  private ComCode getStatusCode(String statusCode) {
    return comCodeRepository.findByCodeTypeAndCode("ISSUE_STATUS", statusCode)
        .orElseThrow(() -> new IllegalArgumentException("상태 코드가 유효하지 않습니다."));
  }
}