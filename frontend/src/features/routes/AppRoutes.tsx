// src/features/routes/AppRoutes.tsx
import { Routes, Route } from 'react-router-dom';
import LoginPage from '../auth/LoginPage';
import TeamPage from '../teams/TeamPage';
import DashboardPage from '../dashboard/DashboardPage';

const AppRoutes = () => (
  <Routes>
    <Route path="/" element={<LoginPage />} />
    <Route path="/login" element={<LoginPage />} />
    <Route path="/teams" element={<TeamPage />} />
    <Route path="/dashboard" element={<DashboardPage />} />
  </Routes>
);

export default AppRoutes;
