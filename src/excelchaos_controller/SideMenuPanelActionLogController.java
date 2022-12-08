package excelchaos_controller;

import excelchaos_view.SideMenuPanelActionLogView;

public class SideMenuPanelActionLogController {
    SideMenuPanelActionLogView actionLogView;

    public SideMenuPanelActionLogController(){
        actionLogView =new SideMenuPanelActionLogView();
        actionLogView.init();
    }
    public SideMenuPanelActionLogView getActionLogView(){
        return actionLogView;
    }
}
