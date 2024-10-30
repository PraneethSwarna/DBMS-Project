package dev.praneeth.backend.Prescription;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PrescriptionDao {

    private final JdbcTemplate jdbcTemplate;

    public PrescriptionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Prescription> prescriptionRowMapper = (rs, rowNum) -> {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionID(rs.getInt("prescriptionID"));
        
        // Handle possible null for prescriptionDate
        java.sql.Date sqlDate = rs.getDate("prescriptionDate");
        prescription.setPrescriptionDate(sqlDate != null ? sqlDate.toLocalDate() : null);

        return prescription;
    };

    private final RowMapper<PrescriptionMedicine> prescriptionMedicineRowMapper = (rs, rowNum) -> {
        PrescriptionMedicine medicine = new PrescriptionMedicine();
        medicine.setPrescriptionID(rs.getInt("prescriptionID"));
        medicine.setMedicineID(rs.getInt("medicineID"));
        medicine.setDosage(rs.getString("dosage"));
        medicine.setFrequency(rs.getString("frequency"));
        medicine.setDuration(rs.getInt("duration"));
        medicine.setInstructions(rs.getString("instructions"));
        return medicine;
    };

    public List<Prescription> getAllPrescriptions() {
        String sql = "SELECT * FROM prescription";
        List<Prescription> prescriptions = jdbcTemplate.query(sql, prescriptionRowMapper);

        for (Prescription prescription : prescriptions) {
            List<PrescriptionMedicine> medicines = getMedicinesByPrescriptionId(prescription.getPrescriptionID());
            prescription.setMedicines(medicines);
        }
        return prescriptions;
    }

    public Optional<Prescription> getPrescriptionById(Integer prescriptionID) {
        String sql = "SELECT * FROM prescription WHERE prescriptionID = ?";
        List<Prescription> prescriptions = jdbcTemplate.query(sql, prescriptionRowMapper, prescriptionID);
        Optional<Prescription> prescriptionOptional = prescriptions.stream().findFirst();

        prescriptionOptional.ifPresent(prescription -> {
            List<PrescriptionMedicine> medicines = getMedicinesByPrescriptionId(prescriptionID);
            prescription.setMedicines(medicines);
        });

        return prescriptionOptional;
    }

    public void addPrescription(Prescription prescription) {
        String sql = "INSERT INTO prescription (prescriptionDate) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, java.sql.Date.valueOf(prescription.getPrescriptionDate()));
            return ps;
        }, keyHolder);

        // Check if the keyHolder contains a key; if not, handle it appropriately
        @SuppressWarnings("null")
        Integer prescriptionID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;

        if (prescriptionID == null) {
            throw new IllegalStateException("Failed to retrieve generated prescriptionID");
        }

        List<PrescriptionMedicine> medicines = prescription.getMedicines();
        for (PrescriptionMedicine medicine : medicines) {
            medicine.setPrescriptionID(prescriptionID);
            addPrescriptionMedicine(medicine);
        }
    }

    public void deletePrescription(Integer prescriptionID) {
        String deleteMedicinesSql = "DELETE FROM prescription_medicine WHERE prescriptionID = ?";
        jdbcTemplate.update(deleteMedicinesSql, prescriptionID);

        String deletePrescriptionSql = "DELETE FROM prescription WHERE prescriptionID = ?";
        jdbcTemplate.update(deletePrescriptionSql, prescriptionID);
    }

    // Updated method for updating prescription to include conditional update for prescriptionDate
    public void updatePrescription(Integer prescriptionID, PrescriptionUpdateRequest updateRequest) {
        Optional<Prescription> existingPrescription = getPrescriptionById(prescriptionID);
        if (existingPrescription.isEmpty()) {
            throw new IllegalStateException("Prescription with ID " + prescriptionID + " does not exist");
        }

        // Update only if prescriptionDate is provided
        if (updateRequest.getPrescriptionDate() != null) {
            String sql = "UPDATE prescription SET prescriptionDate = ? WHERE prescriptionID = ?";
            jdbcTemplate.update(sql, java.sql.Date.valueOf(updateRequest.getPrescriptionDate()), prescriptionID);
        }
    
        // Update or delete existing medicines
        if (updateRequest.getMedicines() != null) {
            for (MedicineUpdateRequest medicineUpdate : updateRequest.getMedicines()) {
                if (medicineUpdate.isDelete()) {
                    removeMedicine(prescriptionID, medicineUpdate.getOriginalMedicineID());
                } else {
                    updateMedicineAttributes(prescriptionID, medicineUpdate);
                }
            }
        }
    
        // Add new medicines
        if (updateRequest.getNewMedicines() != null) {
            for (MedicineUpdateRequest newMedicine : updateRequest.getNewMedicines()) {
                PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
                prescriptionMedicine.setPrescriptionID(prescriptionID);
                prescriptionMedicine.setMedicineID(newMedicine.getNewMedicineID());
                prescriptionMedicine.setDosage(newMedicine.getDosage());
                prescriptionMedicine.setFrequency(newMedicine.getFrequency());
                prescriptionMedicine.setDuration(newMedicine.getDuration());
                prescriptionMedicine.setInstructions(newMedicine.getInstructions());
                addPrescriptionMedicine(prescriptionMedicine);
            }
        }
    }

    // New method to update medicine attributes
    private void updateMedicineAttributes(Integer prescriptionID, MedicineUpdateRequest medicineUpdate) {
        String sql = "UPDATE prescription_medicine SET ";
        List<Object> params = new ArrayList<>();
        boolean first = true;

        // Prepare SQL statement based on available fields
        if (medicineUpdate.getDosage() != null) {
            sql += (first ? "" : ", ") + "dosage = ?";
            params.add(medicineUpdate.getDosage());
            first = false;
        }
        if (medicineUpdate.getFrequency() != null) {
            sql += (first ? "" : ", ") + "frequency = ?";
            params.add(medicineUpdate.getFrequency());
            first = false;
        }
        if (medicineUpdate.getDuration() != null) {
            sql += (first ? "" : ", ") + "duration = ?";
            params.add(medicineUpdate.getDuration());
            first = false;
        }
        if (medicineUpdate.getInstructions() != null) {
            sql += (first ? "" : ", ") + "instructions = ?";
            params.add(medicineUpdate.getInstructions());
        }

        // Finalize the SQL statement
        if (!params.isEmpty()) {
            sql += " WHERE prescriptionID = ? AND medicineID = ?";
            params.add(prescriptionID);
            params.add(medicineUpdate.getOriginalMedicineID());

            jdbcTemplate.update(sql, params.toArray());
        }
    }

    public void addPrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
        String sql = "INSERT INTO prescription_medicine (prescriptionID, medicineID, dosage, frequency, duration, instructions) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                prescriptionMedicine.getPrescriptionID(),
                prescriptionMedicine.getMedicineID(),
                prescriptionMedicine.getDosage(),
                prescriptionMedicine.getFrequency(),
                prescriptionMedicine.getDuration(),
                prescriptionMedicine.getInstructions());
    }

    // Add this method to remove a specific medicine
    public void removeMedicine(Integer prescriptionID, Integer originalMedicineID) {
        String sql = "DELETE FROM prescription_medicine WHERE prescriptionID = ? AND medicineID = ?";
        jdbcTemplate.update(sql, prescriptionID, originalMedicineID);
    }

    private List<PrescriptionMedicine> getMedicinesByPrescriptionId(Integer prescriptionID) {
        String sql = "SELECT * FROM prescription_medicine WHERE prescriptionID = ?";
        return jdbcTemplate.query(sql, prescriptionMedicineRowMapper, prescriptionID);
    }

    // New methods
    public void updateMedicineID(Integer prescriptionID, Integer originalMedicineID, Integer newMedicineID) {
        String sql = "UPDATE prescription_medicine SET medicineID = ? WHERE prescriptionID = ? AND medicineID = ?";
        jdbcTemplate.update(sql, newMedicineID, prescriptionID, originalMedicineID);
    }

    public void updateMedicineAttributes(String sql, Object[] params) {
        jdbcTemplate.update(sql, params);
    }
}
