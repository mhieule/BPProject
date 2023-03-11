package excelchaos_model.datamodel;

import excelchaos_model.calculations.SalaryTableLookUp;
import excelchaos_model.database.Contract;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.Employee;
import excelchaos_model.database.EmployeeDataManager;

import java.math.BigDecimal;
import java.util.ConcurrentModificationException;
import java.util.Date;

public class EmployeeDataInserter {
    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();
    private ContractDataManager contractDataManager = ContractDataManager.getInstance();
    private SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();


    public Employee insertNewEmployeeData(String name, String surname, String email_private, String phone_private,
                                      String citizenship_1, String citizenship_2, String employeeNumber, String tu_id, boolean visa_required,
                                      String status, String transponder_number, String office_number, String phone_tuda,
                                      Date salaryPlannedUntil, Date visaExpiration, Date dateOfBirth, String houseNumber,
                                      String zip_code, String additional_address, String city, String street,String payGrade, String payLevel, Date workStart, Date workEnd,
                                      BigDecimal scope, String hiwiTypeOfPayment, boolean vbl){

        int id = employeeDataManager.getNextID();
        Employee employee = new Employee(id, name, surname, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil, visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);

        employeeDataManager.addEmployee(employee);


        Contract newContract = new Contract(id, payGrade, payLevel, workStart, workEnd, new BigDecimal(0), new BigDecimal(0), scope, hiwiTypeOfPayment, vbl);
        BigDecimal[] startSalary;
        startSalary = salaryTableLookUp.getCurrentPayRateTableEntry(newContract);
        BigDecimal regular_cost = startSalary[0]; //TODO Berechnung für SHK ändern
        BigDecimal bonus_cost = startSalary[1].multiply(new BigDecimal(12));
        newContract = new Contract(id, payGrade, payLevel, workStart, workEnd, regular_cost, bonus_cost, scope, hiwiTypeOfPayment, vbl);
        contractDataManager.addContract(newContract);

        return employee;
    }

    public Employee updateExistingEmployeeData(int employeeID,String name, String surname, String email_private, String phone_private,
                                           String citizenship_1, String citizenship_2, String employeeNumber, String tu_id, boolean visa_required,
                                           String typeOfJob, String transponder_number, String office_number, String phone_tuda,
                                           Date salaryPlannedUntil, Date visaExpiration, Date dateOfBirth, String houseNumber,
                                           String zip_code, String additional_address, String city, String street,String payGrade, String payLevel, Date workStart, Date workEnd,
                                           BigDecimal scope, String hiwiTypeOfPayment, boolean vbl){


        Employee employee = employeeDataManager.getEmployee(employeeID);
        Contract contract = contractDataManager.getContract(employeeID);

        employee.setSurname(surname);
        employee.setName(name);
        employee.setEmail_private(email_private);
        employee.setPhone_private(phone_private);
        employee.setCitizenship_1(citizenship_1);
        employee.setCitizenship_2(citizenship_2);
        employee.setEmployee_number(employeeNumber);
        employee.setTu_id(tu_id);
        employee.setVisa_required(visa_required);
        employee.setStatus(typeOfJob);
        employee.setTransponder_number(transponder_number);
        employee.setOffice_number(office_number);
        employee.setPhone_tuda(phone_tuda);
        employee.setSalary_planned_until(salaryPlannedUntil);
        employee.setVisa_expiration(visaExpiration);
        employee.setDate_of_birth(dateOfBirth);
        employee.setHouse_number(houseNumber);
        employee.setStreet(street);
        employee.setZip_code(zip_code);
        employee.setAdditional_address(additional_address);
        employee.setCity(city);



        contract.setPaygrade(payGrade);
        contract.setPaylevel(payLevel);
        contract.setStart_date(workStart);
        contract.setEnd_date(workEnd);
        contract.setShk_hourly_rate(hiwiTypeOfPayment);
        contract.setScope(scope);
        contract.setVbl_status(vbl); //TODO Unterscheidung bei der Berechnung SHK und Rest und an aktuelles Gehalt anpassen also nicht anhand der PayRateTable berechnen
        BigDecimal[] newCost = salaryTableLookUp.getCurrentPayRateTableEntry(contract);
        contract.setRegular_cost(newCost[0]);
        contract.setBonus_cost(newCost[1].multiply(new BigDecimal(12)));
        employeeDataManager.updateEmployee(employee);
        contractDataManager.updateContract(contract);

        return employee;
    }

    public void updateExistingContract(Contract contract){
        contractDataManager.updateContract(contract);
    }
}
