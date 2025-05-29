import React, { useState } from 'react';
import styles from '../../../shared/styles/ModalForm.module.css';

interface Props {
  isOpen: boolean;
  onClose: () => void;
  onCreate: (title: string, description: string, status: string) => void;
}

const CreateIssueModal: React.FC<Props> = ({ isOpen, onClose, onCreate }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [status, setStatus] = useState('진행중'); // 기본값

  if (!isOpen) return null;

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!title.trim()) return;
    onCreate(title, description, status);
    setTitle('');
    setDescription('');
    setStatus('진행중');
    onClose();
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.modal}>
        <h2 className={styles.title}>이슈 생성</h2>
        <form onSubmit={handleSubmit} className={styles.form}>
          <input
            type="text"
            placeholder="이슈 제목"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
          <textarea
            placeholder="설명 (선택)"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows={4}
          />
          <select value={status} onChange={(e) => setStatus(e.target.value)} required>
            <option value="진행중">진행중</option>
            <option value="완료">완료</option>
            <option value="긴급">긴급</option>
            <option value="보류">보류</option>
          </select>
          <div className={styles.actions}>
            <button type="submit">생성</button>
            <button type="button" onClick={onClose}>취소</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateIssueModal;