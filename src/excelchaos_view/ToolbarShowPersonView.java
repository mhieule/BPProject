package excelchaos_view;

import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolbarShowPersonView extends SearchPanelToolbar {
    private JButton insertPerson;
    private JButton editPerson;
    private JButton deletePerson;

    private JButton exportToCSV;


    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));
        insertPerson = new JButton("Eintrag hinzufügen");
        editPerson = new JButton("Eintrag bearbeiten");
        deletePerson = new JButton("Eintrag löschen");
        exportToCSV = new JButton("Daten als CSV exportieren");

        add(insertPerson);
        add(editPerson);
        editPerson.setEnabled(false);
        add(deletePerson);
        deletePerson.setEnabled(false);
        setUpSearchPanel();
        add(exportToCSV);

    }

    public void setActionListener(ActionListener l) {
        insertPerson.addActionListener(l);
        editPerson.addActionListener(l);
        deletePerson.addActionListener(l);
        exportToCSV.addActionListener(l);
    }

    public JButton getInsertPerson() {
        return insertPerson;
    }

    public JButton getEditPerson() {
        return editPerson;
    }

    public JButton getDeletePerson() {
        return deletePerson;
    }

   public JButton getExportToCSV() {
        return exportToCSV;
    }
}
