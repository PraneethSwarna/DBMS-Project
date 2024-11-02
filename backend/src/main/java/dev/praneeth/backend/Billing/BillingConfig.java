package dev.praneeth.backend.Billing;

// import java.math.BigDecimal;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import java.time.LocalDate;

public class BillingConfig {

    // public static void main(String[] args) {
    //     // Database connection details
    //     String jdbcURL = "jdbc:mysql://localhost:3306/your_database";  // Replace with your DB URL
    //     String jdbcUsername = "your_username";  // Replace with your DB username
    //     String jdbcPassword = "your_password";  // Replace with your DB password

    //     try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
    //         // Create a new Billing instance
    //         Billing billing1 = new Billing(
    //             new BigDecimal("5000.00"),              // totalAmount
    //             new BigDecimal("4500.00"),              // patientPayableAmount
    //             LocalDate.of(2024, 9, 1),   // billingDate
    //             1,                           // serviceID
    //             Billing.Status.PENDING        // status
    //         );

    //         // Save the Billing instance to the database using JDBC
    //         billing1.saveBilling(connection);

    //         System.out.println("Billing record has been saved successfully.");

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }
}
