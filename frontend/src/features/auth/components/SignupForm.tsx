import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../../api/api';
import styles from './LoginForm.module.css'; 

const SignupForm: React.FC = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    loginId: '',
    username: '',
    nickname: '',
    email: '',
    password: '',
  });

  const [showModal, setShowModal] = useState(false); // 모달 상태

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post('/auth/signup', form);

      // 1. 모달 띄우고
      setShowModal(true);

      // 2. 2초 후 로그인 페이지로 이동
      setTimeout(() => {
        setShowModal(false);
        navigate('/');
      }, 2000);
    } catch (err) {
      alert('회원가입 실패. 정보를 확인해주세요.');
      console.error(err);
    }
  };

  return (
    <>
      <form className={styles['login-form']} onSubmit={handleSubmit}>
        <h1>회원가입</h1>
        <div className={styles.avatar}></div>

        <div className={styles['form-group']}>
          <label htmlFor="loginId">아이디</label>
          <input
            type="text"
            id="loginId"
            name="loginId"
            value={form.loginId}
            onChange={handleChange}
            required
            placeholder="아이디를 입력하세요"
          />
        </div>

        <div className={styles['form-group']}>
          <label htmlFor="username">이름</label>
          <input
            type="text"
            id="username"
            name="username"
            value={form.username}
            onChange={handleChange}
            required
            placeholder="이름을 입력하세요"
          />
        </div>

        <div className={styles['form-group']}>
          <label htmlFor="nickname">닉네임</label>
          <input
            type="text"
            id="nickname"
            name="nickname"
            value={form.nickname}
            onChange={handleChange}
            required
            placeholder="닉네임을 입력하세요"
          />
        </div>

        <div className={styles['form-group']}>
          <label htmlFor="email">이메일</label>
          <input
            type="email"
            id="email"
            name="email"
            value={form.email}
            onChange={handleChange}
            required
            placeholder="이메일 주소를 입력하세요"
          />
        </div>

        <div className={styles['form-group']}>
          <label htmlFor="password">비밀번호</label>
          <input
            type="password"
            id="password"
            name="password"
            value={form.password}
            onChange={handleChange}
            required
            placeholder="비밀번호를 입력하세요"
          />
        </div>

        <button type="submit">회원가입</button>
      </form>

      {/* 모달 */}
      {showModal && (
        <div className={styles.modalOverlay}>
          <div className={styles.modalContent}>
            🎉 회원가입이 완료되었습니다!
          </div>
        </div>
      )}
    </>
  );
};

export default SignupForm;
