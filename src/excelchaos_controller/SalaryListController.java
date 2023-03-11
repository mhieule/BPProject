package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.calculations.SalaryProjection;
import excelchaos_model.datamodel.employeedataoperations.EmployeeDataAccess;
import excelchaos_model.export.CSVExporter;
import excelchaos_view.SalaryListView;
import excelchaos_view.ShowSalaryStageDialogView;
import excelchaos_view.ToolbarSalaryListView;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class SalaryListController implements TableModelListener, ActionListener, ItemListener {
    private EmployeeDataAccess employeeDataAccess;
    private SalaryListView salaryListView;
    private ShowSalaryStageDialogView showSalaryStageDialogView;
    private ToolbarSalaryListView toolbar;
    private MainFrameController mainFrameController;
    private String title = "Gehaltsprojektion";

    public SalaryListController(MainFrameController mainFrameController) {
        this.mainFrameController = mainFrameController;
        employeeDataAccess = new EmployeeDataAccess();
        salaryListView = new SalaryListView();
        salaryListView.init();
        salaryListView.createTableWithData(employeeDataAccess.getSalaryDataFromDataBase());
        salaryListView.getTable().getModel().addTableModelListener(this);
        toolbar = new ToolbarSalaryListView();
        toolbar.init();
        toolbar.setActionListener(this);
        toolbar.setItemListener(this);
        salaryListView.add(toolbar, BorderLayout.NORTH);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryListView.getTable(), toolbar);
    }

    public void showSalaryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, salaryListView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    public void updateData() {
        salaryListView.updateTable(employeeDataAccess.getSalaryDataFromDataBase());
        salaryListView.getTable().getModel().addTableModelListener(this);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryListView.getTable(), toolbar);
        toolbar.getEditEntry().setEnabled(false);

    }

    public void buildFuturePayLevelTable(String[][] tableData) {
        salaryListView.changeToFuturePayLevelTable(tableData);
        salaryListView.getTable().getModel().addTableModelListener(this);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryListView.getTable(), toolbar);
        toolbar.getEditEntry().setEnabled(false);
    }

    public void buildPayLevelTableBasedOnChosenDate(String[][] tableData) {
        salaryListView.changeToProjectedSalaryTable(tableData);
        salaryListView.getTable().getModel().addTableModelListener(this);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryListView.getTable(), toolbar);
        toolbar.getEditEntry().setEnabled(false);
    }

    public SalaryListView getSalaryListView() {
        return salaryListView;
    }

    ;

    @Override
    public void tableChanged(TableModelEvent e) {
        int numberOfSelectedRows = salaryListView.getTable().getNumberOfSelectedRows();
        if (e.getColumn() == 0) {
            if (numberOfSelectedRows == 0) {
                toolbar.getEditEntry().setEnabled(false);
            } else if (numberOfSelectedRows == 1) {
                toolbar.getEditEntry().setEnabled(true);
            } else {
               toolbar.getEditEntry().setEnabled(false);
            }
        }
    }

    //TODO komplett überarbeiten, hier ist noch ein Bug (siehe Word Dokument)
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getEditEntry()) {
            InsertSalaryController insertSalaryController = new InsertSalaryController(mainFrameController);
            String employeeID = salaryListView.getTable().getIdsOfCurrentSelectedRows()[0];
            insertSalaryController.fillFields(Integer.parseInt(employeeID));
            insertSalaryController.showInsertSalaryView(mainFrameController);
            toolbar.getShowNextPayGrade().setSelected(false);
            toolbar.getRemoveAdditionalSalaryStage().setEnabled(false);
        } else if (e.getSource() == toolbar.getSalaryStageOn()) {
            showSalaryStageDialogView = new ShowSalaryStageDialogView();
            showSalaryStageDialogView.init();
            showSalaryStageDialogView.setActionListener(this);
        } else if (e.getSource() == toolbar.getRemoveAdditionalSalaryStage()) {
            updateData();
            toolbar.getRemoveAdditionalSalaryStage().setEnabled(false);
        } else if (e.getSource() == toolbar.getIncreaseSalary()) {
            toolbar.getRemoveAdditionalSalaryStage().setEnabled(false);
            //increaseSalaryDialogController = new IncreaseSalaryDialogController(frameController);
        } else if (e.getSource() == toolbar.getExportToCSV()) {
            CSVExporter.createCSVVariableName(salaryListView.getTable()); //TODO Hier muss ganz neue Funktionalität implementiert werden
        } else if (e.getSource() == showSalaryStageDialogView.getCloseButton()) {
            showSalaryStageDialogView.dispose();
        } else if (e.getSource() == showSalaryStageDialogView.getOkayButton()) {
            LocalDate localDate = showSalaryStageDialogView.getDatePicker().getDate();
            if (localDate != null) {
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                SalaryProjection salaryProjection = new SalaryProjection();
                buildPayLevelTableBasedOnChosenDate(salaryProjection.getSalaryProjectionForGivenDate(date));
                showSalaryStageDialogView.dispose();
                toolbar.getRemoveAdditionalSalaryStage().setEnabled(true);
                toolbar.getShowNextPayGrade().setSelected(false);
            } else showSalaryStageDialogView.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            SalaryProjection salaryProjection = new SalaryProjection();
            buildFuturePayLevelTable(salaryProjection.getNextPayLevelProjection());
            toolbar.getRemoveAdditionalSalaryStage().setEnabled(false);
        } else {
            updateData();
        }
    }
}
