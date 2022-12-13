package excelchaos_controller;

import excelchaos_view.DnDCloseButtonTabbedPane;
import excelchaos_view.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameController implements ActionListener {
    private MainFrame window;
    private TabsController tabsController;
    private SideMenuPanelTablesController sideMenuTables;
    private SideMenuPanelActionLogController sideMenuActionLog;
    private ShowPersonController showPersonalData;
    private InsertPersonController insertPersonController;
    private SalaryListController salaryListController;





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
        salaryListController = new SalaryListController(this);


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

    public InsertPersonController getInsertPersonController() {
        return insertPersonController;
    }

    public SalaryListController getSalaryListController() {
        return salaryListController;
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

    public void addTab(String name,JToolBar toolBar, JPanel panel){
        getWindow().add(toolBar, BorderLayout.NORTH);
        getTabs().addTab(name,panel);
        getTabs().setActionListener(getTabsController());
        getTabs().setSelectedIndex(getTabs().indexOfTab(name));
    }
    public void tabSwitch(String name,JToolBar toolBar){
        if(getTabs().getSelectedIndex() == getTabs().indexOfTab(name)){
            getWindow().add(toolBar,BorderLayout.NORTH);
            getWindow().revalidate();
            getWindow().repaint();
        } else {
            getWindow().remove(toolBar);
        }
    }
}
