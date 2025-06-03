package com.irnproj.easycollab.module.issue.service;

import com.irnproj.easycollab.common.exception.CustomException;
import com.irnproj.easycollab.common.exception.ErrorCode;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

  private final IssueRepository issueRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final ComCodeRepository comCodeRepository;

  /**
   * 이슈 생성
   */
  @Transactional
  public IssueResponseDto createIssue(Long projectId, IssueRequestDto requestDto) {
    Project project = findProjectById(projectId);
    User assignee = findUserById(requestDto.getAssigneeId());

    ComCode status = findComCode("ISSUE_STATUS", requestDto.getStatus());

    Issue issue = Issue.builder()
        .title(requestDto.getTitle())
        .content(requestDto.getContent())
        .status(status)
        .assignee(assignee)
        .project(project)
        .build();

    issueRepository.save(issue);
    return IssueResponseDto.of(issue);
  }

  /**
   * 이슈 단건 조회
   */
  @Transactional(readOnly = true)
  public IssueResponseDto getIssue(Long issueId) {
    Issue issue = findIssueById(issueId);
    return IssueResponseDto.of(issue);
  }

  /**
   * 이슈 수정
   */
  @Transactional(readOnly = true)
  public List<IssueResponseDto> getIssuesByProject(Long projectId) {
    return issueRepository.findByProjectId(projectId).stream()
        .map(IssueResponseDto::of)
        .collect(Collectors.toList());
  }


  /**
   * 이슈 삭제
   */
  @Transactional
  public IssueResponseDto updateIssue(Long issueId, IssueRequestDto requestDto) {
    Issue issue = findIssueById(issueId);

    ComCode status = findComCode("ISSUE_STATUS", requestDto.getStatus());
    User assignee = findUserById(requestDto.getAssigneeId());

    issue.update(requestDto.getTitle(), requestDto.getContent(), status, assignee,
        requestDto.getStartDate(), requestDto.getEndDate(), requestDto.isPinned());
    return IssueResponseDto.of(issue);
  }


  /**
   * 프로젝트 내 이슈 목록 조회
   */
  @Transactional
  public void deleteIssue(Long issueId) {
    Issue issue = findIssueById(issueId);
    issueRepository.delete(issue);
  }

  // === 유틸성 메서드 ===

  private Project findProjectById(Long id) {
    return projectRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));
  }

  private Issue findIssueById(Long id) {
    return issueRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ISSUE_NOT_FOUND));
  }

  private User findUserById(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

  private ComCode findComCode(String codeType, String name) {
    return comCodeRepository.findByCodeTypeAndName(codeType, name)
        .orElseThrow(() -> new CustomException(ErrorCode.COM_CODE_NOT_FOUND));
  }
}