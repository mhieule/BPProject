package excelchaos_controller;

import excelchaos_model.calculations.SalaryTableLookUp;
import excelchaos_model.database.Contract;
import excelchaos_model.database.Employee;
import excelchaos_model.datamodel.EmployeeDataAccess;
import excelchaos_model.datamodel.EmployeeDataInserter;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.InsertSalaryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;

public class InsertSalaryController implements ActionListener, ItemListener {
    private EmployeeDataAccess employeeDataAccess;
    private EmployeeDataInserter employeeDataInserter;
    private InsertSalaryView insertSalaryView;
    private MainFrameController frameController;
    private int currentlyEditingContractID = 0;
    private int backUpNumber = 0;

    private SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();


    private String addSalaryTab = "Gehaltseintrag bearbeiten";

    public InsertSalaryController(MainFrameController mainFrameController) {
        employeeDataAccess = new EmployeeDataAccess();
        employeeDataInserter = new EmployeeDataInserter();
        insertSalaryView = new InsertSalaryView();
        insertSalaryView.init();
        insertSalaryView.setActionListener(this);
        insertSalaryView.setItemListener(this);
        frameController = mainFrameController;

    }
    public void showInsertSalaryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(addSalaryTab) == -1) {
            mainFrameController.getTabs().addTab(addSalaryTab, insertSalaryView);
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addSalaryTab));
        } else
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addSalaryTab));
    }

    public void fillFields(int id) {

        currentlyEditingContractID = id;
        Contract contract = employeeDataAccess.getContract(id);

        String[] names = employeeDataAccess.getEmployeeNamesList();
        String currentEmployeeName = employeeDataAccess.getEmployee(id).getSurname() + " " + employeeDataAccess.getEmployee(id).getName();
        insertSalaryView.getNamePickList().setModel(new DefaultComboBoxModel<>(names));
        insertSalaryView.getNamePickList().setSelectedItem(currentEmployeeName);
        insertSalaryView.getNamePickList().setEnabled(false);
        insertSalaryView.getTfGruppe().setSelectedItem(contract.getPaygrade());
        insertSalaryView.getPlStufe().setSelectedItem(contract.getPaylevel());
        if (contract.getVbl_status()) {
            insertSalaryView.getVblList().setSelectedItem("Pflichtig");
        } else {
            insertSalaryView.getVblList().setSelectedItem("Befreit");
        }
        String salary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getRegular_cost());
        String extraCost = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contract.getBonus_cost());
        insertSalaryView.getTfGehalt().setText(salary);
        insertSalaryView.getTfSonderzahlung().setText(extraCost);
    }

    public void resetInputs() {
        backUpNumber = currentlyEditingContractID;
        currentlyEditingContractID = 0;
        insertSalaryView.getTfGruppe().setSelectedItem("Nicht ausgewählt");
        insertSalaryView.getPlStufe().setSelectedItem("Nicht ausgewählt");
        insertSalaryView.getVblList().setSelectedItem("Nicht ausgewählt");
        insertSalaryView.getTfGehalt().setText(null);
        insertSalaryView.getTfSonderzahlung().setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertSalaryView.getSubmit()) {
            Employee employee;
            String paygrade = null;
            String paylevel = null;
            String vblState = null;
            boolean vbl = true;
            BigDecimal gehalt =  new BigDecimal(0);
            BigDecimal sonderzahlung = new BigDecimal(0);
            if (backUpNumber != 0) {
                employee = employeeDataAccess.getEmployee(backUpNumber);
            } else {
                employee = employeeDataAccess.getEmployee(currentlyEditingContractID);
            }
            int id = employee.getId();
            paygrade = insertSalaryView.getTfGruppe().getSelectedItem().toString();
            paylevel = insertSalaryView.getPlStufe().getSelectedItem().toString();
            vblState = insertSalaryView.getVblList().getSelectedItem().toString();

            if (vblState.equals("Befreit")) {
                vbl = false;
            }
            if (!insertSalaryView.getTfGehalt().getText().equals("")) {
                gehalt = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(insertSalaryView.getTfGehalt().getText());
            }
            if (!insertSalaryView.getTfSonderzahlung().getText().equals("")) {
                sonderzahlung = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(insertSalaryView.getTfSonderzahlung().getText());
            }

            if (paygrade.equals("Nicht ausgewählt") || paylevel.equals("Nicht ausgewählt") || vblState.equals("Nicht ausgewählt")) {
                insertSalaryView.markMustBeFilledTextFields();
                JOptionPane.showConfirmDialog(null, "Bitte füllen Sie mindestens die Felder \"Gehaltsklasse\", \"Gehaltsstufe\" und \"VBL-Status\" aus.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                Contract contract = employeeDataAccess.getContract(id);
                contract.setPaygrade(paygrade);
                contract.setPaylevel(paylevel);
                contract.setVbl_status(vbl);
                contract.setRegular_cost(gehalt);
                contract.setBonus_cost(sonderzahlung);

                employeeDataInserter.updateExistingContract(contract);

                insertSalaryView.revalidate();
                insertSalaryView.repaint();
                SalaryListController salaryListController = frameController.getSalaryListController();
                salaryListController.updateData();
                resetInputs();
                frameController.getTabs().removeTabNewWindow(insertSalaryView);
            }
        }
        if (e.getSource() == insertSalaryView.getReset()) {
            resetInputs();
        }
        if (e.getSource() == insertSalaryView.getCancel()) {
            frameController.getTabs().removeTabNewWindow(insertSalaryView);
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (insertSalaryView.getVblList().getSelectedItem().toString().equals("Nicht ausgewählt") || insertSalaryView.getTfGruppe().getSelectedItem().toString().equals("Nicht ausgewählt") || insertSalaryView.getPlStufe().getSelectedItem().toString().equals("Nicht ausgewählt")) {

            } else {
                if (backUpNumber != 0) {
                    currentlyEditingContractID = backUpNumber;
                }
                boolean vbl = false;
                if (insertSalaryView.getVblList().getSelectedItem().toString().equals("Pflichtig")) {
                    vbl = true;
                }
                Contract contract = employeeDataAccess.getContract(currentlyEditingContractID);
                Contract calcContract = new Contract(currentlyEditingContractID, (String) insertSalaryView.getTfGruppe().getSelectedItem(),
                        (String) insertSalaryView.getPlStufe().getSelectedItem(), contract.getStart_date(), contract.getEnd_date(), new BigDecimal(0), new BigDecimal(0), contract.getScope(), "", vbl);
                BigDecimal[] newCost = salaryTableLookUp.getCurrentPayRateTableEntry(calcContract);
                insertSalaryView.getTfGehalt().setText(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(newCost[0]));
                insertSalaryView.getTfSonderzahlung().setText(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(newCost[1] .multiply(new BigDecimal(12))));
            }

        }

    }
}