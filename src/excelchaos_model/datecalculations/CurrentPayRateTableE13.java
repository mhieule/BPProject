package excelchaos_model.datecalculations;

import excelchaos_model.SalaryTable;
import excelchaos_model.SalaryTableManager;
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

    public CurrentPayRateTableE13() {
        numberOfTables = salaryTableManager.getNumOfTables(paygrade);
        tableNames = salaryTableManager.getDistinctTableNames(paygrade);
        tableNameDateTuple = new TableNameDateTuple[numberOfTables];
        initTuples();
    }

    private void initTuples() {
        PayRateTableNameDateSeperator seperator = new PayRateTableNameDateSeperator();
        for (int i = 0; i < numberOfTables; i++) {
            tableNameDateTuple[i] = new TableNameDateTuple(tableNames.get(i), seperator.seperateDateAsDate(tableNames.get(i)));
        }
    }

    public List<SalaryTable> getCurrentPayRateTable() {
        return salaryTableManager.getSalaryTable(determinePayRateTableBasedOnCurrentDate().tableName);
    }

    public List<SalaryTable> getPayRateTableBasedOnChosenDate(LocalDate date) {
        return salaryTableManager.getSalaryTable(determinePayRateTableBasedOnChosenDate(date).tableName);
    }

    private TableNameDateTuple determinePayRateTableBasedOnCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastCheckedDate = null;
        int resultindex = 0;
        List<SalaryTable> salaryTables;
        for (int i = 0; i < numberOfTables; i++) {
            if (currentDate.compareTo(tableNameDateTuple[i].date) >= 0) {
                if (lastCheckedDate == null) {
                    lastCheckedDate = tableNameDateTuple[i].date;
                    resultindex = i;
                } else {
                    if (tableNameDateTuple[i].date.compareTo(lastCheckedDate) > 0) {
                        resultindex = i;
                    } else {
                        lastCheckedDate = tableNameDateTuple[i].date;
                    }
                }


            } else if (currentDate.compareTo(tableNameDateTuple[i].date) < 0) {
                continue;
            }

        }
        return tableNameDateTuple[resultindex];
    }

    private TableNameDateTuple determinePayRateTableBasedOnChosenDate(LocalDate chosenDate) {
        LocalDate currentDate = chosenDate;
        LocalDate lastCheckedDate = null;
        int resultindex = 0;
        List<SalaryTable> salaryTables;
        for (int i = 0; i < numberOfTables; i++) {
            if (currentDate.compareTo(tableNameDateTuple[i].date) >= 0) {
                if (lastCheckedDate == null) {
                    lastCheckedDate = tableNameDateTuple[i].date;
                    resultindex = i;
                } else {
                    if (tableNameDateTuple[i].date.compareTo(lastCheckedDate) > 0) {
                        resultindex = i;
                    } else {
                        lastCheckedDate = tableNameDateTuple[i].date;
                    }
                }
            } else if (currentDate.compareTo(tableNameDateTuple[i].date) < 0) {
                continue;
            }

        }
        return tableNameDateTuple[resultindex];
    }


}
