package excelchaos_view;

import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.inputVerifier.SalaryVerifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;


public class InsertSalarySHKView extends JPanel {

    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();

    private JLabel nameList, hiwiTypeOfPayment, salary, extraCost, puffer;

    private JTextField tfSalary, tfExtraCost;

    private JComboBox namePickList, tfHiwiTypeOfPayment;

    private JButton submit, reset, cancel;

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


        String[] names = employeeDataManager.getAllEmployeesNameList();

        nameList = new JLabel("Name");
        setConstraintsLabel(nameList, 0);
        namePickList = new JComboBox(names);
        namePickList.setMaximumRowCount(10);
        setConstraintsJComboBox(namePickList, 0);
        constraints.insets.top = 5;

        hiwiTypeOfPayment = new JLabel("Stundensatz");
        setConstraintsLabel(hiwiTypeOfPayment, 1);
        String[] hiwiPaymentArray = {"Nicht ausgewählt", "SHK Basisvergütung", "SHK erhöhter Stundensatz", "WHK"};
        tfHiwiTypeOfPayment = new JComboBox(hiwiPaymentArray);
        setConstraintsJComboBox(tfHiwiTypeOfPayment, 1);

        salary = new JLabel("Gehalt");
        setConstraintsLabel(salary, 2);
        tfSalary = new JTextField();
        setConstraintsTextField(tfSalary, 2);
        tfSalary.setInputVerifier(new SalaryVerifier());

        extraCost = new JLabel("Jahressonderzahlung");
        setConstraintsLabel(extraCost, 3);
        tfExtraCost = new JTextField();
        setConstraintsTextField(tfExtraCost, 3);
        tfExtraCost.setInputVerifier(new SalaryVerifier());

        puffer = new JLabel(" ");
        setConstraintsPuffer(puffer, 4);


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

    public void setItemListener(ItemListener l) {
        tfHiwiTypeOfPayment.addItemListener(l);
    }

    public void markMustBeFilledTextFields() {
        hiwiTypeOfPayment.setForeground(Color.RED);
    }


    public JLabel getSalary() {
        return salary;
    }

    public JLabel getHiwiTypeOfPayment() {
        return hiwiTypeOfPayment;
    }

    public JLabel getExtraCost() {
        return extraCost;
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

    public JTextField getTfSalary() {
        return tfSalary;
    }

    public JTextField getTfExtraCost() {
        return tfExtraCost;
    }

    public JComboBox getTfHiwiTypeOfPayment() {
        return tfHiwiTypeOfPayment;
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


