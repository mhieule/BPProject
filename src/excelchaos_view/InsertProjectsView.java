package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class InsertProjectsView extends JPanel {

    private JLabel name, approval, start, duration, puffer;

    private JTextField tfName, tfDuration;

    private DatePicker tfApproval, tfStart;

    private JButton submit, reset;

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

        name = new JLabel("Name");
        setConstraintsLabel(name, 0);
        tfName = new JTextField();
        setConstraintsTextField(tfName, 0);
        constraints.insets.top = 5;

        approval = new JLabel("Bewilligungsdatum");
        setConstraintsLabel(approval, 1);
        tfApproval = new DatePicker();
        setConstraintsDatePicker(tfApproval, 1);

        start = new JLabel("Startdatum");
        setConstraintsLabel(start, 2);
        tfStart = new DatePicker();
        setConstraintsDatePicker(tfStart, 2);

        duration = new JLabel("Laufzeit");
        setConstraintsLabel(duration, 3);
        tfDuration = new JTextField();
        setConstraintsTextField(tfDuration, 3);

        puffer = new JLabel(" ");
        setConstraintsPuffer(puffer,4);

        leftButtons = new JPanel(new FlowLayout());
        rightButtons = new JPanel(new FlowLayout());
        submit = new JButton("Person speichern");
        leftButtons.add(submit);
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
        submit.addActionListener(l);
        reset.addActionListener(l);
    }

    public JButton getSubmit() {
        return submit;
    }

    public JButton getReset() {
        return reset;
    }

    public JTextField getTfName() {
        return tfName;
    }

    public JTextField getTfDuration() {
        return tfDuration;
    }

    public DatePicker getTfApproval() {
        return tfApproval;
    }

    public DatePicker getTfStart() {
        return tfStart;
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
}

