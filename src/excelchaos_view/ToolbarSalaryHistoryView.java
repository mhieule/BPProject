package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolbarSalaryHistoryView extends JToolBar {
    private JButton bliBlaBlub;
    private JButton updateView;


    private JTextField searchField;

    public void init(){
        setFloatable(false);
        setBackground(Color.WHITE);
        bliBlaBlub = new JButton("bli bla blub");
        updateView = new JButton("Aktualisieren");
        searchField = new JTextField("Suchen");
        addSeparator(new Dimension(130,30));
        add(updateView);
        addSeparator(new Dimension(20,30));
        add(bliBlaBlub);
        addSeparator(new Dimension(20,30));
        add(searchField);
    }

    public void setActionListener(ActionListener l){
        bliBlaBlub.addActionListener(l);
        updateView.addActionListener(l);
        searchField.addActionListener(l);
    }

}
