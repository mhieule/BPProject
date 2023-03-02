package excelchaos_model.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class SHKSalaryTableManager {
    private ConnectionSource connectionSource;

    private Dao<SHKSalaryEntry, Integer> SHKDao;

    private static String databaseURL;

    private static SHKSalaryTableManager shkSalaryTableManager;

    private SHKSalaryTableManager() {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseURL;
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.SHKDao = DaoManager.createDao(connectionSource,SHKSalaryEntry.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void createTable() {
        try {
            TableUtils.createTable(connectionSource, SHKSalaryEntry.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }


    public void deleteTable() {
        try {
            TableUtils.dropTable(connectionSource, SHKSalaryEntry.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void addSHKTableEntry(SHKSalaryEntry shkSalaryEntry) {
        try {
            SHKDao.create(shkSalaryEntry);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public SHKSalaryEntry getSHKSalaryEntry(int id) {
        SHKSalaryEntry shkSalaryEntry = null;
        try {
            shkSalaryEntry = SHKDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return shkSalaryEntry;
    }

    public SHKSalaryEntry getSHKSalaryEntryBasedOnDate(Date givenDate){
        List<SHKSalaryEntry> shkSalaryEntries = getAllSHKSalaryEntries();
        SHKSalaryEntry resultEntry = null;
        for (SHKSalaryEntry entry : shkSalaryEntries){
            if(entry.getValidationDate().compareTo(givenDate) == 0){
                resultEntry = entry;
            }
        }
        return resultEntry;
    }

    public List<SHKSalaryEntry> getAllSHKSalaryEntries() {
        List<SHKSalaryEntry> shkSalaryEntries = null;
        try {
            shkSalaryEntries = SHKDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return shkSalaryEntries;
    }

    public int getNextID() {
        int id = 0;
        try {
            QueryBuilder<SHKSalaryEntry, Integer> builder = SHKDao.queryBuilder();
            builder.orderBy("id", false);
            SHKSalaryEntry highest = SHKDao.queryForFirst(builder.prepare());
            if (highest == null) {
                id = 1;
            } else {
                id = highest.getId() + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return id;
    }

    public int getRowCount() {
        long count = 0;
        try {
            QueryBuilder<SHKSalaryEntry, Integer> builder = SHKDao.queryBuilder();
            count = builder.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return (int) count;
    }

    public void updateSHKSalaryEntry(SHKSalaryEntry shkSalaryEntry) {
        try {
            SHKDao.update(shkSalaryEntry);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void removeAllSHKSalaryEntries() {
        try {
            TableUtils.clearTable(connectionSource, SHKSalaryEntry.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void removeSHKSalaryEntry(int id) {
        try {
            DeleteBuilder<SHKSalaryEntry, Integer> builder = SHKDao.deleteBuilder();
            builder.where().eq("id", id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public static SHKSalaryTableManager getInstance() {
        if(shkSalaryTableManager == null){
            shkSalaryTableManager = new SHKSalaryTableManager();
        }
        return shkSalaryTableManager;
    }

    public static void setDatabaseURL(String databaseURL) {
        SHKSalaryTableManager.databaseURL = databaseURL;
    }


}
