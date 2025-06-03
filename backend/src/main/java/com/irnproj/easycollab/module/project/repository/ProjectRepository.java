package com.irnproj.easycollab.module.project.repository;

import com.irnproj.easycollab.module.project.entity.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findByNameContaining(String keyword);
  @Query("SELECT pm.project FROM ProjectMember pm WHERE pm.user.id = :userId")
  List<Project> findByMemberUserId(@Param("userId") Long userId);
  @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.issues")
  List<Project> findAllWithIssues();
}
