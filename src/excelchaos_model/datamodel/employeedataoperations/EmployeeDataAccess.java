package excelchaos_model.datamodel.employeedataoperations;

import excelchaos_model.database.Contract;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.Employee;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.utility.StringAndBigDecimalFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmployeeDataAccess {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();

    public String[][] getEmployeeDataFromDataBase() {
        int lines = employeeDataManager.getRowCount();
        String resultData[][] = new String[lines][];
        int currentIndex = 0;
        List<Employee> employees = employeeDataManager.getAllEmployees();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Employee employee : employees) {
            Contract contract = contractDataManager.getContract(employee.getId());
            String id = String.valueOf(employee.getId());
            String name = employee.getName();
            String surname = employee.getSurname();
            String street = employee.getStreet();
            String houseNumber = employee.getHouse_number();
            String additionalAddress = employee.getAdditional_address();
            String zipCode = employee.getZip_code();
            String city = employee.getCity();
            String dateOfBirth;
            if(employee.getDate_of_birth() == null){
                dateOfBirth = "";
            }else {
                dateOfBirth = dateFormat.format(employee.getDate_of_birth());
            }
            String emailPrivate = employee.getEmail_private();
            String phonePrivate = employee.getPhone_private();
            String phoneTuda = employee.getPhone_tuda();
            String citizenship1 = employee.getCitizenship_1();
            String citizenship2 = employee.getCitizenship_2();
            String visaExpiration;
            if (employee.getVisa_expiration() != null) {
                visaExpiration = dateFormat.format(employee.getVisa_expiration());
            } else {
                visaExpiration = null;
            }
            String employeeNumber = employee.getEmployee_number();
            String transponderNumber = employee.getTransponder_number();
            String officeNumber = employee.getOffice_number();
            String tuId = employee.getTu_id();
            String typeOfJob = employee.getStatus();
            Date startDate = contract.getStart_date();
            String startDateString = dateFormat.format(startDate);
            Date endDate = contract.getEnd_date();
            String endDateString = dateFormat.format(endDate);
            String workScope = "";
            if(typeOfJob.equals("SHK")){
                workScope = StringAndBigDecimalFormatter.formatBigDecimalToHours(contract.getScope());
            } else {
                workScope = StringAndBigDecimalFormatter.formatPercentageToStringForScope(contract.getScope());
            }
            String payGrade = contract.getPaygrade();
            String payLevel = contract.getPaylevel();
            String vblStatus;
            String shkHourlyRate = contract.getShk_hourly_rate();
            if (contract.getVbl_status()) {
                vblStatus = "Pflichtig";
            } else vblStatus = "Befreit";
            if(typeOfJob.equals("SHK")){
                vblStatus = "";
            }

            Date salaryPlannedUntil = employee.getSalary_planned_until();
            String salaryPlannedUntilString = dateFormat.format(salaryPlannedUntil);

            String[] values = {id, surname, name, street, houseNumber, additionalAddress, zipCode, city, dateOfBirth, emailPrivate, phonePrivate, phoneTuda,
                    citizenship1, citizenship2, visaExpiration, employeeNumber, transponderNumber, officeNumber, tuId, typeOfJob, startDateString, endDateString, workScope, payGrade, payLevel, vblStatus, shkHourlyRate, salaryPlannedUntilString};
            resultData[currentIndex] = values;
            currentIndex++;
        }
        return resultData;
    }

    public String[][] getSalaryDataFromDataBase() {
        int lines = contractDataManager.getRowCount();
        String[][] resultData = new String[lines][];
        int currentIndex = 0;
        List<Contract> contracts = contractDataManager.getAllContracts();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Contract contract : contracts) {
            Employee employee = employeeDataManager.getEmployee(contract.getId());
            String id = String.valueOf(employee.getId());
            String name = employee.getName();
            String surname = employee.getSurname();
            String dateOfBirth;
            if(employee.getDate_of_birth() == null){
                dateOfBirth = "";
            }else {
                dateOfBirth = dateFormat.format(employee.getDate_of_birth());
            }
            String group = contract.getPaygrade();
            String stufe = contract.getPaylevel();
            System.out.println("Gehalt: " + contract.getRegular_cost());
            String gehalt = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getRegular_cost());
            String sonderzahlungen = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getBonus_cost());

            String[] values = {id, surname, name, dateOfBirth, group, stufe, gehalt, sonderzahlungen};
            resultData[currentIndex] = values;
            currentIndex++;
        }
        return resultData;
    }

    public String[] getEmployeeNamesList(){
        return employeeDataManager.getAllEmployeesNameList();
    }

    public List<Employee> getAllEmployees(){
        return employeeDataManager.getAllEmployees();
    }

    public Employee getEmployee(int employeeID){
        return employeeDataManager.getEmployee(employeeID);
    }

    public Contract getContract(int employeeID){
        return contractDataManager.getContract(employeeID);
    }



}
