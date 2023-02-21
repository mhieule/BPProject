package excelchaos_controller;

import excelchaos_model.ProjectParticipationDataModel;
import excelchaos_view.ProjectParticipationView;

import java.text.ParseException;

public class ProjectParticipationController {
    private ProjectParticipationView participationView;
    private MainFrameController frameController;
    private String addParticipationTab = "Projektmitarbeit";

    private int[] selectedProjectIds;

    private ProjectParticipationDataModel model;

    public ProjectParticipationController(MainFrameController mainFrameController,String[] selectedProjectIds){
        frameController = mainFrameController;
        model = new ProjectParticipationDataModel(selectedProjectIds);
        this.selectedProjectIds = model.getProjectIds();
        participationView = new ProjectParticipationView();
        participationView.init();
        fillTables();
    }

    public void showInsertProjectsView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(addParticipationTab) == -1) {
            mainFrameController.getTabs().addTab(addParticipationTab, participationView);
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addParticipationTab));
        } else
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addParticipationTab));
    }

    public void fillTables(){
        for (int projectId = 0; projectId < selectedProjectIds.length; projectId++) {
            String projectName = model.getProjectName(selectedProjectIds[projectId]);
            String[] monthColumns = model.getProjectRunTimeInMonths(selectedProjectIds[projectId]);
            String[] involvedEmployees = model.getPersonNamesForProject(selectedProjectIds[projectId]);
            try {
                String[][] tableData = model.getTableData(selectedProjectIds[projectId],involvedEmployees.length,monthColumns);
                String[][] summedTableData = model.getSummedTableData(selectedProjectIds[projectId],involvedEmployees.length,monthColumns);
                String totalCost = model.getTotalProjectPersonalCost();
                participationView.setUpProjectPanel(projectName,monthColumns,involvedEmployees,tableData,summedTableData,totalCost);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
