package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.database.*;
import excelchaos_model.datamodel.EmployeeDataAccess;
import excelchaos_model.datamodel.EmployeeDataDeleter;
import excelchaos_model.export.CSVExporter;
import excelchaos_view.ShowPersonView;
import excelchaos_view.ToolbarShowPersonView;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowPersonController implements ActionListener, TableModelListener {

    private EmployeeDataAccess employeeDataModel;
    private EmployeeDataDeleter employeeDataDeleter;
    private ShowPersonView showPersonView;
    private ToolbarShowPersonView toolbar;
    private MainFrameController mainFrameController;

    private String title = "Personalstammdaten";


    public ShowPersonController(MainFrameController mainFrameController) {
        this.mainFrameController = mainFrameController;
        employeeDataModel = new EmployeeDataAccess();
        employeeDataDeleter = new EmployeeDataDeleter();
        showPersonView = new ShowPersonView();
        toolbar = new ToolbarShowPersonView();
        showPersonView.init();
        toolbar.init();
        toolbar.setActionListener(this);
        showPersonView.createTableWithData(employeeDataModel.getEmployeeDataFromDataBase());
        showPersonView.getTable().getModel().addTableModelListener(this);
        showPersonView.add(toolbar, BorderLayout.NORTH);
        SearchAndFilterModel.setUpSearchAndFilterModel(showPersonView.getTable(), toolbar);
    }

    public ShowPersonView getPersonView() {
        return showPersonView;
    }

    public void showPersonView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, showPersonView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }





    public void updateData() {
        showPersonView.updateTable(employeeDataModel.getEmployeeDataFromDataBase());
        showPersonView.getTable().getModel().addTableModelListener(this);
        SearchAndFilterModel.setUpSearchAndFilterModel(showPersonView.getTable(),toolbar);
        toolbar.getEditPerson().setEnabled(false);
        toolbar.getDeletePerson().setEnabled(false);
    }

    public void deleteData(int[] employeeIds) {
        employeeDataDeleter.deleteData(employeeIds);
        updateData();
    }


    @Override
    public void tableChanged(TableModelEvent e) {
        int numberOfSelectedRows = showPersonView.getTable().getNumberOfSelectedRows();
        if (e.getColumn() == 0) {
            if (numberOfSelectedRows == 0) {
                toolbar.getEditPerson().setEnabled(false);
                toolbar.getDeletePerson().setEnabled(false);
            } else if (numberOfSelectedRows == 1) {
                toolbar.getEditPerson().setEnabled(true);
                toolbar.getDeletePerson().setEnabled(true);
            } else {
                toolbar.getEditPerson().setEnabled(false);
                toolbar.getDeletePerson().setEnabled(true);
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getInsertPerson()) {
            InsertPersonController insertPersonController = new InsertPersonController(mainFrameController);
            insertPersonController.showInsertPersonView(mainFrameController);
        } else if (e.getSource() == toolbar.getEditPerson()) {
            InsertPersonController insertPersonController = new InsertPersonController(mainFrameController);
            String employeeID = showPersonView.getTable().getIdsOfCurrentSelectedRows()[0];
            insertPersonController.fillFields(employeeID);
            insertPersonController.showInsertPersonView(mainFrameController);
        } else if (e.getSource() == toolbar.getDeletePerson()) {
            Object[] options = {"Ok", "Abbrechen"};
            int joptionResult = JOptionPane.showOptionDialog(null, "Sind Sie sicher, dass die ausgewählten Mitarbeiter gelöscht werden sollen?", "Warnung", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (joptionResult == 0) {
                String[] employeeIds = showPersonView.getTable().getIdsOfCurrentSelectedRows();
                int[] Ids = new int[employeeIds.length];
                for (int i = 0; i < employeeIds.length; i++) {
                    Ids[i] = Integer.parseInt(employeeIds[i]);
                }
                deleteData(Ids);
            }


        } else if (e.getSource() == toolbar.getExportToCSV()) {
            CSVExporter.createCSV(showPersonView.getTable(), "Personendaten.csv");

        }
    }
}
