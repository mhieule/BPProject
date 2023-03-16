package excelchaos_model.database;

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

/**
 *  Facilitates database integration for ProjectParticipation
 */

public class ProjectParticipationManager {
    private ConnectionSource connectionSource;
    private Dao<ProjectParticipation, Object> projectParticipationsDao;

    private static String databaseURL;

    /**
     * Creates database connection and DAO
     */

    public ProjectParticipationManager() {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseURL;
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.projectParticipationsDao = DaoManager.createDao(connectionSource, ProjectParticipation.class);
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
            TableUtils.createTable(connectionSource, ProjectParticipation.class);
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
            TableUtils.dropTable(connectionSource, ProjectParticipation.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Adds ProjectParticipation to database
     * @param projectParticipation ProjectParticipation to be added
     */
    public void addProjectParticipation(ProjectParticipation projectParticipation) {
        try {
            projectParticipationsDao.create(projectParticipation);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes ProjectParticipation with specified project id and person id from the database
     * @param project_id id of the associated project
     * @param person_id id of the associated person
     */

    public void removeProjectParticipation(int project_id, int person_id) {
        try {
            DeleteBuilder<ProjectParticipation, Object> builder = projectParticipationsDao.deleteBuilder();
            builder.where().eq("project_id", project_id).and().eq("person_id", person_id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes ProjectParticipation with specified project id and person id from the database on a certain date
     * @param project_id id of the associated project
     * @param person_id id of the associated person
     * @param date specified date
     */
    public void removeProjectParticipationBasedOnDate(int project_id, int person_id, Date date) {
        try {
            DeleteBuilder<ProjectParticipation, Object> builder = projectParticipationsDao.deleteBuilder();
            builder.where().eq("project_id", project_id).and().eq("person_id", person_id).and().eq("participation_period", date);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }

    }

    /**
     * Removes all ProjectParticipations of a specified employee
     * @param person_id id of the employee
     */
    public void removeProjectParticipationBasedOnEmployeeId(int person_id) {
        try {
            DeleteBuilder<ProjectParticipation, Object> builder = projectParticipationsDao.deleteBuilder();
            builder.where().eq("person_id", person_id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }


    /**
     * Removes all ProjectParticipations on a specified project
     * @param projectId id of the project
     */

    public void removeProjectParticipationBasedOnProjectId(int projectId) {
        try {
            DeleteBuilder<ProjectParticipation, Object> builder = projectParticipationsDao.deleteBuilder();
            builder.where().eq("project_id", projectId);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes all ProjectParticipation from the database
     */

    public void removeAllProjectParticipations() {
        try {
            TableUtils.clearTable(connectionSource, ProjectParticipation.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns ProjectParticipations on Project with specified id
     * @param project_id id of the project
     * @return list of all ProjectParticipations on specified project
     */

    public List<ProjectParticipation> getProjectParticipationByProjectID(int project_id) {
        List<ProjectParticipation> projectParticipationList = new ArrayList<>();
        QueryBuilder<ProjectParticipation, Object> queryBuilder = projectParticipationsDao.queryBuilder();
        try {
            queryBuilder.where().eq("project_id", project_id);
            PreparedQuery<ProjectParticipation> preparedQuery = queryBuilder.prepare();
            projectParticipationList = projectParticipationsDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projectParticipationList;
    }

    /**
     * Returns ProjectParticipations on Project with specified id by specific employee
     * @param project_id id of the project
     * @param person_id id of the employee
     * @return list of all ProjectParticipations on specified project by specific employee
     */

    public List<ProjectParticipation> getProjectParticipationByProjectIDAndPersonID(int project_id, int person_id) {
        List<ProjectParticipation> projectParticipationList = new ArrayList<>();
        QueryBuilder<ProjectParticipation, Object> queryBuilder = projectParticipationsDao.queryBuilder();
        try {
            queryBuilder.where().eq("project_id", project_id).and().eq("person_id", person_id);
            PreparedQuery<ProjectParticipation> preparedQuery = queryBuilder.prepare();
            projectParticipationList = projectParticipationsDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projectParticipationList;
    }

    /**
     * Returns ProjectParticipations on by specific employee
     * @param person_id id of the employee
     * @return list of all ProjectParticipations by specific employee
     */

    public List<ProjectParticipation> getProjectParticipationByPersonID(int person_id) {
        List<ProjectParticipation> projectParticipationList = new ArrayList<>();
        QueryBuilder<ProjectParticipation, Object> queryBuilder = projectParticipationsDao.queryBuilder();
        try {
            queryBuilder.where().eq("person_id", person_id);
            PreparedQuery<ProjectParticipation> preparedQuery = queryBuilder.prepare();
            projectParticipationList = projectParticipationsDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projectParticipationList;
    }

    /**
     * Returns list of all ProjectParticipations in database
     * @return
     */
    public List<ProjectParticipation> getAllProjectParticipations() {
        List<ProjectParticipation> projectParticipationList = new ArrayList<>();
        try {
            projectParticipationList = projectParticipationsDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return projectParticipationList;
    }

    /**
     * Returns number of rows with specified person id
     * @param id id of the person
     * @return row count with specified id
     */

    public int getRowCountByPersonID(int id) {
        return getProjectParticipationByPersonID(id).size();
    }

    /**
     * Returns number of rows with specified project id
     * @param id id of the project
     * @return row count with specified id
     */

    public int getRowCountByProjectID(int id) {
        return getProjectParticipationByProjectID(id).size();
    }


    /**
     * Sets the Path where the database can be found.
     *
     * @param databaseURL The path to the database.
     */
    public static void setDatabaseURL(String databaseURL) {
        ProjectParticipationManager.databaseURL = databaseURL;
    }

}
