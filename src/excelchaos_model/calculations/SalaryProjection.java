package excelchaos_model.calculations;

import excelchaos_model.*;
import excelchaos_model.database.Contract;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.Employee;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.utility.StringAndBigDecimalFormatter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalaryProjection {
    private NewAndImprovedSalaryCalculation newAndImprovedSalaryCalculation = new NewAndImprovedSalaryCalculation();
    private SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();
    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();

    private ContractDataManager contractDataManager = ContractDataManager.getInstance();


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
            String gehalt = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getRegular_cost());
            String sonderzahlungen = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getBonus_cost());

            List<Date> payLevelIncreases = new ArrayList<>();
            payLevelIncreases = ProjectedSalaryModel.calculatePayLevelIncrease(contract.getStart_date(), stufe);
            LocalDate firstIncreaseLocaledate = payLevelIncreases.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String firstIncreaseDate = dateFormat.format(payLevelIncreases.get(0));
            String firstIncreaseLevel = ProjectedSalaryModel.getNextPayLevel(stufe);
            Contract firstIncreaseContract = new Contract(contract.getId(), group, firstIncreaseLevel, contract.getStart_date(), contract.getEnd_date(), new BigDecimal(0), new BigDecimal(0), contract.getScope(), contract.getShk_hourly_rate(), contract.getVbl_status());
            BigDecimal[] firstIncreaseCostArray = salaryTableLookUp.getPayRateTableEntryForChosenDate(firstIncreaseContract, firstIncreaseLocaledate);
            String firstIncreaseGehalt = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(firstIncreaseCostArray[0]);
            String firstIncreaseSonderzahlung = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(firstIncreaseCostArray[1].multiply(new BigDecimal(12)));

            payLevelIncreases = ProjectedSalaryModel.calculatePayLevelIncrease(contract.getStart_date(), firstIncreaseLevel);
            LocalDate secondIncreaseLocaleDate = payLevelIncreases.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String secondIncreaseDate = dateFormat.format(payLevelIncreases.get(0));
            String secondIncreaseLevel = ProjectedSalaryModel.getNextPayLevel(firstIncreaseLevel);
            Contract secondIncreaseContract = new Contract(contract.getId(), group, secondIncreaseLevel, contract.getStart_date(), contract.getEnd_date(), new BigDecimal(0), new BigDecimal(0), contract.getScope(), contract.getShk_hourly_rate(), contract.getVbl_status());
            BigDecimal[] secondIncreaseCostArray = salaryTableLookUp.getPayRateTableEntryForChosenDate(secondIncreaseContract, secondIncreaseLocaleDate);
            String secondIncreaseGehalt = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(secondIncreaseCostArray[0]);
            String secondIncreaseSonderzahlung = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(secondIncreaseCostArray[1].multiply(new BigDecimal(12)));
            String[] values = {id, surname, name, group, stufe, gehalt, sonderzahlungen, firstIncreaseDate, group, firstIncreaseLevel, firstIncreaseGehalt, firstIncreaseSonderzahlung,
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
            String gehalt = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getRegular_cost());
            String sonderzahlungen = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getBonus_cost());

            String givenDateAsString = dateFormat.format(givenDate);
            BigDecimal salaryOfGivenMonth = newAndImprovedSalaryCalculation.projectSalaryToGivenMonth(contract.getId(),givenDate);
            String salary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(salaryOfGivenMonth);
            String nextSalaryLevel = ProjectedSalaryModel.calculatePayLevelBasedOnDate(contract.getStart_date(), contract.getPaylevel(), givenDate);

            String[] values = {id, surname, name, group, stufe, gehalt, sonderzahlungen, givenDateAsString, nextSalaryLevel, salary, sonderzahlungen};

            resultData[currentIndex] = values;
            currentIndex++;

        }
        return resultData;
    }
}
