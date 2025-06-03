package com.irnproj.easycollab.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  // 400 Bad Request : 사용자 입력 오류나 요청 형식 오류
  INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
  DUPLICATE_PROJECT_MEMBER("이미 프로젝트에 등록된 사용자입니다.", HttpStatus.BAD_REQUEST),
  PROJECT_MEMBER_MISMATCH("프로젝트 멤버 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
  AUTHENTICATION_FAILED("인증에 실패했습니다.", HttpStatus.BAD_REQUEST),
  CANNOT_DELETE_COMMENT_WITH_CHILD("대댓글이 존재하는 댓글은 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST),

  // 401 Unauthorized : 인증 자체 실패 (로그인 필요)
  UNAUTHORIZED("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED),

  // 403 Forbidden : 인증은 되었으나 권한이 없음
  FORBIDDEN("해당 리소스에 대한 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
  UNAUTHORIZED_COMMENT_ACCESS("댓글에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
  UNAUTHORIZED_NOTIFICATION_ACCESS("해당 알림에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),

  // 404 Not Found : 데이터 중복이나 제약조건 위반
  COM_CODE_NOT_FOUND("해당 공통 코드를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  ROLE_NOT_FOUND("역할 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  USER_NOT_FOUND("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  ISSUE_NOT_FOUND("이슈를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  TEAM_NOT_FOUND("팀을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  PROJECT_NOT_FOUND("프로젝트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  PROJECT_MEMBER_NOT_FOUND("프로젝트 멤버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
  NOTIFICATION_NOT_FOUND("알림을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

  // 409 Conflict
  DUPLICATE_LOGIN_ID("이미 사용 중인 로그인 ID입니다.", HttpStatus.CONFLICT),
  DUPLICATE_EMAIL("이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
  DUPLICATE_NICKNAME("이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT),
  DUPLICATE_TEAM_NAME("이미 존재하는 팀 이름입니다.", HttpStatus.CONFLICT);

  private final String message;
  private final HttpStatus status;

  ErrorCode(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
  }
}