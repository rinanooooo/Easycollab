import React from 'react';

import UserInfoCard from './components/UserInfoCard';
import MyTeamsSection from './components/MyTeamsSection';
import MyProjectsSection from './components/MyProjectsSection';
import MyIssuesSection from './components/MyIssuesSection';

import styles from './MyPage.module.css';

const MyPage = () => {
  return (
    <div className={styles.container}>
      <h1 className={styles.title}>마이페이지</h1>
      <UserInfoCard />
      <MyTeamsSection />
      <MyProjectsSection />
      <MyIssuesSection />
    </div>
  );
};

export default MyPage;
