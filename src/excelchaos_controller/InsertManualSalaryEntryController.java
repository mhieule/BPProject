package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.InsertManualSalaryEntryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class InsertManualSalaryEntryController implements ActionListener {

    private String title = "Gehaltseintrag hinzufügen";

    private InsertManualSalaryEntryView insertManualSalaryEntryView;

    private MainFrameController frameController;

    private int currentEditingID = 0;

    private Date editingDate;

    public InsertManualSalaryEntryController(MainFrameController mainFrameController, String currentEntry) {
        frameController = mainFrameController;
        insertManualSalaryEntryView = new InsertManualSalaryEntryView();
        insertManualSalaryEntryView.init();
        insertManualSalaryEntryView.setActionListener(this);
        insertManualSalaryEntryView.getNamePickList().setSelectedItem(currentEntry);

    }

    public InsertManualSalaryEntryController(MainFrameController mainFrameController,String currentEntry,String[][] data){
        frameController = mainFrameController;
        insertManualSalaryEntryView = new InsertManualSalaryEntryView();
        insertManualSalaryEntryView.init();
        insertManualSalaryEntryView.setActionListener(this);
        insertManualSalaryEntryView.getNamePickList().setSelectedItem(currentEntry);
        currentEditingID = Integer.parseInt(data[0][0]);
        insertManualSalaryEntryView.getDatePicker().setText(data[0][1]);
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            editingDate = format.parse(data[0][1]);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        insertManualSalaryEntryView.getTfNewSalary().setText(data[0][2]);
        insertManualSalaryEntryView.getTfComment().setText(data[0][3]);
    }

    public void showInsertManualSalaryEntryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, insertManualSalaryEntryView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }

    }

    private void insertEntryInDB(){
        ManualSalaryEntryManager manager = new ManualSalaryEntryManager();
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
        Employee temporaryEmployee = employeeDataManager.getEmployeeByName((String) insertManualSalaryEntryView.getNamePickList().getSelectedItem());
        int id = temporaryEmployee.getId();
        double newSalary =  transformer.transformStringToDouble(insertManualSalaryEntryView.getTfNewSalary().getText());
        LocalDate date  = insertManualSalaryEntryView.getDatePicker().getDate();
        Date usageDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String comment = insertManualSalaryEntryView.getTfComment().getText();
        ManualSalaryEntry manualSalaryEntry = new ManualSalaryEntry(id,newSalary,usageDate,comment);
        if(currentEditingID != 0){
            manager.removeManualSalaryEntry(currentEditingID,editingDate);
        }
        manager.addManualSalaryEntry(manualSalaryEntry);
        insertManualSalaryEntryView.revalidate();
        insertManualSalaryEntryView.repaint();
        ManualSalaryEntryController manualSalaryEntryController = frameController.getManualSalaryEntryController();
        manualSalaryEntryController.setTableData(manualSalaryEntryController.getDataFromDB(temporaryEmployee));
    }

    private void resetInputs(){
        insertManualSalaryEntryView.getTfNewSalary().setText(null);
        insertManualSalaryEntryView.getDatePicker().setText(null);
        insertManualSalaryEntryView.getTfComment().setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertManualSalaryEntryView.getCancel()){
            frameController.getTabs().removeTabNewWindow(insertManualSalaryEntryView);
        } else if (e.getSource() == insertManualSalaryEntryView.getSubmitAndClose()) {
            insertEntryInDB();
            frameController.getUpdater().salaryUpDate();
            resetInputs();
            frameController.getTabs().removeTabNewWindow(insertManualSalaryEntryView);
        } else if (e.getSource() == insertManualSalaryEntryView.getSubmit()) {
            insertEntryInDB();
            frameController.getUpdater().salaryUpDate();
            resetInputs();
        } else if (e.getSource() == insertManualSalaryEntryView.getReset()) {
            resetInputs();
        }
    }
}
