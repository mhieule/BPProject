package excelchaos_controller;

import excelchaos_view.MainFrame;

import javax.swing.*;
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
    private ToolbarShowPersonController toolbarShowPerson;


    public MainFrameController() {
        window = new MainFrame();
        sideMenuTables = new SideMenuPanelTablesController(window);
        sideMenuActionLog = new SideMenuPanelActionLogController();
        smallSideBar = new SmallSideBarController(window);
        window.init();
        window.setActionListener(this);
        sideMenuTables.getSideTable().setActionListener(this);
        window.add(sideMenuActionLog.getActionLogView(), BorderLayout.EAST);
        window.add(smallSideBar.getSmallSideBar(),BorderLayout.WEST);
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

    public MainFrame getWindow() {
        return window;
    }
}
