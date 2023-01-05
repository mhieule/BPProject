package excelchaos_controller;

import excelchaos_view.SalaryListView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SalaryListController {
    private SalaryListView salaryListView;
    private ToolbarSalaryListController toolbarSalaryList;
    private MainFrameController frameController;

    private String title = "Gehaltsliste";

    public SalaryListController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        salaryListView = new SalaryListView();
        salaryListView.init();
        toolbarSalaryList = new ToolbarSalaryListController(salaryListView,this);
        salaryListView.add(toolbarSalaryList.getToolbar(),BorderLayout.NORTH);
    }

    public void showSalaryView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1){
            //ActionLogEintrag
            mainFrameController.addTab(title,salaryListView);
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
            frameController.tabSwitch(title,toolbarSalaryList.getToolbar());
        }
    }*/

    public ToolbarSalaryListController getToolbarSalary() {
        return toolbarSalaryList;
    }
}
