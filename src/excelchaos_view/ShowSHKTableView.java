package excelchaos_view;

import excelchaos_model.customcomponentmodels.CustomTableColumnAdjuster;
import excelchaos_view.components.CustomTable;

import javax.swing.*;

import java.awt.*;

public class ShowSHKTableView extends JPanel {
    private CustomTable shkTable;

    /**
     * Sets the layout for this component.
     */
    public void init(){
        setLayout(new BorderLayout());
    }

    /**

     Creates a custom JTable with the provided data and column names, and adds it to the current container.
     The second column of the table is hidden.
     @param tableData a 2D array of String containing the data to be displayed in the table
     @param columnNames an array of String containing the names of the columns to be displayed in the table
     */
    public void createSHKTable(String [][] tableData, String[] columnNames){
        shkTable = new CustomTable(tableData,columnNames);
        shkTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        shkTable.getColumnModel().getColumn(1).setMinWidth(0);
        shkTable.getColumnModel().getColumn(1).setMaxWidth(0);
        shkTable.getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(shkTable);
        tca.adjustColumns();
        JScrollPane scrollpane = new JScrollPane(shkTable);
        scrollpane.setVisible(true);


        add(scrollpane);
        revalidate();
        repaint();
    }

    public CustomTable getTable() {
        return shkTable;
    }
}
