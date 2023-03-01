package excelchaos_view;

import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolbarPayRateTablesView extends SearchPanelToolbar {
    private JButton insertNewPayRateTable;
    private JButton editExistingPayRateTable;
    private JButton deleteExistingPayRateTable;

    public void init() {
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));
        insertNewPayRateTable = new JButton("Neue Entgelttabelle erstellen");
        editExistingPayRateTable = new JButton("Bestehende Entgelttabelle bearbeiten");
        deleteExistingPayRateTable = new JButton("Bestehende Entgelttabelle löschen");
        add(insertNewPayRateTable);
        add(editExistingPayRateTable);
        add(deleteExistingPayRateTable);


    }

    public void setActionListener(ActionListener l) {
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
