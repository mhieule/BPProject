package excelchaos_view.toolbarviews;

import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * ToolbarShowProjectsView is a class that extends the SearchPanelToolbar class to create a toolbar with search panel for managing
 * projects' data in the ShowProjectsView class. It provides buttons for inserting new project, editing a project's information,
 * deleting a project, overviewing cost.
 *
 * @see excelchaos_view.ShowProjectsView
 */
public class ToolbarShowProjectsView extends SearchPanelToolbar {
    private JButton insertProject;
    private JButton editProject;
    private JButton deleteProject;
    private JButton costOverview;

    /**
     * Initializes the ToolbarShowProjectsView object by setting up the toolbar components and adding them to the view. Background color, layout and floatability is also setup
     * The buttons deleteProject, editProject, costOverview are set unenabled on start-up
     */
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
    /**
     * Sets the same ActionListener for the buttons in the toolbar, allowing them to share the same controller which control event handling
     * @see excelchaos_controller.ToolbarShowProjectsController;
     * @param l the ActionListener to be set
     */
    public void setActionListener(ActionListener l) {
        insertProject.addActionListener(l);
        editProject.addActionListener(l);
        deleteProject.addActionListener(l);
        costOverview.addActionListener(l);
    }
    /**
     * @return {@link #insertProject}
     */
    public JButton getInsertProject() {
        return insertProject;
    }
    /**
     * @return {@link #editProject}
     */
    public JButton getEditProject() {
        return editProject;
    }
    /**
     * @return {@link #deleteProject}
     */
    public JButton getDeleteProject() {
        return deleteProject;
    }
    /**
     * @return {@link #costOverview}
     */
    public JButton getCostOverview() {
        return costOverview;
    }

}
