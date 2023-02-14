package excelchaos_view;

import excelchaos_model.EmployeeDataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class ToolbarSalaryHistoryView extends JToolBar {

    private JComboBox nameComboBox;

    private JTextField searchField;

    private JButton änderungen;

    private JLabel nameLabel, searchLabel;

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        nameLabel = new JLabel("Name: ");
        searchLabel = new JLabel("Suchen: ");

        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        ArrayList<String> employeeNames = new ArrayList<String>(List.of(employeeDataManager.getAllEmployeesNameList()));
        ArrayList<String>names = new ArrayList<String>();
        names.add("Keine Auswahl");
        names.addAll(employeeNames);

        nameComboBox = new JComboBox(names.toArray());
        nameComboBox.setMaximumRowCount(10);

        änderungen = new JButton("Änderungen");
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(130,30));

        add(nameLabel);
        add(nameComboBox);
        addSeparator(new Dimension(20,30));
        add(änderungen);
        addSeparator(new Dimension(20,30));
        add(searchLabel);
        add(searchField);
    }

    public void setActionListener(ActionListener l){
        änderungen.addActionListener(l);
        searchField.addActionListener(l);
    }

    public void setItemListener(ItemListener l){
        nameComboBox.addItemListener(l);
    }

    public JComboBox getNameComboBox(){
        return nameComboBox;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getÄnderungen() {
        return änderungen;
    }
}
