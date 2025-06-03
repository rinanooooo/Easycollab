package com.irnproj.easycollab.module.comment.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import com.irnproj.easycollab.module.issue.entity.Issue;
import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "issue_id")
  private Issue issue;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "writer_id")
  private User writer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Comment parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> children = new ArrayList<>();

  /**
   * 댓글 내용 수정
   */
  public void updateContent(String newContent) {
    this.content = newContent;
  }

  /**
   * 부모 댓글 설정 및 양방향 연관관계 설정
   */
  public void setParent(Comment parent) {
    this.parent = parent;
    parent.getChildren().add(this);
  }

  /**
   * 생성자 빌더
   */
  @Builder
  public Comment(String content, Issue issue, User writer) {
    this.content = content;
    this.issue = issue;
    this.writer = writer;
  }
}