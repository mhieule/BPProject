package excelchaos_controller;

import excelchaos_view.SideMenuPanelActionLogView;

import java.awt.*;

public class SideMenuPanelActionLogController {
    SideMenuPanelActionLogView actionLogView;
    MainFrameController frameController;

    public SideMenuPanelActionLogController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        actionLogView = new SideMenuPanelActionLogView();
        actionLogView.init();
        frameController.getWindow().add(actionLogView, BorderLayout.EAST);
    }

    public SideMenuPanelActionLogView getActionLogView() {
        return actionLogView;
    }
}
