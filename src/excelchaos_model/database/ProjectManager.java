package excelchaos_model.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import excelchaos_model.database.Project;

import java.sql.SQLException;
import java.util.List;
/**
 *  Facilitates database integration for Project
 */

public class ProjectManager {
    private ConnectionSource connectionSource;
    private Dao<Project, Integer> projectDao;

    private static String databaseURL;


    /**
     * Creates database connection and DAO
     */

    public ProjectManager() {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseURL;
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.projectDao = DaoManager.createDao(connectionSource, Project.class);
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
            TableUtils.createTable(connectionSource, Project.class);
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
            TableUtils.dropTable(connectionSource, Project.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Adds Project to the table
     * @param project project to added
     */

    public void addProject(Project project) {
        try {
            projectDao.create(project);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns Project with specified id
     * @param id id of the project
     * @return Project with specified id
     */

    public Project getProject(int id) {
        Project project = null;
        try {
            project = projectDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return project;
    }

    /**
     * Returns list of all Projects in the database
     * @return list of all projects
     */

    public List<Project> getAllProjects() {
        List<Project> projects = null;
        try {
            projects = projectDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projects;
    }

    /**
     * Removes project with specified id
     * @param id id of project to be removed
     */

    public void removeProject(int id) {
        try {
            DeleteBuilder<Project, Integer> builder = projectDao.deleteBuilder();
            builder.where().eq("project_id", id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes all Projects from the databse
     */

    public void removeAllProjects() {
        try {
            TableUtils.clearTable(connectionSource, Project.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Updates Project in the database
     * @param project Project to be updated
     */

    public void updateProject(Project project) {
        try {
            projectDao.update(project);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns next available id
     * @return next available id
     */

    public int getNextID() {
        int id = 0;
        try {
            QueryBuilder<Project, Integer> builder = projectDao.queryBuilder();
            builder.orderBy("project_id", false);
            Project highest = projectDao.queryForFirst(builder.prepare());
            if (highest == null) {
                id = 1;
            } else {
                id = highest.getProject_id() + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return id;
    }


    /**
     * Returns total row count of the table
     * @return row count
     */

    public int getRowCount() {
        int rowCount = 0;
        try {
            QueryBuilder<Project, Integer> builder = projectDao.queryBuilder();
            rowCount = (int) builder.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return rowCount;
    }


    public static void setDatabaseURL(String databaseURL) {
        ProjectManager.databaseURL = databaseURL;
    }
}
