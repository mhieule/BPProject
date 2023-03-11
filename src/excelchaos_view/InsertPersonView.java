package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import excelchaos_model.CountryModel;
import excelchaos_model.inputVerifier.WorkScopeVerifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class InsertPersonView extends JPanel {

    private JLabel name, vorname, strasse, hausnummer, adresszusatz, plz, stadt, privatEmail, privateTelefonnummer, geburtsdatum,
            nationalityFirst, nationalitySecond, personalnummer, tuid, typeOfJob, visaValidUntil,
            gehaltEingeplanntBis, transpondernummer, bueronummer, telefonnummerTUDA, workStart, workEnd, workScope, payGroupOnHiring, payLevelOnHiring, hiwiTypeOfPayment, vblstate, puffer;

    private JTextField tfName, tfVorname, tfStrasse, tfHausnummer, tfAdresszusatz, tfPLZ, tfStadt, tfPrivatEmail, tfPrivateTelefonnummer,
            tfPersonalnummer, tfTuid, tfTranspondernummer, tfBueronummer,
            tfTelefonnummerTUDA, tfWorkScope;

    private DatePicker tfGeburtsdatum, tfVisaValidUntil, tfWorkStart, tfWorkEnd, tfSalaryPlannedUntil;

    private JComboBox nationalityPickList, nationalityPickList2, typeOfJobPicklist, hiwiTypeOfPaymentList, payLevelList, payGroupList, vblList;

    private JCheckBox nationalityCheckBox, visaRequiredCheckBox;

    private JButton submit, reset, salary, cancel;

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

        centerDown.setLayout(new BoxLayout(centerDown, BoxLayout.X_AXIS));
        add(centerUp, BorderLayout.CENTER);
        add(centerDown, BorderLayout.SOUTH);

        name = new JLabel("Name*");
        setConstraintsLabel(name, 0);
        tfName = new JTextField();
        setConstraintsTextField(tfName, 0);
        constraints.insets.top = 5;

        vorname = new JLabel("Vorname*");
        setConstraintsLabel(vorname, 1);
        tfVorname = new JTextField();
        setConstraintsTextField(tfVorname, 1);

        strasse = new JLabel("Straße");
        setConstraintsLabel(strasse, 2);
        tfStrasse = new JTextField();
        setConstraintsTextField(tfStrasse, 2);

        hausnummer = new JLabel("Hausnummer");
        setConstraintsLabel(hausnummer, 3);
        tfHausnummer = new JTextField();
        setConstraintsTextField(tfHausnummer, 3);

        adresszusatz = new JLabel("Adresszusatz");
        setConstraintsLabel(adresszusatz, 4);
        tfAdresszusatz = new JTextField();
        setConstraintsTextField(tfAdresszusatz, 4);

        plz = new JLabel("Postleitzahl");
        setConstraintsLabel(plz, 5);
        tfPLZ = new JTextField();
        setConstraintsTextField(tfPLZ, 5);

        stadt = new JLabel("Stadt");
        setConstraintsLabel(stadt, 6);
        tfStadt = new JTextField();
        setConstraintsTextField(tfStadt, 6);

        geburtsdatum = new JLabel("Geburtsdatum");
        setConstraintsLabel(geburtsdatum, 7);
        tfGeburtsdatum = new DatePicker();
        setConstraintsDatePicker(tfGeburtsdatum, 7);

        privatEmail = new JLabel("Private E-Mail-Adresse");
        setConstraintsLabel(privatEmail, 8);
        tfPrivatEmail = new JTextField();
        setConstraintsTextField(tfPrivatEmail, 8);

        privateTelefonnummer = new JLabel("Private Telefonnummer");
        setConstraintsLabel(privateTelefonnummer, 9);
        tfPrivateTelefonnummer = new JTextField();
        setConstraintsTextField(tfPrivateTelefonnummer, 9);

        telefonnummerTUDA = new JLabel("Telefon TU-DA");
        setConstraintsLabel(telefonnummerTUDA, 10);
        tfTelefonnummerTUDA = new JTextField();
        setConstraintsTextField(tfTelefonnummerTUDA, 10);

        nationalityFirst = new JLabel("Staatsangehörigkeit");
        setConstraintsLabel(nationalityFirst, 11);
        String[] nationalityArray = CountryModel.getCountries();
        nationalityPickList = new JComboBox(nationalityArray);
        nationalityPickList.setMaximumRowCount(5);
        setConstraintsJComboBox(nationalityPickList, 11);

        nationalityCheckBox = new JCheckBox("Zweite Staatsangehörigkeit?", false);
        setConstraintsJCheckBox(nationalityCheckBox, 12);

        nationalitySecond = new JLabel("Zweite Staatsangehörigkeit");
        setConstraintsLabel(nationalitySecond, 13);
        nationalitySecond.setVisible(false);

        String[] nationalityArray2 = CountryModel.getCountriesWithKeine();
        nationalityPickList2 = new JComboBox(nationalityArray2);
        setConstraintsJComboBox(nationalityPickList2, 13);
        nationalityPickList2.setVisible(false);

        visaRequiredCheckBox = new JCheckBox("Visum notwendig?", false);
        setConstraintsJCheckBox(visaRequiredCheckBox, 14);
        visaValidUntil = new JLabel("Visum gültig bis");
        setConstraintsLabel(visaValidUntil, 15);
        visaValidUntil.setVisible(false);
        tfVisaValidUntil = new DatePicker();
        setConstraintsDatePicker(tfVisaValidUntil, 15);
        tfVisaValidUntil.setVisible(false);

        personalnummer = new JLabel("Personalnummer");
        setConstraintsLabel(personalnummer, 16);
        tfPersonalnummer = new JTextField();
        setConstraintsTextField(tfPersonalnummer, 16);

        transpondernummer = new JLabel("Transpondernummer");
        setConstraintsLabel(transpondernummer, 17);
        tfTranspondernummer = new JTextField();
        setConstraintsTextField(tfTranspondernummer, 17);

        bueronummer = new JLabel("Büronummer");
        setConstraintsLabel(bueronummer, 18);
        tfBueronummer = new JTextField();
        setConstraintsTextField(tfBueronummer, 18);

        tuid = new JLabel("TU-ID");
        setConstraintsLabel(tuid, 19);
        tfTuid = new JTextField();
        setConstraintsTextField(tfTuid, 19);

        typeOfJob = new JLabel("Art der Anstellung*");
        setConstraintsLabel(typeOfJob, 20);
        String[] statusArray = {"Nicht ausgewählt", "WiMi", "ATM", "SHK"};
        typeOfJobPicklist = new JComboBox(statusArray);
        setConstraintsJComboBox(typeOfJobPicklist, 20);

        workStart = new JLabel("Beschäftigungsbeginn*");
        setConstraintsLabel(workStart, 21);
        tfWorkStart = new DatePicker();
        setConstraintsDatePicker(tfWorkStart, 21);

        workEnd = new JLabel("Beschäftigungsende*");
        setConstraintsLabel(workEnd, 22);
        tfWorkEnd = new DatePicker();
        setConstraintsDatePicker(tfWorkEnd, 22);

        workScope = new JLabel("Beschäftigungsumfang*");
        setConstraintsLabel(workScope, 23);
        tfWorkScope = new JTextField();
        setConstraintsTextField(tfWorkScope, 23);

        payGroupOnHiring = new JLabel("Gehaltsklasse*");
        setConstraintsLabel(payGroupOnHiring, 24);
        payGroupOnHiring.setVisible(false);
        String[] payGroups = {"Nicht ausgewählt", "E13", "E14"};
        payGroupList = new JComboBox(payGroups);
        setConstraintsJComboBox(payGroupList, 24);
        payGroupList.setVisible(false);

        payLevelOnHiring = new JLabel("Gehaltsstufe*");
        setConstraintsLabel(payLevelOnHiring, 25);
        payLevelOnHiring.setVisible(false);
        String[] payLevels = {"Nicht ausgewählt", "1A", "1B", "1", "2", "3", "4", "5", "6"};
        payLevelList = new JComboBox(payLevels);
        setConstraintsJComboBox(payLevelList, 25);
        payLevelList.setVisible(false);

        vblstate = new JLabel("VBL-Status*");
        setConstraintsLabel(vblstate, 26);
        vblstate.setVisible(false);
        String[] vbl = {"Nicht ausgewählt", "Pflichtig", "Befreit"};
        vblList = new JComboBox(vbl);
        setConstraintsJComboBox(vblList, 26);
        vblList.setVisible(false);

        hiwiTypeOfPayment = new JLabel("SHK Stundensatz*");
        setConstraintsLabel(hiwiTypeOfPayment, 27);
        hiwiTypeOfPayment.setVisible(false);
        String[] hiwiPaymentArray = {"Nicht ausgewählt", "SHK Basisvergütung", "SHK erhöhter Stundensatz", "WHK"};
        hiwiTypeOfPaymentList = new JComboBox(hiwiPaymentArray);
        setConstraintsJComboBox(hiwiTypeOfPaymentList, 27);
        hiwiTypeOfPaymentList.setVisible(false);

        gehaltEingeplanntBis = new JLabel("Gehalt eingeplant bis*");
        setConstraintsLabel(gehaltEingeplanntBis, 28);
        tfSalaryPlannedUntil = new DatePicker();
        setConstraintsDatePicker(tfSalaryPlannedUntil, 28);

        puffer = new JLabel(" ");
        setConstraintsPuffer(puffer, 29);

        leftButtons = new JPanel(new FlowLayout());
        rightButtons = new JPanel(new FlowLayout());
        submit = new JButton("Person speichern und Felder zurücksetzen");
        leftButtons.add(submit);
        salary = new JButton("Person speichern und zur Gehaltseingabe");
        leftButtons.add(salary);
        centerDown.add(leftButtons);
        reset = new JButton("Felder zurücksetzen");
        centerDown.add(Box.createHorizontalGlue());
        rightButtons.add(reset);
        cancel = new JButton("Abbrechen");
        rightButtons.add(cancel);
        centerDown.add(rightButtons);

        JScrollPane scrollPane = new JScrollPane(centerUp, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
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
        cancel.addActionListener(l);
    }

    public void markMustBeFilledTextFields() {
        name.setForeground(Color.RED);
        vorname.setForeground(Color.RED);
        gehaltEingeplanntBis.setForeground(Color.RED);
        payGroupOnHiring.setForeground(Color.RED);
        payLevelOnHiring.setForeground(Color.RED);
        workStart.setForeground(Color.RED);
        workEnd.setForeground(Color.RED);
        workScope.setForeground(Color.RED);
        typeOfJob.setForeground(Color.RED);
        vblstate.setForeground(Color.RED);
        hiwiTypeOfPayment.setForeground(Color.RED);
    }

    public JLabel getPayGroupOnHiring() {
        return payGroupOnHiring;
    }

    public JLabel getPayLevelOnHiring() {
        return payLevelOnHiring;
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

    public JLabel getWorkScope() {
        return workScope;
    }

    public JComboBox getNationalityPickList2() {
        return nationalityPickList2;
    }

    public JLabel getVblstate() {
        return vblstate;
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

    public JButton getCancel() {
        return cancel;
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

    public JTextField getTfStrasse() {
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

    public DatePicker getTfSalaryPlannedUntil() {
        return tfSalaryPlannedUntil;
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

    public JComboBox getPayLevelList() {
        return payLevelList;
    }

    public JComboBox getVblList() {
        return vblList;
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

