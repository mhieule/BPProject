package excelchaos_model.datamodel.payratetablesdataoperations;

import excelchaos_model.database.SalaryTableManager;

public class PayRateTablesDataDeleter {
    private SalaryTableManager salaryTableManager = SalaryTableManager.getInstance();

    public void deletePayRateTable(String revertedTableName){
        salaryTableManager.removeSalaryTable(revertedTableName);
    }
}
