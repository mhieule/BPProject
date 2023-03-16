package excelchaos_model.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import excelchaos_model.database.ProjectFunder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Facilitates database integration for ProjectFunder
 */

public class ProjectFunderManager {
    private ConnectionSource connectionSource;
    private Dao<ProjectFunder, Integer> projectFunderDao;

    private static String databaseURL;

    /**
     * Creates database connection and DAO
     */

    public ProjectFunderManager() {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseURL;
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.projectFunderDao = DaoManager.createDao(connectionSource, ProjectFunder.class);
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
            TableUtils.createTable(connectionSource, ProjectFunder.class);
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
            TableUtils.dropTable(connectionSource, ProjectFunder.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Adds ProjectFunder to the database
     * @param projectFunder ProjectFunder to be added to the database
     */
    public void addProjectFunder(ProjectFunder projectFunder) {
        try {
            projectFunderDao.create(projectFunder);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns ProjectFunder with specified id
     * @param id id of the ProjectFunder
     * @return ProjectFunder with specified id
     */

    public ProjectFunder getProjectFunder(int id) {
        ProjectFunder projectFunder = null;
        try {
            projectFunder = projectFunderDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projectFunder;
    }

    /**
     * Returns list of all ProjectFunders in the table
     * @return list of all ProjectFunders
     */

    public List<ProjectFunder> getAllProjectFunder() {
        List<ProjectFunder> projectFunders = null;
        try {
            projectFunders = projectFunderDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projectFunders;
    }

    /**
     * Returns list of all ProjectFunders for specific project
     * @param projectId ID of specified project
     * @return List of all ProjectFunders for specified project
     */
    public List<ProjectFunder> getAllProjectFundersForProject(int projectId) {
        List<ProjectFunder> projectFunderList = new ArrayList<>();
        QueryBuilder<ProjectFunder, Integer> queryBuilder = projectFunderDao.queryBuilder();
        try {
            queryBuilder.where().eq("project_id", projectId);
            PreparedQuery<ProjectFunder> preparedQuery = queryBuilder.prepare();
            projectFunderList = projectFunderDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projectFunderList;
    }

    /**
     * Removes ProjectFunder with specified id
     * @param id id of ProjectFunder to be removed
     */

    public void removeProjectFunder(int id) {
        try {
            DeleteBuilder<ProjectFunder, Integer> builder = projectFunderDao.deleteBuilder();
            builder.where().eq("project_funder_id", id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes ProjectFunders involved in project with specified id
     * @param projectId id of the specified project
     */

    public void removeProjectFunderBasedOnProjectID(int projectId) {
        try {
            DeleteBuilder<ProjectFunder, Integer> builder = projectFunderDao.deleteBuilder();
            builder.where().eq("project_id", projectId);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes all ProjectFunders from the database
     */

    public void removeAllProjectFunder() {
        try {
            TableUtils.clearTable(connectionSource, ProjectFunder.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns total number of rows in the table
     * @return number of rows
     */

    public int getRowCount() {
        return getAllProjectFunder().size();
    }

    /**
     * Returns next available id
     * @return next available id
     */

    public int getNextID() {
        int id = 0;
        try {
            QueryBuilder<ProjectFunder, Integer> builder = projectFunderDao.queryBuilder();
            builder.orderBy("project_funder_id", false);
            ProjectFunder highest = projectFunderDao.queryForFirst(builder.prepare());
            if (highest == null) {
                id = 1;
            } else {
                id = highest.getProject_funder_id() + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return id;
    }

    public void updateProjectFunder(ProjectFunder projectFunder) {
        try {
            projectFunderDao.update(projectFunder);
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
        ProjectFunderManager.databaseURL = databaseURL;
    }
}
