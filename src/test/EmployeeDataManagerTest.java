import excelchaos_model.Employee;
import excelchaos_model.EmployeeDataManager;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EmployeeDataManagerTest {
    @Test
    void testRemoveAll(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        assertEquals(manager.getAllEmployees().size(),0);
    }
    @Test
    void testGetValid(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        int id = 1;
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "France";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = true;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = calendar.getTime();
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        Employee employee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.addEmployee(employee);
        Employee recEmployee = manager.getEmployee(1);
        assertEquals(recEmployee.getId(), employee.getId());
        assertEquals(recEmployee.getSurname(), employee.getSurname());
        assertEquals(recEmployee.getName(), employee.getName());
        assertEquals(recEmployee.getEmail_private(), employee.getEmail_private());
        assertEquals(recEmployee.getPhone_private(), employee.getPhone_private());
        assertEquals(recEmployee.getCitizenship_1(), employee.getCitizenship_1());
        assertEquals(recEmployee.getCitizenship_2(), employee.getCitizenship_2());
        assertEquals(recEmployee.getEmployee_number(), employee.getEmployee_number());
        assertEquals(recEmployee.getTu_id(), employee.getTu_id());
        assertEquals(recEmployee.getVisa_required(), employee.getVisa_required());
        assertEquals(recEmployee.getVisa_expiration(),employee.getVisa_expiration());
        assertEquals(recEmployee.getDate_of_birth(), employee.getDate_of_birth());
        assertEquals(recEmployee.getPhone_tuda(), employee.getPhone_tuda());
        assertEquals(recEmployee.getHouse_number(), employee.getHouse_number());
        assertEquals(recEmployee.getZip_code(), employee.getZip_code());
        assertEquals(recEmployee.getAdditional_address(), employee.getAdditional_address());
        assertEquals(recEmployee.getCity(), employee.getCity());
        assertEquals(recEmployee.getStreet(), employee.getStreet());
    }
    @Test
    void testGetValidNoVisa(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        int id = 1;
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        Employee employee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.addEmployee(employee);
        Employee recEmployee = manager.getEmployee(1);
        assertEquals(recEmployee.getId(), employee.getId());
        assertEquals(recEmployee.getSurname(), employee.getSurname());
        assertEquals(recEmployee.getName(), employee.getName());
        assertEquals(recEmployee.getEmail_private(), employee.getEmail_private());
        assertEquals(recEmployee.getPhone_private(), employee.getPhone_private());
        assertEquals(recEmployee.getCitizenship_1(), employee.getCitizenship_1());
        assertEquals(recEmployee.getCitizenship_2(), employee.getCitizenship_2());
        assertEquals(recEmployee.getEmployee_number(), employee.getEmployee_number());
        assertEquals(recEmployee.getTu_id(), employee.getTu_id());
        assertEquals(recEmployee.getVisa_required(), employee.getVisa_required());
        assertEquals(recEmployee.getVisa_expiration(),employee.getVisa_expiration());
        assertEquals(recEmployee.getDate_of_birth(), employee.getDate_of_birth());
        assertEquals(recEmployee.getPhone_tuda(), employee.getPhone_tuda());
        assertEquals(recEmployee.getHouse_number(), employee.getHouse_number());
        assertEquals(recEmployee.getZip_code(), employee.getZip_code());
        assertEquals(recEmployee.getAdditional_address(), employee.getAdditional_address());
        assertEquals(recEmployee.getCity(), employee.getCity());
        assertEquals(recEmployee.getStreet(), employee.getStreet());
    }

    @Test
    void testGetInvalid(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        int id = 1;
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        Employee employee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.addEmployee(employee);
        Employee recEmployee = manager.getEmployee(2);
        assertNull(recEmployee);
    }
    @Test
    void testRemoveValid(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        int id = 1;
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        Employee employee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.addEmployee(employee);
        manager.removeEmployee(1);
        Employee recEmployee = manager.getEmployee(1);
        assertNull(recEmployee);
    }

    @Test
    void testRemoveInvalid(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        int id = 1;
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        Employee employee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.addEmployee(employee);
        manager.removeEmployee(2);
        assertEquals(manager.getAllEmployees().size(),1);
    }

    @Test
    void testGetAll(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        var employees = new Employee[10];
        for (int i = 0; i < 10; i++){
            Employee employee = new Employee(i, surname, name, email_private, phone_private, citizenship_1,
                    citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                    salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
            manager.addEmployee(employee);
            employees[i] = employee;
        }
        List<Employee> recEmployee = manager.getAllEmployees();
        for (int i = 0; i < 10; i++){
            assertEquals(recEmployee.get(i).getId(), employees[i].getId());
            assertEquals(recEmployee.get(i).getSurname(), employees[i].getSurname());
            assertEquals(recEmployee.get(i).getName(), employees[i].getName());
            assertEquals(recEmployee.get(i).getEmail_private(), employees[i].getEmail_private());
            assertEquals(recEmployee.get(i).getPhone_private(), employees[i].getPhone_private());
            assertEquals(recEmployee.get(i).getCitizenship_1(), employees[i].getCitizenship_1());
            assertEquals(recEmployee.get(i).getCitizenship_2(), employees[i].getCitizenship_2());
            assertEquals(recEmployee.get(i).getEmployee_number(), employees[i].getEmployee_number());
            assertEquals(recEmployee.get(i).getTu_id(), employees[i].getTu_id());
            assertEquals(recEmployee.get(i).getVisa_required(), employees[i].getVisa_required());
            assertEquals(recEmployee.get(i).getVisa_expiration(),employees[i].getVisa_expiration());
            assertEquals(recEmployee.get(i).getDate_of_birth(), employees[i].getDate_of_birth());
            assertEquals(recEmployee.get(i).getPhone_tuda(), employees[i].getPhone_tuda());
            assertEquals(recEmployee.get(i).getHouse_number(), employees[i].getHouse_number());
            assertEquals(recEmployee.get(i).getZip_code(), employees[i].getZip_code());
            assertEquals(recEmployee.get(i).getAdditional_address(), employees[i].getAdditional_address());
            assertEquals(recEmployee.get(i).getCity(), employees[i].getCity());
            assertEquals(recEmployee.get(i).getStreet(), employees[i].getStreet());
        }
    }
    @Test
    void testGetNextID(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        int id = 1;
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        Employee employee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.addEmployee(employee);
        assertEquals(manager.getNextID(), 2);
    }
    @Test
    void testGetNextIDEmpty(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        assertEquals(manager.getNextID(),1);
    }
    @Test
    void testGetRowCount(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        for (int i = 0; i < 10; i++){
            Employee employee = new Employee(i, surname, name, email_private, phone_private, citizenship_1,
                    citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                    salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
            manager.addEmployee(employee);
        }
        assertEquals(manager.getRowCount(),10);
    }

    @Test
    void testGetRowCountEmpty(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        assertEquals(manager.getRowCount(),0);
    }

    @Test
    void testUpdateEmployeeValid(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        int id = 1;
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        Employee employee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, "street");
        manager.addEmployee(employee);
        Employee upEmployee = new Employee(id, "changed_test", "change_test", email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, "changed_test", visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.updateEmployee(upEmployee);
        Employee recEmployee = manager.getEmployee(1);
        assertEquals(recEmployee.getId(), upEmployee.getId());
        assertEquals(recEmployee.getSurname(), upEmployee.getSurname());
        assertEquals(recEmployee.getName(), upEmployee.getName());
        assertEquals(recEmployee.getEmail_private(), upEmployee.getEmail_private());
        assertEquals(recEmployee.getPhone_private(), upEmployee.getPhone_private());
        assertEquals(recEmployee.getCitizenship_1(), upEmployee.getCitizenship_1());
        assertEquals(recEmployee.getCitizenship_2(), upEmployee.getCitizenship_2());
        assertEquals(recEmployee.getEmployee_number(), upEmployee.getEmployee_number());
        assertEquals(recEmployee.getTu_id(), upEmployee.getTu_id());
        assertEquals(recEmployee.getVisa_required(), upEmployee.getVisa_required());
        assertEquals(recEmployee.getVisa_expiration(),upEmployee.getVisa_expiration());
        assertEquals(recEmployee.getDate_of_birth(), upEmployee.getDate_of_birth());
        assertEquals(recEmployee.getPhone_tuda(), upEmployee.getPhone_tuda());
        assertEquals(recEmployee.getHouse_number(), upEmployee.getHouse_number());
        assertEquals(recEmployee.getZip_code(), upEmployee.getZip_code());
        assertEquals(recEmployee.getAdditional_address(), upEmployee.getAdditional_address());
        assertEquals(recEmployee.getCity(), upEmployee.getCity());
        assertEquals(recEmployee.getStreet(), upEmployee.getStreet());
    }
    @Test
    void testUpdateEmployeeInvalid(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        int id = 1;
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        Employee employee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.addEmployee(employee);
        Employee upEmployee = new Employee(3, "changed_test", "change_test", email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, "changed_test", visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.updateEmployee(upEmployee);
        Employee recEmployee = manager.getEmployee(1);
        assertEquals(recEmployee.getId(), employee.getId());
        assertEquals(recEmployee.getSurname(), employee.getSurname());
        assertEquals(recEmployee.getName(), employee.getName());
        assertEquals(recEmployee.getEmail_private(), employee.getEmail_private());
        assertEquals(recEmployee.getPhone_private(), employee.getPhone_private());
        assertEquals(recEmployee.getCitizenship_1(), employee.getCitizenship_1());
        assertEquals(recEmployee.getCitizenship_2(), employee.getCitizenship_2());
        assertEquals(recEmployee.getEmployee_number(), employee.getEmployee_number());
        assertEquals(recEmployee.getTu_id(), employee.getTu_id());
        assertEquals(recEmployee.getVisa_required(), employee.getVisa_required());
        assertEquals(recEmployee.getVisa_expiration(),employee.getVisa_expiration());
        assertEquals(recEmployee.getDate_of_birth(), employee.getDate_of_birth());
        assertEquals(recEmployee.getPhone_tuda(), employee.getPhone_tuda());
        assertEquals(recEmployee.getHouse_number(), employee.getHouse_number());
        assertEquals(recEmployee.getZip_code(), employee.getZip_code());
        assertEquals(recEmployee.getAdditional_address(), employee.getAdditional_address());
        assertEquals(recEmployee.getCity(), employee.getCity());
        assertEquals(recEmployee.getStreet(), employee.getStreet());
        assertNull(manager.getEmployee(3));
    }
    @Test
    void testGetEmployeeByNameValid(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        int id = 1;
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        Employee employee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.addEmployee(employee);
        Employee recEmployee = manager.getEmployeeByName(name + " " + surname);
        assertEquals(recEmployee.getId(), employee.getId());
        assertEquals(recEmployee.getSurname(), employee.getSurname());
        assertEquals(recEmployee.getName(), employee.getName());
        assertEquals(recEmployee.getEmail_private(), employee.getEmail_private());
        assertEquals(recEmployee.getPhone_private(), employee.getPhone_private());
        assertEquals(recEmployee.getCitizenship_1(), employee.getCitizenship_1());
        assertEquals(recEmployee.getCitizenship_2(), employee.getCitizenship_2());
        assertEquals(recEmployee.getEmployee_number(), employee.getEmployee_number());
        assertEquals(recEmployee.getTu_id(), employee.getTu_id());
        assertEquals(recEmployee.getVisa_required(), employee.getVisa_required());
        assertEquals(recEmployee.getVisa_expiration(),employee.getVisa_expiration());
        assertEquals(recEmployee.getDate_of_birth(), employee.getDate_of_birth());
        assertEquals(recEmployee.getPhone_tuda(), employee.getPhone_tuda());
        assertEquals(recEmployee.getHouse_number(), employee.getHouse_number());
        assertEquals(recEmployee.getZip_code(), employee.getZip_code());
        assertEquals(recEmployee.getAdditional_address(), employee.getAdditional_address());
        assertEquals(recEmployee.getCity(), employee.getCity());
        assertEquals(recEmployee.getStreet(), employee.getStreet());
    }

    @Test
    void testGetEmployeeByNameInvalid(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        int id = 1;
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        Employee employee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        manager.addEmployee(employee);
        Employee recEmployee = manager.getEmployeeByName("invalid_name" + " " + "invalid_surname");
        assertNull(recEmployee);
    }

    @Test
    void testGetAllEmployeesNamesList(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        String surname = "Surname1";
        String name  = "Name1";
        String email_private = "Email@1";
        String phone_private = "Phone_priv";
        String citizenship_1 = "Germany";
        String citizenship_2 = "";
        String employeeNumber = "123";
        String tu_id = "ab23cd";
        boolean visa_required = false;
        String status = "status_1";
        String transponder_number = "2345";
        String office_number = "a1";
        String salaryPlannedUntil = "1234";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date visaExpiration = null;
        Date dateOfBirth = calendar.getTime();
        String phone_tuda = "1234";
        String houseNumber = "test_1";
        String zip_code = "test_2";
        String additional_address = "test_3";
        String city = "test_4";
        String street = "test_1";
        var employees = new Employee[10];
        for (int i = 0; i < 10; i++){
            Employee employee = new Employee(i, surname, name, email_private, phone_private, citizenship_1,
                    citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                    salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
            manager.addEmployee(employee);
            employees[i] = employee;
        }
        String[] namesList = manager.getAllEmployeesNameList();
        for(int i = 0; i < 10; i++){
            assertEquals(namesList[i], employees[i].getSurname() + " " + employees[i].getName());
        }
    }

    @Test
    void testGetAllEmployeesNamesListEmpty(){
        var manager = new EmployeeDataManager();
        manager.removeAllEmployees();
        String[] namesList = manager.getAllEmployeesNameList();
        assertEquals(namesList.length, 0);
    }
}
