package excelchaos_controller;

import excelchaos_view.ToolbarPayRateTablesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarPayRateTablesController implements ActionListener {
    private ToolbarPayRateTablesView toolbar;

    private PayRateTablesController payRateController;
    private InsertSHKPayRateTableController insertSHKPayRateTableController;
    private PayRateStageTypeDialogController payRateStageTypeDialogController;

    private MainFrameController frameController;

    public ToolbarPayRateTablesController(MainFrameController mainFrameController, PayRateTablesController payRateTablesController ){
        frameController = mainFrameController;
        payRateController = payRateTablesController;
        toolbar = new ToolbarPayRateTablesView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getInsertNewPayRateTable()){
            if(payRateController.getTitle() =="E13 Entgelttabellen"){
                //insertPayRateTableController = new InsertPayRateTableController(frameController, frameController.getPayRateTablesController().getTitle());
                //insertPayRateTableController.showInsertPayRateTableView(frameController);
                payRateStageTypeDialogController = new PayRateStageTypeDialogController(frameController,payRateController);
            } else if (payRateController.getTitle() =="E14 Entgelttabellen") {
                //insertPayRateTableController = new InsertPayRateTableController(frameController, frameController.getPayRateTablesController().getTitle());
                //insertPayRateTableController.showInsertPayRateTableView(frameController);
                payRateStageTypeDialogController = new PayRateStageTypeDialogController(frameController,payRateController);
            } else if (payRateController.getTitle() == "SHK Entgelttabellen") {
                insertSHKPayRateTableController = new InsertSHKPayRateTableController(frameController);
                insertSHKPayRateTableController.showInsertSHKPayRateTableView(frameController);
            }

        }
    }

    public ToolbarPayRateTablesView getToolbar() {
        return toolbar;
    }
}
