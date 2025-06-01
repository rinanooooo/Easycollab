// src/features/routes/AppRoutes.tsx
import { Routes, Route } from 'react-router-dom';
import LoginPage from '../auth/LoginPage';
import MyPage from '../user/MyPage';
import TeamPage from '../teams/TeamPage';
import DashboardPage from '../dashboard/DashboardPage';
import ProjectDetailPage from '../projects/ProjectDetailPage';
import IssueDetailPage from '../issues/IssueDetailPage';
import SignupPage  from '../auth/SignupPage';

const AppRoutes = () => (
  <Routes>
    <Route path="/" element={<LoginPage />} />
    <Route path="/login" element={<LoginPage />} />
    <Route path="/signup" element={<SignupPage />} />
    <Route path="/mypage" element={<MyPage />} />
    <Route path="/teams" element={<TeamPage />} />
    <Route path="/dashboard" element={<DashboardPage />} />
    <Route path="/projects/:projectId" element={<ProjectDetailPage />} />
    <Route path="/issues/:id" element={<IssueDetailPage />} />
  </Routes>
);

export default AppRoutes;
