import React from 'react';
import { Outlet } from 'react-router-dom';

const MainLayout = () => {
  return (
    <div>
      <header>
        {/* Add any navbar/header here */}
      </header>
      <main>
        <Outlet />
      </main>
    </div>
  );
};

export default MainLayout;
