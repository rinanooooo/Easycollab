import React from 'react';
import LoginForm from './components/LoginForm';
import { useNavigate } from 'react-router-dom';
import styles from './components/LoginForm.module.css';

const LoginPage: React.FC = () => {
  const navigate = useNavigate();

  const handleLoginSuccess = () => {
    navigate('/teams');
  };

  return (
    <div className={styles['login-wrapper']}>
      <LoginForm onLoginSuccess={handleLoginSuccess} />
    </div>
  );
};

export default LoginPage;