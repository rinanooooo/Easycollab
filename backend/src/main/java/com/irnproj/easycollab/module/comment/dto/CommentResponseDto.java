package com.irnproj.easycollab.module.comment.dto;

import com.irnproj.easycollab.module.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentResponseDto {
  private Long id;                         // 댓글 ID
  private String content;                  // 댓글 내용
  private Long parentId;                   // 부모 댓글 ID
  private String writerName;               // 작성자 이름
  private Long writerId;                   // 작성자 ID
  private LocalDateTime createdAt;         // 생성일
  private LocalDateTime updatedAt;         // 수정일
  private List<CommentResponseDto> children = new ArrayList<>();  // 대댓글 목록

  /**
   * Comment 엔티티를 DTO로 변환합니다.
   */
  public static CommentResponseDto of(Comment comment) {
    CommentResponseDto dto = new CommentResponseDto();
    dto.setId(comment.getId());
    dto.setContent(comment.getContent());
    dto.setWriterId(comment.getWriter().getId());
    dto.setWriterName(comment.getWriter().getNickname());
    dto.setCreatedAt(comment.getCreatedAt());
    dto.setUpdatedAt(comment.getUpdatedAt());
    if (comment.getParent() != null) {
      dto.setParentId(comment.getParent().getId());
    }
    return dto;
  }
}