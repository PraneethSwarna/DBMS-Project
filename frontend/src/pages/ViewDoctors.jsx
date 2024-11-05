import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const ViewDoctors = () => {
  const [doctors, setDoctors] = useState([]);
  const [filteredDoctors, setFilteredDoctors] = useState([]);
  const [specialtyFilter, setSpecialtyFilter] = useState('');
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchDoctors();
  }, []);

  useEffect(() => {
    applyFilters();
  }, [specialtyFilter, searchTerm]);

  const fetchDoctors = async () => {
    try {
      const response = await axios.get('/api/v1/doctor');
      setDoctors(response.data);
      setFilteredDoctors(response.data);
    } catch (error) {
      console.error('Error fetching doctors:', error);
    }
  };

  const applyFilters = () => {
    let filtered = doctors;

    if (specialtyFilter) {
      filtered = filtered.filter(doctor => doctor.specialty === specialtyFilter);
    }

    if (searchTerm) {
      filtered = filtered.filter(doctor =>
        `${doctor.firstName} ${doctor.lastName}`.toLowerCase().includes(searchTerm.toLowerCase()) ||
        doctor.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
        doctor.officeNumber.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    setFilteredDoctors(filtered);
  };

  return (
    <div className="p-6">
      <div className="mb-8">
        <h2 className="text-3xl font-semibold select-none">View Doctors</h2>
        <p className="text-gray-600 mt-2 select-none">
          Use the filters below to search for doctors by specialty, name, email, or office number.
        </p>
      </div>

      <div className="mb-4">
        <label className="block text-gray-700 mb-2">Filter by Specialty:</label>
        <select
          value={specialtyFilter}
          onChange={(e) => setSpecialtyFilter(e.target.value)}
          className="border p-2 rounded w-full"
        >
          <option value="">All Specialties</option>
          <option value="Cardiology">Cardiology</option>
          <option value="Dermatology">Dermatology</option>
          <option value="Neurology">Neurology</option>
          <option value="Pediatrics">Pediatrics</option>
          <option value="Radiology">Radiology</option>
          {/* Add more specialties as needed */}
        </select>
      </div>

      <div className="mb-4">
        <label className="block text-gray-700 mb-2">Search:</label>
        <input
          type="text"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          placeholder="Search by name, email, or office number"
          className="border p-2 rounded w-full"
        />
      </div>

      <div className="bg-white shadow-md p-6 rounded-lg">
        {filteredDoctors.length > 0 ? (
          <table className="min-w-full text-left">
            <thead>
              <tr className='text-center'>
                <th className="py-2 px-4">No.</th>
                <th className="py-2 px-4">Name</th>
                <th className="py-2 px-4">Specialty</th>
                <th className="py-2 px-4">Email</th>
                <th className="py-2 px-4">Office Number</th>
                <th className="py-2 px-4">Profile</th>
              </tr>
            </thead>
            <tbody>
              {filteredDoctors.map((doctor, Index) => (
                <tr key={doctor.doctorID} className="border-b text-center">
                  <td className="py-2 px-4">{Index+1}</td>
                  <td className="py-2 px-4">{doctor.firstName} {doctor.lastName}</td>
                  <td className="py-2 px-4">{doctor.specialty}</td>
                  <td className="py-2 px-4">{doctor.email}</td>
                  <td className="py-2 px-4">{doctor.officeNumber}</td>
                  <td className="py-2 px-4">
                    <Link to={`/doctor/view_doctors/${doctor.doctorID}`} className="text-blue-500 hover:underline">
                      View Profile
                    </Link>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p className="text-gray-600">No doctors found.</p>
        )}
      </div>
    </div>
  );
};

export default ViewDoctors;