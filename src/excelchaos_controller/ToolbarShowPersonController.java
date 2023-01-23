package excelchaos_controller;
import excelchaos_view.ToolbarShowPersonView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarShowPersonController implements ActionListener {
    private ToolbarShowPersonView toolbar;
    private MainFrameController frameController;

    public ToolbarShowPersonController(MainFrameController mainFrameController){
        frameController = mainFrameController;
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
            frameController.getInsertPersonController().showInsertPersonView(frameController);
        }
    }
}
