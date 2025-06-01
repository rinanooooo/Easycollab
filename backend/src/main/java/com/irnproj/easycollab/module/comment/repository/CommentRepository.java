package com.irnproj.easycollab.module.comment.repository;

import com.irnproj.easycollab.module.comment.entity.Comment;
import com.irnproj.easycollab.module.issue.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  // 해당 이슈에서 원댓글(부모가 null) 들만 가져옴
  List<Comment> findByIssueAndParentIsNullOrderByCreatedAtAsc(Issue issue);
  // 마이페이지: 내가 작성한 댓글 목록
  List<Comment> findByAuthorIdOrderByCreatedAtDesc(Long authorId);

}