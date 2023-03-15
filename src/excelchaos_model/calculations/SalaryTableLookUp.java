package excelchaos_model.calculations;

import excelchaos_model.database.*;
import excelchaos_model.datecalculations.PayRateTableE13Calculations;
import excelchaos_model.datecalculations.PayRateTableE14Calculations;
import excelchaos_model.datecalculations.PayRateTableSHKCalculations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class SalaryTableLookUp {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private SHKSalaryTableManager shkSalaryTableManager = new SHKSalaryTableManager();


    public BigDecimal[] getCurrentPayRateTableEntry(Contract contract) {
        BigDecimal[] resultValue = new BigDecimal[2];
        Employee employee = employeeDataManager.getEmployee(contract.getId());
        if (employee.getStatus().equals("SHK")) {
            PayRateTableSHKCalculations currentPayRateTableSHK = new PayRateTableSHKCalculations();
            int currentSHKEntryId = currentPayRateTableSHK.getCurrentSHKPayRates();
            SHKSalaryEntry currentEntry =shkSalaryTableManager.getSHKSalaryEntry(currentSHKEntryId);
            if (currentEntry == null) {
                resultValue[0] = new BigDecimal(0);
                resultValue[1] = new BigDecimal(0);
                return resultValue;
            }
            switch (contract.getShk_hourly_rate()) {
                case "SHK Basisvergütung" -> {
                    resultValue[0] = currentEntry.getBasePayRate().multiply(contract.getScope());
                    resultValue[1] = new BigDecimal(0);
                    return resultValue;
                }
                case "SHK erhöhter Stundensatz" -> {
                    resultValue[0] = currentEntry.getExtendedPayRate().multiply(contract.getScope());
                    resultValue[1] = new BigDecimal(0);
                    return resultValue;
                }
                case "WHK" -> {
                    resultValue[0] = currentEntry.getWHKPayRate().multiply(contract.getScope());
                    resultValue[1] = new BigDecimal(0);
                    return resultValue;
                }
            }
        } else {
            if (contract.getPaygrade().equals("E13")) {
                PayRateTableE13Calculations currentPayRateTableE13 = new PayRateTableE13Calculations();
                List<SalaryTable> E13SalaryTableWith1AAnd1B = null;
                List<SalaryTable> E13SalaryTableWithout1AAnd1B = null;


                if (currentPayRateTableE13.getCurrentPayRateWith1AAnd1BTable() == null && currentPayRateTableE13.getCurrentPayRateWithout1AAnd1BTable() == null) {
                    resultValue[0] = new BigDecimal(0);
                    resultValue[1] = new BigDecimal(0);
                    return resultValue;
                }
                if (currentPayRateTableE13.getCurrentPayRateWith1AAnd1BTable() != null) {
                    E13SalaryTableWith1AAnd1B = currentPayRateTableE13.getCurrentPayRateWith1AAnd1BTable();
                } else {
                    if (!contract.getPaylevel().equals("1")) {
                        resultValue[0] = new BigDecimal(0);
                        resultValue[1] = new BigDecimal(0);
                        return resultValue;
                    } else {
                        if (currentPayRateTableE13.getCurrentPayRateWithout1AAnd1BTable() != null) {
                            E13SalaryTableWithout1AAnd1B = currentPayRateTableE13.getCurrentPayRateWithout1AAnd1BTable();
                            if (contract.getVbl_status()) {
                                resultValue[0] = E13SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                                resultValue[1] = E13SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            } else {
                                resultValue[0] = E13SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                                resultValue[1] = E13SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            }
                        } else {
                            resultValue[0] = new BigDecimal(0);
                            resultValue[1] = new BigDecimal(0);
                            return resultValue;
                        }
                    }
                }
                if (currentPayRateTableE13.getCurrentPayRateWithout1AAnd1BTable() != null) {
                    E13SalaryTableWithout1AAnd1B = currentPayRateTableE13.getCurrentPayRateWithout1AAnd1BTable();
                } else {
                    if (contract.getPaylevel().equals("1")) {
                        resultValue[0] = new BigDecimal(0);
                        resultValue[1] = new BigDecimal(0);
                        return resultValue;
                    }
                }

                if (contract.getVbl_status()) {
                    switch (contract.getPaylevel()) {
                        case "1":
                            resultValue[0] = E13SalaryTableWithout1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWithout1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(4).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(4).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(6).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(6).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(8).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(8).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(10).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(10).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(12).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(12).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(14).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(14).getJsz_als_monatliche_zulage();
                            break;
                    }

                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            resultValue[0] = E13SalaryTableWithout1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWithout1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(3).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(3).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(5).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(5).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(7).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(7).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(9).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(9).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(11).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(11).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(13).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(13).getJsz_als_monatliche_zulage();
                            break;
                    }
                }
            } else {
                PayRateTableE14Calculations currentPayRateTableE14 = new PayRateTableE14Calculations();
                List<SalaryTable> E14SalaryTableWith1AAnd1B = null;
                List<SalaryTable> E14SalaryTableWithout1AAnd1B = null;


                if (currentPayRateTableE14.getCurrentPayRateWith1AAnd1BTable() == null && currentPayRateTableE14.getCurrentPayRateWithout1AAnd1BTable() == null) {
                    resultValue[0] = new BigDecimal(0);
                    resultValue[1] = new BigDecimal(0);

                    return resultValue;
                }
                if (currentPayRateTableE14.getCurrentPayRateWith1AAnd1BTable() != null) {
                    E14SalaryTableWith1AAnd1B = currentPayRateTableE14.getCurrentPayRateWith1AAnd1BTable();
                } else {
                    if (!contract.getPaylevel().equals("1")) {
                        resultValue[0] = new BigDecimal(0);
                        resultValue[1] = new BigDecimal(0);

                        return resultValue;
                    } else {
                        if (currentPayRateTableE14.getCurrentPayRateWithout1AAnd1BTable() != null) {
                            E14SalaryTableWithout1AAnd1B = currentPayRateTableE14.getCurrentPayRateWithout1AAnd1BTable();
                            if (contract.getVbl_status()) {
                                resultValue[0] = E14SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                                resultValue[1] = E14SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();

                            } else {
                                resultValue[0] = E14SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                                resultValue[1] = E14SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();

                            }
                        } else {
                            resultValue[0] = new BigDecimal(0);
                            resultValue[1] = new BigDecimal(0);
                            return resultValue;
                        }
                    }
                }
                if (currentPayRateTableE14.getCurrentPayRateWithout1AAnd1BTable() != null) {
                    E14SalaryTableWithout1AAnd1B = currentPayRateTableE14.getCurrentPayRateWithout1AAnd1BTable();
                } else {
                    if (contract.getPaylevel().equals("1")) {
                        resultValue[0] = new BigDecimal(0);
                        resultValue[1] = new BigDecimal(0);

                        return resultValue;
                    }
                }


                if (contract.getVbl_status()) {
                    switch (contract.getPaylevel()) {
                        case "1":
                            resultValue[0] = E14SalaryTableWithout1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWithout1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(4).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(4).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(6).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(6).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(8).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(8).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(10).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(10).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(12).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(12).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(14).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(14).getJsz_als_monatliche_zulage();
                            break;
                    }

                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            resultValue[0] = E14SalaryTableWithout1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWithout1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(3).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(3).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(5).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(5).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(7).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(7).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(9).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(9).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(11).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(11).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(13).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(13).getJsz_als_monatliche_zulage();
                            break;

                    }
                }
            }
        }

        return resultValue;
    }

    public BigDecimal[] getPayRateTableEntryForChosenDate(Contract contract, LocalDate chosenDate) {
        BigDecimal[] resultValue = new BigDecimal[2];
        Employee employee = employeeDataManager.getEmployee(contract.getId());
        if (employee.getStatus().equals("SHK")) {
            PayRateTableSHKCalculations currentPayRateTableSHK = new PayRateTableSHKCalculations();
            int SHKEntryId = currentPayRateTableSHK.getSHKPayRatesBasedOnChosenDate(chosenDate);
            SHKSalaryEntry chosenSHKEntry = shkSalaryTableManager.getSHKSalaryEntry(SHKEntryId);
            if (chosenSHKEntry == null) {
                resultValue[0] = new BigDecimal(0);
                resultValue[1] = new BigDecimal(0);
                return resultValue;
            }
            switch (contract.getShk_hourly_rate()) {
                case "SHK Basisvergütung" -> {
                    resultValue[0] = chosenSHKEntry.getBasePayRate().multiply(contract.getScope());
                    resultValue[1] = new BigDecimal(0);
                    return resultValue;
                }
                case "SHK erhöhter Stundensatz" -> {
                    resultValue[0] = chosenSHKEntry.getExtendedPayRate().multiply(contract.getScope());
                    resultValue[1] = new BigDecimal(0);
                    return resultValue;
                }
                case "WHK" -> {
                    resultValue[0] = chosenSHKEntry.getWHKPayRate().multiply(contract.getScope());
                    resultValue[1] = new BigDecimal(0);
                    return resultValue;
                }
            }
        } else {
            if (contract.getPaygrade().equals("E13")) {
                PayRateTableE13Calculations chosenDatePayRateTableE13 = new PayRateTableE13Calculations();
                List<SalaryTable> E13SalaryTableWith1AAnd1B = null;
                List<SalaryTable> E13SalaryTableWithout1AAnd1B = null;



                if (chosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(chosenDate) == null && chosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate) == null) {
                    resultValue[0] = new BigDecimal(0);
                    resultValue[1] = new BigDecimal(0);
                    return resultValue;
                }
                if (chosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(chosenDate) != null) {
                    E13SalaryTableWith1AAnd1B = chosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(chosenDate);
                } else {
                    if (!contract.getPaylevel().equals("1")) {
                        resultValue[0] = new BigDecimal(0);
                        resultValue[1] = new BigDecimal(0);
                        return resultValue;
                    } else {
                        if (chosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate) != null) {
                            E13SalaryTableWithout1AAnd1B = chosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate);
                            if (contract.getVbl_status()) {
                                resultValue[0] = E13SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                                resultValue[1] = E13SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            } else {
                                resultValue[0] = E13SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());;
                                resultValue[1] = E13SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            }
                        } else {
                            resultValue[0] = new BigDecimal(0);
                            resultValue[1] = new BigDecimal(0);
                            return resultValue;
                        }
                    }
                }
                if (chosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate) != null) {
                    E13SalaryTableWithout1AAnd1B = chosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate);
                } else {
                    if (contract.getPaylevel().equals("1")) {
                        resultValue[0] = new BigDecimal(0);
                        resultValue[1] = new BigDecimal(0);
                        return resultValue;
                    }
                }

                if (contract.getVbl_status()) {
                    switch (contract.getPaylevel()) {
                        case "1":
                            resultValue[0] = E13SalaryTableWithout1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWithout1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(4).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(4).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(6).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(6).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(8).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(8).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(10).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(10).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(12).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(12).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(14).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(14).getJsz_als_monatliche_zulage();
                            break;
                    }

                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            resultValue[0] = E13SalaryTableWithout1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWithout1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(3).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(3).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(5).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(5).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(7).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(7).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(9).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(9).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(11).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(11).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(13).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(13).getJsz_als_monatliche_zulage();
                            break;
                    }
                }

            } else {
                PayRateTableE14Calculations choosenDatePayRateTableE14 = new PayRateTableE14Calculations();
                List<SalaryTable> E14SalaryTableWith1AAnd1B = null;
                List<SalaryTable> E14SalaryTableWithout1AAnd1B = null;


                if (choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(chosenDate) == null && choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate) == null) {
                    resultValue[0] = new BigDecimal(0);
                    resultValue[1] = new BigDecimal(0);
                    return resultValue;
                }
                if (choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(chosenDate) != null) {
                    E14SalaryTableWith1AAnd1B = choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(chosenDate);
                } else {
                    if (!contract.getPaylevel().equals("1")) {
                        resultValue[0] = new BigDecimal(0);
                        resultValue[1] = new BigDecimal(0);
                        return resultValue;
                    } else {
                        if (choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate) != null) {
                            E14SalaryTableWithout1AAnd1B = choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate);
                            if (contract.getVbl_status()) {
                                resultValue[0] = E14SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                                resultValue[1] = E14SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            } else {
                                resultValue[0] = E14SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                                resultValue[1] = E14SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            }
                        } else {
                            resultValue[0] = new BigDecimal(0);
                            resultValue[1] = new BigDecimal(0);
                            return resultValue;
                        }
                    }
                }
                if (choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate) != null) {
                    E14SalaryTableWithout1AAnd1B = choosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate);
                } else {
                    if (contract.getPaylevel().equals("1")) {
                        resultValue[0] = new BigDecimal(0);
                        resultValue[1] = new BigDecimal(0);
                        return resultValue;
                    }
                }

                if (contract.getVbl_status()) {
                    switch (contract.getPaylevel()) {
                        case "1":
                            resultValue[0] = E14SalaryTableWithout1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWithout1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(4).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(4).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(6).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(6).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(8).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(8).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(10).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(10).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(12).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(12).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(14).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(14).getJsz_als_monatliche_zulage();
                            break;
                    }

                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            resultValue[0] = E14SalaryTableWithout1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWithout1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(3).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(3).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(5).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(5).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(7).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(7).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(9).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(9).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(11).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(11).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(13).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(13).getJsz_als_monatliche_zulage();
                            break;

                    }
                }
            }
        }
        return resultValue;
    }

    public LocalDate getActivePayRateTableBasedOnGivenDate(Contract contract, LocalDate chosenDate) {
        Employee employee = employeeDataManager.getEmployee(contract.getId());
        if (employee.getStatus().equals("SHK")) {
            PayRateTableSHKCalculations currentPayRateTableSHK = new PayRateTableSHKCalculations();
            int determinedCurrentID = currentPayRateTableSHK.getSHKPayRatesBasedOnChosenDate(chosenDate);
            Date determinedDate = shkSalaryTableManager.getSHKSalaryEntry(determinedCurrentID).getValidationDate();
            return LocalDate.ofInstant(determinedDate.toInstant(), ZoneId.systemDefault());
        } else {
            PayRateTableE13Calculations chosenDatePayRateTableE13 = new PayRateTableE13Calculations();
            PayRateTableE14Calculations chosenDatePayRateTableE14 = new PayRateTableE14Calculations();
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
    }
}
