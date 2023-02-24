package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.calculations.CalculateSalaryBasedOnPayRateTable;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.InsertPersonView;
import excelchaos_view.InsertSalaryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class InsertSalaryController implements ActionListener, ItemListener {
    private InsertSalaryView insertSalaryView;
    private MainFrameController frameController;

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();

    private int currentlyEditingContractID = 0;
    private int backUpNumber = 0;

    StringAndDoubleTransformationForDatabase stringAndDoubleTransformationForDatabase = new StringAndDoubleTransformationForDatabase();

    private CalculateSalaryBasedOnPayRateTable calculateSalaryBasedOnPayRateTable = new CalculateSalaryBasedOnPayRateTable();

    private String addSalaryTab = "Gehaltseintrag bearbeiten";

    public InsertSalaryController(MainFrameController mainFrameController) {
        insertSalaryView = new InsertSalaryView();
        insertSalaryView.init();
        insertSalaryView.setActionListener(this);
        insertSalaryView.setItemListener(this);
        frameController = mainFrameController;

    }

    public InsertSalaryView getInsertSalaryView() {
        return insertSalaryView;
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
        Contract contract = contractDataManager.getContract(id);

        String[] names = employeeDataManager.getAllEmployeesNameList();
        String currentEmployeeName = employeeDataManager.getEmployee(id).getSurname() + " " + employeeDataManager.getEmployee(id).getName();
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
        String salary = stringAndDoubleTransformationForDatabase.formatDoubleToString(contract.getRegular_cost(), 1);
        String extraCost = stringAndDoubleTransformationForDatabase.formatDoubleToString(contract.getBonus_cost(), 1);
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
            double gehalt = 0;
            double sonderzahlung = 0;
            if (backUpNumber != 0) {
                employee = employeeDataManager.getEmployee(backUpNumber);
            } else {
                employee = employeeDataManager.getEmployee(currentlyEditingContractID);
            }
            int id = employee.getId();
            paygrade = insertSalaryView.getTfGruppe().getSelectedItem().toString();
            paylevel = insertSalaryView.getPlStufe().getSelectedItem().toString();
            vblState = insertSalaryView.getVblList().getSelectedItem().toString();

            if (vblState.equals("Befreit")) {
                vbl = false;
            }
            if(!insertSalaryView.getTfGehalt().getText().equals("")){
                gehalt = stringAndDoubleTransformationForDatabase.transformStringToDouble(insertSalaryView.getTfGehalt().getText());
            }
            if(!insertSalaryView.getTfSonderzahlung().getText().equals("")){
                sonderzahlung = stringAndDoubleTransformationForDatabase.transformStringToDouble(insertSalaryView.getTfSonderzahlung().getText());
            }

            if (paygrade.equals("Nicht ausgewählt") || paylevel.equals("Nicht ausgewählt") || vblState.equals("Nicht ausgewählt")) {
                insertSalaryView.markMustBeFilledTextFields();
                JOptionPane.showConfirmDialog(null, "Bitte füllen Sie mindestens die Felder \"Gehaltsklasse\", \"Gehaltsstufe\" und \"VBL-Status\" aus.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                Contract contract = contractDataManager.getContract(id);
                contract.setPaygrade(paygrade);
                contract.setPaylevel(paylevel);
                contract.setVbl_status(vbl);
                contract.setRegular_cost(gehalt);
                contract.setBonus_cost(sonderzahlung);

                contractDataManager.updateContract(contract);

                insertSalaryView.revalidate();
                insertSalaryView.repaint();
                SalaryListController salaryListController = frameController.getSalaryListController();
                salaryListController.updateData(salaryListController.getSalaryDataFromDataBase());
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
                Contract contract = contractDataManager.getContract(currentlyEditingContractID);
                Contract calcContract = new Contract(currentlyEditingContractID, (String) insertSalaryView.getTfGruppe().getSelectedItem(),
                        (String) insertSalaryView.getPlStufe().getSelectedItem(), contract.getStart_date(), contract.getEnd_date(), 0, 0, contract.getScope(), "", vbl);
                double[] newCost = calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(calcContract);
                insertSalaryView.getTfGehalt().setText(stringAndDoubleTransformationForDatabase.formatDoubleToString(newCost[0], 1));
                insertSalaryView.getTfSonderzahlung().setText(stringAndDoubleTransformationForDatabase.formatDoubleToString(newCost[1] * 12, 1));
            }

        }

    }
}