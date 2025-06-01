// ProjectDetailPage.tsx
import React, { useState } from 'react';
import styles from '../../shared/styles/DetailPage.module.css';
import { useParams, useNavigate } from 'react-router-dom';

const ProjectDetailPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [menuOpen, setMenuOpen] = useState(false);

  // 예시 데이터 (API 연동 전)
  const project = {
    id,
    name: '프로젝트 A',
    status: '진행 중',
    deadline: '2025-06-15',
    issues: [
      { id: 1, title: '이슈 1' },
      { id: 2, title: '이슈 2' },
    ],
  };

  const handleDelete = () => {
    if (window.confirm('이 프로젝트를 삭제하시겠습니까?')) {
      // 삭제 API 요청
      alert('삭제되었습니다.');
      navigate('/');
    }
  };

  return (
    <div style={{ padding: '24px' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <div className={styles.title}>{project.name}</div>
        <div style={{ position: 'relative' }}>
          <button onClick={() => setMenuOpen(!menuOpen)} style={{ background: 'none', border: 'none', fontSize: '24px', cursor: 'pointer' }}>⋯</button>
          {menuOpen && (
            <div style={{
              position: 'absolute',
              right: 0,
              top: '30px',
              background: 'white',
              border: '1px solid #ccc',
              boxShadow: '0 2px 6px rgba(0,0,0,0.2)',
              borderRadius: '4px',
              zIndex: 1
            }}>
              <div style={{ padding: '8px 12px', cursor: 'pointer' }} onClick={() => navigate(`/projects/${project.id}/edit`)}>수정</div>
              <div style={{ padding: '8px 12px', cursor: 'pointer', color: 'red' }} onClick={handleDelete}>삭제</div>
            </div>
          )}
        </div>
      </div>
      <div className={styles.section}><span className={styles.label}>상태:</span><span className={styles.value}>{project.status}</span></div>
      <div className={styles.section}><span className={styles.label}>마감일:</span><span className={styles.value}>{project.deadline}</span></div>
      <div className={styles.section}><strong>관련 이슈</strong>
        <ul>
          {project.issues.map(issue => (
            <li 
              key={issue.id} 
              style={{ cursor: 'pointer', textDecoration: 'underline', color: '#3f51b5' }}
              onClick={() => navigate(`/issues/${issue.id}`)}
            >
              {issue.title}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default ProjectDetailPage;