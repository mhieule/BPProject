package excelchaos_model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectParticipationManager {
    private ConnectionSource connectionSource;
    private Dao<ProjectParticipation, Object> projectParticipationsDao;
    public ProjectParticipationManager(){
        try{
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.projectParticipationsDao = DaoManager.createDao(connectionSource, ProjectParticipation.class);
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, ProjectParticipation.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void deleteTable(){
        try {
            TableUtils.dropTable(connectionSource, ProjectParticipation.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addProjectParticipation(ProjectParticipation projectParticipation){
        try {
            projectParticipationsDao.create(projectParticipation);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void  removeProjectParticipation(int project_id, int person_id){
        try{
            DeleteBuilder<ProjectParticipation, Object> builder = projectParticipationsDao.deleteBuilder();
            builder.where().eq("project_id", project_id).and().eq("person_id", person_id);
            builder.delete();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void  removeProjectParticipationBasedOnDate(int project_id, int person_id, Date date){
        try{
            DeleteBuilder<ProjectParticipation, Object> builder = projectParticipationsDao.deleteBuilder();
            builder.where().eq("project_id", project_id).and().eq("person_id", person_id).and().eq("participation_period",date);
            builder.delete();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }

    }

    public void removeProjectParticipationBasedOnEmployeeId(int person_id){
        try{
            DeleteBuilder<ProjectParticipation, Object> builder = projectParticipationsDao.deleteBuilder();
            builder.where().eq("person_id", person_id);
            builder.delete();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void removeAllProjectParticipations(){
        try {
            TableUtils.clearTable(connectionSource, ProjectParticipation.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public List<ProjectParticipation> getProjectParticipationByProjectID(int project_id){
        List<ProjectParticipation> projectParticipationList = new ArrayList<>();
        QueryBuilder<ProjectParticipation,Object> queryBuilder = projectParticipationsDao.queryBuilder();
        try {
            queryBuilder.where().eq("project_id",project_id);
            PreparedQuery<ProjectParticipation> preparedQuery = queryBuilder.prepare();
            projectParticipationList = projectParticipationsDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return projectParticipationList;
    }

    public List<ProjectParticipation> getProjectParticipationByProjectIDandPersonID(int project_id, int person_id){
        List<ProjectParticipation> projectParticipationList = new ArrayList<>();
        QueryBuilder<ProjectParticipation,Object> queryBuilder = projectParticipationsDao.queryBuilder();
        try {
            queryBuilder.where().eq("project_id", project_id).and().eq("person_id", person_id);
            PreparedQuery<ProjectParticipation> preparedQuery = queryBuilder.prepare();
            projectParticipationList = projectParticipationsDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return projectParticipationList;
    }

    public List<ProjectParticipation> getProjectParticipationByPersonID(int person_id){
        List<ProjectParticipation> projectParticipationList = new ArrayList<>();
        QueryBuilder<ProjectParticipation,Object> queryBuilder = projectParticipationsDao.queryBuilder();
        try {
            queryBuilder.where().eq("person_id",person_id);
            PreparedQuery<ProjectParticipation> preparedQuery = queryBuilder.prepare();
            projectParticipationList = projectParticipationsDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return projectParticipationList;
    }

    public List<ProjectParticipation> getAllProjectParticipations(){
        List<ProjectParticipation> projectParticipationList = new ArrayList<>();
        try {
            projectParticipationList = projectParticipationsDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return projectParticipationList;
    }

    public int getRowCountByPersonID(int id){
        return getProjectParticipationByPersonID(id).size();
    }

    public int getRowCountByProjectID(int id){
        return getProjectParticipationByProjectID(id).size();
    }
}
