package excelchaos_model;

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
 * Class to facilitate database integration for the manual salary entry
 */

public class ManualSalaryEntryManager {
    private ConnectionSource connectionSource;
    private Dao<ManualSalaryEntry, Object> manualSalaryEntriesDao;

    /**
     * Constructor creates database connection and DAO manager
     */

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

    /**
     * Method to create the manual salary entry table in the database
     */

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, ManualSalaryEntry.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    /**
     * Method to delete the manual salary entry table in the database
     */

    public void deleteTable(){
        try {
            TableUtils.dropTable(connectionSource, ManualSalaryEntry.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    /**
     * Method to add a manual salary entry to the database
     * @param manualSalaryEntry manual salary entry to be added to the database
     */

    public void addManualSalaryEntry(ManualSalaryEntry manualSalaryEntry){
        try {
            manualSalaryEntriesDao.create(manualSalaryEntry);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    /**
     * Remove a manual salary entry for a given employee at a given date from the database
     * @param id id of the employee
     * @param date date of the manual salary entry
     */

    public void  removeManualSalaryEntry(int id, Date date){
        try{
            DeleteBuilder<ManualSalaryEntry, Object> builder = manualSalaryEntriesDao.deleteBuilder();
            builder.where().eq("id", id).and().eq("start_date", date);
            builder.delete();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    /**
     * Method to remove all manual salary entries from the database
     */

    public void removeAllManualSalaryEntries(){
        try {
            TableUtils.clearTable(connectionSource, ManualSalaryEntry.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    /**
     * Method to select all manual salary entries of the employee with the given id from the database
     * @param id employee's id
     * @return list of all manual salary entries of the employee
     */

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

    /**
     * Method to select the manual salary entry of a specific employee at a specific date
     * @param id id of the employee
     * @param date date of the manual salary entry
     * @return list of manual salary entries at given date
     */

    public List<ManualSalaryEntry> getManualSalaryEntryByDate(int id, Date date){
        List<ManualSalaryEntry> manualSalaryEntryList = new ArrayList<>();
        QueryBuilder<ManualSalaryEntry,Object> queryBuilder = manualSalaryEntriesDao.queryBuilder();
        try {
            queryBuilder.where().eq("id",id).and().eq("start_date", date);
            PreparedQuery<ManualSalaryEntry> preparedQuery = queryBuilder.prepare();
            manualSalaryEntryList = manualSalaryEntriesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return manualSalaryEntryList;
    }

    /**
     * Method to select all manual salary entries from the db
     * @return list of all manual salary entries in the database
     */

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

    /**
     * Method to select the total number of rows with a given id
     * @param id employee's
     * @return total number of rows
     */

    public int getRowCount(int id){
        List<ManualSalaryEntry> manualSalaryEntryList = getManualSalaryEntry(id);
        return manualSalaryEntryList.size();
    }
}
