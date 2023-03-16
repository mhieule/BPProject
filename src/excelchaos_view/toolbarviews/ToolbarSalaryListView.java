package excelchaos_view.toolbarviews;

import excelchaos_view.components.SearchPanel;
import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 * ToolbarSalaryListView is a class that extends the SearchPanelToolbar class to create a toolbar with search panel for managing
 * employee salary data in the SalaryListView class. It provides buttons for editing employee salary data, increasing an employee's salary,
 * displaying projected salary data, exporting salary data to CSV, and showing next salary grade increases.
 *
 * @see excelchaos_view.SalaryListView
 */
public class ToolbarSalaryListView extends SearchPanelToolbar {
    private JButton editEntry;
    private JButton increaseSalary;

    private JButton salaryStageOn;

    private JButton exportToCSV;

    private JButton removeAdditionalSalaryStage;

    private JToggleButton showNextPayGrade;

    /**
     * Initializes the ToolbarSalaryListView object by setting up the toolbar components and adding them to the view. The editEntry, increaseSalary and removeAdditionalSalaryStage
     * are put unenabled on start-up because on start-up, no row is selected
     */
    public void init() {
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));
        editEntry = new JButton("Eintrag bearbeiten");
        increaseSalary = new JButton("Gehaltserhöhung");
        salaryStageOn = new JButton("Gehaltsprojektion zum ...");
        removeAdditionalSalaryStage = new JButton("Gehaltsprojektion ausblenden");
        showNextPayGrade = new JToggleButton("Gehaltsstufenerhöhungen anzeigen");
        exportToCSV = new JButton("Daten als CSV exportieren");

        add(editEntry);
        editEntry.setEnabled(false);
        increaseSalary.setEnabled(false);
        removeAdditionalSalaryStage.setEnabled(false);

        add(increaseSalary);
        add(showNextPayGrade);
        add(salaryStageOn);
        add(removeAdditionalSalaryStage);
        setUpSearchPanel();
        add(exportToCSV);

    }
    /**
     * Sets the same ActionListener for the buttons in the toolbar, allowing them to share the same controller which control event handling
     * @see excelchaos_controller.SalaryListController
     * @param l the ActionListener to be set
     */
    public void setActionListener(ActionListener l) {
        editEntry.addActionListener(l);
        increaseSalary.addActionListener(l);
        salaryStageOn.addActionListener(l);
        removeAdditionalSalaryStage.addActionListener(l);
        exportToCSV.addActionListener(l);
    }

    /**
     * Set the item listener to showNextPayGrade Togglebutton
     * @param l the item listener to set
     */
    public void setItemListener(ItemListener l) {
        showNextPayGrade.addItemListener(l);
    }

    /**
     * @return {@link #showNextPayGrade}
     */
    public JToggleButton getShowNextPayGrade() {
        return showNextPayGrade;
    }

    /**
     * @return {@link #salaryStageOn}
     */
    public JButton getSalaryStageOn() {
        return salaryStageOn;
    }
    /**
     * @return {@link #removeAdditionalSalaryStage}
     */
    public JButton getRemoveAdditionalSalaryStage() {
        return removeAdditionalSalaryStage;
    }
    /**
     * @return {@link #editEntry}
     */
    public JButton getEditEntry() {
        return editEntry;
    }
    /**
     * @return {@link #exportToCSV}
     */
    public JButton getExportToCSV() {
        return exportToCSV;
    }

    /**
     * @return {@link #increaseSalary}
     */
    public JButton getIncreaseSalary() {
        return increaseSalary;
    }

    ;
}
