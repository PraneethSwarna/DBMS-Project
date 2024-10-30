import { useState } from 'react';
import Sidebar from '../components/Sidebar';
import Navbar from '../components/Navbar';
import DoctorDashboard from '../components/DoctorDashboard';

function App() {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  return (
    <div className="flex">
      <Sidebar isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
      <div className="flex flex-col w-full">
        <Navbar isSidebarOpen={isSidebarOpen} />
        <main className="p-6 bg-gray-100 min-h-screen">
          <DoctorDashboard isSidebarOpen={isSidebarOpen} />
        </main>
      </div>
    </div>
  );
}

export default App;
