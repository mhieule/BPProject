package excelchaos_view;

import javax.swing.*;
import java.awt.*;

/**
 * This class needs complete reworking and implementation since it is unfinished.
 */
public class SalaryHistoryView extends JPanel {

    private JTable table;

    public void init() {
        setLayout(new BorderLayout());
    }

    public void createTable(String[][] data, String[] columnNames) {
        table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);

        add(scrollPane);
        revalidate();
        repaint();
    }

    public JTable getTable() {
        return table;
    }
}
