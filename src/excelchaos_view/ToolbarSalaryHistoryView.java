package excelchaos_view;

import excelchaos_model.EmployeeDataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolbarSalaryHistoryView extends JToolBar {
    private JComboBox nameComboBox;

    private JTextField searchField;

    private JButton änderungen;

    private JLabel nameLabel, searchLabel;

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);

        nameLabel = new JLabel("Name: ");
        searchLabel = new JLabel("Suchen: ");

        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        String[] names = employeeDataManager.getAllEmployeesNameList();
        nameComboBox = new JComboBox(names);
        nameComboBox.setMaximumRowCount(20);

        änderungen = new JButton("Änderungen");
        searchField = new JTextField();

        add(nameLabel);
        add(nameComboBox);
        addSeparator(new Dimension(20,30));
        add(änderungen);
        addSeparator(new Dimension(20,30));
        add(searchLabel);
        add(searchField);
    }

    public void setActionListener(ActionListener l){
        nameComboBox.addActionListener(l);
        änderungen.addActionListener(l);
        searchField.addActionListener(l);
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
