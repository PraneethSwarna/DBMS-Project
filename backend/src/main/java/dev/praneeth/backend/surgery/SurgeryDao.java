package dev.praneeth.backend.Surgery;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SurgeryDao {

    private final JdbcTemplate jdbcTemplate;

    public SurgeryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("unused")
    private RowMapper<Surgery> surgeryRowMapper = (rs, rowNum) -> {
        Surgery surgery = new Surgery();
        surgery.setSurgeryID(rs.getInt("surgeryID"));
        surgery.setSurgeryDate(rs.getDate("surgeryDate") != null ? rs.getDate("surgeryDate").toLocalDate() : null);
        surgery.setSurgeryType(Surgery.SurgeryType.valueOf(rs.getString("surgeryType")));
        surgery.setOutcome(rs.getString("outcome"));
        surgery.setNotes(rs.getString("notes"));
        surgery.setPatientID(rs.getInt("patientID"));

        // Fetch doctors and prescriptions separately
        surgery.setDoctorIDs(getDoctorIDsForSurgery(surgery.getSurgeryID()));
        surgery.setPrescriptionIDs(getPrescriptionIDsForSurgery(surgery.getSurgeryID()));
        return surgery;
    };

    public Optional<Surgery> getSurgeryById(Integer surgeryID) {
        String sql = "SELECT * FROM surgery WHERE surgeryID = ?";
        List<Surgery> surgeries = jdbcTemplate.query(sql, surgeryRowMapper, surgeryID);
        return surgeries.stream().findFirst();
    }

    public List<Surgery> getSurgeries() {
        String sql = "SELECT * FROM surgery";
        List<Surgery> surgeries = jdbcTemplate.query(sql, surgeryRowMapper);

        for (Surgery surgery : surgeries) {
            List<Integer> doctorIDs = getDoctorIDsForSurgery(surgery.getSurgeryID());
            List<Integer> prescriptionIDs = getPrescriptionIDsForSurgery(surgery.getSurgeryID());
            surgery.setDoctorIDs(doctorIDs);
            surgery.setPrescriptionIDs(prescriptionIDs);
        }

        return surgeries;
    }

    @SuppressWarnings("unused")
    public void addSurgery(Surgery surgery) {
        String sql = "INSERT INTO surgery (surgeryDate, surgeryType, outcome, notes, patientID) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, surgery.getSurgeryDate(), surgery.getSurgeryType() != null ? surgery.getSurgeryType().name() : null, surgery.getOutcome(), surgery.getNotes(), surgery.getPatientID());
        
        // Re-query for the surgeryID instead of relying solely on LAST_INSERT_ID()
        Integer surgeryID = jdbcTemplate.queryForObject("SELECT surgeryID FROM surgery WHERE surgeryDate = ? AND patientID = ? ORDER BY surgeryID DESC LIMIT 1",
            Integer.class, surgery.getSurgeryDate(), surgery.getPatientID());
        if (surgeryID == null) throw new IllegalStateException("Failed to retrieve surgery ID after insert.");
        
        // Insert doctor and prescription relations using validated IDs
        if (surgery.getDoctorIDs() != null) {
            for (Integer doctorID : surgery.getDoctorIDs()) {
                jdbcTemplate.update("INSERT INTO surgery_doctor (surgeryID, doctorID) VALUES (?, ?)", surgeryID, doctorID);
            }
        }
        if (surgery.getPrescriptionIDs() != null) {
            for (Integer prescriptionID : surgery.getPrescriptionIDs()) {
                jdbcTemplate.update("INSERT INTO surgery_prescription (surgeryID, prescriptionID) VALUES (?, ?)", surgeryID, prescriptionID);
            }
        }
    }
    

    public void deleteSurgery(Integer surgeryID) {
        String sql = "DELETE FROM surgery WHERE surgeryID = ?";
        jdbcTemplate.update(sql, surgeryID);
    }

    public void updateSurgery(Integer surgeryID, Surgery surgery) {
        String sql = "UPDATE surgery SET surgeryDate = ?, surgeryType = ?, outcome = ?, notes = ?, patientID = ? WHERE surgeryID = ?";
        jdbcTemplate.update(
                sql,
                surgery.getSurgeryDate(),
                surgery.getSurgeryType() != null ? surgery.getSurgeryType().name() : null,
                surgery.getOutcome(),
                surgery.getNotes(),
                surgery.getPatientID(),
                surgeryID);
    }

    public void updateDoctorsForSurgery(Integer surgeryID, List<Integer> addDoctorIDs, List<Integer> removeDoctorIDs) {
        String addDoctorSql = "INSERT INTO surgery_doctor (surgeryID, doctorID) VALUES (?, ?)";
        addDoctorIDs.forEach(doctorID -> jdbcTemplate.update(addDoctorSql, surgeryID, doctorID));

        String removeDoctorSql = "DELETE FROM surgery_doctor WHERE surgeryID = ? AND doctorID = ?";
        removeDoctorIDs.forEach(doctorID -> jdbcTemplate.update(removeDoctorSql, surgeryID, doctorID));
    }

    public void updatePrescriptionsForSurgery(Integer surgeryID, List<Integer> addPrescriptionIDs,
            List<Integer> removePrescriptionIDs) {
        String addPrescriptionSql = "INSERT INTO surgery_prescription (surgeryID, prescriptionID) VALUES (?, ?)";
        addPrescriptionIDs
                .forEach(prescriptionID -> jdbcTemplate.update(addPrescriptionSql, surgeryID, prescriptionID));

        String removePrescriptionSql = "DELETE FROM surgery_prescription WHERE surgeryID = ? AND prescriptionID = ?";
        removePrescriptionIDs
                .forEach(prescriptionID -> jdbcTemplate.update(removePrescriptionSql, surgeryID, prescriptionID));
    }

    private List<Integer> getDoctorIDsForSurgery(Integer surgeryID) {
        String sql = "SELECT doctorID FROM surgery_doctor WHERE surgeryID = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, surgeryID);
    }

    private List<Integer> getPrescriptionIDsForSurgery(Integer surgeryID) {
        String sql = "SELECT prescriptionID FROM surgery_prescription WHERE surgeryID = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, surgeryID);
    }
}
