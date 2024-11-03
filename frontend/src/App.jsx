import React from 'react';
import { Route, createBrowserRouter, createRoutesFromElements, RouterProvider } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import styles from './toastStyles.module.css';
import MainLayout from './layouts/MainLayout';
import PatientLayout from './layouts/PatientLayout';
import DoctorLayout from './layouts/DoctorLayout';
import SignUp from './pages/SignUp';
import LoginPage from './pages/Login';
import HomePage from './pages/HomePage';
import Admin from './pages/Admin';
import NursePage from './pages/NursePage';
import ProtectedRoute from './ProtectedRoute';
import PatientProfile from './pages/PatientProfile';
import PatientSettings from './pages/PatientSettings';
import DoctorDashboard from './components/DoctorDashboard';

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
        <Route path='profile' element={<PatientProfile />} /> 
        <Route path='settings' element={<PatientSettings />} />
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
        {/* <Route path='profile' element={<DoctorProfile />} /> */}
      </Route>

      <Route
        path='nurse'
        element={
          <ProtectedRoute>
            <NursePage />
          </ProtectedRoute>
        }
      />
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
