// src/pages/TeamPage.tsx
import React from 'react';
import TeamForm from '../components/TeamForm';
import TeamList from '../components/TeamList';

const TeamPage: React.FC = () => {
  return (
    <div style={{ maxWidth: 600, margin: '0 auto', padding: 20 }}>
      <h1>EasyCollab - 내 팀 관리</h1>
      <TeamForm />
      <hr />
      <TeamList />
    </div>
  );
};

export default TeamPage;