package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolbarShowPersonView extends JToolBar {
    private JButton insertPerson;
    private JButton editPerson;
    private JButton deletePerson;

    private JLabel searchLabel;
    private JTextField searchField;

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        insertPerson = new JButton("Eintrag hinzufügen");
        editPerson = new JButton("Eintrag bearbeiten");
        deletePerson = new JButton("Eintrag löschen");
        searchLabel = new JLabel("Suchen:");
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(130,30));

        add(insertPerson);
        addSeparator(new Dimension(20,30));
        add(editPerson);
        addSeparator(new Dimension(20,30));
        add(deletePerson);
        addSeparator(new Dimension(20,30));
        add(searchLabel);
        add(searchField);
    }

    public void setActionListener(ActionListener l) {
        insertPerson.addActionListener(l);
        deletePerson.addActionListener(l);
    }

    public JButton getInsertPerson() {
        return insertPerson;
    }

    public JTextField getSearchField() {
        return searchField;
    }
}
