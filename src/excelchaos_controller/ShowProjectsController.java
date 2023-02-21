package excelchaos_controller;

import excelchaos_model.SearchAndFilterModel;
import excelchaos_view.ShowProjectsView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowProjectsController implements ActionListener {
    private ShowProjectsView showProjectsView;
    private MainFrameController frameController;
    private ToolbarShowProjectsController toolbarShowProjects;
    private String title = "Projekt Daten";

    private SearchAndFilterModel searchAndFilterModel;

    public ShowProjectsController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        showProjectsView = new ShowProjectsView();
        toolbarShowProjects = new ToolbarShowProjectsController(frameController,this);
        showProjectsView.init();
        showProjectsView.add(toolbarShowProjects.getToolbar(),BorderLayout.NORTH);
        searchAndFilterModel = new SearchAndFilterModel(showProjectsView.getTable(),toolbarShowProjects.getToolbar().getSearchField());
    }

    public ShowProjectsView getShowProjectsView() {
        return showProjectsView;
    }

    public void showProjectsView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title,showProjectsView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void updateData(){
        showProjectsView.addData();
        showProjectsView.add(toolbarShowProjects.getToolbar(),BorderLayout.NORTH);
    }

    public ToolbarShowProjectsController getToolbarShowProjects() {
        return toolbarShowProjects;
    }
}
