package excelchaos_view.toolbarviews;

import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
/**
 * ToolbarShowSHKTableView is a class that extends the SearchPanelToolbar class to create a toolbar with search panel for managing
 * SHK table's data in the ShowSHKTableView class. It provides buttons for adding entry, editing entry and delete entry from the SHK Table
 *
 * @see excelchaos_view.ShowSHKTableView
 */
public class ToolbarShowSHKTableView extends SearchPanelToolbar {

    private JButton addEntry;
    private JButton editEntry;
    private JButton deleteEntry;

    /**
     * Initializes the view by setting up the toolbar components and adding them to the view. Background color, layout and floatability is also setup
     * The buttons deleteEntry, editEntry are set unenabled on start-up
     */
    public void init() {
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));
        addEntry = new JButton("Eintrag hinzufügen");
        editEntry = new JButton("Eintrag bearbeiten");
        deleteEntry = new JButton("Eintrag löschen");

        add(addEntry);
        add(editEntry);
        add(deleteEntry);
        editEntry.setEnabled(false);
        deleteEntry.setEnabled(false);
        setUpSearchPanel();
    }
    /**
     * Sets the same ActionListener for the buttons in the toolbar, allowing them to share the same controller which control event handling
     * @see excelchaos_controller.ToolbarShowProjectsController;
     * @param l the ActionListener to be set
     */
    public void setActionListener(ActionListener l) {
        addEntry.addActionListener(l);
        editEntry.addActionListener(l);
        deleteEntry.addActionListener(l);
    }
    /**
     * @return {@link #editEntry}
     */
    public JButton getEditEntry() {
        return editEntry;
    }
    /**
     * @return {@link #addEntry}
     */
    public JButton getAddEntry() {
        return addEntry;
    }
    /**
     * @return {@link #deleteEntry}
     */
    public JButton getDeleteEntry() {
        return deleteEntry;
    }
}
