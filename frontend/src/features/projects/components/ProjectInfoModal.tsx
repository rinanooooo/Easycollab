import React from 'react';
import { useNavigate } from 'react-router-dom';
// import styles from './ProjectModal.module.css';
import styles from '../../../shared/styles/ModalForm.module.css';
import common from '../../../shared/styles/Common.module.css';

import { Project as ProjectType  } from '../../../shared/types/project'; 

interface Props {
  project: ProjectType | null;
  onClose: () => void;
  onClick?: () => void;
  onIssueClick?: (issueId: number) => void;
}

const ProjectInfoModal: React.FC<Props> = ({ project, onClose, onIssueClick }) => {
  const navigate = useNavigate();

  if (!project) return null;

  const handleProjectClick = () => {
    navigate(`/projects/${project.id}`);
    onClose();
  };

  const handleIssueClick = (issueId: number) => {
    navigate(`/issues/${issueId}`);
    onClose();
  };

  return (
    <div className={styles.overlay} onClick={onClose}>
      <div className={styles.modal} onClick={(e) => e.stopPropagation()}>
        <h2 className={styles.projectTitle} onClick={handleProjectClick} style={{ cursor: 'pointer' }}>
          {project.title}
        </h2>
        <p>상태: {project.status}</p>
        <p>이슈: {project.issues.length}건</p>

        <ul className={styles.issueList}>
          {project.issues.map((issue) => (
            <li
              key={issue.id}
              className={common.listItem}
              onClick={(e) => {
                  e.stopPropagation();
                  onIssueClick?.(issue.id);
                  handleIssueClick(issue.id);
                }}
            >
              🐞 {issue.title}
            </li>
          ))}
        </ul>

        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
};

export default ProjectInfoModal;