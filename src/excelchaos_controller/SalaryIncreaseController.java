package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.SalaryIncreaseView;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class SalaryIncreaseController implements ItemListener {

    private SalaryIncreaseView salaryIncreaseView;

    private ToolbarSalaryIncreaseController toolbarSalaryIncreaseController;

    private MainFrameController frameController;

    private CustomTableModel customTableModel;

    String columns[] = {"Gültig ab","Neues Gehalt","Kommentar","Sonderzahlung"};

    private String title = "Gehaltserhöhung";

    public SalaryIncreaseController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        salaryIncreaseView = new SalaryIncreaseView();
        toolbarSalaryIncreaseController = new ToolbarSalaryIncreaseController(frameController);
        toolbarSalaryIncreaseController.getToolbar().setItemListener(this);
        salaryIncreaseView.init();
        salaryIncreaseView.add(toolbarSalaryIncreaseController.getToolbar(), BorderLayout.NORTH);
    }
    public void showManualSalaryEntryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, salaryIncreaseView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    private void createTableWithData(String[][] data) {
        salaryIncreaseView.createTable(data,columns);
    }

    private void setTableData(String[][] data) {
        customTableModel = new CustomTableModel(data, columns);
        salaryIncreaseView.getTable().setModel(customTableModel);
    }

    public String[][] getDataFromDB(Employee temporaryEmployee) {

        SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();
        StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        int id = temporaryEmployee.getId();
        int rowCount = salaryIncreaseHistoryManager.getRowCount(id);
        String resultData[][] = new String[rowCount][];
        int currentIndex = 0;

        List<SalaryIncreaseHistory> salaryIncreaseHistory = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id);
        for (SalaryIncreaseHistory entry : salaryIncreaseHistory) {
            String salary = transformer.formatDoubleToString(entry.getNew_salary(), 1);
            String usageDate = dateFormat.format(entry.getStart_date());
            String comment = entry.getComment();
            String specialPayment;
            if(entry.getIs_additional_payment()){
                specialPayment = "Ja";
            } else specialPayment = "Nein";
            String[] values = {usageDate,salary, comment,specialPayment};
            resultData[currentIndex] = values;
            currentIndex++;

        }

        return resultData;


    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        if (!((String) e.getItem()).equals("Keine Auswahl")) {
            Employee temporaryEmployee = employeeDataManager.getEmployeeByName((String) e.getItem());
            String[][] data = getDataFromDB(temporaryEmployee);
            if (salaryIncreaseView.getTable() == null) {
                createTableWithData(data);
                frameController.getTabs().setLabel(title + " " + temporaryEmployee.getSurname()+" " + temporaryEmployee.getName());
            } else {
                setTableData(data);
                frameController.getTabs().setLabel(title + " " + temporaryEmployee.getSurname()+" " + temporaryEmployee.getName());
            }
        } else {
            if(salaryIncreaseView.getTable() != null){
                salaryIncreaseView.getTable().setModel(new DefaultTableModel(null, columns));
                frameController.getTabs().setLabel(title);
            }

        }

    }
}
