import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { FaPhone, FaEnvelope, FaMapMarkerAlt, FaUser, FaEdit, FaTrash, FaPlus } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';

const PatientProfile = () => {
  const [user, setUser] = useState({});
  const [editForm, setEditForm] = useState({});
  const [isEditingUser, setIsEditingUser] = useState(false);
  const [medicalHistory, setMedicalHistory] = useState([]);
  const [showEditForm, setShowEditForm] = useState(false);
  const [editRecord, setEditRecord] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    axios.get(`/api/v1/user/${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    }).then((response) => setUser(response.data))
      .catch((error) => console.error(error));

    axios.get(`/api/v1/medical_history?${userId}`, {
      headers: { Authorization: `Bearer ${token}` },
    }).then((response) => setMedicalHistory(response.data))
      .catch((error) => console.error(error));
  }, [navigate]);

  // Save patient details
  const savePatientDetails = async () => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    try {
      await axios.put(`/api/v1/user/${userId}`, editForm, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setUser(editForm);
      setIsEditingUser(false);
    } catch (error) {
      console.error("Error updating patient details", error);
    }
  };

  const handleEditUserClick = () => {
    setEditForm(user); // Populate form with current user details
    setIsEditingUser(true);
  };

  const handleEditClick = (record) => {
    setEditRecord(record);
    setShowEditForm(true);
  };

  const handleDelete = async (historyID) => {
    const token = localStorage.getItem('token');
    await axios.delete(`/api/v1/medical_history/${historyID}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    setMedicalHistory((prev) => prev.filter((r) => r.historyID !== historyID));
  };

  const handleAddNew = () => {
    setEditRecord({});
    setShowEditForm(true);
  };

  const handleFormSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    if (editRecord.historyID) {
      await axios.put(`/api/v1/medical_history/${editRecord.historyID}`, editRecord, {
        headers: { Authorization: `Bearer ${token}` },
      });
    } else {
      await axios.post(`/api/v1/medical_history`, { ...editRecord, patientId: user.userID }, {
        headers: { Authorization: `Bearer ${token}` },
      });
    }
    setShowEditForm(false);
    const response = await axios.get(`/api/v1/medical_history?patientId=${user.userID}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    setMedicalHistory(response.data);
  };

  return (
    <div className="bg-gray-300 min-h-screen p-6">
      <div className="max-w-4xl mx-auto bg-white shadow-md rounded-lg p-8 mt-20">
        <div className="flex items-center space-x-4 mb-6">
          <div className="text-gray-500 rounded-full p-4 bg-gray-200">
            <FaUser className="text-4xl" />
          </div>
          <div>
            <h2 className="text-2xl font-semibold text-gray-800">{user.firstName} {user.lastName}</h2>
            <p className="text-sm text-gray-600">Patient ID: {user.userID}</p>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
          <div className="bg-gray-50 rounded-lg p-4 shadow-sm">
            {isEditingUser ? (
              <>
                <input type="text" name="phoneNumber" value={editForm.phoneNumber || ''} onChange={e => setEditForm({ ...editForm, phoneNumber: e.target.value })} className="border p-2 mb-2 w-full" />
                <input type="text" name="email" value={editForm.email || ''} onChange={e => setEditForm({ ...editForm, email: e.target.value })} className="border p-2 mb-2 w-full" />
                <input type="text" name="address" value={editForm.address || ''} onChange={e => setEditForm({ ...editForm, address: e.target.value })} className="border p-2 w-full" />
                <button className="bg-yellow-700 text-white p-2 rounded mr-2 mt-2" onClick={savePatientDetails}>Save</button>
                <button className="text-gray-500" onClick={() => setIsEditingUser(false)}>Cancel</button>
              </>
            ) : (
              <>
                <div className="flex items-center mb-2">
                  <FaPhone className="text-gray-600 mr-2" />
                  <p className="text-gray-800">{user.phoneNumber}</p>
                </div>
                <div className="flex items-center mb-2">
                  <FaEnvelope className="text-gray-600 mr-2" />
                  <p className="text-gray-800">{user.email}</p>
                </div>
                <div className="flex items-center">
                  <FaMapMarkerAlt className="text-gray-600 mr-2" />
                  <p className="text-gray-800">{user.address}</p>
                </div>
                <button className="text-yellow-700 hover:text-yellow-800 mt-2" onClick={handleEditUserClick}>
                  Edit Patient Details
                </button>
              </>
            )}
          </div>

          <div className="bg-gray-50 rounded-lg p-4 shadow-sm">
            <p><span className="font-semibold text-gray-600">Gender:</span> {user.gender}</p>
            <p><span className="font-semibold text-gray-600">Date of Birth:</span> {user.dob}</p>
          </div>
        </div>

        {/* Medical History Section... */}
        <h3 className="text-xl font-semibold text-gray-800 mb-4">Medical History</h3>
        <div className="bg-gray-50 rounded-lg shadow-sm overflow-auto">
          <table className="min-w-full text-sm text-left">
            <thead className="bg-gray-200">
              <tr>
                <th className="py-2 px-4 text-gray-700">Date</th>
                <th className="py-2 px-4 text-gray-700">Diagnosis</th>
                <th className="py-2 px-4 text-gray-700">Treatment</th>
                <th className="py-2 px-4 text-gray-700">Notes</th>
                <th className="py-2 px-4 text-gray-700">Actions</th>
              </tr>
            </thead>
            <tbody>
              {medicalHistory.length > 0 ? (
                medicalHistory.map((record) => (
                  <tr key={record.historyID} className="border-b border-gray-200">
                    <td className="py-2 px-4 text-gray-800">{record.recordDate}</td>
                    <td className="py-2 px-4 text-gray-800">{record.diagnosis}</td>
                    <td className="py-2 px-4 text-gray-800">{record.treatment}</td>
                    <td className="py-2 px-4 text-gray-800">{record.notes}</td>
                    <td className="py-2 px-4 flex space-x-2">
                      <FaEdit className="text-yellow-700 cursor-pointer" onClick={() => handleEditClick(record)} />
                      <FaTrash className="text-red-500 cursor-pointer" onClick={() => handleDelete(record.historyID)} />
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5" className="text-center py-4 text-gray-500 relative">
                    No medical history found.
                    <FaPlus
                      className="text-green-500 cursor-pointer absolute bottom-5 right-10"
                      onClick={handleAddNew}
                    />
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>

        {showEditForm && (
          <form onSubmit={handleFormSubmit} className="mt-6 bg-gray-100 p-4 rounded shadow">
            <h3 className="text-lg font-semibold mb-4">{editRecord.historyID ? "Edit" : "Add"} Medical History</h3>
            <input
              type="text"
              placeholder="Diagnosis"
              className="block w-full mb-2 p-2 border rounded"
              value={editRecord.diagnosis || ""}
              onChange={(e) => setEditRecord({ ...editRecord, diagnosis: e.target.value })}
              required
            />
            <input
              type="text"
              placeholder="Treatment"
              className="block w-full mb-2 p-2 border rounded"
              value={editRecord.treatment || ""}
              onChange={(e) => setEditRecord({ ...editRecord, treatment: e.target.value })}
              required
            />
            <textarea
              placeholder="Notes"
              className="block w-full mb-2 p-2 border rounded"
              value={editRecord.notes || ""}
              onChange={(e) => setEditRecord({ ...editRecord, notes: e.target.value })}
            ></textarea>
            <button type="submit" className="bg-green-500 text-white py-1 px-4 rounded">
              Save
            </button>
            <button type="button" onClick={() => setShowEditForm(false)} className="ml-2 bg-gray-300 py-1 px-4 rounded">
              Cancel
            </button>
          </form>
        )}
      </div>
    </div>
  );
};

export default PatientProfile;
