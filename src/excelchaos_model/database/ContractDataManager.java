package excelchaos_model.database;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import excelchaos_model.StartUp;
import excelchaos_model.database.Contract;
import excelchaos_model.export.CSVExporter;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Class to facilitate database integration for Contracts
 */
public class ContractDataManager {
    private ConnectionSource connectionSource;
    private Dao<Contract, Integer> contractsDao;

    private static String databaseURL;

    private static ContractDataManager contractDataManager;

    /**
     * Constructor creates database connection and DAO manager
     */
    private ContractDataManager() {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseURL;
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.contractsDao = DaoManager.createDao(connectionSource, Contract.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to create the Contract table in the database
     */

    public void createTable() {
        try {
            TableUtils.createTable(connectionSource, Contract.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to delete the Contract table in the database
     */

    public void deleteTable() {
        try {
            TableUtils.dropTable(connectionSource, Contract.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Methods adds a contract to the database
     *
     * @param contract contract to be added to
     */

    public void addContract(Contract contract) {
        try {
            contractsDao.create(contract);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to select contract with a given id from the database
     *
     * @param id id of the contract to be selected
     * @return contract with the given id
     */

    public Contract getContract(int id) {
        Contract contract = null;
        try {
            contract = contractsDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return contract;
    }

    /**
     * Method to select all contracts in the database
     *
     * @return all contracts in the database
     */

    public List<Contract> getAllContracts() {
        List<Contract> contracts = null;
        try {
            contracts = contractsDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return contracts;
    }

    /**
     * Method to get the total number of rows in the contract table
     *
     * @return total number of rows in contract table
     */

    public int getRowCount() {
        long count = 0;
        try {
            QueryBuilder<Contract, Integer> builder = contractsDao.queryBuilder();
            count = builder.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return (int) count;
    }

    /**
     * Method to update a given contract that's already in the database
     *
     * @param contract contract to be updated
     */

    public void updateContract(Contract contract) {
        try {
            contractsDao.update(contract);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to remove all contracts from the database
     */

    public void removeAllContracts() {
        try {
            TableUtils.clearTable(connectionSource, Contract.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to remove the contract with the given id from the database
     *
     * @param id id of the contract to be updated
     */

    public void removeContract(int id) {
        try {
            DeleteBuilder<Contract, Integer> builder = contractsDao.deleteBuilder();
            builder.where().eq("id", id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public static ContractDataManager getInstance() {
        if(contractDataManager == null){
            contractDataManager = new ContractDataManager();
        }
        return contractDataManager;
    }

    public static void setDatabaseURL(String databaseURL) {
        ContractDataManager.databaseURL = databaseURL;
    }

}
