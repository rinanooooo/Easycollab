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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

  private final IssueRepository issueRepository;
  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final ComCodeRepository comCodeRepository;

  // Entity → ResponseDto 변환
  private IssueResponseDto toResponseDto(Issue issue) {
    return new IssueResponseDto(
        issue.getId(),
        issue.getTitle(),
        issue.getContent(),
        issue.getStatus().getName(),
        issue.getProject().getId(),
        issue.getProject().getName(),
        issue.getReporter().getId(),
        issue.getReporter().getUsername()
    );
  }
  // 이슈 생성
  public IssueResponseDto createIssue(IssueRequestDto requestDto) {
    Project project = projectRepository.findById(requestDto.getProjectId())
        .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

    User reporter = userRepository.findById(requestDto.getReporterId())
        .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다."));

    ComCode status = comCodeRepository.findByCodeTypeAndCode("ISSUE_STATUS", requestDto.getStatusCode())
        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 상태 코드입니다."));

    Issue issue = Issue.builder()
        .title(requestDto.getTitle())
        .content(requestDto.getContent())
        .project(project)
        .reporter(reporter)
        .status(status)
        .build();

    Issue saved = issueRepository.save(issue);
    return toResponseDto(saved);
  }

  // 전체 이슈 조회
  public List<IssueResponseDto> getAllIssues() {
    return issueRepository.findAll().stream()
        .map(this::toResponseDto)
        .collect(Collectors.toList());
  }

  // 이슈 단건 조회
  public IssueResponseDto getIssueById(Long id) {
    Issue issue = issueRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 이슈를 찾을 수 없습니다."));
    return toResponseDto(issue);
  }

  //  이슈 수정
  @Transactional
  public IssueResponseDto updateIssue(Long id, IssueRequestDto requestDto) {
    Issue issue = issueRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("이슈를 찾을 수 없습니다."));

    Project project = projectRepository.findById(requestDto.getProjectId())
        .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다."));

    User reporter = userRepository.findById(requestDto.getReporterId())
        .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다."));

    ComCode status = comCodeRepository.findByCodeTypeAndCode("ISSUE_STATUS", requestDto.getStatusCode())
        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 상태 코드입니다."));

    issue.setTitle(requestDto.getTitle());
    issue.setContent(requestDto.getContent());
    issue.setStatus(status);
    issue.setProject(project);
    issue.setReporter(reporter);

    return toResponseDto(issue);
  }

  //이슈 삭제
  public void deleteIssue(Long id) {
    Issue issue = issueRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("이슈를 찾을 수 없습니다."));
    issueRepository.delete(issue);
  }

}