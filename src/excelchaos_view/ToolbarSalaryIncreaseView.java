package excelchaos_view;

import excelchaos_model.EmployeeDataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class ToolbarSalaryIncreaseView extends JToolBar {

    private JComboBox nameComboBox;

    private JButton doSalaryIncrease,editSalaryEntry,deleteSalaryEntry;

    private JLabel nameLabel = new JLabel("Person auswählen:");

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        ArrayList<String> employeeNames = new ArrayList<String>(List.of(employeeDataManager.getAllEmployeesNameList()));
        ArrayList<String>names = new ArrayList<String>();
        names.add("Keine Auswahl");
        names.addAll(employeeNames);

        doSalaryIncrease = new JButton("Gehaltserhöhung durchführen");
        editSalaryEntry = new JButton("Ausgewählten Eintrag bearbeiten");
        deleteSalaryEntry = new JButton("Ausgewählten Eintrag löschen");
        nameComboBox = new JComboBox(names.toArray());
        nameComboBox.setBackground(Color.WHITE);
        nameComboBox.setMaximumRowCount(8);
        addSeparator(new Dimension(10,10));
        add(nameLabel);
        addSeparator(new Dimension(5,10));
        add(nameComboBox);
        addSeparator(new Dimension(20,10));
        add(doSalaryIncrease);
        add(editSalaryEntry);
        add(deleteSalaryEntry);
    }

    public void setActionListener(ActionListener l){
        doSalaryIncrease.addActionListener(l);
        editSalaryEntry.addActionListener(l);
        deleteSalaryEntry.addActionListener(l);
    }

    public void setItemListener(ItemListener l){
        nameComboBox.addItemListener(l);
    }
    public JComboBox getNameComboBox(){
        return nameComboBox;
    }

    public JButton getDoSalaryIncrease() {
        return doSalaryIncrease;
    }

    public JButton getEditSalaryEntry() {
        return editSalaryEntry;
    }

    public JButton getDeleteSalaryEntry() {
        return deleteSalaryEntry;
    }
}
