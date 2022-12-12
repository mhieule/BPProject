package excelchaos_controller;

import excelchaos_view.DnDCloseButtonTabbedPane;
import excelchaos_view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TabsController implements ActionListener {
    private DnDCloseButtonTabbedPane tabs;
    private MainFrameController frameController;

    public TabsController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        tabs = new DnDCloseButtonTabbedPane(frameController.getWindow(),frameController);

    }

    public DnDCloseButtonTabbedPane getTabs() {
        return tabs;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int index = tabs.indexOfTab("Personstammdaten");
        if (index >= 0) {
            frameController.getWindow().remove(frameController.getShowPersonalData().getToolbarShowPerson().getToolbar());
            frameController.getWindow().revalidate();
            frameController.getWindow().repaint();
        }


    }
}
