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

/**
 *  Facilitates database integration for SHKSalaryTable
 */

public class SHKSalaryTableManager {
    private ConnectionSource connectionSource;

    private Dao<SHKSalaryEntry, Integer> SHKDao;

    private static String databaseURL;

    /**
     * Creates database connection and DAO
     */

    public SHKSalaryTableManager() {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseURL;
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.SHKDao = DaoManager.createDao(connectionSource, SHKSalaryEntry.class);
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
            TableUtils.createTable(connectionSource, SHKSalaryEntry.class);
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
            TableUtils.dropTable(connectionSource, SHKSalaryEntry.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Adds an SHKSalaryEntry to the database
     * @param shkSalaryEntry SHKSalaryEntry to be added
     */

    public void addSHKTableEntry(SHKSalaryEntry shkSalaryEntry) {
        try {
            SHKDao.create(shkSalaryEntry);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Returns SHKSalaryEntry with given id
     * @param id id of the SHKSalaryEntry
     * @return SHKSalaryEntry with specified id
     */

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

    /**
     * Returns SHKSalaryEntry from a given date
     * @param givenDate date of the SHKSalaryEntry
     * @return SHKSalaryEntry with specified date
     */

    public SHKSalaryEntry getSHKSalaryEntryBasedOnDate(Date givenDate) {
        List<SHKSalaryEntry> shkSalaryEntries = getAllSHKSalaryEntries();
        SHKSalaryEntry resultEntry = null;
        for (SHKSalaryEntry entry : shkSalaryEntries) {
            if (entry.getValidationDate().compareTo(givenDate) == 0) {
                resultEntry = entry;
            }
        }
        return resultEntry;
    }

    /**
     * Returns all SHKSalaryEntries from the table
     * @return List of all SHKSalaryEntries
     */
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

    /**
     * Returns next available id
     * @return next available id
     */

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

    /**
     * Returns total row count of the table
     * @return row count
     */

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

    /**
     * Updates SHKSalaryEntry in the table
     * @param shkSalaryEntry SHKSalaryEntry to be updated
     */

    public void updateSHKSalaryEntry(SHKSalaryEntry shkSalaryEntry) {
        try {
            SHKDao.update(shkSalaryEntry);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes all SHKSalaryEntry from the database
     */

    public void removeAllSHKSalaryEntries() {
        try {
            TableUtils.clearTable(connectionSource, SHKSalaryEntry.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Removes SHKSalaryEntry with specified id from the database
     * @param id id of the SHKSalaryEntry to be removed
     */

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


    /**
     * Sets the Path where the database can be found.
     *
     * @param databaseURL The path to the database.
     */
    public static void setDatabaseURL(String databaseURL) {
        SHKSalaryTableManager.databaseURL = databaseURL;
    }


}
