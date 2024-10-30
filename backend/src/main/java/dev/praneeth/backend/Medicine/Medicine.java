package dev.praneeth.backend.Medicine;

public class Medicine {
    private Integer medicineID;
    private String name;
    private String description;
    private String dosageForm;
    private String strength;

    // Constructors
    public Medicine() {}

    public Medicine(String name, String description, String dosageForm, String strength) {
        this.name = name;
        this.description = description;
        this.dosageForm = dosageForm;
        this.strength = strength;
    }

    // Getters and Setters
    public Integer getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(Integer medicineID) {
        this.medicineID = medicineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "medicineID=" + medicineID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dosageForm='" + dosageForm + '\'' +
                ", strength='" + strength + '\'' +
                '}';
    }
}
