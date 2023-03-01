package excelchaos_controller;

import excelchaos_view.DnDCloseButtonTabbedPane;
import excelchaos_view.MainFrame;
import excelchaos_view.TabCloseButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TabsController {
    private DnDCloseButtonTabbedPane tabs;
    private MainFrameController frameController;

    public TabsController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        tabs = new DnDCloseButtonTabbedPane(frameController.getWindow());

    }

    public DnDCloseButtonTabbedPane getTabs() {
        return tabs;
    }
}
