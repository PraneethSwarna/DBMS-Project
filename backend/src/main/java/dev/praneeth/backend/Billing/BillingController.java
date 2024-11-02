package dev.praneeth.backend.Billing;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @GetMapping
    public List<Billing> getAllBillings() {
        return billingService.getAllBillings();
    }

    @PostMapping
    public void addBilling(@RequestBody Billing billing) {
        billingService.addBilling(billing);
    }

    @DeleteMapping(path = "/{billingId}")
    public void deleteBilling(@PathVariable("billingId") Integer billingId) {
        billingService.deleteBilling(billingId);
    }

    @PutMapping(path = "/{billingId}")
    public void updateBilling(@PathVariable("billingId") Integer billingId, @RequestBody BillingUpdateRequest updateRequest) {
        billingService.updateBilling(billingId, updateRequest);
    }
}
