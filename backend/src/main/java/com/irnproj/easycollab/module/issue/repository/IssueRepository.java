package com.irnproj.easycollab.module.issue.repository;

import com.irnproj.easycollab.module.issue.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
  // 프로젝트 ID 기준 이슈 전체 조회
  List<Issue> findAllByProjectId(Long projectId);
  //  List<Issue> findByProjectId(Long projectId);
  // 필요 시: 특정 상태의 이슈만 조회 등 확장 가능
  // List<Issue> findAllByStatus_Code(String code);
}
