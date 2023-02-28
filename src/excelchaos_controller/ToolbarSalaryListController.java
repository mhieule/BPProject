package excelchaos_controller;

import excelchaos_model.calculations.SalaryProjection;
import excelchaos_model.export.CSVExporter;
import excelchaos_view.SalaryListView;
import excelchaos_view.ShowSalaryStageDialogView;
import excelchaos_view.ToolbarSalaryListView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ToolbarSalaryListController implements ActionListener, ItemListener {
    private ToolbarSalaryListView toolbar;
    private SalaryListController salaryListController;
    private SalaryListView salaryListView;
    ShowSalaryStageDialogView showSalaryStageDialogView;
    private MainFrameController frameController;


    private IncreaseSalaryDialogController increaseSalaryDialogController;


    public ToolbarSalaryListController(MainFrameController mainFrameController, SalaryListView salaryView, SalaryListController salaryController) {
        frameController = mainFrameController;
        salaryListController = salaryController;
        salaryListView = salaryView;
        toolbar = new ToolbarSalaryListView();
        toolbar.init();
        toolbar.setActionListener(this);
        toolbar.setItemListener(this);
    }

    public ToolbarSalaryListView getToolbar() {
        return toolbar;
    }


    //TODO komplett überarbeiten
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getEditEntry()) {
            InsertSalaryController insertSalaryController = new InsertSalaryController(frameController);
            String employeeID = salaryListView.getTable().getIdsOfCurrentSelectedRows()[0];
            insertSalaryController.fillFields(Integer.parseInt(employeeID));
            insertSalaryController.showInsertSalaryView(frameController);
            toolbar.getShowNextPayGrade().setSelected(false);
            toolbar.getRemoveAdditionalSalaryStage().setEnabled(false);
        } else if (e.getSource() == toolbar.getSalaryStageOn()) {
            showSalaryStageDialogView = new ShowSalaryStageDialogView();
            showSalaryStageDialogView.init();
            showSalaryStageDialogView.setActionListener(this);
        } else if (e.getSource() == toolbar.getRemoveAdditionalSalaryStage()) {
            salaryListController.updateData(salaryListController.getSalaryDataFromDataBase());
            toolbar.getRemoveAdditionalSalaryStage().setEnabled(false);
        } else if (e.getSource() == toolbar.getIncreaseSalary()) {
            toolbar.getRemoveAdditionalSalaryStage().setEnabled(false);
            //increaseSalaryDialogController = new IncreaseSalaryDialogController(frameController);
        } else if (e.getSource() == toolbar.getExportToCSV()) {
            CSVExporter.createCSVVariableName(salaryListController.getSalaryListView().getTable());
        } else if (e.getSource() == showSalaryStageDialogView.getCloseButton()) {
            showSalaryStageDialogView.dispose();
        } else if (e.getSource() == showSalaryStageDialogView.getOkayButton()) {
            LocalDate localDate = showSalaryStageDialogView.getDatePicker().getDate();
            if (localDate != null) {
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                SalaryProjection salaryProjection = new SalaryProjection();
                salaryListController.buildPayLevelTableBasedOnChosenDate(salaryProjection.getSalaryProjectionForGivenDate(date));
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
            salaryListController.buildFuturePayLevelTable(salaryProjection.getNextPayLevelProjection());
            toolbar.getRemoveAdditionalSalaryStage().setEnabled(false);
        } else {
            salaryListController.updateData(salaryListController.getSalaryDataFromDataBase());
        }
    }
}
