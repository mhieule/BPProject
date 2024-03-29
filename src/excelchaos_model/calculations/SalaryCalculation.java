package excelchaos_model.calculations;

import excelchaos_model.database.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SalaryCalculation {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private ContractDataManager contractDataManager = new ContractDataManager();

    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();

    private ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();

    public SalaryCalculation() {

    }

    /**
     * Updates the current salaries of all employees based on their current salary information,
     * contract start date, and any manual salary entries or pay level increases that have been
     * recorded. If none of these exist for a given employee, their regular cost in the contract
     * is set to 0.
     * The method retrieves all employees from the employeeDataManager, then iterates through
     * each employee to determine their updated salary.
     */
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

    /**
     * Calculates the projected salary for an employee based on the given month and year.
     * The method takes an employeeId and a Date object representing the month and year for which
     * the salary is to be projected. It then retrieves the relevant salary and contract data
     * from the database and applies various calculations based on the employee's current
     * pay level, manual salary adjustments, and other factors to arrive at a projected salary
     * for the given month and year.
     *
     * @param employeeId The ID of the employee for whom the projected salary is to be calculated
     * @param givenDate  The month and year for which the projected salary is to be calculated
     * @return A BigDecimal object representing the projected salary for the given employee and date
     */
    public BigDecimal projectSalaryToGivenMonth(int employeeId, Date givenDate) {
        BigDecimal projectedSalary = new BigDecimal(0);
        SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();
        Contract contract = contractDataManager.getContract(employeeId);
        LocalDate currentManualSalaryDate = getLastCurrentManualInsertedSalaryDate(employeeId, givenDate);
        LocalDate currentSalaryIncreaseDate = getLastCurrentSalaryIncreaseDate(employeeId, givenDate);
        LocalDate lastPayLevelIncreaseDate = getLastCurrentPayLevelIncreaseDate(contract.getStart_date(), givenDate);
        if (employeeDataManager.getEmployee(employeeId).getStatus().equals("SHK")) {

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

    /**
     * Returns the last manually inserted salary date before the given date for the employee with the given ID.
     *
     * @param id        the ID of the employee
     * @param givenDate the given date to find the last manually inserted salary date before it
     * @return the last manually inserted salary date before the given date as a LocalDate object, or null if no salary date was found
     */
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

    /**
     * Returns the last manually inserted salary for the given employee ID and given date.
     * If no manually inserted salary exists for the given employee ID and date, returns 0.
     *
     * @param id        the ID of the employee
     * @param givenDate the date for which the last manually inserted salary is required
     * @return the last manually inserted salary for the given employee ID and given date, or 0 if none exists
     */
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

    /**
     * Returns the date of the last salary increase for the given employee ID and given date.
     * If no salary increase exists for the given employee ID and date, returns null.
     *
     * @param id        the ID of the employee
     * @param givenDate the date for which the last salary increase date is required
     * @return the date of the last salary increase for the given employee ID and given date, or null if none exists
     */
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

    /**
     * Returns the last salary increase for the employee with the given ID up to the given date,
     * including any additional payments flagged as bonuses.
     * If no salary increase was found, returns 0.
     *
     * @param id        The ID of the employee to retrieve the salary increase for.
     * @param givenDate The date up to which to retrieve the salary increase.
     * @return The last salary increase for the employee up to the given date, including any additional payments
     * flagged as bonuses, or 0 if no salary increase was found.
     */
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

    /**
     * Returns the date of the last pay level increase between the given working start date and current date.
     * If no pay level increase occurred between the given dates, returns null.
     *
     * @param workingStartDate the start date of working
     * @param currentDate      the current date
     * @return the date of the last pay level increase between the given dates, or null if no pay level increase occurred
     */
    private LocalDate getLastCurrentPayLevelIncreaseDate(Date workingStartDate, Date currentDate) {
        if (ProjectedSalaryModel.getLastPayLevelIncreaseDate(workingStartDate, currentDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() == null) {
            return null;
        } else {
            return ProjectedSalaryModel.getLastPayLevelIncreaseDate(workingStartDate, currentDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

    }
}
