import { FaUserMd } from 'react-icons/fa';
import { FaUserFriends } from 'react-icons/fa';
import { FaCalendarAlt } from 'react-icons/fa';
import { FaClipboardList } from 'react-icons/fa';


const DoctorDashboard = ({ isSidebarOpen }) => {
    return (
      <div className={`p-6 transition-all duration-100 ${isSidebarOpen ? 'ml-64' : 'ml-14'}`}>
        <div className="mb-8">
          <h2 className="text-3xl font-semibold select-none">Welcome, John Doe</h2>
          <p className="text-gray-600 mt-2 select-none">
            At Green Hills, we believe that every patient deserves the highest quality care possible. Our commitment to excellence in healthcare is matched only by our compassion for those we serve.
          </p>
        </div>
  
        <div className="grid grid-cols-4 gap-4 mb-8 select-none">
          <div className="bg-white shadow-md p-6 rounded-lg text-center">
            <FaUserMd className="text-green-700 text-4xl mb-2" />
            <h3 className="text-2xl font-bold">6</h3>
            <p className="mt-2 text-gray-600">Doctors</p>
          </div>
          <div className="bg-white shadow-md p-6 rounded-lg text-center">
            <FaUserFriends className="text-blue-700 text-4xl mb-2" />
            <h3 className="text-2xl font-bold">5</h3>
            <p className="mt-2 text-gray-600">Patients</p>
          </div>
          <div className="bg-white shadow-md p-6 rounded-lg text-center">
            <FaCalendarAlt className="text-yellow-500 text-4xl mb-2" />
            <h3 className="text-2xl font-bold">3</h3>
            <p className="mt-2 text-gray-600">Appointments Today</p>
          </div>
          <div className="bg-white shadow-md p-6 rounded-lg text-center">
            <FaClipboardList className="text-red-500 text-4xl mb-2" />
            <h3 className="text-2xl font-bold">2</h3>
            <p className="mt-2 text-gray-600">Pending Appointments</p>
          </div>
        </div>
  
        <div className="mb-8 select-none">
          <h3 className="text-xl font-semibold mb-4">Upcoming Appointments</h3>
          <ul className="space-y-4">
            <li className="flex justify-between items-center bg-white p-4 rounded-lg shadow-md">
              <span>Kim Kardashiann</span>
              <span>Dr. Shrey Naik</span>
              <span>9:30 AM</span>
            </li>
            <li className="flex justify-between items-center bg-white p-4 rounded-lg shadow-md">
              <span>Shaun Lobo</span>
              <span>Dr. Shrey Naik</span>
              <span>10:00 AM</span>
            </li>
          </ul>
        </div>
  
        <div select-none>
          <h3 className="text-xl font-semibold mb-4 select-none">Doctors</h3>
          <ul className="grid grid-cols-2 gap-4">
            <li className="bg-white p-6 rounded-lg shadow-md text-center select-none">
              <span className="block text-lg font-semibold ">Shrey Naik</span>
              <span>Cardiology</span>
            </li>
            <li className="bg-white p-6 rounded-lg shadow-md text-center select-none">
              <span className="block text-lg font-semibold">Karan Kumar</span>
              <span>Gynecology</span>
            </li>
          </ul>
        </div>
      </div>
    );
  };
  
  export default DoctorDashboard;
  