package excelchaos_model.datamodel.payratetablesdataoperations;

import excelchaos_model.database.SalaryTable;
import excelchaos_model.database.SalaryTableManager;
import excelchaos_model.utility.PayRateTableNameDateSeperator;
import excelchaos_model.utility.PayRateTableNameStringEditor;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class PayRateTablesDataAccess {

    private SalaryTableManager salaryTableManager = new SalaryTableManager();

    /**
     * Returns a vector of pay rate tables names for the specified pay grade, sorted in descending order by date.
     *
     * @param payGrade the pay grade for which the pay rate tables names are requested.
     * @return a vector of pay rate tables names, sorted in descending order by date.
     * @throws NullPointerException     if the payGrade is null.
     * @throws IllegalArgumentException if the payGrade is an empty string.
     */

    public Vector<String> getPayRateTablesNameVectorForList(String payGrade) {
        PayRateTableNameDateSeperator payRateTableNameDateSeperator = new PayRateTableNameDateSeperator();

        int numberOfTables = salaryTableManager.getNumOfTables(payGrade);
        String[] tableNames = new String[numberOfTables];
        Vector<String> stringVector = new Vector<>();
        for (int i = 0; i < numberOfTables; i++) {

            tableNames[i] = salaryTableManager.getDistinctTableNames(payGrade).get(i);
            stringVector.add(tableNames[i]);

        }
        stringVector.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                LocalDate l1 = payRateTableNameDateSeperator.seperateDateAsDate(o1);
                LocalDate l2 = payRateTableNameDateSeperator.seperateDateAsDate(o2);
                return l2.compareTo(l1);
            }
        });
        Vector<String> presentVector = new Vector<>();
        for (String string : stringVector) {
            presentVector.add(PayRateTableNameStringEditor.createReadableTableNameForView(string));
        }

        return presentVector;
    }

    public List<SalaryTable> getSalaryTable(String tableTitle) {
        return salaryTableManager.getSalaryTable(tableTitle);
    }
}
