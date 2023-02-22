package excelchaos_controller;
import excelchaos_view.ToolbarShowPersonView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarShowPersonController implements ActionListener {
    private ToolbarShowPersonView toolbar;
    private MainFrameController frameController;

    private ShowPersonController showPersonController;

    public ToolbarShowPersonController(MainFrameController mainFrameController,ShowPersonController showPersonController){
        frameController = mainFrameController;
        this.showPersonController = showPersonController;
        toolbar = new ToolbarShowPersonView();
        toolbar.init();
        toolbar.setActionListener(this);
    }

    public ToolbarShowPersonView getToolbar() {
        return toolbar;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == toolbar.getInsertPerson()){
            InsertPersonController insertPersonController = new InsertPersonController(frameController);
            insertPersonController.showInsertPersonView(frameController);
        }
        else if(e.getSource() == toolbar.getEditPerson()){
            InsertPersonController insertPersonController = new InsertPersonController(frameController);
            String employeeID = showPersonController.getPersonView().getTable().getIdsOfCurrentSelectedRows()[0];
            insertPersonController.fillFields(employeeID);
            insertPersonController.showInsertPersonView(frameController);
        }
        else if(e.getSource() == toolbar.getDeletePerson()){
            //TODO delete muss noch implementiert werden
        }
    }
}
