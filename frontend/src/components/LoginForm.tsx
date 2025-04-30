// LoginForm.tsx
// 로그인 폼 컴포넌트 - 사용자로부터 이메일과 비밀번호를 입력받아 로그인 요청을 수행합니다.

import React, { useState } from 'react';
import api from '../api/api';
import type { AxiosError } from 'axios';
import '../styles/LoginForm.css';

interface LoginFormProps {
  onLoginSuccess: () => void;
}

const LoginForm: React.FC<LoginFormProps> = ({ onLoginSuccess }) => {
  // 상태 변수: 사용자 입력 값
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  // 로그인 요청 처리 함수
  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await api.post('/auth/login', {
        username: email,
        password,
      });

      const { token } = response.data;

      // 토큰을 로컬 스토리지에 저장
      localStorage.setItem('token', token);

      // 로그인 성공 콜백 실행
      onLoginSuccess();
     } catch (error) {
        const axiosError = error as AxiosError<{ message: string }>;
        console.error('로그인 실패:', axiosError.response?.data?.message || axiosError.message);
        alert('로그인에 실패했습니다. 이메일과 비밀번호를 확인해주세요.');
      }
  };

  return (
    <form className="login-form" onSubmit={handleLogin}>
        <h1>Easycollab</h1>
      <div className="form-group">
        <label htmlFor="email">이메일</label>
        <input
          type="text"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          placeholder="아이디 또는 이메일"
        />
      </div>
      <div className="form-group">
        <label htmlFor="password">비밀번호</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          placeholder="비밀번호를 입력하세요"
        />
      </div>
      <button type="submit">로그인</button>
    </form>
  );
};

export default LoginForm;
