// App.tsx
import React from 'react';
import { Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import TeamPage from './pages/TeamPage';

const App: React.FC = () => {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/me" element={<TeamPage />} />
    </Routes>
  );
};

export default App;
