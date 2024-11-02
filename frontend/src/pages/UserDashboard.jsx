import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function PatientHomePage() {
    const [userData, setUserData] = useState(null);
    const navigate = useNavigate();

    // Fetch user data after login
    useEffect(() => {
        const token = localStorage.getItem('token');
        const userId = localStorage.getItem('userId');
    
        if (!token || !userId) {
            navigate('/signup'); // Redirect if token or userId is missing
            return;
        }
    
        axios.get(`http://localhost:8080/api/v1/user/${userId}`, {
            headers: { 'Authorization': `Bearer ${token}` }
        })
        .then(response => setUserData(response.data))
        .catch(error => {
            console.error("Failed to fetch user data:", error);
            navigate('/'); // Redirect on error
        });
    }, [navigate]);
    

    const handleLogout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
        navigate('/login');
    };

    if (!userData) {
        return <p>Loading...</p>;
    }

    return (
        <div className="p-4 bg-gray-100 min-h-screen">
            {/* Header Section */}
            <div className="flex justify-between items-center bg-white p-4 shadow-md rounded-md">
                <h2 className="text-xl font-semibold">Welcome, {userData.firstName}!</h2>
                <button onClick={handleLogout} className="text-red-500 font-bold">Logout</button>
            </div>

            {/* Medical History Upload */}
            <section className="mt-4 bg-white p-4 shadow-md rounded-md">
                <h3 className="text-lg font-semibold">Medical History</h3>
                <p>Upload your medical history for doctors and nurses to review.</p>
                <input type="file" className="mt-2"/>
            </section>

            {/* Book Appointment */}
            <section className="mt-4 bg-white p-4 shadow-md rounded-md">
                <h3 className="text-lg font-semibold">Book an Appointment</h3>
                <button className="bg-blue-500 text-white p-2 rounded-md mt-2">Schedule Appointment</button>
            </section>

            {/* Prescriptions & Diagnoses */}
            <section className="mt-4 bg-white p-4 shadow-md rounded-md">
                <h3 className="text-lg font-semibold">Prescriptions & Diagnoses</h3>
                <p>View your prescribed medications, diagnoses, and lab test suggestions here.</p>
                {/* Render prescriptions dynamically */}
                <div className="mt-2">
                    {userData.prescriptions ? userData.prescriptions.map((prescription, index) => (
                        <div key={index} className="mt-2 p-2 bg-gray-50 rounded-md">
                            <p><strong>Diagnosis:</strong> {prescription.diagnosis}</p>
                            <p><strong>Prescription:</strong> {prescription.details}</p>
                        </div>
                    )) : <p>No prescriptions available.</p>}
                </div>
            </section>

            {/* Lab Test Results */}
            <section className="mt-4 bg-white p-4 shadow-md rounded-md">
                <h3 className="text-lg font-semibold">Lab Test Results</h3>
                <button className="bg-green-500 text-white p-2 rounded-md mt-2">Upload/View Results</button>
            </section>

            {/* Surgery & Room Information */}
            <section className="mt-4 bg-white p-4 shadow-md rounded-md">
                <h3 className="text-lg font-semibold">Surgery & Room Information</h3>
                <p>Check the details of any scheduled surgeries or allocated room here.</p>
                <div className="mt-2">
                    {userData.surgery ? (
                        <div>
                            <p><strong>Surgery:</strong> {userData.surgery.type}</p>
                            <p><strong>Room:</strong> {userData.room ? userData.room.details : 'Not allocated'}</p>
                        </div>
                    ) : <p>No scheduled surgeries or rooms allocated.</p>}
                </div>
            </section>

            {/* Home Consultation */}
            <section className="mt-4 bg-white p-4 shadow-md rounded-md">
                <h3 className="text-lg font-semibold">Home Consultation</h3>
                <button className="bg-purple-500 text-white p-2 rounded-md mt-2">Request Home Consultation</button>
            </section>
        </div>
    );
}

export default PatientHomePage;
