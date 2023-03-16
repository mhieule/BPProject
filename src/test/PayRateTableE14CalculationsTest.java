package test;

import excelchaos_model.database.SalaryTable;
import excelchaos_model.database.SalaryTableManager;
import excelchaos_model.datecalculations.PayRateTableE13Calculations;
import excelchaos_model.datecalculations.PayRateTableE14Calculations;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PayRateTableE14CalculationsTest {
    private PayRateTableE14Calculations payRateTableE14Calculations;

    @Test
    public void testGetCurrentPayRateWith1AAnd1BTable() {
        SalaryTableManager.setDatabaseURL("Excelchaos.db");
        // Test with a date that exists in a pay rate table with "1A" or "1B"
        LocalDate dateWith1AAnd1B = LocalDate.of(2022, 1, 1);
        payRateTableE14Calculations = new PayRateTableE14Calculations();
        List<SalaryTable> salaryTablesWith1AAnd1B = payRateTableE14Calculations.getCurrentPayRateWith1AAnd1BTable();
        SalaryTable expectedSalaryTable = salaryTablesWith1AAnd1B.get(0);
        assertEquals(expectedSalaryTable.getTable_name(), "Entgelttabelle E14 mit Stufe 1A und 1B_01.08.2022");
    }

    @Test
    public void testGetCurrentPayRateWithout1AAnd1BTable() {
        SalaryTableManager.setDatabaseURL("Excelchaos.db");
        // Test with a date that exists in a pay rate table with "1A" or "1B"
        LocalDate dateWith1AAnd1B = LocalDate.of(2022, 1, 1);
        payRateTableE14Calculations = new PayRateTableE14Calculations();
        List<SalaryTable> salaryTablesWithout1AAnd1B = payRateTableE14Calculations.getCurrentPayRateWithout1AAnd1BTable();
        SalaryTable expectedSalaryTable = salaryTablesWithout1AAnd1B.get(0);
        assertEquals(expectedSalaryTable.getTable_name(), "Entgelttabelle E14 mit Stufe 1_01.01.2022");
    }


    @Test
    public void testGetPayRateWithout1AAnd1BTableBasedOnChosenDate() {
        SalaryTableManager.setDatabaseURL("Excelchaos.db");
        // Test with a date that exists in a pay rate table with "1A" or "1B"
        LocalDate dateWithout1AAnd1B = LocalDate.of(2023, 8, 1);
        payRateTableE14Calculations = new PayRateTableE14Calculations();
        List<SalaryTable> salaryTablesWithout1AAnd1B = payRateTableE14Calculations.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(dateWithout1AAnd1B);
        SalaryTable expectedSalaryTable = salaryTablesWithout1AAnd1B.get(0);
        assertEquals(expectedSalaryTable.getTable_name(), "Entgelttabelle E14 mit Stufe 1_01.01.2022");
    }

    @Test
    public void testGetPayRateWith1AAnd1BTableBasedOnChosenDate() {
        SalaryTableManager.setDatabaseURL("Excelchaos.db");
        LocalDate dateWith1AAnd1B = LocalDate.of(2023, 8, 1);
        payRateTableE14Calculations = new PayRateTableE14Calculations();
        List<SalaryTable> salaryTablesWith1AAnd1B = payRateTableE14Calculations.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(dateWith1AAnd1B);
        SalaryTable expectedSalaryTable = salaryTablesWith1AAnd1B.get(0);
        System.out.println(expectedSalaryTable.getTable_name());
        assertEquals(expectedSalaryTable.getTable_name(), "Entgelttabelle E14 mit Stufe 1A und 1B_01.08.2023");
    }

    @Test
    public void testgetActivePayRateTableDateWithoutAAndB(){
        SalaryTableManager.setDatabaseURL("Excelchaos.db");
        // Test with a date that exists in a pay rate table with "1A" or "1B"
        LocalDate dateWithout1AAnd1B = LocalDate.of(2023, 8, 1);
        payRateTableE14Calculations = new PayRateTableE14Calculations();
        LocalDate testDate = payRateTableE14Calculations.getActivePayRateTableDateWithoutAAndB(dateWithout1AAnd1B);
        assertEquals(testDate,LocalDate.of(2022,1,1));
    }

    @Test
    public void testgetActivePayRateTableDateWithAAndB(){
        SalaryTableManager.setDatabaseURL("Excelchaos.db");
        // Test with a date that exists in a pay rate table with "1A" or "1B"
        LocalDate dateWith1AAnd1B = LocalDate.of(2023, 10, 1);
        payRateTableE14Calculations = new PayRateTableE14Calculations();
        LocalDate testDate = payRateTableE14Calculations.getActivePayRateTableDateWithAAndB(dateWith1AAnd1B);
        assertEquals(testDate,LocalDate.of(2023,8,1));
    }
}
