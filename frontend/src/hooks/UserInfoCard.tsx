// src/hooks/useMyInfo.ts
import { useState, useEffect } from 'react';
import api from '../api/api';
import { User } from '../shared/types/User';

export const useMyInfo = () => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<unknown>(null);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const res = await api.get<User>('/user/me');
        setUser(res.data);
      } catch (err) {
        setError(err);
        console.error('내 정보 불러오기 실패:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, []);

  return { user, loading, error };
};