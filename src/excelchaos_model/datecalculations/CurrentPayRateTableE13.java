package excelchaos_model.datecalculations;

import excelchaos_model.database.SalaryTable;
import excelchaos_model.database.SalaryTableManager;
import excelchaos_model.utility.PayRateTableNameDateSeperator;
import excelchaos_model.utility.TableNameDateTuple;

import java.time.LocalDate;
import java.util.List;

public class CurrentPayRateTableE13 {
    private int numberOfTables;

    private final String paygrade = "E13";

    private SalaryTableManager salaryTableManager = new SalaryTableManager();

    private List<String> tableNames;

    private TableNameDateTuple[] tableNameDateTuple;

    private TableNameDateTuple[] payRateTablesWith1AAnd1B, payRateTablesWithout1AAnd1B;

    private int numberOfTablesWith1AAnd1B = 0;

    private int numberOfTablesWithout1AAnd1B = 0;

    public CurrentPayRateTableE13() {
        numberOfTables = salaryTableManager.getNumOfTables(paygrade);
        tableNames = salaryTableManager.getDistinctTableNames(paygrade);
        tableNameDateTuple = new TableNameDateTuple[numberOfTables];
        initTuples();
        seperateTableCategories();
    }

    private void seperateTableCategories() {
        int firstIndex = 0;
        int secondIndex = 0;
        for (int index = 0; index < tableNameDateTuple.length; index++) {
            if (tableNameDateTuple[index].tableName.contains("1A") || tableNameDateTuple[index].tableName.contains("1B")) {
                payRateTablesWith1AAnd1B[firstIndex] = tableNameDateTuple[index];
                firstIndex++;
            } else {
                payRateTablesWithout1AAnd1B[secondIndex] = tableNameDateTuple[index];
                secondIndex++;
            }
        }

    }

    private void initTuples() {
        PayRateTableNameDateSeperator seperator = new PayRateTableNameDateSeperator();
        for (int i = 0; i < numberOfTables; i++) {
            tableNameDateTuple[i] = new TableNameDateTuple(tableNames.get(i), seperator.seperateDateAsDate(tableNames.get(i)));
            if (tableNames.get(i).contains("1A") || tableNames.get(i).contains("1B")) {
                numberOfTablesWith1AAnd1B++;
            } else {
                numberOfTablesWithout1AAnd1B++;
            }
        }
        payRateTablesWith1AAnd1B = new TableNameDateTuple[numberOfTablesWith1AAnd1B];
        payRateTablesWithout1AAnd1B = new TableNameDateTuple[numberOfTablesWithout1AAnd1B];
    }

    public List<SalaryTable> getCurrentPayRateWith1AAnd1BTable() {
        return salaryTableManager.getSalaryTable(determinePayRateTableBasedOnCurrentDate(payRateTablesWith1AAnd1B).tableName);
    }

    public List<SalaryTable> getCurrentPayRateWithout1AAnd1BTable() {
        return salaryTableManager.getSalaryTable(determinePayRateTableBasedOnCurrentDate(payRateTablesWithout1AAnd1B).tableName);
    }

    public List<SalaryTable> getPayRateTableBasedOnChosenDateWith1AAnd1BTable(LocalDate date) {
        return salaryTableManager.getSalaryTable(determinePayRateTableBasedOnChosenDate(date, payRateTablesWith1AAnd1B).tableName);
    }

    public List<SalaryTable> getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(LocalDate date) {
        return salaryTableManager.getSalaryTable(determinePayRateTableBasedOnChosenDate(date, payRateTablesWithout1AAnd1B).tableName);
    }

    private TableNameDateTuple determinePayRateTableBasedOnCurrentDate(TableNameDateTuple[] givenTableNamesAndDates) {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastCheckedDate = null;
        int resultindex = 0;
        for (int i = 0; i < givenTableNamesAndDates.length; i++) {
            if (currentDate.compareTo(givenTableNamesAndDates[i].date) >= 0) {
                if (lastCheckedDate == null) {
                    lastCheckedDate = givenTableNamesAndDates[i].date;
                    resultindex = i;
                } else {
                    if (givenTableNamesAndDates[i].date.compareTo(lastCheckedDate) > 0) {
                        resultindex = i;
                    } else {
                        lastCheckedDate = givenTableNamesAndDates[i].date;
                    }
                }


            } else if (currentDate.compareTo(givenTableNamesAndDates[i].date) < 0) {
                continue;
            }

        }

        return givenTableNamesAndDates[resultindex];
    }

    private TableNameDateTuple determinePayRateTableBasedOnChosenDate(LocalDate chosenDate, TableNameDateTuple[] givenTableNamesAndDates) {
        LocalDate lastCheckedDate = null;
        int resultindex = 0;
        for (int i = 0; i < givenTableNamesAndDates.length; i++) {
            if (chosenDate.compareTo(givenTableNamesAndDates[i].date) >= 0) {
                if (lastCheckedDate == null) {
                    lastCheckedDate = givenTableNamesAndDates[i].date;
                    resultindex = i;
                } else {
                    if (givenTableNamesAndDates[i].date.compareTo(lastCheckedDate) > 0) {
                        resultindex = i;
                    } else {
                        lastCheckedDate = givenTableNamesAndDates[i].date;
                    }
                }
            } else if (chosenDate.compareTo(givenTableNamesAndDates[i].date) < 0) {
                continue;
            }
        }
        return givenTableNamesAndDates[resultindex];
    }


    public LocalDate getActivePayRateTableDateWithAAndB(LocalDate chosenDate) {
        return determinePayRateTableBasedOnChosenDate(chosenDate, payRateTablesWith1AAnd1B).date;
    }

    public LocalDate getActivePayRateTableDateWithoutAAndB(LocalDate chosenDate) {
        return determinePayRateTableBasedOnChosenDate(chosenDate, payRateTablesWithout1AAnd1B).date;
    }


}
