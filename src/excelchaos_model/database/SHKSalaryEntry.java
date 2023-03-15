package excelchaos_model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "SHKSalaryTable")
public class SHKSalaryEntry {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField()
    private Date validation_date;
    @DatabaseField()
    private BigDecimal base_payRate;
    @DatabaseField()
    private BigDecimal extended_payRate;
    @DatabaseField()
    private BigDecimal whk_payRate;

    public SHKSalaryEntry(int id, Date validation_date, BigDecimal base_payRate, BigDecimal extended_payRate, BigDecimal whk_payRate) {
        this.id = id;
        this.validation_date = validation_date;
        this.base_payRate = base_payRate;
        this.extended_payRate = extended_payRate;
        this.whk_payRate = whk_payRate;
    }

    public SHKSalaryEntry() {

    }

    public int getId() {
        return this.id;
    }

    public Date getValidationDate() {
        return this.validation_date;
    }

    public void setValidationDate(Date validation_date) {
        this.validation_date = validation_date;
    }

    public BigDecimal getBasePayRate() {
        return this.base_payRate;
    }

    public void setBasePayRate(BigDecimal base_payRate) {
        this.base_payRate = base_payRate;
    }

    public BigDecimal getExtendedPayRate() {
        return this.extended_payRate;
    }

    public void setExtendedPayRate(BigDecimal extended_payRate) {
        this.extended_payRate = extended_payRate;
    }

    public BigDecimal getWHKPayRate() {
        return this.whk_payRate;
    }

    public void setWHKPayRate(BigDecimal whk_payRate) {
        this.whk_payRate = whk_payRate;
    }


}
