package excelchaos_controller;

import excelchaos_model.export.CSVExporter;
import excelchaos_view.toolbarviews.ToolbarManualSalaryEntryView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarManualSalaryEntryController implements ActionListener {
    private ToolbarManualSalaryEntryView toolbar;
    private ManualSalaryEntryController manualSalaryEntryController;
    private MainFrameController frameController;

    /**
     * Constructor for ToolbarManualSalaryEntryController
     *
     * @param mainFrameController         mainFrameController
     * @param manualSalaryEntryController manualSalaryEntryController
     */
    public ToolbarManualSalaryEntryController(MainFrameController mainFrameController, ManualSalaryEntryController manualSalaryEntryController) {
        frameController = mainFrameController;
        this.manualSalaryEntryController = manualSalaryEntryController;
        toolbar = new ToolbarManualSalaryEntryView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    /**
     * Depending on the source of the event, the corresponding action is performed
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getAddSalaryEntry()) {
            new InsertManualSalaryEntryController(frameController, (String) toolbar.getNameComboBox().getSelectedItem()).showInsertManualSalaryEntryView(frameController);
        } else if (e.getSource() == toolbar.getEditSalaryEntry()) {
            InsertManualSalaryEntryController insertManualSalaryEntryController = new InsertManualSalaryEntryController(frameController, (String) toolbar.getNameComboBox().getSelectedItem(), manualSalaryEntryController.getSalaryEntryView().getManualSalaryEntryTable().getCurrentSelectedRowsAsArray());
            insertManualSalaryEntryController.showInsertManualSalaryEntryView(frameController);
        } else if (e.getSource() == toolbar.getDeleteSalaryEntry()) {
            Object[] options = {"Ok", "Abbrechen"};
            int joptionResult = JOptionPane.showOptionDialog(null, "Sind Sie sicher, dass die ausgewählten Gehaltseinträge gelöscht werden sollen?", "Warnung", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (joptionResult == 0) {
                manualSalaryEntryController.deleteEntries(manualSalaryEntryController.getSalaryEntryView().getManualSalaryEntryTable().getCurrentSelectedRowsAsArray());
            }
        } else if (e.getSource() == toolbar.getExportToCSV()) {
            CSVExporter.createCSVVariableName(manualSalaryEntryController.getSalaryEntryView().getManualSalaryEntryTable());
        }
    }

    /**
     * Updates the toolbar
     */
    public void update() {
        toolbar = new ToolbarManualSalaryEntryView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    public ToolbarManualSalaryEntryView getToolbar() {
        return toolbar;
    }
}
