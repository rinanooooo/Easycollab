import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CreateProjectModal from '../../features/projects/components/CreateProjectModal';
import CreateIssueModal from '../../features/issues/components/CreateIssueModal';
import { statusIconMap, statusColorMap } from '../../constants/statusMap';


import common from '../styles/Common.module.css';
import styles from './RightPanel.module.css';

// 더미 프로젝트 목록 (실제 프로젝트에선 API 호출로 대체)
const myProjects = [
  { id: 1, name: '프로젝트 Alpha' },
  { id: 2, name: '프로젝트 Beta' },
  { id: 3, name: '프로젝트 Gamma' },
  { id: 4, name: '프로젝트 Delta' },
];

// 더미 이슈 목록 (상태와 고정 여부 포함)
const myIssues = [
  { id: 101, title: '이슈 #123 - 오류 수정', status: '긴급', updatedAt: '2024-05-27', isPinned: false },
  { id: 102, title: '이슈 #456 - 마감 임박', status: '주의', updatedAt: '2024-05-25', isPinned: true },
  { id: 103, title: '이슈 #789 - 문서 작성', status: '완료', updatedAt: '2024-05-20', isPinned: false },
  { id: 104, title: '이슈 #999 - 테스트 필요', status: '주의', updatedAt: '2024-05-28', isPinned: false },
  { id: 105, title: '이슈 #1234 - 품질 테스트', status: '주의', updatedAt: '2024-05-29', isPinned: false }
];

const RightPanel: React.FC = () => {
  const navigate = useNavigate();

  // 고정된 이슈 ID 목록 상태
  const [pinnedIds, setPinnedIds] = useState<number[]>(() =>
    myIssues.filter((i) => i.isPinned).map((i) => i.id)
  );

  // 프로젝트, 이슈 더보기 상태
  const [showAllProjects, setShowAllProjects] = useState(false);
  const [showAllIssues, setShowAllIssues] = useState(false);

  // 모달 오픈 상태
  const [isProjectModalOpen, setProjectModalOpen] = useState(false);
  const [isIssueModalOpen, setIssueModalOpen] = useState(false);

  // 최근 업데이트 기준으로 이슈 정렬
  const sortedIssues = myIssues.sort((a, b) => {
    const dA = new Date(a.updatedAt).getTime();
    const dB = new Date(b.updatedAt).getTime();
    return dB - dA;
  });

  // 고정 이슈와 일반 이슈 분리
  const pinnedIssues = sortedIssues.filter((i) => pinnedIds.includes(i.id));
  const normalIssues = sortedIssues.filter((i) => !pinnedIds.includes(i.id));

  // 보여줄 이슈 (더보기 여부에 따라 다르게)
  const visibleIssues = showAllIssues
    ? [...pinnedIssues, ...normalIssues]
    : [...pinnedIssues, ...normalIssues].slice(0, 3);

  // 상태별 이슈 개수 집계
  const statusCounts = sortedIssues.reduce<Record<string, number>>((acc, i) => {
    acc[i.status] = (acc[i.status] || 0) + 1;
    return acc;
  }, {});

  // 핀 토글 함수
  const togglePin = (id: number) => {
    setPinnedIds((prev) =>
      prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]
    );
  };

  return (
    <aside className={styles.container}>
      {/* 내 프로젝트 섹션 */}
      <section>
        <div className={styles.sectionHeader}>
          <span className={styles.sectionTitle}>
            내 프로젝트 <span style={{ color: '#555', fontSize: '12px' }}>({myProjects.length})</span>
          </span>
          <button
            className={common.iconButton}
            onClick={() => setProjectModalOpen(true)}
          >
            ＋
          </button>
        </div>

        <ul className={styles.list}>
          {/* 프로젝트 목록 표시 (최대 3개 또는 전체) */}
          {(showAllProjects ? myProjects : myProjects.slice(0, 3)).map((p) => (
            <li
              key={p.id}
              className={styles.listItem}
              onClick={() => navigate(`/projects/${p.id}`)}
            >
              📁 {p.name}
            </li>
          ))}

          {/* 프로젝트가 4개 이상이면 더보기/접기 버튼 표시 */}
          {myProjects.length > 3 && (
            <li
              className={common.moreToggle}
              onClick={() => setShowAllProjects((prev) => !prev)}
            >
              {showAllProjects ? '− 접기' : '+ 더보기'}
            </li>
          )}
        </ul>
      </section>

      {/* 내 이슈 섹션 */}
      <section className={styles.sectionMarginTop}>
        <div className={styles.sectionHeader}>
          <div className={styles.issueHeader}>
            <span className={styles.sectionTitle}>
              내 이슈 <span style={{ color: '#555', fontSize: '12px' }}>({sortedIssues.length})</span>
            </span>
            {/* 상태별 카운트 출력 */}
            {statusCounts['긴급'] > 0 && <span style={{ color: '#e53935' }}>🔥 {statusCounts['긴급']}</span>}
            {pinnedIds.length > 0 && <span style={{ color: '#333' }}>📌 {pinnedIds.length}</span>}
          </div>
          <button className={common.iconButton} onClick={() => setIssueModalOpen(true)}>＋</button>
        </div>

        <ul className={styles.list}>
          {/* 이슈 목록 표시 (최대 3개 또는 전체) */}
          {visibleIssues.map((i) => (
            <li
              key={i.id}
              className={styles.listItem}
              style={{
                color: statusColorMap[i.status] || '#333',
                fontWeight: i.status === '긴급' ? 'bold' : undefined,
              }}
              onClick={() => navigate(`/issues/${i.id}`)}
            >
              <span>
                {statusIconMap[i.status] || '🐞'} {i.title}
              </span>
              <span onClick={(e) => { e.stopPropagation(); togglePin(i.id); }}>
                {pinnedIds.includes(i.id) ? '📌' : '📍'}
              </span>
            </li>
          ))}

          {/* 이슈가 4개 이상일 때 더보기/접기 버튼 표시 */}
          {sortedIssues.length > 3 && (
            <li
              className={common.moreToggle}
              onClick={() => setShowAllIssues((prev) => !prev)}
            >
              {showAllIssues ? '− 접기' : '+ 더보기'}
            </li>
          )}
        </ul>
      </section>

      {/* 프로젝트 생성 모달 */}
      <CreateProjectModal
        isOpen={isProjectModalOpen}
        onClose={() => setProjectModalOpen(false)}
        onCreate={() => setProjectModalOpen(false)}
      />

      {/* 이슈 생성 모달 */}
      <CreateIssueModal
        isOpen={isIssueModalOpen}
        onClose={() => setIssueModalOpen(false)}
        onCreate={() => setIssueModalOpen(false)}
      />
    </aside>
  );
};

export default RightPanel;