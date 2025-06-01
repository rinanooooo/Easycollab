import React from 'react';

const MyIssuesSection = () => {
  const myIssues = [
    { id: 1, title: '버그 수정 필요', badge: '🔥' },
    { id: 2, title: '기능 추가 요청', badge: '📌' },
  ];

  return (
    <section>
      <div style={{
        display: 'flex', justifyContent: 'space-between',
        alignItems: 'center', marginBottom: '16px'
      }}>
        <h2 style={{ fontSize: '18px', fontWeight: 'bold' }}>내 이슈</h2>
      </div>
      <ul>
        {myIssues.map((issue) => (
          <li key={issue.id}
              style={{
                padding: '12px 16px',
                background: '#fff7f7',
                borderRadius: '8px',
                marginBottom: '8px',
                cursor: 'pointer'
              }}>
            {issue.badge} {issue.title}
          </li>
        ))}
      </ul>
    </section>
  );
};

export default MyIssuesSection;
