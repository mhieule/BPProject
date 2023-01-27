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

public class SalaryIncreaseHistoryManager {
    private ConnectionSource connectionSource;
    private Dao<SalaryIncreaseHistory, Integer> salaryIncreaseHistoriesDao;
    public SalaryIncreaseHistoryManager() {
        try {
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.salaryIncreaseHistoriesDao = DaoManager.createDao(connectionSource, SalaryIncreaseHistory.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, SalaryIncreaseHistory.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addSalaryIncreaseHistory(SalaryIncreaseHistory salaryIncreaseHistory){
        try {
            salaryIncreaseHistoriesDao.create(salaryIncreaseHistory);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public List<SalaryIncreaseHistory> getSalaryIncreaseHistory(int id){
        List<SalaryIncreaseHistory> salaryIncreaseHistoryList = new ArrayList<>();
        QueryBuilder<SalaryIncreaseHistory,Integer> queryBuilder = salaryIncreaseHistoriesDao.queryBuilder();
        try {
            queryBuilder.where().eq("id",id);
            PreparedQuery<SalaryIncreaseHistory> preparedQuery = queryBuilder.prepare();
            salaryIncreaseHistoryList = salaryIncreaseHistoriesDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return salaryIncreaseHistoryList;
    }

    public int getRowCount(int id){
        List<SalaryIncreaseHistory> salaryIncreaseHistoryList = getSalaryIncreaseHistory(id);
        return salaryIncreaseHistoryList.size();
    }
}
