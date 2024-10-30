package dev.praneeth.backend.Medicine;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/medicine")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    // Get all medicines
    @GetMapping
    public List<Medicine> getMedicines() {
        return medicineService.getMedicines();
    }

    // Add a new medicine
    @PostMapping
    public void addMedicine(@RequestBody Medicine medicine) {
        medicineService.addMedicine(medicine);
    }

    // Delete a medicine by ID
    @DeleteMapping(path = "/{medicineId}")
    public void deleteMedicine(@PathVariable("medicineId") Integer medicineId) {
        medicineService.deleteMedicine(medicineId);
    }

    // Update an existing medicine
    @PutMapping(path = "/{medicineId}")
    public void updateMedicine(@PathVariable("medicineId") Integer medicineId, @RequestBody MedicineUpdateRequest updateRequest) {
        medicineService.updateMedicine(medicineId, updateRequest);
    }
}
