package excelchaos_model.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import excelchaos_model.database.PaygradeIncrease;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Facilitates database integration for PaygradeIncrease
 */

public class PaygradeIncreaseManager {
    private ConnectionSource connectionSource;
    private Dao<PaygradeIncrease, Object> paygradeIncreasesDao;

    /**
     * Creates database connection and DAO
     */
    public PaygradeIncreaseManager() {
        try {
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.paygradeIncreasesDao = DaoManager.createDao(connectionSource, PaygradeIncrease.class);
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
            TableUtils.createTable(connectionSource, PaygradeIncrease.class);
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
            TableUtils.dropTable(connectionSource, PaygradeIncrease.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Adds a PaygradeIncrease to the database
     * @param paygradeIncrease paygradeIncrease to be added to the database
     */

    public void addPaygradeIncrease(PaygradeIncrease paygradeIncrease) {
        try {
            paygradeIncreasesDao.create(paygradeIncrease);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes paygradeIncrease from database
     * @param id id of the paygradeIncrease to be removed
     * @param date date of the paygradeIncrease to be removed
     */

    public void removePaygradeIncrease(int id, Date date) {
        try {
            DeleteBuilder<PaygradeIncrease, Object> builder = paygradeIncreasesDao.deleteBuilder();
            builder.where().eq("id", id).and().eq("start_date", date);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes all paygradeIncreases from the database
     */

    public void removeAllPaygradeIncreases() {
        try {
            TableUtils.clearTable(connectionSource, PaygradeIncrease.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns all paygradeIncreases with specified id
     * @param id id of the paygradeIncreases
     * @return List of paygradeIncreases
     */

    public List<PaygradeIncrease> getPaygradeIncrease(int id) {
        List<PaygradeIncrease> paygradeIncreaseList = new ArrayList<>();
        QueryBuilder<PaygradeIncrease, Object> queryBuilder = paygradeIncreasesDao.queryBuilder();
        try {
            queryBuilder.where().eq("id", id);
            PreparedQuery<PaygradeIncrease> preparedQuery = queryBuilder.prepare();
            paygradeIncreaseList = paygradeIncreasesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return paygradeIncreaseList;
    }

    /**
     * Returns all paygradeIncreases with specified id at specified date
     * @param id id of the paygradeIncreases
     * @param date date of the paygradeIncrease
     * @return List of paygradeIncreases
     */

    public List<PaygradeIncrease> getPaygradeIncreaseByDate(int id, Date date) {
        List<PaygradeIncrease> paygradeIncreaseList = new ArrayList<>();
        QueryBuilder<PaygradeIncrease, Object> queryBuilder = paygradeIncreasesDao.queryBuilder();
        try {
            queryBuilder.where().eq("id", id).and().eq("start_date", date);
            PreparedQuery<PaygradeIncrease> preparedQuery = queryBuilder.prepare();
            paygradeIncreaseList = paygradeIncreasesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return paygradeIncreaseList;
    }

    /**
     * Returns all paygradeIncreases in the table
     * @return List of all paygradeIncreases
     */

    public List<PaygradeIncrease> getAllPaygradeIncreases() {
        List<PaygradeIncrease> paygradeIncreaseList = new ArrayList<>();
        try {
            paygradeIncreaseList = paygradeIncreasesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return paygradeIncreaseList;
    }

    /**
     * Returns row count for specified id
     * @param id id of paygradeIncreases
     * @return number of rows
     */

    public int getRowCount(int id) {
        List<PaygradeIncrease> paygradeIncreaseList = getPaygradeIncrease(id);
        return paygradeIncreaseList.size();
    }
}
