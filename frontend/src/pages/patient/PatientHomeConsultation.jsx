import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import Designer from '../../assets/Hospital_3.png'; // Update with the correct path to your image

const HomeConsultation = () => {
    const [consultations, setConsultations] = useState([]);
    const [doctors, setDoctors] = useState([]);
    const [selectedDoctor, setSelectedDoctor] = useState('');
    const [consultationDate, setConsultationDate] = useState('');
    const [consultationTime, setConsultationTime] = useState('');
    const [expandedConsultationId, setExpandedConsultationId] = useState(null);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [consultationId, setConsultationId] = useState("");
    const patientId = localStorage.getItem('userId'); // Replace with actual patient ID

    useEffect(() => {
        fetchConsultations();
        fetchDoctors();
    }, []);

    const fetchConsultations = async () => {
        try {
            const response = await axios.get(`/api/v1/home_consultation/patient/${patientId}`);
            setConsultations(response.data);
        } catch (error) {
            console.error('Error fetching consultations:', error);
        }
    };

    const fetchDoctors = async () => {
        try {
            const response = await axios.get('/api/v1/doctor');
            setDoctors(response.data);
        } catch (error) {
            console.error('Error fetching doctors:', error);
        }
    };

    const createConsultation = async () => {
        try {
            const newConsultation = {
                consultationDate,
                consultationTime,
                doctorID: selectedDoctor,
                patientID: patientId,
                outcome: null,
                notes: null
            };
            await axios.post('/api/v1/home_consultation', newConsultation);
            fetchConsultations(); // Fetch consultations again to get the newly created consultation with its ID
        } catch (error) {
            console.error('Error creating consultation:', error);
        }
    };

    const deleteConsultation = async (consultationId) => {
        try {
            console.log(`Deleting consultation with ID: ${consultationId}`);
            await axios.delete(`/api/v1/home_consultation/${consultationId}`);
            fetchConsultations();
        } catch (error) {
            console.error('Error deleting consultation:', error);
        }
        closeDeleteModal();
    };

    const toggleDetails = (consultationId) => {
        setExpandedConsultationId(expandedConsultationId === consultationId ? null : consultationId);
    };

    const openDeleteModal = async (consultationId) => {
        setShowDeleteModal(true);
        setConsultationId(consultationId);
    };

    const closeDeleteModal = () => {
        setShowDeleteModal(false);
    };

    return (
        <div className='bg-transparent min-h-screen relative'>
            <img
                src={Designer}
                alt="designer"
                className="absolute inset-0 z-[-10] w-full h-full object-cover object-top"
            />
            <div className="absolute inset-0 z-[-5] bg-sky-800 opacity-40" />
            <div className="container min-w-full min-h-screen rounded-xl z-10 relative bg-transparent mx-auto p-4 backdrop-brightness-50 ">
                <section className="mb-8 mt-20">
                    <h2 className="text-[60px] text-white text-center header-font mb-2">Book a Home Consultation!</h2>
                    <p className="text-gray-300 text-center mb-4">Fill in the details below to book a home consultation. You can view or delete your consultations below.</p>
                    <div className="flex flex-col items-center">
                        <div className='flex items-center space-x-20'>
                            <div className='inline-grid'>
                                <input
                                    type="date"
                                    value={consultationDate}
                                    onChange={(e) => setConsultationDate(e.target.value)}
                                    className="mb-4 p-2 rounded"
                                />
                                <input
                                    type="time"
                                    value={consultationTime}
                                    onChange={(e) => setConsultationTime(e.target.value)}
                                    className="mb-4 p-2 rounded"
                                />
                                <select
                                    value={selectedDoctor}
                                    onChange={(e) => setSelectedDoctor(e.target.value)}
                                    className="mb-4 p-2 rounded"
                                >
                                    <option value="">Select Doctor</option>
                                    {doctors.map(doctor => (
                                        <option key={doctor.doctorID} value={doctor.doctorID}>
                                            {doctor.firstName} {doctor.lastName} - {doctor.specialty}
                                        </option>
                                    ))}
                                </select>
                                {selectedDoctor && (
                                    <Link to={`/patient/doctor/${selectedDoctor}`} className="text-blue-300 justify-right mb-4 hover:underline">
                                        View Doctor Profile
                                    </Link>
                                )}
                            </div>
                        </div>
                        <button onClick={createConsultation} className="bg-blue-700 text-white py-2 px-4 rounded-lg hover:bg-blue-800">Book Consultation</button>
                    </div>
                </section>
                <h1 className='text-center mt-12 mb-4 text-2xl text-white'>Your Home Consultations</h1>
                <div className='container mx-auto'>
                    <table className="min-w-full bg-transparent border-collapse">
                        <thead>
                            <tr className="bg-transparent border-b border-gray-400 text-white">
                                <th className="py-2 px-4 border">Consultation Date</th>
                                <th className="py-2 px-4 border">Consultation Time</th>
                                <th className="py-2 px-4 border">Doctor</th>
                                <th className="py-2 px-4 border">Outcome</th>
                                <th className="py-2 px-4 border">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {consultations.map(consultation => (
                                <React.Fragment key={consultation.homeConsultationID}>
                                    <tr className="border-b border-transparent text-gray-300">
                                        <td className="py-2 px-4 border text-center">{consultation.consultationDate}</td>
                                        <td className="py-2 px-4 border text-center">{consultation.consultationTime}</td>
                                        <td className="py-2 px-4 border text-center">
                                            {doctors.find(doc => doc.doctorID === consultation.doctorID)?.firstName} {doctors.find(doc => doc.doctorID === consultation.doctorID)?.lastName} ({doctors.find(doc => doc.doctorID === consultation.doctorID)?.specialty})
                                        </td>
                                        <td className="py-2 px-4 border text-center">{consultation.outcome || 'N/A'}</td>
                                        <td className="py-2 px-4 border text-center">
                                            <div className="flex flex-col items-center">
                                                <button onClick={() => toggleDetails(consultation.homeConsultationID)} className="mb-2 hover:underline text-blue-500">
                                                    {expandedConsultationId === consultation.homeConsultationID ? 'Hide Details' : 'View Details'}
                                                </button>
                                                {(!consultation.outcome && !consultation.notes) && (
                                                    <button onClick={() => openDeleteModal(consultation.homeConsultationID)} className="hover:underline text-red-500">Delete</button>
                                                )}
                                            </div>
                                        </td>
                                    </tr>
                                    {expandedConsultationId === consultation.homeConsultationID && (
                                        <tr className="bg-transparent border-gray-300">
                                            <td colSpan="5" className="p-4">
                                                <div className="space-y-2 border rounded-xl">
                                                    <div className="border-gray-200 py-2">
                                                        <h3 className="font-semibold text-xl mb-2 text-cyan-400 text-center">Notes</h3>
                                                        <p className="text-gray-300 text-center">{consultation.notes || 'No notes available'}</p>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    )}
                                </React.Fragment>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
            {showDeleteModal && (
                <div className="fixed inset-0 z-20 flex items-center justify-center bg-black bg-opacity-50">
                    <div className="bg-white p-6 rounded shadow-lg">
                        <h2 className="text-lg font-semibold mb-4">Confirm delete</h2>
                        <p className="mb-4">Are you sure you want to delete this Home Consultation record?</p>
                        <div className="flex justify-end">
                            <button onClick={closeDeleteModal} className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-semibold py-2 px-4 rounded mr-2">
                                Cancel
                            </button>
                            <button onClick={() => deleteConsultation(consultationId)} className="bg-red-500 hover:bg-red-600 text-white font-semibold py-2 px-4 rounded">
                                Delete
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default HomeConsultation;