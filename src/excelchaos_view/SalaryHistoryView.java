package excelchaos_view;

import excelchaos_model.CustomTableColumnAdjuster;
import excelchaos_model.Employee;
import excelchaos_model.EmployeeDataManager;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SalaryHistoryView extends JPanel{

    private JTable table;

    public void init(){
        setLayout(new BorderLayout());
    }

    public void createTable(String[][] data,String[] columnNames){
        table = new JTable(data,columnNames);
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
