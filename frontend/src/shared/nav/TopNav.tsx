import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth'; // 커스텀 훅

const TopNav: React.FC = () => {
  const navigate = useNavigate();
  const { isLoggedIn, logout } = useAuth();
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const handleLogoClick = () => {
    if (isLoggedIn) {
      navigate('/dashboard');
    } else {
      window.location.reload();
    }
  };

  const toggleDropdown = () => {
    setDropdownOpen((prev) => !prev);
  };

  const goToMyPage = () => {
    setDropdownOpen(false);
    navigate('/mypage');
  };

  const handleLogout = () => {
    setDropdownOpen(false);
    logout();
  };

  return (
    <header style={headerStyle}>
      <div style={logoStyle} onClick={handleLogoClick}>
        EasyCollab
      </div>

      {isLoggedIn && (
        <div style={menuContainerStyle}>
          <div style={menuIconStyle} onClick={toggleDropdown}>
  <span style={iconStyle}>☰</span>
</div>
          {dropdownOpen && (
            <div style={dropdownStyle}>
              <div style={dropdownItemStyle} onClick={goToMyPage}>
                마이페이지
              </div>
              <div style={dropdownItemStyle} onClick={handleLogout}>
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

// 스타일 정의
const headerStyle: React.CSSProperties = {
  display: 'flex',
  justifyContent: 'space-between',
  alignItems: 'center',
  height: '64px',
  padding: '0 24px',
  backgroundColor: '#ffffff',
  borderBottom: '1px solid #eee',
  boxShadow: '0 2px 4px rgba(0,0,0,0.05)',
};

const logoStyle: React.CSSProperties = {
  fontSize: '20px',
  fontWeight: 'bold',
  color: '#6366f1',
  cursor: 'pointer',
};

const menuContainerStyle: React.CSSProperties = {
  position: 'relative',
  cursor: 'pointer',
};

const menuIconStyle: React.CSSProperties = {
  fontSize: '20px',
  cursor: 'pointer',
  padding: '4px 8px',
  border: 'none',
  backgroundColor: 'transparent',
};

const dropdownStyle: React.CSSProperties = {
  position: 'absolute',
  top: '48px',
  right: 0,
  backgroundColor: '#ffffff',
  border: '1px solid #ddd',
  borderRadius: '6px',
  boxShadow: '0 4px 10px rgba(0,0,0,0.1)',
  zIndex: 1000,
  width: '120px',
};

const dropdownItemStyle: React.CSSProperties = {
  padding: '10px',
  fontSize: '14px',
  borderBottom: '1px solid #f0f0f0',
  cursor: 'pointer',
};

const iconStyle: React.CSSProperties = {
  fontSize: '22px',
  color: '#333', 
};