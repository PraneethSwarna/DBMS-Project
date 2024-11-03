import React, { useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import logo from '../assets/logo.png';
import ProfileMenu from './ProfileMenu';

const Navbar = () => {
  const [isScrolled, setIsScrolled] = useState(false);
  const location = useLocation();

  useEffect(() => {
    const handleScroll = () => {
      setIsScrolled(window.scrollY > 0);
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  const getLinkClass = (path) => {
    return location.pathname === path ? 'border-b-[3px] border-white' : '';
  };

  return (
    <>
      <header className={`fixed top-0 left-0 w-full flex pt-2 pb-1 z-20 transition-colors duration-150 ${isScrolled ? 'bg-black' : 'bg-gradient-to-b from-black to-transparent'}`}>
        <div className="container ml-5 flex items-center justify-between">
          <Link to="/" className="flex items-center">
            <img src={logo} alt="logo" id="logo" className="w-10 mr-2 " />
            <h1 className="text-white mt-2 text-3xl font-bold">Artemis</h1>
          </Link>
          <div className="ml-16 translate-y-[4px] xl:block select-none hidden mx-auto space-x-6">
            <Link to="/" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/')}`}>Home</Link>
            <Link to="/patient/appointment" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/popular')}`}>Appointments</Link>
            <Link to="/patient/surgery" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/top_rated')}`}>Surgery</Link>
            <Link to="/patient/home_consultation" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/upcoming')}`}>Home Consultations</Link>
            <Link to="/patient/room_booking" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/upcoming')}`}>Room Booking</Link>
            <Link to="/our_family" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/upcoming')}`}>Our Family</Link>
            <Link onClick={() => document.getElementById("contact-us").scrollIntoView({ behavior: "smooth" })} className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/upcoming')}`}>Contact Us</Link>
          </div>
          <ProfileMenu />
        </div>
      </header>
    </>
  );
};

export default Navbar;
