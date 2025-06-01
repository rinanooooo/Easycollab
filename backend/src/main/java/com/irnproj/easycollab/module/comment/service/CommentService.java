package com.irnproj.easycollab.module.comment.service;

import com.irnproj.easycollab.module.comment.dto.CommentRequestDto;
import com.irnproj.easycollab.module.comment.dto.CommentResponseDto;
import com.irnproj.easycollab.module.comment.entity.Comment;
import com.irnproj.easycollab.module.comment.repository.CommentRepository;
import com.irnproj.easycollab.module.issue.entity.Issue;
import com.irnproj.easycollab.module.issue.repository.IssueRepository;
import com.irnproj.easycollab.module.notification.service.NotificationService;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final IssueRepository issueRepository;
  private final NotificationService notificationService;

  /**
   * 댓글 생성 (대댓글 포함)
   */
  // 부모 ID가 있으면 대댓글, 없으면 원댓글
  @Transactional
  public CommentResponseDto createComment(CommentRequestDto dto, Long userId) {
    User author = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("작성자 정보를 찾을 수 없습니다."));

    Issue issue = issueRepository.findById(dto.getIssueId())
        .orElseThrow(() -> new IllegalArgumentException("이슈 정보를 찾을 수 없습니다."));

    Comment parent = null;
    if (dto.getParentId() != null) {
      parent = commentRepository.findById(dto.getParentId())
          .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다."));
    }

    Comment comment = Comment.builder()
        .content(dto.getContent())
        .author(author)
        .issue(issue)
        .parent(parent)
        .build();

    Comment saved = commentRepository.save(comment);

    User issueAuthor = issue.getReporter();

    // 알림 내용 요약 "'이슈b'에 새로운 댓글: '버그 수정이 필요합니다…'"
    String shortContent = comment.getContent().length() > 20
        ? comment.getContent().substring(0, 20) + "..."
        : comment.getContent();

    if (!issueAuthor.getId().equals(author.getId())) { // 자기 자신에게는 알림 X
      notificationService.createNotification(
          issueAuthor.getId(),
          "'" + issue.getTitle() + "'에 새로운 댓글: '" + shortContent + "'",
          "/issues/" + issue.getId() + "#comment-" + saved.getId()
      );
    }

    return toDto(saved);
  }

  /**
   * 이슈에 달린 댓글 전체 조회 (원댓글 + 대댓글)
   */
  // 원 댓글부터 불러서 자식은 children으로 재귀 구성
  @Transactional(readOnly = true)
  public List<CommentResponseDto> getCommentsByIssue(Long issueId) {
    Issue issue = issueRepository.findById(issueId)
        .orElseThrow(() -> new IllegalArgumentException("이슈 정보를 찾을 수 없습니다."));

    List<Comment> rootComments = commentRepository.findByIssueAndParentIsNullOrderByCreatedAtAsc(issue);

    return rootComments.stream()
        .map(this::toDtoWithChildren)
        .collect(Collectors.toList());
  }

  // 단일 댓글 + 자식 재귀 포함 변환
  private CommentResponseDto toDtoWithChildren(Comment comment) {
    return CommentResponseDto.builder()
        .id(comment.getId())
        .content(comment.getContent())
        .authorName(comment.getAuthor().getNickname())
        .authorId(comment.getAuthor().getId())
        .createdAt(comment.getCreatedAt())
        .updatedAt(comment.getUpdatedAt())
        .children(comment.getChildren().stream()
            .map(this::toDtoWithChildren) // 재귀 호출
            .collect(Collectors.toList()))
        .build();
  }

  // 단일 댓글 변환 (대댓글 포함 X)
  private CommentResponseDto toDto(Comment comment) {
    return CommentResponseDto.builder()
        .id(comment.getId())
        .content(comment.getContent())
        .authorName(comment.getAuthor().getNickname())
        .authorId(comment.getAuthor().getId())
        .createdAt(comment.getCreatedAt())
        .updatedAt(comment.getUpdatedAt())
        .build();
  }

  /**
   * 댓글 수정
   */
  @Transactional
  public void updateComment(Long id, CommentRequestDto dto, Long userId) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

    if (!comment.getAuthor().getId().equals(userId)) {
      throw new IllegalArgumentException("본인의 댓글만 수정할 수 있습니다.");
    }

    comment.update(dto.getContent());
  }

  /**
   * 댓글 삭제
   */
  @Transactional
  public void deleteComment(Long id, Long userId) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

    if (!comment.getAuthor().getId().equals(userId)) {
      throw new IllegalArgumentException("본인의 댓글만 삭제할 수 있습니다.");
    }

    commentRepository.delete(comment);
  }

  // 마이페이지: 내가 작성한 댓글 목록
  @Transactional(readOnly = true)
  public List<CommentResponseDto> getMyComments(Long userId) {
    return commentRepository.findByAuthorIdOrderByCreatedAtDesc(userId).stream()
        .map(this::toDto) // children 없이 단일 댓글만
        .collect(Collectors.toList());
  }


}