package excelchaos_view;

import excelchaos_model.*;
import excelchaos_model.utility.TableColumnAdjuster;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class SalaryListView extends JPanel {
    private CustomTable salaryDataTable;
    public void init(){
        setLayout(new BorderLayout());
    }

    public void createSalaryTable(String [][] tableData, String[] columnNames){
        salaryDataTable = new CustomTable(tableData,columnNames);
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


    public CustomTable getTable() {
        return salaryDataTable;
    }
}
