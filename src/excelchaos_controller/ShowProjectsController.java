package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.database.*;
import excelchaos_view.ShowProjectsView;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ShowProjectsController implements ActionListener, TableModelListener {
    private ShowProjectsView showProjectsView;
    private MainFrameController frameController;
    private ToolbarShowProjectsController toolbarShowProjects;

    private ProjectManager projectManager = new ProjectManager();

    private ProjectCategoryManager projectCategoryManager = new ProjectCategoryManager();

    private ProjectFunderManager projectFunderManager = new ProjectFunderManager();

    private ProjectParticipationManager projectParticipationManager = new ProjectParticipationManager();
    private String title = "Projektdaten";

    private String columns[] = {"ID", "Name", "Bewilligungsdatum", "Anfangsdatum", "Enddatum"};


    public ShowProjectsController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        showProjectsView = new ShowProjectsView();
        toolbarShowProjects = new ToolbarShowProjectsController(frameController, this);
        showProjectsView.init();
        createTableWithData(getProjectsDataFromDataBase());
        showProjectsView.add(toolbarShowProjects.getToolbar(), BorderLayout.NORTH);
        SearchAndFilterModel.setUpSearchAndFilterModel(showProjectsView.getTable(),toolbarShowProjects.getToolbar());
    }

    public ShowProjectsView getShowProjectsView() {
        return showProjectsView;
    }

    public void showProjectsView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, showProjectsView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    public String[][] getProjectsDataFromDataBase() {
        int lines = projectManager.getRowCount();
        String resultData[][] = new String[lines][];
        int currentIndex = 0;
        List<Project> projects = projectManager.getAllProjects();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Project project : projects) {
            String Id = Integer.toString(project.getProject_id());
            String name = project.getProject_name();
            String approval = dateFormat.format(project.getApproval_date());
            String start = dateFormat.format(project.getStart_date());
            String duration = dateFormat.format(project.getDuration());

            String[] values = {Id, name, approval, start, duration};
            resultData[currentIndex] = values;
            currentIndex++;
        }
        return resultData;
    }

    private void createTableWithData(String[][] tableData) {
        showProjectsView.createProjectsTable(tableData, columns);
        showProjectsView.getTable().getModel().addTableModelListener(this);
    }
    public void updateData(String [][] tableData) {
        CustomTableModel customTableModel = new CustomTableModel(tableData,columns);
        showProjectsView.getTable().setModel(customTableModel);
        showProjectsView.getTable().getColumnModel().getColumn(1).setMinWidth(0);
        showProjectsView.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
        showProjectsView.getTable().getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(showProjectsView.getTable());
        tca.adjustColumns();
        customTableModel.addTableModelListener(this);
        SearchAndFilterModel.setUpSearchAndFilterModel(showProjectsView.getTable(),toolbarShowProjects.getToolbar());
        toolbarShowProjects.getToolbar().getEditProject().setEnabled(false);
        toolbarShowProjects.getToolbar().getDeleteProject().setEnabled(false);
        toolbarShowProjects.getToolbar().getCostOverview().setEnabled(false);
    }

    public void deleteData(int[] projectIds){
        for (int i = 0; i < projectIds.length; i++) {
            projectCategoryManager.removeProjectCategoryBasedOnProjectId(projectIds[i]);
            projectFunderManager.removeProjectFunderBasedOnProjectID(projectIds[i]);
            projectParticipationManager.removeProjectParticipationBasedOnProjectId(projectIds[i]);
            projectManager.removeProject(projectIds[i]);
        }
        updateData(getProjectsDataFromDataBase());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int numberOfSelectedRows = showProjectsView.getTable().getNumberOfSelectedRows();
        if (e.getColumn() == 0) {
            if (numberOfSelectedRows == 0) {
                toolbarShowProjects.getToolbar().getEditProject().setEnabled(false);
                toolbarShowProjects.getToolbar().getDeleteProject().setEnabled(false);
                toolbarShowProjects.getToolbar().getCostOverview().setEnabled(false);
            } else if (numberOfSelectedRows == 1) {
                toolbarShowProjects.getToolbar().getEditProject().setEnabled(true);
                toolbarShowProjects.getToolbar().getDeleteProject().setEnabled(true);
                toolbarShowProjects.getToolbar().getCostOverview().setEnabled(true);
            } else {
                toolbarShowProjects.getToolbar().getEditProject().setEnabled(false);
                toolbarShowProjects.getToolbar().getDeleteProject().setEnabled(true);
                toolbarShowProjects.getToolbar().getCostOverview().setEnabled(true);
            }
        }
    }
}
