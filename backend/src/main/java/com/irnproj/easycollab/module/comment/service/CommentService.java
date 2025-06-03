package com.irnproj.easycollab.module.comment.service;

import com.irnproj.easycollab.common.exception.CustomException;
import com.irnproj.easycollab.common.exception.ErrorCode;
import com.irnproj.easycollab.module.comment.dto.CommentResponseDto;
import com.irnproj.easycollab.module.comment.dto.CommentRequestDto;
import com.irnproj.easycollab.module.comment.dto.CommentResponseDto;
import com.irnproj.easycollab.module.comment.entity.Comment;
import com.irnproj.easycollab.module.comment.repository.CommentRepository;
import com.irnproj.easycollab.module.issue.entity.Issue;
import com.irnproj.easycollab.module.issue.repository.IssueRepository;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import com.irnproj.easycollab.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final IssueRepository issueRepository;

  /**
   * 댓글을 생성합니다.
   * 부모 댓글 ID가 있을 경우 대댓글로 저장됩니다.
   */
  @Transactional
  public CommentResponseDto createComment(CommentRequestDto requestDto) {
    Long userId = SecurityUtil.getCurrentUserId();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    Issue issue = issueRepository.findById(requestDto.getIssueId())
        .orElseThrow(() -> new CustomException(ErrorCode.ISSUE_NOT_FOUND));

    Comment comment = Comment.builder()
        .content(requestDto.getContent())
        .issue(issue)
        .writer(user)
        .build();

    if (requestDto.getParentId() != null) {
      Comment parent = commentRepository.findById(requestDto.getParentId())
          .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
      comment.setParent(parent);
    }

    return CommentResponseDto.of(commentRepository.save(comment));
  }

  /**
   * 주어진 이슈 ID에 해당하는 댓글을 트리 형태로 조회합니다.
   */
  public List<CommentResponseDto> getCommentsByIssueId(Long issueId) {
    List<Comment> comments = commentRepository.findByIssueIdWithUserAndParent(issueId);

    Map<Long, List<CommentResponseDto>> groupedByParentId = comments.stream()
        .map(CommentResponseDto::of)
        .collect(Collectors.groupingBy(dto -> dto.getParentId() == null ? 0L : dto.getParentId()));

    return buildCommentTree(groupedByParentId, 0L);
  }

  private List<CommentResponseDto> buildCommentTree(Map<Long, List<CommentResponseDto>> map, Long parentId) {
    List<CommentResponseDto> result = new ArrayList<>();
    List<CommentResponseDto> children = map.getOrDefault(parentId, new ArrayList<>());

    for (CommentResponseDto child : children) {
      child.setChildren(buildCommentTree(map, child.getId()));
      result.add(child);
    }

    return result;
  }

  /**
   * 댓글을 수정합니다. 작성자 본인만 수정할 수 있습니다.
   * 수정 시에는 content만 사용, issueId와 parentId는 무시
   */
  @Transactional
  public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    validateWriter(comment.getWriter().getId());

    comment.updateContent(requestDto.getContent());
    return CommentResponseDto.of(comment);
  }


  /**
   * 댓글을 삭제합니다.
   * 자식 댓글이 존재하는 경우 삭제할 수 없습니다.
   */
  @Transactional
  public void deleteComment(Long commentId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

    validateWriter(comment.getWriter().getId());

    boolean hasChild = commentRepository.existsByParentId(comment.getId());
    if (hasChild) {
      throw new CustomException(ErrorCode.CANNOT_DELETE_COMMENT_WITH_CHILD);
    }

    commentRepository.delete(comment);
  }

  /**
   * 현재 로그인한 사용자가 작성자인지 확인합니다.
   */
  private void validateWriter(Long writerId) {
    Long currentUserId = SecurityUtil.getCurrentUserId();
    if (!writerId.equals(currentUserId)) {
      throw new CustomException(ErrorCode.UNAUTHORIZED_COMMENT_ACCESS);
    }
  }
}