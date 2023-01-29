package excelchaos_view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class SideMenuPanelTables extends JPanel {
    private JButton personenliste;
    private JButton gehaltsliste;
    private JButton inventarliste;
    private JButton gehaltshistorie;

    private JButton salaryIncrease;

    private JButton manualSalary;

    private JButton showE13Tables;
    private JButton showE14Tables;
    private JButton showSHKTables;

    private JToggleButton payRatesToogleButton;

    private JToggleButton salaryToggleButton;

    private JPanel topPanel, centerpanel, payRatePanel, salaryPanel, payRateToggleButtonPanel, payRateTableButtonPanel, salaryToggleButtonPanel,salaryTableButtonPanel;
    private JLabel navi, payTextLabel, payRateOpenArrowLabel, payRateCloseArrowLabel,salaryOpenArrowLabel, salaryCloseArrowLabel, salaryTextLabel;
    private BasicArrowButton arrowButtonWest;

    private ImageIcon salaryCloseArrow = new ImageIcon("resources/images/arrow_right_mini.png");
    private ImageIcon salaryOpenArrow = new ImageIcon("resources/images/arrow_down_mini.png");

    private Border raisedetchedBorder;

    private final int RegularButtonWidth = 140;

    private final int RegularButtonHeigt = 25;

    public void init() {



        personenliste = new JButton("Personenliste");

        inventarliste = new JButton("Inventarliste");



        personenliste.setPreferredSize(new Dimension(RegularButtonWidth, RegularButtonHeigt));
        inventarliste.setPreferredSize(new Dimension(RegularButtonWidth, RegularButtonHeigt));






        arrowButtonWest = new BasicArrowButton(BasicArrowButton.WEST);
        topPanel = new JPanel();
        centerpanel = new JPanel();
        navi = new JLabel("Navigationsleiste");

        topPanel.setPreferredSize(new Dimension(150, 30));
        topPanel.add(navi);
        topPanel.add(arrowButtonWest);
        add(topPanel, BorderLayout.PAGE_START);
        centerpanel.setPreferredSize(new Dimension(150, 500));
        centerpanel.add(personenliste);

        //centerpanel.add(payRates);
        initSalaryExpander();
        centerpanel.add(salaryPanel);
        centerpanel.add(inventarliste);
        initPayRateExpander();
        centerpanel.add(payRatePanel);
        //centerpanel.add(showE13Tables);
        //centerpanel.add(showE14Tables);
        //centerpanel.add(showSHKTables);
        //centerpanel.add(collapsablePanel);
        add(centerpanel, BorderLayout.CENTER);
        setBackground(Color.white);

        setPreferredSize(new Dimension(150, 100));

    }



    public void payRateCloseArrowLabelVisible(){
        payRateOpenArrowLabel.setVisible(false);
        payRateCloseArrowLabel.setVisible(true);
    }

    public void payRateOpenArrowLabelVisible(){
        payRateCloseArrowLabel.setVisible(false);
        payRateOpenArrowLabel.setVisible(true);
    }

    public void salaryCloseArrowLabelVisible(){
        salaryOpenArrowLabel.setVisible(false);
        salaryCloseArrowLabel.setVisible(true);
    }

    public void salaryOpenArrowLabelVisible(){
        salaryCloseArrowLabel.setVisible(false);
        salaryOpenArrowLabel.setVisible(true);
    }

    private void initSalaryExpander(){
        salaryPanel = new JPanel();
        salaryToggleButtonPanel = new JPanel();
        salaryTableButtonPanel = new JPanel();
        raisedetchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        salaryToggleButtonPanel.setPreferredSize(new Dimension(150,45));
        salaryTableButtonPanel.setPreferredSize(new Dimension(150,60));
        salaryToggleButtonPanel.setBorder(raisedetchedBorder);
        salaryPanel.setLayout(new BorderLayout());
        salaryPanel.setPreferredSize(new Dimension(150,45));
        salaryCloseArrowLabel = new JLabel(salaryCloseArrow);
        salaryOpenArrowLabel = new JLabel(salaryOpenArrow);
        salaryTextLabel = new JLabel("Gehälter");
        salaryToggleButton = new JToggleButton();
        salaryToggleButton.setPreferredSize(new Dimension(RegularButtonWidth, 30));
        salaryToggleButton.setLayout(new BoxLayout(salaryToggleButton,BoxLayout.X_AXIS));
        salaryToggleButton.add(Box.createHorizontalStrut(10));
        salaryToggleButton.add(salaryTextLabel);
        salaryToggleButton.add(Box.createHorizontalStrut(25));
        salaryToggleButton.add(salaryCloseArrowLabel);
        salaryCloseArrowLabelVisible();
        salaryToggleButton.add(salaryOpenArrowLabel);
        salaryToggleButtonPanel.add(salaryToggleButton);
        salaryPanel.add(salaryToggleButtonPanel,BorderLayout.NORTH);


        gehaltsliste = new JButton("Gehaltsliste");
        manualSalary = new JButton("Manuelle Einträge");
        salaryIncrease = new JButton("Gehaltserhöhung");
        gehaltshistorie = new JButton("Gehaltshistorie");



        gehaltsliste.setPreferredSize(new Dimension(140, RegularButtonHeigt));
        manualSalary.setPreferredSize(new Dimension(140, RegularButtonHeigt));
        salaryIncrease.setPreferredSize(new Dimension(140, RegularButtonHeigt));
        gehaltshistorie.setPreferredSize(new Dimension(140, RegularButtonHeigt));

        salaryTableButtonPanel.add(gehaltsliste);
        salaryTableButtonPanel.add(manualSalary);
        salaryTableButtonPanel.add(salaryIncrease);
        salaryTableButtonPanel.add(gehaltshistorie);

        salaryTableButtonPanel.setVisible(false);
        salaryPanel.add(salaryTableButtonPanel,BorderLayout.CENTER);


    }

    private void initPayRateExpander(){
        payRatePanel = new JPanel();
        payRateToggleButtonPanel = new JPanel();
        payRateTableButtonPanel = new JPanel();
        raisedetchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        payRateToggleButtonPanel.setPreferredSize(new Dimension(150,45));
        payRateTableButtonPanel.setPreferredSize(new Dimension(150,80));
        payRateToggleButtonPanel.setBorder(raisedetchedBorder);
        //payRatePanel.setLayout(new BoxLayout(payRatePanel,BoxLayout.Y_AXIS));
        payRatePanel.setLayout(new BorderLayout());
        payRatePanel.setPreferredSize(new Dimension(150,120));

        payRateCloseArrowLabel = new JLabel(salaryCloseArrow);
        payRateOpenArrowLabel = new JLabel(salaryOpenArrow);
        payTextLabel = new JLabel("Entgelttabellen");
        payRatesToogleButton = new JToggleButton();
        payRatesToogleButton.setPreferredSize(new Dimension(RegularButtonWidth, 30));
        payRatesToogleButton.setLayout(new BoxLayout(payRatesToogleButton,BoxLayout.X_AXIS));
        payRatesToogleButton.add(payTextLabel);
        payRatesToogleButton.add(payRateCloseArrowLabel);
        payRateCloseArrowLabelVisible();
        payRatesToogleButton.add(payRateOpenArrowLabel);

        payRateToggleButtonPanel.add(payRatesToogleButton);

        payRatePanel.add(payRateToggleButtonPanel,BorderLayout.NORTH);


        showE13Tables = new JButton("E13 Tabellen");
        showE14Tables = new JButton("E14 Tabellen");
        showSHKTables = new JButton("SHK Tabellen");

        showE13Tables.setPreferredSize(new Dimension(120, 20));
        showE14Tables.setPreferredSize(new Dimension(120, 20));
        showSHKTables.setPreferredSize(new Dimension(120, 20));

        payRateTableButtonPanel.add(showE13Tables);
        payRateTableButtonPanel.add(showE14Tables);
        payRateTableButtonPanel.add(showSHKTables);
        payRateTableButtonPanel.setVisible(false);

        payRatePanel.add(payRateTableButtonPanel,BorderLayout.CENTER);


    }

    public JPanel getPayRateToggleButtonPanel() {
        return payRateToggleButtonPanel;
    }

    public Border getRaisedetchedBorder() {
        return raisedetchedBorder;
    }

    public JPanel getPayRatePanel() {
        return payRatePanel;
    }

    public JPanel getPayRateTableButtonPanel() {
        return payRateTableButtonPanel;
    }

    public BasicArrowButton getArrowButtonWest() {
        return arrowButtonWest;
    }

    public JButton getPersonenliste(){
        return personenliste;
    }

    public JButton getGehaltsliste() {
        return gehaltsliste;
    }

    public JButton getGehaltshistorie() {
        return gehaltshistorie;
    }

    public JToggleButton getPayRates() {
        return payRatesToogleButton;
    }

    public JButton getManualSalary() {
        return manualSalary;
    }

    public JButton getSalaryIncrease() {
        return salaryIncrease;
    }

    public JPanel getSalaryPanel() {
        return salaryPanel;
    }

    public JToggleButton getSalaryToggleButton() {
        return salaryToggleButton;
    }

    public JPanel getSalaryTableButtonPanel() {
        return salaryTableButtonPanel;
    }

    public JPanel getSalaryToggleButtonPanel() {
        return salaryToggleButtonPanel;
    }

    public JButton getShowE13Tables() {
        return showE13Tables;
    }

    public JButton getShowE14Tables() {
        return showE14Tables;
    }

    public JButton getShowSHKTables() {
        return showSHKTables;
    }

    public JPanel getCenterpanel() {
        return centerpanel;
    }

    public void setActionListener(ActionListener l) {
        personenliste.addActionListener(l);
        gehaltsliste.addActionListener(l);
        manualSalary.addActionListener(l);
        salaryIncrease.addActionListener(l);
        gehaltshistorie.addActionListener(l);
        arrowButtonWest.addActionListener(l);
        showE13Tables.addActionListener(l);
        showE14Tables.addActionListener(l);
        showSHKTables.addActionListener(l);
    }
    public void setItemListener(ItemListener l){
        payRatesToogleButton.addItemListener(l);
        salaryToggleButton.addItemListener(l);
    }

}
