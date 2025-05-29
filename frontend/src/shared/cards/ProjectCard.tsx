import React, { useState } from 'react';
import styles from './ProjectCard.module.css';
import common from '../styles/Common.module.css';

// 프로젝트 카드에 전달될 props 타입 정의
interface ProjectCardProps {
  id: number;
  title: string;
  status: '예정' | '진행 중' | '완료' | '지연';
  issues?: { id: number; title: string }[];
  onClick?: () => void;
  onIssueClick?: (issueId: number) => void;
}

// 상태에 따른 색상 매핑
const statusColorMap: Record<string, string> = {
  '예정': '#9ca3af',
  '진행 중': '#3b82f6',
  '완료': '#10b981',
  '지연': '#ef4444',
};

const ProjectCard: React.FC<ProjectCardProps> = ({ id, title, status, issues = [], onClick, onIssueClick }) => {
  const [menuOpen, setMenuOpen] = useState(false); // 점 세 개 메뉴 상태

  return (
    <div className={styles.card} onClick={onClick}>
      {/* 카드 상단: 타이틀 + 메뉴 */}
      <div className={styles.cardHeader}>
        <h3 className={styles.cardTitle}>{title}</h3>
        <div className={styles.menuWrapper} onClick={(e) => e.stopPropagation()}>
          <span className={styles.menuButton} onClick={() => setMenuOpen(!menuOpen)}>⋯</span>
          {menuOpen && (
            <div className={styles.dropdown}>
              <div className={styles.dropdownItem}>편집</div>
              <div className={styles.dropdownItem}>삭제</div>
            </div>
          )}
        </div>
      </div>

      {/* 관련 이슈 리스트 */}
      <div className={styles.issues}>
        {issues.map((issue) => (
          <div
            key={issue.id}
            className={common.listItem}
            onClick={(e) => {
              e.stopPropagation();
              onIssueClick?.(issue.id);
            }}
          >
            🐞 {issue.title}
          </div>
        ))}
      </div>

      {/* 상태 뱃지 */}
      <div
        className={styles.badge}
        style={{ backgroundColor: statusColorMap[status] }}
      >
        {status}
      </div>
    </div>
  );
};

export default ProjectCard;
