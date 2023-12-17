import java.io.Serializable;
import java.util.Date;

public class RepairWork implements Serializable {
    private String firm;
    private String workType;
    private String unit;
    private String costPerUnit;
    private Date executionDate;
    private double volume;

    public RepairWork(String firm, String workType, String unit, String costPerUnit, Date executionDate, double volume) {
        this.firm = firm;
        this.workType = workType;
        this.unit = unit;
        this.costPerUnit = costPerUnit;
        this.executionDate = executionDate;
        this.volume = volume;
    }

    // Геттеры и сеттеры для полей
    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(String costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Firm: " + firm +
                ", Work Type: " + workType +
                ", Unit: " + unit +
                ", Cost per Unit: " + costPerUnit +
                ", Execution Date: " + executionDate +
                ", Volume: " + volume;
    }
}
