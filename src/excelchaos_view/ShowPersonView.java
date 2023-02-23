package excelchaos_view;

import excelchaos_model.*;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShowPersonView extends JPanel {
    private CustomTable employeeDataTable;

    public void init() {
        setLayout(new BorderLayout());
    }

    public void createEmployeeTable(String[][] tableData, String[] columnNames) {
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

    public CustomTable getTable() {
        return employeeDataTable;
    }
}
