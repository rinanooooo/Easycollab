package com.irnproj.easycollab.module.issue.repository;

import com.irnproj.easycollab.module.issue.entity.Issue;
import com.irnproj.easycollab.module.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
  // 특정 프로젝트의 이슈 목록 조회
  List<Issue> findByProject(Project project);
}