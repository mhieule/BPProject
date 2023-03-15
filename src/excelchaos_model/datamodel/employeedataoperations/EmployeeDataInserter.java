package excelchaos_model.datamodel.employeedataoperations;

import excelchaos_model.calculations.SalaryTableLookUp;
import excelchaos_model.database.Contract;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.Employee;
import excelchaos_model.database.EmployeeDataManager;

import java.math.BigDecimal;
import java.util.ConcurrentModificationException;
import java.util.Date;

public class EmployeeDataInserter {
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();
    private SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();


    /**
     * Inserts new employee data into the database and creates a new contract for the employee with the provided information.
     *
     * @param name               the name of the employee.
     * @param surname            the surname of the employee.
     * @param email_private      the private email of the employee.
     * @param phone_private      the private phone number of the employee.
     * @param citizenship_1      the primary citizenship of the employee.
     * @param citizenship_2      the secondary citizenship of the employee.
     * @param employeeNumber     the employee number of the employee.
     * @param tu_id              the TU-ID of the employee.
     * @param visa_required      whether the employee requires a visa.
     * @param status             the status of the employee.
     * @param transponder_number the transponder number of the employee.
     * @param office_number      the office number of the employee.
     * @param phone_tuda         the TU phone number of the employee.
     * @param salaryPlannedUntil the planned salary expiration date of the employee.
     * @param visaExpiration     the visa expiration date of the employee.
     * @param dateOfBirth        the date of birth of the employee.
     * @param houseNumber        the house number of the employee's address.
     * @param zip_code           the zip code of the employee's address.
     * @param additional_address additional address information of the employee.
     * @param city               the city of the employee's address.
     * @param street             the street of the employee's address.
     * @param payGrade           the pay grade of the employee's contract.
     * @param payLevel           the pay level of the employee's contract.
     * @param workStart          the start date of the employee's contract.
     * @param workEnd            the end date of the employee's contract.
     * @param scope              the scope of the employee's contract.
     * @param hiwiTypeOfPayment  the type of payment for the students contract.
     * @param vbl                whether the employee is eligible for VBL (Versorgungsanstalt des Bundes und der Länder) pension scheme.
     * @return the newly created Employee object.
     */
    public Employee insertNewEmployeeData(String name, String surname, String email_private, String phone_private,
                                          String citizenship_1, String citizenship_2, String employeeNumber, String tu_id, boolean visa_required,
                                          String status, String transponder_number, String office_number, String phone_tuda,
                                          Date salaryPlannedUntil, Date visaExpiration, Date dateOfBirth, String houseNumber,
                                          String zip_code, String additional_address, String city, String street, String payGrade, String payLevel, Date workStart, Date workEnd,
                                          BigDecimal scope, String hiwiTypeOfPayment, boolean vbl) {

        int id = employeeDataManager.getNextID();
        Employee employee = new Employee(id, name, surname, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil, visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);

        employeeDataManager.addEmployee(employee);


        Contract newContract = new Contract(id, payGrade, payLevel, workStart, workEnd, new BigDecimal(0), new BigDecimal(0), scope, hiwiTypeOfPayment, vbl);
        BigDecimal[] startSalary;
        startSalary = salaryTableLookUp.getCurrentPayRateTableEntry(newContract);
        BigDecimal regular_cost = startSalary[0];
        BigDecimal bonus_cost = startSalary[1].multiply(new BigDecimal(12));
        newContract = new Contract(id, payGrade, payLevel, workStart, workEnd, regular_cost, bonus_cost, scope, hiwiTypeOfPayment, vbl);
        contractDataManager.addContract(newContract);

        return employee;
    }

    /**
     * Updates the data of an existing employee and their contract with the provided information.
     *
     * @param employeeID         The ID of the employee to update.
     * @param name               The updated name of the employee.
     * @param surname            The updated surname of the employee.
     * @param email_private      The updated private email of the employee.
     * @param phone_private      The updated private phone number of the employee.
     * @param citizenship_1      The updated first citizenship of the employee.
     * @param citizenship_2      The updated second citizenship of the employee.
     * @param employeeNumber     The updated employee number of the employee.
     * @param tu_id              The updated TUD identification number of the employee.
     * @param visa_required      A boolean indicating whether a visa is required for the employee.
     * @param typeOfJob          The updated status or type of job of the employee.
     * @param transponder_number The updated transponder number of the employee.
     * @param office_number      The updated office phone number of the employee.
     * @param phone_tuda         The updated TUD phone number of the employee.
     * @param salaryPlannedUntil The updated date until which the salary is planned for the employee.
     * @param visaExpiration     The updated visa expiration date of the employee.
     * @param dateOfBirth        The updated date of birth of the employee.
     * @param houseNumber        The updated house number of the employee's address.
     * @param zip_code           The updated zip code of the employee's address.
     * @param additional_address The updated additional address information of the employee.
     * @param city               The updated city of the employee's address.
     * @param street             The updated street name of the employee's address.
     * @param payGrade           The updated pay grade of the employee's contract.
     * @param payLevel           The updated pay level of the employee's contract.
     * @param workStart          The updated start date of the employee's contract.
     * @param workEnd            The updated end date of the employee's contract.
     * @param scope              The updated scope of the employee's contract.
     * @param hiwiTypeOfPayment  The updated type of payment for Hiwi contracts.
     * @param vbl                A boolean indicating whether the employee has VBL status.
     * @return The updated employee object.
     */
    public Employee updateExistingEmployeeData(int employeeID, String name, String surname, String email_private, String phone_private,
                                               String citizenship_1, String citizenship_2, String employeeNumber, String tu_id, boolean visa_required,
                                               String typeOfJob, String transponder_number, String office_number, String phone_tuda,
                                               Date salaryPlannedUntil, Date visaExpiration, Date dateOfBirth, String houseNumber,
                                               String zip_code, String additional_address, String city, String street, String payGrade, String payLevel, Date workStart, Date workEnd,
                                               BigDecimal scope, String hiwiTypeOfPayment, boolean vbl) {


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
        contract.setVbl_status(vbl);
        BigDecimal[] newCost = salaryTableLookUp.getCurrentPayRateTableEntry(contract);
        contract.setRegular_cost(newCost[0]);
        contract.setBonus_cost(newCost[1].multiply(new BigDecimal(12)));
        employeeDataManager.updateEmployee(employee);
        contractDataManager.updateContract(contract);

        return employee;
    }

    /**
     * Updates an existing contract with the provided data in the contract data manager.
     *
     * @param contract the updated contract to be saved.
     */
    public void updateExistingContract(Contract contract) {
        contractDataManager.updateContract(contract);
    }
}
