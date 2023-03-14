package excelchaos_controller;

import excelchaos_model.calculations.SalaryTableLookUp;
import excelchaos_model.database.Contract;
import excelchaos_model.database.Employee;
import excelchaos_model.datamodel.employeedataoperations.EmployeeDataAccess;
import excelchaos_model.datamodel.employeedataoperations.EmployeeDataInserter;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.InsertSalarySHKView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

public class InsertSalarySHKController implements ActionListener, ItemListener {
    private EmployeeDataAccess employeeDataAccess;
    private EmployeeDataInserter employeeDataInserter;
    private InsertSalarySHKView insertSalarySHKView;
    private MainFrameController frameController;
    private int currentlyEditingContractID = 0;
    private int backUpNumber = 0;

    private SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();


    private String addSalaryTab = "Gehaltseintrag bearbeiten";

    //TODO: an entsprechender stelle controller aufrufen
    public InsertSalarySHKController(MainFrameController mainFrameController) {
        employeeDataAccess = new EmployeeDataAccess();
        employeeDataInserter = new EmployeeDataInserter();
        insertSalarySHKView = new InsertSalarySHKView();
        insertSalarySHKView.init();
        insertSalarySHKView.setActionListener(this);
        insertSalarySHKView.setItemListener(this);
        frameController = mainFrameController;

    }

    //TODO: an entsprechender stelle view aufrufen
    public void showInsertSalarySHKView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(addSalaryTab) == -1) {
            mainFrameController.getTabs().addTab(addSalaryTab, insertSalarySHKView);
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addSalaryTab));
        } else mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addSalaryTab));
    }

    public void fillFields(int id) {
        currentlyEditingContractID = id;
        Contract contract = employeeDataAccess.getContract(id);

        String[] names = employeeDataAccess.getEmployeeNamesList();
        String currentEmployeeName = employeeDataAccess.getEmployee(id).getSurname() + " " + employeeDataAccess.getEmployee(id).getName();
        insertSalarySHKView.getNamePickList().setModel(new DefaultComboBoxModel<>(names));
        insertSalarySHKView.getNamePickList().setSelectedItem(currentEmployeeName);
        insertSalarySHKView.getNamePickList().setEnabled(false);
        insertSalarySHKView.getTfHiwiTypeOfPayment().setSelectedItem(contract.getShk_hourly_rate());

        String salary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getRegular_cost());
        String extraCost = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getBonus_cost());
        insertSalarySHKView.getTfSalary().setText(salary);
        insertSalarySHKView.getTfExtraCost().setText(extraCost);
    }

    public void resetInputs() {
        backUpNumber = currentlyEditingContractID;
        currentlyEditingContractID = 0;
        insertSalarySHKView.getTfHiwiTypeOfPayment().setSelectedItem("Nicht ausgewählt");
        insertSalarySHKView.getTfSalary().setText(null);
        insertSalarySHKView.getTfExtraCost().setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertSalarySHKView.getSubmit()) {
            Employee employee;
            String hiwiTypeOfPayment = null;
            BigDecimal gehalt = new BigDecimal(0);
            BigDecimal sonderzahlung = new BigDecimal(0);
            if (backUpNumber != 0) {
                employee = employeeDataAccess.getEmployee(backUpNumber);
            } else {
                employee = employeeDataAccess.getEmployee(currentlyEditingContractID);
            }
            int id = employee.getId();
            hiwiTypeOfPayment = insertSalarySHKView.getTfHiwiTypeOfPayment().getSelectedItem().toString();

            if (!insertSalarySHKView.getTfSalary().getText().equals("")) {
                gehalt = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(insertSalarySHKView.getTfSalary().getText());
            }
            if (!insertSalarySHKView.getTfExtraCost().getText().equals("")) {
                sonderzahlung = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(insertSalarySHKView.getTfExtraCost().getText());
            }

            if (hiwiTypeOfPayment.equals("Nicht ausgewählt")) {
                insertSalarySHKView.markMustBeFilledTextFields();
                JOptionPane.showConfirmDialog(null, "Bitte füllen Sie mindestens das Feld \"SHK Stundensatz\".", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                Contract contract = employeeDataAccess.getContract(id);
                contract.setShk_hourly_rate(hiwiTypeOfPayment);
                contract.setRegular_cost(gehalt);
                contract.setBonus_cost(sonderzahlung);

                employeeDataInserter.updateExistingContract(contract);

                insertSalarySHKView.revalidate();
                insertSalarySHKView.repaint();
                SalaryListController salaryListController = frameController.getSalaryListController();
                salaryListController.updateData();
                resetInputs();
                frameController.getTabs().removeTabNewWindow(insertSalarySHKView);
            }
        }
        if (e.getSource() == insertSalarySHKView.getReset()) {
            resetInputs();
        }
        if (e.getSource() == insertSalarySHKView.getCancel()) {
            frameController.getTabs().removeTabNewWindow(insertSalarySHKView);
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (insertSalarySHKView.getTfHiwiTypeOfPayment().getSelectedItem().toString().equals("Nicht ausgewählt")) {

            } else {
                if (backUpNumber != 0) {
                    currentlyEditingContractID = backUpNumber;
                }
                Contract contract = employeeDataAccess.getContract(currentlyEditingContractID);
                Contract calcContract = new Contract(currentlyEditingContractID, "", "", contract.getStart_date(), contract.getEnd_date(), new BigDecimal(0), new BigDecimal(0), contract.getScope(), (String) insertSalarySHKView.getTfHiwiTypeOfPayment().getSelectedItem(), false);
                BigDecimal[] newCost = salaryTableLookUp.getCurrentPayRateTableEntry(calcContract);
                insertSalarySHKView.getTfSalary().setText(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(newCost[0]));
                insertSalarySHKView.getTfExtraCost().setText("0");
            }

        }

    }
}