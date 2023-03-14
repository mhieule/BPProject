package excelchaos_model.calculations;

import excelchaos_model.ProjectedSalaryModel;
import excelchaos_model.database.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewAndImprovedSalaryCalculation {

    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();

    private ContractDataManager contractDataManager = ContractDataManager.getInstance();

    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = SalaryIncreaseHistoryManager.getInstance();

    private ManualSalaryEntryManager manualSalaryEntryManager = ManualSalaryEntryManager.getInstance();

    public NewAndImprovedSalaryCalculation() {

    }

    public void updateCurrentSalaries() {
        BigDecimal updatedSalary = new BigDecimal(0);
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Employee> employees = employeeDataManager.getAllEmployees();
        SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();
        for (Employee employee : employees) {
            Contract contract = contractDataManager.getContract(employee.getId());
            LocalDate currentManualSalaryDate = getLastCurrentManualInsertedSalaryDate(employee.getId(), currentDate);
            LocalDate currentSalaryIncreaseDate = getLastCurrentSalaryIncreaseDate(employee.getId(), currentDate);
            LocalDate lastPayLevelIncreaseDate = getLastCurrentPayLevelIncreaseDate(contract.getStart_date(), currentDate);

            if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
                contract.setRegular_cost(updatedSalary);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
                updatedSalary = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                contract.setRegular_cost(updatedSalary);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
                updatedSalary = salaryTableLookUp.getCurrentPayRateTableEntry(contract)[0];
                contract.setRegular_cost(updatedSalary);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate == null) {
                if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                    updatedSalary = getLastCurrentSalaryIncrease(employee.getId(), currentDate);
                } else {
                    updatedSalary = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                }
                contract.setRegular_cost(updatedSalary);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
                if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                    updatedSalary = salaryTableLookUp.getCurrentPayRateTableEntry(contract)[0];
                } else {
                    updatedSalary = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                }
                contract.setRegular_cost(updatedSalary);
                contractDataManager.updateContract(contract);
                continue;
            }
            if (currentManualSalaryDate == null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
                if (currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                    updatedSalary = getLastCurrentSalaryIncrease(employee.getId(), currentDate);
                } else {
                    updatedSalary = salaryTableLookUp.getCurrentPayRateTableEntry(contract)[0];
                }
                contract.setRegular_cost(updatedSalary);
                contractDataManager.updateContract(contract);
                continue;
            }

            if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
                if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0 && currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                    updatedSalary = getLastCurrentSalaryIncrease(employee.getId(), currentDate);
                    contract.setRegular_cost(updatedSalary);
                    contractDataManager.updateContract(contract);
                } else if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                    updatedSalary = salaryTableLookUp.getCurrentPayRateTableEntry(contract)[0];
                    contract.setRegular_cost(updatedSalary);
                    contractDataManager.updateContract(contract);
                } else {
                    updatedSalary = getLastCurrentManualInsertedSalary(employee.getId(), currentDate);
                    contract.setRegular_cost(updatedSalary);
                    contractDataManager.updateContract(contract);
                }
            }


        }
    }


    public BigDecimal projectSalaryToGivenMonth(int employeeId, Date givenDate) {
        BigDecimal projectedSalary = new BigDecimal(0);
        SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();
        Contract contract = contractDataManager.getContract(employeeId);
        LocalDate currentManualSalaryDate = getLastCurrentManualInsertedSalaryDate(employeeId, givenDate);
        LocalDate currentSalaryIncreaseDate = getLastCurrentSalaryIncreaseDate(employeeId, givenDate);
        LocalDate lastPayLevelIncreaseDate = getLastCurrentPayLevelIncreaseDate(contract.getStart_date(), givenDate);
        if(employeeDataManager.getEmployee(employeeId).getStatus().equals("SHK")){

        } else {
            AutomaticPayLevelIncrease payLevelIncrease = new AutomaticPayLevelIncrease();
            contract = payLevelIncrease.performPayLevelIncreaseBasedOnGivenDate(givenDate, contract);
        }
        LocalDate chosenLocalDate = givenDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate payRateTableChangeDate = salaryTableLookUp.getActivePayRateTableBasedOnGivenDate(contract, chosenLocalDate);

        if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
            return projectedSalary;
        }
        if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate == null) {
            projectedSalary = getLastCurrentManualInsertedSalary(employeeId, givenDate);
            return projectedSalary;
        }
        if (currentManualSalaryDate == null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
            projectedSalary = salaryTableLookUp.getPayRateTableEntryForChosenDate(contract, chosenLocalDate)[0];
            return projectedSalary;
        }
        if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate == null) {
            if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                projectedSalary = getLastCurrentSalaryIncrease(employeeId, givenDate);
            } else {
                projectedSalary = getLastCurrentManualInsertedSalary(employeeId, givenDate);
            }
            return projectedSalary;
        }
        if (currentManualSalaryDate != null && currentSalaryIncreaseDate == null && lastPayLevelIncreaseDate != null) {
            if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                projectedSalary = salaryTableLookUp.getPayRateTableEntryForChosenDate(contract, chosenLocalDate)[0];
            } else {
                if (payRateTableChangeDate.compareTo(currentManualSalaryDate) > 0) {
                    projectedSalary = salaryTableLookUp.getPayRateTableEntryForChosenDate(contract, chosenLocalDate)[0];
                } else {
                    projectedSalary = getLastCurrentManualInsertedSalary(employeeId, givenDate);
                }
            }
            return projectedSalary;
        }
        if (currentManualSalaryDate == null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
            if (currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                if (currentSalaryIncreaseDate.compareTo(payRateTableChangeDate) > 0) {
                    projectedSalary = getLastCurrentSalaryIncrease(employeeId, givenDate);
                } else
                    projectedSalary = salaryTableLookUp.getPayRateTableEntryForChosenDate(contract, chosenLocalDate)[0];
            } else {
                projectedSalary = salaryTableLookUp.getPayRateTableEntryForChosenDate(contract, chosenLocalDate)[0];
            }
            return projectedSalary;
        }
        if (currentManualSalaryDate != null && currentSalaryIncreaseDate != null && lastPayLevelIncreaseDate != null) {
            if (currentSalaryIncreaseDate.compareTo(currentManualSalaryDate) > 0 && currentSalaryIncreaseDate.compareTo(lastPayLevelIncreaseDate) > 0) {
                if (currentSalaryIncreaseDate.compareTo(payRateTableChangeDate) > 0) {
                    projectedSalary = getLastCurrentSalaryIncrease(employeeId, givenDate);
                    return projectedSalary;
                } else
                    return salaryTableLookUp.getPayRateTableEntryForChosenDate(contract, chosenLocalDate)[0];
            } else if (lastPayLevelIncreaseDate.compareTo(currentManualSalaryDate) > 0) {
                projectedSalary = salaryTableLookUp.getPayRateTableEntryForChosenDate(contract, chosenLocalDate)[0];
                return projectedSalary;
            } else {
                if (payRateTableChangeDate.compareTo(currentManualSalaryDate) > 0) {
                    projectedSalary = salaryTableLookUp.getPayRateTableEntryForChosenDate(contract, chosenLocalDate)[0];
                    return projectedSalary;
                }
                projectedSalary = getLastCurrentManualInsertedSalary(employeeId, givenDate);
                return projectedSalary;
            }
        }
        return projectedSalary;

    }


    private LocalDate getLastCurrentManualInsertedSalaryDate(int id, Date givenDate) {
        LocalDate lastManualInsertedSalaryDate;
        Date temporaryDate = null;
        for (int i = 0; i < manualSalaryEntryManager.getRowCount(id); i++) {
            if (givenDate.compareTo(manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date()) >= 0) {
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

    private BigDecimal getLastCurrentManualInsertedSalary(int id, Date givenDate) {
        BigDecimal lastManualInsertedSalary = new BigDecimal(0);
        Date temporaryDate = null;
        for (int i = 0; i < manualSalaryEntryManager.getRowCount(id); i++) {
            if (givenDate.compareTo(manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date()) >= 0) {
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

    //TODO Unbedingt testen
    private LocalDate getLastCurrentSalaryIncreaseDate(int id, Date givenDate) {
        LocalDate lastSalaryIncreaseDate;
        Date lastSalaryIncreaseDateNotBonusPayment = null;
        Date temporaryDate = null;
        boolean isBonusPayment = false;
        for (int i = 0; i < salaryIncreaseHistoryManager.getRowCount(id); i++) {
            if (givenDate.compareTo(salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date()) >= 0) {
                if (temporaryDate == null) {
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                    isBonusPayment = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getIs_additional_payment();
                } else if (salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date().compareTo(temporaryDate) > 0) {
                    if (isBonusPayment) {

                    } else {
                        lastSalaryIncreaseDateNotBonusPayment = temporaryDate;
                    }
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                    isBonusPayment = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getIs_additional_payment();

                }
            }
        }
        if (temporaryDate != null) {
            if (isBonusPayment) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(temporaryDate);
                cal2.setTime(givenDate);
                if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                    lastSalaryIncreaseDate = temporaryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                } else if (lastSalaryIncreaseDateNotBonusPayment != null) {
                    lastSalaryIncreaseDate = lastSalaryIncreaseDateNotBonusPayment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                } else return null;
            } else lastSalaryIncreaseDate = temporaryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else lastSalaryIncreaseDate = null;

        return lastSalaryIncreaseDate;

    }

    //TODO Ganz wichtig zu testen
    private BigDecimal getLastCurrentSalaryIncrease(int id, Date givenDate) {
        BigDecimal lastSalaryIncrease = new BigDecimal(0);
        BigDecimal lastSalaryIncreaseNoBonus = new BigDecimal(0);
        Date temporaryDate = null;
        Date lastSalaryIncreaseDateNotBonusPayment = null;
        boolean isBonusPayment = false;
        for (int i = 0; i < salaryIncreaseHistoryManager.getRowCount(id); i++) {
            if (givenDate.compareTo(salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date()) >= 0) {
                if (temporaryDate == null) {
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                    lastSalaryIncrease = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getNew_salary();
                    isBonusPayment = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getIs_additional_payment();
                } else if (salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date().compareTo(temporaryDate) > 0) {
                    if (isBonusPayment) {

                    } else {
                        lastSalaryIncreaseDateNotBonusPayment = temporaryDate;
                        lastSalaryIncreaseNoBonus = lastSalaryIncrease;
                    }
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                    isBonusPayment = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getIs_additional_payment();
                    lastSalaryIncrease = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getNew_salary();
                }
            }
        }
        if (isBonusPayment) {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(temporaryDate);
            cal2.setTime(givenDate);
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                return lastSalaryIncrease;
            } else if (lastSalaryIncreaseDateNotBonusPayment != null) {
                return lastSalaryIncreaseNoBonus;
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
