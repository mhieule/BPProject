package excelchaos_controller;
import excelchaos_view.ToolbarShowProjectsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarShowProjectsController implements ActionListener {
    private ToolbarShowProjectsView toolbar;
    private MainFrameController frameController;

    public ToolbarShowProjectsController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        toolbar = new ToolbarShowProjectsView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    public ToolbarShowProjectsView getToolbar() {
        return toolbar;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == toolbar.getInsertProject()){
            frameController.getInsertProjectsController().showInsertProjectsView(frameController);
        }
        if(e.getSource() == toolbar.getEditProject()){

        }
        if(e.getSource() == toolbar.getDeleteProject()){

        }
        if (e.getSource() == toolbar.getCostOverview()){

        }
    }
}
