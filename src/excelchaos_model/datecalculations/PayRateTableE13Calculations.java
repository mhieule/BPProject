package excelchaos_model.datecalculations;

import excelchaos_model.database.SalaryTable;
import excelchaos_model.database.SalaryTableManager;
import excelchaos_model.utility.PayRateTableNameDateSeperator;
import excelchaos_model.utility.TableNameDateTuple;

import java.time.LocalDate;
import java.util.List;

public class PayRateTableE13Calculations {
    private int numberOfTables;

    private final String paygrade = "E13";

    private SalaryTableManager salaryTableManager = new SalaryTableManager();

    private List<String> tableNames;

    private TableNameDateTuple[] tableNameDateTuple;

    private TableNameDateTuple[] payRateTablesWith1AAnd1B, payRateTablesWithout1AAnd1B;

    private int numberOfTablesWith1AAnd1B = 0;

    private int numberOfTablesWithout1AAnd1B = 0;

    public PayRateTableE13Calculations() {
        numberOfTables = salaryTableManager.getNumOfTables(paygrade);
        tableNames = salaryTableManager.getDistinctTableNames(paygrade);
        tableNameDateTuple = new TableNameDateTuple[numberOfTables];
        initTuples();
        seperateTableCategories();
    }

    /**
     * This method separates the pay rate tables into two categories: those that contain "1A" or "1B" in their name,
     * and those that do not. The pay rate tables that contain "1A" or "1B" are stored in the
     * payRateTablesWith1AAnd1B array, while the others are stored in the payRateTablesWithout1AAnd1B array.
     */
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


    /**
     * Initializes TableNameDateTuple objects for all tables in tableNames list and separates them into two arrays based on
     * whether the table name contains "1A" or "1B".
     */
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

    /**
     * Returns the current pay rate table with "1A" and "1B" category.
     * This method uses the "determinePayRateTableBasedOnCurrentDate()" method to determine the current pay rate table
     * based on the current date, then retrieves the salary table from the salaryTableManager with the corresponding
     * table name. The method returns a list of SalaryTable object(s) with the current pay rates of employees
     * who are categorized under "1A" and "1B".
     *
     * @return a List of SalaryTable object(s) containing current pay rates of employees under "1A" and "1B"
     */
    public List<SalaryTable> getCurrentPayRateWith1AAnd1BTable() {
        if (determinePayRateTableBasedOnCurrentDate(payRateTablesWith1AAnd1B) == null) {
            return null;
        } else
            return salaryTableManager.getSalaryTable(determinePayRateTableBasedOnCurrentDate(payRateTablesWith1AAnd1B).tableName);
    }

    /**
     * Returns the current pay rate table without "1A" and "1B" category.
     * This method uses the "determinePayRateTableBasedOnCurrentDate()" method to determine the current pay rate table
     * based on the current date, then retrieves the salary table from the salaryTableManager with the corresponding
     * table name. The method returns a list of SalaryTable object(s) with the current pay rates of employees
     * who are categorized under "1A" and "1B".
     *
     * @return a List of SalaryTable object(s) containing current pay rates of employees under "1A" and "1B"
     */
    public List<SalaryTable> getCurrentPayRateWithout1AAnd1BTable() {
        if (determinePayRateTableBasedOnCurrentDate(payRateTablesWithout1AAnd1B) == null) {
            return null;
        } else
            return salaryTableManager.getSalaryTable(determinePayRateTableBasedOnCurrentDate(payRateTablesWithout1AAnd1B).tableName);
    }

    /**
     * Returns the salary table that corresponds to the chosen date, from the pay rate tables that contain "1A" or "1B" in their names.
     *
     * @param date the chosen date to search for the pay rate table.
     * @return a list of SalaryTable objects that corresponds to the chosen date and matches the condition.
     */
    public List<SalaryTable> getPayRateTableBasedOnChosenDateWith1AAnd1BTable(LocalDate date) {
        if (determinePayRateTableBasedOnChosenDate(date, payRateTablesWith1AAnd1B) == null) {
            return null;
        } else
            return salaryTableManager.getSalaryTable(determinePayRateTableBasedOnChosenDate(date, payRateTablesWith1AAnd1B).tableName);
    }

