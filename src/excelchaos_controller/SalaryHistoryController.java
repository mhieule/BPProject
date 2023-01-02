package excelchaos_controller;

import excelchaos_view.SalaryHistoryView;
import excelchaos_view.ToolbarSalaryHistoryView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SalaryHistoryController implements ChangeListener {
    private SalaryHistoryView salaryHistoryView;
    private ToolbarSalaryHistoryController toolbarSalaryHistory;
    private MainFrameController frameController;

    private String title = "Gehaltshistorie";

    public SalaryHistoryController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        salaryHistoryView = new SalaryHistoryView();
        toolbarSalaryHistory = new ToolbarSalaryHistoryController(frameController);
        salaryHistoryView.setHasToolbar(true);
        salaryHistoryView.init();
    }

    public void showSalaryHistoryView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1){
            //ActionLogEintrag
            //mainFrameController.getTabs().addTab("Gehaltsliste",salaryListView);
            //mainFrameController.getTabs().setActionListener(frameController.getTabsController());
            //mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab("Gehaltsliste"));
            mainFrameController.addTab(title,toolbarSalaryHistory.getToolbar(),salaryHistoryView);
            mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
            //ActionLogEintrag
            mainFrameController.setChangeListener(this);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (frameController.getTabs().indexOfTab(title) != -1){
            frameController.tabSwitch(title,toolbarSalaryHistory.getToolbar());
        }
    }

    public ToolbarSalaryHistoryController getToolbarSalaryHistory() {
        return toolbarSalaryHistory;
    }
}
