package excelchaos_model.calculations;

import excelchaos_model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class SalaryCalculation {
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private ContractDataManager contractDataManager = new ContractDataManager();

    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();

    private ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();



    public SalaryCalculation() {

    }
    //TODO Großflächig Testen insbesondere If Verzweigungen.
    public void determineCurrentSalary() {
        double result = 0;
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Employee> employees = employeeDataManager.getAllEmployees();
        CalculateSalaryBasedOnPayRateTable calculateSalaryBasedOnPayRateTable = new CalculateSalaryBasedOnPayRateTable();

        for (Employee employee : employees) {
            Contract contract = contractDataManager.getContract(employee.getId());
            LocalDate currentManualSalaryDate = getLastCurrentManualInsertedSalaryDate(employee.getId(), currentDate);
            LocalDate currentSalaryIncreaseDate = getLastCurrentSalaryIncreaseDate(employee.getId(), currentDate);
            LocalDate lastPayLevelIncreaseDate = getLastCurrentPayLevelIncreaseDate(contract.getStart_date(), currentDate);

            if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
                result = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
                result = calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(contract)[0];
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate == null) {
                if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                    result = getLastCurrentSalaryIncrease(employee.getId(), currentDate);
                } else {
                    result = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                }
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
                if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                    result = calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(contract)[0];
                } else {
                    result = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                }
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate == null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
                if (currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                    result = getLastCurrentSalaryIncrease(employee.getId(), currentDate);
                } else {
                    result = calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(contract)[0];
                }
                contract.setRegular_cost(result);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
                if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0 && currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                    result = getLastCurrentSalaryIncrease(employee.getId(), currentDate);
                    contract.setRegular_cost(result);
                    contractDataManager.updateContract(contract);
                } else if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                    result = calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(contract)[0];
                    contract.setRegular_cost(result);
                    contractDataManager.updateContract(contract);
                } else {
                    result = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                    contract.setRegular_cost(result);
                    contractDataManager.updateContract(contract);
                }
            }

        }

    }


    public double determineSalaryOfGivenMonth(int employeeId, Date givenDate){
        double result = 0;
        CalculateSalaryBasedOnPayRateTable calculateSalaryBasedOnPayRateTable = new CalculateSalaryBasedOnPayRateTable();
        Contract contract = contractDataManager.getContract(employeeId);
        LocalDate currentManualSalaryDate = getLastCurrentManualInsertedSalaryDate(employeeId, givenDate);
        LocalDate currentSalaryIncreaseDate = getLastCurrentSalaryIncreaseDate(employeeId, givenDate);
        LocalDate lastPayLevelIncreaseDate = getLastCurrentPayLevelIncreaseDate(contract.getStart_date(), givenDate);
        AutomaticPayLevelIncrease payLevelIncrease = new AutomaticPayLevelIncrease();
        contract = payLevelIncrease.performPayLevelIncreaseBasedOnGivenDate(givenDate,contract);
        if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
            return result;
        }
        if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
            result = getLastCurrentManualInsertedSalary(employeeId, givenDate);
            return result;
        }
        if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
            result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract,lastPayLevelIncreaseDate)[0];
            return result;
        }
        if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate == null) {
            if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                result = getLastCurrentSalaryIncrease(employeeId, givenDate);
            } else {
                result = getLastCurrentManualInsertedSalary(employeeId, givenDate);
            }
            return result;
        }
        if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
            if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract,lastPayLevelIncreaseDate)[0];
            } else {
                result = getLastCurrentManualInsertedSalary(employeeId, givenDate);
            }
            return result;
        }
        if (currentManualSalaryDate == null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
            if (currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                result = getLastCurrentSalaryIncrease(employeeId, givenDate);
            } else {
                result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract,lastPayLevelIncreaseDate)[0];
            }
            return result;
        }
        if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
            if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0 && currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                result = getLastCurrentSalaryIncrease(employeeId, givenDate);
                return result;
            } else if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract,lastPayLevelIncreaseDate)[0];
                return result;
            } else {
                result = getLastCurrentManualInsertedSalary(employeeId, givenDate);
                return result;
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
        if(temporaryDate != null){
            lastManualInsertedSalaryDate = temporaryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else lastManualInsertedSalaryDate = null;

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
        if(temporaryDate != null){
            lastSalaryIncreaseDate = temporaryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else lastSalaryIncreaseDate = null;

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
        if(ProjectedSalaryModel.getLastPayLevelIncreaseDate(workingStartDate, currentDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()== null){
            return null;
        } else {
            return ProjectedSalaryModel.getLastPayLevelIncreaseDate(workingStartDate, currentDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

    }
}
