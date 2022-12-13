package excelchaos_controller;

import excelchaos_view.ToolbarSalaryListView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarSalaryListController implements ActionListener {
    private ToolbarSalaryListView toolbar;
    private MainFrameController frameController;

    public ToolbarSalaryListController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        toolbar = new ToolbarSalaryListView();
        toolbar.init();
        toolbar.setActionListener(this);
    }
    public ToolbarSalaryListView getToolbar() {
        return toolbar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
