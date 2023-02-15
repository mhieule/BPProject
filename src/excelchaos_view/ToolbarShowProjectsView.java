package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolbarShowProjectsView extends JToolBar {
    private JButton insertProject;
    private JButton editProject;
    private JButton deleteProject;
    private JButton costOverview;

    private JLabel searchLabel;
    private JTextField searchField;

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        insertProject = new JButton("hinzufügen");
        editProject = new JButton("bearbeiten");
        deleteProject = new JButton("löschen");
        costOverview = new JButton("Kostenübersicht");
        searchLabel = new JLabel("Suchen:");
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(130,30));

        add(insertProject);
        addSeparator(new Dimension(20,30));
        add(editProject);
        addSeparator(new Dimension(20,30));
        add(deleteProject);
        addSeparator(new Dimension(20,30));
        add(costOverview);
        addSeparator(new Dimension(20,30));
        add(searchLabel);
        add(searchField);
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

    public JTextField getSearchField() {
        return searchField;
    }
}
