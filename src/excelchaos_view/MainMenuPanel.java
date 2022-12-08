package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    private JLabel title;
    private JButton personenliste;
    private JButton gehaltsliste;
    private JButton inventarliste;

    public void init() {
        personenliste = new JButton();
        gehaltsliste = new JButton();
        inventarliste = new JButton();
        title = new JLabel();

        personenliste.setText("Personenstammdaten");
        gehaltsliste.setText("Gehaltsliste");
        inventarliste.setText("Inventarliste");
        title.setText("Hauptmenü");
        GridLayout layout = new GridLayout(2,2);
        setLayout(layout);
        add(personenliste);
        add(gehaltsliste);
        add(inventarliste);
        setBackground(Color.RED);

    }
    public void setActionListener(ActionListener l){
        personenliste.addActionListener(l);
    }
    public JButton getPersonenlisteButton(){
        return personenliste;
    }
}
