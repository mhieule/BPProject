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

public class SalaryIncreaseHistoryManager {
    private ConnectionSource connectionSource;
    private Dao<SalaryIncreaseHistory, Object> salaryIncreaseHistoriesDao;

    private static String databaseURL;


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

    public void createTable() {
        try {
            TableUtils.createTable(connectionSource, SalaryIncreaseHistory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void deleteTable() {
        try {
            TableUtils.dropTable(connectionSource, SalaryIncreaseHistory.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void addSalaryIncreaseHistory(SalaryIncreaseHistory salaryIncreaseHistory) {
        try {
            salaryIncreaseHistoriesDao.create(salaryIncreaseHistory);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

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

    public void removeAllSalaryIncreaseHistories() {
        try {
            TableUtils.clearTable(connectionSource, SalaryIncreaseHistory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

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
