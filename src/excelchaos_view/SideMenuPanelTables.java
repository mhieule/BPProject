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

    private JPanel topPanel, centerpanel;
    private JLabel navi;
    private BasicArrowButton arrowButtonWest;

    public void init() {
        personenliste = new JButton("Personenliste");
        gehaltsliste = new JButton("Gehaltsliste");
        inventarliste = new JButton("Inventarliste");
        gehaltshistorie = new JButton("Gehaltshistorie");
        payRates = new JToggleButton("Entgelttabellen");
        showE13Tables = new JButton("E13 Tabellen");
        showE14Tables = new JButton("E14 Tabellen");
        showSHKTables = new JButton("SHK Tabellen");
        personenliste.setPreferredSize(new Dimension(120, 25));
        inventarliste.setPreferredSize(new Dimension(120, 25));
        gehaltsliste.setPreferredSize(new Dimension(120, 25));
        gehaltshistorie.setPreferredSize(new Dimension(120, 25));
        payRates.setPreferredSize(new Dimension(120, 25));
        showE13Tables.setPreferredSize(new Dimension(120, 25));
        showE14Tables.setPreferredSize(new Dimension(120, 25));
        showSHKTables.setPreferredSize(new Dimension(120, 25));
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
        centerpanel.setPreferredSize(new Dimension(130, 155));
        centerpanel.add(personenliste);
        centerpanel.add(gehaltsliste);
        centerpanel.add(gehaltshistorie);
        centerpanel.add(inventarliste);
        centerpanel.add(payRates);
        centerpanel.add(showE13Tables);
        centerpanel.add(showE14Tables);
        centerpanel.add(showSHKTables);
        add(centerpanel, BorderLayout.CENTER);
        setBackground(Color.white);

        setPreferredSize(new Dimension(130, 100));

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
