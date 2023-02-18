package excelchaos_model.calculations;

import excelchaos_model.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ContractSalary {
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private ContractDataManager contractDataManager = new ContractDataManager();

    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();

    private ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();





    private int numberOfEmployees;

    public ContractSalary(){
        numberOfEmployees = employeeDataManager.getAllEmployees().size();

    }

    public void determineCurrentSalary(){
        Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Employee> employees = employeeDataManager.getAllEmployees();
        for (Employee employee : employees){

        }

    }

    private LocalDate getLastCurrentManualInsertedSalaryDate(int id,Date currentDate){
        LocalDate lastManualInsertedSalaryDate;
        Date temporaryDate = null;
        for (int i = 0; i < manualSalaryEntryManager.getRowCount(id); i++) {
            if(currentDate.compareTo(manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date()) >=0){
                if(temporaryDate == null){
                    temporaryDate = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date();
                } else if(manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date().compareTo(temporaryDate) > 0){
                    temporaryDate = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date();
                }
            }
        }
        lastManualInsertedSalaryDate = temporaryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return lastManualInsertedSalaryDate;
    }

    private double getLastCurrentManualInsertedSalary(int id,Date currentDate){
        double lastManualInsertedSalary = 0;
        Date temporaryDate = null;
        for (int i = 0; i < manualSalaryEntryManager.getRowCount(id); i++) {
            if(currentDate.compareTo(manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date()) >=0){
                if(temporaryDate == null){
                    temporaryDate = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date();
                    lastManualInsertedSalary = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getNew_salary();
                } else if(manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date().compareTo(temporaryDate) > 0){
                    temporaryDate = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getStart_date();
                    lastManualInsertedSalary = manualSalaryEntryManager.getManualSalaryEntry(id).get(i).getNew_salary();
                }
            }
        }
        return lastManualInsertedSalary;
    }

    private LocalDate getLastCurrentSalaryIncreaseDate(int id,Date currentDate){
        LocalDate lastSalaryIncreaseDate;
        Date temporaryDate = null;
        for (int i = 0; i < salaryIncreaseHistoryManager.getRowCount(id); i++) {
            if(currentDate.compareTo(salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date()) >= 0){
                if(temporaryDate == null){
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                } else if(salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date().compareTo(temporaryDate) >0){
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                }
            }
        }
        lastSalaryIncreaseDate = temporaryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return lastSalaryIncreaseDate;

    }

    private double getLastCurrentSalaryIncrease(int id,Date currentDate){
        double lastSalaryIncrease = 0;
        Date temporaryDate = null;
        for (int i = 0; i < salaryIncreaseHistoryManager.getRowCount(id); i++) {
            if(currentDate.compareTo(salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date()) >= 0){
                if(temporaryDate == null){
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                    lastSalaryIncrease = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getNew_salary();
                } else if(salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date().compareTo(temporaryDate) >0){
                    temporaryDate = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getStart_date();
                    lastSalaryIncrease = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id).get(i).getNew_salary();
                }
            }
        }
        return lastSalaryIncrease;

    }

    private LocalDate getLastCurrentPayLevelIncreaseDate(Date workingStartDate,Date currentDate){
        return ProjectedSalaryModel.getLastPayLevelIncreaseDate(workingStartDate,currentDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
