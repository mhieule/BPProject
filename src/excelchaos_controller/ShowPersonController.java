package excelchaos_controller;

import excelchaos_model.SearchAndFilterModel;
import excelchaos_view.ShowPersonView;
import excelchaos_view.SideMenuPanelActionLogView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowPersonController implements ActionListener {
    private ShowPersonView showPersonView;
    private MainFrameController frameController;
    private ToolbarShowPersonController toolbarShowPerson;
    private String title = "Personalstammdaten";

    private SearchAndFilterModel searchAndFilterModel;

    public ShowPersonController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        showPersonView = new ShowPersonView();
        toolbarShowPerson = new ToolbarShowPersonController(frameController);
        showPersonView.init();
        showPersonView.add(toolbarShowPerson.getToolbar(),BorderLayout.NORTH);
        searchAndFilterModel = new SearchAndFilterModel(showPersonView.getTable(),toolbarShowPerson.getToolbar().getSearchField());

    }

    public ShowPersonView getPersonView() {
        return showPersonView;
    }

    public void showPersonView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
            mainFrameController.addTab(title,showPersonView);
            //mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
            SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
            //mainFrameController.setChangeListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /*@Override
    public void stateChanged(ChangeEvent e) {
        if (frameController.getTabs().indexOfTab(title) != -1){
            frameController.tabSwitch(title,getToolbarShowPerson().getToolbar());
        }


    }*/
    public void updateData(){
        showPersonView.addData();
    }
    public ToolbarShowPersonController getToolbarShowPerson() {
        return toolbarShowPerson;
    }
}
