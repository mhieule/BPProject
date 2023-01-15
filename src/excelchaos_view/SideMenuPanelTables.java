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

    private JButton showE13Tables;
    private JButton showE14Tables;
    private JButton showSHKTables;

    private JToggleButton payRatesToogleButton;

    private JPanel topPanel, centerpanel, payRatePanel, toggleButtonPanel, tableButtonPanel;
    private JLabel navi, payTextLabel,openArrowLabel,closeArrowLabel;
    private BasicArrowButton arrowButtonWest;

    private ImageIcon closeArrow;
    private ImageIcon openArrow;

    private Border raisedetchedBorder;

    private final int RegularButtonWidth = 130;

    private final int RegularButtonHeigt = 25;

    public void init() {



        personenliste = new JButton("Personenliste");
        gehaltsliste = new JButton("Gehaltsliste");
        inventarliste = new JButton("Inventarliste");
        gehaltshistorie = new JButton("Gehaltshistorie");


        personenliste.setPreferredSize(new Dimension(RegularButtonWidth, RegularButtonHeigt));
        inventarliste.setPreferredSize(new Dimension(RegularButtonWidth, RegularButtonHeigt));
        gehaltsliste.setPreferredSize(new Dimension(RegularButtonWidth, RegularButtonHeigt));
        gehaltshistorie.setPreferredSize(new Dimension(RegularButtonWidth, RegularButtonHeigt));




        arrowButtonWest = new BasicArrowButton(BasicArrowButton.WEST);
        topPanel = new JPanel();
        centerpanel = new JPanel();
        navi = new JLabel("Navigationsleiste");

        topPanel.setPreferredSize(new Dimension(140, 30));
        topPanel.add(navi);
        topPanel.add(arrowButtonWest);
        add(topPanel, BorderLayout.PAGE_START);
        centerpanel.setPreferredSize(new Dimension(140, 170));
        centerpanel.add(personenliste);
        centerpanel.add(gehaltsliste);
        centerpanel.add(gehaltshistorie);
        centerpanel.add(inventarliste);
        //centerpanel.add(payRates);
        initPayRateExpander();
        centerpanel.add(payRatePanel);
        //centerpanel.add(showE13Tables);
        //centerpanel.add(showE14Tables);
        //centerpanel.add(showSHKTables);
        //centerpanel.add(collapsablePanel);
        add(centerpanel, BorderLayout.CENTER);
        setBackground(Color.white);

        setPreferredSize(new Dimension(140, 100));

    }



    public void closeArrowLabelVisible(){
        openArrowLabel.setVisible(false);
        closeArrowLabel.setVisible(true);
    }

    public void openArrowLabelVisible(){
        closeArrowLabel.setVisible(false);
        openArrowLabel.setVisible(true);
    }

    private void initPayRateExpander(){
        payRatePanel = new JPanel();
        toggleButtonPanel = new JPanel();
        tableButtonPanel = new JPanel();
        raisedetchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        toggleButtonPanel.setPreferredSize(new Dimension(140,45));
        tableButtonPanel.setPreferredSize(new Dimension(140,80));
        toggleButtonPanel.setBorder(raisedetchedBorder);
        //payRatePanel.setLayout(new BoxLayout(payRatePanel,BoxLayout.Y_AXIS));
        payRatePanel.setLayout(new BorderLayout());
        payRatePanel.setPreferredSize(new Dimension(140,120));

        closeArrow = new ImageIcon("resources/images/arrow_right_mini.png");
        openArrow = new ImageIcon("resources/images/arrow_down_mini.png");
        closeArrowLabel = new JLabel(closeArrow);
        openArrowLabel = new JLabel(openArrow);
        payTextLabel = new JLabel("Entgelttabellen");
        payRatesToogleButton = new JToggleButton();
        payRatesToogleButton.setPreferredSize(new Dimension(RegularButtonWidth, 30));
        payRatesToogleButton.setLayout(new BoxLayout(payRatesToogleButton,BoxLayout.X_AXIS));
        payRatesToogleButton.add(payTextLabel);
        payRatesToogleButton.add(closeArrowLabel);
        closeArrowLabelVisible();
        payRatesToogleButton.add(openArrowLabel);

        toggleButtonPanel.add(payRatesToogleButton);

        payRatePanel.add(toggleButtonPanel,BorderLayout.NORTH);


        showE13Tables = new JButton("E13 Tabellen");
        showE14Tables = new JButton("E14 Tabellen");
        showSHKTables = new JButton("SHK Tabellen");

        showE13Tables.setPreferredSize(new Dimension(110, 20));
        showE14Tables.setPreferredSize(new Dimension(110, 20));
        showSHKTables.setPreferredSize(new Dimension(110, 20));

        tableButtonPanel.add(showE13Tables);
        tableButtonPanel.add(showE14Tables);
        tableButtonPanel.add(showSHKTables);
        tableButtonPanel.setVisible(false);

        payRatePanel.add(tableButtonPanel,BorderLayout.CENTER);


    }

    public JPanel getToggleButtonPanel() {
        return toggleButtonPanel;
    }

    public Border getRaisedetchedBorder() {
        return raisedetchedBorder;
    }

    public JPanel getPayRatePanel() {
        return payRatePanel;
    }

    public JPanel getTableButtonPanel() {
        return tableButtonPanel;
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
        gehaltshistorie.addActionListener(l);
        arrowButtonWest.addActionListener(l);
        showE13Tables.addActionListener(l);
        showE14Tables.addActionListener(l);
        showSHKTables.addActionListener(l);
    }
    public void setItemListener(ItemListener l){
        payRatesToogleButton.addItemListener(l);
    }

}
