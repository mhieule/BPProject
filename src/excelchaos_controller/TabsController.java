package excelchaos_controller;

import excelchaos_view.DnDCloseButtonTabbedPane;
import excelchaos_view.MainFrame;


public class TabsController {
    private DnDCloseButtonTabbedPane tabs;
    private MainFrame frame;

    public TabsController(MainFrame window){
        frame = window;
        tabs = new DnDCloseButtonTabbedPane(frame);

    }

    public DnDCloseButtonTabbedPane getTabs() {
        return tabs;
    }


}
