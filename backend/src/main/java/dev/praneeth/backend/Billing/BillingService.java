package dev.praneeth.backend.Billing;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillingService {

    private final BillingDao billingDao;

    public BillingService(BillingDao billingDao) {
        this.billingDao = billingDao;
    }

    public List<Billing> getAllBillings() {
        return billingDao.getAllBillings();
    }

    public void addBilling(Billing billing) {
        billingDao.addBilling(billing);
    }

    public void deleteBilling(Integer billingId) {
        billingDao.getBillingById(billingId)
                .orElseThrow(() -> new IllegalStateException("Billing with id " + billingId + " does not exist"));
        billingDao.deleteBilling(billingId);
    }

    @Transactional
    public void updateBilling(Integer billingId, BillingUpdateRequest updateRequest) {
        Billing billing = billingDao.getBillingById(billingId)
                .orElseThrow(() -> new IllegalStateException("Billing with id " + billingId + " does not exist"));

        if (updateRequest.getTotalAmount() != null) {
            billing.setTotalAmount(updateRequest.getTotalAmount());
        }
        if (updateRequest.getPatientPayableAmount() != null) {
            billing.setPatientPayableAmount(updateRequest.getPatientPayableAmount());
        }
        if (updateRequest.getBillingDate() != null) {
            billing.setBillingDate(updateRequest.getBillingDate());
        }
        if (updateRequest.getDueDate() != null) {
            billing.setDueDate(updateRequest.getDueDate());
        }
        if (updateRequest.getStatus() != null) {
            billing.setStatus(updateRequest.getStatus());
        }

        billingDao.updateBilling(billing);
    }
}
