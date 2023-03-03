package excelchaos_model.calculations;

import excelchaos_model.*;
import excelchaos_model.database.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SalaryCalculation {
    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();

    private ContractDataManager contractDataManager = ContractDataManager.getInstance();

    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = SalaryIncreaseHistoryManager.getInstance();

    private ManualSalaryEntryManager manualSalaryEntryManager = ManualSalaryEntryManager.getInstance();


    public SalaryCalculation() {

    }

    //TODO Großflächig Testen insbesondere If Verzweigungen.
    //TODO
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


    public double determineSalaryOfGivenMonth(int employeeId, Date givenDate) {
        double result = 0;
        CalculateSalaryBasedOnPayRateTable calculateSalaryBasedOnPayRateTable = new CalculateSalaryBasedOnPayRateTable();
        Contract contract = contractDataManager.getContract(employeeId);
        LocalDate currentManualSalaryDate = getLastCurrentManualInsertedSalaryDate(employeeId, givenDate);
        LocalDate currentSalaryIncreaseDate = getLastCurrentSalaryIncreaseDate(employeeId, givenDate);
        LocalDate lastPayLevelIncreaseDate = getLastCurrentPayLevelIncreaseDate(contract.getStart_date(), givenDate);
        AutomaticPayLevelIncrease payLevelIncrease = new AutomaticPayLevelIncrease();
        contract = payLevelIncrease.performPayLevelIncreaseBasedOnGivenDate(givenDate, contract);
        LocalDate chosenLocalDate = givenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate payRateTableChangeDate = calculateSalaryBasedOnPayRateTable.getActivePayRateTableDateBasedOnGivenDate(contract, chosenLocalDate);

        if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
            return result;
        }
        if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
            result = getLastCurrentManualInsertedSalary(employeeId, givenDate);
            return result;
        }
        if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
            result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract, chosenLocalDate)[0];
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
            System.out.println(payRateTableChangeDate + employeeDataManager.getEmployee(contract.getId()).getSurname());
            if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract, chosenLocalDate)[0];
            } else {
                if (payRateTableChangeDate.compareTo(currentManualSalaryDate) > 0) {
                    result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract, chosenLocalDate)[0];
                } else {
                    result = getLastCurrentManualInsertedSalary(employeeId, givenDate);
                }
            }
            return result;
        }
        if (currentManualSalaryDate == null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
            if (currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                if (currentSalaryIncreaseDate.compareTo(payRateTableChangeDate) > 0) {
                    result = getLastCurrentSalaryIncrease(employeeId, givenDate);
                } else
                    result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract, chosenLocalDate)[0];
            } else {
                result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract, chosenLocalDate)[0];
            }
            return result;
        }
        if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
            if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0 && currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                if (currentSalaryIncreaseDate.compareTo(payRateTableChangeDate) > 0) {
                    result = getLastCurrentSalaryIncrease(employeeId, givenDate);
                    return result;
                } else
                    return calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract, chosenLocalDate)[0];
            } else if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract, chosenLocalDate)[0];
                return result;
            } else {
                if (payRateTableChangeDate.compareTo(currentManualSalaryDate) > 0) {
                    result = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(contract, chosenLocalDate)[0];
                    return result;
                }
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
        if (temporaryDate != null) {
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
        Date lastSalaryIncreaseDateNotBonusPayment = null;
        Date temporaryDate = null;
        boolean isBonusPayment = false;
        for (int i = 0; i < salaryIncreaseHistoryManager.getRowCount(id); i++) {
            if (currentDate.compareTo(salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date()) >= 0) {
                if (temporaryDate == null) {
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                    isBonusPayment = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getIs_additional_payment();
                } else if (salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date().compareTo(temporaryDate) > 0) {
                    if (isBonusPayment) {
                        isBonusPayment = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getIs_additional_payment();
                    } else {
                        lastSalaryIncreaseDateNotBonusPayment = temporaryDate;
                    }
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();

                }
            }
        }
        if (temporaryDate != null) {
            if (isBonusPayment) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(temporaryDate);
                cal2.setTime(currentDate);
                if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                    lastSalaryIncreaseDate = temporaryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                } else if (lastSalaryIncreaseDateNotBonusPayment != null) {
                    lastSalaryIncreaseDate = lastSalaryIncreaseDateNotBonusPayment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                } else return null;
            } else lastSalaryIncreaseDate = temporaryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
        if (ProjectedSalaryModel.getLastPayLevelIncreaseDate(workingStartDate, currentDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() == null) {
            return null;
        } else {
            return ProjectedSalaryModel.getLastPayLevelIncreaseDate(workingStartDate, currentDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

    }
}
