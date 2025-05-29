// src/App.tsx
import AppRoutes from './features/routes/AppRoutes';
import TopNav from './shared/nav/TopNav';

function App() {
  return (
    <>
      <TopNav />
      <AppRoutes />
    </>
  );
}

export default App;