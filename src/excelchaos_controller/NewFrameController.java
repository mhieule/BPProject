package excelchaos_controller;

import excelchaos_view.DnDCloseButtonTabbedPane;
import excelchaos_view.NewFrame;

public class NewFrameController {

    private NewFrame newFrame;
    private DnDCloseButtonTabbedPane tabs;

    public NewFrameController(){
        newFrame = new NewFrame();
        newFrame.init();
        tabs = new DnDCloseButtonTabbedPane(newFrame);
        newFrame.add(tabs);
    }

    public NewFrame getWindow() {
        return newFrame;
    }
    public DnDCloseButtonTabbedPane getTabs() {
        return tabs;
    }
}
