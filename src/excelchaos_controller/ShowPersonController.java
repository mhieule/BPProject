package excelchaos_controller;

import excelchaos_model.SearchAndFilterModel;
import excelchaos_view.ShowPersonView;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class ShowPersonController implements TableModelListener {
    private ShowPersonView showPersonView;
    private MainFrameController frameController;
    private ToolbarShowPersonController toolbarShowPerson;
    private String title = "Personalstammdaten";

    private SearchAndFilterModel searchAndFilterModel;

    public ShowPersonController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        showPersonView = new ShowPersonView();
        toolbarShowPerson = new ToolbarShowPersonController(frameController,this);
        showPersonView.init();
        showPersonView.add(toolbarShowPerson.getToolbar(),BorderLayout.NORTH);
        searchAndFilterModel = new SearchAndFilterModel(showPersonView.getTable(),toolbarShowPerson.getToolbar().getSearchField());
        showPersonView.getTable().getModel().addTableModelListener(this);
    }

    public ShowPersonView getPersonView() {
        return showPersonView;
    }

    public void showPersonView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title,showPersonView);
            //mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
            //mainFrameController.setChangeListener(this);
        }
    }


    /*@Override
    public void stateChanged(ChangeEvent e) {
        if (frameController.getTabs().indexOfTab(title) != -1){
            frameController.tabSwitch(title,getToolbarShowPerson().getToolbar());
        }


    }*/

    public void updateData(){
        showPersonView.addData();
        showPersonView.add(toolbarShowPerson.getToolbar(),BorderLayout.NORTH);
    }

    public ToolbarShowPersonController getToolbarShowPerson() {
        return toolbarShowPerson;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int numberOfSelectedRows = showPersonView.getTable().getNumberOfSelectedRows();
        if(e.getColumn() == 0){
            if(numberOfSelectedRows == 0){
                toolbarShowPerson.getToolbar().getEditPerson().setEnabled(false);
                toolbarShowPerson.getToolbar().getDeletePerson().setEnabled(false);
            } else if (numberOfSelectedRows == 1) {
                toolbarShowPerson.getToolbar().getEditPerson().setEnabled(true);
                toolbarShowPerson.getToolbar().getDeletePerson().setEnabled(true);
            } else {
                toolbarShowPerson.getToolbar().getEditPerson().setEnabled(false);
                toolbarShowPerson.getToolbar().getDeletePerson().setEnabled(true);
            }

        }
    }
}
