package excelchaos_model.calculations;

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
    private SalaryCalculation newAndImprovedSalaryCalculation = new SalaryCalculation();
    private SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private ContractDataManager contractDataManager = new ContractDataManager();


    /**
     * Calculates the projected salary of each employee's contract for the next pay level increase date and returns a two-dimensional array of strings representing the result.
     * The method retrieves all contracts and employee data from the contractDataManager and employeeDataManager, respectively.
     * It iterates through each contract and calculates the next pay level increase date, level, and associated salary and bonus payments.
     * The resulting two-dimensional String array represents the projected salary data for all contracts.
     *
     * @return a two-dimensional array of strings representing the projected salary data for each employee's contract.
     */
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
            String paylevel = contract.getPaylevel();
            String salary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getRegular_cost());
            String bonusPayment = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getBonus_cost());
            String firstIncreaseDate;
            String firstIncreaseLevel;
            String firstIncreaseSalary;
            String firstIncreaseBonusPayment;
            String secondIncreaseDate;
            String secondIncreaseLevel;
            String secondIncreaseSalary;
            String secondIncreaseBonusPayment;
            if (employee.getStatus().equals("SHK")) {
                firstIncreaseDate = "";
                firstIncreaseLevel = "";
                firstIncreaseSalary = "";
                firstIncreaseBonusPayment = "";
                secondIncreaseDate = "";
                secondIncreaseLevel = "";
                secondIncreaseSalary = "";
                secondIncreaseBonusPayment = "";
            } else {
                List<Date> payLevelIncreases = new ArrayList<>();
                payLevelIncreases = ProjectedSalaryModel.calculatePayLevelIncrease(contract.getStart_date(), paylevel);
                LocalDate firstIncreaseLocaledate = payLevelIncreases.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                firstIncreaseDate = dateFormat.format(payLevelIncreases.get(0));
                firstIncreaseLevel = ProjectedSalaryModel.getNextPayLevel(paylevel);
                Contract firstIncreaseContract = new Contract(contract.getId(), group, firstIncreaseLevel, contract.getStart_date(), contract.getEnd_date(), new BigDecimal(0), new BigDecimal(0), contract.getScope(), contract.getShk_hourly_rate(), contract.getVbl_status());
                BigDecimal[] firstIncreaseCostArray = salaryTableLookUp.getPayRateTableEntryForChosenDate(firstIncreaseContract, firstIncreaseLocaledate);
                firstIncreaseSalary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(firstIncreaseCostArray[0]);
                firstIncreaseBonusPayment = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(firstIncreaseCostArray[1].multiply(new BigDecimal(12)));

                payLevelIncreases = ProjectedSalaryModel.calculatePayLevelIncrease(contract.getStart_date(), firstIncreaseLevel);
                LocalDate secondIncreaseLocaleDate = payLevelIncreases.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                secondIncreaseDate = dateFormat.format(payLevelIncreases.get(0));
                secondIncreaseLevel = ProjectedSalaryModel.getNextPayLevel(firstIncreaseLevel);
                Contract secondIncreaseContract = new Contract(contract.getId(), group, secondIncreaseLevel, contract.getStart_date(), contract.getEnd_date(), new BigDecimal(0), new BigDecimal(0), contract.getScope(), contract.getShk_hourly_rate(), contract.getVbl_status());
                BigDecimal[] secondIncreaseCostArray = salaryTableLookUp.getPayRateTableEntryForChosenDate(secondIncreaseContract, secondIncreaseLocaleDate);
                secondIncreaseSalary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(secondIncreaseCostArray[0]);
                secondIncreaseBonusPayment = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(secondIncreaseCostArray[1].multiply(new BigDecimal(12)));
            }


            String[] values = {id, surname, name, group, paylevel, salary, bonusPayment, firstIncreaseDate, group, firstIncreaseLevel, firstIncreaseSalary, firstIncreaseBonusPayment,
                    secondIncreaseDate, group, secondIncreaseLevel, secondIncreaseSalary, secondIncreaseBonusPayment};

            resultData[currentIndex] = values;
            currentIndex++;

        }
        return resultData;
    }

    /**
     * Returns a 2-dimensional array of Strings representing the salary projections for each employee's contract based on the given date.
     * The method iterates over all contracts, retrieves the relevant information about each employee and their contract,
     * calculates the salary projection for the given month based on the contract and given date, and returns the results in a 2D array.
     * Each row in the array represents an employee's information, including their ID, name, surname, paygrade, paylevel, regular cost,
     * bonus cost, the given date, the next salary level based on the given date, the projected salary for the given date,
     * and the bonus cost for the contract.
     * The date parameter should be a valid Date object representing the month for which salary projections are requested.
     *
     * @param givenDate the Date object representing the month for which salary projections are requested
     * @return a 2-dimensional array of Strings representing the salary projections for each employee's contract based on the given date
     */
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
            String paylevel = contract.getPaylevel();
            String oldSalary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getRegular_cost());
            String bonusPayment = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getBonus_cost());

            String givenDateAsString = dateFormat.format(givenDate);
            BigDecimal salaryOfGivenMonth = newAndImprovedSalaryCalculation.projectSalaryToGivenMonth(contract.getId(), givenDate);
            String salary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(salaryOfGivenMonth);
            String nextSalaryLevel = ProjectedSalaryModel.calculatePayLevelBasedOnDate(contract.getStart_date(), contract.getPaylevel(), givenDate);

            String[] values = {id, surname, name, group, paylevel, oldSalary, bonusPayment, givenDateAsString, nextSalaryLevel, salary, bonusPayment};

            resultData[currentIndex] = values;
            currentIndex++;

        }
        return resultData;
    }
}
