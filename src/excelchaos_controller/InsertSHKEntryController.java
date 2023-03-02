package excelchaos_controller;


import excelchaos_model.database.SHKSalaryEntry;
import excelchaos_model.database.SHKSalaryTableManager;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.InsertSHKEntryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InsertSHKEntryController implements ActionListener {

    private SHKSalaryTableManager shkSalaryTableManager = SHKSalaryTableManager.getInstance();

    private String title = "SHK Stundensätze hinzufügen";

    private InsertSHKEntryView insertSHKEntryView;

    private MainFrameController frameController;

    private int currentEditingID = 0;

    public InsertSHKEntryController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        insertSHKEntryView = new InsertSHKEntryView();
        insertSHKEntryView.init();
        insertSHKEntryView.setActionListener(this);
    }

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

    public void showInsertSHKEntryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, insertSHKEntryView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }

    }

    private void insertEntryInDB(){
        LocalDate localDate = insertSHKEntryView.getDatePicker().getDate();
        Date validationDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        BigDecimal basePayRate = StringAndDoubleTransformationForDatabase.formatStringToBigDecimalCurrency(insertSHKEntryView.getBasePayRateTextfield().getText());
        BigDecimal extendedPayRate = StringAndDoubleTransformationForDatabase.formatStringToBigDecimalCurrency(insertSHKEntryView.getExtendedPayRateTextField().getText());
        BigDecimal whkPayRate = StringAndDoubleTransformationForDatabase.formatStringToBigDecimalCurrency(insertSHKEntryView.getWHKPayRateTextfield().getText());
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

    private void resetInputs(){
        insertSHKEntryView.getDatePicker().setText(null);
        insertSHKEntryView.getBasePayRateTextfield().setText(null);
        insertSHKEntryView.getExtendedPayRateTextField().setText(null);
        insertSHKEntryView.getWHKPayRateTextfield().setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == insertSHKEntryView.getCancel()){
            frameController.getTabs().removeTabNewWindow(insertSHKEntryView);
        } else if (e.getSource() == insertSHKEntryView.getSubmitAndClose()) {
            insertEntryInDB();
            resetInputs();//TODO Wahrscheinlich SalaryUpdate Aufrufen
            frameController.getTabs().removeTabNewWindow(insertSHKEntryView);
        } else if (e.getSource() == insertSHKEntryView.getSubmit()) {
            insertEntryInDB();//TODO Wahrscheinlich SalaryUpdate Aufrufen
            resetInputs();
        } else if (e.getSource() == insertSHKEntryView.getReset()) {
            resetInputs();
        }
    }
}
