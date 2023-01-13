package excelchaos_controller;

import excelchaos_view.ToolbarPayRateTablesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarPayRateTablesController implements ActionListener {
    private ToolbarPayRateTablesView toolbar;
    private InsertPayRateTableController insertPayRateTableController;
    private PayRateStageTypeDialogController payRateStageTypeDialogController;

    private MainFrameController frameController;

    public ToolbarPayRateTablesController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        toolbar = new ToolbarPayRateTablesView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getInsertNewPayRateTable()){
            if(frameController.getPayRateTablesController().getTitle() =="E13 Entgelttabellen"){
                //insertPayRateTableController = new InsertPayRateTableController(frameController, frameController.getPayRateTablesController().getTitle());
                //insertPayRateTableController.showInsertPayRateTableView(frameController);
                payRateStageTypeDialogController = new PayRateStageTypeDialogController(frameController);
            } else if (frameController.getPayRateTablesController().getTitle() =="E14 Entgelttabellen") {
                //insertPayRateTableController = new InsertPayRateTableController(frameController, frameController.getPayRateTablesController().getTitle());
                //insertPayRateTableController.showInsertPayRateTableView(frameController);
                payRateStageTypeDialogController = new PayRateStageTypeDialogController(frameController);
            } else if (frameController.getPayRateTablesController().getTitle() == "SHK Entgelttabellen") {

            }

        }
    }

    public ToolbarPayRateTablesView getToolbar() {
        return toolbar;
    }
}
