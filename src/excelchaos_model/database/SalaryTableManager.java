package excelchaos_model.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import excelchaos_model.database.SalaryTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Facilitates database integration for SalaryTable
 */

public class SalaryTableManager {
    private ConnectionSource connectionSource;
    private Dao<SalaryTable, Object> salaryTablesDao;

    private static String databaseURL;


    /**
     * Creates database connection and DAO
     */

    public SalaryTableManager() {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseURL;
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.salaryTablesDao = DaoManager.createDao(connectionSource, SalaryTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Creates table in the database
     */

    public void createTable() {
        try {
            TableUtils.createTable(connectionSource, SalaryTable.class);
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
            TableUtils.dropTable(connectionSource, SalaryTable.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Adds SalaryTable to the database
     * @param salaryTable SalaryTable to be added
     */

    public void addSalaryTable(SalaryTable salaryTable) {
        try {
            salaryTablesDao.create(salaryTable);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns list of all SalaryTables in the database
     * @return list of all SalaryTables
     */

    public List<SalaryTable> getAllSalaryTables() {
        List<SalaryTable> salaryTableList = null;
        try {
            salaryTableList = salaryTablesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return salaryTableList;
    }

    /**
     * Returns SalaryTable with a specified table name
     * @param tableName table name of the SalaryTable
     * @return SalaryTable with specified name
     */

    public List<SalaryTable> getSalaryTable(String tableName) {
        List<SalaryTable> salaryTables = new ArrayList<>();
        QueryBuilder<SalaryTable, Object> queryBuilder = salaryTablesDao.queryBuilder();
        try {
            queryBuilder.where().eq("table_name", tableName);
            PreparedQuery<SalaryTable> preparedQuery = queryBuilder.prepare();
            salaryTables = salaryTablesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return salaryTables;
    }

    /**
     * Returns list of all distinct table names for a given paygrade
     * @param paygrade payrade
     * @return list of all distinct table names for given paygrade
     */

    public List<String> getDistinctTableNames(String paygrade) {
        List<SalaryTable> salaryTables = new ArrayList<>();
        List<String> tableNames = new ArrayList<>();
        QueryBuilder<SalaryTable, Object> queryBuilder = salaryTablesDao.queryBuilder();
        try {
            queryBuilder.distinct().selectColumns("table_name");
            queryBuilder.where().eq("paygrade", paygrade);
            PreparedQuery<SalaryTable> preparedQuery = queryBuilder.prepare();
            salaryTables = salaryTablesDao.query(preparedQuery);
            for (SalaryTable salaryTable : salaryTables) {
                tableNames.add(salaryTable.getTable_name());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return tableNames;
    }

    /**
     * Returns total number of tables with specified paygrade in the table
     * @param paygrade paygrade
     * @return number of tables
     */

    public int getNumOfTables(String paygrade) {
        List<String> tableNames = getDistinctTableNames(paygrade);
        return tableNames.size();
    }

    /**
     * Removes all tables from the database
     */

    public void removeAllTables() {
        try {
            TableUtils.clearTable(connectionSource, SalaryTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes all tables with specified table name from database
     * @param tableName name of the table
     */

    public void removeSalaryTable(String tableName) {
        try {
            DeleteBuilder<SalaryTable, Object> builder = salaryTablesDao.deleteBuilder();
            builder.where().eq("table_name", tableName);
            builder.delete();
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
        SalaryTableManager.databaseURL = databaseURL;
    }
}
