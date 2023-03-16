package excelchaos_controller;

import excelchaos_view.components.DnDCloseButtonTabbedPane;


public class TabsController {
    private DnDCloseButtonTabbedPane tabs;
    private MainFrameController frameController;

    /**
     * Constructor for TabsController
     *
     * @param mainFrameController mainFrameController
     */
    public TabsController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        tabs = new DnDCloseButtonTabbedPane(frameController.getWindow());

    }

    public DnDCloseButtonTabbedPane getTabs() {
        return tabs;
    }
}
