package excelchaos_controller;

import excelchaos_model.export.CSVExporter;
import excelchaos_view.ToolbarShowPersonView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarShowPersonController implements ActionListener {
    private ToolbarShowPersonView toolbar;
    private MainFrameController frameController;

    private ShowPersonController showPersonController;

    public ToolbarShowPersonController(MainFrameController mainFrameController, ShowPersonController showPersonController) {
        frameController = mainFrameController;
        this.showPersonController = showPersonController;
        toolbar = new ToolbarShowPersonView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    public ToolbarShowPersonView getToolbar() {
        return toolbar;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getInsertPerson()) {
            InsertPersonController insertPersonController = new InsertPersonController(frameController);
            insertPersonController.showInsertPersonView(frameController);
        } else if (e.getSource() == toolbar.getEditPerson()) {
            InsertPersonController insertPersonController = new InsertPersonController(frameController);
            String employeeID = showPersonController.getPersonView().getTable().getIdsOfCurrentSelectedRows()[0];
            insertPersonController.fillFields(employeeID);
            insertPersonController.showInsertPersonView(frameController);
        } else if (e.getSource() == toolbar.getDeletePerson()) {
            Object[] options = {"Ok", "Abbrechen"};
            int joptionResult = JOptionPane.showOptionDialog(null, "Sind Sie sicher, dass die ausgewählten Mitarbeiter gelöscht werden sollen?", "Warnung", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (joptionResult == 0) {
                String[] employeeIds = showPersonController.getPersonView().getTable().getIdsOfCurrentSelectedRows();
                int[] Ids = new int[employeeIds.length];
                for (int i = 0; i < employeeIds.length; i++) {
                    Ids[i] = Integer.parseInt(employeeIds[i]);
                }
                showPersonController.deleteData(Ids);
            }


        } else if (e.getSource() == toolbar.getExportToCSV()) {
            CSVExporter.createCSV(showPersonController.getPersonView().getTable(), "Personendaten.csv");

        }
    }
}
