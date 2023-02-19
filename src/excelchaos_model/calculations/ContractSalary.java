package excelchaos_model.calculations;

import excelchaos_model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ContractSalary {
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private ContractDataManager contractDataManager = new ContractDataManager();

    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();

    private ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();



    public ContractSalary() {

    }

    //TODO Großflächig testen (insbesondere die verschiedenen If Verzweigungen)
    public double determineCurrentSalary() throws ParseException {
        double result = 0;
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Employee> employees = employeeDataManager.getAllEmployees();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        CalculateSalaryBasedOnPayRateTable calculateSalaryBasedOnPayRateTable = new CalculateSalaryBasedOnPayRateTable();

        for (Employee employee : employees) {
            Contract contract = contractDataManager.getContract(employee.getId());
            LocalDate currentManualSalaryDate = getLastCurrentManualInsertedSalaryDate(employee.getId(), currentDate);
            LocalDate currentSalaryIncreaseDate = getLastCurrentSalaryIncreaseDate(employee.getId(), currentDate);
            LocalDate lastPayLevelIncreaseDate = getLastCurrentPayLevelIncreaseDate(dateFormat.parse(contract.getStart_date()), currentDate); //TODO Später ändern, wenn getStartDate ein Datum zurückgibt

            if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                return result;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
                result = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                return result;
            }
            if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
                result = calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(contract)[0];
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                return result;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate == null) {
                if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                    result = getLastCurrentSalaryIncrease(employee.getId(), currentDate);
                } else {
                    result = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                }
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                return result;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
                if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                    result = calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(contract)[0];
                } else {
                    result = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                }
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                return result;
            }
            if (currentManualSalaryDate == null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
                if (currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                    result = getLastCurrentSalaryIncrease(employee.getId(), currentDate);
                } else {
                    result = calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(contract)[0];
                }
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                return result;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
                if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0 && currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                    result = getLastCurrentSalaryIncrease(employee.getId(), currentDate);
                    contract.setRegular_cost(result);
                    contractDataManager.updateContract(contract);
                    return result;
                } else if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                    result = calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(contract)[0];
                    contract.setRegular_cost(result);
                    contractDataManager.updateContract(contract);
                    return result;
                } else {
                    result = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                    contract.setRegular_cost(result);
                    contractDataManager.updateContract(contract);
                    return result;
                }
            }

        }

        return result;

    }

    private LocalDate getLastCurrentManualInsertedSalaryDate(int id, Date currentDate) {
        LocalDate lastManualInsertedSalaryDate;
        Date temporaryDate = null;
        for (int i = 0; i < manualSalaryEntryManager.getRowCount(id); i++) {
            if (currentDate.compareTo(manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date()) >= 0) {
                if (temporaryDate == null) {
                    temporaryDate = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date();
                } else if (manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date().compareTo(temporaryDate) > 0) {
                    temporaryDate = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date();
                }
            }
        }
        lastManualInsertedSalaryDate = temporaryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return lastManualInsertedSalaryDate;
    }

    private double getLastCurrentManualInsertedSalary(int id, Date currentDate) {
        double lastManualInsertedSalary = 0;
        Date temporaryDate = null;
        for (int i = 0; i < manualSalaryEntryManager.getRowCount(id); i++) {
            if (currentDate.compareTo(manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date()) >= 0) {
                if (temporaryDate == null) {
                    temporaryDate = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date();
                    lastManualInsertedSalary = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getNew_salary();
                } else if (manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date().compareTo(temporaryDate) > 0) {
                    temporaryDate = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date();
                    lastManualInsertedSalary = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getNew_salary();
                }
            }
        }
        return lastManualInsertedSalary;
    }

    private LocalDate getLastCurrentSalaryIncreaseDate(int id, Date currentDate) {
        LocalDate lastSalaryIncreaseDate;
        Date temporaryDate = null;
        for (int i = 0; i < salaryIncreaseHistoryManager.getRowCount(id); i++) {
            if (currentDate.compareTo(salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date()) >= 0) {
                if (temporaryDate == null) {
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                } else if (salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date().compareTo(temporaryDate) > 0) {
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                }
            }
        }
        lastSalaryIncreaseDate = temporaryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return lastSalaryIncreaseDate;

    }

    private double getLastCurrentSalaryIncrease(int id, Date currentDate) {
        double lastSalaryIncrease = 0;
        Date temporaryDate = null;
        for (int i = 0; i < salaryIncreaseHistoryManager.getRowCount(id); i++) {
            if (currentDate.compareTo(salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date()) >= 0) {
                if (temporaryDate == null) {
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                    lastSalaryIncrease = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getNew_salary();
                } else if (salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date().compareTo(temporaryDate) > 0) {
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                    lastSalaryIncrease = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getNew_salary();
                }
            }
        }
        return lastSalaryIncrease;

    }

    private LocalDate getLastCurrentPayLevelIncreaseDate(Date workingStartDate, Date currentDate) {
        return ProjectedSalaryModel.getLastPayLevelIncreaseDate(workingStartDate, currentDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
