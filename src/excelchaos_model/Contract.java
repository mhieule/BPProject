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

    public Contract(int id, String paygrade, String paylevel, String start_date, String end_date){
        this.id = id;
        this.paygrade = paygrade;
        this.paylevel = paylevel;
        this.start_date = start_date;
        this.end_date = end_date;
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
}
