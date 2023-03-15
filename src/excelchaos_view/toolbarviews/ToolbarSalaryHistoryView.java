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

public class ToolbarSalaryHistoryView extends SearchPanelToolbar {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private JComboBox nameComboBox;

    private JButton änderungen;

    private JLabel nameLabel;

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));

        nameLabel = new JLabel("Name: ");


        ArrayList<String> employeeNames = new ArrayList<String>(List.of(employeeDataManager.getAllEmployeesNameList()));
        ArrayList<String>names = new ArrayList<String>();
        names.add("Keine Auswahl");
        names.addAll(employeeNames);

        nameComboBox = new JComboBox(names.toArray());
        nameComboBox.setMaximumRowCount(10);

        änderungen = new JButton("Änderungen");


        add(nameLabel);
        add(nameComboBox);
        addSeparator(new Dimension(20,30));
        add(änderungen);
        addSeparator(new Dimension(20,30));
        setUpSearchPanel();
    }

    public void setActionListener(ActionListener l){
        änderungen.addActionListener(l);
    }

    public void setItemListener(ItemListener l){
        nameComboBox.addItemListener(l);
    }

    public JComboBox getNameComboBox(){
        return nameComboBox;
    }

    public JButton getÄnderungen() {
        return änderungen;
    }
}
