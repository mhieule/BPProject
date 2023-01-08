package excelchaos_model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Contracts")
public class Contract {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField()
    private String paygrade;
    @DatabaseField()
    private String paylevel;
    @DatabaseField()
    private String start_date;
    @DatabaseField()
    private String end_date;
    @DatabaseField()
    private double regular_cost;
    @DatabaseField()
    private double bonus_cost;

    public Contract(int id, String paygrade, String paylevel, String start_date, String end_date, double regular_cost,
                    double bonus_cost){
        this.id = id;
        this.paygrade = paygrade;
        this.paylevel = paylevel;
        this.start_date = start_date;
        this.end_date = end_date;
        this.regular_cost = regular_cost;
        this.bonus_cost = bonus_cost;
    }

    public Contract(){

    }

    public int getId(){
        return this.id;
    }

    public String getPaygrade(){
        return this.paygrade;
    }

    public void setPaygrade(String paygrade){
        this.paygrade = paygrade;
    }

    public String getPaylevel(){
        return this.paylevel;
    }

    public void setPaylevel(String paylevel){
        this.paylevel = paylevel;
    }

    public String getStart_date(){
        return this.start_date;
    }

    public void setStart_date(String start_date){
        this.start_date = start_date;
    }

    public String getEnd_date(){
        return this.end_date;
    }

    public void setEnd_date(String end_date){
        this.end_date = end_date;
    }

    public double getRegular_cost(){
        return this.regular_cost;
    }

    public void setRegular_cost(double regular_cost){
        this.regular_cost = regular_cost;
    }

    public double getBonus_cost(){
        return this.bonus_cost;
    }

    public void setBonus_cost(double bonus_cost){
        this.bonus_cost = bonus_cost;
    }
}
