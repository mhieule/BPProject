package excelchaos_model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Contracts")
public class Contract {
    @DatabaseField(id = true)
    private int id;
    @DatabaseField()
    private String paygrade;
    @DatabaseField()
    private String paylevel;
    @DatabaseField()
    private Date start_date;
    @DatabaseField()
    private Date end_date;
    @DatabaseField()
    private double regular_cost;
    @DatabaseField()
    private double bonus_cost;
    @DatabaseField()
    private double scope;
    @DatabaseField()
    private String shk_hourly_rate;
    @DatabaseField()
    private boolean vbl_status;

    public Contract(int id, String paygrade, String paylevel, Date start_date, Date end_date, double regular_cost,
                    double bonus_cost, double scope, String shk_hourly_rate, boolean vbl_status){
        this.id = id;
        this.paygrade = paygrade;
        this.paylevel = paylevel;
        this.start_date = start_date;
        this.end_date = end_date;
        this.regular_cost = regular_cost;
        this.bonus_cost = bonus_cost;
        this.scope = scope;
        this.shk_hourly_rate = shk_hourly_rate;
        this.vbl_status = vbl_status;
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

    public Date getStart_date(){
        return this.start_date;
    }

    public void setStart_date(Date start_date){
        this.start_date = start_date;
    }

    public Date getEnd_date(){
        return this.end_date;
    }

    public void setEnd_date(Date end_date){
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

    public void setScope(double scope){
        this.scope = scope;
    }

    public double getScope(){
        return this.scope;
    }

    public void setShk_hourly_rate(String shk_hourly_rate){
        this.shk_hourly_rate = shk_hourly_rate;
    }

    public String getShk_hourly_rate(){
        return this.shk_hourly_rate;
    }

    public void setVbl_status(boolean vbl_status){
        this.vbl_status = vbl_status;
    }

    public boolean getVbl_status() {
        return vbl_status;
    }
}
