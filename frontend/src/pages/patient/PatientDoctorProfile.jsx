import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaPhone, FaEnvelope, FaMapMarkerAlt, FaUserMd, FaClinicMedical } from 'react-icons/fa';
import { useParams } from 'react-router-dom';
import Designer from '../../assets/Hospital_3.png'; // Update with the correct path to your image

const PatientDoctorProfile = () => {
    const { doctorId } = useParams();
    const [doctor, setDoctor] = useState(null);

    useEffect(() => {
        fetchDoctor();
    }, [doctorId]);

    const fetchDoctor = async () => {
        try {
            const response = await axios.get(`/api/v1/doctor/${doctorId}`);
            setDoctor(response.data);
        } catch (error) {
            console.error('Error fetching doctor:', error);
        }
    };

    return (
        <div className="p-6">
            <div className="max-w-4xl mx-auto bg-white shadow-md rounded-lg p-8 mt-20">
                <div className="flex items-center space-x-4 mb-6">
                    <div className="text-gray-500 rounded-full p-4 bg-gray-200">
                        <FaUserMd className="text-4xl" />
                    </div>
                    <div>
                        {doctor ? (
                            <>
                                <h2 className="text-2xl font-semibold text-gray-800">{doctor.firstName} {doctor.lastName}</h2>
                                <p className="text-sm text-gray-600">Doctor ID: {doctor.doctorID}</p>
                            </>
                        ) : (
                            <p className="text-red-400">No doctor with This ID</p>
                        )}
                    </div>
                </div>

                {doctor && (
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                        <div className="bg-gray-50 rounded-lg p-4 shadow-sm">
                            <div className="flex items-center mb-2">
                                <FaPhone className="text-gray-600 mr-2" />
                                <p className="text-gray-800">{doctor.phoneNumber}</p>
                            </div>
                            <div className="flex items-center mb-2">
                                <FaEnvelope className="text-gray-600 mr-2" />
                                <p className="text-gray-800">{doctor.email}</p>
                            </div>
                            <div className="flex items-center mb-2">
                                <FaClinicMedical className="text-gray-600 mr-2" />
                                <p className="text-gray-800">Office Number: {doctor.officeNumber}</p>
                            </div>
                        </div>

                        <div className="bg-gray-50 rounded-lg p-4 shadow-sm">
                            <p><span className="font-semibold text-gray-600">Specialty:</span> {doctor.specialty}</p>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default PatientDoctorProfile;