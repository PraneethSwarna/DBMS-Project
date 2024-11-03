import { useState } from 'react';
import Sidebar from '../components/Sidebar';
import NavbarDashboard from '../components/NavbarDashboard';
import { Outlet } from 'react-router-dom';

const DoctorLayout = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  return (
    <div className="flex">
      <Sidebar isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
      <div className="flex flex-col w-full">
        <NavbarDashboard isSidebarOpen={isSidebarOpen} />
        <main className="p-6 bg-gray-100 min-h-screen transition-all duration-100">
          <Outlet /> {/* This will render the specific doctor page content */}
        </main>
      </div>
    </div>
  );
};

export default DoctorLayout;
