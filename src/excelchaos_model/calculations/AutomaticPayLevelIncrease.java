package excelchaos_model.calculations;

import excelchaos_model.database.Contract;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.Employee;
import excelchaos_model.database.EmployeeDataManager;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class AutomaticPayLevelIncrease {
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private ContractDataManager contractDataManager = new ContractDataManager();
    private List<Employee> employeeList;

    private List<Date>[] payLevelIncreaseForEmployees;

    private int numberOfEmployees;

    /**
     * Constructor creates a new AutomaticPayLevelIncrease Object which prepares the object for Performing automatic paylevel increases of employees.
     */
    public AutomaticPayLevelIncrease() {
        numberOfEmployees = employeeDataManager.getAllEmployees().size();
        employeeList = employeeDataManager.getAllEmployees();
        payLevelIncreaseForEmployees = new List[numberOfEmployees];
    }

    /**
     * Performs the paylevel increases of all employees that are not SHK when the program is started.
     */
    public void performPayLevelIncrease() {
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        int i = 0;
        for (Employee employee : employeeList) {
            if (employee.getStatus().equals("SHK")) {

            } else {
                if (contractDataManager.getContract(employee.getId()) == null) {
                    i++;
                    continue;
                }
                payLevelIncreaseForEmployees[i] = ProjectedSalaryModel.calculatePayLevelIncrease(contractDataManager.getContract(employee.getId()).getStart_date(), contractDataManager.getContract(employee.getId()).getPaylevel());
                if (currentDate.compareTo(payLevelIncreaseForEmployees[i].get(0)) >= 0) {
                    setNewPayLevel(contractDataManager.getContract(employee.getId()));
                }
                if (i < numberOfEmployees) {
                    i++;
                } else break;
            }
        }

    }

    /**
     * This method performs the paylevel increase for a single employee based on a date that is given to the method through an input.
     * It is later used for a future or past salary projection.
     *
     * @param givenDate The date the paylevel will be projected on.
     * @param contract  The contract of the employee which paylevel is projected.
     * @return A contract that has the new paylevel set as its attribute. This value is never saved in the database.
     */
    public Contract performPayLevelIncreaseBasedOnGivenDate(Date givenDate, Contract contract) {
        contract.setPaylevel(ProjectedSalaryModel.calculatePayLevelBasedOnDate(contract.getStart_date(), contract.getPaylevel(), givenDate));
        return contract;
    }

    /**
     * This method sets a new paylevel for a contract it is given. The updated contract is saved in the database.
     *
     * @param contract The contract of the employee that is being updated in the method.
     */
    private void setNewPayLevel(Contract contract) {
        switch (contract.getPaylevel()) {
            case "1A":
                contract.setPaylevel("1B");
                contractDataManager.updateContract(contract);
                break;
            case "1B":
            case "1":
                contract.setPaylevel("2");
                contractDataManager.updateContract(contract);
                break;
            case "2":
                contract.setPaylevel("3");
                contractDataManager.updateContract(contract);
                break;
            case "3":
                contract.setPaylevel("4");
                contractDataManager.updateContract(contract);
                break;
            case "4":
                contract.setPaylevel("5");
                contractDataManager.updateContract(contract);
                break;
            case "5":
                contract.setPaylevel("6");
                contractDataManager.updateContract(contract);
                break;
            case "6":
                break;
        }
    }
}
