package excelchaos_model.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import excelchaos_model.database.SalaryIncreaseHistory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  Facilitates database integration for SalaryIncreaseHistory
 */

public class SalaryIncreaseHistoryManager {
    private ConnectionSource connectionSource;
    private Dao<SalaryIncreaseHistory, Object> salaryIncreaseHistoriesDao;

    private static String databaseURL;

    /**
     * Creates database connection and DAO
     */

    public SalaryIncreaseHistoryManager() {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseURL;
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.salaryIncreaseHistoriesDao = DaoManager.createDao(connectionSource, SalaryIncreaseHistory.class);
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
            TableUtils.createTable(connectionSource, SalaryIncreaseHistory.class);
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
            TableUtils.dropTable(connectionSource, SalaryIncreaseHistory.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Adds SalaryIncreaseHistory to database
     * @param salaryIncreaseHistory SalaryIncreaseHistory to be added
     */

    public void addSalaryIncreaseHistory(SalaryIncreaseHistory salaryIncreaseHistory) {
        try {
            salaryIncreaseHistoriesDao.create(salaryIncreaseHistory);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes SalaryIncreaseHistory with specified id on specified date
     * @param id id associated with the SalaryIncreaseHistory
     * @param date date of the SalaryIncreaseHistory
     */

    public void removeSalaryIncreaseHistory(int id, Date date) {
        try {
            DeleteBuilder<SalaryIncreaseHistory, Object> builder = salaryIncreaseHistoriesDao.deleteBuilder();
            builder.where().eq("id", id).and().eq("start_date", date);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes all SalaryIncreaseHistories for specified employee
     * @param id id of the employee
     */

    public void removeAllSalaryIncreaseHistoryForEmployee(int id) {
        try {
            DeleteBuilder<SalaryIncreaseHistory, Object> builder = salaryIncreaseHistoriesDao.deleteBuilder();
            builder.where().eq("id", id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes all SalaryIncreaseHistories from table
     */

    public void removeAllSalaryIncreaseHistories() {
        try {
            TableUtils.clearTable(connectionSource, SalaryIncreaseHistory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns all SalaryIncreaseHistories for specified employee
     * @param id id of the employee
     * @return list with all SalaryIncreaseHistories
     */

    public List<SalaryIncreaseHistory> getSalaryIncreaseHistory(int id) {
        List<SalaryIncreaseHistory> salaryIncreaseHistoryList = new ArrayList<>();
        QueryBuilder<SalaryIncreaseHistory, Object> queryBuilder = salaryIncreaseHistoriesDao.queryBuilder();
        try {
            queryBuilder.where().eq("id", id);
            PreparedQuery<SalaryIncreaseHistory> preparedQuery = queryBuilder.prepare();
            salaryIncreaseHistoryList = salaryIncreaseHistoriesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return salaryIncreaseHistoryList;
    }

    /**
     * Returns all SalaryIncreaseHistories for specified employee on specified date
     * @param id id of the employee
     * @param date date of the SalaryIncreaseHistory
     * @return list with all SalaryIncreaseHistories
     */

    public List<SalaryIncreaseHistory> getSalaryIncreaseHistoryByDate(int id, Date date) {
        List<SalaryIncreaseHistory> salaryIncreaseHistoryList = new ArrayList<>();
        QueryBuilder<SalaryIncreaseHistory, Object> queryBuilder = salaryIncreaseHistoriesDao.queryBuilder();
        try {
            queryBuilder.where().eq("id", id).and().eq("start_date", date);
            PreparedQuery<SalaryIncreaseHistory> preparedQuery = queryBuilder.prepare();
            salaryIncreaseHistoryList = salaryIncreaseHistoriesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return salaryIncreaseHistoryList;
    }

    /**
     * Returns all SalaryIncreaseHistories in the table
     * @return list of all SalaryIncreaseHistories
     */

    public List<SalaryIncreaseHistory> getAllSalaryIncreaseHistories() {
        List<SalaryIncreaseHistory> salaryIncreaseHistoryList = new ArrayList<>();
        try {
            salaryIncreaseHistoryList = salaryIncreaseHistoriesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return salaryIncreaseHistoryList;
    }

    /**
     * Returns total row count of the table
     * @return row count
     */

    public int getRowCount(int id) {
        List<SalaryIncreaseHistory> salaryIncreaseHistoryList = getSalaryIncreaseHistory(id);
        return salaryIncreaseHistoryList.size();
    }

    /**
     * Sets the Path where the database can be found.
     *
     * @param databaseURL The path to the database.
     */
    public static void setDatabaseURL(String databaseURL) {
        SalaryIncreaseHistoryManager.databaseURL = databaseURL;
    }


}
