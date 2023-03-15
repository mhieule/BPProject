package excelchaos_controller;

import excelchaos_model.database.Employee;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.database.ManualSalaryEntry;
import excelchaos_model.database.ManualSalaryEntryManager;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.InsertManualSalaryEntryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InsertManualSalaryEntryController implements ActionListener {
    private ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private String title = "Gehaltseintrag hinzufügen";
    private InsertManualSalaryEntryView insertManualSalaryEntryView;
    private MainFrameController frameController;
    private int currentEditingID = 0;
    private Date editingDate;

    /**
     * Constructor for the InsertManualSalaryEntryController
     * @param mainFrameController the main frame controller
     * @param currentEntry the currently selected Name as String
     */
    public InsertManualSalaryEntryController(MainFrameController mainFrameController, String currentEntry) {
        frameController = mainFrameController;
        insertManualSalaryEntryView = new InsertManualSalaryEntryView();
        insertManualSalaryEntryView.init();
        insertManualSalaryEntryView.setActionListener(this);
        insertManualSalaryEntryView.getNamePickList().setSelectedItem(currentEntry);
    }

    /**
     * Constructor for the InsertManualSalaryEntryController
     * @param mainFrameController the main frame controller
     * @param currentEntry the currently selected Name as String
     * @param data the data corresponding to the currently selected entry
     */
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

    /**
     * Depending on e calls Method adds a tab of insertManualSalaryEntryView to the mainFrameController
     * @param mainFrameController the main frame controller
     */
    public void showInsertManualSalaryEntryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, insertManualSalaryEntryView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    /**
     * This method inserts a new manual salary entry into the database. It first retrieves the necessary information
     * from the GUI elements, creates a new ManualSalaryEntry object, removes the current entry if in editing mode,
     * adds the new entry to the database, and updates the GUI table.
     */
    private void insertEntryInDB(){
        Employee temporaryEmployee = employeeDataManager.getEmployeeByName((String) insertManualSalaryEntryView.getNamePickList().getSelectedItem());
        int id = temporaryEmployee.getId();
        BigDecimal newSalary =  StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(insertManualSalaryEntryView.getTfNewSalary().getText());
        LocalDate date  = insertManualSalaryEntryView.getDatePicker().getDate();
        Date usageDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String comment = insertManualSalaryEntryView.getTfComment().getText();
        ManualSalaryEntry manualSalaryEntry = new ManualSalaryEntry(id,newSalary,usageDate,comment);
        if(currentEditingID != 0){
            manualSalaryEntryManager.removeManualSalaryEntry(currentEditingID,editingDate);
        }
        manualSalaryEntryManager.addManualSalaryEntry(manualSalaryEntry);
        insertManualSalaryEntryView.revalidate();
        insertManualSalaryEntryView.repaint();
        ManualSalaryEntryController manualSalaryEntryController = frameController.getManualSalaryEntryController();
        manualSalaryEntryController.setTableData(manualSalaryEntryController.getDataFromDB(temporaryEmployee));
    }

    /**
     * This method resets the input fields of insertManualSalaryEntryView.
     */
    private void resetInputs(){
        insertManualSalaryEntryView.getTfNewSalary().setText(null);
        insertManualSalaryEntryView.getDatePicker().setText(null);
        insertManualSalaryEntryView.getTfComment().setText(null);
    }

    /**
     * Depending on the performed action e it cancels, submits, resets or closes the insertManualSalaryEntryView
     * @param e the event to be processed
     */
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
