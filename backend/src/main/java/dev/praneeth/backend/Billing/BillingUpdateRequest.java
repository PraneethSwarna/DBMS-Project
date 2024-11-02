package dev.praneeth.backend.Billing;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillingUpdateRequest {
    
    private BigDecimal totalAmount;
    private BigDecimal patientPayableAmount;
    private LocalDate billingDate;
    private LocalDate dueDate;
    private Billing.Status status;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPatientPayableAmount() {
        return patientPayableAmount;
    }

    public void setPatientPayableAmount(BigDecimal patientPayableAmount) {
        this.patientPayableAmount = patientPayableAmount;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Billing.Status getStatus() {
        return status;
    }

    public void setStatus(Billing.Status status) {
        this.status = status;
    }
}
