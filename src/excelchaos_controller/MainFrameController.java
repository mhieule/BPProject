package excelchaos_controller;

import excelchaos_view.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameController implements ActionListener {
    private MainFrame window;
    private MainMenuPanelController mainMenu;
    private SideMenuPanelTablesController sideMenuTables;
    private SmallSideBarController smallSideBar;
    private SideMenuPanelActionLogController sideMenuActionLog;
    private ShowPersonController personalData;
    private InsertPersonController insertPersonController;

    public MainFrameController() {
        window = new MainFrame();
        sideMenuTables = new SideMenuPanelTablesController();
        sideMenuActionLog = new SideMenuPanelActionLogController();
        window.init();
        window.setActionListener(this);
        sideMenuTables.getSideTable().setActionListener(this);
        window.add(sideMenuActionLog.getActionLogView(), BorderLayout.EAST);
        window.add(sideMenuTables.getSideTable(), BorderLayout.WEST);

        window.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == window.getInsertItem()) {
            insertPersonController = new InsertPersonController();
            window.getTabs().addTab("Person hinzufügen", insertPersonController.getInsertPersonView());
            window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Person hinzufügen"));

        }
        if (e.getSource() == window.getSeeItem()) {
            personalData = new ShowPersonController();
            window.getTabs().addTab("Personstammdaten", personalData.getPersonView());
            window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Personstammdaten"));
        }
        if (e.getSource() == sideMenuTables.getSideTable().getArrowButtonWest()) {
            smallSideBar = new SmallSideBarController();
            window.remove(sideMenuTables.getSideTable());
            window.add(smallSideBar.getSmallSideBar(), BorderLayout.WEST);
            smallSideBar.getSmallSideBar().setActionListener(this);
            window.revalidate();
            window.repaint();
        }
        if (e.getSource() == smallSideBar.getSmallSideBar().getArrowButtonEast()) {
            window.remove(smallSideBar.getSmallSideBar());
            window.add(sideMenuTables.getSideTable(),BorderLayout.WEST);
            window.revalidate();
            window.repaint();
        }
    }
}
