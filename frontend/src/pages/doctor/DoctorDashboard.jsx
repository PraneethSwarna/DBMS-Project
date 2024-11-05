import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaUserMd, FaClipboardList, FaUserFriends, FaCalendarAlt, FaUserNurse } from 'react-icons/fa';

const DoctorDashboard = () => {
  const [doctorId, setDoctorId] = useState(localStorage.getItem('doctorId'));
  const [stats, setStats] = useState({
    doctors: 0,
    patients: 0,
    nurses: 0,
    pendingAppointments: 0,
  });
  const [upcomingAppointments, setUpcomingAppointments] = useState([]);
  const [upcomingSurgeries, setUpcomingSurgeries] = useState([]);
  const [upcomingHomeConsultations, setUpcomingHomeConsultations] = useState([]);

  useEffect(() => {
    fetchStats();
    fetchUpcomingAppointments();
    fetchUpcomingSurgeries();
    fetchUpcomingHomeConsultations();
  }, []);

  const fetchStats = async () => {
    try {
      const [doctorsResponse, patientsResponse, nursesResponse, appointmentsResponse] = await Promise.all([
        axios.get('/api/v1/doctor'),
        axios.get('/api/v1/user'),
        axios.get('/api/v1/nurse'),
        axios.get('/api/v1/appointment'),
      ]);

      setStats({
        doctors: doctorsResponse.data.length,
        patients: patientsResponse.data.length,
        nurses: nursesResponse.data.length,
        pendingAppointments: appointmentsResponse.data.filter(appointment => appointment.status === 'Pending').length,
      });
    } catch (error) {
      console.error('Error fetching stats:', error);
    }
  };

  const fetchUpcomingAppointments = async () => {
    try {
      const response = await axios.get(`/api/v1/appointment?doctorId=${doctorId}`);
      setUpcomingAppointments(response.data.filter(appointment => appointment.status === 'scheduled'));
    } catch (error) {
      console.error('Error fetching upcoming appointments:', error);
    }
  };

  const fetchUpcomingSurgeries = async () => {
    try {
      const response = await axios.get(`/api/v1/surgery/doctor/${doctorId}`);
      setUpcomingSurgeries(response.data.filter(surgery => surgery.outcome === null));
    } catch (error) {
      console.error('Error fetching upcoming surgeries:', error);
    }
  };

  const fetchUpcomingHomeConsultations = async () => {
    try {
      const response = await axios.get(`/api/v1/home_consultation/doctor/${doctorId}`);
      setUpcomingHomeConsultations(response.data.filter(consultation => consultation.outcome === null));
    } catch (error) {
      console.error('Error fetching upcoming home consultations:', error);
    }
  };

  return (
    <div className='p-6 select-none'>
      <div className="mb-8">
        <h2 className="text-3xl font-semibold select-none">Welcome, Doctor</h2>
        <p className="text-gray-600 mt-2 select-none">
          At Green Hills, we believe that every patient deserves the highest quality care possible. Our commitment to excellence in healthcare is matched only by our compassion for those we serve.
        </p>
      </div>

      <div className="grid grid-cols-4 gap-4 mb-8 select-none">
        <div className="bg-white shadow-md p-6 rounded-lg text-center">
          <FaUserMd className="text-green-700 text-4xl mb-2" />
          <h3 className="text-2xl font-bold">{stats.doctors}</h3>
          <p className="mt-2 text-gray-600">Doctors</p>
        </div>
        <div className="bg-white shadow-md p-6 rounded-lg text-center">
          <FaUserFriends className="text-blue-700 text-4xl mb-2" />
          <h3 className="text-2xl font-bold">{stats.patients}</h3>
          <p className="mt-2 text-gray-600">Patients</p>
        </div>
        <div className="bg-white shadow-md p-6 rounded-lg text-center">
          <FaUserNurse className="text-yellow-700 text-4xl mb-2" />
          <h3 className="text-2xl font-bold">{stats.nurses}</h3>
          <p className="mt-2 text-gray-600">Nurses</p>
        </div>
        <div className="bg-white shadow-md p-6 rounded-lg text-center">
          <FaClipboardList className="text-red-700 text-4xl mb-2" />
          <h3 className="text-2xl font-bold">{stats.pendingAppointments}</h3>
          <p className="mt-2 text-gray-600">Pending Appointments</p>
        </div>
      </div>

      <div className="mb-8">
        <h3 className="text-2xl font-semibold mb-4">Upcoming Appointments</h3>
        <div className="bg-white shadow-md p-6 rounded-lg">
          {upcomingAppointments.length > 0 ? (
            <table className="min-w-full text-left">
              <thead>
                <tr>
                  <th className="py-2 px-4">Date</th>
                  <th className="py-2 px-4">Time</th>
                  <th className="py-2 px-4">Patient Name</th>
                  <th className="py-2 px-4">Reason</th>
                </tr>
              </thead>
              <tbody>
                {upcomingAppointments.map(appointment => (
                  <tr key={appointment.appointmentID} className="border-b">
                    <td className="py-2 px-4">{appointment.date}</td>
                    <td className="py-2 px-4">{appointment.time}</td>
                    <td className="py-2 px-4">{appointment.patientName}</td>
                    <td className="py-2 px-4">{appointment.reason}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p className="text-gray-600">No upcoming appointments.</p>
          )}
        </div>
      </div>

      <div className="mb-8">
        <h3 className="text-2xl font-semibold mb-4">Upcoming Surgeries</h3>
        <div className="bg-white shadow-md p-6 rounded-lg">
          {upcomingSurgeries.length > 0 ? (
            <table className="min-w-full text-left">
              <thead>
                <tr>
                  <th className="py-2 px-4">Date</th>
                  <th className="py-2 px-4">Patient Name</th>
                  <th className="py-2 px-4">Surgery Type</th>
                  <th className="py-2 px-4">Notes</th>
                </tr>
              </thead>
              <tbody>
                {upcomingSurgeries.map(surgery => (
                  <tr key={surgery.surgeryID} className="border-b">
                    <td className="py-2 px-4">{surgery.surgeryDate}</td>
                    <td className="py-2 px-4">{surgery.patientName}</td>
                    <td className="py-2 px-4">{surgery.surgeryType}</td>
                    <td className="py-2 px-4">{surgery.notes}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p className="text-gray-600">No upcoming surgeries.</p>
          )}
        </div>
      </div>

      <div className="mb-8">
        <h3 className="text-2xl font-semibold mb-4">Upcoming Home Consultations</h3>
        <div className="bg-white shadow-md p-6 rounded-lg">
          {upcomingHomeConsultations.length > 0 ? (
            <table className="min-w-full text-left">
              <thead>
                <tr>
                  <th className="py-2 px-4">Date</th>
                  <th className="py-2 px-4">Time</th>
                  <th className="py-2 px-4">Patient Name</th>
                  <th className="py-2 px-4">Notes</th>
                </tr>
              </thead>
              <tbody>
                {upcomingHomeConsultations.map(consultation => (
                  <tr key={consultation.homeConsultationID} className="border-b">
                    <td className="py-2 px-4">{consultation.consultationDate}</td>
                    <td className="py-2 px-4">{consultation.consultationTime}</td>
                    <td className="py-2 px-4">{consultation.patientName}</td>
                    <td className="py-2 px-4">{consultation.notes}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p className="text-gray-600">No upcoming home consultations.</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default DoctorDashboard;