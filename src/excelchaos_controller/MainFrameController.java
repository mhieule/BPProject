package excelchaos_controller;

import excelchaos_model.BackEndUpdates;
import excelchaos_model.utility.Updater;
import excelchaos_view.*;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameController {
    private MainFrame window;

    private BackEndUpdates backEndUpdates = new BackEndUpdates();
    private TabsController tabsController;
    private SideMenuPanelTablesController sideMenuTables;
    private ShowPersonController showPersonalData;
    private SalaryListController salaryListController;
   // private SalaryHistoryController salaryHistoryController;
    private ShowProjectsController showProjectsController;
    private ManualSalaryEntryController manualSalaryEntryController;
    private SalaryIncreaseController salaryIncreaseController;
    private ShowSHKTableController showSHKTableController;

    private Updater updater;


    public MainFrameController() {
        window = new MainFrame();
        window.init();
        //window.setActionListener(this);
        //backEndUpdates.calculationsOnStartUp();
        updater = new Updater(this);
        tabsController = new TabsController(this);
        window.add(tabsController.getTabs());
        sideMenuTables = new SideMenuPanelTablesController(this);
        //sideMenuActionLog = new SideMenuPanelActionLogController(this);
        showPersonalData = new ShowPersonController(this);
        salaryListController = new SalaryListController(this);
        //salaryHistoryController = new SalaryHistoryController(this);
        manualSalaryEntryController = new ManualSalaryEntryController(this);
        salaryIncreaseController = new SalaryIncreaseController(this);
        showProjectsController = new ShowProjectsController(this);
        showSHKTableController = new ShowSHKTableController(this);

        window.setVisible(true);
    }


    public MainFrame getWindow() {
        return window;
    }

    public ShowPersonController getShowPersonalData() {
        return showPersonalData;
    }



    public SalaryListController getSalaryListController() {
        return salaryListController;
    }

   /* public SalaryHistoryController getSalaryHistoryController(){return salaryHistoryController;}*/

    public ManualSalaryEntryController getManualSalaryEntryController() {
        return manualSalaryEntryController;
    }

    public Updater getUpdater() {
        return updater;
    }

    public SalaryIncreaseController getSalaryIncreaseController() {
        return salaryIncreaseController;
    }

    public ShowProjectsController getShowProjectsController() {
        return showProjectsController;
    }

    public ShowSHKTableController getShowSHKTableController() {
        return showSHKTableController;
    }

    public TabsController getTabsController() {
        return tabsController;
    }

    public DnDCloseButtonTabbedPane getTabs() {
        return tabsController.getTabs();
    }

    public void addTab(String name, JPanel panel){
        getTabs().addTab(name,panel);
        getTabs().setSelectedIndex(getTabs().indexOfTab(name));
    }

    /*public void tabSwitch(String name,JToolBar toolBar){
        if(getTabs().getSelectedIndex() == getTabs().indexOfTab(name)){
            getWindow().add(toolBar,BorderLayout.NORTH);
            getWindow().revalidate();
            getWindow().repaint();
        } else {
            getWindow().remove(toolBar);
        }
    }*/
    /*public void removeAllToolbars(){
        getWindow().remove(getShowPersonalData().getToolbarShowPerson().getToolbar());
        getWindow().remove(getSalaryListController().getToolbarSalary().getToolbar());
        getWindow().remove(getSalaryHistoryController().getToolbarSalaryHistory().getToolbar());
        getWindow().revalidate();
        getWindow().repaint();
    }*/
    /*public void addToolbarsNewWindow(Component component){
        if (component.getClass() == ShowPersonView.class){
            if (((ShowPersonView) component).hasToolbar()){
                getWindow().add(showPersonalData.getToolbarShowPerson().getToolbar(),BorderLayout.NORTH);
            }
        } else if (component.getClass() == SalaryListView.class){
            if (((SalaryListView) component).hasToolbar()){
                getWindow().add(salaryListController.getToolbarSalary().getToolbar(),BorderLayout.NORTH);
            }
        }
        getWindow().revalidate();
        getWindow().repaint();
    }*/
   /* public void removeToolbarsNewWindow(Component component){
        if (component.getClass() == ShowPersonView.class){
            if (((ShowPersonView) component).hasToolbar()){
                getWindow().remove(showPersonalData.getToolbarShowPerson().getToolbar());

            }
        } else if (component.getClass() == SalaryListView.class){
            if (((SalaryListView) component).hasToolbar()){
                getWindow().remove(salaryListController.getToolbarSalary().getToolbar());
            }
        } else if (component.getClass() == SalaryHistoryView.class){
            if (((SalaryHistoryView) component).hasToolbar()){
                getWindow().remove(salaryHistoryController.getToolbarSalaryHistory().getToolbar());
            }
        }

        getWindow().revalidate();
        getWindow().repaint();
    }*/

}
