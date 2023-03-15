package excelchaos_model.datamodel.payratetablesdataoperations;

import excelchaos_model.database.SalaryTableManager;

public class PayRateTablesDataDeleter {
    private SalaryTableManager salaryTableManager = new SalaryTableManager();

    /**
     * Deletes the table with the given Name.
     * @param revertedTableName The name of the table that is going to be deleted.
     */
    public void deletePayRateTable(String revertedTableName){
        salaryTableManager.removeSalaryTable(revertedTableName);
    }
}
