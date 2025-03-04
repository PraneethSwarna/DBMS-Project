TRUNCATE TABLE users;

DELETE FROM surgery_doctor WHERE surgeryID IN (SELECT surgeryID FROM surgery);
DELETE FROM surgery_prescription WHERE surgeryID IN (SELECT surgeryID FROM surgery);

DELETE FROM prescription_medicine WHERE prescriptionID IN (SELECT prescriptionID FROM prescription);
DELETE FROM diagnosis;

ALTER TABLE medicine AUTO_INCREMENT = 1;

use dbms;

DELETE FROM homeConsultation;

ALTER TABLE hospitalizations
RENAME TO hospitalization;

ALTER TABLE nurse
ADD COLUMN password VARCHAR(255);

ALTER TABLE lab_result
RENAME COLUMN test_date TO testDate;

ALTER TABLE diagnosis
ADD CONSTRAINT fk_PrescriptionID
FOREIGN KEY (prescriptionID) REFERENCES prescription(prescriptionID);

DESCRIBE surgery;

-- Make diagnosisID, doctorID, appointmentDate, and appointmentTime nullable
ALTER TABLE appointment
MODIFY COLUMN diagnosisID INT NULL,
MODIFY COLUMN doctorID INT NULL,
MODIFY COLUMN appointmentDate DATE NULL,
MODIFY COLUMN appointmentTime TIME NULL;

-- Add status column with enum type including "Pending"
ALTER TABLE appointment
MODIFY COLUMN status ENUM('Pending', 'Scheduled', 'Cancelled', 'Completed') DEFAULT 'Pending';

ALTER TABLE surgery
MODIFY COLUMN surgeryType ENUM(
    'Appendectomy',
    'Cholecystectomy',
    'HerniaRepair',
    'Mastectomy',
    'CSection',
    'HipReplacement',
    'KneeReplacement',
    'CoronaryArteryBypass',
    'GastricBypass',
    'Tonsillectomy'
) NOT NULL;

select * from users;

DESCRIBE hospitalization;

ALTER TABLE user
RENAME COLUMN phone_number TO phoneNumber;

-- Step 1: Drop the existing primary key constraint
ALTER TABLE room DROP PRIMARY KEY;

-- Step 2: Remove the roomID column
ALTER TABLE room DROP COLUMN roomID;

-- Step 3: Add a primary key constraint to the roomNumber column
ALTER TABLE room ADD PRIMARY KEY (roomNumber);

DROP TABLE hospitalization;

CREATE TABLE Prescription (
    prescriptionID INT PRIMARY KEY AUTO_INCREMENT,
    prescriptionDate DATE
);

CREATE TABLE PrescriptionMedicine (
    prescriptionID INT,
    medicineID INT,
    dosage VARCHAR(50),
    frequency VARCHAR(50),
    duration INT,
    instructions VARCHAR(255),
    PRIMARY KEY (prescriptionID, medicineID),
    FOREIGN KEY (prescriptionID) REFERENCES Prescription(prescriptionID) ON DELETE CASCADE,
    FOREIGN KEY (medicineID) REFERENCES Medicine(medicineID)
);

DELETE from prescription where prescriptionID = 6;

CREATE TABLE surgery (
    surgeryID INT AUTO_INCREMENT PRIMARY KEY,
    surgeryDate DATE,
    surgeryType VARCHAR(255) NOT NULL,
    outcome VARCHAR(255),
    notes TEXT,
    patientID INT NOT NULL,
    FOREIGN KEY (patientID) REFERENCES user(patientID)
);

select * from surgery_doctor;

CREATE TABLE surgery_doctor (
    surgeryID INT,
    doctorID INT,
    PRIMARY KEY (surgeryID, doctorID),
    FOREIGN KEY (surgeryID) REFERENCES surgery(surgeryID),
    FOREIGN KEY (doctorID) REFERENCES doctor(doctorID)
);

CREATE TABLE surgery_prescription (
    surgeryID INT,
    prescriptionID INT,
    PRIMARY KEY (surgeryID, prescriptionID),
    FOREIGN KEY (surgeryID) REFERENCES surgery(surgeryID),
    FOREIGN KEY (prescriptionID) REFERENCES prescription(prescriptionID)
);

DROP TABLE medical_history;
DESCRIBE surgery;

-- Drop the existing home_consultation table
DROP TABLE IF EXISTS home_consultation;

-- Create the home_consultation table with the specified columns
CREATE TABLE home_consultation (
    homeConsultationID INT AUTO_INCREMENT PRIMARY KEY,
    consultationDate DATE NOT NULL,
    consultationTime TIME(6) NULL,
    doctorID INT NULL,
    notes TEXT,
    outcome VARCHAR(255),
    patientID INT NOT NULL,
    FOREIGN KEY (doctorID) REFERENCES doctor(doctorID),
    FOREIGN KEY (patientID) REFERENCES user(userID)
);

CREATE TABLE hospitalization (
    hospitalizationID INT PRIMARY KEY AUTO_INCREMENT,
    admissionDate DATE NOT NULL,
    dischargeDate DATE NULL,
    reason VARCHAR(255) NOT NULL,
    notes TEXT,
    patientID INT NOT NULL,
    roomNumber VARCHAR(10),
    FOREIGN KEY (patientID) REFERENCES user(userID),
    FOREIGN KEY (roomNumber) REFERENCES room(roomNumber)
);

CREATE TABLE medical_history (
    medicalHistoryID INT PRIMARY KEY AUTO_INCREMENT,
    diagnosis VARCHAR(255) NOT NULL,
    notes TEXT,
    patientID INT NOT NULL,
    recordDate DATE NOT NULL,
    treatment VARCHAR(255),
    FOREIGN KEY (patientID) REFERENCES user(userID)
);
