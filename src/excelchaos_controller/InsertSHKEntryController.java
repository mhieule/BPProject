package excelchaos_controller;


import excelchaos_model.database.SHKSalaryEntry;
import excelchaos_model.database.SHKSalaryTableManager;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.InsertSHKEntryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InsertSHKEntryController implements ActionListener {
    private SHKSalaryTableManager shkSalaryTableManager = new SHKSalaryTableManager();
    private String title = "SHK Stundensätze hinzufügen";
    private InsertSHKEntryView insertSHKEntryView;
    private MainFrameController frameController;
    private int currentEditingID = 0;

    /**
     * Constructor for the InsertSHKEntryController
     * @param mainFrameController the mainframecontroller
     */
    public InsertSHKEntryController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        insertSHKEntryView = new InsertSHKEntryView();
        insertSHKEntryView.init();
        insertSHKEntryView.setActionListener(this);
    }

    /**
     * Constructor for the InsertSHKEntryController with the data to be viewed
     * @param mainFrameController the mainframecontroller
     * @param data the data to be viewed
     */
    public InsertSHKEntryController(MainFrameController mainFrameController, String[][] data){
        frameController = mainFrameController;
        insertSHKEntryView = new InsertSHKEntryView();
        insertSHKEntryView.init();
        insertSHKEntryView.setActionListener(this);
        currentEditingID = Integer.parseInt(data[0][0]);
        insertSHKEntryView.getDatePicker().setText(data[0][1]);
        insertSHKEntryView.getBasePayRateTextfield().setText(data[0][2]);
        insertSHKEntryView.getExtendedPayRateTextField().setText(data[0][3]);
        insertSHKEntryView.getWHKPayRateTextfield().setText(data[0][4]);
    }

    /**
     * Adds tab with insertSHKEntryView.
     * @param mainFrameController the mainframecontroller
     */
    public void showInsertSHKEntryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, insertSHKEntryView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    /**
     * Inserts the data into the database
     */
    private void insertEntryInDB(){
        LocalDate localDate = insertSHKEntryView.getDatePicker().getDate();
        Date validationDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        BigDecimal basePayRate = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(insertSHKEntryView.getBasePayRateTextfield().getText());
        BigDecimal extendedPayRate = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(insertSHKEntryView.getExtendedPayRateTextField().getText());
        BigDecimal whkPayRate = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(insertSHKEntryView.getWHKPayRateTextfield().getText());
        if(currentEditingID != 0){
            SHKSalaryEntry updateEntry = shkSalaryTableManager.getSHKSalaryEntry(currentEditingID);
            updateEntry.setValidationDate(validationDate);
            updateEntry.setBasePayRate(basePayRate);
            updateEntry.setExtendedPayRate(extendedPayRate);
            updateEntry.setWHKPayRate(whkPayRate);
            shkSalaryTableManager.updateSHKSalaryEntry(updateEntry);
        } else {
            currentEditingID = shkSalaryTableManager.getNextID();
            SHKSalaryEntry newEntry = new SHKSalaryEntry(currentEditingID,validationDate,basePayRate,extendedPayRate,whkPayRate);
            shkSalaryTableManager.addSHKTableEntry(newEntry);
        }
        insertSHKEntryView.revalidate();
        insertSHKEntryView.repaint();
        ShowSHKTableController showSHKTableController = frameController.getShowSHKTableController();
        showSHKTableController.updateData(showSHKTableController.getSHKDataFromDatabase());
        showSHKTableController.getToolbarShowSHKTableController().getToolbar().getDeleteEntry().setEnabled(false);
        showSHKTableController.getToolbarShowSHKTableController().getToolbar().getEditEntry().setEnabled(false);
    }

    /**
     * Resets the inputs
     */
    private void resetInputs(){
        insertSHKEntryView.getDatePicker().setText(null);
        insertSHKEntryView.getBasePayRateTextfield().setText(null);
        insertSHKEntryView.getExtendedPayRateTextField().setText(null);
        insertSHKEntryView.getWHKPayRateTextfield().setText(null);
    }

    /**
     * Depending on the event e cancels, submits or resets the inputs
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == insertSHKEntryView.getCancel()){
            frameController.getTabs().removeTabNewWindow(insertSHKEntryView);
        } else if (e.getSource() == insertSHKEntryView.getSubmitAndClose()) {
            insertEntryInDB();
            resetInputs();
            frameController.getTabs().removeTabNewWindow(insertSHKEntryView);
        } else if (e.getSource() == insertSHKEntryView.getSubmit()) {
            insertEntryInDB();
            resetInputs();
        } else if (e.getSource() == insertSHKEntryView.getReset()) {
            resetInputs();
        }
    }
}
