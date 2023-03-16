package excelchaos_view.toolbarviews;

import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * ToolbarShowPersonView is a class that extends the SearchPanelToolbar class to create a toolbar with search panel for managing
 * employee data in the ShowPersonView class. It provides buttons for inserting new employee, editing an employee's information,
 * deleting an employee's information, exporting employees' data to CSV
 *
 * @see excelchaos_view.ShowPersonView
 */
public class ToolbarShowPersonView extends SearchPanelToolbar {
    private JButton insertPerson;
    private JButton editPerson;
    private JButton deletePerson;

    private JButton exportToCSV;

    /**
     * Initializes the ToolbarSalaryListView object by setting up the toolbar components and adding them to the view. Background color, layout and floatability is also setup
     */
    public void init() {
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));
        insertPerson = new JButton("Eintrag hinzufügen");
        editPerson = new JButton("Eintrag bearbeiten");
        deletePerson = new JButton("Eintrag löschen");
        exportToCSV = new JButton("Daten als CSV exportieren");

        add(insertPerson);
        add(editPerson);
        editPerson.setEnabled(false);
        add(deletePerson);
        deletePerson.setEnabled(false);
        setUpSearchPanel();
        add(exportToCSV);

    }
    /**
     * Sets the same ActionListener for the buttons in the toolbar, allowing them to share the same controller which control event handling
     * @see excelchaos_controller.ShowPersonController;
     * @param l the ActionListener to be set
     */
    public void setActionListener(ActionListener l) {
        insertPerson.addActionListener(l);
        editPerson.addActionListener(l);
        deletePerson.addActionListener(l);
        exportToCSV.addActionListener(l);
    }
    /**
     * @return {@link #insertPerson}
     */
    public JButton getInsertPerson() {
        return insertPerson;
    }
    /**
     * @return {@link #editPerson}
     */
    public JButton getEditPerson() {
        return editPerson;
    }
    /**
     * @return {@link #deletePerson}
     */
    public JButton getDeletePerson() {
        return deletePerson;
    }
    /**
     * @return {@link #exportToCSV}
     */
    public JButton getExportToCSV() {
        return exportToCSV;
    }
}
