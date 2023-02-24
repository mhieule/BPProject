package excelchaos_controller;

import excelchaos_model.SalaryTableManager;
import excelchaos_view.ToolbarPayRateTablesView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarPayRateTablesController implements ActionListener {
    private ToolbarPayRateTablesView toolbar;

    private PayRateTablesController payRateController;

    private SalaryTableManager manager = new SalaryTableManager();
    private InsertSHKPayRateTableController insertSHKPayRateTableController;
    private PayRateStageTypeDialogController payRateStageTypeDialogController;

    private MainFrameController frameController;

    public ToolbarPayRateTablesController(MainFrameController mainFrameController, PayRateTablesController payRateTablesController) {
        frameController = mainFrameController;
        payRateController = payRateTablesController;
        toolbar = new ToolbarPayRateTablesView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getInsertNewPayRateTable()) {
            if (payRateController.getTitle() == "E13 Entgelttabellen") {
                payRateStageTypeDialogController = new PayRateStageTypeDialogController(frameController, payRateController);
            } else if (payRateController.getTitle() == "E14 Entgelttabellen") {
                payRateStageTypeDialogController = new PayRateStageTypeDialogController(frameController, payRateController);
            } else if (payRateController.getTitle() == "SHK Entgelttabellen") {
                insertSHKPayRateTableController = new InsertSHKPayRateTableController(frameController);
                insertSHKPayRateTableController.showInsertSHKPayRateTableView(frameController);
            }

        } else if (e.getSource() == toolbar.getEditExistingPayRateTable()) {
            ShowPayRateTableController payRateTableController = new ShowPayRateTableController(frameController, manager, payRateController.getPayRateTablesView().getPayRateTableList().getSelectedValue().toString(), payRateController.getPayGradeFromTitle(),payRateController);
            payRateTableController.insertValuesInTable();
            payRateController.getPayRateTablesView().getPayRateTableList().clearSelection();
            toolbar.getEditExistingPayRateTable().setEnabled(false);
            toolbar.getDeleteExistingPayRateTable().setEnabled(false);
        } else if (e.getSource() == toolbar.getDeleteExistingPayRateTable()) {
            Object[] options = {"Ok", "Abbrechen"};
            int joptionResult = JOptionPane.showOptionDialog(null, "Sind Sie sicher, dass die ausgewählte Entgelttabelle gelöscht werden soll?", "Warnung", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (joptionResult == 0) {
                payRateController.deletePayRateTable(payRateController.getPayRateTablesView().getPayRateTableList().getSelectedValue().toString());
            }
            toolbar.getEditExistingPayRateTable().setEnabled(false);
            toolbar.getDeleteExistingPayRateTable().setEnabled(false);
        }
    }

    public ToolbarPayRateTablesView getToolbar() {
        return toolbar;
    }
}
