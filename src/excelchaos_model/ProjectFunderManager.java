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
import java.util.List;

public class ProjectFunderManager {
    private ConnectionSource connectionSource;
    private Dao<ProjectFunder, Integer> projectFunderDao;
    public ProjectFunderManager(){
        try{
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.projectFunderDao = DaoManager.createDao(connectionSource, ProjectFunder.class);
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, ProjectFunder.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void deleteTable(){
        try {
            TableUtils.dropTable(connectionSource, ProjectFunder.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addProjectFunder(ProjectFunder projectFunder){
        try {
            projectFunderDao.create(projectFunder);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public ProjectFunder getProjectFunder(int id){
        ProjectFunder projectFunder = null;
        try {
            projectFunder = projectFunderDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return projectFunder;
    }

    public List<ProjectFunder> getAllProjectFunder(){
        List<ProjectFunder> projectFunders = null;
        try {
            projectFunders = projectFunderDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return projectFunders;
    }


    public List<ProjectFunder> getAllProjectFundersForProject(int projectId){
        List<ProjectFunder> projectFunderList = new ArrayList<>();
        QueryBuilder<ProjectFunder,Integer> queryBuilder = projectFunderDao.queryBuilder();
        try {
            queryBuilder.where().eq("project_id",projectId);
            PreparedQuery<ProjectFunder> preparedQuery = queryBuilder.prepare();
            projectFunderList = projectFunderDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return projectFunderList;
    }

    public void removeProjectFunder(int id){
        try {
            DeleteBuilder<ProjectFunder, Integer> builder = projectFunderDao.deleteBuilder();
            builder.where().eq("project_funder_id", id);
            builder.delete();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void removeAllProjectFunder(){
        try {
            TableUtils.clearTable(connectionSource, ProjectFunder.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public int getRowCount(){
        return getAllProjectFunder().size();
    }

    public int getNextID(){
        int id = 0;
        try {
            QueryBuilder<ProjectFunder, Integer> builder = projectFunderDao.queryBuilder();
            builder.orderBy("project_funder_id", false);
            ProjectFunder highest = projectFunderDao.queryForFirst(builder.prepare());
            if (highest == null){
                id = 1;
            }else{
                id = highest.getProject_funder_id()+1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return id;
    }

    public void updateProjectFunder(ProjectFunder projectFunder){
        try {
            projectFunderDao.update(projectFunder);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }
}
