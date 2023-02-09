package excelchaos_model.calculations;

import excelchaos_model.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    private LocalDate getLastCurrentPayLevelIncreaseDate(Date workingStartDate,Date currentDate){
        return ProjectedSalaryModel.getLastPayLevelIncreaseDate(workingStartDate,currentDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
