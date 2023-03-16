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

/**
 * ToolbarSalaryHistoryView class extends the SearchPanelToolbar class and represents
 * the toolbar view for the salary history view in the ExcelChaos application. This view
 * contains a combo box to select an employee name and a button to show the changes in the
 * salary history of the selected employee.
 *
 * @see excelchaos_view.SalaryHistoryView
 */
public class ToolbarSalaryHistoryView extends SearchPanelToolbar {
    /**
     * create an instance of the EmployeeDataManager class to take advantage of the calculation in this model class
     */
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    /**
     * Combo box allowing the user to choose the employee from a list
     */
    private JComboBox nameComboBox;
    /**
     * Button to show all the salary changes made to this employee
     */
    private JButton änderungen;

    /**
     * Label to display the name of the employee
     */
    private JLabel nameLabel;

    /**
     * Initializes the toolbar view by setting its background color, layout and
     * adding its components such as the combo box for employee name selection,
     * a label for the name, a button to show the changes and a search panel.
     */
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

    /**
     * set the ActionListener for Aenderungen button
     * @param l the action listener to set
     */
    public void setActionListener(ActionListener l){
        änderungen.addActionListener(l);
    }

    /**
     * Set the item listener to nameComboBox
     * @param l the item listener to set
     */
    public void setItemListener(ItemListener l){
        nameComboBox.addItemListener(l);
    }
    /**
     * @return {@link #nameComboBox}
     */
    public JComboBox getNameComboBox(){
        return nameComboBox;
    }
    /**
     * @return {@link #änderungen}
     */
    public JButton getÄnderungen() {
        return änderungen;
    }
}
