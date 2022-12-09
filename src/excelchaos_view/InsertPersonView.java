package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InsertPersonView extends JPanel {
    private JLabel name, vorname, strasse, hausnummer, adresszusatz, plz, stadt, privatEmail, privateTelefonnummer, geburtsdatum,
            nationalityFirst, nationalitySecond, personalnummer, tuid, vertragMit, status,
            gehaltEingeplanntBis, transpondernummer, bueronummer, telefonnummerTUDA, inventarList;

    private JTextField tfName, tfVorname, tfStrasse, tfHausnummer, tfAdresszusatz, tfPLZ, tfStadt, tfPrivatEmail, tfPrivateTelefonnummer, tfGeburtsdatum,
            tfPersonalnummer, tfTuid, tfVertragMit, tfGehaltEingeplanntBis, tfTranspondernummer, tfBueronummer,
            tfTelefonnummerTUDA, tfInventarList;


    private JComboBox nationalityPickList, nationalityPickList2, statusPicklist;
    private JCheckBox nationalityCheckBox;

    private JButton submit, reset;

    private JPanel centerUp, centerDown;

    private int beginYPos = 50;
    private int factor = 0;
    private final int Y_INCREASE = 30;
    private final int LABEL_X_POS = 20;
    private final int FIELD_X_POS = 200;
    private final int LABEL_WIDTH = 180;
    private final int LABEL_HEIGHT = 25;
    private final int FIELD_WIDTH = 400;
    private final int FIELD_HEIGHT = 25;

    public void init() {
        setLayout(new BorderLayout());
        centerUp = new JPanel();
        centerDown = new JPanel();

        centerUp.setLayout(null);
        centerDown.setLayout(new GridLayout());
        add(centerUp, BorderLayout.CENTER);
        add(centerDown, BorderLayout.SOUTH);

        SideMenuPanelActionLogView.model.addElement("Eintrag einfügen");

        name = new JLabel("Nachname");
        centerUp.add(name);
        name.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfName = new JTextField();
        tfName.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfName);
        factor++;

        vorname = new JLabel("Vorname");
        vorname.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        centerUp.add(vorname);
        tfVorname = new JTextField();
        tfVorname.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfVorname);
        factor++;

        // An dieser Stelle müssen folgende 6 Felder hinzugefügt werden: Straße, Hausnummer, Adresszusatz, PLZ, Stadt, Land
        strasse = new JLabel("Straße");
        centerUp.add(strasse);
        strasse.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfStrasse = new JTextField();
        tfStrasse.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfStrasse);
        factor++;

        // Hausnummer
        hausnummer = new JLabel("Hausnummer");
        centerUp.add(hausnummer);
        hausnummer.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfHausnummer = new JTextField();
        tfHausnummer.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfHausnummer);
        factor++;

        // Adresszusatz
        adresszusatz = new JLabel("Adresszusatz");
        centerUp.add(adresszusatz);
        adresszusatz.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfAdresszusatz = new JTextField();
        tfAdresszusatz.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfAdresszusatz);
        factor++;

        // PLZ
        plz = new JLabel("PLZ");
        centerUp.add(plz);
        plz.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfPLZ = new JTextField();
        tfPLZ.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfPLZ);
        factor++;

        // Stadt
        stadt = new JLabel("Stadt");
        centerUp.add(stadt);
        stadt.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfStadt = new JTextField();
        tfStadt.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfStadt);
        factor++;

        // Land -> soll Land als Feld eingefügt werden????

        privatEmail = new JLabel("Private E-mail Adresse");
        centerUp.add(privatEmail);
        privatEmail.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfPrivatEmail = new JTextField();
        tfPrivatEmail.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfPrivatEmail);
        factor++;

        privateTelefonnummer = new JLabel("Private Telefonnummer");
        centerUp.add(privateTelefonnummer);
        privateTelefonnummer.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfPrivateTelefonnummer = new JTextField();
        tfPrivateTelefonnummer.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfPrivateTelefonnummer);
        factor++;

        geburtsdatum = new JLabel("Geburtsdatum");
        centerUp.add(geburtsdatum);
        geburtsdatum.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfGeburtsdatum = new JTextField();
        tfGeburtsdatum.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfGeburtsdatum); // Wird hier das Gebutsdatum eingetragen? Soll man hier vor dem Eintragen eine Fallunterscheidung anlegen?
        factor++;

        // Das Feld sollte als Auswahlliste angelegt sein!
        nationalityFirst = new JLabel("Staatsangehörigkeit");
        centerUp.add(nationalityFirst);
        nationalityFirst.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);

        String[] nationalityArray = {"Afghanistan", "Ägypten", "Australien", "Belgien", "Chile",
                "China", "Deutschland", "Finnland", "Indien", "Italien", "Kanada"}; // Ist nur ein Beispiel an Auswahl
        nationalityPickList = new JComboBox(nationalityArray);
        nationalityPickList.setMaximumRowCount(5); // die Anzahl der Reihen in der Auswahlliste, die bei der Wahl angezeigt werden
        nationalityPickList.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(nationalityPickList);
        factor++;

        // Checkbox, die abfragt, ob eine zweite Staatsangehoerigkeit vorhanden ist. Wenn diese TRUE ist, erst dann sollte "STAATANGEHOERIGKEIT 2?" sichtbar und der Eintrag auch möglich sein!
        nationalityCheckBox = new JCheckBox("zweite Staatsangehörigkeit?", false);
        nationalityCheckBox.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        centerUp.add(nationalityCheckBox);
        factor++;

        // Sichtbarkeit von dem Label mit dem dazugehörigen Textfeld für die Eintragung
        nationalitySecond = new JLabel("Zweite Staatsangehörigkeit");
        centerUp.add(nationalitySecond);
        nationalitySecond.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        nationalitySecond.setVisible(false);
        String[] nationalityArray2 = {"Keine", "Deutschland", "Afghanistan", "Ägypten", "Australien", "Belgien", "Chile",
                "China", "Finnland", "Indien", "Italien", "Kanada"}; // Ist nur ein Beispiel an Auswahl
        nationalityPickList2 = new JComboBox(nationalityArray2);
        nationalityPickList2.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(nationalityPickList2);
        nationalityPickList2.setVisible(false);
        factor++;

        personalnummer = new JLabel("Personalnummer");
        centerUp.add(personalnummer);
        personalnummer.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfPersonalnummer = new JTextField();
        tfPersonalnummer.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfPersonalnummer);
        factor++;

        tuid = new JLabel("TU-ID");
        tuid.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        centerUp.add(tuid);
        tfTuid = new JTextField();
        tfTuid.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfTuid);
        factor++;

        vertragMit = new JLabel("VERTRAG MIT?");
        centerUp.add(vertragMit);
        vertragMit.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfVertragMit = new JTextField();
        tfVertragMit.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfVertragMit);
        factor++;

        status = new JLabel("Status");
        centerUp.add(status);
        status.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        String[] statusArray = {"Nicht ausgewählt", "WiMi", "ATM", "SHK"};
        statusPicklist = new JComboBox(statusArray);
        statusPicklist.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(statusPicklist);
        factor++;

        //Wenn oben ATM oder WiMi ausgewählt wird, dann haben diese weiteren Felder.
        // Welche genau - ist unbekannt, muss geklärt und eingetragen werden (Viktoria)

        gehaltEingeplanntBis = new JLabel("Gehalt eingeplant bis");
        centerUp.add(gehaltEingeplanntBis);
        gehaltEingeplanntBis.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfGehaltEingeplanntBis = new JTextField();
        tfGehaltEingeplanntBis.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfGehaltEingeplanntBis);
        factor++;

        transpondernummer = new JLabel("Transpondernummer");
        centerUp.add(transpondernummer);
        transpondernummer.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfTranspondernummer = new JTextField();
        tfTranspondernummer.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfTranspondernummer);
        factor++;

        bueronummer = new JLabel("Büronummer");
        centerUp.add(bueronummer);
        bueronummer.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfBueronummer = new JTextField();
        tfBueronummer.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfBueronummer);
        factor++;

        telefonnummerTUDA = new JLabel("Telefon TU-DA");
        centerUp.add(telefonnummerTUDA);
        telefonnummerTUDA.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfTelefonnummerTUDA = new JTextField();
        tfTelefonnummerTUDA.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfTelefonnummerTUDA);
        factor++;

        inventarList = new JLabel("INVENTAR?");
        centerUp.add(inventarList);
        inventarList.setBounds(LABEL_X_POS, beginYPos + factor * Y_INCREASE, LABEL_WIDTH, LABEL_HEIGHT);
        tfInventarList = new JTextField();
        tfInventarList.setBounds(FIELD_X_POS, beginYPos + factor * Y_INCREASE, FIELD_WIDTH, FIELD_HEIGHT);
        centerUp.add(tfInventarList);

        beginYPos = 50;
        factor = 0;

        submit = new JButton("Person speichern");
        centerDown.add(submit);
        reset = new JButton("Felder zurücksetzen");
        centerDown.add(reset);

        revalidate();
        repaint();
    }

    public void setActionListener(ActionListener l) {
        nationalityCheckBox.addActionListener(l);
        submit.addActionListener(l);
    }

    public JCheckBox getNationalityCheckBox() {
        return nationalityCheckBox;
    }

    public JLabel getNationalitySecond() {
        return nationalitySecond;
    }

    public JComboBox getNationalityPickList2() {
        return nationalityPickList2;
    }

    public JButton getSubmit() {
        return submit;
    }

    public JTextField getTfName() {
        return tfName;
    }

    public JTextField getTfVorname() {
        return tfVorname;
    }

    public JTextField getTfStrasse() {
        return tfStrasse;
    }

    public JTextField getTfHausnummer() {
        return tfHausnummer;
    }

    public JTextField getTfAdresszusatz() {
        return tfAdresszusatz;
    }

    public JTextField getTfPLZ() {
        return tfPLZ;
    }

    public JTextField getTfStadt() {
        return tfStadt;
    }

    public JTextField getTfPrivatEmail() {
        return tfPrivatEmail;
    }

    public JTextField getTfPrivateTelefonnummer() {
        return tfPrivateTelefonnummer;
    }

    public JTextField getTfGeburtsdatum() {
        return tfGeburtsdatum;
    }

    public JTextField getTfPersonalnummer() {
        return tfPersonalnummer;
    }

    public JTextField getTfTuid() {
        return tfTuid;
    }

    public JTextField getTfVertragMit() {
        return tfVertragMit;
    }

    public JTextField getTfGehaltEingeplanntBis() {
        return tfGehaltEingeplanntBis;
    }

    public JTextField getTfTranspondernummer() {
        return tfTranspondernummer;
    }

    public JTextField getTfBueronummer() {
        return tfBueronummer;
    }

    public JTextField getTfTelefonnummerTUDA() {
        return tfTelefonnummerTUDA;
    }

    public JTextField getTfInventarList() {
        return tfInventarList;
    }

    public JComboBox getStatusPicklist() {
        return statusPicklist;
    }

    public JComboBox getNationalityPickList() {
        return nationalityPickList;
    }
}
