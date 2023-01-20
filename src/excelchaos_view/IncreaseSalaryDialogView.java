package excelchaos_view;

import excelchaos_model.IncreaseSalaryType;

import static excelchaos_model.IncreaseSalaryType.*;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;

public class IncreaseSalaryDialogView extends JDialog {
    private JButton okayButton;
    private JButton closeButton;
    private JButton projectButton;
    private JPanel buttonPanel;
    private JPanel buttonWestPanel;
    private JPanel buttonEastPanel;
    private JPanel contentPanel, contentNorthPanel, contentSouthPanel;
    private CustomTable table;

    private JRadioButton absolute;
    private JRadioButton relative;
    private JRadioButton mixed;
    private ButtonGroup group;

    private JTextField textFieldAbsolute;
    private JTextField textFieldRelative;
    private Box boxPanel;
    private JTextField mixedAbsolute, mixedRelative;
    private JRadioButton mixedMin, mixedMax;
    private ButtonGroup mixedGroup;


    public void init(CustomTable table){

        this.table=table;

        //set some basic properties of the increase salary dialog view
        setLayout(new BorderLayout());
        setTitle("Gehaltserhöhung");

        //initialize the buttons
        projectButton = new JButton("Projizieren");
        okayButton = new JButton("OK");
        closeButton = new JButton("Abbrechen");

        //init the instruction label
        //instructionLabel=new JLabel("Zur Gehaltserhöhung bitte die folgende Personalen bestätigen oder abbrechen");



        //initialize components for the south content panel (for the radiobuttons)
        absolute=new JRadioButton("Option 1: Absolut in Euro");
        relative=new JRadioButton("Option 2: Relativ in %");
        mixed=new JRadioButton("Option 3: Gemischt");
        group = new ButtonGroup(); group.add(absolute); group.add(relative); group.add(mixed);
        textFieldAbsolute = new JTextField(); textFieldAbsolute.setVisible(false);
        textFieldRelative = new JTextField(); textFieldRelative.setVisible(false);
        mixedAbsolute = new JTextField();
        mixedRelative = new JTextField();
        mixedMin = new JRadioButton("Minimum aus den beiden");
        mixedMax = new JRadioButton("Maximum aus den beiden");
        mixedGroup = new ButtonGroup();
        mixedGroup.add(mixedMin); mixedGroup.add(mixedMax);

        boxPanel = Box.createVerticalBox();
        boxPanel.add(new JLabel("Absolut in Euro:"));
        boxPanel.add(mixedAbsolute);
        boxPanel.add(new JLabel("oder relativ in %"));
        boxPanel.add(mixedRelative);
        boxPanel.add(mixedMin); boxPanel.add(mixedMax);
        boxPanel.setVisible(false);


        //initialize, set some properties and components for the panels
        contentPanel = new JPanel(new BorderLayout());
        contentNorthPanel = new JPanel();
        contentNorthPanel.setLayout(new BoxLayout(contentNorthPanel, 1));
        contentNorthPanel.add(new JScrollPane(table));
        contentSouthPanel = new JPanel();
        contentSouthPanel.setLayout(new BoxLayout(contentSouthPanel, 1));
        contentSouthPanel.add(absolute);
        contentSouthPanel.add(textFieldAbsolute);
        contentSouthPanel.add(relative);
        contentSouthPanel.add(textFieldRelative);
        contentSouthPanel.add(mixed);
        contentSouthPanel.add(boxPanel);

        contentPanel.add(contentNorthPanel, BorderLayout.CENTER);
        contentPanel.add(contentSouthPanel, BorderLayout.SOUTH);

        buttonPanel = new JPanel(new BorderLayout());
        buttonWestPanel = new JPanel(new FlowLayout());
        buttonEastPanel = new JPanel(new FlowLayout());
        buttonWestPanel.add(projectButton);
        buttonWestPanel.add(okayButton);
        buttonEastPanel.add(closeButton);
        buttonPanel.add(buttonWestPanel, BorderLayout.WEST);
        buttonPanel.add(buttonEastPanel, BorderLayout.EAST);

        //add the panel to the dialog
        //add(instructionLabel, BorderLayout.NORTH);
        add(new JSeparator(),BorderLayout.CENTER);
        add(contentPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        //set properties for the dialog
        setLocationRelativeTo(getParent());
        setAlwaysOnTop(true);
        setResizable(true);
        setVisible(true);
        setSize(500,500);

        addProjectionColumn();
    }

    public void addProjectionColumn(){
        this.table.addColumn(new TableColumn());
        TableColumn projectedColumn = this.table.getColumnModel().getColumn(table.getColumnCount()-1);
        projectedColumn.setHeaderValue("Erhöhte Gehalt (init)");
    }

    public void setActionListener(ActionListener l){
        okayButton.addActionListener(l);
        closeButton.addActionListener(l);
        projectButton.addActionListener(l);
        absolute.addActionListener(l);
        relative.addActionListener(l);
        mixed.addActionListener(l);
    }

    public void setProjectionColumnInvisible(){
        if(getCurrentTable().getColumnCount()==8) {
            getCurrentTable().getColumnModel().getColumn(7).setMinWidth(0);
            getCurrentTable().getColumnModel().getColumn(7).setMaxWidth(0);
            getCurrentTable().getColumnModel().getColumn(7).setPreferredWidth(0);
            getCurrentTable().doLayout();
        }
    }

    public void setProjectionColumnVisible(){
        if(getCurrentTable().getColumnCount()==8) {
            getCurrentTable().getColumnModel().getColumn(7).setMinWidth(100);
            getCurrentTable().getColumnModel().getColumn(7).setMaxWidth(100000);
            getCurrentTable().getColumnModel().getColumn(7).setPreferredWidth(100);
            getCurrentTable().doLayout();
        }
    }

    public void setProjectionView(IncreaseSalaryType type){
        setProjectionColumnVisible();
        for(int i=0; i<table.getRowCount();i++) {
            double currentSalary = Double.parseDouble(String.valueOf(table.getValueAt(i, 5)));
            double finalSalary = 0;
            if (type == ABSOLUTE) {
                finalSalary = currentSalary+Double.parseDouble(textFieldAbsolute.getText());
            } else if (type == RELATIVE) {
                finalSalary = currentSalary+Double.parseDouble(textFieldRelative.getText())*currentSalary/100;
            } else {
                double finalAbsoluteSalary = currentSalary + Double.parseDouble(mixedAbsolute.getText());
                double finalRelativeSalary = currentSalary + Double.parseDouble(mixedRelative.getText()) * currentSalary / 100;
                if (type == MIXED_MIN) {
                    finalSalary = Math.min(finalAbsoluteSalary, finalRelativeSalary);
                } else if (type == MIXED_MAX) {
                    finalSalary = Math.max(finalAbsoluteSalary, finalRelativeSalary);
                }
            }
            table.setValueAt(finalSalary, i, 7);
        }
        table.doLayout();
    }

    public void setAbsoluteView(){
        textFieldAbsolute.setVisible(true);
        textFieldRelative.setVisible(false);
        boxPanel.setVisible(false);
        revalidate();
        setProjectionColumnInvisible();
    }

    public void setRelativeView(){
        textFieldAbsolute.setVisible(false);
        textFieldRelative.setVisible(true);
        boxPanel.setVisible(false);
        revalidate();
        setProjectionColumnInvisible();
    }

    public void setMixedView(){
        textFieldAbsolute.setVisible(false);
        textFieldRelative.setVisible(false);
        boxPanel.setVisible(true);
        revalidate();
        setProjectionColumnInvisible();
    }

    public JButton getCloseButton() {
        return closeButton;
    }
    public JButton getOkayButton() {
        return okayButton;
    }
    public JButton getProjectButton(){return projectButton;}
    public CustomTable getCurrentTable(){return table;}
    public JRadioButton getAbsolute(){return absolute;}
    public JTextField getTextFieldAbsolute() {return textFieldAbsolute;}
    public JTextField getTextFieldRelative() {return textFieldRelative;}
    public Box getBoxPanel() {return boxPanel;}
    public JRadioButton getRelative(){return relative;}
    public JRadioButton getMixed(){return mixed;}

    public JRadioButton getMixedMin() {return mixedMin;}

    public JRadioButton getMixedMax() {return mixedMax;}
}
