import React from 'react';
import { useMyInfo } from '../../../hooks/UserInfoCard';

const UserInfoCard = () => {
  const { user, loading, error } = useMyInfo();

  if (loading) return <div>로딩 중...</div>;
  if (error || !user) return <div>사용자 정보를 불러올 수 없습니다.</div>;

  return (
    <div style={{
      padding: '16px',
      background: '#f5f5f5',
      borderRadius: '12px',
      marginBottom: '24px'
    }}>
      <h2>{user.nickname} ({user.username}) 님</h2>
      <p>Email: {user.email}</p>
      <p>아이디: {user.loginId}</p>
      <p>역할: {user.role}</p>
    </div>
  );
};

export default UserInfoCard;