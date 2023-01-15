package excelchaos_model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class EmployeeDataManager {
    private ConnectionSource connectionSource;
    private Dao<Employee, Integer> employeesDao;
    public EmployeeDataManager(){
        try{
            String databaseUrl = "jdbc:sqlite:Excelchaos.db";
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.employeesDao = DaoManager.createDao(connectionSource, Employee.class);
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
            employeesDao.create(employee);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public Employee getEmployee(int id){
        Employee employee = null;
        try {
            employee = employeesDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return employee;
    }

    public Employee getEmployeeByName(String surNameAndName){
        Employee employee = null;
        List<Employee> employeeList = getAllEmployees();
        for (Employee emp: employeeList) {
            String name = emp.getSurname() + " " + emp.getName();
            if(name.equals(surNameAndName)){
                employee = emp;
                return employee;
            }
        }
        return employee;
    }

    public List<Employee> getAllEmployees(){
        List<Employee> employees = null;
        try {
            employees = employeesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return employees;
    }

    public int getNextID(){
        int id = 0;
        try {
            QueryBuilder<Employee, Integer> builder = employeesDao.queryBuilder();
            builder.orderBy("id", false);
            Employee highest = employeesDao.queryForFirst(builder.prepare());
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

    public int getRowCount(){
        long count = 0;
        try {
            QueryBuilder<Employee, Integer> builder = employeesDao.queryBuilder();
            count = builder.countOf();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
        return (int)count;
    }

    public void removeEmployee(int id){
        try {
            DeleteBuilder<Employee, Integer> builder = employeesDao.deleteBuilder();
            builder.where().eq("id", id);
            builder.delete();
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public void removeAllEmployees(){
        try {
            TableUtils.clearTable(connectionSource, Employee.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+ ":" + e.getMessage());
        }
    }

    public String[] getAllEmployeesNameList(){
        List<Employee> employeeList = getAllEmployees();
        String[] names = new String[getRowCount()];
        for (int i = 0; i < names.length; i++) {
            names[i] = employeeList.get(i).getSurname() + " " + employeeList.get(i).getName();
        }
        Arrays.sort(names);
        return names;
    }
}
