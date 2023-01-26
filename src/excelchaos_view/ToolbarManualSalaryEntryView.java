package excelchaos_view;

import excelchaos_model.EmployeeDataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ToolbarManualSalaryEntryView extends JToolBar {

    private JComboBox nameComboBox;

    private JButton addSalaryEntry;

    private JLabel nameLabel = new JLabel("Person auswählen:");

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        String[] names = employeeDataManager.getAllEmployeesNameList();

        addSalaryEntry = new JButton("Gehaltseintrag hinzufügen");
        nameComboBox = new JComboBox(names);
        nameComboBox.setBackground(Color.WHITE);
        nameComboBox.setMaximumRowCount(8);
        addSeparator(new Dimension(10,10));
        add(nameLabel);
        addSeparator(new Dimension(5,10));
        add(nameComboBox);
        addSeparator(new Dimension(20,10));
        add(addSalaryEntry);
    }

    public void setActionListener(ActionListener l){
        addSalaryEntry.addActionListener(l);
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
}
