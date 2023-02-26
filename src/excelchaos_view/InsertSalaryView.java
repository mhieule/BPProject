package excelchaos_view;

import excelchaos_model.EmployeeDataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;


public class InsertSalaryView extends JPanel {

    private JLabel nameList, gruppe, stufe, gehalt, sonderzahlung,vblstate, puffer;

    private JTextField  tfGehalt, tfSonderzahlung;

    private JComboBox namePickList, plStufe,tfGruppe,vblList;

    private JButton submit, reset ,cancel;

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

        vblstate = new JLabel("VBL-Status");
        setConstraintsLabel(vblstate,3);
        String[] vbl = {"Nicht ausgewählt","Pflichtig","Befreit"};
        vblList = new JComboBox(vbl);
        setConstraintsJComboBox(vblList,3);

        gehalt = new JLabel("Gehalt");
        setConstraintsLabel(gehalt, 4);
        tfGehalt = new JTextField();
        setConstraintsTextField(tfGehalt, 4);

        sonderzahlung = new JLabel("Jahressonderzahlung");
        setConstraintsLabel(sonderzahlung, 5);
        tfSonderzahlung = new JTextField();
        setConstraintsTextField(tfSonderzahlung, 5);

        puffer = new JLabel(" ");
        setConstraintsPuffer(puffer,6);


        leftButtons = new JPanel(new FlowLayout());
        rightButtons = new JPanel(new FlowLayout());
        submit = new JButton("Gehaltseintrag speichern und Verlassen");
        leftButtons.add(submit);
        centerDown.add(leftButtons);
        cancel = new JButton("Abbrechen");
        reset = new JButton("Felder zurücksetzen");
        centerDown.add(Box.createHorizontalGlue());
        rightButtons.add(reset);
        rightButtons.add(cancel);
        centerDown.add(rightButtons);

        JScrollPane scrollPane = new JScrollPane(centerUp, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        revalidate();
        repaint();
    }

    public void setActionListener(ActionListener l) {
        submit.addActionListener(l);
        reset.addActionListener(l);
        cancel.addActionListener(l);
    }

    public void setItemListener(ItemListener l){
        vblList.addItemListener(l);
        plStufe.addItemListener(l);
        tfGruppe.addItemListener(l);
    }

    public void markMustBeFilledTextFields(){
        gruppe.setForeground(Color.RED);
        stufe.setForeground(Color.RED);
        vblstate.setForeground(Color.RED);
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

    public JButton getCancel() {
        return cancel;
    }

    public JButton getSubmit() {
        return submit;
    }

    public JComboBox getNamePickList() {
        return namePickList;
    }

    public JComboBox getVblList() {
        return vblList;
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


