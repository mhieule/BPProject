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
 * ToolbarSalaryIncreaseView is a class that extends the SearchPanelToolbar class to create a toolbar viewed for managing
 * employee salary data in the SalaryIncreaseView. It provides buttons for adding, editing, and deleting salary entries, as well as exporting
 * data to CSV. It also includes a search panel and a combo box for selecting an employee by name.
 *
 * @see excelchaos_view.SalaryIncreaseView
 */
public class ToolbarSalaryIncreaseView extends SearchPanelToolbar {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private JComboBox nameComboBox;

    private JButton doSalaryIncrease, deleteSalaryEntry, exportToCSV, editSalaryEntry;

    private JLabel nameLabel = new JLabel("Person auswählen:");

    /**
     * Initializes the ToolbarSalaryIncreaseView by setting its layout, adding buttons and components to it,
     * and disabling them by default (as per default, no row is selected, so no buttons should be made available).
     */
    public void init() {
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));


        ArrayList<String> employeeNames = new ArrayList<String>(List.of(employeeDataManager.getAllEmployeesNameList()));
        ArrayList<String> names = new ArrayList<String>();
        names.add("Keine Auswahl");
        names.addAll(employeeNames);

        doSalaryIncrease = new JButton("Eintrag hinzufügen");
        editSalaryEntry = new JButton("Eintrag bearbeiten");
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
        add(editSalaryEntry);
        add(deleteSalaryEntry);
        setUpSearchPanel();
        add(exportToCSV);
        editSalaryEntry.setEnabled(false);
        deleteSalaryEntry.setEnabled(false);
        doSalaryIncrease.setEnabled(false);
        exportToCSV.setEnabled(false);
    }

    /**
     * Sets the same ActionListener for the buttons in the toolbar, allowing them to share the same controller which control event handling
     * @see excelchaos_controller.ToolbarSalaryIncreaseController
     * @param l the ActionListener to be set
     */
    public void setActionListener(ActionListener l) {
        doSalaryIncrease.addActionListener(l);
        editSalaryEntry.addActionListener(l);
        deleteSalaryEntry.addActionListener(l);
        exportToCSV.addActionListener(l);
    }

    /**
     * Sets an ItemListener for the nameComboBox in the toolbar.
     *
     * @param l the ItemListener to be set
     */
    public void setItemListener(ItemListener l) {
        nameComboBox.addItemListener(l);
    }

    /**
     * @return {@link #nameComboBox}
     */
    public JComboBox getNameComboBox() {
        return nameComboBox;
    }
    /**
     * @return {@link #doSalaryIncrease}
     */
    public JButton getDoSalaryIncrease() {
        return doSalaryIncrease;
    }
    /**
     * @return {@link #editSalaryEntry}
     */
    public JButton getEditSalaryEntry() {
        return editSalaryEntry;
    }
    /**
     * @return {@link #deleteSalaryEntry}
     */
    public JButton getDeleteSalaryEntry() {
        return deleteSalaryEntry;
    }
    /**
     * @return {@link #exportToCSV}
     */
    public JButton getExportToCSV() {
        return exportToCSV;
    }
}
