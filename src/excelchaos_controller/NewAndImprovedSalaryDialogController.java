package excelchaos_controller;

import excelchaos_model.calculations.SalaryCalculation;
import excelchaos_model.calculations.SalaryTableLookUp;
import excelchaos_model.constants.IncreaseSalaryOption;
import excelchaos_model.database.Contract;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.IncreaseSalaryDialogView;
import excelchaos_view.NewAndImprovedIncreaseSalaryDialogView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class NewAndImprovedSalaryDialogController implements ActionListener {
    private NewAndImprovedIncreaseSalaryDialogView increaseSalaryDialogView;
    private MainFrameController frameController;

    private boolean isProjectionColumnOpenedBefore;

    private ContractDataManager contractDataManager = new ContractDataManager();

    private ArrayList<Integer> employeeIDList;

    private SalaryCalculation salaryCalculation = new SalaryCalculation();

    private SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();


    /**
     * Constructor for NewAndImprovedSalaryDialogController
     * @param frameController frame controller
     * @param employeeIDList list of employee IDs
     */

    public NewAndImprovedSalaryDialogController(MainFrameController frameController, ArrayList<Integer> employeeIDList) {
        this.frameController = frameController;
        this.employeeIDList = employeeIDList;
        this.isProjectionColumnOpenedBefore = false;
        increaseSalaryDialogView = new NewAndImprovedIncreaseSalaryDialogView();
        increaseSalaryDialogView.init(employeeIDList);
        increaseSalaryDialogView.setActionListener(this);

    }

    /**
     * Returns list of projected salaries before increase
     * @return list of projected salaries before increase
     */
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

    /**
     * Returns list of projected salaries after increase
     * @param option option specifies increase
     * @return list of projected salaries before increase
     */
    public BigDecimal[] projectedSalaryAfterIncrease(IncreaseSalaryOption option){
        BigDecimal[] result = new BigDecimal[employeeIDList.size()];

        int currentIndex = 0;

        for (int i = 0; i < employeeIDList.size(); i++) {
            Contract contract = contractDataManager.getContract(employeeIDList.get(i));
            String paygrade = contract.getPaygrade();
            String paylevel = contract.getPaylevel();

            LocalDate salaryIncreaseStartDate = increaseSalaryDialogView.getStartDate().getDate();
            Date startDate = Date.from(salaryIncreaseStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            BigDecimal projectedSalary = salaryCalculation.projectSalaryToGivenMonth(i,startDate);
            BigDecimal finalSalary;
            if(option == IncreaseSalaryOption.ABSOLUTE){
                BigDecimal[] percentages = salaryTableLookUp.getPercentagesOfPayRateTable(contract,salaryIncreaseStartDate);
                BigDecimal baseIncrease = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getAbsoluteTextField().getText());
                BigDecimal absoluteSum = new BigDecimal(0);
                for (int j = 0; j < percentages.length; j++) {
                    absoluteSum = absoluteSum.add(baseIncrease.multiply(percentages[j]));
                }
                absoluteSum = absoluteSum.add(baseIncrease);
                absoluteSum = absoluteSum.multiply(contract.getScope());
                finalSalary = projectedSalary.add(absoluteSum);

            } else if (option == IncreaseSalaryOption.RELATIVE) {
                finalSalary = projectedSalary.multiply(StringAndBigDecimalFormatter.formatStringToPercentageValueForScope(increaseSalaryDialogView.getRelativeTextField().getText()));
                finalSalary = finalSalary.add(projectedSalary);
            } else {
                BigDecimal relative = new BigDecimal(0);
                BigDecimal absolute = new BigDecimal(0);
                BigDecimal[] percentages = salaryTableLookUp.getPercentagesOfPayRateTable(contract,salaryIncreaseStartDate);
                BigDecimal baseIncrease = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getAbsoluteTextField().getText());

                for (int j = 0; j < percentages.length; j++) {
                    absolute = absolute.add(baseIncrease.multiply(percentages[j]));
                }
                absolute = absolute.add(baseIncrease);
                absolute = absolute.multiply(contract.getScope());

                relative = projectedSalary.multiply(StringAndBigDecimalFormatter.formatStringToPercentageValueForScope(increaseSalaryDialogView.getRelativeTextField().getText()));

                if(relative.compareTo(absolute) < 0){
                    finalSalary = absolute;
                } else {
                    finalSalary = relative;
                }
                finalSalary = finalSalary.add(projectedSalary);

            }
            result[i] = finalSalary;


        }
        return result;
    }


    /**
     * Checks if action has been performed
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == increaseSalaryDialogView.getCancelButton()){
            increaseSalaryDialogView.dispose();
        } else if (e.getSource() == increaseSalaryDialogView.getAbsoluteRadioButton()) {
            increaseSalaryDialogView.setAbsoluteRadioButtonSelected();
        } else if (e.getSource() == increaseSalaryDialogView.getRelativeRadioButton()) {
            increaseSalaryDialogView.setRelativeRadioButtonSelected();
        } else if (e.getSource() == increaseSalaryDialogView.getMixedRadioButton()) {
            increaseSalaryDialogView.setMixedRadioButtonSelected();
        }
    }

}
