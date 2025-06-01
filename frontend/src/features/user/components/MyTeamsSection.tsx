import React from 'react';

const MyTeamsSection = () => {
  const myTeams = [
    { id: 1, name: 'Team Alpha' },
    { id: 2, name: 'Team Beta' },
    { id: 3, name: 'Team Gamma' },
  ];

  return (
    <section style={{ marginBottom: '32px' }}>
      <div style={{
        display: 'flex', justifyContent: 'space-between',
        alignItems: 'center', marginBottom: '16px'
      }}>
        <h2 style={{ fontSize: '18px', fontWeight: 'bold' }}>내 팀</h2>
      </div>
      <ul>
        {myTeams.map((team) => (
          <li key={team.id}
              style={{
                padding: '12px 16px',
                background: '#f9f9f9',
                borderRadius: '8px',
                marginBottom: '8px',
                cursor: 'pointer'
              }}>
            {team.name}
          </li>
        ))}
      </ul>
    </section>
  );
};

export default MyTeamsSection;
