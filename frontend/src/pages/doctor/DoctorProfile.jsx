import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { FaPhone, FaEnvelope, FaClinicMedical, FaUserMd, FaLock } from 'react-icons/fa';

const DoctorProfile = () => {
    const [doctor, setDoctor] = useState(null);
    const [editForm, setEditForm] = useState({});
    const [isEditing, setIsEditing] = useState(false);
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [isEditingPassword, setIsEditingPassword] = useState(false);

    useEffect(() => {
        fetchDoctor();
    }, []);

    const fetchDoctor = async () => {
        const doctorId = localStorage.getItem('doctorId');
        try {
            const response = await axios.get(`/api/v1/doctor/${doctorId}`);
            setDoctor(response.data);
        } catch (error) {
            console.error('Error fetching doctor:', error);
        }
    };

    const saveDoctorDetails = async () => {
        const doctorId = localStorage.getItem('doctorId');
        try {
            await axios.put(`/api/v1/doctor/${doctorId}`, editForm);
            setDoctor({ ...doctor, ...editForm }); // Update the doctor state with the new details
            setIsEditing(false);
        } catch (error) {
            console.error('Error updating doctor details', error);
        }
    };

    const savePassword = async () => {
        if (password !== confirmPassword) {
            alert("Passwords do not match");
            return;
        }
        const doctorId = localStorage.getItem('doctorId');
        try {
            await axios.put(`/api/v1/doctor/${doctorId}/password`, { password });
            setIsEditingPassword(false);
            setPassword('');
            setConfirmPassword('');
        } catch (error) {
            console.error('Error updating password', error);
        }
    };

    const handleEditClick = () => {
        setEditForm({
            phoneNumber: doctor.phoneNumber,
            email: doctor.email,
            officeNumber: doctor.officeNumber
        }); // Populate form with current doctor details
        setIsEditing(true);
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
                            <p className="text-gray-800">Loading doctor profile...</p>
                        )}
                    </div>
                </div>

                {doctor && (
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                        <div className="bg-gray-50 rounded-lg p-4 shadow-sm">
                            {isEditing ? (
                                <>
                                    <input type="text" name="phoneNumber" value={editForm.phoneNumber || ''} onChange={e => setEditForm({ ...editForm, phoneNumber: e.target.value })} className="border p-2 mb-2 w-full" />
                                    <input type="text" name="email" value={editForm.email || ''} onChange={e => setEditForm({ ...editForm, email: e.target.value })} className="border p-2 mb-2 w-full" />
                                    <input type="text" name="officeNumber" value={editForm.officeNumber || ''} onChange={e => setEditForm({ ...editForm, officeNumber: e.target.value })} className="border p-2 w-full" />
                                    <button className="bg-yellow-700 text-white p-2 rounded mr-2 mt-2" onClick={saveDoctorDetails}>Save</button>
                                    <button className="text-gray-500" onClick={() => setIsEditing(false)}>Cancel</button>
                                </>
                            ) : (
                                <>
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
                                    <button className="text-yellow-700 hover:text-yellow-800 mt-2" onClick={handleEditClick}>
                                        Edit Doctor Details
                                    </button>
                                </>
                            )}
                        </div>

                        <div className="bg-gray-50 rounded-lg p-4 shadow-sm">
                            <p><span className="font-semibold text-gray-600">Specialty:</span> {doctor.specialty}</p>
                            {isEditingPassword ? (
                                <>
                                    <input type="password" name="password" placeholder="New Password" value={password} onChange={e => setPassword(e.target.value)} className="border p-2 mb-2 w-full" />
                                    <input type="password" name="confirmPassword" placeholder="Confirm Password" value={confirmPassword} onChange={e => setConfirmPassword(e.target.value)} className="border p-2 mb-2 w-full" />
                                    <button className="bg-yellow-700 text-white p-2 rounded mr-2 mt-2" onClick={savePassword}>Save</button>
                                    <button className="text-gray-500" onClick={() => setIsEditingPassword(false)}>Cancel</button>
                                </>
                            ) : (
                                <button className="text-yellow-700 hover:text-yellow-800 mt-2" onClick={() => setIsEditingPassword(true)}>
                                    Edit Password
                                </button>
                            )}
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default DoctorProfile;