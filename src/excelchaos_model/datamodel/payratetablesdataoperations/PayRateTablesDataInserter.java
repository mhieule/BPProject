package excelchaos_model.datamodel.payratetablesdataoperations;

import excelchaos_model.database.SalaryTable;
import excelchaos_model.database.SalaryTableManager;

import java.math.BigDecimal;
import java.util.List;

public class PayRateTablesDataInserter {
    private SalaryTableManager salaryTableManager = SalaryTableManager.getInstance();

    public void saveSalaryTableInDatabase(BigDecimal[][] newPayRates, String tableTitle, String tableName,int border){
        List<SalaryTable> salaryTables = salaryTableManager.getSalaryTable(tableTitle);
        salaryTableManager.removeSalaryTable(tableTitle);

        int column = 0;
        for (SalaryTable salaryTable : salaryTables) {
            salaryTable.setGrundendgeld(newPayRates[0][column]);
            salaryTable.setAv_ag_anteil_lfd_entgelt(newPayRates[1][column]);
            salaryTable.setKv_ag_anteil_lfd_entgelt(newPayRates[2][column]);
            salaryTable.setZusbei_af_lfd_entgelt(newPayRates[3][column]);
            salaryTable.setPv_ag_anteil_lfd_entgelt(newPayRates[4][column]);
            salaryTable.setRv_ag_anteil_lfd_entgelt(newPayRates[5][column]);
            salaryTable.setSv_umlage_u2(newPayRates[6][column]);
            salaryTable.setSteuern_ag(newPayRates[7][column]);
            salaryTable.setZv_Sanierungsbeitrag(newPayRates[8][column]);
            salaryTable.setZv_umlage_allgemein(newPayRates[9][column]);
            salaryTable.setVbl_wiss_4perc_ag_buchung(newPayRates[10][column]);
            salaryTable.setMtl_kosten_ohne_jsz(newPayRates[11][column]);
            salaryTable.setJsz_als_monatliche_zulage(newPayRates[12][column]);
            salaryTable.setMtl_kosten_mit_jsz(newPayRates[13][column]);
            salaryTable.setJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung(newPayRates[14][column]);
            salaryTable.setTable_name(tableName);
            salaryTableManager.addSalaryTable(salaryTable);
            if (column < border) {
                column++;
            } else {
                break;
            }
        }
    }
}
