package excelchaos_model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDataManager {
    private ConnectionSource connectionSource;
    private Dao<Employee, Integer> contractsDao;
    public EmployeeDataManager(){
        try{
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.contractsDao = DaoManager.createDao(connectionSource, Employee.class);
        }catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void createTable(){
        try {
            TableUtils.createTable(connectionSource, Employee.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void addEmployee(Employee employee){
        try {
            contractsDao.create(employee);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public Employee getEmployee(int id){
        Employee employee = null;
        try {
            employee = contractsDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return employee;
    }

    public List<Employee> getAllEmployees(){
        List<Employee> employees = null;
        try {
            employees = contractsDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return employees;
    }
}
