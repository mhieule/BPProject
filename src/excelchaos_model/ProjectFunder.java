package excelchaos_model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ProjectFunder")
public class ProjectFunder {
    @DatabaseField
    private int project_id;
    @DatabaseField(id = true)
    private int project_funder_id;
    @DatabaseField
    private String project_funder_name;
    @DatabaseField
    private String funding_id;
    @DatabaseField
    private String project_number;

    public ProjectFunder(int project_id, int project_funder_id, String project_funder_name, String funding_id,
                         String project_number){
        this.project_id = project_id;
        this.project_funder_id = project_funder_id;
        this.project_funder_name = project_funder_name;
        this.funding_id = funding_id;
        this.project_number = project_number;

    }
    public ProjectFunder(){

    }

    public int getProject_id(){
        return this.project_id;
    }

    public int getProject_funder_id(){
        return this.project_funder_id;
    }

    public String getProject_funder_name(){
        return this.project_funder_name;
    }

    public void setProject_funder_name(String project_funder_name){
        this.project_funder_name = project_funder_name;
    }

    public String getFunding_id(){
        return this.funding_id;
    }

    public void setFunding_id(String funding_id){
        this.funding_id = funding_id;
    }

    public String getProject_number(){
        return this.project_number;
    }

    public void setProject_number(String project_number){
        this.project_number = project_number;
    }
}
