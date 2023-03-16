package excelchaos_view;

import excelchaos_view.components.CustomTable;

import javax.swing.*;
import java.awt.*;

public class SalaryIncreaseView extends JPanel {

    private CustomTable salaryIncreaseTable;

    /**
     * Sets the layout for this component.
     */
    public void init() {
        setLayout(new BorderLayout());
    }

    /**
     * Creates a new salaryIncrease data table using the specified data and column names.
     *
     * @param tableData   a 2D array of strings representing the data to be displayed in the table
     * @param columnNames an array of strings representing the names of the columns in the table
     */
    public void createTable(String[][] tableData, String[] columnNames) {
        salaryIncreaseTable = new CustomTable(tableData, columnNames);
        JScrollPane scrollPane = new JScrollPane(salaryIncreaseTable);
        scrollPane.setVisible(true);

        add(scrollPane);
        revalidate();
        repaint();
    }

    public CustomTable getSalaryIncreaseTable() {
        return salaryIncreaseTable;
    }
}
