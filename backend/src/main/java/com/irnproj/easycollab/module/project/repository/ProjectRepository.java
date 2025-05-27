package com.irnproj.easycollab.module.project.repository;

import com.irnproj.easycollab.module.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  // 단일 팀의 프로젝트 조회
  List<Project> findAllByTeamId(Long teamId);
  // 여러 팀에 속한 모든 프로젝트 일괄 조회 (IN 절 사용)
  List<Project> findAllByTeamIdIn(List<Long> teamIds);
//  List<Project> findByTeamId(Long teamId);
}