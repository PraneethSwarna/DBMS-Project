import React from 'react';
import { Route, createBrowserRouter, createRoutesFromElements, RouterProvider } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import styles from './toastStyles.module.css';
import MainLayout from './layouts/MainLayout';
import SignUp from './pages/SignUp';
import LoginPage from './pages/Login';
import HomePage from './pages/HomePage';
import DoctorPage from './pages/DoctorPage';
import Address from './components/Address';

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path='/' element={<MainLayout />}>
      <Route index element={<HomePage />} />
      <Route path='login' element={<LoginPage />} />
      <Route path='signup' element={<SignUp />} />
      <Route path='doctor' element={<DoctorPage />} />
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