package excelchaos_view;

import excelchaos_model.CustomTableColumnAdjuster;
import excelchaos_view.components.CustomTable;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShowSHKTableView extends JPanel {
    private CustomTable shkTable;

    public void init(){
        setLayout(new BorderLayout());
    }

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
}
