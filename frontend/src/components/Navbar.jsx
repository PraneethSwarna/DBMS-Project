import { FaUserCircle } from 'react-icons/fa';

const Navbar = ({ isSidebarOpen }) => {
  return (
    <nav
      className={`bg-white shadow-md p-4 flex justify-between items-center transition-all duration-100 ${
        isSidebarOpen ? 'ml-64' : 'ml-14'
      }`}
    >
      <h1 className="text-[36px] font-semibold text-green-700 ml-8 select-none">Artemis Hospital</h1>
      <div className="flex items-center space-x-4">
        <span className="text-gray-600 select-none">Welcome, John Doe</span>
        <FaUserCircle className="text-green-700 text-2xl" />
      </div>
    </nav>
  );
};

export default Navbar;
