import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Designer from '../../assets/Hospital_4.png';

const PatientAppointment = () => {
    const [appointments, setAppointments] = useState([]);
    const [diagnoses, setDiagnoses] = useState({});
    const [prescriptions, setPrescriptions] = useState({});
    const [labTests, setLabTests] = useState({});
    const [labResults, setLabResults] = useState({});
    const [medicineNames, setMedicineNames] = useState({});
    const [expandedAppointmentId, setExpandedAppointmentId] = useState(null);
    const userId = localStorage.getItem('userId');

    useEffect(() => {
        const fetchAppointments = async () => {
            try {
                const response = await axios.get(`/api/v1/appointment/patient/${userId}`);
                const appointmentsData = response.data.sort((a, b) => b.appointmentID - a.appointmentID);
                setAppointments(appointmentsData);

                const diagnosisPromises = appointmentsData.map(appointment =>
                    appointment.diagnosisID ? axios.get(`/api/v1/diagnosis/${appointment.diagnosisID}`) : Promise.resolve(null)
                );

                const diagnosesData = await Promise.all(diagnosisPromises);
                const diagnosesMap = {};
                const prescriptionPromises = [];
                const labTestPromises = [];
                const labResultPromises = [];

                diagnosesData.forEach((diagnosisResponse, index) => {
                    if (diagnosisResponse) {
                        const diagnosis = diagnosisResponse.data;
                        diagnosesMap[appointmentsData[index].diagnosisID] = diagnosis;
                        if (diagnosis.prescriptionID) prescriptionPromises.push(axios.get(`/api/v1/prescription/${diagnosis.prescriptionID}`));
                        if (diagnosis.labTestID) labTestPromises.push(axios.get(`/api/v1/lab_test/${diagnosis.labTestID}`));
                        if (diagnosis.labResultID) labResultPromises.push(axios.get(`/api/v1/lab_result/${diagnosis.labResultID}`));
                    }
                });
                setDiagnoses(diagnosesMap);

                const prescriptionsData = await Promise.all(prescriptionPromises);
                const prescriptionsMap = {};
                const medicinePromises = [];

                prescriptionsData.forEach(prescriptionResponse => {
                    if (prescriptionResponse) {
                        const prescription = prescriptionResponse.data;
                        prescriptionsMap[prescription.prescriptionID] = prescription;
                        prescription.medicines.forEach(medicine =>
                            medicinePromises.push(
                                axios.get(`/api/v1/medicine/${medicine.medicineID}`).then(res => {
                                    setMedicineNames(prev => ({ ...prev, [medicine.medicineID]: res.data.name }));
                                })
                            )
                        );
                    }
                });
                await Promise.all(medicinePromises); // Wait for all medicine names to load
                setPrescriptions(prescriptionsMap);

                const labTestsData = await Promise.all(labTestPromises);
                const labTestsMap = {};
                labTestsData.forEach(labTestResponse => {
                    if (labTestResponse) labTestsMap[labTestResponse.data.labTestID] = labTestResponse.data;
                });
                setLabTests(labTestsMap);

                const labResultsData = await Promise.all(labResultPromises);
                const labResultsMap = {};
                labResultsData.forEach(labResultResponse => {
                    if (labResultResponse) labResultsMap[labResultResponse.data.labResultID] = labResultResponse.data;
                });
                setLabResults(labResultsMap);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchAppointments();
    }, [userId]);

    const renderValue = (value) => (value !== null && value !== undefined ? value : 'NIL');

    const toggleDetails = (appointmentId) => {
        setExpandedAppointmentId(expandedAppointmentId === appointmentId ? null : appointmentId);
    };

    const createAppointment = async () => {
        try {
            const response = await axios.post(`/api/v1/appointment`, {
                patientID: userId,
                status: 'Pending',
            });
            setAppointments(prev => [response.data, ...prev]); // Add new appointment to top
        } catch (error) {
            console.error('Error creating appointment:', error);
        }
    };

    const deleteAppointment = async (appointmentId) => {
        try {
            await axios.delete(`/api/v1/appointment/${appointmentId}`);
            setAppointments(prev => prev.filter(app => app.appointmentID !== appointmentId));
        } catch (error) {
            console.error('Error deleting appointment:', error);
        }
    };


    return (
        <div className='bg-transparent min-h-screen relative'>
            <img
                src={Designer}
                alt="designer"
                className="absolute inset-0 z-[-10] w-full h-full object-cover object-top"
            />
            <div className="absolute inset-0 z-[-5] bg-blue-gray-900 opacity-80" />
            <div className="container rounded-xl z-10 relative bg-transparent mx-auto p-4">
                <section className="mb-8 mt-20">
                    <h2 className="text-[60px] text-white text-center header-font mb-2">Book an Appointment!</h2>
                    <p className="text-gray-300 text-center mb-4">Click the button below to book an appointment. Your appointment will be scheduled as pending and you can view or delete it below.</p>
                    <button onClick={createAppointment} className="bg-blue-700 text-white ml-[565px] py-2 px-4 rounded-lg hover:bg-blue-800">Book Appointment</button>
                </section>
                <h1 className='text-center mt-12 mb-4 text-2xl text-white'>Your Appointments</h1>
                <table className="min-w-full bg-transparent border-collapse">
                    <thead>
                        <tr className="bg-transparent border-b border-gray-400 text-white">
                            <th className="py-2 px-4 border">Appointment Date</th>
                            <th className="py-2 px-4 border">Appointment Time</th>
                            <th className="py-2 px-4 border">Status</th>
                            <th className="py-2 px-4 border">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {appointments.map(appointment => (
                            <React.Fragment key={appointment.appointmentID}>
                                <tr className="border-b border-transparent text-gray-300">
                                    <td className="py-2 px-4 border text-center">{renderValue(appointment.appointmentDate)}</td>
                                    <td className="py-2 px-4 border text-center">{renderValue(appointment.appointmentTime)}</td>
                                    <td className="py-2 px-4 border text-center">{renderValue(appointment.status)}</td>
                                    <td className="py-2 px-4 border grid text-center">
                                        <button onClick={() => toggleDetails(appointment.appointmentID)} className="mr-2 hover:underline text-blue-500">
                                            {expandedAppointmentId === appointment.appointmentID ? 'Hide Details' : 'View Details'}
                                        </button>
                                        {(appointment.status === 'Pending' || appointment.status === 'Scheduled') && (
                                            <button onClick={() => deleteAppointment(appointment.appointmentID)} className="text-red-600 hover:underline">Delete</button>
                                        )}
                                    </td>
                                </tr>
                                {expandedAppointmentId === appointment.appointmentID && (
                                    <tr className="bg-transparent border-b border-gray-300">
                                        <td colSpan="4" className="p-4">
                                            <div className="space-y-0 border rounded-lg p-4">
                                                <div className="grid items-center text-center space-x-8 border-b pb-2">
                                                    <h2 className="font-semibold text-xl text-cyan-400">Diagnosis</h2>
                                                    <div className="text-sm mt-4 text-gray-300">
                                                        Notes: {renderValue(diagnoses[appointment.diagnosisID]?.notes)}
                                                    </div>
                                                </div>
                                                {diagnoses[appointment.diagnosisID]?.prescriptionID && prescriptions[diagnoses[appointment.diagnosisID].prescriptionID] && (
                                                    <div className="py-4 text-center">
                                                        <h3 className="font-semibold text-xl text-blue-300">Prescription</h3>
                                                        <span className='text-gray-300'>Date: {renderValue(prescriptions[diagnoses[appointment.diagnosisID].prescriptionID].prescriptionDate)}</span>
                                                        <div className="overflow-x-auto mt-2">
                                                            <table className="min-w-full text-white border">
                                                                <thead className="bg-gray-600">
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
                                                                    {prescriptions[diagnoses[appointment.diagnosisID].prescriptionID].medicines.map((medicine, index) => (
                                                                        <tr key={medicine.medicineID} className="border-t text-gray-300">
                                                                            <td className="p-2 border text-center">{index + 1}</td>
                                                                            <td className="p-2 border">{medicineNames[medicine.medicineID]}</td>
                                                                            <td className="p-2 border">{medicine.dosage}</td>
                                                                            <td className="p-2 border">{medicine.frequency}</td>
                                                                            <td className="p-2 border">{medicine.duration}</td>
                                                                            <td className="p-2 border">{medicine.instructions}</td>
                                                                        </tr>
                                                                    ))}
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                )}

                                                {diagnoses[appointment.diagnosisID]?.labTestID && labTests[diagnoses[appointment.diagnosisID].labTestID] && (
                                                    <div className="py-2">
                                                        <h3 className="font-semibold text-xl text-center text-green-400">Lab Test</h3>
                                                        <div className="flex space-x-4 text-gray-300">
                                                            <span>Name: {renderValue(labTests[diagnoses[appointment.diagnosisID].labTestID].name)}</span>
                                                            <span>Description: {renderValue(labTests[diagnoses[appointment.diagnosisID].labTestID].description)}</span>
                                                            <span>Normal Range: {renderValue(labTests[diagnoses[appointment.diagnosisID].labTestID].normalRange)}</span>
                                                            <span>Units: {renderValue(labTests[diagnoses[appointment.diagnosisID].labTestID].units)}</span>
                                                        </div>
                                                    </div>
                                                )}
                                                {diagnoses[appointment.diagnosisID]?.labResultID && labResults[diagnoses[appointment.diagnosisID].labResultID] && (
                                                    <div className="py-2">
                                                        <h3 className="font-semibold text-xl text-purple-400 text-center">Lab Result</h3>
                                                        <div className="flex space-x-8 text-gray-300 justify-center">
                                                            <span>Result Value: {renderValue(labResults[diagnoses[appointment.diagnosisID].labResultID].resultValue)}</span>
                                                            <span>Test Date: {renderValue(labResults[diagnoses[appointment.diagnosisID].labResultID].testDate)}</span>
                                                            <span>Notes: {renderValue(labResults[diagnoses[appointment.diagnosisID].labResultID].notes)}</span>
                                                        </div>
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
        </div>
    );
};

export default PatientAppointment;
