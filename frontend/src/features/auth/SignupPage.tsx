// SignupPage.tsx
import React from 'react';
import SignupForm from './components/SignupForm';

const SignupPage: React.FC = () => {
  return (
    <div style={{ display: 'flex', justifyContent: 'center', padding: '40px' }}>
      <SignupForm />
    </div>
  );
};

export default SignupPage;
