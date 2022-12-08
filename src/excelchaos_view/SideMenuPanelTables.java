package excelchaos_view;

import javax.swing.*;
import java.awt.*;

public class SideMenuPanelTables extends JPanel {
    private JButton personenliste;
    private JButton gehaltsliste;
    private JButton inventarliste;

    public void init() {
        personenliste = new JButton("Personenliste");
        gehaltsliste = new JButton("Gehaltsliste");
        inventarliste = new JButton("Inventarliste");

        setBackground(Color.white);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        add(personenliste);
        add(gehaltsliste);
        add(inventarliste);
        setPreferredSize(new Dimension(130,100));
    }

}
