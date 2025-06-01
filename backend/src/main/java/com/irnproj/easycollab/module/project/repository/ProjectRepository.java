package com.irnproj.easycollab.module.project.repository;

import com.irnproj.easycollab.module.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  // 프로젝트명에 키워드가 포함된 항목 검색 (대소문자 무시)
  List<Project> findByNameContainingIgnoreCase(String name);
}
