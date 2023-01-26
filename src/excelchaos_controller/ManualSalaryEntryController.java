package excelchaos_controller;

import excelchaos_model.Employee;
import excelchaos_model.EmployeeDataManager;
import excelchaos_view.ManualSalaryEntryView;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ManualSalaryEntryController implements ItemListener {
    private ManualSalaryEntryView salaryEntryView;
    private ToolbarManualSalaryEntryController toolbarManualSalaryEntry;
    private MainFrameController frameController;

    private DefaultTableModel defaultTableModel;

    String columns[] = {"Name", "Vorname", "Gehalt", "Gültig ab", "Kommentar"};
    private String title = "Manuelle Gehaltseinträge";

    public ManualSalaryEntryController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        salaryEntryView = new ManualSalaryEntryView();
        toolbarManualSalaryEntry = new ToolbarManualSalaryEntryController(frameController);
        toolbarManualSalaryEntry.getToolbar().setItemListener(this);
        defaultTableModel = new DefaultTableModel(null,columns);
        salaryEntryView.init(defaultTableModel);
        salaryEntryView.add(toolbarManualSalaryEntry.getToolbar(), BorderLayout.NORTH);

    }

    public void showManualSalaryEntryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            //ActionLogEintrag
            mainFrameController.addTab(title, salaryEntryView);
            //mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        DefaultTableModel tableModel = new DefaultTableModel(null,columns);
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        String[] rowData = new String[5];
        Employee temporaryEmployee = employeeDataManager.getEmployeeByName((String) e.getItem());
        rowData[0] = temporaryEmployee.getName();
        rowData[1] = temporaryEmployee.getSurname();
        rowData[2] = null;
        rowData[3] = null;
        rowData[4] = null;
        tableModel.addRow(rowData);
        defaultTableModel = tableModel;
        salaryEntryView.getTable().setModel(defaultTableModel);
    }
}
