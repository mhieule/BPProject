package excelchaos_controller;

import excelchaos_view.SalaryHistoryView;
import excelchaos_view.ToolbarSalaryHistoryView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SalaryHistoryController  {
    private SalaryHistoryView salaryHistoryView;
    private ToolbarSalaryHistoryController toolbarSalaryHistory;
    private MainFrameController frameController;

    private String title = "Gehaltshistorie";

    public SalaryHistoryController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        salaryHistoryView = new SalaryHistoryView();
        toolbarSalaryHistory = new ToolbarSalaryHistoryController(frameController);
        salaryHistoryView.init();
        salaryHistoryView.add(toolbarSalaryHistory.getToolbar(),BorderLayout.NORTH);
    }

    public void showSalaryHistoryView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1){
            //ActionLogEintrag
            mainFrameController.addTab(title,salaryHistoryView);
            //mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
            //ActionLogEintrag
            //mainFrameController.setChangeListener(this);
        }
    }

    /*@Override
    public void stateChanged(ChangeEvent e) {
        if (frameController.getTabs().indexOfTab(title) != -1){
            frameController.tabSwitch(title,toolbarSalaryHistory.getToolbar());
        }
    }*/

    public ToolbarSalaryHistoryController getToolbarSalaryHistory() {
        return toolbarSalaryHistory;
    }
}
