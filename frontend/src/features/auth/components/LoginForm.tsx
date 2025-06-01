// LoginForm.tsx

import React, { useState, } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../../api/api';
import type { AxiosError } from 'axios';
import styles from './LoginForm.module.css';

interface LoginFormProps {
  onLoginSuccess: () => void;
}

const LoginForm: React.FC<LoginFormProps> = ({ onLoginSuccess }) => {
  const [loginId, setLoginId] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await api.post('/auth/login', {
        loginId,
        password,
      });
      const { token } = response.data;
      localStorage.setItem('token', token);
      onLoginSuccess();
    } catch (error) {
      const axiosError = error as AxiosError<{ message: string }>;
      console.error('로그인 실패:', axiosError.response?.data?.message || axiosError.message);
      alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
    }
  };

  return (
    <form className={styles['login-form']} onSubmit={handleLogin}>
      <h1>Easycollab</h1>
      <div className={styles.avatar}></div>
      <div className={styles['form-group']}>
        <label htmlFor="loginId">아이디</label>
        <input
          type="text"
          id="loginId"
          value={loginId}
          onChange={(e) => setLoginId(e.target.value)}
          required
          placeholder="아이디"
        />
      </div>
      <div className={styles['form-group']}>
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
      <div className={styles['form-actions']}>
        <button type="submit">로그인</button>
        <div className={styles['form-signup-now']} >
          <span onClick={() => navigate('/signup')}>
              작은 팀, 큰 가능성. 지금 바로 시작해보세요.
          </span>
        </div>
      </div>
    </form>
  );
};

export default LoginForm;