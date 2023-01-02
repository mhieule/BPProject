package excelchaos_controller;

import excelchaos_view.SalaryHistoryView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SalaryHistoryController implements ChangeListener {
    private SalaryHistoryView salaryHistoryView;
    private ToolbarSalaryListController toolbarSalaryList;
    private MainFrameController frameController;

    private String title = "Gehaltshistorie";

    public SalaryHistoryController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        salaryHistoryView = new SalaryHistoryView();
        toolbarSalaryList = new ToolbarSalaryListController(frameController);
        salaryHistoryView.setHasToolbar(false);
        salaryHistoryView.init();
    }

    public void showSalaryHistoryView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1){
            //ActionLogEintrag
            //mainFrameController.getTabs().addTab("Gehaltsliste",salaryListView);
            //mainFrameController.getTabs().setActionListener(frameController.getTabsController());
            //mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab("Gehaltsliste"));
            mainFrameController.addTab(title,toolbarSalaryList.getToolbar(),salaryHistoryView);
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
            frameController.tabSwitch(title,toolbarSalaryList.getToolbar());
        }
    }

    public ToolbarSalaryListController getToolbarSalary() {
        return toolbarSalaryList;
    }
}
