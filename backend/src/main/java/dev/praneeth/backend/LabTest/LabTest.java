package dev.praneeth.backend.LabTest;

public class LabTest {

    private Integer labTestID;
    private String name;
    private String description;
    private String normalRange;
    private String units;

    // Constructors
    public LabTest() {}

    public LabTest(String name, String description, String normalRange, String units) {
        this.name = name;
        this.description = description;
        this.normalRange = normalRange;
        this.units = units;
    }

    // Getters and Setters
    public Integer getLabTestID() {
        return labTestID;
    }

    public void setLabTestID(Integer labTestID) {
        this.labTestID = labTestID;
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

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "LabTest{" +
                "labTestID=" + labTestID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", normalRange='" + normalRange + '\'' +
                ", units='" + units + '\'' +
                '}';
    }
}
