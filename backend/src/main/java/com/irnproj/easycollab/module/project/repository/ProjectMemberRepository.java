package com.irnproj.easycollab.module.project.repository;

import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.project.entity.ProjectMember;
import com.irnproj.easycollab.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
  List<ProjectMember> findByProject(Project project);
  boolean existsByProjectAndUser(Project project, User user);
}
