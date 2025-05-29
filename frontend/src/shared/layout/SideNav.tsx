import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CreateTeamModal from '../../features/teams/components/CreateTeamModal';

import common from '../styles/Common.module.css';
import styles from './SideNav.module.css';

// 타입 정의: 이슈, 프로젝트, 팀
interface Issue {
  id: number;
  title: string;
}

interface Project {
  id: number;
  name: string;
  issues: Issue[];
}

interface Team {
  teamName: string;
  id: number;
  isMyTeam: boolean;
  projects: Project[];
}

// 더미 데이터 (실제에선 API 호출 결과로 대체)
const dummyData: Team[] = [
  {
    id: 1,
    teamName: '백엔드 개발자팀',
    isMyTeam: true,
    projects: [
      {
        id: 1,
        name: 'Project A-1',
        issues: [
          { id: 101, title: '이슈 A-1-1' },
          { id: 102, title: '이슈 A-1-2' },
        ],
      },
    ],
  },
  {
    id: 2,
    teamName: '프론트엔드 개발자팀',
    isMyTeam: false,
    projects: [],
  },
];

const SideNav: React.FC = () => {
  const [showOnlyMyTeams, setShowOnlyMyTeams] = useState(false); // "내 팀만 보기" 체크박스 상태
  const [isTeamModalOpen, setTeamModalOpen] = useState(false); // 팀 생성 모달 열림 여부

  const filteredTeams = showOnlyMyTeams
    ? dummyData.filter((team) => team.isMyTeam)
    : dummyData;

  const handleCreateTeam = () => {
    setTeamModalOpen(true);
  };

  return (
    <aside className={styles.container}>
      {/* 상단: 전체 팀 제목, 내 팀만 필터, + 버튼 */}
      <div style={{
        ...titleStyle,
        marginBottom: '16px',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center'
      }}>
        <span>
          전체 팀
          <label className={styles.checkboxLabel}>
            <input
              type="checkbox"
              checked={showOnlyMyTeams}
              className={styles.checkbox}
              onChange={(e) => setShowOnlyMyTeams(e.target.checked)}
            />
            <span style={{ whiteSpace: 'nowrap', color: '#555', fontSize: '12px' }}>내 팀만</span>
          </label>
        </span>

        <div style={{ display: 'flex', alignItems: 'center', gap: '6px' }}>
          <button className={common.iconButton} onClick={handleCreateTeam}>＋</button>
        </div>
      </div>

      {/* 팀 목록 */}
      <ul className={styles.teamList}>
        {filteredTeams.map((team) => (
          <TeamItem key={team.id} team={team} />
        ))}
      </ul>

      {/* 팀 생성 모달 */}
      <CreateTeamModal
        isOpen={isTeamModalOpen}
        onClose={() => setTeamModalOpen(false)}
        onCreate={(teamName) => {
          console.log('생성된 팀 이름:', teamName);
          setTeamModalOpen(false);
          // TODO: 팀 목록 업데이트 API 또는 상태 반영
        }}
      />
    </aside>
  );
};

export default SideNav;

interface TeamItemProps {
  team: Team;
}

// 각 팀 아이템: 클릭 시 프로젝트 펼침/접힘
const TeamItem: React.FC<TeamItemProps> = ({ team }) => {
  const [open, setOpen] = useState(false); // 팀 펼침 상태

  return (
    <li>
      <div className={styles.itemWrapper}>
        <div
          className={styles.teamRow}
          onClick={() => setOpen(!open)}
        >
          {open ? '▼' : '▶'}
          <span style={teamNameStyle}>{team.teamName}</span>
        </div>
      </div>

      {/* 팀 펼침 시 프로젝트 목록 출력 */}
      {open &&
        team.projects.map((project) => (
          <ProjectItem key={project.id} project={project} />
        ))}
    </li>
  );
};

// 각 프로젝트 아이템: 클릭 시 이슈 펼침/접힘
const ProjectItem: React.FC<{ project: Project }> = ({ project }) => {
  const [projectOpen, setProjectOpen] = useState(false); // 프로젝트 펼침 여부
  const navigate = useNavigate();

  return (
    <ul className={styles.projectList}>
      <li className={styles.projectItem} onClick={() => setProjectOpen(!projectOpen)}>
        <span style={{ display: 'inline-flex', alignItems: 'center', gap: '6px' }}>
          📁 {project.name}
        </span>
        <span className={styles.badge}>{project.issues.length}</span>
      </li>

      {/* 프로젝트 펼침 시 이슈 목록 출력 */}
      {projectOpen && (
        <ul style={{ listStyleType: 'none', margin: 0, padding: 0 }}>
          {project.issues.map((issue) => (
            <li
              key={issue.id}
              className={styles.issueItem}
              onClick={() => navigate(`/issues/${issue.id}`)}
            >
              <span style={{ fontSize: '14px', marginRight: '4px' }}>ㄴ 🐞</span>
              {issue.title}
            </li>
          ))}
        </ul>
      )}
    </ul>
  );
};

// 제목 텍스트 스타일
const titleStyle: React.CSSProperties = {
  fontSize: '16px',
  fontWeight: 'bold',
};

// 팀 이름 텍스트 스타일
const teamNameStyle: React.CSSProperties = {
  marginLeft: '6px',
  whiteSpace: 'nowrap',
  overflow: 'hidden',
  textOverflow: 'ellipsis',
};