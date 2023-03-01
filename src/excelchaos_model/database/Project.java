package excelchaos_model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Project")
public class Project {
    @DatabaseField(id = true)
    private int project_id;
    @DatabaseField
    private String project_name;
    @DatabaseField
    private Date start_date;
    @DatabaseField
    private Date approval_date;
    @DatabaseField
    private Date duration;

    public Project(int project_id, String project_name, Date start_date, Date approval_date, Date duration) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.start_date = start_date;
        this.approval_date = approval_date;
        this.duration = duration;
    }

    public Project() {

    }

    public int getProject_id() {
        return this.project_id;
    }

    public String getProject_name() {
        return this.project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Date getStart_date() {
        return this.start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getApproval_date() {
        return this.approval_date;
    }

    public void setApproval_date(Date approval_date) {
        this.approval_date = approval_date;
    }

    public Date getDuration() {
        return this.duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }
}
