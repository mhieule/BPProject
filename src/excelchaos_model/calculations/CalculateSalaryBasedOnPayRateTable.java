package excelchaos_model.calculations;

import excelchaos_model.Contract;
import excelchaos_model.Employee;
import excelchaos_model.SalaryTable;
import excelchaos_model.datecalculations.CurrentPayRateTableE13;
import excelchaos_model.datecalculations.CurrentPayRateTableE14;

import java.time.LocalDate;
import java.util.List;

public class CalculateSalaryBasedOnPayRateTable {

    boolean vblpflicht = true; //TODO Als Attribut in Contract einführen um die entsprechende Konditionenabfrage(If) durchführen zu können.

    public double getCurrentPayRateTableEntryForWiMiAndATM(Contract contract) {
        double result = 0;
        CurrentPayRateTableE13 currentPayRateTableE13 = new CurrentPayRateTableE13();
        CurrentPayRateTableE14 currentPayRateTableE14 = new CurrentPayRateTableE14();
        List<SalaryTable> E13SalaryTable = currentPayRateTableE13.getCurrentPayRateTable();
        List<SalaryTable> E14SalaryTable = currentPayRateTableE14.getCurrentPayRateTable();
        if (contract.getPaygrade().equals("E13")) {
            if (vblpflicht) {
                if (E13SalaryTable.get(0).getTable_name().contains("1A")) {
                    switch (contract.getPaylevel()) {
                        case "1A":
                            result = E13SalaryTable.get(2).getMtl_kosten_ohne_jsz();
                            break;
                        case "1B":
                            result = E13SalaryTable.get(4).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E13SalaryTable.get(6).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E13SalaryTable.get(8).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E13SalaryTable.get(10).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E13SalaryTable.get(12).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E13SalaryTable.get(14).getMtl_kosten_ohne_jsz();
                            break;
                    }
                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            result = E13SalaryTable.get(2).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E13SalaryTable.get(4).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E13SalaryTable.get(6).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E13SalaryTable.get(8).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E13SalaryTable.get(10).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E13SalaryTable.get(12).getMtl_kosten_ohne_jsz();
                            break;
                    }
                }

            } else {
                if (E13SalaryTable.get(0).getTable_name().contains("1A")) {
                    switch (contract.getPaylevel()) {
                        case "1A":
                            result = E13SalaryTable.get(1).getMtl_kosten_ohne_jsz();
                            break;
                        case "1B":
                            result = E13SalaryTable.get(3).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E13SalaryTable.get(5).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E13SalaryTable.get(7).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E13SalaryTable.get(9).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E13SalaryTable.get(11).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E13SalaryTable.get(13).getMtl_kosten_ohne_jsz();
                            break;
                    }
                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            result = E13SalaryTable.get(1).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E13SalaryTable.get(3).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E13SalaryTable.get(5).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E13SalaryTable.get(7).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E13SalaryTable.get(9).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E13SalaryTable.get(11).getMtl_kosten_ohne_jsz();
                            break;
                    }
                }

            }
        } else {
            if (vblpflicht) {
                if (E14SalaryTable.get(0).getTable_name().contains("1A")) {
                    switch (contract.getPaylevel()) {
                        case "1A":
                            result = E14SalaryTable.get(2).getMtl_kosten_ohne_jsz();
                            break;
                        case "1B":
                            result = E14SalaryTable.get(4).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E14SalaryTable.get(6).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E14SalaryTable.get(8).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E14SalaryTable.get(10).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E14SalaryTable.get(12).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E14SalaryTable.get(14).getMtl_kosten_ohne_jsz();
                            break;

                    }
                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            result = E14SalaryTable.get(2).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E14SalaryTable.get(4).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E14SalaryTable.get(6).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E14SalaryTable.get(8).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E14SalaryTable.get(10).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E14SalaryTable.get(12).getMtl_kosten_ohne_jsz();
                            break;
                    }
                }

            } else {
                if (E14SalaryTable.get(0).getTable_name().contains("1A")) {
                    switch (contract.getPaylevel()) {
                        case "1A":
                            result = E14SalaryTable.get(1).getMtl_kosten_ohne_jsz();
                            break;
                        case "1B":
                            result = E14SalaryTable.get(3).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E14SalaryTable.get(5).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E14SalaryTable.get(7).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E14SalaryTable.get(9).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E14SalaryTable.get(11).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E14SalaryTable.get(13).getMtl_kosten_ohne_jsz();
                            break;
                    }
                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            result = E14SalaryTable.get(1).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E14SalaryTable.get(3).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E14SalaryTable.get(5).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E14SalaryTable.get(7).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E14SalaryTable.get(9).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E14SalaryTable.get(11).getMtl_kosten_ohne_jsz();
                            break;
                    }
                }

            }
        }
        return result;
    }

    public double getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(Contract contract, LocalDate choosenDate) {
        double result = 0;
        CurrentPayRateTableE13 currentPayRateTableE13 = new CurrentPayRateTableE13();
        CurrentPayRateTableE14 currentPayRateTableE14 = new CurrentPayRateTableE14();
        List<SalaryTable> E13SalaryTable = currentPayRateTableE13.getPayRateTableBasedOnChosenDate(choosenDate);
        List<SalaryTable> E14SalaryTable = currentPayRateTableE14.getPayRateTableBasedOnChosenDate(choosenDate);
        if (contract.getPaygrade().equals("E13")) {
            if (vblpflicht) {
                if (E13SalaryTable.get(0).getTable_name().contains("1A")) {
                    switch (contract.getPaylevel()) {
                        case "1A":
                            result = E13SalaryTable.get(2).getMtl_kosten_ohne_jsz();
                            break;
                        case "1B":
                            result = E13SalaryTable.get(4).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E13SalaryTable.get(6).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E13SalaryTable.get(8).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E13SalaryTable.get(10).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E13SalaryTable.get(12).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E13SalaryTable.get(14).getMtl_kosten_ohne_jsz();
                            break;
                    }
                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            result = E13SalaryTable.get(2).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E13SalaryTable.get(4).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E13SalaryTable.get(6).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E13SalaryTable.get(8).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E13SalaryTable.get(10).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E13SalaryTable.get(12).getMtl_kosten_ohne_jsz();
                            break;
                    }
                }

            } else {
                if (E13SalaryTable.get(0).getTable_name().contains("1A")) {
                    switch (contract.getPaylevel()) {
                        case "1A":
                            result = E13SalaryTable.get(1).getMtl_kosten_ohne_jsz();
                            break;
                        case "1B":
                            result = E13SalaryTable.get(3).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E13SalaryTable.get(5).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E13SalaryTable.get(7).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E13SalaryTable.get(9).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E13SalaryTable.get(11).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E13SalaryTable.get(13).getMtl_kosten_ohne_jsz();
                            break;
                    }
                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            result = E13SalaryTable.get(1).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E13SalaryTable.get(3).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E13SalaryTable.get(5).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E13SalaryTable.get(7).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E13SalaryTable.get(9).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E13SalaryTable.get(11).getMtl_kosten_ohne_jsz();
                            break;
                    }
                }

            }
        } else {
            if (vblpflicht) {
                if (E14SalaryTable.get(0).getTable_name().contains("1A")) {
                    switch (contract.getPaylevel()) {
                        case "1A":
                            result = E14SalaryTable.get(2).getMtl_kosten_ohne_jsz();
                            break;
                        case "1B":
                            result = E14SalaryTable.get(4).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E14SalaryTable.get(6).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E14SalaryTable.get(8).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E14SalaryTable.get(10).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E14SalaryTable.get(12).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E14SalaryTable.get(14).getMtl_kosten_ohne_jsz();
                            break;

                    }
                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            result = E14SalaryTable.get(2).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E14SalaryTable.get(4).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E14SalaryTable.get(6).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E14SalaryTable.get(8).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E14SalaryTable.get(10).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E14SalaryTable.get(12).getMtl_kosten_ohne_jsz();
                            break;
                    }
                }

            } else {
                if (E14SalaryTable.get(0).getTable_name().contains("1A")) {
                    switch (contract.getPaylevel()) {
                        case "1A":
                            result = E14SalaryTable.get(1).getMtl_kosten_ohne_jsz();
                            break;
                        case "1B":
                            result = E14SalaryTable.get(3).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E14SalaryTable.get(5).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E14SalaryTable.get(7).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E14SalaryTable.get(9).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E14SalaryTable.get(11).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E14SalaryTable.get(13).getMtl_kosten_ohne_jsz();
                            break;
                    }
                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            result = E14SalaryTable.get(1).getMtl_kosten_ohne_jsz();
                            break;
                        case "2":
                            result = E14SalaryTable.get(3).getMtl_kosten_ohne_jsz();
                            break;
                        case "3":
                            result = E14SalaryTable.get(5).getMtl_kosten_ohne_jsz();
                            break;
                        case "4":
                            result = E14SalaryTable.get(7).getMtl_kosten_ohne_jsz();
                            break;
                        case "5":
                            result = E14SalaryTable.get(9).getMtl_kosten_ohne_jsz();
                            break;
                        case "6":
                            result = E14SalaryTable.get(11).getMtl_kosten_ohne_jsz();
                            break;
                    }
                }

            }
        }
        return result;
    }

    public double getPayRateTableEntryForSHK(Employee employee, Contract contract) {
        double result = 0;

        return result;
    }
}
