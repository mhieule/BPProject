package excelchaos_view;

import excelchaos_view.components.SearchPanel;
import excelchaos_view.components.SearchPanelToolbar;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ToolbarSalaryListView extends SearchPanelToolbar {
    private JButton editEntry;
    private JButton increaseSalary;

    private JButton salaryStageOn;

    private JButton exportToCSV;

    private JButton removeAdditionalSalaryStage;

    private JToggleButton showNextPayGrade;



    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new WrapLayout(FlowLayout.LEFT));
        editEntry = new JButton("Eintrag bearbeiten");
        increaseSalary = new JButton("Gehaltserhöhung");
        salaryStageOn = new JButton("Gehaltsprojektion");
        removeAdditionalSalaryStage = new JButton("Gehaltsprojektion ausblenden");
        showNextPayGrade = new JToggleButton("Gehaltsstufenerhöhungen anzeigen");
        exportToCSV = new JButton("Daten als CSV exportieren");

        add(editEntry);
        editEntry.setEnabled(false);
        removeAdditionalSalaryStage.setEnabled(false);

        add(increaseSalary);
        add(showNextPayGrade);
        add(salaryStageOn);
        add(removeAdditionalSalaryStage);
        setUpSearchPanel();
        add(exportToCSV);

    }

    public void setActionListener(ActionListener l){
        editEntry.addActionListener(l);
        increaseSalary.addActionListener(l);
        salaryStageOn.addActionListener(l);
        removeAdditionalSalaryStage.addActionListener(l);
        exportToCSV.addActionListener(l);
    }
    public void setItemListener(ItemListener l){
        showNextPayGrade.addItemListener(l);
    }

    public JToggleButton getShowNextPayGrade() {
        return showNextPayGrade;
    }

    public JButton getSalaryStageOn() {
        return salaryStageOn;
    }

    public JButton getRemoveAdditionalSalaryStage() {
        return removeAdditionalSalaryStage;
    }

    public JButton getEditEntry() {
        return editEntry;
    }

    public JButton getExportToCSV() {
        return exportToCSV;
    }
    public JButton getIncreaseSalary(){ return increaseSalary;};
}
