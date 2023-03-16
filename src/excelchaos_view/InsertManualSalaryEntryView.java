package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.inputVerifier.SalaryVerifier;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.*;

/**
 * This class represents the manual salary insertion. It is a subsclass of JPanel
 */
public class InsertManualSalaryEntryView extends JPanel {
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private JLabel nameList, newSalaryLabel, usageDateLabel, commentLabel, puffer;

    private JTextField tfNewSalary, tfComment;

    private DatePicker datePicker;

    private JComboBox namePickList;

    private JButton submit, submitAndClose, reset, cancel;

    private JPanel centerUp, centerDown, leftButtons, rightButtons;

    private GridBagConstraints constraints;

    private final int FIELD_WIDTH = 400;
    private final int FIELD_HEIGHT = 25;

    /**
     * Initialize by adding all of the components to the view, organising these components with constraint methods and add layout for the view
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

        centerDown.setLayout(new WrapLayout(FlowLayout.LEFT));
        add(centerUp, BorderLayout.CENTER);
        add(centerDown, BorderLayout.SOUTH);

        String[] names = employeeDataManager.getAllEmployeesNameList();

        nameList = new JLabel("Name");
        setConstraintsLabel(nameList, 0);
        namePickList = new JComboBox(names);
        namePickList.setMaximumRowCount(10);
        namePickList.setBackground(Color.WHITE);
        setConstraintsJComboBox(namePickList, 0);
        constraints.insets.top = 5;

        usageDateLabel = new JLabel("Gültig ab");
        setConstraintsLabel(usageDateLabel, 1);
        datePicker = new DatePicker();
        LocalDate localDate = LocalDate.of(Year.now().getValue(), YearMonth.now().getMonth().getValue(), 1);
        datePicker.setDate(localDate);
        setConstraintsDatePicker(datePicker, 1);

        newSalaryLabel = new JLabel("Neuer Gehaltseintrag");
        setConstraintsLabel(newSalaryLabel, 2);
        tfNewSalary = new JTextField();
        setConstraintsTextField(tfNewSalary, 2);
        tfNewSalary.setInputVerifier(new SalaryVerifier());


        commentLabel = new JLabel("Kommentar");
        setConstraintsLabel(commentLabel, 3);
        tfComment = new JTextField();
        setConstraintsTextField(tfComment, 3);


        puffer = new JLabel(" ");
        setConstraintsPuffer(puffer, 5);


        leftButtons = new JPanel(new FlowLayout());
        rightButtons = new JPanel(new FlowLayout());
        submit = new JButton("Gehaltseintrag speichern und Felder zurücksetzen");
        submitAndClose = new JButton("Gehaltseintrag speichern und schließen");
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

    /**
     * Add same action listener to the buttons submit, reset, cancel, submit and close, allowing them to share the same controller class to handle action events
     * @param l action listener to set
     */
    public void setActionListener(ActionListener l) {
        submit.addActionListener(l);
        reset.addActionListener(l);
        cancel.addActionListener(l);
        submitAndClose.addActionListener(l);
    }

    /**
     * @return the cancel button
     */
    public JButton getCancel() {
        return cancel;
    }
    /**
     * @return the submit and close button
     */
    public JButton getSubmitAndClose() {
        return submitAndClose;
    }
    /**
     * @return the comment label
     */
    public JLabel getCommentLabel() {
        return commentLabel;
    }
    /**
     * @return the new salary label
     */
    public JLabel getNewSalaryLabel() {
        return newSalaryLabel;
    }
    /**
     * @return the datepicker component
     */
    public DatePicker getDatePicker() {
        return datePicker;
    }
    /**
     * @return the usage date label component
     */
    public JLabel getUsageDateLabel() {
        return usageDateLabel;
    }
    /**
     * @return the reset button component
     */
    public JButton getReset() {
        return reset;
    }
    /**
     * @return the submit button component
     */
    public JButton getSubmit() {
        return submit;
    }
    /**
     * @return the combobox to choose employee's name
     */
    public JComboBox getNamePickList() {
        return namePickList;
    }
    /**
     * @return the textfield which contains the comment
     */
    public JTextField getTfComment() {
        return tfComment;
    }

    /**
     * @return the textfield which contains the new salary
     */
    public JTextField getTfNewSalary() {
        return tfNewSalary;
    }

    /**
     * Set constrain for label
     * @param label whose position needs to be change
     * @param rowNumber indicating the gridy in the constrained Gridbag Layout
     */
    private void setConstraintsLabel(JLabel label, int rowNumber) {
        constraints.gridx = 0;
        constraints.gridy = rowNumber;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        centerUp.add(label, constraints);
    }
    /**
     * Set constrain for text field
     * @param textField whose position needs to be change
     * @param rowNumber indicating the gridy in the constrained Gridbag Layout
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
     * Set constrain for JComboBox
     * @param jComboBox whose position needs to be change
     * @param rowNumber indicating the gridy in the constrained Gridbag Layout
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
     * Set constrain for DatePicker
     * @param datePicker whose position needs to be change
     * @param rowNumber indicating the gridy in the constrained Gridbag Layout
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
     * Set constrain for puffer label
     * @param label whose position needs to be change
     * @param rowNumber indicating the gridy in the constrained Gridbag Layout
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
