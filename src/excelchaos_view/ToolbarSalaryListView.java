package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ToolbarSalaryListView extends JToolBar {
    private JButton insertEntry;
    private JButton deleteEntry;
    private JButton increaseSalary;

    private JButton salaryStageOn;

    private JButton removeAdditionalSalaryStage;
    private JButton changeSalaryGroup;

    private JToggleButton showNextPayGrade;

    private JLabel searchLabel;
    private JTextField searchField;

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        insertEntry = new JButton("Gehaltseintrag hinzufügen");
        deleteEntry = new JButton(("Gehaltseintrag löschen"));
        increaseSalary = new JButton("Gehaltserhöhung");
        salaryStageOn = new JButton("Gehaltsstufenprojektion");
        removeAdditionalSalaryStage = new JButton("Gehaltsstufenprojektion ausblenden");
        changeSalaryGroup = new JButton("Gehaltsgruppe ändern");
        showNextPayGrade = new JToggleButton("Gehaltsstufenerhöhungen anzeigen");
        searchLabel = new JLabel("Suchen:");
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(130,30));
        addSeparator(new Dimension(30,30));
        add(insertEntry);
        addSeparator(new Dimension(20,30));
        add(deleteEntry);
        addSeparator(new Dimension(20,30));
        add(changeSalaryGroup);
        addSeparator(new Dimension(20,30));
        add(increaseSalary);
        addSeparator(new Dimension(20,30));
        add(showNextPayGrade);
        addSeparator(new Dimension(20,30));
        add(salaryStageOn);
        addSeparator(new Dimension(20,30));
        add(removeAdditionalSalaryStage);
        removeAdditionalSalaryStage.setVisible(false);
        addSeparator(new Dimension(20,30));
        add(searchLabel);
        add(searchField);

    }

    public void setActionListener(ActionListener l){
        insertEntry.addActionListener(l);
        deleteEntry.addActionListener(l);
        increaseSalary.addActionListener(l);
        changeSalaryGroup.addActionListener(l);
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

    public JButton getInsertEntry() {
        return insertEntry;
    }

    public JTextField getSearchField() {
        return searchField;
    }
}
