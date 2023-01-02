package excelchaos_controller;

import excelchaos_view.ToolbarSalaryHistoryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarSalaryHistoryController implements ActionListener {
    private ToolbarSalaryHistoryView toolbar;
    private MainFrameController frameController;

    public ToolbarSalaryHistoryController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        toolbar = new ToolbarSalaryHistoryView();
        toolbar.init();
        toolbar.setActionListener(this);
    }
    public ToolbarSalaryHistoryView getToolbar() {
        return toolbar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
