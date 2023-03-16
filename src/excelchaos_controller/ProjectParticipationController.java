package excelchaos_controller;

import excelchaos_model.datamodel.projectparticipation.ProjectParticipationDataModel;
import excelchaos_view.ProjectParticipationView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class ProjectParticipationController implements ActionListener {
    private ProjectParticipationView participationView;
    private MainFrameController frameController;
    private String addParticipationTab = "Projektplanung";

    private int[] selectedProjectIds;



    private ProjectParticipationDataModel model;

    /**
     * Constructor for ProjectParticipationController
     * @param mainFrameController main frame controller
     * @param selectedProjectIds selected project ids
     */

    public ProjectParticipationController(MainFrameController mainFrameController,String[] selectedProjectIds){
        frameController = mainFrameController;
        model = new ProjectParticipationDataModel(selectedProjectIds);
        this.selectedProjectIds = model.getProjectIds();
        participationView = new ProjectParticipationView();
        participationView.init();
        fillTables();
        if(selectedProjectIds.length < 2){
            addParticipationTab = "Projektplanung " + model.getProjectName(Integer.parseInt(selectedProjectIds[0]));
        }
    }

    /**
     * Displays insert projects view in main frame
     * @param mainFrameController main frame controller
     */

    public void showInsertProjectsView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(addParticipationTab) == -1) {
            mainFrameController.getTabs().addTab(addParticipationTab, participationView);
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addParticipationTab));
        } else
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addParticipationTab));
    }

    /**
     * Fills table with project data
     */

    public void fillTables(){
        for (int projectId = 0; projectId < selectedProjectIds.length; projectId++) {
            model.getProjectRunTimeInMonths(selectedProjectIds[projectId]);
            model.getPersonNamesForProject(selectedProjectIds[projectId]);
        }
        for (int projectId = 0; projectId < selectedProjectIds.length; projectId++) {
            String projectName = model.getProjectName(selectedProjectIds[projectId]);
            String[] monthColumns = model.getProjectRunTimeInMonths(selectedProjectIds[projectId]);
            String[] involvedEmployees = model.getPersonNamesForProject(selectedProjectIds[projectId]);
            try {
                if(projectId == 0){

                }
                String[][] tableData = model.getTableData(selectedProjectIds[projectId],involvedEmployees.length,monthColumns,involvedEmployees);
                String[][] summedTableData = model.getSummedTableData(selectedProjectIds[projectId],involvedEmployees.length,monthColumns);
                String totalCost = model.getTotalProjectPersonalCost();
                participationView.setUpProjectPanel(projectName,monthColumns,involvedEmployees,tableData,summedTableData,totalCost,selectedProjectIds[projectId]);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }

        String[] allEmployeeNames = model.getAllEmployeesNamesForSelectedProjects();
        String[] allMonths = model.getRuntimeInMonthsForAllProjects();
        String[][] totalParticipations = model.getTotalParticipationsOfShownEmployees();
        participationView.setUpParticipationSumPanel(allEmployeeNames,allMonths,totalParticipations);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
