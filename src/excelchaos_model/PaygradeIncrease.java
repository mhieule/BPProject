package excelchaos_model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "PaygradeIncrease")
public class PaygradeIncrease {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField()
    private double new_salary;
    @DatabaseField()
    private Date start_date;

    public PaygradeIncrease(int id, double new_salary, Date start_date){
        this.id = id;
        this.new_salary = new_salary;
        this.start_date = start_date;
    }

    public PaygradeIncrease(){

    }

    public int getId(){
        return this.id;
    }

    public double getNew_salary(){
        return this.new_salary;
    }

    public void setNew_salary(double new_salary){
        this.new_salary = new_salary;
    }

    public Date getStart_date(){
        return this.start_date;
    }

    public void setStart_date(Date start_date){
        this.start_date = start_date;
    }
}


