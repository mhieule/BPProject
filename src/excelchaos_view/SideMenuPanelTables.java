package excelchaos_view;

import javax.swing.*;
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

    private JToggleButton payRates;

    private JPanel topPanel, centerpanel, payRatePanel;
    private JLabel navi, payTextLabel,openArrowLabel,closeArrowLabel;
    private BasicArrowButton arrowButtonWest;

    private ImageIcon closeArrow;
    private ImageIcon openArrow;

    public void init() {
        closeArrow = new ImageIcon("resources/images/arrow_right_mini.png");
        openArrow = new ImageIcon("resources/images/arrow_down_mini.png");
        closeArrowLabel = new JLabel(closeArrow);
        openArrowLabel = new JLabel(openArrow);
        payTextLabel = new JLabel("Entgelttabellen");


        personenliste = new JButton("Personenliste");
        gehaltsliste = new JButton("Gehaltsliste");
        inventarliste = new JButton("Inventarliste");
        gehaltshistorie = new JButton("Gehaltshistorie");

        payRates = new JToggleButton();
        payRates.setLayout(new BoxLayout(payRates,BoxLayout.X_AXIS));
        payRates.add(payTextLabel);
        payRates.add(closeArrowLabel);
        closeArrowLabelVisible();
        payRates.add(openArrowLabel);



        showE13Tables = new JButton("E13 Tabellen");
        showE14Tables = new JButton("E14 Tabellen");
        showSHKTables = new JButton("SHK Tabellen");
        personenliste.setPreferredSize(new Dimension(120, 25));
        inventarliste.setPreferredSize(new Dimension(120, 25));
        gehaltsliste.setPreferredSize(new Dimension(120, 25));
        gehaltshistorie.setPreferredSize(new Dimension(120, 25));
        payRates.setPreferredSize(new Dimension(120, 25));
        showE13Tables.setPreferredSize(new Dimension(110, 20));
        showE14Tables.setPreferredSize(new Dimension(110, 20));
        showSHKTables.setPreferredSize(new Dimension(110, 20));
        showE13Tables.setVisible(false);
        showE14Tables.setVisible(false);
        showSHKTables.setVisible(false);


        arrowButtonWest = new BasicArrowButton(BasicArrowButton.WEST);
        topPanel = new JPanel();
        centerpanel = new JPanel();
        navi = new JLabel("Navigationsleiste");

        topPanel.setPreferredSize(new Dimension(140, 30));
        topPanel.add(navi);
        topPanel.add(arrowButtonWest);
        add(topPanel, BorderLayout.PAGE_START);
        centerpanel.setPreferredSize(new Dimension(130, 400));
        centerpanel.add(personenliste);
        centerpanel.add(gehaltsliste);
        centerpanel.add(gehaltshistorie);
        centerpanel.add(inventarliste);
        centerpanel.add(payRates);
        initPayRatePanel();
        //CollapsablePanel collapsablePanel = new CollapsablePanel("Entgelttabellen",payRatePanel);
        centerpanel.add(showE13Tables);
        centerpanel.add(showE14Tables);
        centerpanel.add(showSHKTables);
        //centerpanel.add(collapsablePanel);
        add(centerpanel, BorderLayout.CENTER);
        setBackground(Color.white);

        setPreferredSize(new Dimension(130, 100));

    }



    public void closeArrowLabelVisible(){
        openArrowLabel.setVisible(false);
        closeArrowLabel.setVisible(true);
    }

    public void openArrowLabelVisible(){
        closeArrowLabel.setVisible(false);
        openArrowLabel.setVisible(true);
    }

    private void initPayRatePanel(){
        payRatePanel = new JPanel();
        payRatePanel.setLayout(new BoxLayout(payRatePanel,BoxLayout.Y_AXIS));
        payRatePanel.setPreferredSize(new Dimension(130,100));
        payRatePanel.add(showE13Tables);
        payRatePanel.add(showE14Tables);
        payRatePanel.add(showSHKTables);
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
        return payRates;
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
        payRates.addItemListener(l);
    }

}
