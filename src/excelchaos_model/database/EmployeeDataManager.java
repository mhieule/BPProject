package excelchaos_model.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import excelchaos_model.database.Employee;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * Class to facilitate database integration for Employees
 */

public class EmployeeDataManager {
    private ConnectionSource connectionSource;
    private Dao<Employee, Integer> employeesDao;

    private static String databaseURL;


    /**
     * Constructor creates database connection and DAO manager
     */

    public EmployeeDataManager() {
        try {
            String databaseUrl = "jdbc:sqlite:" + databaseURL;
            this.connectionSource = new JdbcConnectionSource(databaseUrl);
            this.employeesDao = DaoManager.createDao(connectionSource, Employee.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to create the Employee table in the database
     */

    public void createTable() {
        try {
            TableUtils.createTable(connectionSource, Employee.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to delete the Employee table in the database
     */

    public void deleteTable() {
        try {
            TableUtils.dropTable(connectionSource, Employee.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to add an Employee to the database
     *
     * @param employee employee to be added to the database
     */

    public void addEmployee(Employee employee) {
        try {
            employeesDao.create(employee);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to select the employee with the given id from the database
     *
     * @param id id of the employee to be selected
     * @return the employee with the given id
     */

    public Employee getEmployee(int id) {
        Employee employee = null;
        try {
            employee = employeesDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return employee;
    }

    /**
     * Method to select an employee with a given name and surname from the database
     *
     * @param surNameAndName name and surname of the employee to be selected
     * @return the employee with a given name and surname
     */

    public Employee getEmployeeByName(String surNameAndName) {
        Employee employee = null;
        List<Employee> employeeList = getAllEmployees();
        for (Employee emp : employeeList) {
            String name = emp.getSurname() + " " + emp.getName();
            if (name.equals(surNameAndName)) {
                employee = emp;
                return employee;
            }
        }
        return employee;
    }

    /**
     * Method to select all employees from the databse
     *
     * @return list of all employees in the database
     */

    public List<Employee> getAllEmployees() {
        List<Employee> employees = null;
        try {
            employees = employeesDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return employees;
    }

    /**
     * Method to generate the next id not in use yet
     *
     * @return next id not in use yet
     */

    public int getNextID() {
        int id = 0;
        try {
            QueryBuilder<Employee, Integer> builder = employeesDao.queryBuilder();
            builder.orderBy("id", false);
            Employee highest = employeesDao.queryForFirst(builder.prepare());
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
     * Method to get the total number of rows in the employee table
     *
     * @return total number of rows in the employee table
     */

    public int getRowCount() {
        long count = 0;
        try {
            QueryBuilder<Employee, Integer> builder = employeesDao.queryBuilder();
            count = builder.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
        return (int) count;
    }

    /**
     * Method to remove the employee with the given id from the database
     *
     * @param id id of the employee to be removed
     */

    public void removeEmployee(int id) {
        try {
            DeleteBuilder<Employee, Integer> builder = employeesDao.deleteBuilder();
            builder.where().eq("id", id);
            builder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to remove all employees from the database
     */

    public void removeAllEmployees() {
        try {
            TableUtils.clearTable(connectionSource, Employee.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
        }
    }

    /**
     * Method to generate a list of all employee names and surnames
     *
     * @return list of all names and surnames of employees in the database
     */

    public String[] getAllEmployeesNameList() {
        List<Employee> employeeList = getAllEmployees();
        String[] names = new String[getRowCount()];
        for (int i = 0; i < names.length; i++) {
            names[i] = employeeList.get(i).getSurname() + " " + employeeList.get(i).getName();
        }
        Arrays.sort(names);
        return names;
    }

    /**
     * Method to update the given employee in the database
     *
     * @param employee employee to be updated
     */

    public void updateEmployee(Employee employee) {
        try {
            employeesDao.update(employee);
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
        EmployeeDataManager.databaseURL = databaseURL;
    }
}
