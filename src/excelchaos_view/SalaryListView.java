package excelchaos_view;

import excelchaos_model.customcomponentmodels.CustomTableColumnAdjuster;
import excelchaos_model.customcomponentmodels.CustomTableModel;
import excelchaos_view.components.CustomTable;

import javax.swing.*;
import java.awt.*;

public class SalaryListView extends JPanel {
    private CustomTable salaryDataTable;

    private String columns[] = {
            "ID", "Vorname", "Name", "Geburtsdatum", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung"
    };

    private String next2PayLevelIncreaseColumns[] = {
            "ID", "Vorname", "Name", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung", "Höherstufung 1 ab", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung",
            "Höherstufung 2 ab", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung"
    };

    private String salaryLevelIncreaseBasedOnChosenDateColumns[] = {
            "ID", "Vorname", "Name", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung", "Gewähltes Datum", "Stufe zum gewählten Zeitpunkt",
            "Gehaltskosten zum gewählten Zeitpunkt", "Jahressonderzahlung"
    };

    /**
     * Sets the layout for this component.
     */
    public void init() {
        setLayout(new BorderLayout());
    }

    /**
     * Creates a new salary data table using the specified data and column names.
     *
     * @param tableData   a 2D array of strings representing the data to be displayed in the table
     * @param columnNames an array of strings representing the names of the columns in the table
     */
    public void createSalaryTable(String[][] tableData, String[] columnNames) {
        salaryDataTable = new CustomTable(tableData, columnNames);
        salaryDataTable.getTableHeader().setReorderingAllowed(false);
        salaryDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        salaryDataTable.getColumnModel().getColumn(1).setMinWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setMaxWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(salaryDataTable);
        tca.adjustColumns();
        JScrollPane scrollpane = new JScrollPane(salaryDataTable);
        scrollpane.setVisible(true);

        add(scrollpane);
        revalidate();
        repaint();
    }

    /**
     * Updates the salaryDataTable with the given tableData.
     *
     * @param tableData a two-dimensional array of Strings containing the data for the table
     */
    public void updateTable(String[][] tableData) {
        CustomTableModel customTableModel = new CustomTableModel(tableData, columns);
        salaryDataTable.setModel(customTableModel);
        salaryDataTable.getColumnModel().getColumn(1).setMinWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setMaxWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(salaryDataTable);
        tca.adjustColumns();
    }

    /**
     * Changes the salary data table to the future pay level table using the provided
     * 2-dimensional array of table data. The table model is created using the provided
     * data and the next 2 pay level increase columns. The new model is set as the
     * model for the salary data table.
     *
     * @param tableData a 2-dimensional array of table data containing the future pay level information
     */
    public void changeToFuturePayLevelTable(String[][] tableData) {
        CustomTableModel customTableModel = new CustomTableModel(tableData, next2PayLevelIncreaseColumns);
        salaryDataTable.setModel(customTableModel);
        salaryDataTable.getColumnModel().getColumn(1).setMinWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setMaxWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(salaryDataTable);
        tca.adjustColumns();
    }

    /**
     * Changes the data displayed in the salaryDataTable to show the projected salary table based on the chosen date.
     *
     * @param tableData the data to be displayed in the salaryDataTable
     */
    public void changeToProjectedSalaryTable(String[][] tableData) {
        CustomTableModel customTableModel = new CustomTableModel(tableData, salaryLevelIncreaseBasedOnChosenDateColumns);
        salaryDataTable.setModel(customTableModel);
        salaryDataTable.getColumnModel().getColumn(1).setMinWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setMaxWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(salaryDataTable);
        tca.adjustColumns();
    }

    /**
     * Creates a table with the given data and sets it as the salary data table.
     *
     * @param tableData a 2-dimensional array of Strings representing the data to be displayed in the table
     */
    public void createTableWithData(String[][] tableData) {
        createSalaryTable(tableData, columns);
    }


    public CustomTable getTable() {
        return salaryDataTable;
    }
}
