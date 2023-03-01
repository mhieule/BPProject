package excelchaos_view;

import excelchaos_view.components.CustomTable;

import javax.swing.*;
import java.awt.*;

public class ManualSalaryEntryView extends JPanel {

    private CustomTable table;

    public void init(){
        setLayout(new BorderLayout());
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(table);
        //tca.adjustColumns();



    }

    public void createTable(String[][] data,String[] columnNames){
        table = new CustomTable(data,columnNames);
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
