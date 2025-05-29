// src/hooks/useAuth.ts
import { useCallback } from 'react';

export const useAuth = () => {
  const token = localStorage.getItem('token');
  const isLoggedIn = Boolean(token);

  const logout = useCallback(() => {
    localStorage.removeItem('token');
    window.location.href = '/login'; // 로그아웃 후 로그인 페이지로 이동
  }, []);

  return { isLoggedIn, token, logout };
};