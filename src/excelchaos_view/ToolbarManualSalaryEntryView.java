package excelchaos_view;

import excelchaos_model.database.EmployeeDataManager;
import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class ToolbarManualSalaryEntryView extends SearchPanelToolbar {

    private JComboBox nameComboBox;

    private JButton addSalaryEntry,editSalaryEntry,deleteSalaryEntry,exportToCSV;

    private JLabel nameLabel = new JLabel("Person auswählen:");

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));

        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        ArrayList<String> employeeNames = new ArrayList<String>(List.of(employeeDataManager.getAllEmployeesNameList()));
        ArrayList<String>names = new ArrayList<String>();
        names.add("Keine Auswahl");
        names.addAll(employeeNames);

        addSalaryEntry = new JButton("Eintrag hinzufügen");
        editSalaryEntry = new JButton("Eintrag bearbeiten");
        deleteSalaryEntry = new JButton("Eintrag löschen");
        exportToCSV = new JButton("Daten als CSV exportieren");

        nameComboBox = new JComboBox(names.toArray());
        nameComboBox.setBackground(Color.WHITE);
        nameComboBox.setMaximumRowCount(8);
        add(nameLabel);
        addSeparator(new Dimension(5,10));
        add(nameComboBox);
        addSeparator(new Dimension(20,10));
        add(addSalaryEntry);
        add(editSalaryEntry);
        add(deleteSalaryEntry);
        setUpSearchPanel();
        add(exportToCSV);
        editSalaryEntry.setEnabled(false);
        deleteSalaryEntry.setEnabled(false);
        addSalaryEntry.setEnabled(false);
        exportToCSV.setEnabled(false);
    }

    public void setActionListener(ActionListener l){
        addSalaryEntry.addActionListener(l);
        editSalaryEntry.addActionListener(l);
        deleteSalaryEntry.addActionListener(l);
        exportToCSV.addActionListener(l);
    }

    public void setItemListener(ItemListener l){
        nameComboBox.addItemListener(l);
    }

    public JComboBox getNameComboBox(){
        return nameComboBox;
    }

    public JButton getAddSalaryEntry() {
        return addSalaryEntry;
    }

    public JButton getEditSalaryEntry() {
        return editSalaryEntry;
    }

    public JButton getDeleteSalaryEntry() {
        return deleteSalaryEntry;
    }

    public JButton getExportToCSV() {
        return exportToCSV;
    }
}
