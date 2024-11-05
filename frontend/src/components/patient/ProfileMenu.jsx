import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const ProfileMenu = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleDropdown = (event) => {
    event.stopPropagation();
    setIsOpen(!isOpen);
  };

  const closeDropdown = () => {
    setIsOpen(false);
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (isOpen && !event.target.closest('#dropdownAvatar')) {
        closeDropdown();
      }
    };

    window.addEventListener('click', handleClickOutside);

    return () => {
      window.removeEventListener('click', handleClickOutside);
    };
  }, [isOpen]);

  const navigate = useNavigate();

  const logoutHandler = () => {
    // Remove token and user ID from localStorage
    localStorage.removeItem('token');
    localStorage.removeItem('userId');

    // Navigate to login page
    navigate('/login');
  };

  return (
    <>
      <button
        id="dropdownUserAvatarButton"
        data-dropdown-toggle="dropdownAvatar"
        className="md:block hidden absolute top-0 right-10 w-8 h-8 mt-4 mb-2 hover:cursor-pointer bg-gray-800 rounded-full md:me-0"
        type="button"
        onClick={toggleDropdown}
      >
        <span className="sr-only">Open user menu</span>
        <img
          className="w-8 h-8 rounded-full"
          src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgB730p0ChSl_CNr5N6n05AGzEtEAhFypOFg&s"
          alt="user photo"
        />
      </button>
      {/* Dropdown menu */}
      <div
        id="dropdownAvatar"
        className={`z-10 ${isOpen ? 'block' : 'hidden'} absolute right-10 top-[72px] bg-gray-900 divide-y divide-gray-700 rounded-lg shadow w-44`}
      >
        <div className="px-4 py-3 text-sm text-white">
          <div>Welcome</div>
        </div>
        <ul
          className="py-2 text-sm text-gray-300 dark:text-gray-200"
          aria-labelledby="dropdownUserAvatarButton"
        >
          <li>
            <Link
              to="/patient/profile"
              className="block px-4 py-2 hover:bg-gray-800"
            >
              Dashboard
            </Link>
          </li>
          <li>
            <button
              onClick={logoutHandler}
              className="block px-4 py-2 text-sm text-gray-300 hover:bg-gray-800 w-full text-left"
            >
              Sign out
            </button>
          </li>
        </ul>
      </div>
    </>
  );
};

export default ProfileMenu;
