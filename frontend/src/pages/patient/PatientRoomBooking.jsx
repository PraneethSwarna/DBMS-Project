import React, { useState, useEffect } from 'react';
import axios from 'axios';

const PatientRoomBooking = () => {
  const [admissionDate, setAdmissionDate] = useState('');
  const [dischargeDate, setDischargeDate] = useState('');
  const [reason, setReason] = useState('');
  const [roomType, setRoomType] = useState('');
  const [availableRooms, setAvailableRooms] = useState([]);
  const [bookings, setBookings] = useState([]);
  const [expandedBookingId, setExpandedBookingId] = useState(null);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [bookingId, setBookingId] = useState("");
  const patientId = localStorage.getItem('userId'); // Replace with actual patient ID

  useEffect(() => {
    fetchBookings();
  }, []);

  const fetchBookings = async () => {
    try {
      const response = await axios.get(`/api/v1/hospitalization/patient/${patientId}`);
      setBookings(response.data);
    } catch (error) {
      console.error('Error fetching bookings:', error);
    }
  };

  const checkRoomAvailability = async () => {
    try {
      const response = await axios.get(`/api/v1/room/status/Available/type/${roomType}`);
      const rooms = response.data;
      setAvailableRooms(rooms);
      console.log('Available rooms:', rooms);
      if (rooms.length === 0) {
        alert('No rooms of this type are available.');
      } else {
        alert('Rooms are available');
      }
    } catch (error) {
      console.error('Error checking room availability:', error);
    }
  };

  const bookRoom = async () => {
    if (availableRooms.length === 0) {
      alert('No rooms of this type are available.');
      return;
    }

    const room = availableRooms[0]; // Book the first available room

    try {
      const newBooking = {
        admissionDate,
        dischargeDate: dischargeDate || null,
        reason,
        patientID: patientId,
        roomNumber: room.roomNumber,
        notes: null
      };
      await axios.post('/api/v1/hospitalization', newBooking);
      await axios.put(`/api/v1/room/${room.roomNumber}`, { status: 'Occupied' });
      setAvailableRooms(availableRooms.filter(r => r.roomNumber !== room.roomNumber)); // Remove the booked room from available rooms
      fetchBookings(); // Fetch bookings again to get the newly created booking
    } catch (error) {
      console.error('Error booking room:', error);
    }
  };

  const deleteBooking = async (bookingId) => {
    try {
      console.log(`Deleting booking with ID: ${bookingId}`);
      await axios.delete(`/api/v1/hospitalization/${bookingId}`);
      fetchBookings();
    } catch (error) {
      console.error('Error deleting booking:', error);
    }
    closeDeleteModal();
  };

  const toggleDetails = (bookingId) => {
    setExpandedBookingId(expandedBookingId === bookingId ? null : bookingId);
  };

  const openDeleteModal = async (bookingId) => {
    setShowDeleteModal(true);
    setBookingId(bookingId);
  };

  const closeDeleteModal = () => {
    setShowDeleteModal(false);
  };

  return (
    <div className='bg-transparent min-h-screen relative'>
      <img
        src="https://media.istockphoto.com/id/1364971557/photo/hospital-recovery-room-with-beds-and-chairs-3d-rendering.jpg?s=612x612&w=0&k=20&c=qpLCCYKBxWiVpV74zLbsV69Trb0ga8plCIsx7h7CLAw="
        alt="designer"
        className="absolute inset-0 z-[-10] w-full h-full object-cover object-top"
      />
      <div className="absolute inset-0 z-[-5] bg-blue-gray-900 opacity-80" />
      <div className="container rounded-xl z-10 relative bg-transparent mx-auto p-4">
        <section className="mb-8 mt-20">
          <h2 className="text-[60px] text-white text-center header-font mb-2">Book a Room!</h2>
          <p className="text-gray-300 text-center mb-4">Fill in the details below to book a room. You can view or delete your bookings below.</p>
          <div className="flex flex-col items-center">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
              <div>
                <label className="block text-gray-300 mb-2">Admission Date:</label>
                <input
                  type="date"
                  value={admissionDate}
                  onChange={(e) => setAdmissionDate(e.target.value)}
                  className="p-2 rounded w-full"
                />
              </div>
              <div>
                <label className="block text-gray-300 mb-2">Discharge Date:</label>
                <input
                  type="date"
                  value={dischargeDate}
                  onChange={(e) => setDischargeDate(e.target.value)}
                  className="p-2 rounded w-full"
                />
              </div>
              <div className="md:col-span-2">
                <label className="block text-gray-300 mb-2">Enter your reason for booking a room here:</label>
                <input
                  type="text"
                  value={reason}
                  onChange={(e) => setReason(e.target.value)}
                  placeholder="Enter reason"
                  className="p-2 rounded w-full"
                />
              </div>
              <div className="md:col-span-2">
                <label className="block text-gray-300 mb-2">select the type of room you want to book:</label>
                <select
                  value={roomType}
                  onChange={(e) => setRoomType(e.target.value)}
                  className="p-2 rounded w-full"
                >
                  <option value="">Select Room Type</option>
                  <option value="General">General</option>
                  <option value="Private">Private</option>
                  <option value="ICU">ICU</option>
                </select>
              </div>
              <div className="md:col-span-2">
                <button onClick={checkRoomAvailability} className="bg-blue-700 text-white py-2 px-4 rounded-lg hover:bg-blue-800 w-full">Check Availability</button>
              </div>
            </div>
            <button onClick={bookRoom} className="bg-blue-700 text-white py-2 px-4 rounded-lg hover:bg-blue-800">Book Room</button>
          </div>
        </section>
        <h1 className='text-center mt-12 mb-4 text-2xl text-white'>Your Bookings</h1>
        <table className="min-w-full bg-transparent border-collapse">
          <thead>
            <tr className="bg-transparent border-b border-gray-400 text-white">
              <th className="py-2 px-4 border">Admission Date</th>
              <th className="py-2 px-4 border">Discharge Date</th>
              <th className="py-2 px-4 border">Reason</th>
              <th className="py-2 px-4 border">Room Number</th>
              <th className="py-2 px-4 border">Outcome</th>
              <th className="py-2 px-4 border">Actions</th>
            </tr>
          </thead>
          <tbody>
            {bookings.map(booking => (
              <React.Fragment key={booking.hospitalizationID}>
                <tr className="border-b border-transparent text-gray-300">
                  <td className="py-2 px-4 border text-center">{booking.admissionDate}</td>
                  <td className="py-2 px-4 border text-center">{booking.dischargeDate || 'N/A'}</td>
                  <td className="py-2 px-4 border text-center">{booking.reason}</td>
                  <td className="py-2 px-4 border text-center">{booking.roomNumber}</td>
                  <td className="py-2 px-4 border text-center">{booking.outcome || 'N/A'}</td>
                  <td className="py-2 px-4 border text-center">
                    <div className="flex flex-col items-center">
                      <button onClick={() => toggleDetails(booking.hospitalizationID)} className="mb-2 hover:underline text-blue-500">
                        {expandedBookingId === booking.hospitalizationID ? 'Hide Details' : 'View Details'}
                      </button>
                      {new Date(booking.admissionDate) > new Date() && (
                        <button onClick={() => openDeleteModal(booking.hospitalizationID)} className="hover:underline text-red-500">Delete</button>
                      )}
                    </div>
                  </td>
                </tr>
                {expandedBookingId === booking.hospitalizationID && (
                  <tr className="bg-transparent border-gray-300">
                    <td colSpan="6" className="p-4">
                      <div className="space-y-2 border rounded-xl">
                        <div className="border-gray-200 py-2">
                          <h3 className="font-semibold text-xl mb-2 text-cyan-400 text-center">Room Details</h3>
                          <RoomDetails roomNumber={booking.roomNumber} />
                        </div>
                        <div className="border-gray-200 py-2">
                          <h3 className="font-semibold text-xl mb-2 text-cyan-400 text-center">Notes</h3>
                          <p className="text-gray-300 text-center">{booking.notes || 'No notes available'}</p>
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
      {showDeleteModal && (
        <div className="fixed inset-0 z-20 flex items-center justify-center bg-black bg-opacity-50">
          <div className="bg-white p-6 rounded shadow-lg">
            <h2 className="text-lg font-semibold mb-4">Confirm delete</h2>
            <p className="mb-4">Are you sure you want to delete this booking?</p>
            <div className="flex justify-end">
              <button onClick={closeDeleteModal} className="bg-gray-300 hover:bg-gray-400 text-gray-800 font-semibold py-2 px-4 rounded mr-2">
                Cancel
              </button>
              <button onClick={() => deleteBooking(bookingId)} className="bg-red-500 hover:bg-red-600 text-white font-semibold py-2 px-4 rounded">
                Delete
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

const RoomDetails = ({ roomNumber }) => {
  const [roomDetails, setRoomDetails] = useState(null);

  useEffect(() => {
    fetchRoomDetails();
  }, [roomNumber]);

  const fetchRoomDetails = async () => {
    try {
      const response = await axios.get(`/api/v1/room/${roomNumber}`);
      setRoomDetails(response.data);
    } catch (error) {
      console.error('Error fetching room details:', error);
    }
  };

  return (
    <div>
      {roomDetails ? (
        <div>
          <p className="text-gray-300">Room Number: {roomDetails.roomNumber}</p>
          <p className="text-gray-300">Room Type: {roomDetails.roomType}</p>
          <p className="text-gray-300">Capacity: {roomDetails.capacity}</p>
          <p className="text-gray-300">Status: {roomDetails.status}</p>
        </div>
      ) : (
        <p className="text-gray-300">Loading room details...</p>
      )}
    </div>
  );
};

export default PatientRoomBooking;