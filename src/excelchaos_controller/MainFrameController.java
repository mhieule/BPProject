package excelchaos_controller;

import excelchaos_view.DnDCloseButtonTabbedPane;
import excelchaos_view.MainFrame;

import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameController implements ActionListener {
    private MainFrame window;
    private TabsController tabsController;
    private SideMenuPanelTablesController sideMenuTables;
    private SideMenuPanelActionLogController sideMenuActionLog;
    private ShowPersonController showPersonalData;
    private InsertPersonController insertPersonController;





    public MainFrameController() {
        window = new MainFrame();
        window.init();
        window.setActionListener(this);
        tabsController = new TabsController(this);
        window.add(tabsController.getTabs());
        sideMenuTables = new SideMenuPanelTablesController(this);
        sideMenuActionLog = new SideMenuPanelActionLogController(this);
        insertPersonController = new InsertPersonController(this);
        showPersonalData = new ShowPersonController(this);


        window.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == window.getInsertItem()) {
            insertPersonController.showInsertPersonView(this);

        } else if (e.getSource() == window.getSeeItem()) {
            showPersonalData.showPersonView(this);

        }

    }

    public MainFrame getWindow() {
        return window;
    }

    public ShowPersonController getShowPersonalData() {
        return showPersonalData;
    }
    public void setChangeListener (ChangeListener l){
        tabsController.getTabs().addChangeListener(l);
    }

    public TabsController getTabsController() {
        return tabsController;
    }

    public DnDCloseButtonTabbedPane getTabs() {
        return tabsController.getTabs();
    }
}
