package excelchaos_model.calculations;

import excelchaos_model.*;
import excelchaos_model.database.Contract;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.Employee;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalaryProjection {
    private SalaryCalculation salaryCalculation = new SalaryCalculation();
    private StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
    private CalculateSalaryBasedOnPayRateTable calculateSalaryBasedOnPayRateTable = new CalculateSalaryBasedOnPayRateTable();
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private ContractDataManager contractDataManager = new ContractDataManager();


    public String[][] getNextPayLevelProjection() {
        int lines = contractDataManager.getRowCount();
        String[][] resultData = new String[lines][];
        int currentIndex = 0;
        List<Contract> contracts = contractDataManager.getAllContracts();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Contract contract : contracts) {
            Employee employee = employeeDataManager.getEmployee(contract.getId());
            String id = String.valueOf(employee.getId());
            String name = employee.getName();
            String surname = employee.getSurname();
            String group = contract.getPaygrade();
            String stufe = contract.getPaylevel();
            String gehalt = transformer.formatDoubleToString(contract.getRegular_cost(), 1);
            String sonderzahlungen = transformer.formatDoubleToString(contract.getBonus_cost(), 1);

            List<Date> payLevelIncreases = new ArrayList<>();
            payLevelIncreases = ProjectedSalaryModel.calculatePayLevelIncrease(contract.getStart_date(), stufe);
            LocalDate firstIncreaseLocaledate = payLevelIncreases.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String firstIncreaseDate = dateFormat.format(payLevelIncreases.get(0));
            String firstIncreaseLevel = ProjectedSalaryModel.getNextPayLevel(stufe);
            Contract firstIncreaseContract = new Contract(contract.getId(), group, firstIncreaseLevel, contract.getStart_date(), contract.getEnd_date(), 0, 0, contract.getScope(), "", contract.getVbl_status());
            double[] firstIncreaseCostArray = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(firstIncreaseContract, firstIncreaseLocaledate);
            String firstIncreaseGehalt = transformer.formatDoubleToString(firstIncreaseCostArray[0], 1);
            String firstIncreaseSonderzahlung = transformer.formatDoubleToString(firstIncreaseCostArray[1] * 12, 1);

            payLevelIncreases = ProjectedSalaryModel.calculatePayLevelIncrease(contract.getStart_date(), firstIncreaseLevel);
            LocalDate secondIncreaseLocaleDate = payLevelIncreases.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String secondIncreaseDate = dateFormat.format(payLevelIncreases.get(0));
            String secondIncreaseLevel = ProjectedSalaryModel.getNextPayLevel(firstIncreaseLevel);
            Contract secondIncreaseContract = new Contract(contract.getId(), group, secondIncreaseLevel, contract.getStart_date(), contract.getEnd_date(), 0, 0, contract.getScope(), "", contract.getVbl_status());
            double[] secondIncreaseCostArray = calculateSalaryBasedOnPayRateTable.getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(secondIncreaseContract, secondIncreaseLocaleDate);
            String secondIncreaseGehalt = transformer.formatDoubleToString(secondIncreaseCostArray[0], 1);
            String secondIncreaseSonderzahlung = transformer.formatDoubleToString(secondIncreaseCostArray[1] * 12, 1);
            String[] values = {id, name, surname, group, stufe, gehalt, sonderzahlungen, firstIncreaseDate, group, firstIncreaseLevel, firstIncreaseGehalt, firstIncreaseSonderzahlung,
                    secondIncreaseDate, group, secondIncreaseLevel, secondIncreaseGehalt, secondIncreaseSonderzahlung};

            resultData[currentIndex] = values;
            currentIndex++;

        }
        return resultData;
    }

    public String[][] getSalaryProjectionForGivenDate(Date givenDate) {
        int lines = contractDataManager.getRowCount();
        String[][] resultData = new String[lines][];
        int currentIndex = 0;
        List<Contract> contracts = contractDataManager.getAllContracts();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Contract contract : contracts) {
            Employee employee = employeeDataManager.getEmployee(contract.getId());
            String id = String.valueOf(employee.getId());
            String name = employee.getName();
            String surname = employee.getSurname();
            String group = contract.getPaygrade();
            String stufe = contract.getPaylevel();
            String gehalt = transformer.formatDoubleToString(contract.getRegular_cost(), 1);
            String sonderzahlungen = transformer.formatDoubleToString(contract.getBonus_cost(), 1);

            String givenDateAsString = dateFormat.format(givenDate);
            double salaryOfGivenMonth = salaryCalculation.determineSalaryOfGivenMonth(contract.getId(), givenDate);
            String salary = transformer.formatDoubleToString(salaryOfGivenMonth, 1);
            String nextSalaryLevel = ProjectedSalaryModel.calculatePayLevelBasedOnDate(contract.getStart_date(), contract.getPaylevel(), givenDate);

            String[] values = {id, name, surname, group, stufe, gehalt, sonderzahlungen, givenDateAsString, nextSalaryLevel, salary, sonderzahlungen};

            resultData[currentIndex] = values;
            currentIndex++;

        }
        return resultData;
    }
}
