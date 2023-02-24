package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ToolbarSalaryListView extends JToolBar {
    private JButton editEntry;
    private JButton increaseSalary;

    private JButton salaryStageOn;

    private JButton removeAdditionalSalaryStage;

    private JToggleButton showNextPayGrade;

    private JLabel searchLabel;
    private JTextField searchField;

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        editEntry = new JButton("Eintrag bearbeiten");
        increaseSalary = new JButton("Gehaltserhöhung");
        salaryStageOn = new JButton("Gehaltsprojektion");
        removeAdditionalSalaryStage = new JButton("Gehaltsstufenprojektion ausblenden");
        showNextPayGrade = new JToggleButton("Gehaltsstufenerhöhungen anzeigen");
        searchLabel = new JLabel("Suchen:");
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(130,30));
        add(editEntry);
        editEntry.setEnabled(false);

        add(increaseSalary);
        add(showNextPayGrade);
        add(salaryStageOn);
        add(removeAdditionalSalaryStage);
        add(searchLabel);
        add(searchField);

    }

    public void setActionListener(ActionListener l){
        editEntry.addActionListener(l);
        increaseSalary.addActionListener(l);
        salaryStageOn.addActionListener(l);
        removeAdditionalSalaryStage.addActionListener(l);
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

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getIncreaseSalary(){ return increaseSalary;};
}
