import React, { useState } from "react";
import axios from "axios";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { FaEye, FaEyeSlash } from 'react-icons/fa'; // Import Font Awesome icons

const LoginPage = () => {
    const [role, setRole] = useState("Doctor");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isPasswordVisible, setIsPasswordVisible] = useState(false);

    const handleRoleChange = (e) => {
        setRole(e.target.value);
    };

    const handleLogin = async (e) => {
        e.preventDefault();
    
        try {
            const apiUrl = role === "Doctor"
                ? "http://localhost:8080/api/v1/doctor/login"
                : "http://localhost:8080/api/v1/nurse/login";
    
            const response = await axios.post(apiUrl, { email, password });
            const { token, role: returnedRole, doctorId, nurseId } = response.data;
    
            // Save token and role to localStorage
            localStorage.setItem("token", token);
            localStorage.setItem("role", returnedRole);

            // Save the specific ID based on role
            if (returnedRole === "doctor" && doctorId) {
                localStorage.setItem("doctorId", doctorId);
            } else if (returnedRole === "nurse" && nurseId) {
                localStorage.setItem("nurseId", nurseId);
            }
            
            // Redirect based on role
            window.location.href = returnedRole === "doctor" ? "/doctor" : "/nurse";
        } catch (err) {
            // Error handling
            if (err.response) {
                if (err.response.status === 401) {
                    toast.error("Invalid email or password. Please try again.");
                } else if (err.response.status === 500) {
                    toast.error("Server error. Please try again later.");
                } else {
                    toast.error(err.response.data.message || "An error occurred. Please try again.");
                }
            } else {
                toast.error("Network error. Please check your connection.");
            }
        }
    };

    const togglePasswordVisibility = () => {
        setIsPasswordVisible(!isPasswordVisible);
    };
    
    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-100">
            <div className="w-full max-w-sm bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-2xl font-semibold text-center text-gray-800 mb-6">Admin Login</h2>
                <form onSubmit={handleLogin} className="space-y-4">
                    <div>
                        <label htmlFor="role" className="block text-gray-600 mb-2">Select Role</label>
                        <select
                            id="role"
                            value={role}
                            onChange={handleRoleChange}
                            className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                        >
                            <option value="Doctor">Doctor</option>
                            <option value="Nurse">Nurse</option>
                        </select>
                    </div>
                    <div>
                        <label htmlFor="email" className="block text-gray-600 mb-2">Email</label>
                        <input
                            id="email"
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Enter your email"
                            className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                        />
                    </div>
                    <div>
                        <label htmlFor="password" className="block text-gray-600 mb-2">Password</label>
                        <div className="relative">
                            <input
                                id="password"
                                type={isPasswordVisible ? "text" : "password"}
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                placeholder="Enter your password"
                                className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-black"
                            />
                            <button
                                type="button"
                                onClick={togglePasswordVisibility}
                                className="absolute inset-y-0 right-0 pr-3 flex items-center text-gray-500"
                            >
                                {isPasswordVisible ? <FaEyeSlash /> : <FaEye />}
                            </button>
                        </div>
                    </div>
                    <button
                        type="submit"
                        className="w-full bg-black text-white font-semibold py-2 rounded-lg hover:bg-gray-400 hover:text-black transition duration-200"
                    >
                        Login
                    </button>
                </form>
            </div>
            <ToastContainer />
        </div>
    );
};

export default LoginPage;
