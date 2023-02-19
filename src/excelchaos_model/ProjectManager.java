package excelchaos_model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class ProjectManager {
    private ConnectionSource connectionSource;
    private Dao<Project, Integer> projectDao;

    public ProjectManager() {
        try {
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.projectDao = DaoManager.createDao(connectionSource, Project.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, Project.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void deleteTable(){
        try {
            TableUtils.dropTable(connectionSource, Project.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addProject(Project project){
        try {
            projectDao.create(project);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public Project getProject(int id){
        Project project = null;
        try {
            project = projectDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return project;
    }

    public List<Project> getAllProjects(){
        List<Project> projects = null;
        try {
            projects = projectDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return projects;
    }

    public void removeProject(int id){
        try{
            DeleteBuilder<Project, Integer> builder = projectDao.deleteBuilder();
            builder.where().eq("project_id", id);
            builder.delete();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void removeAllProjects(){
        try {
            TableUtils.clearTable(connectionSource, Project.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void updateProject(Project project){
        try {
            projectDao.update(project);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public int getNextID(){
        int id = 0;
        try {
            QueryBuilder<Project, Integer> builder = projectDao.queryBuilder();
            builder.orderBy("project_id", false);
            Project highest = projectDao.queryForFirst(builder.prepare());
            if (highest == null){
                id = 1;
            }else{
                id = highest.getProject_id()+1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return id;
    }

    public int getRowCount(){
        int rowCount = 0;
        try {
            QueryBuilder<Project, Integer> builder = projectDao.queryBuilder();
            rowCount = (int) builder.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return rowCount;
    }
}
