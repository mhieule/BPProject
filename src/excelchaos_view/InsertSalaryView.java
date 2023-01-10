package excelchaos_view;

import excelchaos_model.CountryModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class InsertSalaryView extends JPanel {

    private JLabel status, stufe, gehalt, sonderzahlung;

    private JTextField tfStatus, tfStufe, tfGehalt, tfSonderzahlung;

    // private JComboBox;

    private JButton submit, reset;

    private JPanel centerUp, centerDown;

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

        centerDown.setLayout(new GridLayout());
        add(centerUp, BorderLayout.CENTER);
        add(centerDown, BorderLayout.SOUTH);

        status = new JLabel("Status");
        setConstraintsLabel(status, 0);
        tfStatus = new JTextField();
        setConstraintsTextField(tfStatus, 0);
        constraints.insets.top = 5;

        stufe = new JLabel("Stufe");
        setConstraintsLabel(stufe, 1);
        tfStufe = new JTextField();
        setConstraintsTextField(tfStufe, 1);

        gehalt = new JLabel("Gehalt");
        setConstraintsLabel(gehalt, 2);
        tfGehalt = new JTextField();
        setConstraintsTextField(tfGehalt, 2);


        sonderzahlung = new JLabel("Sonderzahlung");
        setConstraintsLabel(sonderzahlung, 3);
        tfSonderzahlung = new JTextField();
        setConstraintsTextField(tfSonderzahlung, 3);


        submit = new JButton("Gehaltseintrag speichern");
        centerDown.add(submit);
        reset = new JButton("Felder zurücksetzen");
        centerDown.add(reset);

        JScrollPane scrollPane = new JScrollPane(centerUp, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        revalidate();
        repaint();
    }

    public void setActionListener(ActionListener l) {
        submit.addActionListener(l);
    }

    public JLabel getGehalt() {
        return gehalt;
    }

    public JLabel getStatus(){
        return status;
    }

    public JLabel getSonderzahlung() {
        return sonderzahlung;
    }

    public JLabel getStufe(){
        return stufe;
    }

    public JButton getReset() {
        return reset;
    }

    public JButton getSubmit() {
        return submit;
    }

    private void setConstraintsLabel(JLabel label, int rowNumber) {
        constraints.gridx = 0;
        constraints.gridy = rowNumber;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        constraints.weighty = 1.0;
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
}


