import React, { useState } from 'react';
import styles from '../../../shared/styles/ModalForm.module.css';

interface Props {
  isOpen: boolean;
  onClose: () => void;
  onCreate: (teamName: string) => void;
}

const CreateTeamModal: React.FC<Props> = ({ isOpen, onClose, onCreate }) => {
  const [teamName, setTeamName] = useState('');

  if (!isOpen) return null;

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!teamName.trim()) return;
    onCreate(teamName);
    setTeamName('');
    onClose();
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h2 className={styles.title}>팀 생성</h2>
        <form onSubmit={handleSubmit} className={styles.form}>
          <input
            type="text"
            placeholder="팀 이름"
            value={teamName}
            onChange={(e) => setTeamName(e.target.value)}
            required
          />
          <div className={styles.actions}>
            <button type="submit">생성</button>
            <button type="button" onClick={onClose}>취소</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateTeamModal;