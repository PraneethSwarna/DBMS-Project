import { useState } from 'react';
import { FaUserMd, FaCalendarAlt, FaClipboardList, FaBars, FaUserFriends, FaSignOutAlt } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

const Sidebar = ({ isOpen, toggleSidebar }) => {

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('doctorId');
    localStorage.removeItem('role');
    navigate('/admin');
  };

  return (
    <aside
      className={`bg-green-700 text-white h-screen fixed top-0 left-0 transition-all duration-100 ${isOpen ? 'w-64' : 'w-14'
        }`}
    >
      <div className="flex items-center justify-between py-[35px] pl-[14px] bg-green-900">
        <FaBars onClick={toggleSidebar} className="cursor-pointer ml-1" />
      </div>
      <nav className="p-0">
        <ul className="space-y-1">
          <li className="flex items-center p-5 hover:bg-green-600 select-none">
            <FaUserMd />
            {isOpen && <span className="ml-2">Doctors</span>}
          </li>
          <li className="flex items-center p-5 hover:bg-green-600 select-none">
            <FaUserFriends />
            {isOpen && <span className="ml-2">Patients</span>}
          </li>
          <li className="flex items-center p-5 hover:bg-green-600 select-none">
            <FaCalendarAlt />
            {isOpen && <span className="ml-2">Appointments</span>}
          </li>
          <li className="flex items-center p-5 hover:bg-green-600 select-none">
            <FaClipboardList />
            {isOpen && <span className="ml-2">Reports</span>}
          </li>
        </ul>
      </nav>
      <footer className="p-0 mt-80">
        <li onClick={handleLogout} className="flex items-center text-white hover:bg-green-600 p-5 rounded">
          <FaSignOutAlt />
          {isOpen && <span className="ml-2 select-none">Logout</span>}
        </li>
      </footer>
    </aside>
  );
};

export default Sidebar;
