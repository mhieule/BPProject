package excelchaos_model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "ProjectParticipation")
public class ProjectParticipation {
    @DatabaseField
    private int project_id;
    @DatabaseField
    private int person_id;
    @DatabaseField
    private double scope;
    @DatabaseField
    private Date participation_period;

    public ProjectParticipation(int project_id, int person_id, double scope, Date participation_period) {
        this.project_id = project_id;
        this.person_id = person_id;
        this.scope = scope;
        this.participation_period = participation_period;
    }

    public ProjectParticipation() {

    }

    public int getProject_id() {
        return this.project_id;
    }

    public int getPerson_id() {
        return this.person_id;
    }

    public double getScope() {
        return this.scope;
    }

    public void setScope(double scope) {
        this.scope = scope;
    }

    public Date getParticipation_period() {
        return this.participation_period;
    }

    public void setParticipation_period(Date participation_period) {
        this.participation_period = participation_period;
    }
}
