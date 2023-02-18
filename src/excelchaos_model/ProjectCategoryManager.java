package excelchaos_model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class ProjectCategoryManager {
    private ConnectionSource connectionSource;
    private Dao<ProjectCategory, Integer> projectCategoriesDao;
    public ProjectCategoryManager(){
        try{
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.projectCategoriesDao = DaoManager.createDao(connectionSource, ProjectCategory.class);
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, ProjectCategory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void deleteTable(){
        try {
            TableUtils.dropTable(connectionSource, ProjectCategory.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addProjectCategory(ProjectCategory projectCategory){
        try {
            projectCategoriesDao.create(projectCategory);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public ProjectCategory getProject(int id){
        ProjectCategory projectCategory = null;
        try {
            projectCategory = projectCategoriesDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return projectCategory;
    }

    public List<ProjectCategory> getAllProjectCategories(){
        List<ProjectCategory> projectCategories = null;
        try {
            projectCategories = projectCategoriesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return projectCategories;
    }

    public void removeProjectCategory(int id){
        try{
            DeleteBuilder<ProjectCategory, Integer> builder = projectCategoriesDao.deleteBuilder();
            builder.where().eq("project_category_id", id);
            builder.delete();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void removeAllProjectCategories(){
        try {
            TableUtils.clearTable(connectionSource, ProjectCategory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void updateProjectCategory(ProjectCategory projectCategory){
        try {
            projectCategoriesDao.update(projectCategory);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }
}