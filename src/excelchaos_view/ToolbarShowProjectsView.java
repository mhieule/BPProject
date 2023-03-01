package excelchaos_view;

import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolbarShowProjectsView extends SearchPanelToolbar {
    private JButton insertProject;
    private JButton editProject;
    private JButton deleteProject;
    private JButton costOverview;


    public void init() {
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));
        insertProject = new JButton("Eintrag hinzufügen");
        editProject = new JButton("Eintrag bearbeiten");
        deleteProject = new JButton("Eintrag löschen");
        costOverview = new JButton("Projektplanung anzeigen");

        add(insertProject);
        add(editProject);
        add(deleteProject);
        add(costOverview);
        setUpSearchPanel();

        editProject.setEnabled(false);
        deleteProject.setEnabled(false);
        costOverview.setEnabled(false);
    }

    public void setActionListener(ActionListener l) {
        insertProject.addActionListener(l);
        editProject.addActionListener(l);
        deleteProject.addActionListener(l);
        costOverview.addActionListener(l);
    }

    public JButton getInsertProject() {
        return insertProject;
    }

    public JButton getEditProject() {
        return editProject;
    }

    public JButton getDeleteProject() {
        return deleteProject;
    }

    public JButton getCostOverview() {
        return costOverview;
    }

}
