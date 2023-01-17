package excelchaos_model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryTableManager {
    private ConnectionSource connectionSource;
    private Dao<SalaryTable, Object> salaryTablesDao;
    public SalaryTableManager() {
        try {
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.salaryTablesDao = DaoManager.createDao(connectionSource, SalaryTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, SalaryTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void deleteTable(){
        try {
            TableUtils.dropTable(connectionSource, SalaryTable.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addSalaryTable(SalaryTable salaryTable){
        try {
            salaryTablesDao.create(salaryTable);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public List<SalaryTable> getSalaryTable(String tableName){
        List<SalaryTable> salaryTables = new ArrayList<>();
        QueryBuilder<SalaryTable, Object> queryBuilder = salaryTablesDao.queryBuilder();
        try {
            queryBuilder.where().eq("table_name",tableName);
            PreparedQuery<SalaryTable> preparedQuery = queryBuilder.prepare();
            salaryTables = salaryTablesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return salaryTables;
    }

    public List<String> getDistinctTableNames(String paygrade){
        List<SalaryTable> salaryTables = new ArrayList<>();
        List<String> tableNames = new ArrayList<>();
        QueryBuilder<SalaryTable, Object> queryBuilder = salaryTablesDao.queryBuilder();
        try {
            queryBuilder.distinct().selectColumns("table_name");
            queryBuilder.where().eq("paygrade",paygrade);
            PreparedQuery<SalaryTable> preparedQuery = queryBuilder.prepare();
            salaryTables = salaryTablesDao.query(preparedQuery);
            for (SalaryTable salaryTable: salaryTables){
                tableNames.add(salaryTable.getTable_name());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return tableNames;
    }

    public int getNumOfTables(String paygrade){
        List<String> tableNames = getDistinctTableNames(paygrade);
        return tableNames.size();
    }
}
