package excelchaos_model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.Date;

@DatabaseTable(tableName = "SalaryIncreaseHistory")
public class SalaryIncreaseHistory {
    @DatabaseField()
    private int id;
    @DatabaseField()
    private BigDecimal new_salary;
    @DatabaseField()
    private Date start_date;
    @DatabaseField()
    private BigDecimal absoluteIncreaseValue;
    @DatabaseField()
    private BigDecimal percentIncreaseValue;
    @DatabaseField()
    private String comment;
    @DatabaseField()
    private boolean is_additional_payment;


    public SalaryIncreaseHistory(int id, BigDecimal new_salary, Date start_date, BigDecimal absoluteIncreaseValue, BigDecimal percentIncreaseValue, String comment, boolean is_additional_payment) {
        this.id = id;
        this.new_salary = new_salary;
        this.start_date = start_date;
        this.absoluteIncreaseValue = absoluteIncreaseValue;
        this.percentIncreaseValue = percentIncreaseValue;
        this.comment = comment;
        this.is_additional_payment = is_additional_payment;
    }

    public SalaryIncreaseHistory() {

    }

    public int getId() {
        return this.id;
    }

    public BigDecimal getNew_salary() {
        return this.new_salary;
    }

    public void setNew_salary(BigDecimal new_salary) {
        this.new_salary = new_salary;
    }

    public Date getStart_date() {
        return this.start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public BigDecimal getAbsoluteIncreaseValue() {
        return absoluteIncreaseValue;
    }

    public void setAbsoluteIncreaseValue(BigDecimal absoluteIncreaseValue) {
        this.absoluteIncreaseValue = absoluteIncreaseValue;
    }

    public BigDecimal getPercentIncreaseValue() {
        return percentIncreaseValue;
    }

    public void setPercentIncreaseValue(BigDecimal percentIncreaseValue) {
        this.percentIncreaseValue = percentIncreaseValue;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getIs_additional_payment() {
        return this.is_additional_payment;
    }

    public void setIs_additional_payment(boolean is_additional_payment) {
        this.is_additional_payment = is_additional_payment;
    }
}
