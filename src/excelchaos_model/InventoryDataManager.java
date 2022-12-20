package excelchaos_model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class InventoryDataManager {
    private ConnectionSource connectionSource;
    private Dao<Inventory, Integer> inventoriesDao;
    public InventoryDataManager(){
        try {
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.inventoriesDao = DaoManager.createDao(connectionSource, Inventory.class);
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
            inventoriesDao.create(inventory);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public Inventory getInventory(int id){
        Inventory inventory = null;
        try {
            inventory = inventoriesDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return inventory;
    }

    public List<Inventory> getAllInventories(){
        List<Inventory> inventories = null;
        try {
            inventories = inventoriesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return inventories;
    }

    public int getNextID(){
        int id = 0;
        try {
            QueryBuilder<Inventory, Integer> builder = inventoriesDao.queryBuilder();
            builder.orderBy("id", false);
            Inventory highest = inventoriesDao.queryForFirst(builder.prepare());
            if (highest == null){
                id = 1;
            }else{
                id = highest.getId()+1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return id;
    }
}
