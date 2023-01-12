package excelchaos_controller;

import excelchaos_view.ToolbarPayRateTablesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarPayRateTablesController implements ActionListener {
    private ToolbarPayRateTablesView toolbar;
    private InsertPayRateTableController insertPayRateTableController;

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
            insertPayRateTableController = new InsertPayRateTableController(frameController, frameController.getPayRateTablesController().getTitle());
            insertPayRateTableController.showInsertPayRateTableView(frameController);
        }
    }

    public ToolbarPayRateTablesView getToolbar() {
        return toolbar;
    }
}
