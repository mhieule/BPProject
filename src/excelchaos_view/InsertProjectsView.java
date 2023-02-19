package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import excelchaos_model.utility.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;


public class InsertProjectsView extends JPanel {

    private JLabel name, approval, start, duration, puffer, categoryLabel, funderLabel, participationLabel;

    private JTextField tfName;

    private DatePicker tfApproval, tfStart, tfDuration;

    private JButton submit, reset, cancel;

    private JPanel projectValuesPanel, categoriesPanel, projectFunderPanel, projectParticipationPanel, tablePanel, buttonPanel, leftButtons, rightButtons;

    private GridBagConstraints textFieldConstraints;

    private JTable categoriesTable, projectFunderTable, projectParticipationTable;

    private final int FIELD_WIDTH = 400;
    private final int FIELD_HEIGHT = 25;

    public void init() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        textFieldConstraints = new GridBagConstraints();

        projectValuesPanel = new JPanel();
        tablePanel = new JPanel();
        categoriesPanel = new JPanel();
        projectFunderPanel = new JPanel();
        projectParticipationPanel = new JPanel();
        buttonPanel = new JPanel();

        GridBagLayout projectValuesLayout = new GridBagLayout();
        projectValuesPanel.setLayout(projectValuesLayout);


        textFieldConstraints.insets = new Insets(30, 25, 0, 50);
        textFieldConstraints.anchor = GridBagConstraints.FIRST_LINE_START;


        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        tablePanel.setLayout(new GridLayout(3, 1, 0, 20));

        add(projectValuesPanel, BorderLayout.WEST);

        setUpCategoriesPanel();
        tablePanel.add(categoriesPanel);


        setUpProjectFunderPanel();
        tablePanel.add(projectFunderPanel);

        setUpProjectParticipationPanel();
        tablePanel.add(projectParticipationPanel);

        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        name = new JLabel("Name");
        setConstraintsLabel(name, 0);
        tfName = new JTextField();
        setConstraintsTextField(tfName, 0);
        textFieldConstraints.insets.top = 5;

        approval = new JLabel("Bewilligungsdatum");
        setConstraintsLabel(approval, 1);
        tfApproval = new DatePicker();
        setConstraintsDatePicker(tfApproval, 1);

        start = new JLabel("Startdatum");
        setConstraintsLabel(start, 2);
        tfStart = new DatePicker();
        setConstraintsDatePicker(tfStart, 2);

        duration = new JLabel("Enddatum");
        setConstraintsLabel(duration, 3);
        tfDuration = new DatePicker();
        setConstraintsDatePicker(tfDuration, 3);

        puffer = new JLabel(" ");
        setConstraintsPuffer(puffer, 4);

        leftButtons = new JPanel(new FlowLayout());
        rightButtons = new JPanel(new FlowLayout());
        submit = new JButton("Projekt speichern");
        leftButtons.add(submit);
        buttonPanel.add(leftButtons);
        reset = new JButton("Felder zurücksetzen");
        cancel = new JButton("Abbrechen");
        buttonPanel.add(Box.createHorizontalGlue());
        rightButtons.add(reset);
        rightButtons.add(cancel);
        buttonPanel.add(rightButtons);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));


        revalidate();
        repaint();
    }

    public void setUpCategoriesPanel() {
        categoriesPanel.setLayout(new BorderLayout());
        categoryLabel = new JLabel("Kategorien");
        categoryLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        String columns[] = {"Name", "Bewilligte Mittel"};
        DefaultTableModel categoriesModel = new DefaultTableModel(null, columns);
        categoriesModel.setRowCount(40);
        categoriesTable = new JTable(categoriesModel);
        JScrollPane categoriesScrollpane = new JScrollPane(categoriesTable);
        categoriesScrollpane.setVisible(true);
        categoriesPanel.add(categoriesScrollpane, BorderLayout.CENTER);
        categoriesPanel.add(categoryLabel, BorderLayout.NORTH);

    }

    public void setUpProjectFunderPanel() {
        projectFunderPanel.setLayout(new BorderLayout());
        funderLabel = new JLabel("Projektträger");
        funderLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        String columns[] = {"Name des Projektträgers", "Förderkennzeichen", "Projektnummer der TU Darmstadt"};
        DefaultTableModel funderModel = new DefaultTableModel(null, columns);
        funderModel.setRowCount(40);
        projectFunderTable = new JTable(funderModel);
        JScrollPane funderScrollpane = new JScrollPane(projectFunderTable);
        funderScrollpane.setVisible(true);
        projectFunderPanel.add(funderScrollpane, BorderLayout.CENTER);
        projectFunderPanel.add(funderLabel, BorderLayout.NORTH);
    }

    public void setUpProjectParticipationPanel() {
        projectParticipationPanel.setLayout(new BorderLayout());
        participationLabel = new JLabel("Mitarbeiter");
        participationLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        String columns[] = {"Name", "Beschäftigungsumfang", "Datum"};
        DefaultTableModel participationModel = new DefaultTableModel(null, columns);
        participationModel.setRowCount(40);
        projectParticipationTable = new JTable(participationModel);
        JScrollPane participationScrollpane = new JScrollPane(projectParticipationTable);
        participationScrollpane.setVisible(true);
        projectParticipationPanel.add(participationScrollpane, BorderLayout.CENTER);
        projectParticipationPanel.add(participationLabel, BorderLayout.NORTH);
    }

    public void setActionListener(ActionListener l) {
        submit.addActionListener(l);
        reset.addActionListener(l);
        cancel.addActionListener(l);
    }

    public JButton getSubmit() {
        return submit;
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

    public DatePicker getTfDuration() {
        return tfDuration;
    }

    public DatePicker getTfApproval() {
        return tfApproval;
    }

    public DatePicker getTfStart() {
        return tfStart;
    }

    public JTable getCategoriesTable() {
        return categoriesTable;
    }

    public JTable getProjectFunderTable() {
        return projectFunderTable;
    }

    public JTable getProjectParticipationTable() {
        return projectParticipationTable;
    }

    private void setConstraintsLabel(JLabel label, int rowNumber) {
        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = rowNumber;
        textFieldConstraints.gridwidth = 1;
        textFieldConstraints.weightx = 0.0;
        projectValuesPanel.add(label, textFieldConstraints);
    }

    private void setConstraintsTextField(JTextField textField, int rowNumber) {
        textField.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        textFieldConstraints.gridx = 1;
        textFieldConstraints.gridy = rowNumber;
        textFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
        textFieldConstraints.weightx = 1.0;
        projectValuesPanel.add(textField, textFieldConstraints);
    }

    private void setConstraintsDatePicker(DatePicker datePicker, int rowNumber) {
        datePicker.setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        textFieldConstraints.gridx = 1;
        textFieldConstraints.gridy = rowNumber;
        textFieldConstraints.gridwidth = GridBagConstraints.REMAINDER;
        textFieldConstraints.weightx = 1.0;
        projectValuesPanel.add(datePicker, textFieldConstraints);
    }

    private void setConstraintsPuffer(JLabel label, int rowNumber) {
        textFieldConstraints.gridx = 0;
        textFieldConstraints.gridy = rowNumber;
        textFieldConstraints.gridwidth = 1;
        textFieldConstraints.weightx = 0.0;
        textFieldConstraints.weighty = 1.0;
        projectValuesPanel.add(label, textFieldConstraints);
    }
}

