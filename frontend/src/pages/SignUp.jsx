import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import axios from 'axios';
import logo from '../assets/logo.png';
import styles from '../PageBackground.module.css';
// import gsap from 'gsap';
// import ScrollTrigger from 'gsap/ScrollTrigger';

// gsap.registerPlugin(ScrollTrigger);

const SignUp = () => {
  const [email, setEmail] = useState('');
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    dob: '',
    gender: 'Male',
    address: '',
    phone_number: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const [userInfo, setUserInfo] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (userInfo) {
      navigate('/');
    }
  }, [userInfo, navigate]);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubscribe = () => {
    alert(`Subscribed with: ${email}`);
    setEmail("");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      toast.error('Passwords do not match');
      return;
    }

    try {
      const response = await axios.post('http://localhost:8080/api/v1/user', formData);
      setUserInfo(response.data); // Save user data locally in component state
      toast.success('Account created successfully!');
      navigate('/');
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to create account');
    }
  };

  // useEffect(() => {
  //   const cards = document.querySelectorAll('.info-card');
  //   cards.forEach((card) => {
  //     gsap.from(card, {
  //       y: 100,
  //       opacity: 0,
  //       duration: 1,
  //       scrollTrigger: {
  //         trigger: card,
  //         start: 'top 80%',
  //         end: '+=300',
  //         toggleActions: 'play none none reverse',
  //       },
  //     });
  //   });
  // }, []);

  return (
    <div className={styles['page-background']}>
      <div className="absolute bg-black bg-opacity-30 inset-0 m-0"></div>
      <div className="relative flex bg-transparent items-center m-2 space-x-4">
        <img src={logo} loading="lazy" className="w-8 hover:animate-spin" alt="tailus logo" />
        <h2 className="text-3xl text-white font-bold mt-3">Atermis</h2>
      </div>
      <div className="absolute right-12 top-4 flex justify-center space-x-8">
        <a href="https://x.com/praneethswarna" className="cursor-pointer ">
          <span>
            <svg fill="#fff" xmlns="http://www.w3.org/2000/svg" width={24} height={24} viewBox="0 0 24 24">
              <path d="M24 4.557c-.883.392-1.832.656-2.828.775 1.017-.609 1.798-1.574 2.165-2.724-.951.564-2.005.974-3.127 1.195-.897-.957-2.178-1.555-3.594-1.555-3.179 0-5.515 2.966-4.797 6.045-4.091-.205-7.719-2.165-10.148-5.144-1.29 2.213-.669 5.108 1.523 6.574-.806-.026-1.566-.247-2.229-.616-.054 2.281 1.581 4.415 3.949 4.89-.693.188-1.452.232-2.224.084.626 1.956 2.444 3.379 4.6 3.419-2.07 1.623-4.678 2.348-7.29 2.04 2.179 1.397 4.768 2.212 7.548 2.212 9.142 0 14.307-7.721 13.995-14.646.962-.695 1.797-1.562 2.457-2.549z" />
            </svg>
          </span>
        </a>
        <a href="https://www.facebook.com/praneethchandra123/" className="cursor-pointer">
          <span>
            <svg fill="#fff" xmlns="http://www.w3.org/2000/svg" width={24} height={24} viewBox="0 0 24 24">
              <path d="M9 8h-3v4h3v12h5v-12h3.642l.358-4h-4v-1.667c0-.955.192-1.333 1.115-1.333h2.885v-5h-3.808c-3.596 0-5.192 1.583-5.192 4.615v3.385z" />
            </svg>
          </span>
        </a>
        <a href="https://www.instagram.com/praneethchandra123/" className="cursor-pointer">
          <span>
            <svg fill="#fff" xmlns="http://www.w3.org/2000/svg" width={24} height={24} viewBox="0 0 24 24">
              <path d="M12 2.163c3.204 0 3.584.012 4.85.07 3.252.148 4.771 1.691 4.919 4.919.058 1.265.069 1.645.069 4.849 0 3.205-.012 3.584-.069 4.849-.149 3.225-1.664 4.771-4.919 4.919-1.266.058-1.644.07-4.85.07-3.204 0-3.584-.012-4.849-.07-3.26-.149-4.771-1.699-4.919-4.92-.058-1.265-.07-1.644-.07-4.849 0-3.204.013-3.583.07-4.849.149-3.227 1.664-4.771 4.919-4.919 1.266-.057 1.645-.069 4.849-.069zm0-2.163c-3.259 0-3.667.014-4.947.072-4.358.2-6.78 2.618-6.98 6.98-.059 1.281-.073 1.689-.073 4.948 0 3.259.014 3.668.072 4.948.2 4.358 2.618 6.78 6.98 6.98 1.281.058 1.689.072 4.948.072 3.259 0 3.668-.014 4.948-.072 4.354-.2 6.782-2.618 6.979-6.98.059-1.28.073-1.689.073-4.948 0-3.259-.014-3.667-.072-4.947-.196-4.354-2.617-6.78-6.979-6.98-1.281-.059-1.69-.073-4.949-.073zm0 5.838c-3.403 0-6.162 2.759-6.162 6.162s2.759 6.163 6.162 6.163 6.162-2.759 6.162-6.163c0-3.403-2.759-6.162-6.162-6.162zm0 10.162c-2.209 0-4-1.79-4-4 0-2.209 1.791-4 4-4s4 1.791 4 4c0 2.21-1.791 4-4 4zm6.406-11.845c-.796 0-1.441.645-1.441 1.44s.645 1.44 1.441 1.44c.795 0 1.439-.645 1.439-1.44s-.644-1.44-1.439-1.44z" />
            </svg>
          </span>
        </a>
        <a href="https://github.com/mazerunner1001" className="cursor-pointer translate-y-[-4px]">
          <span>
            <svg fill="#fff" xmlns="http://www.w3.org/2000/svg" width={36} height={36} viewBox="0 0 28 28">
              <path
                d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z"
              />
            </svg>
          </span>
        </a>
      </div>
      <div className="relative w-full items-center justify-center text-center styles.black-area z-0 mt-28">
        <h1 className="font-extrabold text-4xl sm:text-5xl lg:text-[80px] tracking-tight text-center text-white">Welcome to Atermis</h1>
        <p className="mt-6 text-3xl text-center max-w-3xl lg:block hidden mx-auto text-white ">Step into a world of seamless healthcare management with our state-of-the-art hospital platform. Join a thriving community of patients, doctors, and staff where efficiency meets compassion. Sign up today to experience personalized care, book appointments, access medical records, and stay connected with your healthcare team. Your health journey begins here, and we’re with you every step of the way.</p>
        <div className="relative flex items-center justify-center mt-12">
          <Link to="/">
            <button className="bg-black text-white text-2xl px-8 py-3 rounded-full hover:bg-white hover:text-black transition duration-300">Explore</button>
          </Link>
          <div className="inline-flex items-center bg-gradient-to-r from-gray-800 to-gray-900 rounded-full ml-8 divide-x divide-black">
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              className="rounded-l-lg px-4 py-2 outline-none text-black text-lg w-full"
            />
            <button
              onClick={handleSubscribe}
              className="rounded-r-lg bg-black px-4 py-2 flex items-center text-lg justify-center text-white hover:text-black hover:bg-white"
            >
              <svg className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
              </svg>
              Subscribe
            </button>
          </div>
        </div>
        <div className=" items-stretch bg-white h-[200px] w-[2px] mx-auto mt-8"></div>
      </div>
      <div className="container pb-16 m-auto text-black md:px-12 mb-20">
        <div className="m-auto md:w-8/12 lg:w-6/12 xl:w-6/12 bg-transparent shadow-3xl relative">
          <div className="p-6 sm:p-16 bg-black rounded-3xl border bg-opacity-35">
            <h1 className="text-2xl font-bold leading-tight tracking-tight text-white md:text-3xl text-center">
              Create an account
            </h1>
            <form onSubmit={handleSubmit} className="space-y-4 md:space-y-6">
              <div>
                <label htmlFor="firstName" className="block mb-2 text-sm font-medium text-white">
                  First Name
                </label>
                <input
                  type="text"
                  name="firstName"
                  className="bg-gray-900 placeholder-gray-600 border-secondary text-white sm:text-md rounded-xl block w-full p-2.5"
                  placeholder="John"
                  value={formData.firstName}
                  onChange={handleChange}
                  required
                />
              </div>
              <div>
                <label htmlFor="lastName" className="block mb-2 text-sm font-medium text-white">
                  Last Name
                </label>
                <input
                  type="text"
                  name="lastName"
                  className="bg-gray-900 placeholder-gray-600 border-secondary text-white sm:text-md rounded-xl block w-full p-2.5"
                  placeholder="Doe"
                  value={formData.lastName}
                  onChange={handleChange}
                  required
                />
              </div>
              <div>
                <label htmlFor="dob" className="block mb-2 text-sm font-medium text-white">
                  Date of Birth
                </label>
                <input
                  type="date"
                  name="dob"
                  className="bg-gray-900 placeholder-gray-600 border-secondary text-white sm:text-md rounded-xl block w-full p-2.5"
                  value={formData.dob}
                  onChange={handleChange}
                  required
                />
              </div>
              <div>
                <label htmlFor="gender" className="block mb-2 text-sm font-medium text-white">
                  Gender
                </label>
                <select
                  name="gender"
                  className="bg-gray-900 placeholder-gray-600 border-secondary text-white sm:text-md rounded-xl block w-full p-2.5"
                  value={formData.gender}
                  onChange={handleChange}
                >
                  <option value="Male">Male</option>
                  <option value="Female">Female</option>
                  <option value="Other">Other</option>
                </select>
              </div>
              <div>
                <label htmlFor="address" className="block mb-2 text-sm font-medium text-white">
                  Address
                </label>
                <input
                  type="text"
                  name="address"
                  className="bg-gray-900 placeholder-gray-600 border-secondary text-white sm:text-md rounded-xl block w-full p-2.5"
                  placeholder="Address"
                  value={formData.address}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label htmlFor="phone_number" className="block mb-2 text-sm font-medium text-white">
                  Phone Number
                </label>
                <input
                  type="tel"
                  name="phone_number"
                  className="bg-gray-900 placeholder-gray-600 border-secondary text-white sm:text-md rounded-xl block w-full p-2.5"
                  placeholder="Phone Number"
                  value={formData.phone_number}
                  onChange={handleChange}
                />
              </div>
              <div>
                <label htmlFor="email" className="block mb-2 text-sm font-medium text-white">
                  Email
                </label>
                <input
                  type="email"
                  name="email"
                  className="bg-gray-900 placeholder-gray-600 border-secondary text-white sm:text-md rounded-xl block w-full p-2.5"
                  placeholder="johndoe@gmail.com"
                  value={formData.email}
                  onChange={handleChange}
                  required
                />
              </div>
              <div>
                <label htmlFor="password" className="block mb-2 text-sm font-medium text-white">
                  Password
                </label>
                <input
                  type="password"
                  name="password"
                  className="bg-gray-900 placeholder-gray-600 border-secondary text-white sm:text-md rounded-xl block w-full p-2.5"
                  placeholder="••••••••"
                  value={formData.password}
                  onChange={handleChange}
                  required
                />
              </div>
              <div>
                <label htmlFor="confirmPassword" className="block mb-2 text-sm font-medium text-white">
                  Confirm Password
                </label>
                <input
                  type="password"
                  name="confirmPassword"
                  className="bg-gray-900 placeholder-gray-600 border-secondary text-white sm:text-md rounded-xl block w-full p-2.5"
                  placeholder="••••••••"
                  value={formData.confirmPassword}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="flex items-center justify-center">
                <div className="group my-8 relative">
                  <div className="absolute -inset-1 rounded-full bg-gradient-to-r from-rose-400 to-sky-500 opacity-75 blur transition duration-500 group-hover:opacity-100"></div>
                  <button className="relative rounded-full bg-black px-24 py-3 text-lg text-white">
                    Create an account
                  </button>
                </div>
              </div>
              <p className="text-md font-light text-gray-500 dark:text-gray-400">
                Already have an account?{" "}
                <Link
                  to="/login"
                  className="text-md text-gray-500 hover:underline dark:text-primary-500"
                >
                  Login here
                </Link>
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignUp;
