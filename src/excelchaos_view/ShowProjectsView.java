package excelchaos_view;

import excelchaos_model.*;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ShowProjectsView extends JPanel {
    private CustomTable projectsTable;

    public void init() {
        setLayout(new BorderLayout());
    }

    public void createProjectsTable(String[][] tableData, String[] columnNames){
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
