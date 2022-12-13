package excelchaos_controller;

import excelchaos_view.SalaryListView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SalaryListController implements ChangeListener {
    private SalaryListView salaryListView;
    private ToolbarSalaryListController toolbarSalaryList;
    private MainFrameController frameController;

    private String title = "Gehaltsliste";

    public SalaryListController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        salaryListView = new SalaryListView();
        toolbarSalaryList = new ToolbarSalaryListController(frameController);
        salaryListView.init();
    }

    public void showSalaryView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1){
            //ActionLogEintrag
            //mainFrameController.getTabs().addTab("Gehaltsliste",salaryListView);
            //mainFrameController.getTabs().setActionListener(frameController.getTabsController());
            //mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab("Gehaltsliste"));
            mainFrameController.addTab(title,toolbarSalaryList.getToolbar(),salaryListView);
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
