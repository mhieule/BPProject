package excelchaos_controller;

import excelchaos_model.calculations.SalaryCalculation;
import excelchaos_model.calculations.SalaryTableLookUp;
import excelchaos_model.constants.IncreaseSalaryOption;
import excelchaos_model.database.*;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.IncreaseSalaryDialogView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class IncreaseSalaryDialogController implements ActionListener {
    private IncreaseSalaryDialogView increaseSalaryDialogView;
    private MainFrameController frameController;

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();
    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();

    private ArrayList<Integer> employeeIDList;

    private SalaryCalculation salaryCalculation = new SalaryCalculation();

    private SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();

    private Date editedDate = null;

    private SalaryIncreaseHistory editedEntry;

    /**
     * Constructor for  IncreaseSalaryDialogController
     *
     * @param frameController frame controller
     * @param employeeIDList  list of employee IDs
     */

    public IncreaseSalaryDialogController(MainFrameController frameController, ArrayList<Integer> employeeIDList) {
        this.frameController = frameController;
        this.employeeIDList = employeeIDList;
        increaseSalaryDialogView = new IncreaseSalaryDialogView();
        increaseSalaryDialogView.init(employeeIDList);
        increaseSalaryDialogView.setActionListener(this);

    }

    public void fillFields(String[] data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDate = LocalDate.parse(data[0], formatter);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        increaseSalaryDialogView.getStartDate().setDate(localDate);
        BigDecimal oldValue = salaryCalculation.projectSalaryToGivenMonth(employeeIDList.get(0), date);
        BigDecimal newValue = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(data[1]);
        BigDecimal difference = newValue.subtract(oldValue);
        increaseSalaryDialogView.getSalaryTable().setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(oldValue), 0, 3);
        increaseSalaryDialogView.getSalaryTable().setValueAt(data[1], 0, 4);
        increaseSalaryDialogView.getSalaryTable().setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(difference), 0, 5);
        data[2] = data[2].replaceAll("\\h", " ");
        if (data[2].equals("0,00 €")) {
            increaseSalaryDialogView.getRelativeRadioButton().setSelected(true);
            increaseSalaryDialogView.getRelativeTextField().setText(data[3]);
        } else if (data[3].equals("0%")) {
            increaseSalaryDialogView.getAbsoluteRadioButton().setSelected(true);
            increaseSalaryDialogView.getAbsoluteTextField().setEnabled(true);
            increaseSalaryDialogView.getAbsoluteTextField().setBackground(Color.WHITE);
            increaseSalaryDialogView.getRelativeTextField().setEnabled(false);
            increaseSalaryDialogView.getRelativeTextField().setBackground(Color.LIGHT_GRAY);
            increaseSalaryDialogView.getAbsoluteTextField().setText(data[2]);
        } else {
            increaseSalaryDialogView.getMixedRadioButton().setSelected(true);
            increaseSalaryDialogView.getAbsoluteTextField().setEnabled(true);
            increaseSalaryDialogView.getAbsoluteTextField().setBackground(Color.WHITE);
            increaseSalaryDialogView.getAbsoluteTextField().setText(data[2]);
            increaseSalaryDialogView.getRelativeTextField().setText(data[3]);
        }
        increaseSalaryDialogView.getCommentTextField().setText(data[4]);
        if (data[5].equals("Ja")) {
            increaseSalaryDialogView.getBonusRadioButton().setSelected(true);
        }
        editedDate = date;
        editedEntry = salaryIncreaseHistoryManager.getSalaryIncreaseHistoryByDate(employeeIDList.get(0), date).get(0);
        salaryIncreaseHistoryManager.removeSalaryIncreaseHistory(employeeIDList.get(0), date);
    }

    /**
     * Returns list of projected salaries before increase
     *
     * @return list of projected salaries before increase
     */

    public BigDecimal[] projectedSalaryBeforeIncrease() {
        BigDecimal[] result = new BigDecimal[employeeIDList.size()];
        LocalDate startLocalDate = increaseSalaryDialogView.getStartDate().getDate();
        Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        int currentIndex = 0;
        for (Integer employeeID : employeeIDList) {
            result[currentIndex] = salaryCalculation.projectSalaryToGivenMonth(employeeID, startDate);
            currentIndex++;
        }
        return result;
    }

    /**
     * Returns list of projected salaries after increase
     *
     * @param option option specifies increase
     * @return list of projected salaries before increase
     */

    public BigDecimal[] projectedSalaryAfterIncrease(IncreaseSalaryOption option) {
        BigDecimal[] result = new BigDecimal[employeeIDList.size()];


        for (int i = 0; i < employeeIDList.size(); i++) {
            Contract contract = contractDataManager.getContract(employeeIDList.get(i));

            LocalDate salaryIncreaseStartDate = increaseSalaryDialogView.getStartDate().getDate();
            Date startDate = Date.from(salaryIncreaseStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            BigDecimal projectedSalary = salaryCalculation.projectSalaryToGivenMonth(employeeIDList.get(i), startDate);
            BigDecimal finalSalary;
            if (option == IncreaseSalaryOption.ABSOLUTE) {
                BigDecimal[] percentages = salaryTableLookUp.getPercentagesOfPayRateTable(contract, salaryIncreaseStartDate);
                BigDecimal baseIncrease = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getAbsoluteTextField().getText());
                BigDecimal absoluteSum = new BigDecimal(0);
                for (int j = 0; j < percentages.length; j++) {
                    absoluteSum = absoluteSum.add(baseIncrease.multiply(percentages[j].divide(new BigDecimal(100), 5, RoundingMode.HALF_EVEN)));
                }
                absoluteSum = absoluteSum.add(baseIncrease);
                absoluteSum = absoluteSum.multiply(contract.getScope());
                finalSalary = projectedSalary.add(absoluteSum);

            } else if (option == IncreaseSalaryOption.RELATIVE) {
                finalSalary = projectedSalary.multiply(StringAndBigDecimalFormatter.formatStringToPercentageValueForSalaryIncrease(increaseSalaryDialogView.getRelativeTextField().getText()));
                finalSalary = finalSalary.add(projectedSalary);
            } else {
                BigDecimal relative = new BigDecimal(0);
                BigDecimal absolute = new BigDecimal(0);
                BigDecimal[] percentages = salaryTableLookUp.getPercentagesOfPayRateTable(contract, salaryIncreaseStartDate);
                BigDecimal baseIncrease = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getAbsoluteTextField().getText());

                for (int j = 0; j < percentages.length; j++) {
                    absolute = absolute.add(baseIncrease.multiply(percentages[j].divide(new BigDecimal(100), 5, RoundingMode.HALF_EVEN)));
                }
                absolute = absolute.add(baseIncrease);
                absolute = absolute.multiply(contract.getScope());

                relative = projectedSalary.multiply(StringAndBigDecimalFormatter.formatStringToPercentageValueForSalaryIncrease(increaseSalaryDialogView.getRelativeTextField().getText()));

                if (relative.compareTo(absolute) < 0) {
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
     * Adds salary increase to database
     *
     * @param option salary increase option
     */
    public void submitSalaryIncreaseToDatabase(IncreaseSalaryOption option) {
        for (int i = 0; i < employeeIDList.size(); i++) {
            Contract contract = contractDataManager.getContract(employeeIDList.get(i));

            LocalDate salaryIncreaseStartDate = increaseSalaryDialogView.getStartDate().getDate();
            Date startDate = Date.from(salaryIncreaseStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            BigDecimal projectedSalary = salaryCalculation.projectSalaryToGivenMonth(employeeIDList.get(i), startDate);
            BigDecimal finalSalary;
            if (option == IncreaseSalaryOption.ABSOLUTE) {
                BigDecimal[] percentages = salaryTableLookUp.getPercentagesOfPayRateTable(contract, salaryIncreaseStartDate);
                BigDecimal baseIncrease = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getAbsoluteTextField().getText());
                BigDecimal absoluteSum = new BigDecimal(0);
                for (int j = 0; j < percentages.length; j++) {
                    absoluteSum = absoluteSum.add(baseIncrease.multiply(percentages[j].divide(new BigDecimal(100), 5, RoundingMode.HALF_EVEN)));
                }
                absoluteSum = absoluteSum.add(baseIncrease);
                absoluteSum = absoluteSum.multiply(contract.getScope());
                finalSalary = projectedSalary.add(absoluteSum);

            } else if (option == IncreaseSalaryOption.RELATIVE) {
                finalSalary = projectedSalary.multiply(StringAndBigDecimalFormatter.formatStringToPercentageValueForSalaryIncrease(increaseSalaryDialogView.getRelativeTextField().getText()));
                finalSalary = finalSalary.add(projectedSalary);

            } else {
                BigDecimal relative = new BigDecimal(0);
                BigDecimal absolute = new BigDecimal(0);
                BigDecimal[] percentages = salaryTableLookUp.getPercentagesOfPayRateTable(contract, salaryIncreaseStartDate);
                BigDecimal baseIncrease = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getAbsoluteTextField().getText());

                for (int j = 0; j < percentages.length; j++) {
                    absolute = absolute.add(baseIncrease.multiply(percentages[j].divide(new BigDecimal(100), 5, RoundingMode.HALF_EVEN)));
                }
                absolute = absolute.add(baseIncrease);
                absolute = absolute.multiply(contract.getScope());

                relative = projectedSalary.multiply(StringAndBigDecimalFormatter.formatStringToPercentageValueForSalaryIncrease(increaseSalaryDialogView.getRelativeTextField().getText()));

                if (relative.compareTo(absolute) < 0) {
                    finalSalary = absolute;
                } else {
                    finalSalary = relative;
                }
                finalSalary = finalSalary.add(projectedSalary);

            }
            BigDecimal absoluteIncreaseValue;
            if (increaseSalaryDialogView.getAbsoluteTextField().getText() == null || increaseSalaryDialogView.getAbsoluteTextField().getText().equals("")) {
                absoluteIncreaseValue = new BigDecimal(0);
            } else {
                absoluteIncreaseValue = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(increaseSalaryDialogView.getAbsoluteTextField().getText());
            }
            BigDecimal percentageIncreaseValue;
            if (increaseSalaryDialogView.getRelativeTextField().getText() == null || increaseSalaryDialogView.getRelativeTextField().getText().equals("")) {
                percentageIncreaseValue = new BigDecimal(0);
            } else {
                percentageIncreaseValue = StringAndBigDecimalFormatter.formatStringToPercentageValueForSalaryIncrease(increaseSalaryDialogView.getRelativeTextField().getText());
            }
            boolean isBonus = increaseSalaryDialogView.getBonusRadioButton().isSelected();
            String comment = increaseSalaryDialogView.getCommentTextField().getText();
            SalaryIncreaseHistory salaryIncreaseHistory = new SalaryIncreaseHistory(employeeIDList.get(i), finalSalary, startDate, absoluteIncreaseValue, percentageIncreaseValue, comment, isBonus);
            if (editedDate == null) {
                salaryIncreaseHistoryManager.addSalaryIncreaseHistory(salaryIncreaseHistory);
            } else {
                salaryIncreaseHistoryManager.removeSalaryIncreaseHistory(employeeIDList.get(i), editedDate);
                salaryIncreaseHistoryManager.addSalaryIncreaseHistory(salaryIncreaseHistory);
            }
            SalaryIncreaseController salaryIncreaseController = frameController.getSalaryIncreaseController();
            salaryIncreaseController.setTableData(salaryIncreaseController.getDataFromDB(employeeDataManager.getEmployee(employeeIDList.get(i))));
            frameController.getUpdater().salaryUpDate();
        }
    }

    /**
     * Checks if action has been performed
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == increaseSalaryDialogView.getCancelButton()) {
            if (editedDate != null) {
                salaryIncreaseHistoryManager.addSalaryIncreaseHistory(editedEntry);
            }
            increaseSalaryDialogView.dispose();
        } else if (e.getSource() == increaseSalaryDialogView.getAbsoluteRadioButton()) {
            increaseSalaryDialogView.setAbsoluteRadioButtonSelected();
        } else if (e.getSource() == increaseSalaryDialogView.getRelativeRadioButton()) {
            increaseSalaryDialogView.setRelativeRadioButtonSelected();
        } else if (e.getSource() == increaseSalaryDialogView.getMixedRadioButton()) {
            increaseSalaryDialogView.setMixedRadioButtonSelected();
        } else if (e.getSource() == increaseSalaryDialogView.getProjectButton()) {
            if (increaseSalaryDialogView.getStartDate().getDate() != null) {
                if (increaseSalaryDialogView.getAbsoluteRadioButton().isSelected()) {
                    if (increaseSalaryDialogView.getAbsoluteTextField().getText() == null || increaseSalaryDialogView.getAbsoluteTextField().getText().equals("")) {
                        JOptionPane.showMessageDialog(increaseSalaryDialogView, "Bitte geben sie einen Absoluten Betrag zur Erhöhung ein.", "Kein Betrag eingegeben.", JOptionPane.ERROR_MESSAGE);
                    } else {
                        increaseSalaryDialogView.setProjectionView(projectedSalaryBeforeIncrease(), projectedSalaryAfterIncrease(IncreaseSalaryOption.ABSOLUTE));
                    }
                } else if (increaseSalaryDialogView.getRelativeRadioButton().isSelected()) {
                    if (increaseSalaryDialogView.getRelativeTextField().getText() == null || increaseSalaryDialogView.getRelativeTextField().getText().equals("")) {
                        JOptionPane.showMessageDialog(increaseSalaryDialogView, "Bitte geben sie einen Prozentwert zur Erhöhung ein.", "Kein Prozentwert eingegeben.", JOptionPane.ERROR_MESSAGE);
                    } else {
                        increaseSalaryDialogView.setProjectionView(projectedSalaryBeforeIncrease(), projectedSalaryAfterIncrease(IncreaseSalaryOption.RELATIVE));
                    }
                } else if (increaseSalaryDialogView.getMixedRadioButton().isSelected()) {
                    if (increaseSalaryDialogView.getRelativeTextField().getText() == null || increaseSalaryDialogView.getRelativeTextField().getText().equals("") || increaseSalaryDialogView.getAbsoluteTextField().getText() == null || increaseSalaryDialogView.getAbsoluteTextField().getText().equals("")) {
                        JOptionPane.showMessageDialog(increaseSalaryDialogView, "Bitte geben sie in beide Felder einen Wert zur Erhöhung ein.", "Fehlender Wert.", JOptionPane.ERROR_MESSAGE);
                    } else {
                        increaseSalaryDialogView.setProjectionView(projectedSalaryBeforeIncrease(), projectedSalaryAfterIncrease(IncreaseSalaryOption.MIXED));

                    }
                }
            } else {

                JOptionPane.showMessageDialog(increaseSalaryDialogView, "Bitte wählen Sie ein Startdatum aus.", "Kein Startdatum ausgewählt.", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (e.getSource() == increaseSalaryDialogView.getSaveAndExitButton()) {
            if (increaseSalaryDialogView.getStartDate().getDate() != null) {
                if (increaseSalaryDialogView.getAbsoluteRadioButton().isSelected()) {
                    if (increaseSalaryDialogView.getAbsoluteTextField().getText() == null || increaseSalaryDialogView.getAbsoluteTextField().getText().equals("")) {
                        JOptionPane.showMessageDialog(increaseSalaryDialogView, "Bitte geben sie einen Absoluten Betrag zur Erhöhung ein.", "Kein Betrag eingegeben.", JOptionPane.ERROR_MESSAGE);
                    } else {
                        submitSalaryIncreaseToDatabase(IncreaseSalaryOption.ABSOLUTE);
                    }
                } else if (increaseSalaryDialogView.getRelativeRadioButton().isSelected()) {
                    if (increaseSalaryDialogView.getRelativeTextField().getText() == null || increaseSalaryDialogView.getRelativeTextField().getText().equals("")) {
                        JOptionPane.showMessageDialog(increaseSalaryDialogView, "Bitte geben sie einen Prozentwert zur Erhöhung ein.", "Kein Prozentwert eingegeben.", JOptionPane.ERROR_MESSAGE);
                    } else {
                        submitSalaryIncreaseToDatabase(IncreaseSalaryOption.RELATIVE);
                    }
                } else if (increaseSalaryDialogView.getMixedRadioButton().isSelected()) {
                    if (increaseSalaryDialogView.getRelativeTextField().getText() == null || increaseSalaryDialogView.getRelativeTextField().getText().equals("") || increaseSalaryDialogView.getAbsoluteTextField().getText() == null || increaseSalaryDialogView.getAbsoluteTextField().getText().equals("")) {
                        JOptionPane.showMessageDialog(increaseSalaryDialogView, "Bitte geben sie in beide Felder einen Wert zur Erhöhung ein.", "Fehlender Wert.", JOptionPane.ERROR_MESSAGE);
                    } else {
                        submitSalaryIncreaseToDatabase(IncreaseSalaryOption.MIXED);

                    }
                }
            } else {
                JOptionPane.showMessageDialog(increaseSalaryDialogView, "Bitte wählen Sie ein Startdatum aus.", "Kein Startdatum ausgewählt.", JOptionPane.ERROR_MESSAGE);
                return;
            }
            increaseSalaryDialogView.dispose();
        }
    }

}
