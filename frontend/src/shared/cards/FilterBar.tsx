// FilterBar.tsx
import React from 'react';
import styles from './ProjectCardList.module.css';
import searchIcon from '../icons/searchIcon_modified.svg';

interface Props {
  filter: string;
  onFilterChange: (filter: string) => void;
  searchTerm: string;
  onSearchChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const FilterBar: React.FC<Props> = ({ filter, onFilterChange }) => {
  return (
    <div className={styles.filterContainer}>
      {/* 필터탭 */}
      <div className={styles.filterTabs}>
        {['전체', '예정', '진행 중', '지연', '완료'].map((status) => (
          <button
            key={status}
            className={filter === status ? styles.activeTab : styles.tab}
            onClick={() => onFilterChange(status)}
          >
            {status}
          </button>
        ))}
      </div>

      {/* 검색창 */}
      <div className={styles.searchRow}>
          <input
            className={styles.searchInput}
            placeholder="검색어를 입력하세요"
          />
          <img src={searchIcon} alt="검색" className={styles.searchIcon} />
        </div>
    </div>
  );
};

export default FilterBar;