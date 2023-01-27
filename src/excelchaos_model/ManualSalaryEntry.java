package excelchaos_model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "ManualSalaryEntry")
public class ManualSalaryEntry {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField()
    private double new_salary;
    @DatabaseField()
    private Date start_date;
    @DatabaseField()
    private String comment;

    public ManualSalaryEntry(int id, double new_salary, Date start_date, String comment){
        this.id = id;
        this.new_salary = new_salary;
        this.start_date = start_date;
        this.comment = comment;
    }

    public ManualSalaryEntry(){

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

    public String getComment(){
        return this.comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }
}
