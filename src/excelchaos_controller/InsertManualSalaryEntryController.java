package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_view.InsertManualSalaryEntryView;
import excelchaos_view.ManualSalaryEntryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class InsertManualSalaryEntryController implements ActionListener {

    private String title = "Gehaltseintrag hinzufügen";

    private InsertManualSalaryEntryView insertManualSalaryEntryView;

    private MainFrameController frameController;

    public InsertManualSalaryEntryController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        insertManualSalaryEntryView = new InsertManualSalaryEntryView();
        insertManualSalaryEntryView.init();
        insertManualSalaryEntryView.setActionListener(this);

    }

    public void showInsertManualSalaryEntryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            //ActionLogEintrag
            mainFrameController.addTab(title, insertManualSalaryEntryView);
            //mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }

    }

    private void insertEntryInDB(){
        ManualSalaryEntryManager manager = new ManualSalaryEntryManager();
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
        Calendar calendar = Calendar.getInstance();
        Employee temporaryEmployee = employeeDataManager.getEmployeeByName((String) insertManualSalaryEntryView.getNamePickList().getSelectedItem());
        int id = temporaryEmployee.getId();
        double newSalary =  transformer.transformStringToDouble(insertManualSalaryEntryView.getTfNewSalary().getText());
        LocalDate date  = insertManualSalaryEntryView.getDatePicker().getDate();
        calendar.set(date.getYear(),date.getMonth().getValue(),date.getDayOfMonth());
        Date usageDate = calendar.getTime();
        String comment = insertManualSalaryEntryView.getTfComment().getText();
        ManualSalaryEntry manualSalaryEntry = new ManualSalaryEntry(id,newSalary,usageDate,comment);
        manager.addManualSalaryEntry(manualSalaryEntry);
        insertManualSalaryEntryView.revalidate();
        insertManualSalaryEntryView.repaint();
        frameController.getManualSalaryEntryController().getDataFromDB(temporaryEmployee);

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
            resetInputs();
            frameController.getTabs().removeTabNewWindow(insertManualSalaryEntryView);
        } else if (e.getSource() == insertManualSalaryEntryView.getSubmit()) {
            insertEntryInDB();
            resetInputs();
        } else if (e.getSource() == insertManualSalaryEntryView.getReset()) {
            resetInputs();
        }
    }
}
