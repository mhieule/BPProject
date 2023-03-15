package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.constants.IncreaseSalaryOption;
import excelchaos_view.components.CustomTable;

import static excelchaos_model.constants.IncreaseSalaryOption.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class IncreaseSalaryDialogView extends JDialog {

    private EmployeeDataManager employeeDataManager= new EmployeeDataManager();
    private ContractDataManager contractDataManager= new ContractDataManager();
    private JButton okayButton;
    private JButton closeButton;


   // private JButton projectButton;
    private JPanel buttonPanel;
    private JPanel buttonWestPanel;
    private JPanel buttonEastPanel;
    private JPanel contentPanel, contentNorthPanel;
    private CustomTable table;

    private JRadioButton absoluteRadioButton;
    private JRadioButton relativeRadioButton;
    private JRadioButton mixedRadioButton;
    private ButtonGroup optionRadioButtonGroup, increaseOptionRadioButtonGroup;

    private JTextField textFieldAbsolute;
    private JTextField textFieldRelative;
    private JTextField textFieldComment;
    private Box payMixedOptionPanel;
    private JTextField mixedAbsolute, mixedRelative;
    private JRadioButton mixedMinRadioButton, mixedMaxRadioButton;
    private ButtonGroup mixedOptionRadioButtonGroup;
    private JLabel currentSalary, currentBonus, dateQuery, commentQuery, increaseTypeQuery,increaseOptionQuery;
    private JRadioButton salaryIncreaseRadioButton, bonusRadioButton;

    private DatePicker startDate;

    public void init(String name){

        this.table= new CustomTable(null);

        //set some basic properties of the increase salary dialog view
        setLayout(new BorderLayout());
        setTitle("Gehaltserhöhung "+name);

        //initialize the buttons
       // projectButton = new JButton("Projizieren");
        okayButton = new JButton("OK");
        closeButton = new JButton("Abbrechen");

        //evtl eine eigene Modelle Klasse/Methode

        currentSalary = new JLabel("");
        currentBonus = new JLabel("");
        dateQuery = new JLabel("3. Wählen Sie eine Startdatum: ");
        commentQuery = new JLabel("4. Kommentar");
        increaseTypeQuery = new JLabel("1. Erhöhungstyp?");
        increaseOptionQuery = new JLabel("2. Erhöhungsoption auswählen");
        currentSalary.setText("Aktuelle Gehaltkosten: "+contractDataManager.getContract(employeeDataManager.getEmployeeByName(name).getId()).getRegular_cost()+" €");
        currentBonus.setText("Aktuelle Sonderzahlung: "+contractDataManager.getContract(employeeDataManager.getEmployeeByName(name).getId()).getBonus_cost()+" €");


        salaryIncreaseRadioButton = new JRadioButton("Normale Gehaltserhöhung");
        bonusRadioButton = new JRadioButton("Sonderzahlung");
        increaseOptionRadioButtonGroup = new ButtonGroup();  increaseOptionRadioButtonGroup.add(salaryIncreaseRadioButton); increaseOptionRadioButtonGroup.add(bonusRadioButton);
        textFieldComment = new JTextField();
        startDate = new DatePicker();
        //startDate.setPreferredSize(new Dimension(100,20));

        //initialize components for the south content panel (for the radiobuttons)
        absoluteRadioButton =new JRadioButton("Option 1: Absolut in €");
        relativeRadioButton =new JRadioButton("Option 2: Relativ in %");
        mixedRadioButton =new JRadioButton("Option 3: Gemischt");
        optionRadioButtonGroup = new ButtonGroup(); optionRadioButtonGroup.add(absoluteRadioButton); optionRadioButtonGroup.add(relativeRadioButton); optionRadioButtonGroup.add(mixedRadioButton);
        textFieldAbsolute = new JTextField(); textFieldAbsolute.setVisible(false);
        textFieldRelative = new JTextField(); textFieldRelative.setVisible(false);
        mixedAbsolute = new JTextField();
        mixedRelative = new JTextField();
        mixedMinRadioButton = new JRadioButton("Minimum aus den beiden");
        mixedMaxRadioButton = new JRadioButton("Maximum aus den beiden");
        mixedOptionRadioButtonGroup = new ButtonGroup();
        mixedOptionRadioButtonGroup.add(mixedMinRadioButton); mixedOptionRadioButtonGroup.add(mixedMaxRadioButton);

        payMixedOptionPanel = Box.createVerticalBox();
        payMixedOptionPanel.add(new JLabel("Absolut in €:"));
        payMixedOptionPanel.add(mixedAbsolute);
        payMixedOptionPanel.add(new JLabel("oder relativ in %"));
        payMixedOptionPanel.add(mixedRelative);
        payMixedOptionPanel.add(mixedMinRadioButton); payMixedOptionPanel.add(mixedMaxRadioButton);
        payMixedOptionPanel.setVisible(false);
        payMixedOptionPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));


        //initialize, set some properties and components for the panels
        contentPanel = new JPanel(new BorderLayout());
        Border margin = BorderFactory.createEmptyBorder(10, 30, 10, 30);
        contentPanel.setBorder(margin);
        contentNorthPanel = new JPanel();
        contentNorthPanel.setLayout(new BoxLayout(contentNorthPanel, 1));
        contentNorthPanel.add(currentSalary);
        contentNorthPanel.add(currentBonus);
        contentNorthPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentNorthPanel.add(increaseTypeQuery);
        contentNorthPanel.add(salaryIncreaseRadioButton);
        contentNorthPanel.add(bonusRadioButton);
        contentNorthPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel datePanel=new JPanel();
        datePanel.add(startDate);

        contentNorthPanel.add(increaseOptionQuery);
        contentNorthPanel.add(absoluteRadioButton);
        contentNorthPanel.add(textFieldAbsolute);
        contentNorthPanel.add(relativeRadioButton);
        contentNorthPanel.add(textFieldRelative);
        contentNorthPanel.add(mixedRadioButton);
        contentNorthPanel.add(payMixedOptionPanel);
        contentNorthPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentNorthPanel.add(dateQuery);
        contentNorthPanel.add(datePanel);
        contentNorthPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentNorthPanel.add(commentQuery);
        contentNorthPanel.add(textFieldComment);


        contentPanel.add(contentNorthPanel, BorderLayout.CENTER);
        //contentPanel.add(contentNorthPanel, BorderLayout.SOUTH);

        buttonPanel = new JPanel(new BorderLayout());
        buttonWestPanel = new JPanel(new FlowLayout());
        buttonEastPanel = new JPanel(new FlowLayout());
       // buttonWestPanel.add(projectButton);
        buttonWestPanel.add(okayButton);
        buttonEastPanel.add(closeButton);
        buttonPanel.add(buttonWestPanel, BorderLayout.WEST);
        buttonPanel.add(buttonEastPanel, BorderLayout.EAST);

        //add the panel to the dialog
        //add(instructionLabel, BorderLayout.NORTH);
        //add(new JSeparator(),BorderLayout.CENTER);
        add(contentPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        //set properties for the dialog
        setLocation(100,300);
        //setLocationRelativeTo(getParent());
        setAlwaysOnTop(true);
        setResizable(true);
        setVisible(true);
        setSize(800,650);

        addProjectionColumn();
    }

    public void addProjectionColumn(){
        //this.table.addColumn(new TableColumn());
        //TableColumn projectedColumn = this.table.getColumnModel().getColumn(table.getColumnCount()-1);
        //projectedColumn.setHeaderValue("Erhöhte Gehalt");
    }

    public void setActionListener(ActionListener l){
        okayButton.addActionListener(l);
        closeButton.addActionListener(l);
       // projectButton.addActionListener(l);
        absoluteRadioButton.addActionListener(l);
        relativeRadioButton.addActionListener(l);
        mixedRadioButton.addActionListener(l);
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

    public void setProjectionView(IncreaseSalaryOption type){
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
        payMixedOptionPanel.setVisible(false);
        revalidate();
        //setProjectionColumnInvisible();
    }

    public void setRelativeView(){
        textFieldAbsolute.setVisible(false);
        textFieldRelative.setVisible(true);
        payMixedOptionPanel.setVisible(false);
        revalidate();
        //setProjectionColumnInvisible();
    }

    public void setMixedView(){
        textFieldAbsolute.setVisible(false);
        textFieldRelative.setVisible(false);
        payMixedOptionPanel.setVisible(true);
        revalidate();
        //setProjectionColumnInvisible();
    }

    public void noIncreaseTypeSelected(){
        JDialog dialog = new JDialog();
        dialog.add(new JLabel("Keine Erhöhungstyp ausgewählt !"));
        dialog.setSize(new Dimension(250,100));
        dialog.setVisible(true);
    }

    public void noIncreaseOptionSelected() {
        JDialog dialog = new JDialog();
        dialog.add(new JLabel("Keine Erhöhungsoption ausgewählt !"));
        dialog.setSize(new Dimension(300,100));
        dialog.setVisible(true);
    }

    public void noMinMaxSelected() {
        JDialog dialog = new JDialog();
        dialog.add(new JLabel("Sie haben gemischte Option ausgewählt, bitte Minimum oder Maximum auswählen"));
        dialog.setSize(new Dimension(500,100));
        dialog.setVisible(true);
    }

    public JButton getCloseButton() {
        return closeButton;
    }
    public JButton getOkayButton() {
        return okayButton;
    }
    /*public JButton getProjectButton(){return projectButton;}*/
    public CustomTable getCurrentTable(){return table;}
    public JRadioButton getAbsoluteRadioButton(){return absoluteRadioButton;}
    public JTextField getTextFieldAbsolute() {return textFieldAbsolute;}
    public JTextField getTextFieldRelative() {return textFieldRelative;}
    public Box getPayMixedOptionPanel() {return payMixedOptionPanel;}
    public JRadioButton getRelativeRadioButton(){return relativeRadioButton;}
    public JRadioButton getMixedRadioButton(){return mixedRadioButton;}

    public JRadioButton getMixedMinRadioButton() {return mixedMinRadioButton;}

    public JRadioButton getMixedMaxRadioButton() {return mixedMaxRadioButton;}

    public JRadioButton getSalaryIncreaseRadioButton(){return salaryIncreaseRadioButton;}

    public JRadioButton getBonusRadioButton() {return bonusRadioButton;}
    public DatePicker getStartDate(){return startDate;}

    public JTextField getMixedAbsolute() {return mixedAbsolute;}

    public JTextField getMixedRelative() {return mixedRelative;}

    public JTextField getTextFieldComment() {return textFieldComment;}
}
