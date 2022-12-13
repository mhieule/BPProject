package excelchaos_controller;

import excelchaos_view.MainFrame;
import excelchaos_view.SideMenuPanelTables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideMenuPanelTablesController implements ActionListener {
    private SideMenuPanelTables sideMenu;
    private MainFrameController frameController;

    private ShowPersonController personalData;
    private ToolbarShowPersonController toolbar;
    private SmallSideBarController smallSideBar;

    public SideMenuPanelTablesController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        sideMenu = new SideMenuPanelTables();
        sideMenu.init();
        sideMenu.setActionListener(this);
        mainFrameController.getWindow().add(sideMenu, BorderLayout.WEST);


    }

    public SideMenuPanelTables getSideTable() {
        return sideMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sideMenu.getArrowButtonWest()) {
            westArrowButtonPressed(frameController);
        } else if (e.getSource() == sideMenu.getPersonenliste()) {
            frameController.getShowPersonalData().showPersonView(frameController);
        } else if (e.getSource()==sideMenu.getGehaltsliste()){
            frameController.getSalaryListController().showSalaryView(frameController);
        }
    }

    private void westArrowButtonPressed(MainFrameController frameController) {
        smallSideBar = new SmallSideBarController(frameController);
        frameController.getWindow().remove(sideMenu);
        frameController.getWindow().add(smallSideBar.getSmallSideBar(), BorderLayout.WEST);
        frameController.getWindow().revalidate();
        frameController.getWindow().repaint();
    }


}
