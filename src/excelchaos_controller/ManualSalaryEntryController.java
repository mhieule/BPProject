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

    private CustomTableModel defaultTableModel;

    String columns[] = {"Gültig ab","Gehalt", "Kommentar"};
    private String title = "Manuelle Gehaltseinträge";

    public ManualSalaryEntryController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        salaryEntryView = new ManualSalaryEntryView();
        toolbarManualSalaryEntry = new ToolbarManualSalaryEntryController(frameController);
        toolbarManualSalaryEntry.getToolbar().setItemListener(this);
        salaryEntryView.init();
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

    public String[][] getDataFromDB(Employee temporaryEmployee) {

        ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();
        StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        int id = temporaryEmployee.getId();
        int rowCount = manualSalaryEntryManager.getRowCount(id);
        String resultData[][] = new String[rowCount][];
        int currentIndex = 0;

        List<ManualSalaryEntry> manualSalaryEntryList = manualSalaryEntryManager.getManualSalaryEntry(id);
        for (ManualSalaryEntry entry : manualSalaryEntryList) {
            String salary = transformer.formatDoubleToString(entry.getNew_salary(), 1);
            String usageDate = dateFormat.format(entry.getStart_date());
            String comment = entry.getComment();
            String[] values = {usageDate,salary, comment};
            resultData[currentIndex] = values;
            currentIndex++;

        }

        return resultData;


    }

    private void createTableWithData(String[][] data) {
        salaryEntryView.createTable(data,columns);
    }

    private void setTableData(String[][] data) {
        defaultTableModel = new CustomTableModel(data, columns);
        salaryEntryView.getTable().setModel(defaultTableModel);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        if (!((String) e.getItem()).equals("Keine Auswahl")) {
            Employee temporaryEmployee = employeeDataManager.getEmployeeByName((String) e.getItem());
            String[][] data = getDataFromDB(temporaryEmployee);
            if (salaryEntryView.getTable() == null) {
                createTableWithData(data);
                frameController.getTabs().setLabel(title + " " + temporaryEmployee.getSurname()+" " + temporaryEmployee.getName());
            } else {
                setTableData(data);
                frameController.getTabs().setLabel(title + " " + temporaryEmployee.getSurname()+" " + temporaryEmployee.getName());
            }
        } else {
            if(salaryEntryView.getTable() != null){
                salaryEntryView.getTable().setModel(new DefaultTableModel(null, columns));
                frameController.getTabs().setLabel(title);
            }

        }

    }
}
