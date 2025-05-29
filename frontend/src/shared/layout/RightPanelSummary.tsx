
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { statusIconMap, statusColorMap } from '../../constants/statusMap';

import styles from './RightPanel.module.css';
import common from '../styles/Common.module.css';

const myProjects = [
  { id: 1, name: '프로젝트 Alpha' },
  { id: 2, name: '프로젝트 Beta' },
  { id: 3, name: '프로젝트 Gamma' },
  { id: 4, name: '프로젝트 Delta' },
];

const myIssues = [
  { id: 101, title: '이슈 #123 - 오류 수정', status: '긴급' },
  { id: 102, title: '이슈 #456 - 마감 임박', status: '주의' },
  { id: 103, title: '이슈 #789 - 문서 작성', status: '완료' },
  { id: 104, title: '이슈 #999 - 추가 확인', status: '주의' },
  { id: 105, title: '이슈 #1234 - 품질 테스트', status: '주의' },
];

const RightPanelSummary: React.FC = () => {
  const navigate = useNavigate();

  const [showAllProjects, setShowAllProjects] = useState(false);
  const [showAllIssues, setShowAllIssues] = useState(false);

  const visibleProjects = showAllProjects ? myProjects : myProjects.slice(0, 3);
  const visibleIssues = showAllIssues ? myIssues : myIssues.slice(0, 3);

  return (
    <div style={menuWrapperStyle}>
      <div style={menuGroupStyle}>
        <div className={common.sectionTitle}>
          📁 내 프로젝트 <span className={common.badge}>({myProjects.length})</span>
        </div>

        {visibleProjects.map((p) => (
          <div
            key={p.id}
            className={styles.listItem}
            onClick={() => navigate(`/projects/${p.id}`)}
          >
            {p.name}
          </div>
        ))}

        {myProjects.length > 3 && (
          <div
            className={common.moreToggle}
            onClick={() => setShowAllProjects((prev) => !prev)}
          >
            {showAllProjects ? '− 접기' : '+ 더보기'}
          </div>
        )}
      </div>

      <div style={menuGroupStyle}>
        <div className={common.sectionTitle}>
          🐞 내 이슈 <span className={common.badge}>({myIssues.length})</span>
        </div>

        {visibleIssues.map((i) => (
          <div
            key={i.id}
            className={styles.listItem}
            onClick={() => navigate(`/issues/${i.id}`)}
            style={{
              color: statusColorMap[i.status] || '#333',
              fontWeight: i.status === '긴급' ? 'bold' : undefined,
            }}
          >
            {statusIconMap[i.status] || '🐞'} {i.title}
          </div>
        ))}

        {myIssues.length > 3 && (
          <div
            className={common.moreToggle}
            onClick={() => setShowAllIssues((prev) => !prev)}
          >
            {showAllIssues ? '− 접기' : '+ 더보기'}
          </div>
        )}
      </div>
    </div>
  );
};

export default RightPanelSummary;

const menuWrapperStyle: React.CSSProperties = {
  display: 'flex',
  justifyContent: 'space-between',
  flexWrap: 'wrap',
  padding: '10px 16px',
  backgroundColor: '#ffffff',
  borderBottom: '1px solid #ddd',
  fontSize: '13px',
  gap: '24px',
};

const menuGroupStyle: React.CSSProperties = {
  flex: 1,
  minWidth: '150px',
};
