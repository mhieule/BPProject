package excelchaos_model.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import excelchaos_model.database.ProjectCategory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Facilitates database integration for ProjectCategory
 */

public class ProjectCategoryManager {
    private ConnectionSource connectionSource;
    private Dao<ProjectCategory, Integer> projectCategoriesDao;

    private static String databaseURL;

    /**
     * Creates database connection and DAO
     */

    public ProjectCategoryManager() {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseURL;
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.projectCategoriesDao = DaoManager.createDao(connectionSource, ProjectCategory.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Creates table in the database
     */

    public void createTable() {
        try {
            TableUtils.createTable(connectionSource, ProjectCategory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Deletes table from the database
     */

    public void deleteTable() {
        try {
            TableUtils.dropTable(connectionSource, ProjectCategory.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Adds a ProjectCategory to the database
     * @param projectCategory projectCategory to be added to the database
     */

    public void addProjectCategory(ProjectCategory projectCategory) {
        try {
            projectCategoriesDao.create(projectCategory);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns project with specified id from database
     * @param id id of the project to be returned
     * @return project with specified id
     */

    public ProjectCategory getProject(int id) {
        ProjectCategory projectCategory = null;
        try {
            projectCategory = projectCategoriesDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projectCategory;
    }

    /**
     * Returns list of all projects in the database
     * @return list of all projects
     */

    public List<ProjectCategory> getAllProjectCategories() {
        List<ProjectCategory> projectCategories = null;
        try {
            projectCategories = projectCategoriesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projectCategories;
    }

    /**
     * Returns list of all ProjectCategories for specific project
     * @param projectId id of the project
     * @return list of all ProjectCategories with specified project id
     */

    public List<ProjectCategory> getAllProjectCategoriesForProject(int projectId) {
        List<ProjectCategory> projectCategoryList = new ArrayList<>();
        QueryBuilder<ProjectCategory, Integer> queryBuilder = projectCategoriesDao.queryBuilder();
        try {
            queryBuilder.where().eq("project_id", projectId);
            PreparedQuery<ProjectCategory> preparedQuery = queryBuilder.prepare();
            projectCategoryList = projectCategoriesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projectCategoryList;
    }

    /**
     * Removes ProjectCategory with specified id from database
     * @param id id of ProjectCategory to be removed
     */

    public void removeProjectCategory(int id) {
        try {
            DeleteBuilder<ProjectCategory, Integer> builder = projectCategoriesDao.deleteBuilder();
            builder.where().eq("category_id", id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes ProjectCategory with specified project id from database
     * @param projectID project id of the ProjectCategory to be removed
     */

    public void removeProjectCategoryBasedOnProjectId(int projectID) {
        try {
            DeleteBuilder<ProjectCategory, Integer> builder = projectCategoriesDao.deleteBuilder();
            builder.where().eq("project_id", projectID);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes all ProjectCategories from table
     */

    public void removeAllProjectCategories() {
        try {
            TableUtils.clearTable(connectionSource, ProjectCategory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns total row count of the table
     * @return row count
     */

    public int getRowCount() {
        return getAllProjectCategories().size();
    }

    /**
     * Returns next available id
     * @return next available id
     */

    public int getNextID() {
        int id = 0;
        try {
            QueryBuilder<ProjectCategory, Integer> builder = projectCategoriesDao.queryBuilder();
            builder.orderBy("category_id", false);
            ProjectCategory highest = projectCategoriesDao.queryForFirst(builder.prepare());
            if (highest == null) {
                id = 1;
            } else {
                id = highest.getCategory_id() + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return id;
    }

    /**
     * Updates the specified ProjectCategory
     * @param projectCategory ProjectCategory to be updated
     */

    public void updateProjectCategory(ProjectCategory projectCategory) {
        try {
            projectCategoriesDao.update(projectCategory);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Sets the Path where the database can be found.
     *
     * @param databaseURL The path to the database.
     */
    public static void setDatabaseURL(String databaseURL) {
        ProjectCategoryManager.databaseURL = databaseURL;
    }
}
