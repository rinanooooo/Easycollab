// App.tsx
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';

const App: React.FC = () => {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      {/* 추후 팀 목록 페이지 추가 예정 */}
    </Routes>
  );
};

export default App;
