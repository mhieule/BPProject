package excelchaos_controller;

import excelchaos_view.MainFrame;
import excelchaos_view.SmallSideBar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmallSideBarController implements ActionListener {
    private SmallSideBar smallSideBar;
    private MainFrameController frameController;
    private SideMenuPanelTablesController sideMenuTables;

    public SmallSideBarController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        smallSideBar = new SmallSideBar();
        smallSideBar.init();
        smallSideBar.setActionListener(this);

    }

    public SmallSideBar getSmallSideBar() {
        return smallSideBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == smallSideBar.getArrowButtonEast()) {
            eastArrowButtonPressed(frameController);
        }

    }
    private void eastArrowButtonPressed(MainFrameController mainFrameController){
        mainFrameController.getWindow().remove(smallSideBar);
        sideMenuTables = new SideMenuPanelTablesController(mainFrameController);
        mainFrameController.getWindow().add(sideMenuTables.getSideTable(), BorderLayout.WEST);
        mainFrameController.getWindow().revalidate();
        mainFrameController.getWindow().repaint();
    }
}
