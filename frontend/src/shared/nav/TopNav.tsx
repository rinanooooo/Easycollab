import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import api from '../../api/api';
import styles from './TopNav.module.css';

const TopNav: React.FC = () => {
  const navigate = useNavigate();
  const { isLoggedIn, logout } = useAuth();

  // ☰ 또는 🔔 중 열린 메뉴
  const [openMenu, setOpenMenu] = useState<'notification' | 'menu' | null>(null);
  const [user, setUser] = useState<{ nickname: string; imageUrl?: string } | null>(null);
  const [unreadCount, setUnreadCount] = useState(3); // 실제 알림 수는 나중에 API 연동

  // 더미 알림 데이터
  const dummyNotifications = [
    {
      id: 1,
      time: '09:47',
      content: "[프로젝트 Alpha] 새로운 이슈가 등록되었습니다: '회원가입 시 500 에러'",
    },
    {
      id: 2,
      time: '08:30',
      content: "[프로젝트 Bravo] '로그인 API 속도 개선' 작업이 완료되었습니다.",
    },
    {
      id: 3,
      time: '2025.06.01',
      content: "[프로젝트 Alpha] '프론트엔드 스타일 리팩토링'에 대한 댓글이 달렸습니다.",
    },
    {
      id: 4,
      time: '2025.05.31',
      content: "[프로젝트 Charlie] 새 프로젝트가 생성되었습니다.",
    },
    {
      id: 5,
      time: '2025.05.30',
      content: "[프로젝트 Bravo] 'JWT 토큰 만료 로직 수정' 이슈가 할당되었습니다.",
    },
  ];

  // 로그인 유저 정보 가져오기
  useEffect(() => {
    if (!isLoggedIn) return;
    const fetchUser = async () => {
      try {
        const res = await api.get('/user/me');
        setUser({ nickname: res.data.nickname, imageUrl: res.data.imageUrl });
      } catch (err) {
        console.error('유저 정보 가져오기 실패:', err);
      }
    };
    fetchUser();
  }, [isLoggedIn]);

  // 외부 클릭 시 드롭다운 닫기
  useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      const target = e.target as HTMLElement;
      if (!target.closest(`.${styles['topNav-userSection']}`)) {
        setOpenMenu(null);
      }
    };
    document.addEventListener('click', handleClickOutside);
    return () => document.removeEventListener('click', handleClickOutside);
  }, []);

  // 로고 클릭
  const handleLogoClick = () => {
    if (isLoggedIn) {
      navigate('/dashboard');
    } else {
      window.location.reload();
    }
  };

  // 마이페이지 이동
  const goToMyPage = () => {
    setOpenMenu(null);
    navigate('/mypage');
  };

  // 로그아웃
  const handleLogout = () => {
    setOpenMenu(null);
    logout();
  };

  return (
    <header className={styles['topNav-header']}>
      <div className={styles['topNav-logo']} onClick={handleLogoClick}>
        EasyCollab
      </div>

      {isLoggedIn && (
        <div className={styles['topNav-userSection']}>
          {/* 사용자 이름 */}
          <span className={styles['topNav-nickname']}>{user?.nickname}님</span>

          {/* 🔔 알림 */}
          <div className={styles['topNav-notification-wrapper']}>
            <div
              className={styles['topNav-bell-icon']}
              onClick={() =>
                setOpenMenu((prev) => (prev === 'notification' ? null : 'notification'))
              }
              title={`${unreadCount}개의 읽지 않은 알림`}
            >
              🔔
              {unreadCount > 0 && <span className={styles['topNav-badge']} />}
            </div>

            {openMenu === 'notification' && (
              <div className={styles['topNav-notification-dropdown']}>
                <div>
                  <b>최근 알림</b>
                  <span className={styles['topNav-notification-isRead']}>
                    {`${unreadCount}개의 읽지 않은 알림`}
                  </span>
                </div>
                <ul>
                  {dummyNotifications.slice(0, 3).map((n) => (
                    <li key={n.id}>
                      <div className={styles['topNav-notification-time']}>{n.time}</div>
                      <div className={styles['topNav-notification-text']}>{n.content}</div>
                    </li>
                  ))}
                </ul>

                {dummyNotifications.length > 3 && (
                  <div className={styles['topNav-see-more']}>
                    <a href="/mypage/notifications">최근 알림 목록 더보기</a>
                  </div>
                )}
              </div>
            )}
          </div>

          {/* ☰ 메뉴 */}
          <div
            className={styles['topNav-menu-icon']}
            onClick={() =>
              setOpenMenu((prev) => (prev === 'menu' ? null : 'menu'))
            }
          >
            <span className={styles['topNav-icon']}>☰</span>
          </div>

          {openMenu === 'menu' && (
            <div className={styles['topNav-dropdown-menu']}>
              <div className={styles['topNav-dropdown-item']} onClick={goToMyPage}>
                마이페이지
              </div>
              <div className={styles['topNav-dropdown-item']} onClick={handleLogout}>
                로그아웃
              </div>
            </div>
          )}
        </div>
      )}
    </header>
  );
};

export default TopNav;