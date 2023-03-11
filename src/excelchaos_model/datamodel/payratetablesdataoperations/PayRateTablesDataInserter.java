package excelchaos_model.datamodel.payratetablesdataoperations;

import excelchaos_model.database.SalaryTable;
import excelchaos_model.database.SalaryTableManager;

import java.math.BigDecimal;
import java.util.List;

public class PayRateTablesDataInserter {
    private SalaryTableManager salaryTableManager = SalaryTableManager.getInstance();

    public void updateSalaryTable(BigDecimal[][] newPayRates, String tableTitle, String tableName, int border){
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

    public void insertNewSalaryTable(String tableName, String paygrade, BigDecimal[][] payRateValues){
        for (int column = 0; column < payRateValues[0].length; column++) {
            BigDecimal grundentgelt = payRateValues[0][column];
            BigDecimal av_ag_anteil_lfd_entgelt = payRateValues[1][column];
            BigDecimal kv_ag_anteil_lfd_entgelt = payRateValues[2][column];
            BigDecimal zusbei_af_lfd_entgelt = payRateValues[3][column];
            BigDecimal pv_ag_anteil_lfd_entgelt = payRateValues[4][column];
            BigDecimal rv_ag_anteil_lfd_entgelt = payRateValues[5][column];
            BigDecimal sv_umlage_u2 = payRateValues[6][column];
            BigDecimal steuern_ag = payRateValues[7][column];
            BigDecimal zv_Sanierungsbeitrag = payRateValues[8][column];
            BigDecimal zv_umlage_allgemein = payRateValues[9][column];
            BigDecimal vbl_wiss_4perc_ag_buchung = payRateValues[10][column];
            BigDecimal mtl_kosten_ohne_jsz = payRateValues[11][column];
            BigDecimal jsz_als_monatliche_zulage = payRateValues[12][column];
            BigDecimal mtl_kosten_mit_jsz = payRateValues[13][column];
            BigDecimal jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung = payRateValues[14][column];

            SalaryTable salaryTable = new SalaryTable(tableName, grundentgelt, av_ag_anteil_lfd_entgelt, kv_ag_anteil_lfd_entgelt, zusbei_af_lfd_entgelt, pv_ag_anteil_lfd_entgelt, rv_ag_anteil_lfd_entgelt, sv_umlage_u2, steuern_ag, zv_Sanierungsbeitrag, zv_umlage_allgemein, vbl_wiss_4perc_ag_buchung, mtl_kosten_ohne_jsz, jsz_als_monatliche_zulage, mtl_kosten_mit_jsz, jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung, paygrade);
            salaryTableManager.addSalaryTable(salaryTable);
        }
    }
}
