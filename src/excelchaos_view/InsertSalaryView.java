package excelchaos_view;

import excelchaos_model.EmployeeDataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class InsertSalaryView extends JPanel {

    private JLabel nameList, gruppe, stufe, gehalt, sonderzahlung, puffer;

    private JTextField  tfGehalt, tfSonderzahlung;

    private JComboBox namePickList, plStufe,tfGruppe;

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

        centerDown.setLayout(new GridLayout());
        add(centerUp, BorderLayout.CENTER);
        add(centerDown, BorderLayout.SOUTH);

        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        String[] names = employeeDataManager.getAllEmployeesNameList();

        nameList = new JLabel("Name");
        setConstraintsLabel(nameList, 0);
        namePickList = new JComboBox(names);
        namePickList.setMaximumRowCount(10);
        setConstraintsJComboBox(namePickList, 0);
        constraints.insets.top = 5;

        gruppe = new JLabel("Gehaltsklasse");
        setConstraintsLabel(gruppe, 1);
        String[] payGrade = {"Nicht ausgewählt","E13","E14"};
        tfGruppe = new JComboBox(payGrade);
        setConstraintsJComboBox(tfGruppe, 1);

        stufe = new JLabel("Gehaltsstufe");
        setConstraintsLabel(stufe, 2);
        String[] payLevel = {"Nicht ausgewählt", "1A","1B","1","2","3","4","5","6"};
        plStufe = new JComboBox(payLevel);
        setConstraintsJComboBox(plStufe,2);

        gehalt = new JLabel("Gehalt");
        setConstraintsLabel(gehalt, 3);
        tfGehalt = new JTextField();
        setConstraintsTextField(tfGehalt, 3);

        sonderzahlung = new JLabel("Sonderzahlung");
        setConstraintsLabel(sonderzahlung, 4);
        tfSonderzahlung = new JTextField();
        setConstraintsTextField(tfSonderzahlung, 4);

        puffer = new JLabel(" ");
        setConstraintsPuffer(puffer,5);


        leftButtons = new JPanel(new FlowLayout());
        rightButtons = new JPanel(new FlowLayout());
        submit = new JButton("Gehaltseintrag speichern");
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


    public JLabel getGehalt() {
        return gehalt;
    }

    public JLabel getGruppe(){
        return gruppe;
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

    public JComboBox getNamePickList() {
        return namePickList;
    }

    public JTextField getTfGehalt() {
        return tfGehalt;
    }

    public JTextField getTfSonderzahlung() {
        return tfSonderzahlung;
    }

    public JComboBox getTfGruppe() {
        return tfGruppe;
    }

    public JComboBox getPlStufe() {
        return plStufe;
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

    private void setConstraintsJComboBox(JComboBox jComboBox, int rowNumber) {
        jComboBox.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        constraints.gridx = 1;
        constraints.gridy = rowNumber;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1.0;
        centerUp.add(jComboBox, constraints);
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


