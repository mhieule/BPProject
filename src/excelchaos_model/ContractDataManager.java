package excelchaos_model;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class ContractDataManager {
    private ConnectionSource connectionSource;
    private Dao<Contract, Integer> contractsDao;
    public ContractDataManager() {
        try {
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.contractsDao = DaoManager.createDao(connectionSource, Contract.class);
        } catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, Contract.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void deleteTable(){
        try {
            TableUtils.dropTable(connectionSource, Contract.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addContract(Contract contract){
        try {
            contractsDao.create(contract);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public Contract getContract(int id){
        Contract contract = null;
        try {
            contract = contractsDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return contract;
    }

    public List<Contract> getAllContracts(){
        List<Contract> contracts = null;
        try {
            contracts = contractsDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return contracts;
    }

    public int getRowCount(){
        long count = 0;
        try {
            QueryBuilder<Contract, Integer> builder = contractsDao.queryBuilder();
            count = builder.countOf();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return (int)count;
    }

    public void updateContract(Contract contract){
        try {
            contractsDao.update(contract);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void removeAllContracts(){
        try {
            TableUtils.clearTable(connectionSource, Contract.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void removeContract(int id){
        try {
            DeleteBuilder<Contract, Integer> builder = contractsDao.deleteBuilder();
            builder.where().eq("id", id);
            builder.delete();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

}
