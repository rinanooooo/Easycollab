package com.irnproj.easycollab.module.comment.repository;

import com.irnproj.easycollab.module.comment.entity.Comment;
import com.irnproj.easycollab.module.issue.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  /**
   * 이슈 ID로 댓글 목록을 조회 (작성자 및 부모 댓글까지 fetch join 포함)
   */
  @Query("SELECT c FROM Comment c " +
      "JOIN FETCH c.writer " +
      "LEFT JOIN FETCH c.parent " +
      "WHERE c.issue.id = :issueId")
  List<Comment> findByIssueIdWithUserAndParent(Long issueId);

  /**
   * 특정 댓글의 자식 댓글 존재 여부 확인
   */
  boolean existsByParentId(Long parentId);
}