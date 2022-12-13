package excelchaos_controller;

import excelchaos_view.ShowPersonView;
import excelchaos_view.SideMenuPanelActionLogView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowPersonController implements ActionListener, ChangeListener {
    private ShowPersonView personView;
    private MainFrameController frameController;
    private ToolbarShowPersonController toolbarShowPerson;
    private String title = "Personalstammdaten";

    public ShowPersonController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        personView = new ShowPersonView();
        toolbarShowPerson = new ToolbarShowPersonController(frameController);
        personView.init();
    }

    public ShowPersonView getPersonView() {
        return personView;
    }

    public void showPersonView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
            //mainFrameController.getWindow().add(toolbarShowPerson.getToolbar(), BorderLayout.NORTH);
            //mainFrameController.getTabs().addTab("Personstammdaten", personView);
            //mainFrameController.getTabs().setActionListener(frameController.getTabsController());
            //mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab("Personstammdaten"));
            mainFrameController.addTab(title,toolbarShowPerson.getToolbar(),personView);
            mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
            SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
            mainFrameController.setChangeListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (frameController.getTabs().indexOfTab(title) != -1){
            frameController.tabSwitch(title,getToolbarShowPerson().getToolbar());
        }


    }

    public ToolbarShowPersonController getToolbarShowPerson() {
        return toolbarShowPerson;
    }
}
