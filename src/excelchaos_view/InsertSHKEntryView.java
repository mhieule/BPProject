package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import excelchaos_model.inputVerifier.SalaryVerifier;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

public class InsertSHKEntryView extends JPanel {
    private JLabel dateLabel, basePayRateLabel, extendedPayRateLabel, WHKPayRateLabel, puffer;

    private JTextField basePayRateTextfield, extendedPayRateTextField, WHKPayRateTextfield;

    private DatePicker datePicker;

    private JPanel centerUp, centerDown, leftButtons, rightButtons;

    private JButton submit, submitAndClose, reset, cancel;

    private GridBagConstraints constraints;

    private final int FIELD_WIDTH = 400;
    private final int FIELD_HEIGHT = 25;

    public void init() {
        setLayout(new BorderLayout());
        centerUp = new JPanel();
        centerDown = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        centerUp.setLayout(gridBagLayout);

        constraints = new GridBagConstraints();
        constraints.insets = new Insets(30, 25, 0, 50);
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;

        centerDown.setLayout(new WrapLayout(FlowLayout.LEFT));
        add(centerUp, BorderLayout.CENTER);
        add(centerDown, BorderLayout.SOUTH);

        dateLabel = new JLabel("Stundensätze gültig ab");
        setConstraintsLabel(dateLabel, 0);
        datePicker = new DatePicker();
        LocalDate localDate = LocalDate.of(Year.now().getValue(), YearMonth.now().getMonth().getValue(), 1);
        datePicker.setDate(localDate);
        setConstraintsDatePicker(datePicker, 0);

        basePayRateLabel = new JLabel("SHK Basisvergütung");
        setConstraintsLabel(basePayRateLabel, 1);
        basePayRateTextfield = new JTextField();
        setConstraintsTextField(basePayRateTextfield, 1);
        basePayRateTextfield.setInputVerifier(new SalaryVerifier());

        extendedPayRateLabel = new JLabel("SHK erhöhter Stundensatz");
        setConstraintsLabel(extendedPayRateLabel, 2);
        extendedPayRateTextField = new JTextField();
        setConstraintsTextField(extendedPayRateTextField, 2);
        basePayRateTextfield.setInputVerifier(new SalaryVerifier());

        WHKPayRateLabel = new JLabel("WHK");
        setConstraintsLabel(WHKPayRateLabel, 3);
        WHKPayRateTextfield = new JTextField();
        setConstraintsTextField(WHKPayRateTextfield, 3);
        basePayRateTextfield.setInputVerifier(new SalaryVerifier());

        puffer = new JLabel(" ");
        setConstraintsPuffer(puffer, 4);

        leftButtons = new JPanel(new FlowLayout());
        rightButtons = new JPanel(new FlowLayout());

        submit = new JButton("SHK Stundensatzeintrag speichern und Felder zurücksetzen");
        submitAndClose = new JButton("SHK Stundensatzeintrag speichern und schließen");
        leftButtons.setLayout(new FlowLayout());
        leftButtons.add(submit);
        leftButtons.add(submitAndClose);
        centerDown.add(leftButtons);
        reset = new JButton("Felder zurücksetzen");
        cancel = new JButton("Abbrechen");
        centerDown.add(Box.createHorizontalStrut(100));
        rightButtons.add(reset);
        rightButtons.add(cancel);
        centerDown.add(rightButtons);

        JScrollPane scrollPane = new JScrollPane(centerUp, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        revalidate();
        repaint();

    }

    public void setActionListener(ActionListener l){
        submit.addActionListener(l);
        reset.addActionListener(l);
        cancel.addActionListener(l);
        submitAndClose.addActionListener(l);
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

    private void setConstraintsPuffer(JLabel label, int rowNumber) {
        constraints.gridx = 0;
        constraints.gridy = rowNumber;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
        centerUp.add(label, constraints);
    }

    public JButton getCancel() {
        return cancel;
    }

    public JButton getSubmitAndClose() {
        return submitAndClose;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public JButton getReset() {
        return reset;
    }

    public JButton getSubmit() {
        return submit;
    }

    public JTextField getBasePayRateTextfield() {
        return basePayRateTextfield;
    }

    public JTextField getExtendedPayRateTextField() {
        return extendedPayRateTextField;
    }

    public JTextField getWHKPayRateTextfield() {
        return WHKPayRateTextfield;
    }
}
