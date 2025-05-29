import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ProjectCard from './ProjectCard';
import FilterBar from './FilterBar';
import StatusSummary from './StatusSummary';
import { useBreakpoint } from '../../hooks/useBreakpoint';
import { Project } from '../types/project';
import ProjectModal from '../../features/projects/components/ProjectInfoModal';

import common from '../styles/Common.module.css';
import styles from './ProjectCardList.module.css';

// 프로젝트 상태 타입 정의
export type ProjectStatus = '예정' | '진행 중' | '완료' | '지연';

// 임시 프로젝트 데이터
const dummyProjects: Project[] = [
  { id: 1, title: 'Project A', status: '진행 중', isPinned: true, isMyTeam: true, issues: [ { id: 101, title: '버그 수정' },  { id: 102, title: '버그 추가' }] },
  { id: 2, title: 'Project B', status: '완료', isPinned: false, isMyTeam: false, issues: [] },
  { id: 3, title: 'Project C', status: '지연', isPinned: false, isMyTeam: true, issues: [ { id: 103, title: 'QA 테스트' }] },
  { id: 4, title: 'Project D', status: '진행 중', isPinned: true, isMyTeam: false, issues: [] },
  { id: 5, title: 'Project E', status: '완료', isPinned: false, isMyTeam: true, issues: [] },
  { id: 6, title: 'Project F', status: '지연', isPinned: false, isMyTeam: true, issues: [ { id: 104, title: '디자인 수정' }] },
  { id: 7, title: 'Project G', status: '예정', isPinned: false, isMyTeam: false, issues: [] },
];

const ProjectCardList: React.FC = () => {
  const { isMobile } = useBreakpoint();
  const [filter, setFilter] = useState<'전체' | '예정' | '진행 중' | '완료' | '지연'>('전체'); // 상태 필터
  const [searchTerm, setSearchTerm] = useState(''); // 검색어
  const [sortOrder, setSortOrder] = useState<'최신순' | '이름순'>('최신순'); // 정렬 방식
  const [showMyTeamOnly, setShowMyTeamOnly] = useState(false); // 내 팀만 보기
  const [visibleCount, setVisibleCount] = useState(6); // 보여지는 개수
  const [selectedProject, setSelectedProject] = useState<Project | null>(null); // 선택된 프로젝트
  const navigate = useNavigate();

  const initialVisible = isMobile ? 4 : 6;
  const increment = isMobile ? 2 : 3;

  useEffect(() => {
    setVisibleCount(isMobile ? 4 : 6);
  }, [filter, showMyTeamOnly, isMobile]);

  // 필터 + 정렬된 프로젝트 리스트
  const filtered = dummyProjects
    .filter((p) => (filter === '전체' || p.status === filter))
    .filter((p) => !showMyTeamOnly || p.isMyTeam)
    .filter((p) => p.title.toLowerCase().includes(searchTerm.toLowerCase()))
    .sort((a, b) => sortOrder === '이름순' ? a.title.localeCompare(b.title) : b.id - a.id);

  const visible = filtered.slice(0, visibleCount);
  const urgentCount = filtered.filter((p) => p.status === '지연').length;
  const pinnedCount = filtered.filter((p) => p.isPinned).length;

  return (
    <div>
      {/* 필터바 (상태, 검색어) */}
      <FilterBar
        filter={filter}
        onFilterChange={(f) => setFilter(f as typeof filter)}
        searchTerm={searchTerm}
        onSearchChange={(e) => setSearchTerm(e.target.value)}
      />

      {/* 프로젝트 요약 (내 팀만 보기, 정렬 등) */}
      <StatusSummary
        showMyTeamOnly={showMyTeamOnly}
        onToggleMyTeam={() => setShowMyTeamOnly((prev) => !prev)}
        total={filtered.length}
        urgentCount={urgentCount}
        pinnedCount={pinnedCount}
        sortOrder={sortOrder}
        onSortChange={(o) => setSortOrder(o as typeof sortOrder)}
      />

      {/* 프로젝트 카드 리스트 */}
      <div className={styles.cardGrid}>
        {visible.map((p) => (
          <ProjectCard
            key={p.id}
            id={p.id}
            title={p.title}
            status={p.status}
            issues={p.issues}
            onClick={() => setSelectedProject(p)}
            onIssueClick={(issueId) => navigate(`/issues/${issueId}`)}
          />
        ))}
      </div>

      {/* 더보기/접기 버튼 */}
      {filtered.length > initialVisible && (
        <div
          className={common.moreToggle}
          style={{fontSize: '15px', marginTop: '10px'}}
          onClick={() =>
            visibleCount >= filtered.length
              ? setVisibleCount(initialVisible)
              : setVisibleCount((prev) => prev + increment)
          }
        >
          {visibleCount >= filtered.length ? '− 접기' : '+ 더보기'}
        </div>
      )}

      {/* 상세 모달 */}
      <ProjectModal
        project={selectedProject}
        onClose={() => setSelectedProject(null)}
      />
    </div>
  );
};

export default ProjectCardList;