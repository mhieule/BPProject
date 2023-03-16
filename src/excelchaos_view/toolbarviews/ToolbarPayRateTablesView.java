package excelchaos_view.toolbarviews;

import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The ToolbarPayRateTablesView class represents the toolbar used in the PayRateTableView to create, edit, and delete pay rate tables.
 * It extends the SearchPanelToolbar class to inherit the basic search functionality.
 *
 * @see excelchaos_view.PayRateTablesView
 */
public class ToolbarPayRateTablesView extends SearchPanelToolbar {
    /**
     * The button to insert a new payrate table
     */
    private JButton insertNewPayRateTable;
    /**
     * The button to edit an existing pay rate table.
     */
    private JButton editExistingPayRateTable;
    /**
     * The button to delete an existing pay rate table.
     */
    private JButton deleteExistingPayRateTable;

    /**
     * Initializes the toolbar with the buttons to create, edit, and delete pay rate tables as well as initializes the view with background color, layout and non-floatable property
     */
    public void init() {
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));
        insertNewPayRateTable = new JButton("Neue Entgelttabelle erstellen");
        editExistingPayRateTable = new JButton("Ausgewählte Entgelttabelle öffnen");
        deleteExistingPayRateTable = new JButton("Bestehende Entgelttabelle löschen");
        add(insertNewPayRateTable);
        add(editExistingPayRateTable);
        add(deleteExistingPayRateTable);


    }
    /**
     * Sets the same ActionListener for the add, edit, delete, and export buttons, allowing them to share the controller to handle event associated with these buttons
     *
     * @param l the ActionListener to set
     */
    public void setActionListener(ActionListener l) {
        insertNewPayRateTable.addActionListener(l);
        editExistingPayRateTable.addActionListener(l);
        deleteExistingPayRateTable.addActionListener(l);
    }

    /**
     * @return {@link #insertNewPayRateTable}
     */
    public JButton getInsertNewPayRateTable() {
        return insertNewPayRateTable;
    }
    /**
     * @return {@link #editExistingPayRateTable}
     */
    public JButton getEditExistingPayRateTable() {
        return editExistingPayRateTable;
    }
    /**
     * @return {@link #deleteExistingPayRateTable}
     */
    public JButton getDeleteExistingPayRateTable() {
        return deleteExistingPayRateTable;
    }
}
