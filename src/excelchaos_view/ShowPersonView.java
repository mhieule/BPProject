package excelchaos_view;

import excelchaos_model.customcomponentmodels.CustomTableColumnAdjuster;
import excelchaos_model.customcomponentmodels.CustomTableModel;
import excelchaos_view.components.CustomTable;

import javax.swing.*;
import java.awt.*;

public class ShowPersonView extends JPanel {
    private CustomTable employeeDataTable;

    private String columns[] = {"ID", "Vorname", "Name", "Straße", "Haunsummer", "Adresszusatz", "Postleitzahl", "Stadt",
            "Geburtsdatum", "E-Mail Privat", "Telefon Privat", "Telefon TUDA", "Staatsangehörigkeit 1", "Staatsangehörigkeit 2", "Visum Gültigkeit", "Personalnummer", "Transpondernummer", "Büronummer", "TU-ID",
            "Anstellungsart", "Beschäftigungsbeginn", "Beschäftigungsende", "Beschäftigungsumfgang", "Gehaltsklasse", "Gehaltsstufe", "VBL-Status", "SHK Stundensatz", "Gehalt Eingeplant bis"
    };

    /**
     * Sets the layout for this component.
     */
    public void init() {
        setLayout(new BorderLayout());
    }

    /**
     * Creates a new employee data table using the specified data and column names.
     *
     * @param tableData   a 2D array of strings representing the data to be displayed in the table
     * @param columnNames an array of strings representing the names of the columns in the table
     */
    private void createEmployeeTable(String[][] tableData, String[] columnNames) {
        employeeDataTable = new CustomTable(tableData, columnNames);
        employeeDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        employeeDataTable.getColumnModel().getColumn(1).setMinWidth(0);
        employeeDataTable.getColumnModel().getColumn(1).setMaxWidth(0);
        employeeDataTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(employeeDataTable);
        tca.adjustColumns();
        JScrollPane scrollpane = new JScrollPane(employeeDataTable);
        scrollpane.setVisible(true);

        add(scrollpane);
        revalidate();
        repaint();
    }

    /**
     * Creates a table with the given data and sets it as the employee data table.
     *
     * @param tableData a 2-dimensional array of Strings representing the data to be displayed in the table
     */
    public void createTableWithData(String[][] tableData) {
        createEmployeeTable(tableData, columns);
    }

    /**
     * Updates the employeeDataTable with the given tableData.
     *
     * @param tableData a two-dimensional array of Strings containing the data for the table
     *
     */
    public void updateTable(String[][] tableData) {
        CustomTableModel customTableModel = new CustomTableModel(tableData, columns);
        employeeDataTable.setModel(customTableModel);
        employeeDataTable.getColumnModel().getColumn(1).setMinWidth(0);
        employeeDataTable.getColumnModel().getColumn(1).setMaxWidth(0);
        employeeDataTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(employeeDataTable);
        tca.adjustColumns();
    }

    public CustomTable getTable() {
        return employeeDataTable;
    }
}
