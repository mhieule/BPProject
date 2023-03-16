package excelchaos_controller;

import excelchaos_model.calculations.SalaryProjection;
import excelchaos_model.database.Employee;
import excelchaos_model.datamodel.employeedataoperations.EmployeeDataAccess;
import excelchaos_model.export.CSVExporter;
import excelchaos_model.utility.SearchAndFilterModel;
import excelchaos_view.SalaryListView;
import excelchaos_view.ShowSalaryStageDialogView;
import excelchaos_view.toolbarviews.ToolbarSalaryListView;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class SalaryListController implements TableModelListener, ActionListener, ItemListener {
    private EmployeeDataAccess employeeDataAccess;
    private SalaryListView salaryListView;
    private ShowSalaryStageDialogView showSalaryStageDialogView;
    private ToolbarSalaryListView toolbar;
    private MainFrameController mainFrameController;
    private String title = "Gehaltsprojektion";

    /**
     * Constructor for SalaryListController
     *
     * @param mainFrameController MainFrameController
     */
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

    /**
     * Adds the SalaryListView to the MainFrameController
     *
     * @param mainFrameController MainFrameController
     */
    public void showSalaryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, salaryListView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    /**
     * updates the data of SalaryListView
     */
    public void updateData() {
        salaryListView.updateTable(employeeDataAccess.getSalaryDataFromDataBase());
        salaryListView.getTable().getModel().addTableModelListener(this);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryListView.getTable(), toolbar);
        toolbar.getEditEntry().setEnabled(false);

    }

    /**
     * Builds the table for the future pay level
     *
     * @param tableData String[][] with the data for the table
     */
    public void buildFuturePayLevelTable(String[][] tableData) {
        salaryListView.changeToFuturePayLevelTable(tableData);
        salaryListView.getTable().getModel().addTableModelListener(this);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryListView.getTable(), toolbar);
        toolbar.getEditEntry().setEnabled(false);
    }

    /**
     * Builds the table for the projected salary based on the chosen date
     *
     * @param tableData String[][] with the data for the table
     */
    public void buildPayLevelTableBasedOnChosenDate(String[][] tableData) {
        salaryListView.changeToProjectedSalaryTable(tableData);
        salaryListView.getTable().getModel().addTableModelListener(this);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryListView.getTable(), toolbar);
        toolbar.getEditEntry().setEnabled(false);
    }

    public SalaryListView getSalaryListView() {
        return salaryListView;
    }

    /**
     * @param e a {@code TableModelEvent} to notify listener that a table model
     *          has changed
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int numberOfSelectedRows = salaryListView.getTable().getNumberOfSelectedRows();
        if (e.getColumn() == 0) {
            if (numberOfSelectedRows == 0) {
                toolbar.getEditEntry().setEnabled(false);
                toolbar.getIncreaseSalary().setEnabled(false);
            } else if (numberOfSelectedRows == 1) {
                toolbar.getEditEntry().setEnabled(true);
                toolbar.getIncreaseSalary().setEnabled(true);
            } else {
                toolbar.getEditEntry().setEnabled(false);
                toolbar.getIncreaseSalary().setEnabled(true);
            }
        }
    }

    /**
     * Depending on event e, the method calls the corresponding method
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getEditEntry()) {
            String employeeID = salaryListView.getTable().getIdsOfCurrentSelectedRows()[0];
            Employee employee = employeeDataAccess.getEmployee(Integer.parseInt(employeeID));
            if (employee.getStatus().equals("SHK")) {
                InsertSalarySHKController insertSalarySHKController = new InsertSalarySHKController(mainFrameController);
                insertSalarySHKController.fillFields(Integer.parseInt(employeeID));
                insertSalarySHKController.showInsertSalarySHKView(mainFrameController);
            } else {
                InsertSalaryController insertSalaryController = new InsertSalaryController(mainFrameController);
                insertSalaryController.fillFields(Integer.parseInt(employeeID));
                insertSalaryController.showInsertSalaryView(mainFrameController);
            }
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
            String[] selectedEmployeeID = salaryListView.getTable().getIdsOfCurrentSelectedRows();
            ArrayList<Integer> employeeIDList = new ArrayList<>();
            for (String IDString : selectedEmployeeID) {
                employeeIDList.add(Integer.parseInt(IDString));
            }
            IncreaseSalaryDialogController salaryDialogController = new IncreaseSalaryDialogController(mainFrameController, employeeIDList);
        } else if (e.getSource() == toolbar.getExportToCSV()) {
            CSVExporter.createCSVSalaryProjection();
        } else if (e.getSource() == showSalaryStageDialogView.getCloseButton()) {
            showSalaryStageDialogView.dispose();
        } else if (e.getSource() == showSalaryStageDialogView.getOkayButton()) {
            toolbar.getShowNextPayGrade().setSelected(false);
            LocalDate localDate = showSalaryStageDialogView.getDatePicker().getDate();
            if (localDate != null) {
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                SalaryProjection salaryProjection = new SalaryProjection();
                buildPayLevelTableBasedOnChosenDate(salaryProjection.getSalaryProjectionForGivenDate(date));
                showSalaryStageDialogView.dispose();
                toolbar.getRemoveAdditionalSalaryStage().setEnabled(true);

            } else showSalaryStageDialogView.dispose();
        }
    }

    /**
     * Depending on event e, the method selects or deselects the Item
     *
     * @param e the event to be processed
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            SalaryProjection salaryProjection = new SalaryProjection();
            buildFuturePayLevelTable(salaryProjection.getNextPayLevelProjection());
            toolbar.getRemoveAdditionalSalaryStage().setEnabled(false);
        }
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            if (e.getSource() == toolbar.getShowNextPayGrade()) {
                updateData();
            }

        }
    }
}
