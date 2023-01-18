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
            PayRateTablesController E13Controller = new PayRateTablesController(frameController);
            E13Controller.setTitle("E13 Entgelttabellen");
            E13Controller.showPayRatesView(frameController);
        } else if (e.getSource() == sideMenu.getShowE14Tables()){
            PayRateTablesController E14Controller = new PayRateTablesController(frameController);
            E14Controller.setTitle("E14 Entgelttabellen");
            E14Controller.showPayRatesView(frameController);
        } else if (e.getSource() == sideMenu.getShowSHKTables()){
            PayRateTablesController SHKController = new PayRateTablesController(frameController);
            SHKController.setTitle("SHK Entgelttabellen");
            SHKController.showPayRatesView(frameController);
        }
    }
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            sideMenu.getCenterpanel().setPreferredSize(new Dimension(140,255));
            sideMenu.getTableButtonPanel().setVisible(true);
            sideMenu.getToggleButtonPanel().setBorder(null);
            sideMenu.getToggleButtonPanel().setPreferredSize(new Dimension(140,35));
            sideMenu.getPayRatePanel().setBorder(sideMenu.getRaisedetchedBorder());
            sideMenu.openArrowLabelVisible();
        } else {
            sideMenu.getCenterpanel().setPreferredSize(new Dimension(140,170));
            sideMenu.getTableButtonPanel().setVisible(false);
            sideMenu.getPayRatePanel().setBorder(null);
            sideMenu.getToggleButtonPanel().setBorder(sideMenu.getRaisedetchedBorder());
            sideMenu.getToggleButtonPanel().setPreferredSize(new Dimension(140,45));
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
