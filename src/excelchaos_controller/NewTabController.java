package excelchaos_controller;

import excelchaos_view.MainFrame;
import excelchaos_view.NewTabView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewTabController implements ActionListener {
    private NewTabView window;
    private MainMenuPanelController mainMenu;
    private SideMenuPanelTablesController sideMenuTables;
    private SmallSideBarController smallSideBar;
    //private SideMenuPanelActionLogController sideMenuActionLog;
    private ShowPersonController personalData;
    private InsertPersonController insertPersonController;

    public NewTabController() {
        window = new NewTabView();
        sideMenuTables = new SideMenuPanelTablesController();
        //sideMenuActionLog = new SideMenuPanelActionLogController();
        window.init();
        window.setActionListener(this);
        sideMenuTables.getSideTable().setActionListener(this);
        //window.add(sideMenuActionLog.getActionLogView(), BorderLayout.EAST);
        window.add(sideMenuTables.getSideTable(), BorderLayout.WEST);

        window.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == window.getInsertItem()) {
            if(window.getTabs().indexOfTab("Person hinzufügen")==-1) {
                insertPersonController = new InsertPersonController();
                window.getTabs().addTab("Person hinzufügen", insertPersonController.getInsertPersonView());
                //window.getTabs().setTabComponentAt(window.getTabs().indexOfTab("Person hinzufügen"), new ButtonTabComponent(window.getTabs()));
                //window.getTabs().addCloseButton(window.getTabs().indexOfTab("Person hinzufügen"));
                window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Person hinzufügen"));
            } else window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Person hinzufügen"));

        } else if (e.getSource() == window.getSeeItem() || e.getSource() == sideMenuTables.getSideTable().getPersonenliste()) {
            if(window.getTabs().indexOfTab("Personstammdaten")==-1) {
                personalData = new ShowPersonController();
                window.getTabs().addTab("Personstammdaten", personalData.getPersonView());
                //window.getTabs().setTabComponentAt(window.getTabs().indexOfTab("Personstammdaten"), new ButtonTabComponent(window.getTabs()));
                //window.getTabs().addCloseButton(window.getTabs().indexOfTab("Personstammdaten"));
                window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Personstammdaten"));
            } else window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Personstammdaten"));
        } else if (e.getSource() == sideMenuTables.getSideTable().getArrowButtonWest()) {
            smallSideBar = new SmallSideBarController();
            window.remove(sideMenuTables.getSideTable());
            window.add(smallSideBar.getSmallSideBar(), BorderLayout.WEST);
            smallSideBar.getSmallSideBar().setActionListener(this);
            window.revalidate();
            window.repaint();
        } else if (e.getSource() == smallSideBar.getSmallSideBar().getArrowButtonEast()) {
            window.remove(smallSideBar.getSmallSideBar());
            window.add(sideMenuTables.getSideTable(), BorderLayout.WEST);
            window.revalidate();
            window.repaint();
        }
    }

    public NewTabView getWindow() {
        return window;
    }
}

