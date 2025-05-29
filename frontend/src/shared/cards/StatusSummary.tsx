// StatusSummary.tsx
import React from 'react';
import styles from './ProjectCardList.module.css';

interface Props {
  showMyTeamOnly: boolean;
  onToggleMyTeam: () => void;
  total: number;
  urgentCount: number;
  pinnedCount: number;
  sortOrder: string;
  onSortChange: (order: string) => void;
}

const StatusSummary: React.FC<Props> = ({ showMyTeamOnly, onToggleMyTeam, total, urgentCount, pinnedCount, sortOrder, onSortChange }) => {
  return (
    <div className={styles.summaryContainer}>
      {/* 내팀만 보기 설정 */}
      <label className={styles.checkboxLabel}>
        <input
          type="checkbox"
          checked={showMyTeamOnly}
          onChange={onToggleMyTeam}
        />
        우리 팀만 보기
        <span className={styles.summary}>
          ({total}) 🔥 {urgentCount} 📌 {pinnedCount}
        </span>

      </label>

      {/* 드롭다운 */}
      <select className={styles.selectBox} value={sortOrder} onChange={(e) => onSortChange(e.target.value)}>
        <option value="최신순">최신순</option>
        <option value="이름순">이름순</option>
      </select>
    </div>
  );
};

export default StatusSummary;