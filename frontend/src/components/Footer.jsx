import React from "react";
import { FaMapMarkerAlt, FaPhoneAlt, FaEnvelope, FaFacebook, FaInstagram } from "react-icons/fa";

const Footer = () => {
  return (
    <div className="bg-black text-white">
      {/* Map Section */}
      <div className="w-full h-96">
        <iframe
          width="100%"
          height="100%"
          frameBorder="0"
          title="Location Map"
          src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d887.3498887346307!2d81.21479548814928!3d27.175176191999295!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x4739dd99bfd25821!2zMjcCsDEwJzMwLjYiTiA4McKwMTInNTUuMiJF!5e0!3m2!1sen!2sin!4v1672054582033!5m2!1sen!2sin"
          allowFullScreen
          className="rounded-none"
        ></iframe>
      </div>

      {/* Footer Section */}
      <div className="container mt-8 mx-auto flex flex-col md:flex-row justify-around items-center space-y-6 md:space-y-0">
        
        {/* Address Section */}
        <div className="flex items-center space-x-3">
          <FaMapMarkerAlt className="text-blue-500" size={30} />
          <div>
            <h2 className="font-semibold">Our Address:</h2>
            <p>3 Ami'ad Street, Tel-Aviv<br />Yafo, 6108401 Israel</p>
          </div>
        </div>

        {/* Phone Section */}
        <div className="flex items-center space-x-3">
          <FaPhoneAlt className="text-blue-500" size={30} />
          <div>
            <h2 className="font-semibold">Call Us:</h2>
            <p>054-624-1163<br />Fax/Call: 03-6814052</p>
          </div>
        </div>

        {/* Email Section */}
        <div className="flex items-center space-x-3">
          <FaEnvelope className="text-blue-500" size={30} />
          <div>
            <h2 className="font-semibold">Email Us at:</h2>
            <p>Layla@housethree.co.il</p>
          </div>
        </div>

        {/* Social Media Section */}
        <div className="flex space-x-4">
          <a href="#" aria-label="Instagram" className="text-pink-600 hover:text-white">
            <FaInstagram size={30} />
          </a>
          <a href="#" aria-label="Facebook" className="text-blue-700 hover:text-white">
            <FaFacebook size={30} />
          </a>
        </div>
      </div>

      {/* Footer Bottom */}
      <div className="text-center mt-16 mb-8">
        <p>&copy; 2024 All Copyrights Reserved To Artemis Hospitals</p>
        <div className="flex justify-center space-x-4 mt-2">
          <a href="/privacy-policy" className="hover:text-white">Privacy Policy</a>
          <a href="/terms-conditions" className="hover:text-white">Terms & Conditions</a>
        </div>
      </div>
    </div>
  );
};

export default Footer;
