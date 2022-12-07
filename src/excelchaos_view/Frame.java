package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Locale;

/**
 *
 */
public class Frame extends JFrame implements ActionListener {
    private JMenuBar menubar;
    private JMenu actionMenu, helpMenu, docuMenu;
    private JMenuItem insertItem, updateItem, deleteItem, seeItem, aboutUsItem, docuItem;
    private JPanel eastPanel, centerPanel;
    private JLabel name, vorname, strasse, hausnummer, adresszusatz, plz, stadt, privatEmail, privateTelefonnummer, geburtsdatum,
            nationalityFirst, nationalitySecond, personalnummer, tuid, vertragMit, status,
            gehaltEingeplanntBis, transpondernummer, bueronummer, telefonnummerTUDA, inventarList;

    private JTextField tfName, tfVorname, tfStrasse, tfHausnummer, tfAdresszusatz, tfPLZ, tfStadt, tfPrivatEmail, tfPrivateTelefonnummer, tfGeburtsdatum,
            tfPersonalnummer, tfTuid, tfVertragMit, tfGehaltEingeplanntBis, tfTranspondernummer, tfBueronummer,
            tfTelefonnummerTUDA, tfInventarList;


    private JComboBox nationalityPickList, nationalityPickList2, statusPicklist;
    private JCheckBox nationalityCheckBox;

    private DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> list = new JList<>(model);
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


    Font f1 = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
    Font f2 = new Font(Font.SANS_SERIF, Font.PLAIN, 15);

    /**
     *
     */
    public Frame() {
        menubar = new JMenuBar();
        menubar.setBackground(Color.WHITE);

        actionMenu = new JMenu("Aktivitäten".toUpperCase());
        actionMenu.setFont(f1);
        actionMenu.setMnemonic(KeyEvent.VK_A);
        helpMenu = new JMenu("Hilfe".toUpperCase());
        helpMenu.setFont(f1);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        docuMenu = new JMenu("Dokumentation".toUpperCase());
        docuMenu.setFont(f1);
        docuMenu.setMnemonic(KeyEvent.VK_D);

        menubar.add(actionMenu);
        menubar.add(helpMenu);
        menubar.add(docuMenu);

        insertItem = new JMenuItem("Neue Person anlegen");
        insertItem.setFont(f2);
        insertItem.setMnemonic(KeyEvent.VK_I);
        updateItem = new JMenuItem("Bestehende Einträge aktualisieren");
        updateItem.setFont(f2);
        updateItem.setMnemonic(KeyEvent.VK_U);
        deleteItem = new JMenuItem("Bestehende Einträge löschen");
        deleteItem.setFont(f2);
        deleteItem.setMnemonic(KeyEvent.VK_E);
        seeItem = new JMenuItem("Daten anzeigen");
        seeItem.setFont(f2);
        seeItem.setMnemonic(KeyEvent.VK_S);
        aboutUsItem = new JMenuItem("Über");
        aboutUsItem.setFont(f2);
        aboutUsItem.setMnemonic(KeyEvent.VK_U);
        docuItem = new JMenuItem("Dokumentation v1.0");
        docuItem.setFont(f2);
        docuItem.setMnemonic(KeyEvent.VK_T);

        insertItem.addActionListener(this);
        updateItem.addActionListener(this);
        deleteItem.addActionListener(this);
        seeItem.addActionListener(this);

        actionMenu.add(seeItem);
        actionMenu.add(insertItem);
        actionMenu.add(updateItem);
        actionMenu.add(deleteItem);
        docuMenu.add(aboutUsItem);
        docuMenu.add(docuItem);

        eastPanel = new JPanel();
        eastPanel.setBackground(Color.white);
        eastPanel.setLayout(new BorderLayout());
        model.addElement("Historie");
        eastPanel.add(list, BorderLayout.CENTER);
        eastPanel.setPreferredSize(new Dimension(150, 100));

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.white);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setPreferredSize(new Dimension(100, 100));

