import React from "react";
import { motion } from "framer-motion";
import { useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import Hero from "../assets/Hospital_2.png"
import Hero2 from "../assets/Hospital_1.png"
import Doctor from "../assets/Doctor.png"

const HomePage = () => {
    const navigate = useNavigate();

    const redirectToDashboard = () => {
        navigate("/dashboard");
    };

    return (
        <div className="bg-white text-gray-800 select-none font-sans">
            <Navbar />
            {/* Hero Section */}
            <section className="relative h-screen flex items-center justify-center bg-black">
                <img src={Hero} alt="Hospital" className="absolute inset-0 object-cover w-full h-full opacity-50" />
                <div className="flex justify-between">
                    <div className="relative flex flex-col items-center justify-center text-center text-white">
                        <motion.h1 initial={{ opacity: 0, y: -50 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 1 }}
                            className="text-4xl mt-8 md:text-[68px] font-bold">
                            Welcome to Artemis
                        </motion.h1>
                        <motion.p initial={{ opacity: 0, y: 50 }} animate={{ opacity: 1, y: 0 }} transition={{ duration: 1, delay: 0.5 }}
                            className="text-[18px] mt-8 max-w-xl">
                            Your Health, Our Commitment. Delivering exceptional medical services with over 20 years of excellence. Our state-of-the-art facilities and internationally acclaimed experts ensure compassionate, innovative care. Accredited globally and recipient of numerous awards, Artemis is your trusted partner in health. Join thousands of satisfied patients and experience the Artemis difference.
                        </motion.p>
                        <motion.button
                            onClick={() => document.getElementById("services-section").scrollIntoView({ behavior: "smooth" })}
                            className="mt-8 px-6 py-3 bg-black hover:bg-white rounded-full transition text-white hover:text-black border p-2"
                            initial={{ opacity: 0 }}
                            animate={{ opacity: 1 }}
                            transition={{ duration: 1, delay: 1 }}
                        >
                            Explore Our Services
                        </motion.button>

                    </div>
                    <img src={Doctor} alt="Doctor" className="mt-[80px] w-auto h-[660px] z-10 lg:block hidden" />
                </div>

            </section>

            {/* About Section */}
            <section className="py-16 bg-teal-100">
                <div className="container mx-auto px-6 md:px-12 text-center">
                    <motion.h2 initial={{ opacity: 0 }} whileInView={{ opacity: 1 }} transition={{ duration: 0.7, delay: 0.1 }}
                        className="text-3xl md:text-[70px] font-bold header-font text-teal-900">
                        About Our Hospital
                    </motion.h2>
                    <motion.p initial={{ opacity: 0, y: 30 }} whileInView={{ opacity: 1, y: 0 }} transition={{ duration: 0.7, delay: 0.3 }}
                        className="text-lg mt-8 max-w-2xl mx-auto">
                        With years of dedicated service, we have become a trusted name in healthcare. Our state-of-the-art facility and experienced staff ensure that every patient receives top-notch care.
                    </motion.p>
                    <div className="mt-8 grid grid-cols-1 md:grid-cols-3 gap-8">
                        <motion.div whileHover={{ scale: 1.05 }} className="bg-white shadow-lg rounded-lg p-6">
                            <img src={Hero2} alt="Modern Infrastructure" className="w-full rounded-lg h-48 object-cover" />
                            <h3 className="text-xl font-bold mt-4 text-teal-700">Modern Infrastructure</h3>
                            <p className="mt-2">We leverage advanced technology to offer comprehensive healthcare solutions.</p>
                        </motion.div>
                        <motion.div whileHover={{ scale: 1.05 }} className="bg-white shadow-lg rounded-lg p-6">
                            <img src="https://img.freepik.com/premium-photo/successful-team-medical-doctors-are-looking-camera-smiling-while-standing-hospital_939033-6150.jpg" alt="Experienced Team" className="w-full rounded-lg h-48 object-cover" />
                            <h3 className="text-xl font-bold mt-4 text-teal-700">Experienced Team</h3>
                            <p className="mt-2">Our dedicated team of experts brings years of experience in medical services.</p>
                        </motion.div>
                        <motion.div whileHover={{ scale: 1.05 }} className="bg-white shadow-lg rounded-lg p-6">
                            <img src="https://t3.ftcdn.net/jpg/02/66/13/68/360_F_266136824_wN5sDX6HVYMc4eaLwSgrIU7QhV1VOQcA.jpg" alt="Advanced Equipment" className="w-full rounded-lg h-48 object-cover" />
                            <h3 className="text-xl font-bold mt-4 text-teal-700">Advanced Equipment</h3>
                            <p className="mt-2">Our facility is equipped with cutting-edge technology for precise diagnosis and treatment.</p>
                        </motion.div>
                    </div>
                </div>
            </section>

            {/* Services Section */}
            <section id="services-section" className="py-16 bg-blue-50">
                <div className="container mx-auto px-6 md:px-12 text-center">
                    <motion.h2 initial={{ opacity: 0 }} whileInView={{ opacity: 1 }} transition={{ duration: 0.7, delay: 0.1 }}
                        className="text-3xl md:text-[70px] font-bold header-font text-blue-800">
                        Our Services
                    </motion.h2>
                    <motion.p initial={{ opacity: 0, y: 30 }} whileInView={{ opacity: 1, y: 0 }} transition={{ duration: 0.7, delay: 0.3 }}
                        className="text-lg mt-8 max-w-2xl mx-auto">
                        We offer a wide range of specialized services to meet your healthcare needs.
                    </motion.p>

                    {/* Appointments Section */}
                    <section className="py-8 mt-8 rounded-xl bg-blue-100 flex items-center justify-between">
                        <motion.div
                            className="w-1/2 px-6"
                            whileHover={{ scale: 1.05 }}
                            initial={{ opacity: 0, x: -50 }}
                            whileInView={{ opacity: 1, x: 0 }}
                            transition={{ duration: 0.7 }}
                        >
                            <div className="relative group overflow-hidden rounded-lg shadow-lg">
                                <img src="https://media.istockphoto.com/id/1319031310/photo/doctor-writing-a-medical-prescription.jpg?s=612x612&w=0&k=20&c=DWZGM8lBb5Bun7cbxhKT1ruVxRC_itvFzA9jxgoA0N8=" alt="Appointments" className="transition-transform duration-300 group-hover:scale-110" />
                                <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                                    <p className="text-white text-lg font-semibold">Book Your Appointment Today</p>
                                </div>
                            </div>
                        </motion.div>
                        <motion.div className="w-1/2 px-6 text-left" initial={{ opacity: 0, x: 50 }} whileInView={{ opacity: 1, x: 0 }} transition={{ duration: 0.7 }}>
                            <h3 className="text-2xl font-semibold text-blue-900">Appointments</h3>
                            <p className="mt-4">Get easy and quick access to our healthcare experts. Schedule an appointment that fits your needs and receive personalized care at your convenience. Our team of experienced professionals is here to ensure a seamless healthcare journey for you.</p>
                            <button onClick={() => navigate("/appointments")} className="mt-4 px-4 py-2 bg-blue-900 text-white rounded-lg hover:bg-blue-700 transition">
                                Book Appointment
                            </button>
                        </motion.div>
                    </section>

                    {/* Surgeries Section */}
                    <section className="py-16">
                        <div className="container mx-auto px-6 md:px-12 text-center">
                            <p className="text-lg mt-8 max-w-2xl mx-auto">We offer a comprehensive range of specialized surgeries to meet your healthcare needs. Each surgery is performed by expert surgeons using state-of-the-art technology to ensure the best outcomes for our patients.</p>
                            <div className="overflow-x-auto mt-8 flex space-x-6 py-4 scrollbar-thin scrollbar-thumb-blue-400 no-scrollbar">
                                {[
                                    { title: "Appendectomy", description: "Safe and effective removal of the appendix.", image: "https://img.freepik.com/free-photo/different-doctors-doing-surgical-procedure-patient_23-2148962491.jpg" },
                                    { title: "Cholecystectomy", description: "Minimally invasive gallbladder removal.", image: "https://madeforthismoment.asahq.org/wp-content/uploads/2021/07/Hernia-Surgery-iStock-Image-1024x683.jpg" },
                                    { title: "Hernia Repair", description: "Surgical solution for hernia discomfort.", image: "https://img.freepik.com/free-photo/doctors-doing-surgical-procedure-patient_23-2148962500.jpg" },
                                    { title: "Mastectomy", description: "Comprehensive care for breast surgery.", image: "https://media.istockphoto.com/id/864573868/photo/medical-team-performing-surgical-operation-in-modern-operating-room.jpg?s=612x612&w=0&k=20&c=zXF_9Ras9TchDAEksAbaUzsB3a6ObdUU8gZAAVb93OI=" },
                                    { title: "C-Section", description: "Safe delivery with care and precision.", image: "https://cdn.pixabay.com/photo/2016/11/08/05/29/operation-1807543_640.jpg" },
                                    { title: "Hip Replacement", description: "Relieve pain and restore mobility.", image: "https://img.freepik.com/premium-photo/surgeons-operating-hd-8k-wallpaper-stock-photographic-image_853645-60594.jpg" },
                                    { title: "Knee Replacement", description: "Get back to pain-free movement.", image: "https://t3.ftcdn.net/jpg/04/77/21/58/360_F_477215899_zYi33s9MY3x2X3bNm4orqCluoofbjZt5.jpg" },
                                    { title: "Coronary Artery Bypass", description: "Advanced cardiac surgery.", image: "https://img.freepik.com/premium-photo/surgery-operation-hd-8k-wallpaper-stock-photographic-image_1030895-31876.jpg" },
                                    { title: "Gastric Bypass", description: "Weight-loss surgery with care.", image: "https://img.freepik.com/free-photo/ordinary-busy-day-surgeon_329181-19717.jpg" },
                                    { title: "Tonsillectomy", description: "Relief from chronic throat issues.", image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTku223en9k0AeLXStrItYXYpkKk1eKAXhWfA&s" }
                                ].map((surgery, index) => (
                                    <motion.div
                                        key={index}
                                        whileHover={{ scale: 1.05 }}
                                        initial={{ opacity: 0, x: 50 }}
                                        whileInView={{ opacity: 1, x: 0 }}
                                        transition={{
                                            duration: 0.5
                                             // Adds staggered delay effect based on the index
                                        }}
                                        className="bg-white shadow-lg rounded-lg p-4 min-w-[300px] cursor-pointer transition-transform"
                                    >
                                        <img src={surgery.image} alt={surgery.title} className="rounded-lg h-48 w-full object-cover mb-4" />
                                        <h3 className="text-xl font-semibold text-blue-800">{surgery.title}</h3>
                                        <p className="mt-2 text-gray-700">{surgery.description}</p>
                                    </motion.div>
                                ))}
                            </div>
                        </div>
                    </section>


                    {/* Home Consultation Section */}
                    <section className="py-8 mt-8 bg-green-100 text-center">
                        <div className="container mx-auto flex items-center justify-center gap-8 px-6 md:px-12">
                            <motion.div className="relative group overflow-hidden rounded-lg shadow-lg w-full md:w-1/2" whileHover={{ scale: 1.05 }} transition={{ duration: 0.3 }}>
                                <img src="https://medintu.in/wp-content/uploads/2021/07/Untitled-design-9-768x512.jpg" alt="Home Consultation" className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110" />
                                <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                                    <p className="text-white text-lg font-semibold">Schedule a Home Consultation</p>
                                </div>
                            </motion.div>
                            <motion.div className="text-left w-full md:w-1/2" initial={{ opacity: 0, x: 50 }} whileInView={{ opacity: 1, x: 0 }} transition={{ duration: 0.7 }}>
                                <h3 className="text-2xl font-semibold text-green-900">Home Consultation</h3>
                                <p className="mt-4">Experience quality healthcare from the comfort of your home. Our home consultation service allows you to connect with our medical experts for personalized advice, diagnosis, and treatment.</p>
                                <button onClick={() => navigate("/home-consultation")} className="mt-4 px-4 py-2 bg-green-900 text-white rounded-lg hover:bg-green-700 transition">
                                    Schedule a Home Consultation
                                </button>
                            </motion.div>
                        </div>
                    </section>


                    {/* Room Booking Section */}
                    <section className="py-8 mt-8 bg-yellow-100 text-center">
                        <div className="container mx-auto flex items-center justify-center gap-8 px-6 md:px-12">
                            <motion.div className="text-left w-full md:w-1/2" initial={{ opacity: 0, x: -50 }} whileInView={{ opacity: 1, x: 0 }} transition={{ duration: 0.7 }}>
                                <h3 className="text-2xl font-semibold text-yellow-900">Room Booking</h3>
                                <p className="mt-4">Book a comfortable, modern room tailored to your recovery needs. Our rooms are designed to provide a tranquil environment with all necessary amenities to ensure a comfortable stay.</p>
                                <button onClick={() => navigate("/room-booking")} className="mt-4 px-4 py-2 bg-yellow-900 text-white rounded-lg hover:bg-yellow-700 transition">
                                    Book a Room
                                </button>
                            </motion.div>
                            <motion.div className="relative group overflow-hidden rounded-lg shadow-lg w-full md:w-1/2" whileHover={{ scale: 1.05 }} transition={{ duration: 0.3 }}>
                                <img src="https://media.istockphoto.com/id/1364971557/photo/hospital-recovery-room-with-beds-and-chairs-3d-rendering.jpg?s=612x612&w=0&k=20&c=qpLCCYKBxWiVpV74zLbsV69Trb0ga8plCIsx7h7CLAw=" alt="Room Booking" className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110" />
                                <div className="absolute inset-0 bg-black bg-opacity-50 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                                    <p className="text-white text-lg font-semibold">Book a Comfortable Room</p>
                                </div>
                            </motion.div>
                        </div>
                    </section>

                </div>
            </section>

            {/* Testimonials Section */}
            <section className="py-16 bg-lime-50">
                <div className="container mx-auto px-6 md:px-12 text-center">
                    <motion.h2 initial={{ opacity: 0 }} whileInView={{ opacity: 1 }} transition={{ duration: 0.7, delay: 0.1 }}
                        className="text-3xl md:text-[70px] font-bold header-font text-lime-900">
                        What Our Patients Say
                    </motion.h2>
                    <div className="mt-8 space-y-6 w-4/5 mx-auto">
                        <motion.div
                            initial={{ opacity: 0, x: -100 }}
                            whileInView={{ opacity: 1, x: 0 }}
                            transition={{ duration: 0.7, delay: 0.1 }}
                            className="bg-lime-100 p-6 rounded-3xl shadow-lg flex items-center space-x-12"
                        >
                            <img
                                src="https://static01.nyt.com/newsgraphics/2020/11/12/fake-people/4b806cf591a8a76adfc88d19e90c8c634345bf3d/fallbacks/mobile-01.jpg"
                                className="w-20 h-20 rounded-full ml-8 object-cover"
                                alt="User"
                            />
                            <div className="w-3/4">
                                <p className="text-lg">“From the moment I walked into Artemis, I was met with genuine warmth and professionalism. The doctors explained every step of my treatment, ensuring I felt safe and informed. The nursing staff was exceptionally attentive, always with a reassuring smile. Artemis exceeded all my expectations.”</p>
                                <h3 className="font-semibold mt-4">- Jane Doe</h3>
                            </div>
                        </motion.div>

                        <motion.div
                            initial={{ opacity: 0, x: 100 }}
                            whileInView={{ opacity: 1, x: 0 }}
                            transition={{ duration: 0.7, delay: 0.1 }}
                            className="bg-lime-100 p-6 rounded-3xl shadow-lg flex items-center space-x-12"
                        >
                            <div className="w-3/4 ml-16">
                                <p className="text-lg">“Artemis stands out as a beacon of excellence in healthcare. The staff’s dedication to patient welfare is evident in every interaction. The facilities are top-notch, and the medical team’s expertise instills confidence and trust. I am deeply grateful for the support and care I received at Artemis.”</p>
                                <h3 className="font-semibold mt-4">- John Travolta</h3>
                            </div>
                            <img
                                src="https://d2v5dzhdg4zhx3.cloudfront.net/web-assets/images/storypages/short/ai_face_generator/ai_face_generator/webp/thumb_image/002.webp"
                                className="w-20 h-20 rounded-full object-cover"
                                alt="User"
                            />
                        </motion.div>
                    </div>
                </div>
            </section>

            {/* footer Section */}
            <div className="text-center pt-12 bg-lime-50">
                <motion.h2 initial={{ opacity: 0 }} whileInView={{ opacity: 1 }} transition={{ duration: 0.7, delay: 0.1 }}
                    className="text-3xl md:text-[70px] font-bold header-font text-lime-900 text-center mb-8">
                    Get In Touch
                </motion.h2>
                <Footer />
            </div>
        </div>
    );
};

export default HomePage;
