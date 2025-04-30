// LoginPage.tsx
// 로그인 페이지 컴포넌트 - LoginForm을 포함하며 로그인 성공 시 동작을 처리합니다.

import React from 'react';
import LoginForm from '../components/LoginForm';
import { useNavigate } from 'react-router-dom';

const LoginPage: React.FC = () => {
  const navigate = useNavigate();

  // 로그인 성공 시 팀 목록 페이지로 이동
  const handleLoginSuccess = () => {
    navigate('/teams');
  };

  return (
    <div>
      <LoginForm onLoginSuccess={handleLoginSuccess} />
    </div>
  );
};

export default LoginPage;
