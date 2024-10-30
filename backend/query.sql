TRUNCATE TABLE users;

ALTER TABLE users AUTO_INCREMENT = 1;

use dbms;

ALTER TABLE rooms
RENAME TO room;

ALTER TABLE appointment
RENAME COLUMN appointment_date TO appointmentDate;

select * from users;

DESCRIBE diagnosis;

ALTER TABLE room
RENAME COLUMN room_type TO roomType;

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


