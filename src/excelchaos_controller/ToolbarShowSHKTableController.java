package excelchaos_controller;

import excelchaos_view.ToolbarShowSHKTableView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarShowSHKTableController implements ActionListener {

    private ToolbarShowSHKTableView toolbar;

    private ShowSHKTableController showSHKTableController;

    private MainFrameController frameController;

    public ToolbarShowSHKTableController(MainFrameController mainFrameController, ShowSHKTableController showSHKTableController) {
        frameController = mainFrameController;
        this.showSHKTableController = showSHKTableController;
        toolbar = new ToolbarShowSHKTableView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    public ToolbarShowSHKTableView getToolbar() {
        return toolbar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getAddEntry()) {
            InsertSHKEntryController insertSHKEntryController = new InsertSHKEntryController(frameController);
            insertSHKEntryController.showInsertSHKEntryView(frameController);
        } else if (e.getSource() == toolbar.getEditEntry()) {
            InsertSHKEntryController insertSHKEntryController = new InsertSHKEntryController(frameController,showSHKTableController.getShowSHKTableView().getTable().getCurrentSelectedRowsAsArray());
            insertSHKEntryController.showInsertSHKEntryView(frameController);
        } else if (e.getSource() == toolbar.getDeleteEntry()) {
            Object[] options = {"Ok", "Abbrechen"};
            int joptionResult = JOptionPane.showOptionDialog(null, "Sind Sie sicher, dass die ausgewählten SHK Stundensätze gelöscht werden sollen?", "Warnung", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (joptionResult == 0) {
                showSHKTableController.deleteSHKEntries(showSHKTableController.getShowSHKTableView().getTable().getIdsOfCurrentSelectedRows());
            }
        }
    }
}
