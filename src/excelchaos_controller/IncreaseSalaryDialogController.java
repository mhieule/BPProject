package excelchaos_controller;

import excelchaos_model.calculations.SalaryCalculation;
import excelchaos_model.constants.IncreaseSalaryOption;
import excelchaos_model.database.*;
import excelchaos_model.datecalculations.PayRateTableE13Calculations;
import excelchaos_model.datecalculations.PayRateTableE14Calculations;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.IncreaseSalaryDialogView;
import excelchaos_view.SalaryListView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class IncreaseSalaryDialogController implements ActionListener {
    private MainFrameController frameController;
    private IncreaseSalaryDialogView increaseSalaryDialogView;

    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();
    private ContractDataManager contractDataManager = new ContractDataManager();
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private boolean isProjectedColumnOpened;
    private ArrayList<Integer> employeeIDList;
    private SalaryCalculation salaryCalculation = new SalaryCalculation();
    private String increaseSalaryTab = "Gehaltserhöhung durchführen";

    private boolean isProjectionColumnOpenedBefore;


    public IncreaseSalaryDialogController(MainFrameController frameController, ArrayList<Integer> employeeIDList) {
        this.frameController = frameController;
        this.isProjectedColumnOpened = false;
        this.employeeIDList = employeeIDList;
        this.isProjectionColumnOpenedBefore=false;
        increaseSalaryDialogView = new IncreaseSalaryDialogView();
        increaseSalaryDialogView.init(employeeIDList);
        increaseSalaryDialogView.setActionListener(this);

    }
    public void showSalaryIncreaseView(){
        if (frameController.getTabs().indexOfTab(increaseSalaryTab) == -1) {
            frameController.getTabs().addTab(increaseSalaryTab, increaseSalaryDialogView);
            frameController.getTabs().setSelectedIndex(frameController.getTabs().indexOfTab(increaseSalaryTab));
        } else
            frameController.getTabs().setSelectedIndex(frameController.getTabs().indexOfTab(increaseSalaryTab));
    }


    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == increaseSalaryDialogView.getCloseButton()) {
            //increaseSalaryDialogView.dispose();
        } else if (e.getSource() == increaseSalaryDialogView.getProjectButton()) {
            //Check if the inputs are sufficient, if not throw error message
            boolean inputVerified = isInputVerified();
            if (inputVerified && !increaseSalaryDialogView.isProjectedColumnOpened()) {
                if (!isProjectionColumnOpenedBefore) {
                    increaseSalaryDialogView.addProjectionColumn();
                    isProjectionColumnOpenedBefore = true;
                } else {
                }

                if (increaseSalaryDialogView.getAbsoluteRadioButton().isSelected()) {
                    //increaseSalaryDialogView.addProjectionColumn(IncreaseSalaryOption.ABSOLUTE, projectedSalaryBeforeIncrease(), /*projectedSalaryAfterIncrease(IncreaseSalaryOption.ABSOLUTE)*/ projectedSalaryBeforeIncrease());
                    increaseSalaryDialogView.setProjectionView(IncreaseSalaryOption.ABSOLUTE, projectedSalaryBeforeIncrease(), projectedSalaryAfterIncrease(IncreaseSalaryOption.ABSOLUTE));
                } else if (increaseSalaryDialogView.getRelativeRadioButton().isSelected()) {
                    //increaseSalaryDialogView.addProjectionColumn(IncreaseSalaryOption.RELATIVE, projectedSalaryBeforeIncrease(), projectedSalaryAfterIncrease(IncreaseSalaryOption.RELATIVE));
                    increaseSalaryDialogView.setProjectionView(IncreaseSalaryOption.RELATIVE, projectedSalaryBeforeIncrease(), projectedSalaryAfterIncrease(IncreaseSalaryOption.RELATIVE));
                } else if (increaseSalaryDialogView.getMixedRadioButton().isSelected()) {
                    increaseSalaryDialogView.setProjectionView(IncreaseSalaryOption.MIXED, projectedSalaryBeforeIncrease(), projectedSalaryAfterIncrease(IncreaseSalaryOption.MIXED));
                }
            }
            //Instruct the view to add a new column to the current table which contains projected salary after increase


        } else if (e.getSource() == increaseSalaryDialogView.getOkayButton()) {

            isInputVerified();
            if (increaseSalaryDialogView.getAbsoluteRadioButton().isSelected()) {
                writeDataToDB(IncreaseSalaryOption.ABSOLUTE);
            } else if (increaseSalaryDialogView.getRelativeRadioButton().isSelected()) {
                writeDataToDB(IncreaseSalaryOption.RELATIVE);
            } else if (increaseSalaryDialogView.getMixedRadioButton().isSelected()) {
                writeDataToDB(IncreaseSalaryOption.MIXED);
            }


        } else if (e.getSource() == increaseSalaryDialogView.getAbsoluteRadioButton()) {
            increaseSalaryDialogView.setAbsoluteView();
            if (increaseSalaryDialogView.isProjectedColumnOpened()) {
                increaseSalaryDialogView.setProjectionColumnInvisible();
                increaseSalaryDialogView.toggleVisibility();
            }

        } else if (e.getSource() == increaseSalaryDialogView.getRelativeRadioButton()) {
            increaseSalaryDialogView.setRelativeView();
            if (increaseSalaryDialogView.isProjectedColumnOpened()) {
                increaseSalaryDialogView.setProjectionColumnInvisible();
                increaseSalaryDialogView.toggleVisibility();
            }

        } else if (e.getSource() == increaseSalaryDialogView.getMixedRadioButton()) {
            increaseSalaryDialogView.setMixedView();
            if (increaseSalaryDialogView.isProjectedColumnOpened()) {
                increaseSalaryDialogView.setProjectionColumnInvisible();
                increaseSalaryDialogView.toggleVisibility();
            }

        }
    }

    private boolean isInputVerified(){
        boolean result = false;
        if (!increaseSalaryDialogView.getSalaryIncreaseRadioButton().isSelected() && !increaseSalaryDialogView.getBonusRadioButton().isSelected()) {
            increaseSalaryDialogView.noIncreaseTypeSelected();
            return false;
        } else if (!increaseSalaryDialogView.getAbsoluteRadioButton().isSelected() && !increaseSalaryDialogView.getRelativeRadioButton().isSelected() && !increaseSalaryDialogView.getMixedRadioButton().isSelected()) {
            increaseSalaryDialogView.noIncreaseOptionSelected();
            return false;
        } /*else if (increaseSalaryDialogView.getMixedRadioButton().isSelected() && !increaseSalaryDialogView.getMixedMinRadioButton().isSelected() && !increaseSalaryDialogView.getMixedMaxRadioButton().isSelected()) {
            increaseSalaryDialogView.noMinMaxSelected();
            return false;
        } */else if (increaseSalaryDialogView.getStartDate().getDate()==null){
            increaseSalaryDialogView.noStartDateSelected();
            return false;
        } else {
            if (increaseSalaryDialogView.getAbsoluteRadioButton().isSelected()) {
                try{
                    double number = Double.parseDouble(increaseSalaryDialogView.getTextFieldAbsolute().getText());
                }
                catch (NumberFormatException ex){
                    increaseSalaryDialogView.inputNotANumber();
                    return false;
                }
            } else if (increaseSalaryDialogView.getRelativeRadioButton().isSelected()) {
                try{
                    double number = Double.parseDouble(increaseSalaryDialogView.getTextFieldRelative().getText());
                }
                catch (NumberFormatException ex){
                    increaseSalaryDialogView.inputNotANumber();
                    return false;
                }

            } else if (increaseSalaryDialogView.getMixedRadioButton().isSelected()) {
                try{
                    double number = Double.parseDouble(increaseSalaryDialogView.getMixedAbsolute().getText());
                    double number1 = Double.parseDouble(increaseSalaryDialogView.getMixedRelative().getText());
                }
                catch (NumberFormatException ex){
                    increaseSalaryDialogView.inputNotANumber();
                    return false;
                }

            }  else {
                return true;
            }
        }

        return true;
    }
    //TODO migrate to model
    public BigDecimal[] projectedSalaryBeforeIncrease(){
        BigDecimal[] result = new BigDecimal[employeeIDList.size()];
        LocalDate startLocalDate = increaseSalaryDialogView.getStartDate().getDate();
        Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        int currentIndex=0;
        for(Integer employeeID : employeeIDList){
            result[currentIndex] = salaryCalculation.projectSalaryToGivenMonth(employeeID, startDate);
            currentIndex++;
        }
        return result;
    }

    public BigDecimal[] projectedSalaryAfterIncrease(IncreaseSalaryOption option){
        BigDecimal[] result = new BigDecimal[employeeIDList.size()];
        int currentIndex=0;


        for (Integer employeeID : employeeIDList) {
            //Information about the employee (Payrate, Paylevel)
            Contract employeeContract = contractDataManager.getContract(employeeID);
            String paygrade = employeeContract.getPaygrade();
            String paylevel = employeeContract.getPaylevel();

            //Start date transformation
            LocalDate startLocalDate = increaseSalaryDialogView.getStartDate().getDate();
            Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            //
            PayRateTableE13Calculations currentPayRateTableE13 = new PayRateTableE13Calculations();
            PayRateTableE14Calculations currentPayRateTableE14 = new PayRateTableE14Calculations();
            BigDecimal procent = new BigDecimal(0);
            if (paygrade.equals("E13")) {
                if (paylevel.equals("1A") || paylevel.equals("1B")) {
                    SalaryTable temp = currentPayRateTableE13.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(startLocalDate).get(0);
                    BigDecimal av_ag = temp.getAv_ag_anteil_lfd_entgelt();
                    BigDecimal kv_ag = temp.getKv_ag_anteil_lfd_entgelt();
                    BigDecimal ZusBei = temp.getZusbei_af_lfd_entgelt();
                    BigDecimal pv_ag = temp.getPv_ag_anteil_lfd_entgelt();
                    BigDecimal rv_ag = temp.getRv_ag_anteil_lfd_entgelt();
                    BigDecimal sv_umlage = temp.getSv_umlage_u2();
                    BigDecimal steuernAG = temp.getSteuern_ag();
                    BigDecimal ZV_Sanierung = temp.getZv_Sanierungsbeitrag();
                    BigDecimal ZV_Umlage = temp.getZv_umlage_allgemein();
                    BigDecimal VBL = temp.getVbl_wiss_4perc_ag_buchung();
                    BigDecimal[] bigDecimals = {av_ag, kv_ag, ZusBei, pv_ag, rv_ag, sv_umlage, steuernAG, ZV_Sanierung, ZV_Umlage, VBL};
                    if (employeeContract.getVbl_status()) {
                        for (BigDecimal bigDecimal : bigDecimals) {
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(VBL);
                    } else {
                        for (BigDecimal bigDecimal : bigDecimals) {
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(ZV_Umlage).subtract(ZV_Sanierung);
                    }
                } else {
                    SalaryTable temp = currentPayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(startLocalDate).get(0);
                    BigDecimal av_ag = temp.getAv_ag_anteil_lfd_entgelt();
                    BigDecimal kv_ag = temp.getKv_ag_anteil_lfd_entgelt();
                    BigDecimal ZusBei = temp.getZusbei_af_lfd_entgelt();
                    BigDecimal pv_ag = temp.getPv_ag_anteil_lfd_entgelt();
                    BigDecimal rv_ag = temp.getRv_ag_anteil_lfd_entgelt();
                    BigDecimal sv_umlage = temp.getSv_umlage_u2();
                    BigDecimal steuernAG = temp.getSteuern_ag();
                    BigDecimal ZV_Sanierung = temp.getZv_Sanierungsbeitrag();
                    BigDecimal ZV_Umlage = temp.getZv_umlage_allgemein();
                    BigDecimal VBL = temp.getVbl_wiss_4perc_ag_buchung();
                    BigDecimal[] bigDecimals = {av_ag, kv_ag, ZusBei, pv_ag, rv_ag, sv_umlage, steuernAG, ZV_Sanierung, ZV_Umlage, VBL};
                    if (employeeContract.getVbl_status()) {
                        for (BigDecimal bigDecimal : bigDecimals) {
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(VBL);
                    } else {
                        for (BigDecimal bigDecimal : bigDecimals) {
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(ZV_Umlage).subtract(ZV_Sanierung);
                    }
                }
            } else if (paygrade.equals("E14")) {
                if (paylevel.equals("1A") || paylevel.equals("1B")) {
                    SalaryTable temp = currentPayRateTableE14.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(startLocalDate).get(0);
                    BigDecimal av_ag = temp.getAv_ag_anteil_lfd_entgelt();
                    BigDecimal kv_ag = temp.getKv_ag_anteil_lfd_entgelt();
                    BigDecimal ZusBei = temp.getZusbei_af_lfd_entgelt();
                    BigDecimal pv_ag = temp.getPv_ag_anteil_lfd_entgelt();
                    BigDecimal rv_ag = temp.getRv_ag_anteil_lfd_entgelt();
                    BigDecimal sv_umlage = temp.getSv_umlage_u2();
                    BigDecimal steuernAG = temp.getSteuern_ag();
                    BigDecimal ZV_Sanierung = temp.getZv_Sanierungsbeitrag();
                    BigDecimal ZV_Umlage = temp.getZv_umlage_allgemein();
                    BigDecimal VBL = temp.getVbl_wiss_4perc_ag_buchung();
                    BigDecimal[] bigDecimals = {av_ag, kv_ag, ZusBei, pv_ag, rv_ag, sv_umlage, steuernAG, ZV_Sanierung, ZV_Umlage, VBL};
                    if (employeeContract.getVbl_status()) {
                        for (BigDecimal bigDecimal : bigDecimals) {
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(VBL);
                    } else {
                        for (BigDecimal bigDecimal : bigDecimals) {
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(ZV_Umlage).subtract(ZV_Sanierung);
                    }
                } else {
                    SalaryTable temp = currentPayRateTableE14.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(startLocalDate).get(0);
                    BigDecimal av_ag = temp.getAv_ag_anteil_lfd_entgelt();
                    BigDecimal kv_ag = temp.getKv_ag_anteil_lfd_entgelt();
                    BigDecimal ZusBei = temp.getZusbei_af_lfd_entgelt();
                    BigDecimal pv_ag = temp.getPv_ag_anteil_lfd_entgelt();
                    BigDecimal rv_ag = temp.getRv_ag_anteil_lfd_entgelt();
                    BigDecimal sv_umlage = temp.getSv_umlage_u2();
                    BigDecimal steuernAG = temp.getSteuern_ag();
                    BigDecimal ZV_Sanierung = temp.getZv_Sanierungsbeitrag();
                    BigDecimal ZV_Umlage = temp.getZv_umlage_allgemein();
                    BigDecimal VBL = temp.getVbl_wiss_4perc_ag_buchung();
                    BigDecimal[] bigDecimals = {av_ag, kv_ag, ZusBei, pv_ag, rv_ag, sv_umlage, steuernAG, ZV_Sanierung, ZV_Umlage, VBL};
                    if (employeeContract.getVbl_status()) {
                        for (BigDecimal bigDecimal : bigDecimals) {
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(VBL);
                    } else {
                        for (BigDecimal bigDecimal : bigDecimals) {
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(ZV_Umlage).subtract(ZV_Sanierung);
                    }
                }
            } else {
                System.err.println("jfiofjoiwefjeorifjeorifjeo");
            }

            //Salary Increase Type (normal or bonus)
            boolean isBonus = (increaseSalaryDialogView.getBonusRadioButton().isSelected()) ? true : false;

            //New salary calculation
            BigDecimal projectedSalary = salaryCalculation.projectSalaryToGivenMonth(employeeID, startDate);
            BigDecimal finalSalary = new BigDecimal(0);

            BigDecimal absoluteInputBigDecimal = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getTextFieldAbsolute().getText());
            BigDecimal modifiedAbsoluteInputBigDecimal = absoluteInputBigDecimal.multiply(procent).divide(new BigDecimal(100));
            BigDecimal absoluteFinal = modifiedAbsoluteInputBigDecimal.add(projectedSalary).add(absoluteInputBigDecimal);

            BigDecimal relativeInputBigDecimal = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getTextFieldRelative().getText());
            BigDecimal modifiedRelativeInputBigDecimal = projectedSalary.multiply(relativeInputBigDecimal).divide(new BigDecimal(100));
            BigDecimal relativeFinal = modifiedRelativeInputBigDecimal.add(projectedSalary);

            if (option == IncreaseSalaryOption.ABSOLUTE) {
                //finalSalary = projectedSalary.add(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getTextFieldAbsolute().getText())); //TODO Eingabevalidierung
                finalSalary = absoluteFinal;
            } else if (option == IncreaseSalaryOption.RELATIVE) {
                //finalSalary = projectedSalary.add(StringAndBigDecimalFormatter.formatStringToPercentageValueForScope(increaseSalaryDialogView.getTextFieldRelative().getText()).multiply(projectedSalary.divide(new BigDecimal(100))));
                finalSalary = relativeFinal;
            } else {
                BigDecimal mixedAbsoluteInputBigDecimal = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getMixedAbsolute().getText());
                BigDecimal modifiedMixedAbsoluteInputBigDecimal = mixedAbsoluteInputBigDecimal.multiply(procent).divide(new BigDecimal(100));
                BigDecimal mixedAbsoluteFinal = modifiedMixedAbsoluteInputBigDecimal.add(projectedSalary).add(mixedAbsoluteInputBigDecimal);

                BigDecimal mixedRelativeInputBigDecimal = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getMixedRelative().getText());
                BigDecimal modifiedMixedRelativeInputBigDecimal = projectedSalary.multiply(mixedRelativeInputBigDecimal).divide(new BigDecimal(100));
                BigDecimal mixedRelativeFinal = modifiedMixedRelativeInputBigDecimal.add(projectedSalary);

                finalSalary = (mixedAbsoluteFinal.compareTo(mixedRelativeFinal) < 0) ? mixedRelativeFinal : mixedAbsoluteFinal;

                /*BigDecimal finalAbsoluteSalary = projectedSalary.add(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getMixedAbsolute().getText()));
                BigDecimal finalRelativeSalary = projectedSalary.add(StringAndBigDecimalFormatter.formatStringToPercentageValueForScope(increaseSalaryDialogView.getMixedRelative().getText()).multiply(projectedSalary.divide(new BigDecimal(100))));
                if (option == IncreaseSalaryOption.MIXED_MIN) {
                    finalSalary = (finalAbsoluteSalary.compareTo(finalRelativeSalary) < 0) ? finalAbsoluteSalary : finalRelativeSalary; //TODO Könnte auch falsch sein
                } else if (option == IncreaseSalaryOption.MIXED_MAX) {
                    finalSalary = (finalAbsoluteSalary.compareTo(finalRelativeSalary) > 0) ? finalAbsoluteSalary : finalRelativeSalary;
                }
                 */


            }

            result[currentIndex]=finalSalary;
            currentIndex=currentIndex+1;
        }

        return result;
    }

    //TODO migrate to model
    public void writeDataToDB(IncreaseSalaryOption option) {
        for (Integer employeeID : employeeIDList) {
            //Information about the employee (Payrate, Paylevel)
            Contract employeeContract = contractDataManager.getContract(employeeID);
            String paygrade = employeeContract.getPaygrade();
            String paylevel = employeeContract.getPaylevel();

            //Start date transformation
            LocalDate startLocalDate = increaseSalaryDialogView.getStartDate().getDate();
            Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            //
            PayRateTableE13Calculations currentPayRateTableE13=new PayRateTableE13Calculations();
            PayRateTableE14Calculations currentPayRateTableE14=new PayRateTableE14Calculations();
            BigDecimal procent = new BigDecimal(0);
            if(paygrade.equals("E13")){
                if(paylevel.equals("1A") || paylevel.equals("1B")){
                    SalaryTable temp = currentPayRateTableE13.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(startLocalDate).get(0);
                    BigDecimal av_ag = temp.getAv_ag_anteil_lfd_entgelt();
                    BigDecimal kv_ag = temp.getKv_ag_anteil_lfd_entgelt();
                    BigDecimal ZusBei = temp.getZusbei_af_lfd_entgelt();
                    BigDecimal pv_ag = temp.getPv_ag_anteil_lfd_entgelt();
                    BigDecimal rv_ag = temp.getRv_ag_anteil_lfd_entgelt();
                    BigDecimal sv_umlage = temp.getSv_umlage_u2();
                    BigDecimal steuernAG = temp.getSteuern_ag();
                    BigDecimal ZV_Sanierung = temp.getZv_Sanierungsbeitrag();
                    BigDecimal ZV_Umlage = temp.getZv_umlage_allgemein();
                    BigDecimal VBL = temp.getVbl_wiss_4perc_ag_buchung();
                    BigDecimal[] bigDecimals = {av_ag, kv_ag, ZusBei, pv_ag, rv_ag, sv_umlage, steuernAG, ZV_Sanierung, ZV_Umlage, VBL};
                    if(employeeContract.getVbl_status()){
                        for(BigDecimal bigDecimal:bigDecimals){
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(VBL);
                    } else {
                        for(BigDecimal bigDecimal:bigDecimals){
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(ZV_Umlage).subtract(ZV_Sanierung);
                    }
                } else {
                    SalaryTable temp = currentPayRateTableE13.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(startLocalDate).get(0);
                    BigDecimal av_ag = temp.getAv_ag_anteil_lfd_entgelt();
                    BigDecimal kv_ag = temp.getKv_ag_anteil_lfd_entgelt();
                    BigDecimal ZusBei = temp.getZusbei_af_lfd_entgelt();
                    BigDecimal pv_ag = temp.getPv_ag_anteil_lfd_entgelt();
                    BigDecimal rv_ag = temp.getRv_ag_anteil_lfd_entgelt();
                    BigDecimal sv_umlage = temp.getSv_umlage_u2();
                    BigDecimal steuernAG = temp.getSteuern_ag();
                    BigDecimal ZV_Sanierung = temp.getZv_Sanierungsbeitrag();
                    BigDecimal ZV_Umlage = temp.getZv_umlage_allgemein();
                    BigDecimal VBL = temp.getVbl_wiss_4perc_ag_buchung();
                    BigDecimal[] bigDecimals = {av_ag, kv_ag, ZusBei, pv_ag, rv_ag, sv_umlage, steuernAG, ZV_Sanierung, ZV_Umlage, VBL};
                    if(employeeContract.getVbl_status()){
                        for(BigDecimal bigDecimal:bigDecimals){
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(VBL);
                    } else {
                        for(BigDecimal bigDecimal:bigDecimals){
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(ZV_Umlage).subtract(ZV_Sanierung);
                    }
                }
            } else if (paygrade.equals("E14")){
                if(paylevel.equals("1A") || paylevel.equals("1B")){
                    SalaryTable temp = currentPayRateTableE14.getPayRateTableBasedOnChosenDateWith1AAnd1BTable(startLocalDate).get(0);
                    BigDecimal av_ag = temp.getAv_ag_anteil_lfd_entgelt();
                    BigDecimal kv_ag = temp.getKv_ag_anteil_lfd_entgelt();
                    BigDecimal ZusBei = temp.getZusbei_af_lfd_entgelt();
                    BigDecimal pv_ag = temp.getPv_ag_anteil_lfd_entgelt();
                    BigDecimal rv_ag = temp.getRv_ag_anteil_lfd_entgelt();
                    BigDecimal sv_umlage = temp.getSv_umlage_u2();
                    BigDecimal steuernAG = temp.getSteuern_ag();
                    BigDecimal ZV_Sanierung = temp.getZv_Sanierungsbeitrag();
                    BigDecimal ZV_Umlage = temp.getZv_umlage_allgemein();
                    BigDecimal VBL = temp.getVbl_wiss_4perc_ag_buchung();
                    BigDecimal[] bigDecimals = {av_ag, kv_ag, ZusBei, pv_ag, rv_ag, sv_umlage, steuernAG, ZV_Sanierung, ZV_Umlage, VBL};
                    if(employeeContract.getVbl_status()){
                        for(BigDecimal bigDecimal:bigDecimals){
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(VBL);
                    } else {
                        for(BigDecimal bigDecimal:bigDecimals){
                            procent = procent.add(bigDecimal);
                        }
                        procent=procent.subtract(ZV_Umlage).subtract(ZV_Sanierung);
                    }
                } else {
                    SalaryTable temp = currentPayRateTableE14.getPayRateTableBasedOnChosenDateWithout1AAnd1BTable(startLocalDate).get(0);
                    BigDecimal av_ag = temp.getAv_ag_anteil_lfd_entgelt();
                    BigDecimal kv_ag = temp.getKv_ag_anteil_lfd_entgelt();
                    BigDecimal ZusBei = temp.getZusbei_af_lfd_entgelt();
                    BigDecimal pv_ag = temp.getPv_ag_anteil_lfd_entgelt();
                    BigDecimal rv_ag = temp.getRv_ag_anteil_lfd_entgelt();
                    BigDecimal sv_umlage = temp.getSv_umlage_u2();
                    BigDecimal steuernAG = temp.getSteuern_ag();
                    BigDecimal ZV_Sanierung = temp.getZv_Sanierungsbeitrag();
                    BigDecimal ZV_Umlage = temp.getZv_umlage_allgemein();
                    BigDecimal VBL = temp.getVbl_wiss_4perc_ag_buchung();
                    BigDecimal[] bigDecimals = {av_ag, kv_ag, ZusBei, pv_ag, rv_ag, sv_umlage, steuernAG, ZV_Sanierung, ZV_Umlage, VBL};
                    if(employeeContract.getVbl_status()){
                        for(BigDecimal bigDecimal:bigDecimals){
                            procent = procent.add(bigDecimal);
                        }
                        procent=procent.subtract(VBL);
                    } else {
                        for(BigDecimal bigDecimal:bigDecimals){
                            procent = procent.add(bigDecimal);
                        }
                        procent = procent.subtract(ZV_Umlage).subtract(ZV_Sanierung);
                    }
                }
            } else {
                System.err.println("jfiofjoiwefjeorifjeorifjeo");
            }

            //Salary Increase Type (normal or bonus)
            boolean isBonus = (increaseSalaryDialogView.getBonusRadioButton().isSelected()) ? true : false;

            //New salary calculation
            BigDecimal projectedSalary = salaryCalculation.projectSalaryToGivenMonth(employeeID, startDate);
            BigDecimal finalSalary = new BigDecimal(0);

            BigDecimal absoluteInputBigDecimal = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getTextFieldAbsolute().getText());
            BigDecimal modifiedAbsoluteInputBigDecimal = absoluteInputBigDecimal.multiply(procent).divide(new BigDecimal(100));
            BigDecimal absoluteFinal = modifiedAbsoluteInputBigDecimal.add(projectedSalary).add(absoluteInputBigDecimal);

            BigDecimal relativeInputBigDecimal = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getTextFieldRelative().getText());
            BigDecimal modifiedRelativeInputBigDecimal = projectedSalary.multiply(relativeInputBigDecimal).divide(new BigDecimal(100));
            BigDecimal relativeFinal = modifiedRelativeInputBigDecimal.add(projectedSalary);

            if (option == IncreaseSalaryOption.ABSOLUTE) {
                //finalSalary = projectedSalary.add(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getTextFieldAbsolute().getText())); //TODO Eingabevalidierung
                finalSalary = absoluteFinal;
            } else if (option == IncreaseSalaryOption.RELATIVE) {
                //finalSalary = projectedSalary.add(StringAndBigDecimalFormatter.formatStringToPercentageValueForScope(increaseSalaryDialogView.getTextFieldRelative().getText()).multiply(projectedSalary.divide(new BigDecimal(100))));
                finalSalary = relativeFinal;
            } else {
                BigDecimal mixedAbsoluteInputBigDecimal = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getMixedAbsolute().getText());
                BigDecimal modifiedMixedAbsoluteInputBigDecimal = mixedAbsoluteInputBigDecimal.multiply(procent).divide(new BigDecimal(100));
                BigDecimal mixedAbsoluteFinal = modifiedMixedAbsoluteInputBigDecimal.add(projectedSalary).add(mixedAbsoluteInputBigDecimal);

                BigDecimal mixedRelativeInputBigDecimal = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getMixedRelative().getText());
                BigDecimal modifiedMixedRelativeInputBigDecimal = projectedSalary.multiply(mixedRelativeInputBigDecimal).divide(new BigDecimal(100));
                BigDecimal mixedRelativeFinal = modifiedMixedRelativeInputBigDecimal.add(projectedSalary);

                finalSalary = (mixedAbsoluteFinal.compareTo(mixedRelativeFinal) < 0) ? mixedRelativeFinal : mixedAbsoluteFinal;
            }


            //Comment
            String comment = increaseSalaryDialogView.getTextFieldComment().getText();

            //DB write
           /* SalaryIncreaseHistory salaryIncreaseHistory = new SalaryIncreaseHistory(employeeID, finalSalary, startDate, comment, isBonus);
            salaryIncreaseHistoryManager.addSalaryIncreaseHistory(salaryIncreaseHistory);
            SalaryIncreaseController salaryIncreaseController = frameController.getSalaryIncreaseController();
            salaryIncreaseController.setTableData(salaryIncreaseController.getDataFromDB(employeeDataManager.getEmployee(employeeID)));*/
            frameController.getUpdater().salaryUpDate();
        }
    }





}
