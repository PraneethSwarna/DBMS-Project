import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const ProtectedRoute = ({ children }) => {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  // Use current path to verify doctor/nurse routes
  const location = useLocation();
  const isDoctorRoute = location.pathname.startsWith("/doctor");
  const isNurseRoute = location.pathname.startsWith("/nurse");
  const isPatientRoute = location.pathname.startsWith("/patient");

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  if (isDoctorRoute && role !== "doctor") {
    return <Navigate to="/admin" replace />;
  }

  if (isNurseRoute && role !== "nurse") {
    return <Navigate to="/admin" replace />;
  }

  if (isPatientRoute && role !== "patient") {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;
