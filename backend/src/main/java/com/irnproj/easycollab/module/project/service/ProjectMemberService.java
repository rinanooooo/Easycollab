package com.irnproj.easycollab.module.project.service;

import com.irnproj.easycollab.common.exception.CustomException;
import com.irnproj.easycollab.common.exception.ErrorCode;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import com.irnproj.easycollab.module.project.dto.ProjectMemberRequestDto;
import com.irnproj.easycollab.module.project.dto.ProjectMemberResponseDto;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.project.entity.ProjectMember;
import com.irnproj.easycollab.module.project.repository.ProjectMemberRepository;
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
@Transactional
public class ProjectMemberService {

  private final ProjectRepository projectRepository;
  private final ProjectMemberRepository projectMemberRepository;
  private final UserRepository userRepository;
  private final ComCodeRepository comCodeRepository;

  /**
   * 프로젝트에 멤버 추가
   */
  public ProjectMemberResponseDto addProjectMember(Long projectId, ProjectMemberRequestDto dto) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

    User user = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    ComCode role = comCodeRepository.findById(dto.getRoleCodeId())
        .orElseThrow(() -> new CustomException(ErrorCode.COM_CODE_NOT_FOUND));

    // 중복 체크
    if (projectMemberRepository.existsByProjectAndUser(project, user)) {
      throw new CustomException(ErrorCode.DUPLICATE_PROJECT_MEMBER);
    }

    ProjectMember member = ProjectMember.builder()
        .project(project)
        .user(user)
        .roleCode(role)
        .build();

    projectMemberRepository.save(member);
    return ProjectMemberResponseDto.from(member);
  }

  /**
   * 프로젝트 멤버 목록 조회
   */
  @Transactional(readOnly = true)
  public List<ProjectMemberResponseDto> getProjectMembers(Long projectId) {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_NOT_FOUND));

    return projectMemberRepository.findByProject(project).stream()
        .map(ProjectMemberResponseDto::from)
        .collect(Collectors.toList());
  }

  /**
   * 프로젝트 멤버 역할 변경
   */
  public ProjectMemberResponseDto updateMemberRole(Long projectId, Long memberId, Long roleCodeId) {
    ProjectMember member = projectMemberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));

    // 프로젝트 소속 확인
    if (!member.getProject().getId().equals(projectId)) {
      throw new CustomException(ErrorCode.PROJECT_MEMBER_MISMATCH);
    }

    ComCode newRole = comCodeRepository.findById(roleCodeId)
        .orElseThrow(() -> new CustomException(ErrorCode.COM_CODE_NOT_FOUND));

    member.updateRole(newRole);
    return ProjectMemberResponseDto.from(member);
  }

  /**
   * 프로젝트 멤버 삭제
   */
  public void removeProjectMember(Long projectId, Long memberId) {
    ProjectMember member = projectMemberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.PROJECT_MEMBER_NOT_FOUND));

    if (!member.getProject().getId().equals(projectId)) {
      throw new CustomException(ErrorCode.PROJECT_MEMBER_MISMATCH);
    }

    projectMemberRepository.delete(member);
  }
}