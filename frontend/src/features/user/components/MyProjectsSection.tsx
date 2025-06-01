import React from 'react';

const MyProjectsSection = () => {
  const myProjects = [
    { id: 1, name: '프로젝트 A' },
    { id: 2, name: '프로젝트 B' },
  ];

  return (
    <section style={{ marginBottom: '32px' }}>
      <div style={{
        display: 'flex', justifyContent: 'space-between',
        alignItems: 'center', marginBottom: '16px'
      }}>
        <h2 style={{ fontSize: '18px', fontWeight: 'bold' }}>내 프로젝트</h2>
      </div>
      <ul>
        {myProjects.map((project) => (
          <li key={project.id}
              style={{
                padding: '12px 16px',
                background: '#f0f0ff',
                borderRadius: '8px',
                marginBottom: '8px',
                cursor: 'pointer'
              }}>
            {project.name}
          </li>
        ))}
      </ul>
    </section>
  );
};

export default MyProjectsSection;
