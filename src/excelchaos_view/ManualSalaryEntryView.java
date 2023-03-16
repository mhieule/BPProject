package excelchaos_view;

import excelchaos_view.components.CustomTable;

import javax.swing.*;
import java.awt.*;

public class ManualSalaryEntryView extends JPanel {
    private CustomTable table;

    /**
     * sets the layout of the view
     */
    public void init() {
        setLayout(new BorderLayout());
    }

    /**
     * creates a table with the given data and column names
     *
     * @param data        String[][] containing the data
     * @param columnNames String[] containing the column names
     */
    public void createTable(String[][] data, String[] columnNames) {
        table = new CustomTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);

        add(scrollPane);
        revalidate();
        repaint();
    }

    public CustomTable getTable() {
        return table;
    }
}
