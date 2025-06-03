package com.irnproj.easycollab.module.issue.repository;

import com.irnproj.easycollab.module.issue.entity.Issue;
import com.irnproj.easycollab.module.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
  List<Issue> findByProjectId(Long projectId);
  // 특정 프로젝트의 이슈 목록 조회
//  List<Issue> findByProject(Project project);
//  List<Issue> findByReporterIdOrAssigneeId(Long reporterId, Long assigneeId);
//  @Query("SELECT i FROM Issue i JOIN FETCH i.team WHERE i.project.id = :projectId")
//  List<Issue> findByProjectIdWithTeam(@Param("projectId") Long projectId);
}