package dev.praneeth.backend.Billing;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Billing {
    private Integer billingID;
    private BigDecimal totalAmount;
    private BigDecimal patientPayableAmount;
    private LocalDate billingDate;
    private LocalDate dueDate;
    private Integer serviceID;
    private Status status;

    public enum Status {
        PAID,
        UNPAID,
        PARTIALLY_PAID
    }

    // Constructors
    public Billing() {}

    public Billing(BigDecimal totalAmount, BigDecimal patientPayableAmount, LocalDate billingDate, Integer serviceID, Status status) {
        this.totalAmount = totalAmount;
        this.patientPayableAmount = patientPayableAmount;
        this.billingDate = billingDate;
        this.dueDate = billingDate.plusWeeks(2);
        this.serviceID = serviceID;
        this.status = status;
    }

    // Getters and Setters
    public Integer getBillingID() {
        return billingID;
    }

    public void setBillingID(Integer billingID) {
        this.billingID = billingID;
    }

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

    public Integer getServiceID() {
        return serviceID;
    }

    public void setServiceID(Integer serviceID) {
        this.serviceID = serviceID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Billing{" +
                "billingID=" + billingID +
                ", totalAmount=" + totalAmount +
                ", patientPayableAmount=" + patientPayableAmount +
                ", billingDate=" + billingDate +
                ", dueDate=" + dueDate +
                ", serviceID=" + serviceID +
                ", status=" + status +
                '}';
    }
}

