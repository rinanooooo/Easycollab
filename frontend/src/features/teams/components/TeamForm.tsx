// src/components/TeamForm.tsx
import React, { useState } from 'react';
import api from '../../../api/api';
import type { AxiosError } from 'axios'; // 타입만 쓸 경우에는 type import도 가능



const TeamForm: React.FC = () => {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const res = await api.post('/teams', { name, description });
      setMessage(`✅ 팀 생성 성공 (ID: ${res.data.id})`);
      setName('');
      setDescription('');
    } catch (err: unknown) {
        const axiosError = err as AxiosError<{ message: string }>;
        setMessage('❌ 팀 생성 실패: ' + (axiosError.response?.data?.message || axiosError.message));
      }
  };

  return (
    <div>
      <h2>팀 생성</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>팀 이름</label>
          <input value={name} onChange={(e) => setName(e.target.value)} required />
        </div>
        <div>
          <label>설명</label>
          <textarea value={description} onChange={(e) => setDescription(e.target.value)} />
        </div>
        <button type="submit">생성</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default TeamForm;