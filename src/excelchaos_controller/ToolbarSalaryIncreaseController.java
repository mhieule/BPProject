package excelchaos_controller;

import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.export.CSVExporter;
import excelchaos_view.toolbarviews.ToolbarSalaryIncreaseView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToolbarSalaryIncreaseController implements ActionListener {

    private ToolbarSalaryIncreaseView toolbar;

    private MainFrameController frameController;

    private SalaryIncreaseController salaryIncreaseController;


    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();

    public ToolbarSalaryIncreaseController(MainFrameController mainFrameController, SalaryIncreaseController salaryIncreaseController) {
        frameController = mainFrameController;
        this.salaryIncreaseController = salaryIncreaseController;
        toolbar = new ToolbarSalaryIncreaseView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getDoSalaryIncrease()) {
            //Not directly accessible through the table because sometimes the table does not exist (when the employee is new for example)
            String employeeName = toolbar.getNameComboBox().getSelectedItem().toString();
            int employeeID = employeeDataManager.getEmployeeByName(employeeName).getId();
            ArrayList<Integer> employeeIDList = new ArrayList<>();
            employeeIDList.add(Integer.valueOf(employeeID));
            new IncreaseSalaryDialogController(frameController, employeeIDList);
        } else if (e.getSource() == toolbar.getEditSalaryEntry()) {
            String employeeName = toolbar.getNameComboBox().getSelectedItem().toString();
            int employeeID = employeeDataManager.getEmployeeByName(employeeName).getId();
            ArrayList<Integer> employeeIDList = new ArrayList<>();
            employeeIDList.add(Integer.valueOf(employeeID));
            String [] currentselectedRow = salaryIncreaseController.getSalaryIncreaseView().getSalaryIncreaseTable().getCurrentSelectedRowsAsArray()[0];
            String[] fillingData = new String[6];
            fillingData[0] = currentselectedRow[1];
            fillingData[1] = currentselectedRow[2];
            fillingData[2] = currentselectedRow[3];
            fillingData[3] = currentselectedRow[4];
            fillingData[4] = currentselectedRow[5];
            fillingData[5] = currentselectedRow[6];
            new IncreaseSalaryDialogController(frameController, employeeIDList).fillFields(fillingData);
        } else if (e.getSource() == toolbar.getDeleteSalaryEntry()) {
            Object[] options = {"Ok", "Abbrechen"};
            int joptionResult = JOptionPane.showOptionDialog(null, "Sind Sie sicher, dass die ausgewählten Gehaltseinträge gelöscht werden sollen?", "Warnung", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (joptionResult == 0) {
                salaryIncreaseController.deleteEntries(salaryIncreaseController.getSalaryIncreaseView().getSalaryIncreaseTable().getCurrentSelectedRowsAsArray());
            }
        } else if (e.getSource() == toolbar.getExportToCSV()) {
            CSVExporter.createCSVVariableName(salaryIncreaseController.getSalaryIncreaseView().getSalaryIncreaseTable());
        }
    }

    public void update() {
        toolbar = new ToolbarSalaryIncreaseView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    public ToolbarSalaryIncreaseView getToolbar() {
        return toolbar;
    }
}
