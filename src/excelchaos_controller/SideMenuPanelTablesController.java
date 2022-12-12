package excelchaos_controller;

import excelchaos_view.MainFrame;
import excelchaos_view.SideMenuPanelTables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideMenuPanelTablesController implements ActionListener {
    private SideMenuPanelTables sideMenu;
    private MainFrame frame;

    private ShowPersonController personalData;
    private ToolbarShowPersonController toolbar;
    private SmallSideBarController smallSideBar;

    public SideMenuPanelTablesController(MainFrame window) {
        frame = window;
        sideMenu = new SideMenuPanelTables();
        sideMenu.init();
        sideMenu.setActionListener(this);


    }

    public SideMenuPanelTables getSideTable() {
        return sideMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sideMenu.getArrowButtonWest()) {
            westArrowButtonPressed(frame);
        } else if (e.getSource() == sideMenu.getPersonenliste()) {
            personListButtonPressed(frame);
        } else if(e.getSource() == frame.getTabs().getButton()){
            closePersonToolbar(frame);
        }
    }

    private void westArrowButtonPressed(MainFrame window) {
        smallSideBar = new SmallSideBarController(window);
        window.remove(sideMenu);
        window.add(smallSideBar.getSmallSideBar(), BorderLayout.WEST);
        window.revalidate();
        window.repaint();
    }

    private void personListButtonPressed(MainFrame window) {
        if (window.getTabs().indexOfTab("Personstammdaten") == -1) {
            personalData = new ShowPersonController();
            toolbar = new ToolbarShowPersonController(window);
            toolbar.getToolbar().setActionListener(this);
            window.add(toolbar.getToolbar(), BorderLayout.NORTH);
            window.getTabs().addTab("Personstammdaten", personalData.getPersonView());
            window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Personstammdaten"));
            window.getTabs().setActionListener(this);
        } else window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Personstammdaten"));
    }
    private void closePersonToolbar(MainFrame window){
        window.remove(toolbar.getToolbar());
        window.revalidate();
        window.repaint();
    }
}
