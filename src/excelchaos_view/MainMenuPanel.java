package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    private JLabel title;
    private JButton personenliste;
    private JButton gehaltsliste;
    private JButton inventarliste;
    private JButton gehaltshistorie;

    public void init() {
        personenliste = new JButton();
        gehaltsliste = new JButton();
        inventarliste = new JButton();
        gehaltshistorie = new JButton();

        title = new JLabel();

        personenliste.setText("Personenstammdaten");
        gehaltsliste.setText("Gehaltsliste");
        inventarliste.setText("Inventarliste");
        gehaltshistorie.setText("Gehaltshistorie");
        title.setText("Hauptmenü");

        add(title, BorderLayout.PAGE_START);
        add(personenliste, BorderLayout.CENTER);
        add(gehaltsliste, BorderLayout.CENTER);
        add(inventarliste, BorderLayout.CENTER);
        add(gehaltshistorie, BorderLayout.CENTER);

    }

    public void setActionListener(ActionListener l) {
        personenliste.addActionListener(l);
    }

    public JButton getPersonenlisteButton() {
        return personenliste;
    }
}
