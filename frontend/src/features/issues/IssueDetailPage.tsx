// IssueDetailPage.tsx
import React, { useState, useEffect, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import styles from '../../shared/styles/DetailPage.module.css';
import common from '../../shared/styles/Common.module.css';

const IssueDetailPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [menuOpenIndex, setMenuOpenIndex] = useState<number | null>(null);
  const [titleMenuOpen, setTitleMenuOpen] = useState(false);
  const titleMenuRef = useRef<HTMLDivElement | null>(null);
  const commentMenuRef = useRef<HTMLDivElement | null>(null);

  const [comment, setComment] = useState('');
  const [comments, setComments] = useState([
    { author: '홍길동', content: '이슈 확인했습니다. 빠르게 처리할게요.' },
    { author: '김철수', content: '관련 로그 첨부 부탁드립니다.' },
    { author: '나', content: '지금 수정 중입니다.' }
  ]);

  const issue = {
    id,
    title: '버튼 클릭 시 오류 발생',
    statusName: '진행 중',
    statusCode: 'IN_PROGRESS',
    reporterName: '홍길동',
    assigneeName: '김철수',
    projectName: '프로젝트 A',
    projectId: 1,
    createdAt: '2025-05-01',
    updatedAt: '2025-05-28',
    content: '버튼 클릭 시 콘솔에 에러가 출력되고 동작이 안 됩니다.',
  };

  const handleDelete = (index: number) => {
    if (window.confirm('댓글을 삭제하시겠습니까?')) {
      setComments(comments.filter((_, i) => i !== index));
      setMenuOpenIndex(null);
    }
  };

  const handleEdit = (index: number) => {
    const newContent = window.prompt('댓글을 수정하세요', comments[index].content);
    if (newContent !== null) {
      const updated = [...comments];
      updated[index].content = newContent;
      setComments(updated);
      setMenuOpenIndex(null);
    }
  };

  const handleAddComment = () => {
    if (comment.trim()) {
      setComments([...comments, { author: '나', content: comment }]);
      setComment('');
    }
  };

  const handleDeleteIssue = () => {
    if (window.confirm('이 이슈를 삭제하시겠습니까?')) {
      alert('삭제되었습니다.');
      navigate('/issues');
    }
  };

  useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      if (commentMenuRef.current && !commentMenuRef.current.contains(e.target as Node)) {
        setMenuOpenIndex(null);
      }
      if (titleMenuRef.current && !titleMenuRef.current.contains(e.target as Node)) {
        setTitleMenuOpen(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  return (
    <div style={{ padding: '24px' }}>
        <span
          style={{ textDecoration: 'underline', cursor: 'pointer' }}
          onClick={() => navigate(`/projects/${issue.projectId}`)}
        >
          {issue.projectName}
        </span>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <h2 className={styles.title}>{issue.title}</h2>
        <div style={{ position: 'relative' }} ref={titleMenuRef}>
          <button onClick={() => setTitleMenuOpen(prev => !prev)} className={common.menuButton}>⋮</button>
          {titleMenuOpen && (
            <div className={common.menuDropdown}>
              <div onClick={() => navigate(`/issues/${issue.id}/edit`)} className={common.menuItem}>수정</div>
              <div onClick={handleDeleteIssue} className={common.menuItem} style={{ color: 'red' }}>삭제</div>
            </div>
          )}
        </div>
      </div>

      <p><span className={styles.label}>상태:</span> {issue.statusName} ({issue.statusCode})</p>
      <p><span className={styles.label}>작성자:</span> {issue.reporterName}</p>
      <p><span className={styles.label}>담당자:</span> {issue.assigneeName || '없음'}</p>
      <p className={styles.meta}>등록일: {issue.createdAt}</p>
      <p className={styles.meta}>수정일: {issue.updatedAt}</p>
      <hr />
      <p>{issue.content}</p>
      <hr />
      <div className={styles.section}>
        <strong>댓글</strong>
        <div className={styles.commentContainer}>
          <input
            type="text"
            placeholder="댓글을 입력하세요"
            value={comment}
            onChange={e => setComment(e.target.value)}
            className={styles.commentInput}
          />
          <button onClick={handleAddComment} className={styles.commentButton}>등록</button>
        </div>
        <hr />
        <div className={styles.commentList}>
          {comments.map((c, index) => (
            <div key={index} className={styles.commentItem}>
              <div className={styles.commentAuthor}>{c.author}</div>
              <div className={styles.commentContent}>{c.content}</div>
              {c.author === '나' && (
                <div style={{ position: 'relative', marginLeft: 'auto' }} ref={commentMenuRef}>
                  <button 
                    style={{margin: '0 auto'}}
                    onClick={() => setMenuOpenIndex(index === menuOpenIndex ? null : index)} className={common.menuButton}>⋮</button>
                  {menuOpenIndex === index && (
                    <div className={common.menuDropdown}>
                      <div onClick={() => handleEdit(index)} className={common.menuItem}>수정</div>
                      <div onClick={() => handleDelete(index)} className={common.menuItem} style={{ color: 'red' }}>삭제</div>
                    </div>
                  )}
                </div>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default IssueDetailPage;