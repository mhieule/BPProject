package excelchaos_controller;

import excelchaos_view.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameController implements ActionListener {
    private MainFrame window;
    private MainMenuPanelController mainMenu;
    private SideMenuPanelTablesController sideMenuTables;
    private SideMenuPanelActionLogController sideMenuActionLog;
    private ShowPersonController personalData;
    private InsertPersonController insertPersonController;

    public MainFrameController(){
        window = new MainFrame();
        sideMenuTables = new SideMenuPanelTablesController();
        sideMenuActionLog = new SideMenuPanelActionLogController();
        mainMenu = new MainMenuPanelController();
        window.init();
        window.setActionListener(this);
        window.add(sideMenuActionLog.getActionLogView(),BorderLayout.EAST);
        window.add(sideMenuTables.getSideTable(), BorderLayout.WEST);
        window.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == window.getInsertItem()){
            insertPersonController = new InsertPersonController();
            window.getTabs().addTab("Person hinzufügen",insertPersonController.getInsertPersonView());
            window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Person hinzufügen"));

        }
        if(e.getSource() == window.getSeeItem()){
            personalData = new ShowPersonController();
            window.getTabs().addTab("Personstammdaten",personalData.getPersonView());
            window.getTabs().setSelectedIndex(window.getTabs().indexOfTab("Personstammdaten"));
        }
    }
}
