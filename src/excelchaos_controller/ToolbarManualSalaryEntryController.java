package excelchaos_controller;

import excelchaos_view.ManualSalaryEntryView;
import excelchaos_view.ToolbarManualSalaryEntryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarManualSalaryEntryController implements ActionListener {
    private ToolbarManualSalaryEntryView toolbar;

    private MainFrameController frameController;

    public ToolbarManualSalaryEntryController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        toolbar = new ToolbarManualSalaryEntryView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public ToolbarManualSalaryEntryView getToolbar() {
        return toolbar;
    }
}
