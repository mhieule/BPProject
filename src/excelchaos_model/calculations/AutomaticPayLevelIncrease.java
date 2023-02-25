package excelchaos_model.calculations;

import excelchaos_model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public AutomaticPayLevelIncrease() {
        numberOfEmployees = employeeDataManager.getAllEmployees().size();
        employeeList = employeeDataManager.getAllEmployees();
        payLevelIncreaseForEmployees = new List[numberOfEmployees];
    }

    //TODO Großflächig testen
    public void performPayLevelIncrease() {
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        int i = 0;
        for (Employee employee : employeeList) {
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

    public Contract performPayLevelIncreaseBasedOnGivenDate(Date givenDate, Contract contract) {
        contract.setPaylevel(ProjectedSalaryModel.calculatePayLevelBasedOnDate(contract.getStart_date(), contract.getPaylevel(), givenDate));
        return contract;
    }


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
