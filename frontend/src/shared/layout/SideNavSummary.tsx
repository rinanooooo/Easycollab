
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import common from '../styles/Common.module.css';

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
  {
    id: 3,
    teamName: '디자인팀',
    isMyTeam: false,
    projects: [],
  },
  {
    id: 4,
    teamName: '기획팀',
    isMyTeam: false,
    projects: [],
  },
  {
    id: 5,
    teamName: 'QA팀',
    isMyTeam: false,
    projects: [],
  },
];

const SideNavSummary: React.FC = () => {
  const navigate = useNavigate();
  const [openTeamIds, setOpenTeamIds] = useState<number[]>([]);
  const [showAll, setShowAll] = useState(false);

  const sortedTeams = [
    ...dummyData.filter((t) => t.isMyTeam),
    ...dummyData.filter((t) => !t.isMyTeam),
  ];

  const visibleTeams = showAll ? sortedTeams : sortedTeams.slice(0, 3);

  const toggleTeam = (id: number) => {
    setOpenTeamIds((prev) =>
      prev.includes(id) ? prev.filter((tid) => tid !== id) : [...prev, id]
    );
  };

  return (
    <div style={{ padding: '16px' }}>
      <div className={common.sectionTitle}>
        📋 전체 팀 <span className={common.badge}>({sortedTeams.length})</span>
      </div>

      {visibleTeams.map((team) => {
        const isOpen = openTeamIds.includes(team.id);
        const isPinned = team.isMyTeam;

        return (
          <div key={team.id} style={{ marginBottom: '6px' }}>
            <div className={common.toggleRow} onClick={() => toggleTeam(team.id)}>
              <span>
                {isOpen ? '▼' : '▶'} {team.teamName} (📁 {team.projects.length})
              </span>
              {isPinned && <span style={{ marginLeft: '8px' }}>📌</span>}
            </div>

            {isOpen &&
              team.projects.map((project) => (
                <div
                  key={project.id}
                  className={common.subItem}
                  onClick={() => navigate(`/projects/${project.id}`)}
                >
                  📁 {project.name} (🐞 {project.issues.length})
                </div>
              ))}
          </div>
        );
      })}

      {sortedTeams.length > 3 && (
        <div className={common.moreToggle} onClick={() => setShowAll((prev) => !prev)}>
          {showAll ? '− 접기' : '+ 더보기'}
        </div>
      )}
    </div>
  );
};

export default SideNavSummary;
