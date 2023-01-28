package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_view.ManualSalaryEntryView;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    public void getDataFromDB(Employee temporaryEmployee){
        DefaultTableModel tableModel = new DefaultTableModel(null,columns);

        ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();
        StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        int id = temporaryEmployee.getId();
        int rowCount = manualSalaryEntryManager.getRowCount(id);



        List<ManualSalaryEntry> manualSalaryEntryList = manualSalaryEntryManager.getManualSalaryEntry(id);
        for (ManualSalaryEntry entry : manualSalaryEntryList){
            String salary = transformer.formatDoubleToString(entry.getNew_salary(),1);
            String usageDate = dateFormat.format(entry.getStart_date());
            String comment = entry.getComment();
            String[] values = {temporaryEmployee.getName(),temporaryEmployee.getSurname(),salary,usageDate,comment};
            tableModel.addRow(values);


        }

        defaultTableModel = tableModel;
        salaryEntryView.getTable().setModel(defaultTableModel);

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        Employee temporaryEmployee = employeeDataManager.getEmployeeByName((String) e.getItem());
        getDataFromDB(temporaryEmployee);
    }
}
