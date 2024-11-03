import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import ProfileMenu from './patient/ProfileMenu';
import logo from '../assets/logo.png';

const Navbar = () => {
  const [isScrolled, setIsScrolled] = useState(false);
  const location = useLocation();
  const navigate = useNavigate();

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
    return location.pathname === path ? 'border-b-2 border-white' : '';
  };

  const handleContactUsClick = () => {
    if (location.pathname !== '/') {
      navigate('/', { state: { scrollToContact: true } });
    } else {
      document.getElementById('contact-us').scrollIntoView({ behavior: 'smooth' });
    }
  };

  useEffect(() => {
    if (location.state?.scrollToContact) {
      document.getElementById('contact-us').scrollIntoView({ behavior: 'smooth' });
    }
  }, [location]);

  return (
    <header className={`fixed top-0 left-0 w-full flex pt-2 pb-1 z-20 transition-colors duration-150 ${isScrolled ? 'bg-black' : 'bg-gradient-to-b from-black to-transparent'}`}>
      <div className="container ml-5 flex items-center justify-between">
        <Link to="/" className="flex items-center">
          <img src={logo} alt="logo" id="logo" className="w-10 mr-2 " />
          <h1 className="text-white mt-2 text-3xl font-bold">Artemis</h1>
        </Link>
        <div className="ml-16 translate-y-[4px] xl:block select-none hidden mx-auto space-x-6">
          <Link to="/" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/')}`}>Home</Link>
          <Link to="/patient/appointment" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/patient/appointment')}`}>Appointments</Link>
          <Link to="/patient/surgery" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/patient/surgery')}`}>Surgery</Link>
          <Link to="/patient/home_consultation" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/patient/home_consultation')}`}>Home Consultations</Link>
          <Link to="/patient/room_booking" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/patient/room_booking')}`}>Room Booking</Link>
          <Link to="/our_family" className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/our_family')}`}>Our Family</Link>
          <button onClick={handleContactUsClick} className={`text-white hover:text-gray-500 font-medium font-sans pb-3 ${getLinkClass('/contact_us')}`}>Contact Us</button>
        </div>
        <ProfileMenu />
      </div>
    </header>
  );
};

export default Navbar;
