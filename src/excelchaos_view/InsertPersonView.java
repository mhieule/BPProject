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

    /**
     * initialises GUI elements
     */
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

    /**
     * Sets the action listener for all Buttons, ComboBoxes and CheckBoxes
     * @param l is the ActionListener
     */
    public void setActionListener(ActionListener l) {
        nationalityCheckBox.addActionListener(l);
        visaRequiredCheckBox.addActionListener(l);
        submit.addActionListener(l);
        salary.addActionListener(l);
        typeOfJobPicklist.addActionListener(l);
        reset.addActionListener(l);
        cancel.addActionListener(l);
    }

    /**
     * Changes color of necessary text fields to red
     */
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

    /**
     *
     * @return label of pay group
     */
    public JLabel getPayGroupOnHiring() {
        return payGroupOnHiring;
    }

    /**
     *
     * @return label of pay level
     */
    public JLabel getPayLevelOnHiring() {
        return payLevelOnHiring;
    }

    /**
     *
     * @return label of type of payment
     */
    public JLabel getHiwiTypeOfPayment() {
        return hiwiTypeOfPayment;
    }

    /**
     *
     * @return label of nationality CheckBox
     */
    public JCheckBox getNationalityCheckBox() {
        return nationalityCheckBox;
    }

    /**
     *
     * @return label of second nationality pick list
     */
    public JLabel getNationalitySecond() {
        return nationalitySecond;
    }

    /**
     *
     * @return label of work scope
     */
    public JLabel getWorkScope() {
        return workScope;
    }

    /**
     *
     * @return ComboBox of second nationality pick list
     */
    public JComboBox getNationalityPickList2() {
        return nationalityPickList2;
    }

    /**
     *
     * @return label of vbl state
     */
    public JLabel getVblstate() {
        return vblstate;
    }

    /**
     *
     * @return CheckBox if Visa is required
     */
    public JCheckBox getVisaRequiredCheckBox() {
        return visaRequiredCheckBox;
    }

    /**
     *
     * @return label of visa valid until
     */
    public JLabel getVisaValidUntil() {
        return visaValidUntil;
    }

    /**
     *
     * @return Date Picker of visa valid until
     */
    public DatePicker getTfVisaValidUntil() {
        return tfVisaValidUntil;
    }

    /**
     *
     * @return submit button
     */
    public JButton getSubmit() {
        return submit;
    }

    /**
     *
     * @return salary entry button
     */
    public JButton getSalaryEntry() {
        return salary;
    }

    /**
     *
     * @retur reset button
     */
    public JButton getReset() {
        return reset;
    }

    /**
     *
     * @return cancel button
     */
    public JButton getCancel() {
        return cancel;
    }

    /**
     *
     * @return entered name
     */
    public JTextField getTfName() {
        return tfName;
    }

    /**
     *
     * @return entered surname
     */
    public JTextField getTfVorname() {
        return tfVorname;
    }

    /**
     *
     * @return entered house number
     */
    public JTextField getTfHausnummer() {
        return tfHausnummer;
    }

    /**
     *
     * @return entered additional address information
     */
    public JTextField getTfAdresszusatz() {
        return tfAdresszusatz;
    }

    /**
     *
     * @return entered postal code
     */
    public JTextField getTfPLZ() {
        return tfPLZ;
    }

    /**
     *
     * @return entered city
     */
    public JTextField getTfStadt() {
        return tfStadt;
    }

    /**
     *
     * @return entered street
     */
    public JTextField getTfStrasse() {
        return tfStrasse;
    }

    /**
     *
     * @return entered email address
     */
    public JTextField getTfPrivatEmail() {
        return tfPrivatEmail;
    }

    /**
     *
     * @return entered private phone number
     */
    public JTextField getTfPrivateTelefonnummer() {
        return tfPrivateTelefonnummer;
    }

    /**
     *
     * @return entered birthday
     */
    public DatePicker getTfGeburtsdatum() {
        return tfGeburtsdatum;
    }

    /**
     *
     * @return entered personal number
     */
    public JTextField getTfPersonalnummer() {
        return tfPersonalnummer;
    }

    /**
     *
     * @return entered TUID
     */
    public JTextField getTfTuid() {
        return tfTuid;
    }

    /**
     *
     * @return entered date until which the salary is planned
     */
    public DatePicker getTfSalaryPlannedUntil() {
        return tfSalaryPlannedUntil;
    }

    /**
     *
     * @return entered transponder number
     */
    public JTextField getTfTranspondernummer() {
        return tfTranspondernummer;
    }

    /**
     *
     * @return entered office number
     */
    public JTextField getTfBueronummer() {
        return tfBueronummer;
    }

    /**
     *
     * @return entered office phone number
     */
    public JTextField getTfTelefonnummerTUDA() {
        return tfTelefonnummerTUDA;
    }

    /**
     *
     * @return type of job pick list
     */
    public JComboBox getStatusPicklist() {
        return typeOfJobPicklist;
    }

    /**
     *
     * @return nationality pick list
     */
    public JComboBox getNationalityPickList() {
        return nationalityPickList;
    }

    /**
     *
     * @return date of work end
     */
    public DatePicker getTfWorkEnd() {
        return tfWorkEnd;
    }

    /**
     *
     * @return date of work start
     */
    public DatePicker getTfWorkStart() {
        return tfWorkStart;
    }

    /**
     *
     * @return type of payment pick list
     */
    public JComboBox getHiwiTypeOfPaymentList() {
        return hiwiTypeOfPaymentList;
    }

    /**
     *
     * @return type of job pick list
     */
    public JComboBox getTypeOfJobPicklist() {
        return typeOfJobPicklist;
    }

    /**
     *
     * @return entered work scope
     */
    public JTextField getTfWorkScope() {
        return tfWorkScope;
    }

    /**
     *
     * @return pay group pick list
     */
    public JComboBox getPayGroupList() {
        return payGroupList;
    }

    /**
     *
     * @return pay level pick list
     */
    public JComboBox getPayLevelList() {
        return payLevelList;
    }

    /**
     *
     * @return vbl pick list
     */
    public JComboBox getVblList() {
        return vblList;
    }

    /**
     * sets the constraints for the label
     * @param label JLabel to which constraints should be applied
     * @param rowNumber row number of the label
     */
    private void setConstraintsLabel(JLabel label, int rowNumber) {
        constraints.gridx = 0;
        constraints.gridy = rowNumber;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        centerUp.add(label, constraints);
    }

    /**
     * sets the constraints for the text field
     * @param textField JTextField to which constraints should be applied
     * @param rowNumber row number of the text field
     */
    private void setConstraintsTextField(JTextField textField, int rowNumber) {
        textField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        constraints.gridx = 1;
        constraints.gridy = rowNumber;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0;
        centerUp.add(textField, constraints);
    }

    /**
     * sets the constraints for the date picker
     * @param datePicker DatePicker to which constraints should be applied
     * @param rowNumber row number of the date picker
     */
    private void setConstraintsDatePicker(DatePicker datePicker, int rowNumber) {
        datePicker.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        constraints.gridx = 1;
        constraints.gridy = rowNumber;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0;
        centerUp.add(datePicker, constraints);
    }

    /**
     * sets the constraints for the pick list
     * @param jComboBox JComboBox to which constraints should be applied
     * @param rowNumber row number of the pick list
     */
    private void setConstraintsJComboBox(JComboBox jComboBox, int rowNumber) {
        jComboBox.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        constraints.gridx = 1;
        constraints.gridy = rowNumber;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0;
        centerUp.add(jComboBox, constraints);
    }

    /**
     * sets the constraints for the checkbox
     * @param jCheckBox JCheckBox to which constraints should be applied
     * @param rowNumber row number of the checkbox
     */
    private void setConstraintsJCheckBox(JCheckBox jCheckBox, int rowNumber) {
        constraints.gridx = 0;
        constraints.gridy = rowNumber;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        centerUp.add(jCheckBox, constraints);
    }

    /**
     * sets the constraints for the puffer. this ensures that each element is scaled vertically correct
     * @param label JLabel to which constraints should be applied
     * @param rowNumber row number of the puffer
     */
    private void setConstraintsPuffer(JLabel label, int rowNumber) {
        constraints.gridx = 0;
        constraints.gridy = rowNumber;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        centerUp.add(label, constraints);
    }
}

