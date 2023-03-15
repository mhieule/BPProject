package excelchaos_view.toolbarviews;

import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ToolbarShowSHKTableView extends SearchPanelToolbar {

    private JButton addEntry;
    private JButton editEntry;
    private JButton deleteEntry;

    public void init() {
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));
        addEntry = new JButton("Eintrag hinzufügen");
        editEntry = new JButton("Eintrag bearbeiten");
        deleteEntry = new JButton("Eintrag löschen");

        add(addEntry);
        add(editEntry);
        add(deleteEntry);
        editEntry.setEnabled(false);
        deleteEntry.setEnabled(false);
        setUpSearchPanel();
    }

    public void setActionListener(ActionListener l) {
        addEntry.addActionListener(l);
        editEntry.addActionListener(l);
        deleteEntry.addActionListener(l);
    }

    public JButton getEditEntry() {
        return editEntry;
    }

    public JButton getAddEntry() {
        return addEntry;
    }

    public JButton getDeleteEntry() {
        return deleteEntry;
    }
}
