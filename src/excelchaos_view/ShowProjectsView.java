package excelchaos_view;

import excelchaos_model.customcomponentmodels.CustomTableColumnAdjuster;
import excelchaos_view.components.CustomTable;

import javax.swing.*;
import java.awt.*;

public class ShowProjectsView extends JPanel {
    private CustomTable projectsTable;

    /**
     * Sets the layout for this component.
     */
    public void init() {
        setLayout(new BorderLayout());
    }

    /**
     * Creates a table to display project data with the given table data and column names.
     * The table is created using a CustomTable object with the provided data and column names, and is set to not auto-resize.
     *
     * @param tableData   a two-dimensional String array containing the data to be displayed in the table
     * @param columnNames a String array containing the names of the columns to be displayed in the table
     */
    public void createProjectsTable(String[][] tableData, String[] columnNames) {
        projectsTable = new CustomTable(tableData, columnNames);
        projectsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        projectsTable.getColumnModel().getColumn(1).setMinWidth(0);
        projectsTable.getColumnModel().getColumn(1).setMaxWidth(0);
        projectsTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(projectsTable);
        tca.adjustColumns();
        JScrollPane scrollpane = new JScrollPane(projectsTable);
        scrollpane.setVisible(true);

        add(scrollpane);
        revalidate();
        repaint();
    }


    public CustomTable getTable() {
        return projectsTable;
    }
}
