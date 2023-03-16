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
 * The ToolbarManualSalaryEntryView class represents a toolbar view that is used for manual salary entry view.
 * It extends the SearchPanelToolbar class, which provides search panel functionality.
 * This toolbar includes a combo box for selecting an employee, buttons for adding, editing, and deleting salary entries,
 * and a button for exporting data as CSV.
 *
 * @see excelchaos_view.ManualSalaryEntryView
 */

public class ToolbarManualSalaryEntryView extends SearchPanelToolbar {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private JComboBox nameComboBox;

    private JButton addSalaryEntry, editSalaryEntry, deleteSalaryEntry, exportToCSV;

    private JLabel nameLabel = new JLabel("Person auswählen:");

    /**
     * Initializes the toolbar by setting its layout, background color, and adding its components.
     * The combo box is populated with employee names retrieved from the EmployeeDataManager.
     * The add, edit, delete, and export buttons are added to the toolbar, and the search panel is set up.
     * The add, edit, delete, and export buttons are initially disabled.
     */
    public void init() {
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));


        ArrayList<String> employeeNames = new ArrayList<String>(List.of(employeeDataManager.getAllEmployeesNameList()));
        ArrayList<String> names = new ArrayList<String>();
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
        addSeparator(new Dimension(5, 10));
        add(nameComboBox);
        addSeparator(new Dimension(20, 10));
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

    /**
     * Sets the ActionListener for the add, edit, delete, and export buttons.
     *
     * @param l the ActionListener to set
     */
    public void setActionListener(ActionListener l) {
        addSalaryEntry.addActionListener(l);
        editSalaryEntry.addActionListener(l);
        deleteSalaryEntry.addActionListener(l);
        exportToCSV.addActionListener(l);
    }

    /**
     * Sets the ItemListener for the nameComboBox.
     *
     * @param l the ItemListener to set
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
     * @return {@link #addSalaryEntry}
     */
    public JButton getAddSalaryEntry() {
        return addSalaryEntry;
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
