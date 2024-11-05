import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Designer from '../../assets/Hospital_2.png'; // Update with the correct path to your image

const PatientSurgery = () => {
    const [surgeries, setSurgeries] = useState([]);
    const [doctors, setDoctors] = useState([]);
    const [selectedDoctors, setSelectedDoctors] = useState([]);
    const [surgeryDate, setSurgeryDate] = useState('');
    const [surgeryType, setSurgeryType] = useState('');
    const [expandedSurgeryId, setExpandedSurgeryId] = useState(null);
    const [prescriptions, setPrescriptions] = useState({});
    const [medicines, setMedicines] = useState({});
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [surgeryId, setSurgeryId] = useState("");
    const patientId = localStorage.getItem('userId'); // Replace with actual patient ID

    useEffect(() => {
        fetchSurgeries();
        fetchDoctors();
    }, []);

    const fetchSurgeries = async () => {
        try {
            const response = await axios.get(`/api/v1/surgery/patient/${patientId}`);
            setSurgeries(response.data);
        } catch (error) {
            console.error('Error fetching surgeries:', error);
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

    const handleDoctorSelection = (doctorId) => {
        if (selectedDoctors.includes(doctorId)) {
            setSelectedDoctors(selectedDoctors.filter(id => id !== doctorId));
        } else if (selectedDoctors.length < 3) {
            setSelectedDoctors([...selectedDoctors, doctorId]);
        }
    };

    const createSurgery = async () => {
        try {
            const newSurgery = {
                surgeryDate,
                surgeryType,
                doctorIDs: selectedDoctors,
                patientID: patientId,
                outcome: null,
                prescriptionIDs: null,
                notes: null
            };
            await axios.post('/api/v1/surgery', newSurgery);
            fetchSurgeries(); // Fetch surgeries again to get the newly created surgery with its ID
        } catch (error) {
            console.error('Error creating surgery:', error);
        }
    };

    const deleteSurgery = async (surgeryId) => {
        try {
            console.log(`Deleting surgery with ID: ${surgeryId}`);
            await axios.delete(`/api/v1/surgery/${surgeryId}`);
            fetchSurgeries();
        } catch (error) {
            console.error('Error deleting surgery:', error);
        };
        closeDeleteModal();
    };

    const toggleDetails = (surgeryId) => {
        setExpandedSurgeryId(expandedSurgeryId === surgeryId ? null : surgeryId);
    };

    const fetchPrescriptionById = async (prescriptionId) => {
        if (!prescriptions[prescriptionId]) {
            const response = await axios.get(`/api/v1/prescription/${prescriptionId}`);
            setPrescriptions((prevPrescriptions) => ({
                ...prevPrescriptions,
                [prescriptionId]: response.data
            }));
        }
    };

    const fetchMedicineById = async (medicineId) => {
        if (!medicines[medicineId]) {
            const response = await axios.get(`/api/v1/medicine/${medicineId}`);
            setMedicines((prevMedicines) => ({
                ...prevMedicines,
                [medicineId]: response.data
            }));
        }
    };

    const openDeleteModal = async (surgeryId) => {
        setShowDeleteModal(true);
        setSurgeryId(surgeryId);
    };

    const closeDeleteModal = () => {
        setShowDeleteModal(false);
    };

    return (
        <div className='bg-transparent min-h-screen relative'>
            <img
                src="https://images.pexels.com/photos/421927/pexels-photo-421927.jpeg?cs=srgb&dl=pexels-chris-schippers-139261-421927.jpg&fm=jpg"
                alt="designer"
                className="absolute inset-0 z-[-10] w-full h-full object-cover object-top"
            />
            <div className="absolute inset-0 z-[-5] bg-blue-gray-900 opacity-80" />
            <div className="container rounded-xl z-10 relative bg-transparent mx-auto p-4">
                <section className="mb-8 mt-20">
                    <h2 className="text-[60px] text-white text-center header-font mb-2">Book a Surgery!</h2>
                    <p className="text-gray-300 text-center mb-4">Fill in the details below to book a surgery. You can view or delete your surgeries below.</p>
                    <div className="flex flex-col items-center">
                        <div className='flex items-center space-x-20'>
                            <div className='inline-grid'>
                                <input
                                    type="date"
                                    value={surgeryDate}
                                    onChange={(e) => setSurgeryDate(e.target.value)}
                                    className="mb-4 p-2 rounded"
                                />
                                <select
                                    value={surgeryType}
                                    onChange={(e) => setSurgeryType(e.target.value)}
                                    className="mb-4 p-2 rounded"
                                >
                                    <option value="">Select Surgery Type</option>
                                    <option value="Appendectomy">Appendectomy</option>
                                    <option value="Cholecystectomy">Cholecystectomy</option>
                                    <option value="HerniaRepair">HerniaRepair</option>
                                    <option value="Mastectomy">Mastectomy</option>
                                    <option value="CSection">CSection</option>
                                    <option value="HipReplacement">HipReplacement</option>
                                    <option value="KneeReplacement">KneeReplacement</option>
                                    <option value="CoronaryArteryBypass">CoronaryArteryBypass</option>
                                    <option value="GastricBypass">GastricBypass</option>
                                    <option value="Tonsillectomy">Tonsillectomy</option>
                                </select>
                            </div>
                            <div className="mb-4">
                                <label className="block text-white mb-2 text-center">Select Doctors (max 3):</label>
                                <div className='h-[200px] overflow-y-scroll border rounded-md px-4 py-2 seperate'>
                                    {doctors.map(doctor => (
                                        <div key={doctor.doctorID} className=" flex items-center mb-2">
                                            <input
                                                type="checkbox"
                                                checked={selectedDoctors.includes(doctor.doctorID)}
                                                onChange={() => handleDoctorSelection(doctor.doctorID)}
                                                className="mr-2"
                                            />
                                            <span className="text-white">{doctor.firstName} {doctor.lastName} - {doctor.specialty}</span>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        </div>
                        <button onClick={createSurgery} className="bg-blue-700 text-white py-2 px-4 rounded-lg hover:bg-blue-800">Book Surgery</button>
                    </div>
                </section>
                <h1 className='text-center mt-12 mb-4 text-2xl text-white'>Your Surgeries</h1>
                <table className="min-w-full bg-transparent border-collapse">
                    <thead>
                        <tr className="bg-transparent border-b border-gray-400 text-white">
                            <th className="py-2 px-4 border">Surgery Date</th>
                            <th className="py-2 px-4 border">Surgery Type</th>
                            <th className="py-2 px-4 border">Outcome</th>
                            <th className="py-2 px-4 border">Doctors</th>
                            <th className="py-2 px-4 border">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {surgeries.map(surgery => (
                            <React.Fragment key={surgery.surgeryID}>
                                <tr className="border-b border-transparent text-gray-300">
                                    <td className="py-2 px-4 border text-center">{surgery.surgeryDate}</td>
                                    <td className="py-2 px-4 border text-center">{surgery.surgeryType}</td>
                                    <td className="py-2 px-4 border text-center">{surgery.outcome || 'N/A'}</td>
                                    <td className="py-2 px-4 border text-center">
                                        {surgery.doctorIDs.map(doctorId => {
                                            const doctor = doctors.find(doc => doc.doctorID === doctorId);
                                            return doctor ? (
                                                <div key={doctorId}>
                                                    {doctor.firstName} {doctor.lastName} ({doctor.specialty})
                                                </div>
                                            ) : 'Loading...';
                                        })}
                                    </td>
                                    <td className="py-2 px-4 border text-center">
                                        <div className="flex flex-col items-center">
                                            {(!(!surgery.prescriptionIDs || surgery.prescriptionIDs.length === 0)) && (
                                                <button onClick={() => toggleDetails(surgery.surgeryID)} className="mb-2 hover:underline text-blue-500">
                                                    {expandedSurgeryId === surgery.surgeryID ? 'Hide Details' : 'View Details'}
                                                </button>
                                            )}
                                            {(!surgery.prescriptionIDs || surgery.prescriptionIDs.length === 0) && (
                                                <button onClick={() => openDeleteModal(surgery.surgeryID)} className="hover:underline text-red-500">Delete</button>
                                            )}
                                        </div>
                                    </td>
                                </tr>
                                {expandedSurgeryId === surgery.surgeryID && (
                                    <tr className="bg-transparent border-gray-300">
                                        <td colSpan="5" className="p-4">
                                            <div className="space-y-2 border rounded-xl">
                                                <div className="border-gray-200 py-2">
                                                    <h3 className="font-semibold text-xl mb-2 text-cyan-400 text-center">Notes</h3>
                                                    <p className="text-gray-300 text-center">{surgery.notes || 'No notes available'}</p>
                                                </div>
                                                {surgery.prescriptionIDs && surgery.prescriptionIDs.length > 0 && (
                                                    <div className="border-gray-200 py-2">
                                                        <h3 className="font-semibold text-xl mb-2 text-cyan-400 text-center">Prescription</h3>
                                                        <table className="min-w-[1220px] text-white border mx-auto">
                                                            <thead className="bg-gray-700">
                                                                <tr>
                                                                    <th className="p-2 border">No.</th>
                                                                    <th className="p-2 border">Medicine Name</th>
                                                                    <th className="p-2 border">Dosage</th>
                                                                    <th className="p-2 border">Frequency</th>
                                                                    <th className="p-2 border">Duration (days)</th>
                                                                    <th className="p-2 border">Instructions</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                {surgery.prescriptionIDs.map(prescriptionId => {
                                                                    fetchPrescriptionById(prescriptionId);
                                                                    const prescription = prescriptions[prescriptionId];
                                                                    return prescription ? (
                                                                        prescription.medicines.map((medicine, index) => {
                                                                            fetchMedicineById(medicine.medicineID);
                                                                            return (
                                                                                <tr key={medicine.medicineID} className="border-t text-gray-300 text-center">
                                                                                    <td className="p-2 border text-center">{index + 1}</td>
                                                                                    <td className="p-2 border">{medicines[medicine.medicineID]?.name || 'Loading...'}</td>
                                                                                    <td className="p-2 border">{medicine.dosage}</td>
                                                                                    <td className="p-2 border">{medicine.frequency}</td>
                                                                                    <td className="p-2 border">{medicine.duration}</td>
                                                                                    <td className="p-2 border">{medicine.instructions}</td>
                                                                                </tr>
                                                                            );
                                                                        })
                                                                    ) : (
                                                                        <tr key={prescriptionId}>
                                                                            <td colSpan="6" className="p-2 border text-center text-gray-700">Loading prescription...</td>
                                                                        </tr>
                                                                    );
                                                                })}
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                )}
                                            </div>
                                        </td>
                                    </tr>
                                )}
                            </React.Fragment>
                        ))}
                    </tbody>
                </table>
            </div>
            {showDeleteModal && (
                <div className="fixed inset-0 z-20 flex items-center justify-center bg-black bg-opacity-50">
                    <div className="bg-white p-6 rounded shadow-lg">
                        <h2 className="text-lg font-semibold mb-4">Confirm delete</h2>
                        <p className="mb-4">Are you sure you want to this Surgery record?</p>
                        <div className="flex justify-end">
                            <button onClick={closeDeleteModal} className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-semibold py-2 px-4 rounded mr-2">
                                Cancel
                            </button>
                            <button onClick={() => deleteSurgery(surgeryId)} className="bg-red-500 hover:bg-red-600 text-white font-semibold py-2 px-4 rounded">
                                Delete
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div >
    );
};

export default PatientSurgery;