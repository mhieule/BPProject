package excelchaos_view;

import excelchaos_model.CustomTableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ManualSalaryEntryView extends JPanel {

    private CustomTable table;

    public void init(DefaultTableModel model){
        setLayout(new BorderLayout());



        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        table = new CustomTable(model);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(table);
        //tca.adjustColumns();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVisible(true);

        add(scrollPane);


    }

    public CustomTable getTable() {
        return table;
    }
}
