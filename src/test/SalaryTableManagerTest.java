import excelchaos_model.database.SalaryTable;
import excelchaos_model.database.SalaryTableManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalaryTableManagerTest {
    @Test
    void testRemoveAll(){
        var manager = new SalaryTableManager();
        manager.removeAllTables();
        assertEquals(manager.getAllSalaryTables().size(), 0);
    }


    @Test
    void testGetValid(){
        var manager = new SalaryTableManager();
        manager.removeAllTables();
        var salaryTable = new SalaryTable("test_01",  0.25, 4.0,
                2.0, 3.0, 1.9, 1.7, 2.9, 3.1, 6.0, 5.1, 5.2, 5.3, 5.6, 5.7, 6.3, "E13");
        manager.addSalaryTable(salaryTable);
        var recTable = manager.getSalaryTable("test_01").get(0);
        assertEquals(salaryTable.getTable_name(), recTable.getTable_name());
        assertEquals(salaryTable.getGrundendgeld(), recTable.getGrundendgeld());
        assertEquals(salaryTable.getAv_ag_anteil_lfd_entgelt(), recTable.getAv_ag_anteil_lfd_entgelt());
        assertEquals(salaryTable.getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung(),
                recTable.getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung());
        assertEquals(salaryTable.getAv_ag_anteil_lfd_entgelt(), recTable.getAv_ag_anteil_lfd_entgelt());
        assertEquals(salaryTable.getJsz_als_monatliche_zulage(), recTable.getJsz_als_monatliche_zulage());
        assertEquals(salaryTable.getKv_ag_anteil_lfd_entgelt(), recTable.getKv_ag_anteil_lfd_entgelt());
        assertEquals(salaryTable.getMtl_kosten_mit_jsz(), recTable.getMtl_kosten_mit_jsz());
        assertEquals(salaryTable.getMtl_kosten_ohne_jsz(), recTable.getMtl_kosten_ohne_jsz());
        assertEquals(salaryTable.getPv_ag_anteil_lfd_entgelt(), recTable.getPv_ag_anteil_lfd_entgelt());
        assertEquals(salaryTable.getRv_ag_anteil_lfd_entgelt(), recTable.getRv_ag_anteil_lfd_entgelt());
        assertEquals(salaryTable.getPaygrade(), recTable.getPaygrade());
        assertEquals(salaryTable.getSteuern_ag(), recTable.getSteuern_ag());
        assertEquals(salaryTable.getVbl_wiss_4perc_ag_buchung(), recTable.getVbl_wiss_4perc_ag_buchung());
        assertEquals(salaryTable.getSv_umlage_u2(), recTable.getSv_umlage_u2());
        assertEquals(salaryTable.getZv_Sanierungsbeitrag(), recTable.getZv_Sanierungsbeitrag());
        assertEquals(salaryTable.getZv_umlage_allgemein(), recTable.getZv_umlage_allgemein());
        assertEquals(salaryTable.getZusbei_af_lfd_entgelt(), recTable.getZusbei_af_lfd_entgelt());
    }

    @Test
    void testGetInvalid(){
        var manager = new SalaryTableManager();
        manager.removeAllTables();
        var salaryTable = new SalaryTable("test_01",  0.25, 4.0,
                2.0, 3.0, 1.9, 1.7, 2.9, 3.1, 6.0, 5.1, 5.2, 5.3, 5.6, 5.7, 6.3, "E13");
        manager.addSalaryTable(salaryTable);
        var recTable = manager.getSalaryTable("test_02");
        assertEquals(recTable.size(), 0);
    }

    @Test
    void testRemoveValid(){
        var manager = new SalaryTableManager();
        manager.removeAllTables();
        var salaryTable = new SalaryTable("test_01",  0.25, 4.0,
                2.0, 3.0, 1.9, 1.7, 2.9, 3.1, 6.0, 5.1, 5.2, 5.3, 5.6, 5.7, 6.3, "E13");
        manager.addSalaryTable(salaryTable);
        manager.removeSalaryTable("test_01");
        var recTable = manager.getSalaryTable("test_01");
        assertEquals(recTable.size(), 0);
    }

    @Test
    void testGetAll(){
        var manager = new SalaryTableManager();
        manager.removeAllTables();
        var salaryTables = new SalaryTable[10];
        for (int i = 0; i < 10; i++){
            var salaryTable = new SalaryTable("test_0"+i,  0.25, 4.0,
                    2.0, 3.0, 1.9, 1.7, 2.9, 3.1, 6.0, 5.1, 5.2, 5.3, 5.6, 5.7, 6.3, "E13");
            manager.addSalaryTable(salaryTable);
            salaryTables[i] = salaryTable;
        }
        var recTable = manager.getAllSalaryTables();
        for (int i = 0; i < 10; i++){
            assertEquals(salaryTables[i].getTable_name(), recTable.get(i).getTable_name());
            assertEquals(salaryTables[i].getGrundendgeld(), recTable.get(i).getGrundendgeld());
            assertEquals(salaryTables[i].getAv_ag_anteil_lfd_entgelt(), recTable.get(i).getAv_ag_anteil_lfd_entgelt());
            assertEquals(salaryTables[i].getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung(),
                    recTable.get(i).getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung());
            assertEquals(salaryTables[i].getAv_ag_anteil_lfd_entgelt(), recTable.get(i).getAv_ag_anteil_lfd_entgelt());
            assertEquals(salaryTables[i].getJsz_als_monatliche_zulage(), recTable.get(i).getJsz_als_monatliche_zulage());
            assertEquals(salaryTables[i].getKv_ag_anteil_lfd_entgelt(), recTable.get(i).getKv_ag_anteil_lfd_entgelt());
            assertEquals(salaryTables[i].getMtl_kosten_mit_jsz(), recTable.get(i).getMtl_kosten_mit_jsz());
            assertEquals(salaryTables[i].getMtl_kosten_ohne_jsz(), recTable.get(i).getMtl_kosten_ohne_jsz());
            assertEquals(salaryTables[i].getPv_ag_anteil_lfd_entgelt(), recTable.get(i).getPv_ag_anteil_lfd_entgelt());
            assertEquals(salaryTables[i].getRv_ag_anteil_lfd_entgelt(), recTable.get(i).getRv_ag_anteil_lfd_entgelt());
            assertEquals(salaryTables[i].getPaygrade(), recTable.get(i).getPaygrade());
            assertEquals(salaryTables[i].getSteuern_ag(), recTable.get(i).getSteuern_ag());
            assertEquals(salaryTables[i].getVbl_wiss_4perc_ag_buchung(), recTable.get(i).getVbl_wiss_4perc_ag_buchung());
            assertEquals(salaryTables[i].getSv_umlage_u2(), recTable.get(i).getSv_umlage_u2());
            assertEquals(salaryTables[i].getZv_Sanierungsbeitrag(), recTable.get(i).getZv_Sanierungsbeitrag());
            assertEquals(salaryTables[i].getZv_umlage_allgemein(), recTable.get(i).getZv_umlage_allgemein());
            assertEquals(salaryTables[i].getZusbei_af_lfd_entgelt(), recTable.get(i).getZusbei_af_lfd_entgelt());
        }
    }

    @Test
    void testGetNum(){
        var manager = new SalaryTableManager();
        manager.removeAllTables();
        var salaryTable = new SalaryTable("test_01",  0.25, 4.0,
                2.0, 3.0, 1.9, 1.7, 2.9, 3.1, 6.0, 5.1, 5.2, 5.3, 5.6, 5.7, 6.3, "E13");
        manager.addSalaryTable(salaryTable);
        manager.addSalaryTable(salaryTable);
        salaryTable = new SalaryTable("test_02",  0.25, 4.0,
                2.0, 3.0, 1.9, 1.7, 2.9, 3.1, 6.0, 5.1, 5.2, 5.3, 5.6, 5.7, 6.3, "E13");
        manager.addSalaryTable(salaryTable);
        assertEquals(manager.getNumOfTables("E13"), 2);
    }

    @Test
    void testGetNumEmpty(){
        var manager = new SalaryTableManager();
        manager.removeAllTables();
        assertEquals(manager.getNumOfTables("E13"), 0);
    }

    @Test
    void testGetDistinct(){
        var manager = new SalaryTableManager();
        manager.removeAllTables();
        var salaryTable = new SalaryTable("test_01",  0.25, 4.0,
                2.0, 3.0, 1.9, 1.7, 2.9, 3.1, 6.0, 5.1, 5.2, 5.3, 5.6, 5.7, 6.3, "E13");
        manager.addSalaryTable(salaryTable);
        manager.addSalaryTable(salaryTable);
        salaryTable = new SalaryTable("test_02",  0.25, 4.0,
                2.0, 3.0, 1.9, 1.7, 2.9, 3.1, 6.0, 5.1, 5.2, 5.3, 5.6, 5.7, 6.3, "E13");
        manager.addSalaryTable(salaryTable);
        assertEquals(manager.getDistinctTableNames("E13").get(0), "test_01");
        assertEquals(manager.getDistinctTableNames("E13").get(1), "test_02");
    }

    @Test
    void testGetDistinctEmpty(){
        var manager = new SalaryTableManager();
        manager.removeAllTables();
        assertEquals(manager.getDistinctTableNames("E13").size(), 0);
    }
}
