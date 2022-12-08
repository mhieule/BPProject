package excelchaos_view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;

public class SideMenuPanelTables extends JPanel {
    private JButton personenliste;
    private JButton gehaltsliste;
    private JButton inventarliste;

    private JPanel topPanel, centerpanel;
    private JLabel navi;
    BasicArrowButton arrowButtonWest;

    public void init() {
        personenliste = new JButton("Personenliste");
        gehaltsliste = new JButton("Gehaltsliste");
        inventarliste = new JButton("Inventarliste");
        personenliste.setPreferredSize(new Dimension(115,25));
        inventarliste.setPreferredSize(new Dimension(115,25));
        gehaltsliste.setPreferredSize(new Dimension(115,25));

        arrowButtonWest = new BasicArrowButton(BasicArrowButton.WEST);
        topPanel = new JPanel();
        centerpanel = new JPanel();
        navi = new JLabel("Navigationsleiste");

        topPanel.setPreferredSize(new Dimension(130, 30));
        topPanel.add(navi);
        topPanel.add(arrowButtonWest);
        add(topPanel, BorderLayout.PAGE_START);
        centerpanel.setPreferredSize(new Dimension(130, 100));
        centerpanel.add(personenliste);
        centerpanel.add(gehaltsliste);
        centerpanel.add(inventarliste);
        add(centerpanel, BorderLayout.CENTER);
        setBackground(Color.white);


        //add(personenliste);
        //add(gehaltsliste);
        //add(inventarliste);
        setPreferredSize(new Dimension(130, 100));

    }

    public BasicArrowButton getArrowButtonWest() {
        return arrowButtonWest;
    }

    public void setActionListener(ActionListener l) {
        arrowButtonWest.addActionListener(l);
    }

}
