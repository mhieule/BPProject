package excelchaos_controller;

import excelchaos_view.SideMenuPanelTables;

public class SideMenuPanelTablesController {
    private SideMenuPanelTables sideMenu;

    public SideMenuPanelTablesController() {
        sideMenu = new SideMenuPanelTables();
        sideMenu.init();


    }

    public SideMenuPanelTables getSideTable() {
        return sideMenu;
    }


}
