TRUNCATE TABLE users;

DELETE FROM surgery_doctor WHERE surgeryID IN (SELECT surgeryID FROM surgery);
DELETE FROM surgery_prescription WHERE surgeryID IN (SELECT surgeryID FROM surgery);

DELETE FROM prescription_medicine WHERE prescriptionID IN (SELECT prescriptionID FROM prescription);
DELETE FROM diagnosis;

ALTER TABLE diagnosis AUTO_INCREMENT = 1;

use dbms;

ALTER TABLE users
RENAME TO user;

ALTER TABLE user
RENAME COLUMN patientID TO userID;

select * from users;

DESCRIBE diagnosis;

ALTER TABLE user
RENAME COLUMN phone_number TO phoneNumber;

DROP TABLE prescription;

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

DROP TABLE surgery;

