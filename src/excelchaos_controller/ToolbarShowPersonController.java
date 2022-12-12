package excelchaos_controller;

import excelchaos_view.MainFrame;
import excelchaos_view.ToolbarShowPersonView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarShowPersonController implements ActionListener {
    private ToolbarShowPersonView toolbar;
    private MainFrame frame;

    public ToolbarShowPersonController(MainFrame window){
        frame = window;
        toolbar = new ToolbarShowPersonView();
        toolbar.init();
        toolbar.setActionListener(this);

    }

    public ToolbarShowPersonView getToolbar() {
        return toolbar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
