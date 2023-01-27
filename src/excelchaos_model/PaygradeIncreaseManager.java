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

public class PaygradeIncreaseManager {
    private ConnectionSource connectionSource;
    private Dao<PaygradeIncrease, Object> paygradeIncreasesDao;
    public PaygradeIncreaseManager() {
        try {
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.paygradeIncreasesDao = DaoManager.createDao(connectionSource, PaygradeIncrease.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, PaygradeIncrease.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void deleteTable(){
        try {
            TableUtils.dropTable(connectionSource, PaygradeIncrease.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addPaygradeIncrease(PaygradeIncrease paygradeIncrease){
        try {
            paygradeIncreasesDao.create(paygradeIncrease);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public List<PaygradeIncrease> getPaygradeIncrease(int id){
        List<PaygradeIncrease> paygradeIncreaseList = new ArrayList<>();
        QueryBuilder<PaygradeIncrease,Object> queryBuilder = paygradeIncreasesDao.queryBuilder();
        try {
            queryBuilder.where().eq("id",id);
            PreparedQuery<PaygradeIncrease> preparedQuery = queryBuilder.prepare();
            paygradeIncreaseList = paygradeIncreasesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return paygradeIncreaseList;
    }

    public int getRowCount(int id){
        List<PaygradeIncrease> paygradeIncreaseList = getPaygradeIncrease(id);
        return paygradeIncreaseList.size();
    }
}
