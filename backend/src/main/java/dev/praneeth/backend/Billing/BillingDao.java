package dev.praneeth.backend.Billing;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BillingDao {

    private final JdbcTemplate jdbcTemplate;

    public BillingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Billing> billingRowMapper() {
        return new RowMapper<>() {
            @Override
            public Billing mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
                Billing billing = new Billing();
                billing.setBillingID(rs.getInt("billingID"));
                billing.setTotalAmount(rs.getBigDecimal("total_amount"));
                billing.setPatientPayableAmount(rs.getBigDecimal("patient_payable_amount"));
                billing.setBillingDate(rs.getDate("billing_date").toLocalDate());
                billing.setDueDate(rs.getDate("due_date").toLocalDate());
                billing.setServiceID(rs.getInt("serviceID"));
                billing.setStatus(Billing.Status.valueOf(rs.getString("status")));
                return billing;
            };
        };
    }

    public List<Billing> getAllBillings() {
        String sql = "SELECT * FROM billings";
        return jdbcTemplate.query(sql, billingRowMapper());
    }

    public Optional<Billing> getBillingById(Integer id) {
        String sql = "SELECT * FROM billings WHERE billingID = ?";
        return jdbcTemplate.query(sql, billingRowMapper(), id).stream().findFirst();
    }

    public int addBilling(Billing billing) {
        String sql = "INSERT INTO billings (total_amount, patient_payable_amount, billing_date, due_date, serviceID, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                billing.getTotalAmount(),
                billing.getPatientPayableAmount(),
                billing.getBillingDate(),
                billing.getDueDate(),
                billing.getServiceID(),
                billing.getStatus().toString());
    }

    public int deleteBilling(Integer id) {
        String sql = "DELETE FROM billings WHERE billingID = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int updateBilling(Billing billing) {
        String sql = "UPDATE billings SET total_amount = ?, patient_payable_amount = ?, billing_date = ?, due_date = ?, serviceID = ?, status = ? WHERE billingID = ?";
        return jdbcTemplate.update(sql,
                billing.getTotalAmount(),
                billing.getPatientPayableAmount(),
                billing.getBillingDate(),
                billing.getDueDate(),
                billing.getServiceID(),
                billing.getStatus().toString(),
                billing.getBillingID());
    }
}
