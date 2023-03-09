package excelchaos_model.calculations;

import excelchaos_model.database.*;
import excelchaos_model.datecalculations.CurrentPayRateTableE13;
import excelchaos_model.datecalculations.CurrentPayRateTableE14;
import excelchaos_model.datecalculations.CurrentPayRateTableSHK;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/*public class CalculateSalaryBasedOnPayRateTable {


    //TODO Großflächig testen
    public double[] getCurrentPayRateTableEntryForWiMiAndATM(Contract contract) {
        double[] result = new double[2];
        CurrentPayRateTableE13 currentPayRateTableE13 = new CurrentPayRateTableE13();
        CurrentPayRateTableE14 currentPayRateTableE14 = new CurrentPayRateTableE14();


        if (contract.getPaygrade().equals("E13")) {
            List<SalaryTable> E13SalaryTableWith1AAnd1B = null;
            List<SalaryTable> E13SalaryTableWithout1AAnd1B = null;

            if (currentPayRateTableE13.getCurrentPayRateWith1AAnd1BTable() == null && currentPayRateTableE13.getCurrentPayRateWithout1AAnd1BTable() == null) {
                result[0] = 0;
                result[1] = 0;
                return result;
            }
            if (currentPayRateTableE13.getCurrentPayRateWith1AAnd1BTable() != null) {
                E13SalaryTableWith1AAnd1B = currentPayRateTableE13.getCurrentPayRateWith1AAnd1BTable();
            } else {
                if (!contract.getPaylevel().equals("1")) {
                    result[0] = 0;
                    result[1] = 0;
                    return result;
                } else {
                    if (currentPayRateTableE13.getCurrentPayRateWithout1AAnd1BTable() != null) {
                        E13SalaryTableWithout1AAnd1B = currentPayRateTableE13.getCurrentPayRateWithout1AAnd1BTable();
                        if (contract.getVbl_status()) {
                            result[0] = E13SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                            result[1] = E13SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        } else {
                            result[0] = E13SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                            result[1] = E13SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        }
                    } else {
                        result[0] = 0;
                        result[1] = 0;
                        return result;
                    }
                }
            }
            if (currentPayRateTableE13.getCurrentPayRateWithout1AAnd1BTable() != null) {
                E13SalaryTableWithout1AAnd1B = currentPayRateTableE13.getCurrentPayRateWithout1AAnd1BTable();
            } else {
                if (contract.getPaylevel().equals("1")) {
                    result[0] = 0;
                    result[1] = 0;
                    return result;
                }
            }

            if (contract.getVbl_status()) {
                switch (contract.getPaylevel()) {
                    case "1":
                        result[0] = E13SalaryTableWithout1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWithout1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        break;
                    case "1A":
                        result[0] = E13SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        break;
                    case "1B":
                        result[0] = E13SalaryTableWith1AAnd1B.get(4).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(4).getJsz_als_monatliche_zulage();
                        break;
                    case "2":
                        result[0] = E13SalaryTableWith1AAnd1B.get(6).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(6).getJsz_als_monatliche_zulage();
                        break;
                    case "3":
                        result[0] = E13SalaryTableWith1AAnd1B.get(8).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(8).getJsz_als_monatliche_zulage();
                        break;
                    case "4":
                        result[0] = E13SalaryTableWith1AAnd1B.get(10).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(10).getJsz_als_monatliche_zulage();
                        break;
                    case "5":
                        result[0] = E13SalaryTableWith1AAnd1B.get(12).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(12).getJsz_als_monatliche_zulage();
                        break;
                    case "6":
                        result[0] = E13SalaryTableWith1AAnd1B.get(14).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(14).getJsz_als_monatliche_zulage();
                        break;
                }

            } else {
                switch (contract.getPaylevel()) {
                    case "1":
                        result[0] = E13SalaryTableWithout1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWithout1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        break;
                    case "1A":
                        result[0] = E13SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        break;
                    case "1B":
                        result[0] = E13SalaryTableWith1AAnd1B.get(3).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(3).getJsz_als_monatliche_zulage();
                        break;
                    case "2":
                        result[0] = E13SalaryTableWith1AAnd1B.get(5).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(5).getJsz_als_monatliche_zulage();
                        break;
                    case "3":
                        result[0] = E13SalaryTableWith1AAnd1B.get(7).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(7).getJsz_als_monatliche_zulage();
                        break;
                    case "4":
                        result[0] = E13SalaryTableWith1AAnd1B.get(9).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(9).getJsz_als_monatliche_zulage();
                        break;
                    case "5":
                        result[0] = E13SalaryTableWith1AAnd1B.get(11).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(11).getJsz_als_monatliche_zulage();
                        break;
                    case "6":
                        result[0] = E13SalaryTableWith1AAnd1B.get(13).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(13).getJsz_als_monatliche_zulage();
                        break;

                }
            }

        } else {
            List<SalaryTable> E14SalaryTableWith1AAnd1B = null;
            List<SalaryTable> E14SalaryTableWithout1AAnd1B = null;

            if (currentPayRateTableE14.getCurrentPayRateWith1AAnd1BTable() == null && currentPayRateTableE14.getCurrentPayRateWithout1AAnd1BTable() == null) {
                result[0] = 0;
                result[1] = 0;

                return result;
            }
            if (currentPayRateTableE14.getCurrentPayRateWith1AAnd1BTable() != null) {
                E14SalaryTableWith1AAnd1B = currentPayRateTableE14.getCurrentPayRateWith1AAnd1BTable();
            } else {
                if (!contract.getPaylevel().equals("1")) {
                    result[0] = 0;
                    result[1] = 0;

                    return result;
                } else {
                    if (currentPayRateTableE14.getCurrentPayRateWithout1AAnd1BTable() != null) {
                        E14SalaryTableWithout1AAnd1B = currentPayRateTableE14.getCurrentPayRateWithout1AAnd1BTable();
                        if (contract.getVbl_status()) {
                            result[0] = E14SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                            result[1] = E14SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();

                        } else {
                            result[0] = E14SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                            result[1] = E14SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();

                        }
                    } else {
                        result[0] = 0;
                        result[1] = 0;
                        return result;
                    }
                }
            }
            if (currentPayRateTableE14.getCurrentPayRateWithout1AAnd1BTable() != null) {
                E14SalaryTableWithout1AAnd1B = currentPayRateTableE14.getCurrentPayRateWithout1AAnd1BTable();
            } else {
                if (contract.getPaylevel().equals("1")) {
                    result[0] = 0;
                    result[1] = 0;

                    return result;
                }
            }

            if (contract.getVbl_status()) {
                switch (contract.getPaylevel()) {
                    case "1":
                        result[0] = E14SalaryTableWithout1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWithout1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        break;
                    case "1A":
                        result[0] = E14SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        break;
                    case "1B":
                        result[0] = E14SalaryTableWith1AAnd1B.get(4).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(4).getJsz_als_monatliche_zulage();
                        break;
                    case "2":
                        result[0] = E14SalaryTableWith1AAnd1B.get(6).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(6).getJsz_als_monatliche_zulage();
                        break;
                    case "3":
                        result[0] = E14SalaryTableWith1AAnd1B.get(8).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(8).getJsz_als_monatliche_zulage();
                        break;
                    case "4":
                        result[0] = E14SalaryTableWith1AAnd1B.get(10).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(10).getJsz_als_monatliche_zulage();
                        break;
                    case "5":
                        result[0] = E14SalaryTableWith1AAnd1B.get(12).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(12).getJsz_als_monatliche_zulage();
                        break;
                    case "6":
                        result[0] = E14SalaryTableWith1AAnd1B.get(14).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(14).getJsz_als_monatliche_zulage();
                        break;
                }

            } else {
                switch (contract.getPaylevel()) {
                    case "1":
                        result[0] = E14SalaryTableWithout1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWithout1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        break;
                    case "1A":
                        result[0] = E14SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        break;
                    case "1B":
                        result[0] = E14SalaryTableWith1AAnd1B.get(3).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(3).getJsz_als_monatliche_zulage();
                        break;
                    case "2":
                        result[0] = E14SalaryTableWith1AAnd1B.get(5).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(5).getJsz_als_monatliche_zulage();
                        break;
                    case "3":
                        result[0] = E14SalaryTableWith1AAnd1B.get(7).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(7).getJsz_als_monatliche_zulage();
                        break;
                    case "4":
                        result[0] = E14SalaryTableWith1AAnd1B.get(9).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(9).getJsz_als_monatliche_zulage();
                        break;
                    case "5":
                        result[0] = E14SalaryTableWith1AAnd1B.get(11).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(11).getJsz_als_monatliche_zulage();
                        break;
                    case "6":
                        result[0] = E14SalaryTableWith1AAnd1B.get(13).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(13).getJsz_als_monatliche_zulage();
                        break;

                }
            }
        }
        return result;
    }

    //TODO Großflächig testen
    public double[] getPayRateTableEntryForWiMiAndATMBasedOnChoosenDate(Contract contract, LocalDate choosenDate) {
        double[] result = new double[2];
        CurrentPayRateTableE13 choosenDatePayRateTableE13 = new CurrentPayRateTableE13();
        CurrentPayRateTableE14 choosenDatePayRateTableE14 = new CurrentPayRateTableE14();


        if (contract.getPaygrade().equals("E13")) {
            List<SalaryTable> E13SalaryTableWith1AAnd1B = null;
            List<SalaryTable> E13SalaryTableWithout1AAnd1B = null;

            if (choosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(choosenDate) == null && choosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(choosenDate) == null) {
                result[0] = 0;
                result[1] = 0;
                return result;
            }
            if (choosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(choosenDate) != null) {
                E13SalaryTableWith1AAnd1B = choosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(choosenDate);
            } else {
                if (!contract.getPaylevel().equals("1")) {
                    result[0] = 0;
                    result[1] = 0;
                    return result;
                } else {
                    if (choosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(choosenDate) != null) {
                        E13SalaryTableWithout1AAnd1B = choosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(choosenDate);
                        if (contract.getVbl_status()) {
                            result[0] = E13SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                            result[1] = E13SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        } else {
                            result[0] = E13SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                            result[1] = E13SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        }
                    } else {
                        result[0] = 0;
                        result[1] = 0;
                        return result;
                    }
                }
            }
            if (choosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(choosenDate) != null) {
                E13SalaryTableWithout1AAnd1B = choosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(choosenDate);
            } else {
                if (contract.getPaylevel().equals("1")) {
                    result[0] = 0;
                    result[1] = 0;
                    return result;
                }
            }

            if (contract.getVbl_status()) {
                switch (contract.getPaylevel()) {
                    case "1":
                        result[0] = E13SalaryTableWithout1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWithout1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        break;
                    case "1A":
                        result[0] = E13SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        break;
                    case "1B":
                        result[0] = E13SalaryTableWith1AAnd1B.get(4).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(4).getJsz_als_monatliche_zulage();
                        break;
                    case "2":
                        result[0] = E13SalaryTableWith1AAnd1B.get(6).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(6).getJsz_als_monatliche_zulage();
                        break;
                    case "3":
                        result[0] = E13SalaryTableWith1AAnd1B.get(8).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(8).getJsz_als_monatliche_zulage();
                        break;
                    case "4":
                        result[0] = E13SalaryTableWith1AAnd1B.get(10).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(10).getJsz_als_monatliche_zulage();
                        break;
                    case "5":
                        result[0] = E13SalaryTableWith1AAnd1B.get(12).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(12).getJsz_als_monatliche_zulage();
                        break;
                    case "6":
                        result[0] = E13SalaryTableWith1AAnd1B.get(14).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(14).getJsz_als_monatliche_zulage();
                        break;
                }

            } else {
                switch (contract.getPaylevel()) {
                    case "1":
                        result[0] = E13SalaryTableWithout1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWithout1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        break;
                    case "1A":
                        result[0] = E13SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        break;
                    case "1B":
                        result[0] = E13SalaryTableWith1AAnd1B.get(3).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(3).getJsz_als_monatliche_zulage();
                        break;
                    case "2":
                        result[0] = E13SalaryTableWith1AAnd1B.get(5).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(5).getJsz_als_monatliche_zulage();
                        break;
                    case "3":
                        result[0] = E13SalaryTableWith1AAnd1B.get(7).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(7).getJsz_als_monatliche_zulage();
                        break;
                    case "4":
                        result[0] = E13SalaryTableWith1AAnd1B.get(9).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(9).getJsz_als_monatliche_zulage();
                        break;
                    case "5":
                        result[0] = E13SalaryTableWith1AAnd1B.get(11).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(11).getJsz_als_monatliche_zulage();
                        break;
                    case "6":
                        result[0] = E13SalaryTableWith1AAnd1B.get(13).getMtl_kosten_ohne_jsz();
                        result[1] = E13SalaryTableWith1AAnd1B.get(13).getJsz_als_monatliche_zulage();
                        break;

                }
            }

        } else {
            List<SalaryTable> E14SalaryTableWith1AAnd1B = null;
            List<SalaryTable> E14SalaryTableWithout1AAnd1B = null;

            if (choosenDatePayRateTableE14.getCurrentPayRateWith1AAnd1BTable() == null && choosenDatePayRateTableE14.getCurrentPayRateWithout1AAnd1BTable() == null) {
                result[0] = 0;
                result[1] = 0;
                return result;
            }
            if (choosenDatePayRateTableE14.getCurrentPayRateWith1AAnd1BTable() != null) {
                E14SalaryTableWith1AAnd1B = choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(choosenDate);
            } else {
                if (!contract.getPaylevel().equals("1")) {
                    result[0] = 0;
                    result[1] = 0;
                    return result;
                } else {
                    if (choosenDatePayRateTableE14.getCurrentPayRateWithout1AAnd1BTable() != null) {
                        E14SalaryTableWithout1AAnd1B = choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(choosenDate);
                        if (contract.getVbl_status()) {
                            result[0] = E14SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                            result[1] = E14SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        } else {
                            result[0] = E14SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                            result[1] = E14SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        }
                    } else {
                        result[0] = 0;
                        result[1] = 0;
                        return result;
                    }
                }
            }
            if (choosenDatePayRateTableE14.getCurrentPayRateWithout1AAnd1BTable() != null) {
                E14SalaryTableWithout1AAnd1B = choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(choosenDate);
            } else {
                if (contract.getPaylevel().equals("1")) {
                    result[0] = 0;
                    result[1] = 0;
                    return result;
                }
            }

            if (contract.getVbl_status()) {
                switch (contract.getPaylevel()) {
                    case "1":
                        result[0] = E14SalaryTableWithout1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWithout1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        break;
                    case "1A":
                        result[0] = E14SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                        break;
                    case "1B":
                        result[0] = E14SalaryTableWith1AAnd1B.get(4).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(4).getJsz_als_monatliche_zulage();
                        break;
                    case "2":
                        result[0] = E14SalaryTableWith1AAnd1B.get(6).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(6).getJsz_als_monatliche_zulage();
                        break;
                    case "3":
                        result[0] = E14SalaryTableWith1AAnd1B.get(8).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(8).getJsz_als_monatliche_zulage();
                        break;
                    case "4":
                        result[0] = E14SalaryTableWith1AAnd1B.get(10).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(10).getJsz_als_monatliche_zulage();
                        break;
                    case "5":
                        result[0] = E14SalaryTableWith1AAnd1B.get(12).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(12).getJsz_als_monatliche_zulage();
                        break;
                    case "6":
                        result[0] = E14SalaryTableWith1AAnd1B.get(14).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(14).getJsz_als_monatliche_zulage();
                        break;
                }

            } else {
                switch (contract.getPaylevel()) {
                    case "1":
                        result[0] = E14SalaryTableWithout1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWithout1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        break;
                    case "1A":
                        result[0] = E14SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                        break;
                    case "1B":
                        result[0] = E14SalaryTableWith1AAnd1B.get(3).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(3).getJsz_als_monatliche_zulage();
                        break;
                    case "2":
                        result[0] = E14SalaryTableWith1AAnd1B.get(5).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(5).getJsz_als_monatliche_zulage();
                        break;
                    case "3":
                        result[0] = E14SalaryTableWith1AAnd1B.get(7).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(7).getJsz_als_monatliche_zulage();
                        break;
                    case "4":
                        result[0] = E14SalaryTableWith1AAnd1B.get(9).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(9).getJsz_als_monatliche_zulage();
                        break;
                    case "5":
                        result[0] = E14SalaryTableWith1AAnd1B.get(11).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(11).getJsz_als_monatliche_zulage();
                        break;
                    case "6":
                        result[0] = E14SalaryTableWith1AAnd1B.get(13).getMtl_kosten_ohne_jsz();
                        result[1] = E14SalaryTableWith1AAnd1B.get(13).getJsz_als_monatliche_zulage();
                        break;

                }
            }
        }
        return result;
    }

    //TODO Evtl Abfragen hinzufügen, dass dinge nicht null sind
    public LocalDate getActivePayRateTableDateBasedOnGivenDate(Contract contract, LocalDate chosenDate) {
        CurrentPayRateTableE13 chosenDatePayRateTableE13 = new CurrentPayRateTableE13();
        CurrentPayRateTableE14 chosenDatePayRateTableE14 = new CurrentPayRateTableE14();
        switch (contract.getPaygrade()) {
            case "E13":
                if (contract.getPaylevel().equals("1")) {
                    return chosenDatePayRateTableE13.getActivePayRateTableDateWithoutAAndB(chosenDate);
                } else return chosenDatePayRateTableE13.getActivePayRateTableDateWithAAndB(chosenDate);
            case "E14":
                if (contract.getPaylevel().equals("1")) {
                    return chosenDatePayRateTableE14.getActivePayRateTableDateWithoutAAndB(chosenDate);
                } else return chosenDatePayRateTableE14.getActivePayRateTableDateWithAAndB(chosenDate);
        }
        return null;

    }

    public BigDecimal getCurrentPayRateTableEntryForSHK(Contract contract) {
        CurrentPayRateTableSHK currentPayRateTableSHK = new CurrentPayRateTableSHK();
        int currentSHKEntryId = currentPayRateTableSHK.getCurrentSHKPayRates();
        SHKSalaryEntry currentEntry = SHKSalaryTableManager.getInstance().getSHKSalaryEntry(currentSHKEntryId);
        return switch (contract.getShk_hourly_rate()) {
            case "SHK Basisvergütung" -> currentEntry.getBasePayRate();
            case "SHK erhöhter Stundensatz" -> currentEntry.getExtendedPayRate();
            case "WHK" -> currentEntry.getWHKPayRate();
            default -> null;
        };
    }

    public BigDecimal getPayRateTableEntryForSHKBasedOnChosenDate(Contract contract, LocalDate chosenDate){
        CurrentPayRateTableSHK currentPayRateTableSHK = new CurrentPayRateTableSHK();
        int SHKEntryId = currentPayRateTableSHK.getSHKPayRatesBasedOnChosenDate(chosenDate);
        SHKSalaryEntry currentEntry = SHKSalaryTableManager.getInstance().getSHKSalaryEntry(SHKEntryId);
        return switch (contract.getShk_hourly_rate()) {
            case "SHK Basisvergütung" -> currentEntry.getBasePayRate();
            case "SHK erhöhter Stundensatz" -> currentEntry.getExtendedPayRate();
            case "WHK" -> currentEntry.getWHKPayRate();
            default -> null;
        };
    }
}*/
