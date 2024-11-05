import React from 'react';
import { Route, createBrowserRouter, createRoutesFromElements, RouterProvider } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import styles from './toastStyles.module.css';
import MainLayout from './layouts/MainLayout';
import PatientLayout from './layouts/PatientLayout';
import PatientProfile from './pages/patient/PatientProfile';
import PatientAppointments from './pages/patient/PatientAppointment';
import PatientSurgery from './pages/patient/PatientSurgery';
import PatientHomeConsultation from './pages/patient/PatientHomeConsultation';
import PatientDoctorProfile from './pages/patient/PatientDoctorProfile';
import PatientRoomBooking from './pages/patient/PatientRoomBooking';
import DoctorLayout from './layouts/DoctorLayout';
import DoctorProfile from './pages/doctor/DoctorProfile';
import DoctorDashboard from './pages/doctor/DoctorDashboard';
import SignUp from './pages/SignUp';
import LoginPage from './pages/Login';
import HomePage from './pages/HomePage';
import Admin from './pages/Admin';
import NursePage from './pages/nurse/NursePage';
import ProtectedRoute from './ProtectedRoute';
import ViewDoctors from './pages/ViewDoctors';

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path='/' element={<MainLayout />}>
      <Route index element={<HomePage />} />
      <Route path='login' element={<LoginPage />} />
      <Route path='signup' element={<SignUp />} />
      <Route path='admin' element={<Admin />} />

      <Route
        path='patient'
        element={
          <ProtectedRoute>
            <PatientLayout />
          </ProtectedRoute>
        }
      >
        <Route path='appointment' element={<PatientAppointments />} />
        <Route path='surgery' element={<PatientSurgery />} />
        <Route path='home_consultation' element={<PatientHomeConsultation />} />
        <Route path='room_booking' element={<PatientRoomBooking />} />
        <Route path='profile' element={<PatientProfile />} />
        <Route path='doctor/:doctorId' element={<PatientDoctorProfile />} />
      </Route>

      <Route
        path='doctor'
        element={
          <ProtectedRoute>
            <DoctorLayout />
          </ProtectedRoute>
        }
      >
        <Route index element={<DoctorDashboard />} />
        <Route path='profile' element={<DoctorProfile />} />
        <Route path='view_doctors/:doctorId' element={<PatientDoctorProfile />} />
        <Route path='view_doctors' element={<ViewDoctors />} />
      </Route>

      <Route
        path='nurse'
        element={
          <ProtectedRoute>
            <NursePage />
          </ProtectedRoute>
        }
      >
        <Route path='doctor/:doctorId' element={<PatientDoctorProfile />} />
        <Route path='view_doctors' element={<ViewDoctors />} />
      </Route>
    </Route>
  )
);

const App = () => {
  return (
    <>
      <RouterProvider router={router} />
      <ToastContainer
        toastClassName={styles['toast-container']}
        bodyClassName={styles['toast-body']}
        closeButtonClassName={styles['close-button']}
        progressClassName={styles['progress-bar']}
        iconClassName={styles['toast-icon']}
      />
    </>
  );
}

export default App;
