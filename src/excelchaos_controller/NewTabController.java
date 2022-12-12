package excelchaos_controller;


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
    private ToolbarShowPersonController toolbarShowPerson;
    private InsertPersonController insertPersonController;

    public NewTabController() {
        window = new NewTabView();
        sideMenuTables = new SideMenuPanelTablesController(window);
        //sideMenuActionLog = new SideMenuPanelActionLogController();
        window.init();
        window.setActionListener(this);
        //window.add(sideMenuActionLog.getActionLogView(), BorderLayout.EAST);
        window.add(sideMenuTables.getSideTable(), BorderLayout.WEST);

        window.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == window.getInsertItem()) {
            if (window.getTabs().indexOfTab("Person hinzufügen") == -1) {
                insertPersonController = new InsertPersonController();
                window.getTabs().addTab("Person hinzufügen", insertPersonController.getInsertPersonView());
                window.getTabs().setActionListener(this);
                //window.getTabs().setTabComponentAt(window.getTabs().indexOfTab("Person hinzufügen"), new ButtonTabComponent(window.getTabs()));
                //window.getTabs().addCloseButton(window.getTabs().indexOfTab("Person hinzufügen"));
                window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Person hinzufügen"));
            } else window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Person hinzufügen"));

        } else if (e.getSource() == window.getSeeItem()) {
            if (window.getTabs().indexOfTab("Personstammdaten") == -1) {
                personalData = new ShowPersonController();
                toolbarShowPerson = new ToolbarShowPersonController(window);
                toolbarShowPerson.getToolbar().setActionListener(this);
                window.add(toolbarShowPerson.getToolbar(), BorderLayout.NORTH);
                window.getTabs().addTab("Personstammdaten", personalData.getPersonView());
                window.getTabs().setActionListener(this);
                //window.getTabs().setTabComponentAt(window.getTabs().indexOfTab("Personstammdaten"), new ButtonTabComponent(window.getTabs()));
                //window.getTabs().addCloseButton(window.getTabs().indexOfTab("Personstammdaten"));
                window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Personstammdaten"));
            } else window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Personstammdaten"));

        } else if (e.getSource() == window.getTabs().getButton()) {
            window.remove(toolbarShowPerson.getToolbar());
            window.revalidate();
            window.repaint();

        }

    }

    public NewTabView getWindow() {
        return window;
    }
}

