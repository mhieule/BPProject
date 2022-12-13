package excelchaos_model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InventoryDataManager {
    private ConnectionSource connectionSource;
    private Dao<Inventory, Integer> contractsDao;
    public InventoryDataManager(){
        try {
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.contractsDao = DaoManager.createDao(connectionSource, Inventory.class);
        } catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, Inventory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addInventory(Inventory inventory){
        try {
            contractsDao.create(inventory);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public Inventory getInventory(int id){
        Inventory inventory = null;
        try {
            inventory = contractsDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return inventory;
    }

    public List<Inventory> getAllInventories(){
        List<Inventory> inventories = null;
        try {
            inventories = contractsDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return inventories;
    }
}
