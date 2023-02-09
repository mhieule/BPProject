package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import excelchaos_model.CountryModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class InsertPersonView extends JPanel {

    private JLabel name, vorname, strasse, hausnummer, adresszusatz, plz, stadt, privatEmail, privateTelefonnummer, geburtsdatum,
            nationalityFirst, nationalitySecond, personalnummer, tuid, typeOfJob, visaValidUntil,
            gehaltEingeplanntBis, transpondernummer, bueronummer, telefonnummerTUDA, workStart, workEnd, workScope, payGroupOnHiring, payGradeOnHiring, hiwiTypeOfPayment, puffer;

    private JTextField tfName, tfVorname, tfStrasse, tfHausnummer, tfAdresszusatz, tfPLZ, tfStadt, tfPrivatEmail, tfPrivateTelefonnummer,
            tfPersonalnummer, tfTuid, tfGehaltEingeplanntBis, tfTranspondernummer, tfBueronummer,
            tfTelefonnummerTUDA, tfWorkScope;

    private DatePicker tfGeburtsdatum, tfVisaValidUntil, tfWorkStart, tfWorkEnd;


    private JComboBox nationalityPickList, nationalityPickList2, typeOfJobPicklist, hiwiTypeOfPaymentList, payGradeList, payGroupList;
    private JCheckBox nationalityCheckBox, visaRequiredCheckBox;

    private JButton submit, reset, salary;

    private JPanel centerUp, centerDown, leftButtons, rightButtons;

    private GridBagConstraints constraints;

    private final int FIELD_WIDTH = 400;
    private final int FIELD_HEIGHT = 25;

    public void init() {
        setLayout(new BorderLayout());
        centerUp = new JPanel();
        centerDown = new JPanel();
        GridBagLayout gridLayout = new GridBagLayout();
        centerUp.setLayout(gridLayout);
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(30, 25, 0, 50);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;


        centerDown.setLayout(new BoxLayout(centerDown,BoxLayout.X_AXIS));
        add(centerUp, BorderLayout.CENTER);
        add(centerDown, BorderLayout.SOUTH);

        name = new JLabel("Nachname");

        setConstraintsLabel(name, 0);
        tfName = new JTextField();
        setConstraintsTextField(tfName, 0);
        constraints.insets.top = 5;

        vorname = new JLabel("Vorname");
        setConstraintsLabel(vorname, 1);
        tfVorname = new JTextField();
        setConstraintsTextField(tfVorname, 1);

        // An dieser Stelle müssen folgende 6 Felder hinzugefügt werden: Straße, Hausnummer, Adresszusatz, PLZ, Stadt, Land
        strasse = new JLabel("Straße");
        setConstraintsLabel(strasse, 2);
        tfStrasse = new JTextField();
        setConstraintsTextField(tfStrasse, 2);


        // Hausnummer
        hausnummer = new JLabel("Hausnummer");
        setConstraintsLabel(hausnummer, 3);
        tfHausnummer = new JTextField();
        setConstraintsTextField(tfHausnummer, 3);

        // Adresszusatz
        adresszusatz = new JLabel("Adresszusatz");
        setConstraintsLabel(adresszusatz, 4);
        tfAdresszusatz = new JTextField();
        setConstraintsTextField(tfAdresszusatz, 4);

        // PLZ
        plz = new JLabel("Postleitzahl");
        setConstraintsLabel(plz, 5);
        tfPLZ = new JTextField();
        setConstraintsTextField(tfPLZ, 5);

        // Stadt
        stadt = new JLabel("Stadt");
        setConstraintsLabel(stadt, 6);
        tfStadt = new JTextField();
        setConstraintsTextField(tfStadt, 6);
        //factor++;

        // Land -> soll Land als Feld eingefügt werden????

        privatEmail = new JLabel("Private E-Mail-Adresse");
        setConstraintsLabel(privatEmail, 7);
        tfPrivatEmail = new JTextField();
        setConstraintsTextField(tfPrivatEmail, 7);

        privateTelefonnummer = new JLabel("Private Telefonnummer");
        setConstraintsLabel(privateTelefonnummer, 8);
        tfPrivateTelefonnummer = new JTextField();
        setConstraintsTextField(tfPrivateTelefonnummer, 8);

        geburtsdatum = new JLabel("Geburtsdatum");
        setConstraintsLabel(geburtsdatum, 9);
        tfGeburtsdatum = new DatePicker();
        setConstraintsDatePicker(tfGeburtsdatum, 9);

        nationalityFirst = new JLabel("Staatsangehörigkeit");
        setConstraintsLabel(nationalityFirst, 10);
        String[] nationalityArray = CountryModel.getCountries();
        nationalityPickList = new JComboBox(nationalityArray);
        nationalityPickList.setMaximumRowCount(5); // die Anzahl der Reihen in der Auswahlliste, die bei der Wahl angezeigt werden
        setConstraintsJComboBox(nationalityPickList, 10);

        // Checkbox, die abfragt, ob eine zweite Staatsangehoerigkeit vorhanden ist. Wenn diese TRUE ist, erst dann sollte "STAATANGEHOERIGKEIT 2?" sichtbar und der Eintrag auch möglich sein!
        nationalityCheckBox = new JCheckBox("Zweite Staatsangehörigkeit?", false);
        setConstraintsJCheckBox(nationalityCheckBox, 11);

        // Sichtbarkeit von dem Label mit dem dazugehörigen Textfeld für die Eintragung
        nationalitySecond = new JLabel("Zweite Staatsangehörigkeit");
        setConstraintsLabel(nationalitySecond, 12);
        nationalitySecond.setVisible(false);

        String[] nationalityArray2 = CountryModel.getCountriesWithKeine();
        nationalityPickList2 = new JComboBox(nationalityArray2);
        setConstraintsJComboBox(nationalityPickList2, 12);
        nationalityPickList2.setVisible(false);

        visaRequiredCheckBox = new JCheckBox("Visum notwendig?", false);
        setConstraintsJCheckBox(visaRequiredCheckBox, 13);
        visaValidUntil = new JLabel("Visum gültig bis");
        setConstraintsLabel(visaValidUntil, 14);
        visaValidUntil.setVisible(false);
        tfVisaValidUntil = new DatePicker();
        setConstraintsDatePicker(tfVisaValidUntil, 14);
        tfVisaValidUntil.setVisible(false);


        personalnummer = new JLabel("Personalnummer");
        setConstraintsLabel(personalnummer, 15);
        tfPersonalnummer = new JTextField();
        setConstraintsTextField(tfPersonalnummer, 15);

        tuid = new JLabel("TU-ID");
        setConstraintsLabel(tuid, 16);
        tfTuid = new JTextField();
        setConstraintsTextField(tfTuid, 16);

        typeOfJob = new JLabel("Art der Anstellung");
        setConstraintsLabel(typeOfJob, 17);
        String[] statusArray = {"Nicht ausgewählt", "WiMi", "ATM", "SHK"};
        typeOfJobPicklist = new JComboBox(statusArray);
        setConstraintsJComboBox(typeOfJobPicklist, 17);

        workStart = new JLabel("Beschäftigungsbeginn");
        setConstraintsLabel(workStart, 18);
        tfWorkStart = new DatePicker();
        setConstraintsDatePicker(tfWorkStart, 18);

        workEnd = new JLabel("Beschäftigungsende");
        setConstraintsLabel(workEnd, 19);
        tfWorkEnd = new DatePicker();
        setConstraintsDatePicker(tfWorkEnd, 19);

        workScope = new JLabel("Beschäftigungsumfang");
        setConstraintsLabel(workScope, 20);
        tfWorkScope = new JTextField();
        setConstraintsTextField(tfWorkScope, 20);

        payGroupOnHiring = new JLabel("Gehaltsgruppe bei Einstellung");
        setConstraintsLabel(payGroupOnHiring, 21);
        payGroupOnHiring.setVisible(false);
        String[] payGroups = {"Nicht ausgewählt", "E13", "E14"};
        payGroupList = new JComboBox(payGroups);
        setConstraintsJComboBox(payGroupList, 21);
        payGroupList.setVisible(false);

        payGradeOnHiring = new JLabel("Gehaltsstufe bei Einstellung");
        setConstraintsLabel(payGradeOnHiring, 22);
        payGradeOnHiring.setVisible(false);
        String[] payGrades = {"Nicht ausgewählt", "1A","1B","1","2","3","4","5","6"};
        payGradeList = new JComboBox(payGrades);
        setConstraintsJComboBox(payGradeList,22);
        payGradeList.setVisible(false);

        hiwiTypeOfPayment = new JLabel("SHK Stundensatz");
        setConstraintsLabel(hiwiTypeOfPayment, 21);
        hiwiTypeOfPayment.setVisible(false);
        String[] hiwiPaymentArray = {"Nicht ausgewählt", "SHK Basisvergütung", "SHK erhöhter Stundensatz", "WHK"};
        hiwiTypeOfPaymentList = new JComboBox(hiwiPaymentArray);
        setConstraintsJComboBox(hiwiTypeOfPaymentList, 21);
        hiwiTypeOfPaymentList.setVisible(false);

        gehaltEingeplanntBis = new JLabel("Gehalt eingeplant bis");
        setConstraintsLabel(gehaltEingeplanntBis, 23);
        tfGehaltEingeplanntBis = new JTextField();
        setConstraintsTextField(tfGehaltEingeplanntBis, 23);

        transpondernummer = new JLabel("Transpondernummer");
        setConstraintsLabel(transpondernummer, 24);
        tfTranspondernummer = new JTextField();
        setConstraintsTextField(tfTranspondernummer, 24);

        bueronummer = new JLabel("Büronummer");
        setConstraintsLabel(bueronummer, 25);
        tfBueronummer = new JTextField();
        setConstraintsTextField(tfBueronummer, 25);

        telefonnummerTUDA = new JLabel("Telefon TU-DA");
        setConstraintsLabel(telefonnummerTUDA, 26);
        tfTelefonnummerTUDA = new JTextField();
        setConstraintsTextField(tfTelefonnummerTUDA, 26);

        puffer = new JLabel(" ");
        setConstraintsPuffer(puffer,27);

        leftButtons = new JPanel(new FlowLayout());
        rightButtons = new JPanel(new FlowLayout());
        submit = new JButton("Person speichern");
        leftButtons.add(submit);
        salary = new JButton("Person speichern und zur Gehaltseingabe");
        leftButtons.add(salary);
        centerDown.add(leftButtons);
        reset = new JButton("Felder zurücksetzen");
        centerDown.add(Box.createHorizontalGlue());
        rightButtons.add(reset);
        centerDown.add(rightButtons);

        JScrollPane scrollPane = new JScrollPane(centerUp, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        revalidate();
        repaint();
    }

    public void setActionListener(ActionListener l) {
        nationalityCheckBox.addActionListener(l);
        visaRequiredCheckBox.addActionListener(l);
        submit.addActionListener(l);
        salary.addActionListener(l);
        typeOfJobPicklist.addActionListener(l);
        reset.addActionListener(l);
    }

    public JLabel getPayGroupOnHiring() {
        return payGroupOnHiring;
    }

    public JLabel getPayGradeOnHiring() {
        return payGradeOnHiring;
    }

    public JLabel getHiwiTypeOfPayment() {
        return hiwiTypeOfPayment;
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

    public JCheckBox getVisaRequiredCheckBox() {
        return visaRequiredCheckBox;
    }

    public JLabel getVisaValidUntil() {
        return visaValidUntil;
    }

    public DatePicker getTfVisaValidUntil() {
        return tfVisaValidUntil;
    }

    public JButton getSubmit() {
        return submit;
    }

    public JButton getSalaryEntry() {
        return salary;
    }

    public JButton getReset() {
        return reset;
    }

    public JTextField getTfName() {
        return tfName;
    }

    public JTextField getTfVorname() {
        return tfVorname;
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

    public JTextField getTfStrasse(){
        return tfStrasse;
    }

    public JTextField getTfPrivatEmail() {
        return tfPrivatEmail;
    }

    public JTextField getTfPrivateTelefonnummer() {
        return tfPrivateTelefonnummer;
    }

    public DatePicker getTfGeburtsdatum() {
        return tfGeburtsdatum;
    }

    public JTextField getTfPersonalnummer() {
        return tfPersonalnummer;
    }

    public JTextField getTfTuid() {
        return tfTuid;
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


    public JComboBox getStatusPicklist() {
        return typeOfJobPicklist;
    }

    public JComboBox getNationalityPickList() {
        return nationalityPickList;
    }

    public DatePicker getTfWorkEnd() {
        return tfWorkEnd;
    }

    public DatePicker getTfWorkStart() {
        return tfWorkStart;
    }

    public JComboBox getHiwiTypeOfPaymentList() {
        return hiwiTypeOfPaymentList;
    }

    public JComboBox getTypeOfJobPicklist() {
        return typeOfJobPicklist;
    }

    public JTextField getTfWorkScope() {
        return tfWorkScope;
    }

    public JComboBox getPayGroupList() {
        return payGroupList;
    }

    public JComboBox getPayGradeList() {
        return payGradeList;
    }

    private void setConstraintsLabel(JLabel label, int rowNumber) {
        constraints.gridx = 0;
        constraints.gridy = rowNumber;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        centerUp.add(label, constraints);
    }

    private void setConstraintsTextField(JTextField textField, int rowNumber) {
        textField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        constraints.gridx = 1;
        constraints.gridy = rowNumber;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0;
        centerUp.add(textField, constraints);
    }

    private void setConstraintsDatePicker(DatePicker datePicker, int rowNumber) {
        datePicker.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        constraints.gridx = 1;
        constraints.gridy = rowNumber;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0;
        centerUp.add(datePicker, constraints);
    }

    private void setConstraintsJComboBox(JComboBox jComboBox, int rowNumber) {
        jComboBox.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        constraints.gridx = 1;
        constraints.gridy = rowNumber;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0;
        centerUp.add(jComboBox, constraints);
    }

    private void setConstraintsJCheckBox(JCheckBox jCheckBox, int rowNumber) {
        constraints.gridx = 0;
        constraints.gridy = rowNumber;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        centerUp.add(jCheckBox, constraints);
    }

    private void setConstraintsPuffer(JLabel label, int rowNumber) {
        constraints.gridx = 0;
        constraints.gridy = rowNumber;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        centerUp.add(label, constraints);
    }
}

