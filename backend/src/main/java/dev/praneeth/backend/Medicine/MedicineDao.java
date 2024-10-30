package dev.praneeth.backend.Medicine;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class MedicineDao {

    private final JdbcTemplate jdbcTemplate;

    public MedicineDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Medicine object
    private RowMapper<Medicine> medicineRowMapper = new RowMapper<>() {
        @Override
        public Medicine mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            Medicine medicine = new Medicine();
            medicine.setMedicineID(rs.getInt("medicineID"));
            medicine.setName(rs.getString("name"));
            medicine.setDescription(rs.getString("description"));
            medicine.setDosageForm(rs.getString("dosageForm"));
            medicine.setStrength(rs.getString("strength"));
            return medicine;
        }
    };

    // Fetch all medicines
    public List<Medicine> getAllMedicines() {
        String sql = "SELECT * FROM medicine";
        return jdbcTemplate.query(sql, medicineRowMapper);
    }

    // Fetch medicine by ID
    public Optional<Medicine> getMedicineById(Integer medicineID) {
        String sql = "SELECT * FROM medicine WHERE medicineID = ?";
        List<Medicine> medicines = jdbcTemplate.query(sql, medicineRowMapper, medicineID);
        return medicines.stream().findFirst(); // Return as Optional
    }

    // Fetch medicine by name
    public Optional<Medicine> getMedicineByName(String name) {
        String sql = "SELECT * FROM medicine WHERE name = ?";
        List<Medicine> medicines = jdbcTemplate.query(sql, medicineRowMapper, name);
        return medicines.stream().findFirst();
    }

    // Add a new medicine
    public void addMedicine(Medicine medicine) {
        String sql = "INSERT INTO medicine (name, description, dosageForm, strength) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                medicine.getName(),
                medicine.getDescription(),
                medicine.getDosageForm(),
                medicine.getStrength());
    }

    // Delete a medicine by ID
    public void deleteMedicine(Integer medicineID) {
        String sql = "DELETE FROM medicine WHERE medicineID = ?";
        jdbcTemplate.update(sql, medicineID);
    }

    // Update a medicine by ID
    public void updateMedicine(Integer medicineID, Medicine medicine) {
        String sql = "UPDATE medicine SET name = ?, description = ?, dosageForm = ?, strength = ? WHERE medicineID = ?";
        jdbcTemplate.update(sql,
                medicine.getName(),
                medicine.getDescription(),
                medicine.getDosageForm(),
                medicine.getStrength(),
                medicineID);
    }
}
