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

public class ManualSalaryEntryManager {
    private ConnectionSource connectionSource;
    private Dao<ManualSalaryEntry, Object> manualSalaryEntriesDao;
    public ManualSalaryEntryManager() {
        try {
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.manualSalaryEntriesDao = DaoManager.createDao(connectionSource, ManualSalaryEntry.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, ManualSalaryEntry.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void deleteTable(){
        try {
            TableUtils.dropTable(connectionSource, ManualSalaryEntry.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addManualSalaryEntry(ManualSalaryEntry manualSalaryEntry){
        try {
            manualSalaryEntriesDao.create(manualSalaryEntry);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void removeAllManualSalaryEntries(){
        try {
            TableUtils.clearTable(connectionSource, ManualSalaryEntry.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public List<ManualSalaryEntry> getManualSalaryEntry(int id){
        List<ManualSalaryEntry> manualSalaryEntryList = new ArrayList<>();
        QueryBuilder<ManualSalaryEntry,Object> queryBuilder = manualSalaryEntriesDao.queryBuilder();
        try {
            queryBuilder.where().eq("id",id);
            PreparedQuery<ManualSalaryEntry> preparedQuery = queryBuilder.prepare();
            manualSalaryEntryList = manualSalaryEntriesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return manualSalaryEntryList;
    }

    public List<ManualSalaryEntry> getAllManualSalaryEntries(){
        List<ManualSalaryEntry> manualSalaryEntryList = new ArrayList<>();
        try {
            manualSalaryEntryList = manualSalaryEntriesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return manualSalaryEntryList;
    }

    public int getRowCount(int id){
        List<ManualSalaryEntry> manualSalaryEntryList = getManualSalaryEntry(id);
        return manualSalaryEntryList.size();
    }
}
