package excelchaos_controller;

import excelchaos_view.ToolbarManualSalaryEntryView;
import excelchaos_view.ToolbarSalaryIncreaseView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarSalaryIncreaseController implements ActionListener {

    private ToolbarSalaryIncreaseView toolbar;

    private MainFrameController frameController;

    public ToolbarSalaryIncreaseController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        toolbar = new ToolbarSalaryIncreaseView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public ToolbarSalaryIncreaseView getToolbar() {
        return toolbar;
    }
}
