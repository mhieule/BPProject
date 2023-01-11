package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolbarPayRateTablesView extends JToolBar {
    private JButton insertNewPayRateTable;
    private JButton editExistingPayRateTable;
    private JButton deleteExistingPayRateTable;

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new FlowLayout());
        insertNewPayRateTable = new JButton("Neue Entgelttabelle erstellen");
        editExistingPayRateTable = new JButton("Bestehende Entgelttabelle bearbeiten");
        deleteExistingPayRateTable = new JButton("Bestehende Entgelttabelle löschen");
        addSeparator(new Dimension(130,30));
        add(insertNewPayRateTable);
        addSeparator(new Dimension(20,30));
        add(editExistingPayRateTable);
        addSeparator(new Dimension(20,30));
        add(deleteExistingPayRateTable);
        addSeparator(new Dimension(20,30));


    }
    public void setActionListener(ActionListener l){
        insertNewPayRateTable.addActionListener(l);
        editExistingPayRateTable.addActionListener(l);
        deleteExistingPayRateTable.addActionListener(l);
    }

    public JButton getInsertNewPayRateTable() {
        return insertNewPayRateTable;
    }

    public JButton getEditExistingPayRateTable() {
        return editExistingPayRateTable;
    }

    public JButton getDeleteExistingPayRateTable() {
        return deleteExistingPayRateTable;
    }
}
