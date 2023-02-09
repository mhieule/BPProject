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

   private List<Date>[] payLevelIncreasesForEmployees;

   private int numberOfEmployees;

   public AutomaticPayLevelIncrease(){
       numberOfEmployees = employeeDataManager.getAllEmployees().size();
       employeeList = employeeDataManager.getAllEmployees();
       payLevelIncreasesForEmployees = new List[numberOfEmployees];
   }

   public void performPayLevelIncrease() throws ParseException {
       Date currentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
       DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
       for (int i = 0; i < numberOfEmployees; i++) {
           payLevelIncreasesForEmployees[i] = ProjectedSalaryModel.calculatePayLevelIncrease(dateFormat.parse(contractDataManager.getContract(employeeList.get(i).getId()).getStart_date()),contractDataManager.getContract(employeeList.get(i).getId()).getPaylevel());
            if(currentDate.compareTo(payLevelIncreasesForEmployees[i].get(0)) >= 0){
                setNewPayLevel(contractDataManager.getContract(i).getPaylevel(),i);
            }
       }

   }


   private void setNewPayLevel(String payLevel, int id){
       Contract contract = contractDataManager.getContract(id);
       switch (payLevel){
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