    /**
     * Returns the salary table that corresponds to the chosen date, from the pay rate tables that do not contain "1A" or "1B" in their names.
     *
     * @param date the chosen date to search for the pay rate table.
     * @return a list of SalaryTable objects that corresponds to the chosen date and matches the condition.
     */
    public List<SalaryTable> getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(LocalDate date) {
        if (determinePayRateTableBasedOnChosenDate(date, payRateTablesWithout1AAnd1B) == null) {
            return null;
        } else
            return salaryTableManager.getSalaryTable(determinePayRateTableBasedOnChosenDate(date, payRateTablesWithout1AAnd1B).tableName);
    }

    /**
     * Determines the pay rate table based on the current date from the given array of table names and dates.
     * The method loops through the given table names and dates and finds the latest date that is less than or equal to the current date.
     * The table with this latest date is then returned.
     *
     * @param givenTableNamesAndDates the array of table names and dates to search through
     * @return the TableNameDateTuple object with the latest date that is less than or equal to the current date
     */
    private TableNameDateTuple determinePayRateTableBasedOnCurrentDate(TableNameDateTuple[] givenTableNamesAndDates) {
        LocalDate currentDate = LocalDate.now();
        LocalDate currentlyValidDate = null;
        if (givenTableNamesAndDates.length == 0) {
            return null;
        }
        int resultIndex = 0;
        for (int i = 0; i < givenTableNamesAndDates.length; i++) {
            if (currentDate.compareTo(givenTableNamesAndDates[i].date) >= 0) {
                if (currentlyValidDate == null) {
                    currentlyValidDate = givenTableNamesAndDates[i].date;
                    resultIndex = i;
                } else {
                    if (givenTableNamesAndDates[i].date.compareTo(currentlyValidDate) > 0) {
                        resultIndex = i;
                        currentlyValidDate = givenTableNamesAndDates[i].date;
                    }
                }


            } else if (currentDate.compareTo(givenTableNamesAndDates[i].date) < 0) {
                continue;
            }
        }

        return givenTableNamesAndDates[resultIndex];
    }

    /**
     * Determines the most recent pay rate table based on the chosen date and an array of table names and their corresponding dates.
     * If the chosen date is on or after the date of a table in the array, that table is considered currently valid.
     * If multiple tables are currently valid, the method returns the table with the latest date.
     *
     * @param chosenDate              the chosen date to determine the pay rate table
     * @param givenTableNamesAndDates an array of table names and their corresponding dates to be searched
     * @return the most recent pay rate table based on the chosen date
     */
    private TableNameDateTuple determinePayRateTableBasedOnChosenDate(LocalDate chosenDate, TableNameDateTuple[] givenTableNamesAndDates) {
        LocalDate currentlyValidDate = null;
        if (givenTableNamesAndDates.length == 0) {
            return null;
        }
        int resultIndex = 0;
        for (int i = 0; i < givenTableNamesAndDates.length; i++) {
            if (chosenDate.compareTo(givenTableNamesAndDates[i].date) >= 0) {
                if (currentlyValidDate == null) {
                    currentlyValidDate = givenTableNamesAndDates[i].date;
                    resultIndex = i;
                } else {
                    if (givenTableNamesAndDates[i].date.compareTo(currentlyValidDate) > 0) {
                        resultIndex = i;
                        currentlyValidDate = givenTableNamesAndDates[i].date;
                    }
                }
            } else if (chosenDate.compareTo(givenTableNamesAndDates[i].date) < 0) {
                continue;
            }
        }
        return givenTableNamesAndDates[resultIndex];
    }


    /**
     * Returns the date of the active pay rate table with A and B categories based on the given date.
     *
     * @param chosenDate the date to determine the active pay rate table
     * @return the date of the active pay rate table
     */
    public LocalDate getActivePayRateTableDateWithAAndB(LocalDate chosenDate) {
        if (determinePayRateTableBasedOnChosenDate(chosenDate, payRateTablesWith1AAnd1B) == null) {
            return null;
        } else
            return determinePayRateTableBasedOnChosenDate(chosenDate, payRateTablesWith1AAnd1B).date;
    }

    /**
     * Returns the date of the active pay rate table without A and B categories based on the given date.
     *
     * @param chosenDate the date to determine the active pay rate table
     * @return the date of the active pay rate table
     */
    public LocalDate getActivePayRateTableDateWithoutAAndB(LocalDate chosenDate) {
        if (determinePayRateTableBasedOnChosenDate(chosenDate, payRateTablesWithout1AAnd1B) == null) {
            return null;
        } else
            return determinePayRateTableBasedOnChosenDate(chosenDate, payRateTablesWithout1AAnd1B).date;
    }


}
