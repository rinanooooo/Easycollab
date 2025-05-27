package com.irnproj.easycollab.module.project.repository;

import com.irnproj.easycollab.module.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findByTeamId(Long teamId);
}