        this.setTitle("EXCELCHAOS");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application
        this.setResizable(true); // die Groesse des angezeigten Fensters kann mit der Maus gezogen/geaendert werden
        this.setSize(750, 750);
        this.setLayout(new BorderLayout(10, 10));
        this.setJMenuBar(menubar);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(centerPanel, BorderLayout.CENTER);
        this.setVisible(true);

    }

    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        // Was passiert, wenn checkBoxZweiteStaatsangehoerigkeit angeklickt (auf TRUE gesetzt) wurde:
        if (e.getSource() == nationalityCheckBox) {
            nationalitySecond.setVisible(true);
            nationalityPickList2.setVisible(true);
        }


        if (insertItem.equals(e.getSource())) {
            doInsertItem();
        }
        if (seeItem.equals(e.getSource())) {
            try {
                doSeeItem();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (updateItem.equals(e.getSource())) {
            doUpdateItem();
        }
        if (deleteItem.equals(e.getSource())) {
            doDeleteItem();
        }
        if (e.getSource() == submit) {
            System.out.println("submitting");
            File file = new File("D:\\INTELLIJ BP CHAOS\\BPchaos\\src\\main\\java\\bp\\view\\data.txt");
            FileWriter fr = null;
            try {
                fr = new FileWriter(file, true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            BufferedWriter br = new BufferedWriter(fr);
            try {
                br.write(tfName.getText() + "," + tfVorname.getText() + "," + tfStrasse.getText() + ","
                        + tfPrivatEmail.getText() + "," + tfPrivateTelefonnummer.getText() + "," + tfGeburtsdatum.getText() + ","
                        + nationalityPickList.getSelectedItem().toString() + "," + nationalityPickList2.getSelectedItem().toString() + "," + tfPersonalnummer.getText() + ","
                        + tfTuid.getText() + "," + tfVertragMit.getText() + "," + statusPicklist.getSelectedItem().toString() + ","
                        + tfGehaltEingeplanntBis.getText() + "," + tfTranspondernummer.getText() + "," + tfBueronummer.getText() + ","
                        + tfTelefonnummerTUDA.getText() + "," + tfInventarList.getText() + "\n"
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try {
                br.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                fr.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            model.addElement("Eintrag eingefügt!");
            centerPanel.removeAll();
            centerPanel.revalidate();
            centerPanel.repaint();
        }
    }

    /**
     *
     */
    private void doDeleteItem() {
        System.out.println("DELETING ITEMS");
    }

    /**
     *
     */
    private void doUpdateItem() {
        System.out.println("UPDATING ITEMS");
        centerPanel.removeAll();
        centerPanel.repaint();
    }

    /**
     * @throws IOException
     */
    private void doSeeItem() throws IOException {
        centerPanel.removeAll();
        centerPanel.setLayout(new BorderLayout());
        System.out.println("SEEING ITEMS");

        String column[] = {"NAME", "VORNAME", "WOHNADRESSE", "PRIVAT E-MAIL", "PRIVAT TELEFONNR",
                "GEBURTSDATUM", "STAATANGEHOERIGKEIT 1", "STAATANGEHOERIGKEIT 2", "PERSONAL NR", "TUID", "VERTRAG MIT",
                "STATUS", "GEHALT EINGEPLANNT BIS", "TRANSPONDERNR", "BUERONUMMER", "TELEFONNR TUDA", "INVENTAR LIST"
        };
        File f = new File("D:\\INTELLIJ BP CHAOS\\BPchaos\\src\\main\\java\\bp\\view\\data.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            int lines = 0;
            while (br.readLine() != null) lines++;
            br.close();
            String resultData[][] = new String[lines][];


            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line = null;
            int currentIndex = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                resultData[currentIndex] = values;
                currentIndex++;
            }
            JTable jt = new JTable(resultData, column);

            jt.setBounds(30, 40, 200, 300);
            JScrollPane sp = new JScrollPane(jt);

            centerPanel.add(sp);
            centerPanel.revalidate();
            centerPanel.repaint();
            model.addElement("SEEING ENTRY");
            list.revalidate();
            list.repaint();
            eastPanel.revalidate();
            eastPanel.repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     *
     */
    private void doInsertItem() {
        System.out.println("INSERTING ITEMS");
        centerPanel.removeAll();
        centerPanel.setLayout(new BorderLayout());
        centerUp = new JPanel();
        centerDown = new JPanel();

        centerUp.setLayout(null);
        centerDown.setLayout(new GridLayout());
        centerPanel.add(centerUp, BorderLayout.CENTER);
        centerPanel.add(centerDown, BorderLayout.SOUTH);

        model.addElement("INSERTING ENTRY");

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
        nationalityCheckBox.addActionListener(this);
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
        submit.addActionListener(this);
        centerDown.add(submit);
        reset = new JButton("Felder zurücksetzen");
        centerDown.add(reset);

        centerPanel.revalidate();
        centerPanel.repaint();
    }


}

