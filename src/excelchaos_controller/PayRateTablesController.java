package excelchaos_controller;

import excelchaos_view.PayRateTablesView;
import excelchaos_view.SideMenuPanelActionLogView;

import java.awt.*;

public class PayRateTablesController {
    private PayRateTablesView payRateTablesView;
    private MainFrameController frameController;
    private ToolbarPayRateTablesController toolbarPayRateTables;
    private String title;

    public PayRateTablesController(MainFrameController mainFrameController){
        frameController = mainFrameController;

    }


    public void showPayRatesView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            //SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
            payRateTablesView = new PayRateTablesView();
            toolbarPayRateTables = new ToolbarPayRateTablesController(frameController,this);
            payRateTablesView.init();
            payRateTablesView.add(toolbarPayRateTables.getToolbar(), BorderLayout.NORTH);
            mainFrameController.addTab(title,payRateTablesView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
            //SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
        }
    }

    public void setTitle(String name){
        title = name;
    }

    public String getTitle() {
        return title;
    }
}
