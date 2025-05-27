// src/components/TeamList.tsx
import React, { useEffect, useState } from 'react';
import api from '../api/api';

interface Team {
  id: number;
  name: string;
  description: string;
}

const TeamList: React.FC = () => {
  const [teams, setTeams] = useState<Team[]>([]);
  const [error, setError] = useState('');
//   const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchTeams = async () => {
      try {
        const res = await api.get('/teams');
        setTeams(res.data);
      } catch (err) {
        console.error(err);
        setError('팀 목록을 불러오지 못했습니다.');
      }      
    };

    fetchTeams();
  }, []);

  return (
    <div>
      <h2>📋 내 팀 목록</h2>
      {error && <p>{error}</p>}
      <ul>
        {teams.map((team) => (
          <li key={team.id}>
            <strong>{team.name}</strong>: {team.description}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TeamList;