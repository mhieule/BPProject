package excelchaos_view.toolbarviews;

import excelchaos_model.database.EmployeeDataManager;
import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class ToolbarSalaryIncreaseView extends SearchPanelToolbar {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private JComboBox nameComboBox;

    private JButton doSalaryIncrease, deleteSalaryEntry, exportToCSV;//,editSalaryEntry; TODO Edit Button implementieren, Vielleicht auch nicht

    private JLabel nameLabel = new JLabel("Person auswählen:");

    public void init() {
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));


        ArrayList<String> employeeNames = new ArrayList<String>(List.of(employeeDataManager.getAllEmployeesNameList()));
        ArrayList<String> names = new ArrayList<String>();
        names.add("Keine Auswahl");
        names.addAll(employeeNames);

        doSalaryIncrease = new JButton("Eintrag hinzufügen");
        //   editSalaryEntry = new JButton("Eintrag bearbeiten");
        deleteSalaryEntry = new JButton("Eintrag löschen");
        exportToCSV = new JButton("Daten als CSV exportieren");
        nameComboBox = new JComboBox(names.toArray());
        nameComboBox.setBackground(Color.WHITE);
        nameComboBox.setMaximumRowCount(8);
        add(nameLabel);
        addSeparator(new Dimension(5, 10));
        add(nameComboBox);
        addSeparator(new Dimension(20, 10));
        add(doSalaryIncrease);
        // add(editSalaryEntry);
        add(deleteSalaryEntry);
        setUpSearchPanel();
        add(exportToCSV);
        //   editSalaryEntry.setEnabled(false);
        deleteSalaryEntry.setEnabled(false);
        doSalaryIncrease.setEnabled(false);
        exportToCSV.setEnabled(false);
    }

    public void setActionListener(ActionListener l) {
        doSalaryIncrease.addActionListener(l);
        // editSalaryEntry.addActionListener(l);
        deleteSalaryEntry.addActionListener(l);
        exportToCSV.addActionListener(l);
    }

    public void setItemListener(ItemListener l) {
        nameComboBox.addItemListener(l);
    }

    public JComboBox getNameComboBox() {
        return nameComboBox;
    }

    public JButton getDoSalaryIncrease() {
        return doSalaryIncrease;
    }

  /*  public JButton getEditSalaryEntry() {
        return editSalaryEntry;
    }*/

    public JButton getDeleteSalaryEntry() {
        return deleteSalaryEntry;
    }

    public JButton getExportToCSV() {
        return exportToCSV;
    }
}
