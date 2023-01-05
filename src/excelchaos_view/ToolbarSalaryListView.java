package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class ToolbarSalaryListView extends JToolBar {
    private JButton insertEntry;
    private JButton deleteEntry;
    private JButton updateView;
    private JButton increaseSalary;
    //private JButton getFutureSalaryStage;
    private JButton changeSalaryGroup;

    private JToggleButton showNextPayGrade;

    private JTextField searchField;

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        insertEntry = new JButton("Gehaltseintrag hinzufügen");
        deleteEntry = new JButton(("Gehaltseintrag löschen"));
        updateView = new JButton("Aktualisieren");
        increaseSalary = new JButton("Gehaltserhöhung");
        //getFutureSalaryStage = new JButton("Zukünftige Gehaltsstufe");
        changeSalaryGroup = new JButton("Gehaltsgruppe ändern");
        showNextPayGrade = new JToggleButton("Gehaltsstufenerhöhungen anzeigen");
        searchField = new JTextField("Suchen");
        addSeparator(new Dimension(130,30));
        add(updateView);
        addSeparator(new Dimension(20,30));
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
        add(searchField);
        addSeparator(new Dimension(20,30));

    }

    public void setActionListener(ActionListener l){
        insertEntry.addActionListener(l);
        deleteEntry.addActionListener(l);
        updateView.addActionListener(l);
        increaseSalary.addActionListener(l);
        changeSalaryGroup.addActionListener(l);
    }
    public void setItemListener(ItemListener l){
        showNextPayGrade.addItemListener(l);
    }

    public JToggleButton getShowNextPayGrade() {
        return showNextPayGrade;
    }
}
