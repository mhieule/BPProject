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

    /**
     * This method determines the current pay rate table entry in the currently active pay rate table specified by the parameters set in the contract that the method is given.
     *
     * @param contract The contract object of the employee that we want the pay rate table entry for.
     * @return A BigDecimal Array containing the current € Value of Bonus and Regular cost in the active pay rate table.
     */
    public BigDecimal[] getCurrentPayRateTableEntry(Contract contract) {
        BigDecimal[] resultValue = new BigDecimal[2];
        Employee employee = employeeDataManager.getEmployee(contract.getId());
        if (employee.getStatus().equals("SHK")) {
            PayRateTableSHKCalculations currentPayRateTableSHK = new PayRateTableSHKCalculations();
            int currentSHKEntryId = currentPayRateTableSHK.getCurrentSHKPayRates();
            SHKSalaryEntry currentEntry = shkSalaryTableManager.getSHKSalaryEntry(currentSHKEntryId);
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
                    System.out.println(contract.getPaylevel());
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
                            resultValue[0] = E13SalaryTableWithout1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWithout1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(4).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(4).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(6).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(6).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(8).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(8).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(10).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(10).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(12).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(12).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(14).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(14).getJsz_als_monatliche_zulage();
                            break;
                    }

                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            resultValue[0] = E13SalaryTableWithout1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWithout1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(3).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(3).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(5).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(5).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(7).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(7).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(9).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(9).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(11).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E13SalaryTableWith1AAnd1B.get(11).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E13SalaryTableWith1AAnd1B.get(13).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
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
                                resultValue[0] = E14SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                                ;
                                resultValue[1] = E14SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();

                            } else {
                                resultValue[0] = E14SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                                ;
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
                            resultValue[0] = E14SalaryTableWithout1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWithout1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(4).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(4).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(6).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(6).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(8).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(8).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(10).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(10).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(12).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(12).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(14).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(14).getJsz_als_monatliche_zulage();
                            break;
                    }

                } else {
                    switch (contract.getPaylevel()) {
                        case "1":
                            resultValue[0] = E14SalaryTableWithout1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWithout1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1A":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(1).getJsz_als_monatliche_zulage();
                            break;
                        case "1B":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(3).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(3).getJsz_als_monatliche_zulage();
                            break;
                        case "2":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(5).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(5).getJsz_als_monatliche_zulage();
                            break;
                        case "3":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(7).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(7).getJsz_als_monatliche_zulage();
                            break;
                        case "4":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(9).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(9).getJsz_als_monatliche_zulage();
                            break;
                        case "5":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(11).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(11).getJsz_als_monatliche_zulage();
                            break;
                        case "6":
                            resultValue[0] = E14SalaryTableWith1AAnd1B.get(13).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                            ;
                            resultValue[1] = E14SalaryTableWith1AAnd1B.get(13).getJsz_als_monatliche_zulage();
                            break;

                    }
                }
            }
        }

        return resultValue;
    }

    /**
     * This method determines the pay rate table entry in the active pay rate table specified by the chosenDate and the parameters set in the contract that the method is given.
     * It projects the salary into the future/past.
     *
     * @param contract   The contract object of the employee that we want the pay rate table entry for.
     * @param chosenDate The date by which the active pay rate table is determined.
     * @return A BigDecimal Array containing the current € Value of Bonus and Regular cost in the active pay rate table.
     */
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
                                resultValue[0] = E13SalaryTableWith1AAnd1B.get(2).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                                ;
                                resultValue[1] = E13SalaryTableWith1AAnd1B.get(2).getJsz_als_monatliche_zulage();
                            } else {
                                resultValue[0] = E13SalaryTableWith1AAnd1B.get(1).getMtl_kosten_ohne_jsz().multiply(contract.getScope());
                                ;
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

    /**
     * Retrieves the active pay rate table based on a given date for a specific contract.
     * If the employee is a student employee ("SHK"), the pay rate table is determined using the
     * PayRateTableSHKCalculations class. If the employee is not a student employee, the pay rate table
     * is determined based on the employee's pay grade and pay level using the PayRateTableE13Calculations
     * and PayRateTableE14Calculations classes. The chosen date is used to determine the effective date
     * of the active pay rate table. The method returns the effective date as a LocalDate object.
     *
     * @param contract   The contract for which to retrieve the active pay rate table.
     * @param chosenDate The chosen date to use for determining the active pay rate table.
     * @return A LocalDate object representing the effective date of the active pay rate table.
     */
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

    /**
     * Returns an array of BigDecimal percentages based on the pay grade and level of the given contract
     * and the chosen date. If the pay grade is "E13" or "E14" and the VBL status is true, the returned
     * array will have 9 elements, otherwise it will have 8.
     *
     * @param contract   The contract for which to get the percentages
     * @param chosenDate The chosen date to base the pay rate table on
     * @return An array of BigDecimal percentages based on the contract and chosen date
     */
    public BigDecimal[] getPercentagesOfPayRateTable(Contract contract, LocalDate chosenDate) {
        BigDecimal[] percentages = null;

        if (contract.getPaygrade().equals("E13")) {
            PayRateTableE13Calculations chosenDatePayRateTableE13 = new PayRateTableE13Calculations();
            List<SalaryTable> salaryTables;
            if (contract.getPaylevel().equals("1")) {
                salaryTables = chosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate);
            } else {
                salaryTables = chosenDatePayRateTableE13.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(chosenDate);
            }
            if (contract.getVbl_status()) {
                percentages = new BigDecimal[9];
                percentages[0] = salaryTables.get(0).getAv_ag_anteil_lfd_entgelt();
                percentages[1] = salaryTables.get(0).getKv_ag_anteil_lfd_entgelt();
                percentages[2] = salaryTables.get(0).getZusbei_af_lfd_entgelt();
                percentages[3] = salaryTables.get(0).getPv_ag_anteil_lfd_entgelt();
                percentages[4] = salaryTables.get(0).getRv_ag_anteil_lfd_entgelt();
                percentages[5] = salaryTables.get(0).getSv_umlage_u2();
                percentages[6] = salaryTables.get(0).getSteuern_ag();
                percentages[7] = salaryTables.get(0).getZv_Sanierungsbeitrag();
                percentages[8] = salaryTables.get(0).getZv_umlage_allgemein();
            } else {
                percentages = new BigDecimal[8];
                percentages[0] = salaryTables.get(0).getAv_ag_anteil_lfd_entgelt();
                percentages[1] = salaryTables.get(0).getKv_ag_anteil_lfd_entgelt();
                percentages[2] = salaryTables.get(0).getZusbei_af_lfd_entgelt();
                percentages[3] = salaryTables.get(0).getPv_ag_anteil_lfd_entgelt();
                percentages[4] = salaryTables.get(0).getRv_ag_anteil_lfd_entgelt();
                percentages[5] = salaryTables.get(0).getSv_umlage_u2();
                percentages[6] = salaryTables.get(0).getSteuern_ag();
                percentages[7] = salaryTables.get(0).getVbl_wiss_4perc_ag_buchung();
            }
        } else if (contract.getPaygrade().equals("E14")) {
            PayRateTableE14Calculations chosenDatePayRateTableE14 = new PayRateTableE14Calculations();
            List<SalaryTable> salaryTables;
            if (contract.getPaylevel().equals("1")) {
                salaryTables = chosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(chosenDate);
            } else {
                salaryTables = chosenDatePayRateTableE14.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(chosenDate);
            }
            if (contract.getVbl_status()) {
                percentages = new BigDecimal[9];
                percentages[0] = salaryTables.get(0).getAv_ag_anteil_lfd_entgelt();
                percentages[1] = salaryTables.get(0).getKv_ag_anteil_lfd_entgelt();
                percentages[2] = salaryTables.get(0).getZusbei_af_lfd_entgelt();
                percentages[3] = salaryTables.get(0).getPv_ag_anteil_lfd_entgelt();
                percentages[4] = salaryTables.get(0).getRv_ag_anteil_lfd_entgelt();
                percentages[5] = salaryTables.get(0).getSv_umlage_u2();
                percentages[6] = salaryTables.get(0).getSteuern_ag();
                percentages[7] = salaryTables.get(0).getZv_Sanierungsbeitrag();
                percentages[8] = salaryTables.get(0).getZv_umlage_allgemein();
            } else {
                percentages = new BigDecimal[8];
                percentages[0] = salaryTables.get(0).getAv_ag_anteil_lfd_entgelt();
                percentages[1] = salaryTables.get(0).getKv_ag_anteil_lfd_entgelt();
                percentages[2] = salaryTables.get(0).getZusbei_af_lfd_entgelt();
                percentages[3] = salaryTables.get(0).getPv_ag_anteil_lfd_entgelt();
                percentages[4] = salaryTables.get(0).getRv_ag_anteil_lfd_entgelt();
                percentages[5] = salaryTables.get(0).getSv_umlage_u2();
                percentages[6] = salaryTables.get(0).getSteuern_ag();
                percentages[7] = salaryTables.get(0).getVbl_wiss_4perc_ag_buchung();
            }

        }
        return percentages;
    }
}
