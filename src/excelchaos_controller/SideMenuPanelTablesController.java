package excelchaos_controller;

import excelchaos_view.MainFrame;
import excelchaos_view.SideMenuPanelTables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SideMenuPanelTablesController implements ActionListener, ItemListener {
    private SideMenuPanelTables sideMenu;
    private MainFrameController frameController;

    private SmallSideBarController smallSideBar;

    public SideMenuPanelTablesController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        sideMenu = new SideMenuPanelTables();
        sideMenu.init();
        sideMenu.setActionListener(this);
        sideMenu.setItemListener(this);
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
        } else if (e.getSource()==sideMenu.getGehaltshistorie()){
        frameController.getSalaryHistoryController().showSalaryHistoryView(frameController);
        } else if (e.getSource() == sideMenu.getShowE13Tables()){
            frameController.getPayRateTablesController().setTitle("E13 Entgelttabellen");
            frameController.getPayRateTablesController().showPayRatesView(frameController);
        } else if (e.getSource() == sideMenu.getShowE14Tables()){
            frameController.getPayRateTablesController().setTitle("E14 Entgelttabellen");
            frameController.getPayRateTablesController().showPayRatesView(frameController);
        } else if (e.getSource() == sideMenu.getShowSHKTables()){
            frameController.getPayRateTablesController().setTitle("SHK Entgelttabellen");
            frameController.getPayRateTablesController().showPayRatesView(frameController);
        }
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            sideMenu.getCenterpanel().setPreferredSize(new Dimension(130,245));
            sideMenu.getShowE13Tables().setVisible(true);
            sideMenu.getShowE14Tables().setVisible(true);
            sideMenu.getShowSHKTables().setVisible(true);
            sideMenu.openArrowLabelVisible();
        } else {
            sideMenu.getCenterpanel().setPreferredSize(new Dimension(130,155));
            sideMenu.getShowE13Tables().setVisible(false);
            sideMenu.getShowE14Tables().setVisible(false);
            sideMenu.getShowSHKTables().setVisible(false);
            sideMenu.closeArrowLabelVisible();
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
