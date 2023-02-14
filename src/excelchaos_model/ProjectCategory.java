package excelchaos_model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ProjectCategory")
public class ProjectCategory {
    @DatabaseField
    private int project_id;
    @DatabaseField(id = true)
    private int category_id;
    @DatabaseField
    private String category_name;
    @DatabaseField
    private double approved_funds;

    public ProjectCategory(int project_id, int category_id, String category_name, double approved_funds){
        this.project_id = project_id;
        this.category_id = category_id;
        this.category_name = category_name;
        this.approved_funds = approved_funds;
    }

    public ProjectCategory(){

    }

    public int getProject_id(){
        return this.project_id;
    }

    public int getCategory_id(){
        return this.category_id;
    }

    public String getCategory_name(){
        return this.category_name;
    }

    public void setCategory_name(String category_name){
        this.category_name = category_name;
    }

    public double getApproved_funds(){
        return this.approved_funds;
    }

    public void setApproved_funds(double approved_funds){
        this.approved_funds = approved_funds;
    }
}
