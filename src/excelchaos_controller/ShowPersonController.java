package excelchaos_controller;

import excelchaos_view.ShowPersonView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowPersonController implements ActionListener, ChangeListener {
    private ShowPersonView personView;
    private MainFrameController frameController;
    private ToolbarShowPersonController toolbarShowPerson;

    public ShowPersonController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        personView = new ShowPersonView();
        toolbarShowPerson = new ToolbarShowPersonController();
        personView.init();
        toolbarShowPerson.getToolbar().setActionListener(this);
    }

    public ShowPersonView getPersonView() {
        return personView;
    }

    public void showPersonView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab("Personstammdaten") == -1) {
            mainFrameController.getWindow().add(toolbarShowPerson.getToolbar(), BorderLayout.NORTH);
            mainFrameController.getTabs().addTab("Personstammdaten", personView);
            mainFrameController.getTabs().setActionListener(frameController.getTabsController());
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab("Personstammdaten"));
            mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab("Personstammdaten"));
            mainFrameController.setChangeListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (frameController.getTabs().indexOfTab("Personstammdaten") != -1){
            if(frameController.getTabs().getSelectedIndex() == frameController.getTabs().indexOfTab("Personstammdaten")){
                frameController.getWindow().add(toolbarShowPerson.getToolbar(), BorderLayout.NORTH);
            } else {
                frameController.getWindow().remove(toolbarShowPerson.getToolbar());
            }
        }


    }

    public ToolbarShowPersonController getToolbarShowPerson() {
        return toolbarShowPerson;
    }
}
