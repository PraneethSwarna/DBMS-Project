import React, { useState, useEffect } from 'react';
import { FaUserCircle } from 'react-icons/fa';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const DoctorNavbar = () => {
  const [doctorName, setDoctorName] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchDoctorName = async () => {
      const doctorId = localStorage.getItem('doctorId');
      try {
        const response = await axios.get(`/api/v1/doctor/${doctorId}`);
        const doctor = response.data;
        setDoctorName(`${doctor.firstName} ${doctor.lastName}`);
      } catch (error) {
        console.error('Error fetching doctor name:', error);
      }
    };

    fetchDoctorName();
  }, []);

  const handleProfileClick = () => {
    navigate('/doctor/profile');
  };

  return (
    <nav className="bg-white shadow-md p-4 flex justify-between items-center fixed top-0 left-0 w-full z-10">
      <h1 className="text-[36px] font-semibold text-green-700 ml-[650px] select-none">Artemis Hospital</h1>
      <div className="flex items-center space-x-4">
        <span className="text-gray-600 select-none">Welcome, {doctorName}</span>
        <FaUserCircle
          className="text-green-700 text-2xl cursor-pointer"
          onClick={handleProfileClick}
          title="Go to Profile"
        />
      </div>
    </nav>
  );
};

export default DoctorNavbar;
