package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.calculations.CalculateSalaryBasedOnPayRateTable;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.InsertPersonView;
import excelchaos_view.InsertSalaryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertSalaryController implements ActionListener {
    private InsertSalaryView insertSalaryView;
    private MainFrameController frameController;

    private String addSalaryTab = "Gehaltseintrag bearbeiten";

    public InsertSalaryController(MainFrameController mainFrameController) {
        insertSalaryView = new InsertSalaryView();
        insertSalaryView.init();
        insertSalaryView.setActionListener(this);
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

    public void fillFields(String surNameAndName){
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        ContractDataManager contractDataManager = new ContractDataManager();
        Contract contract = contractDataManager.getContract(employeeDataManager.getEmployeeByName(surNameAndName).getId());
        CalculateSalaryBasedOnPayRateTable calculateSalaryBasedOnPayRateTable = new CalculateSalaryBasedOnPayRateTable();
        String[] names = employeeDataManager.getAllEmployeesNameList();

        insertSalaryView.getNamePickList().setModel(new DefaultComboBoxModel<>(names));
        insertSalaryView.getNamePickList().setSelectedItem(surNameAndName);
        insertSalaryView.getNamePickList().setEnabled(false);
        insertSalaryView.getTfGruppe().setSelectedItem(contract.getPaygrade());
        insertSalaryView.getTfGruppe().setEnabled(false);
        insertSalaryView.getPlStufe().setSelectedItem(contract.getPaylevel());

        StringAndDoubleTransformationForDatabase stringAndDoubleTransformationForDatabase = new StringAndDoubleTransformationForDatabase();
        String salary = stringAndDoubleTransformationForDatabase.formatDoubleToString(calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(contract)[0], 1);
        String extraCost = stringAndDoubleTransformationForDatabase.formatDoubleToString(calculateSalaryBasedOnPayRateTable.getCurrentPayRateTableEntryForWiMiAndATM(contract)[1]*12, 1);
        insertSalaryView.getTfGehalt().setText(salary);
        insertSalaryView.getTfSonderzahlung().setText(extraCost);
    }

    public void resetInputs(){
        insertSalaryView.getTfGruppe().setSelectedItem("Nicht ausgewählt");
        insertSalaryView.getPlStufe().setSelectedItem("Nicht ausgewählt");
        insertSalaryView.getTfGehalt().setText(null);
        insertSalaryView.getTfSonderzahlung().setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertSalaryView.getSubmit()) {
            ContractDataManager contractDataManager = new ContractDataManager();
            EmployeeDataManager employeeDataManager = new EmployeeDataManager();
            Employee employee = employeeDataManager.getEmployeeByName(insertSalaryView.getNamePickList().getSelectedItem().toString());

            StringAndDoubleTransformationForDatabase stringAndDoubleTransformationForDatabase = new StringAndDoubleTransformationForDatabase();

            int id = employee.getId();
            String paygrade = insertSalaryView.getTfGruppe().getSelectedItem().toString();
            String paylevel = insertSalaryView.getPlStufe().getSelectedItem().toString();
            double gehalt = stringAndDoubleTransformationForDatabase.transformStringToDouble(insertSalaryView.getTfGehalt().getText());
            double sonderzahlung = stringAndDoubleTransformationForDatabase.transformStringToDouble(insertSalaryView.getTfSonderzahlung().getText());

            Contract contract = contractDataManager.getContract(id);
            contract.setPaygrade(paygrade);
            contract.setPaylevel(paylevel);
            contract.setRegular_cost(gehalt);
            contract.setBonus_cost(sonderzahlung);

            contractDataManager.updateContract(contract);

            insertSalaryView.revalidate();
            insertSalaryView.repaint();
            frameController.getSalaryListController().updateData();
            resetInputs();
            frameController.getTabs().removeTabNewWindow(insertSalaryView);
        }
        if(e.getSource() == insertSalaryView.getReset()){
            resetInputs();
        }

    }
}