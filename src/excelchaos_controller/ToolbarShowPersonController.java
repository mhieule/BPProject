package excelchaos_controller;

import excelchaos_view.MainFrame;
import excelchaos_view.ToolbarShowPersonView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarShowPersonController {
    private ToolbarShowPersonView toolbar;

    public ToolbarShowPersonController(){

        toolbar = new ToolbarShowPersonView();
        toolbar.init();


    }

    public ToolbarShowPersonView getToolbar() {
        return toolbar;
    }



}
