package excelchaos_controller;

import excelchaos_view.toolbarviews.ToolbarShowProjectsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarShowProjectsController implements ActionListener {
    private ToolbarShowProjectsView toolbar;
    private MainFrameController frameController;
    private ShowProjectsController showProjectsController;

    /**
     * Constructor for ToolbarShowProjectsController
     *
     * @param mainFrameController    mainFrameController
     * @param showProjectsController showProjectsController
     */
    public ToolbarShowProjectsController(MainFrameController mainFrameController, ShowProjectsController showProjectsController) {
        this.showProjectsController = showProjectsController;
        frameController = mainFrameController;
        toolbar = new ToolbarShowProjectsView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    public ToolbarShowProjectsView getToolbar() {
        return toolbar;
    }


    /**
     * Depending on the source of the event, the corresponding action is performed
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getInsertProject()) {
            InsertProjectsController insertProjectsController = new InsertProjectsController(frameController);
            insertProjectsController.showInsertProjectsView(frameController);
        }
        if (e.getSource() == toolbar.getEditProject()) {
            InsertProjectsController insertProjectsController = new InsertProjectsController(frameController);
            String projectID = showProjectsController.getShowProjectsView().getTable().getIdsOfCurrentSelectedRows()[0];
            insertProjectsController.fillFields(projectID);
            insertProjectsController.showInsertProjectsView(frameController);
        }
        if (e.getSource() == toolbar.getDeleteProject()) {
            Object[] options = {"Ok", "Abbrechen"};
            int joptionResult = JOptionPane.showOptionDialog(null, "Sind Sie sicher, dass die ausgewählten Projekte gelöscht werden sollen?", "Warnung", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (joptionResult == 0) {
                String[] projectIds = showProjectsController.getShowProjectsView().getTable().getIdsOfCurrentSelectedRows();
                int[] Ids = new int[projectIds.length];
                for (int i = 0; i < projectIds.length; i++) {
                    Ids[i] = Integer.parseInt(projectIds[i]);
                }
                showProjectsController.deleteData(Ids);
            }
        }
        if (e.getSource() == toolbar.getCostOverview()) {
            if (showProjectsController.getShowProjectsView().getTable().isRowCurrentlySelected()) {
                new ProjectParticipationController(frameController, showProjectsController.getShowProjectsView().getTable().getIdsOfCurrentSelectedRows()).showInsertProjectsView(frameController);
            }
        }
    }
}
