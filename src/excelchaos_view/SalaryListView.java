package excelchaos_view;

import excelchaos_model.*;
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

    public void init() {
        setLayout(new BorderLayout());
    }

    public void createSalaryTable(String[][] tableData, String[] columnNames) {
        salaryDataTable = new CustomTable(tableData, columnNames);
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

    public void updateTable(String[][] tableData){
        CustomTableModel customTableModel = new CustomTableModel(tableData, columns);
        salaryDataTable.setModel(customTableModel);
        salaryDataTable.getColumnModel().getColumn(1).setMinWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setMaxWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(salaryDataTable);
        tca.adjustColumns();
    }

    public void changeToFuturePayLevelTable(String[][] tableData){
        CustomTableModel customTableModel = new CustomTableModel(tableData, next2PayLevelIncreaseColumns);
        salaryDataTable.setModel(customTableModel);
        salaryDataTable.getColumnModel().getColumn(1).setMinWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setMaxWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(salaryDataTable);
        tca.adjustColumns();
    }

    public void changeToProjectedSalaryTable(String[][] tableData){
        CustomTableModel customTableModel = new CustomTableModel(tableData, salaryLevelIncreaseBasedOnChosenDateColumns);
        salaryDataTable.setModel(customTableModel);
        salaryDataTable.getColumnModel().getColumn(1).setMinWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setMaxWidth(0);
        salaryDataTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(salaryDataTable);
        tca.adjustColumns();
    }

    public void createTableWithData(String[][] tableData) {
        createSalaryTable(tableData, columns);
    }


    public CustomTable getTable() {
        return salaryDataTable;
    }
}
