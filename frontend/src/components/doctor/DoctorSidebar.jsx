import React, { useState } from 'react';
import { FaSignOutAlt, FaHouseUser, FaUserFriends, FaUserMd, FaUserNurse, FaCalendarAlt, FaHome, FaBars } from 'react-icons/fa';
import { Link, useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const DoctorSidebar = ({ isOpen, toggleSidebar }) => {
  const [showLogoutModal, setShowLogoutModal] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();
  const openLogoutModal = () => setShowLogoutModal(true);
  const closeLogoutModal = () => setShowLogoutModal(false);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('doctorId');
    localStorage.removeItem('role');
    navigate('/admin');
  };

  const getLinkClass = (path) => {
    return location.pathname === path ? 'flex items-center p-5 bg-green-500 select-none' : 'flex items-center p-5 hover:bg-green-600 select-none';
  };

  return (
    <>
      <aside
        className={`bg-green-700 z-20 inset-0 text-white h-screen fixed top-0 left-0 transition-all duration-100 ${isOpen ? 'w-64' : 'w-14'
          }`}
      >
        <div className="flex items-center justify-between py-[35px] pl-[14px] bg-green-900">
          <FaBars onClick={toggleSidebar} className="cursor-pointer ml-1" />
        </div>
        <nav className="p-0">
          <ul className="space-y-1">
            <Link to="/doctor">
              <li className={getLinkClass('/doctor')}>
                <FaHouseUser />
                {isOpen && <span className="ml-2">Dashboard</span>}
              </li>
            </Link>
            <Link to="/doctor/patients">
              <li className={getLinkClass('/doctor/patients')}>
                <FaUserFriends />
                {isOpen && <span className="ml-2">Patients</span>}
              </li>
            </Link>
            <Link to="/doctor/view_doctors">
              <li className={getLinkClass('/doctor/view_doctors')}>
                <FaUserMd />
                {isOpen && <span className="ml-2">Doctors</span>}
              </li>
            </Link>
            <Link to="/doctor/nurses">
              <li className={getLinkClass('/doctor/nurses')}>
                <FaUserNurse />
                {isOpen && <span className="ml-2">Nurses</span>}
              </li>
            </Link>
            <Link to="/doctor/appointments">
              <li className={getLinkClass('/doctor/appointments')}>
                <FaCalendarAlt />
                {isOpen && <span className="ml-2">Appointments</span>}
              </li>
            </Link>
            <Link to="/doctor/home_consultations">
              <li className={getLinkClass('/doctor/home_consultations')}>
                <FaHome />
                {isOpen && <span className="ml-2">Home Consultation</span>}
              </li>
            </Link>
          </ul>
        </nav>
        <footer className="p-0 mt-48 mb-4">
          <li onClick={openLogoutModal} className="flex items-center text-white hover:bg-green-600 p-5 rounded cursor-pointer">
            <FaSignOutAlt />
            {isOpen && <span className="ml-2 select-none">Logout</span>}
          </li>
        </footer>
      </aside>

      {showLogoutModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded shadow-lg">
            <h2 className="text-lg font-semibold mb-4">Confirm Logout</h2>
            <p className="mb-4">Are you sure you want to logout?</p>
            <div className="flex justify-end">
              <button onClick={closeLogoutModal} className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-semibold py-2 px-4 rounded mr-2">
                Cancel
              </button>
              <button onClick={handleLogout} className="bg-red-500 hover:bg-red-600 text-white font-semibold py-2 px-4 rounded">
                Logout
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default DoctorSidebar;