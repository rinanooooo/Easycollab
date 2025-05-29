import React, { useEffect, useState } from 'react';
import SideNav from '../../shared/layout/SideNav';
import SideNavSummary from '../../shared/layout/SideNavSummary';
import RightPanel from '../../shared/layout/RightPanel';
import RightPanelSummary from '../../shared/layout/RightPanelSummary';
import ProjectCardList from '../../shared/cards/ProjectCardList';

const DashboardPage: React.FC = () => {
  const [isMobile, setIsMobile] = useState(window.innerWidth < 1024);

  useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth < 1024);
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return (
    <div>
      {isMobile && (
        <>
          <SideNavSummary />      {/* 모바일 요약용 사이드 */}
          <RightPanelSummary />   {/* 모바일 요약용 우측 */}
        </>
      )}

      <div style={containerStyle}>
        {!isMobile && <SideNav />}   {/* 데스크탑 전용 */}
        <main style={mainContentStyle}>
          <ProjectCardList />
        </main>
        {!isMobile && <RightPanel />}      {/* 데스크탑 전용 */}
      </div>
    </div>
  );
};

export default DashboardPage;

const containerStyle: React.CSSProperties = {
  display: 'flex',
  height: 'calc(100vh - 64px)',
  backgroundColor: '#f9f9f9',
};

const mainContentStyle: React.CSSProperties = {
  flex: 1,
  padding: '24px',
  overflowY: 'auto',
};
