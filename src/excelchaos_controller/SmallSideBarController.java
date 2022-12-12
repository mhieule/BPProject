package excelchaos_controller;

import excelchaos_view.MainFrame;
import excelchaos_view.SmallSideBar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmallSideBarController implements ActionListener {
    private SmallSideBar smallSideBar;
    private MainFrame frame;
    private SideMenuPanelTablesController sideMenuTables;

    public SmallSideBarController(MainFrame window) {
        frame = window;
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
            eastArrowButtonPressed(frame);
        }

    }
    private void eastArrowButtonPressed(MainFrame window){
        window.remove(smallSideBar);
        sideMenuTables = new SideMenuPanelTablesController(window);
        window.add(sideMenuTables.getSideTable(), BorderLayout.WEST);
        window.revalidate();
        window.repaint();
    }
}
