package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.tableeditors.DateTableEditor;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.inputVerifier.SalaryVerifier;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class InsertProjectsView extends JPanel {
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private JLabel name, approval, start, duration, categoryLabel, categoriesSum, funderLabel, participationLabel;

    private JTextField tfName;

    private DatePicker tfApproval, tfStart, tfDuration;

    private String[] categoryColumns, funderColumns, participationColumns;

    private String[] categoryColumnWithID, funderColumnWithID, participationColumnsWithID;

    private JButton submitAndReset, submitAndClose, reset, cancel;

    private JPanel flowProjectValuesPanel, mainPanel, mixedPanel, projectValuesPanel, categoriesPanel, categoriesSumPanel, projectFunderPanel, projectParticipationPanel, buttonPanel, leftButtons, rightButtons;

    private GridBagConstraints textFieldConstraints;

    private JTable categoriesTable, projectFunderTable, projectParticipationTable;

    private final int FIELD_WIDTH = 400;
    private final int FIELD_HEIGHT = 25;

    /**
     * Initializes the GUI components and adds them to the panel.
     */
    public void init() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        categoryColumnWithID = new String[]{"CategoryID", "ProjektID", "Name", "Bewilligte Mittel"};

        funderColumnWithID = new String[]{"FunderID", "ProjektID", "Name des Projektträgers", "Förderkennzeichen", "Projektnummer der TU Darmstadt"};

        participationColumnsWithID = new String[]{"ProjektID", "Name", "Beschäftigungsumfang", "Projektmitarbeitsbeginn", "Projektmitarbeitsende"};

        textFieldConstraints = new GridBagConstraints();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(30, 0));

        mixedPanel = new JPanel();
        mixedPanel.setLayout(new GridLayout(3, 1, 0, 20));

        projectParticipationPanel = new JPanel();
        projectValuesPanel = new JPanel();
        flowProjectValuesPanel = new JPanel();
        categoriesPanel = new JPanel();
        categoriesSumPanel = new JPanel();
        projectFunderPanel = new JPanel();
        buttonPanel = new JPanel();

        textFieldConstraints.insets = new Insets(30, 25, 0, 50);
        textFieldConstraints.anchor = GridBagConstraints.FIRST_LINE_START;

        buttonPanel.setLayout(new WrapLayout(FlowLayout.LEFT));

        add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);

        setUpProjectValuesPanel();
        mixedPanel.add(flowProjectValuesPanel);
        setUpCategoriesPanel();
        mixedPanel.add(categoriesPanel);
        setUpCategorySumLabel();
        setUpProjectFunderPanel();
        mixedPanel.add(projectFunderPanel);

        setUpProjectParticipationPanel();
        mainPanel.add(mixedPanel, BorderLayout.WEST);
        mainPanel.add(projectParticipationPanel, BorderLayout.CENTER);

        leftButtons = new JPanel(new FlowLayout());
        rightButtons = new JPanel(new FlowLayout());
        submitAndReset = new JButton("Projekt speichern und Felder zurücksetzen");
        submitAndClose = new JButton("Projekt speichern und Verlassen");
        leftButtons.add(submitAndReset);
        leftButtons.add(submitAndClose);
        buttonPanel.add(leftButtons);
        reset = new JButton("Felder zurücksetzen");
        cancel = new JButton("Abbrechen");
        buttonPanel.add(Box.createHorizontalStrut(100));
        rightButtons.add(reset);
        rightButtons.add(cancel);
        buttonPanel.add(rightButtons);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVisible(true);
        add(scrollPane);

        revalidate();
        repaint();
    }

    /**
     * Sets the color of the text fields to red.
     */
    public void markMustBeFilledTextFields() {
        name.setForeground(Color.RED);
        approval.setForeground(Color.RED);
        start.setForeground(Color.RED);
        duration.setForeground(Color.RED);
    }

    /**
     * Sets up the project values panel.
     */
    public void setUpProjectValuesPanel() {
        flowProjectValuesPanel.setLayout(new FlowLayout());
        projectValuesPanel.setLayout(new GridLayout(4, 2, 0, 10));

        name = new JLabel("Projektname");
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

        flowProjectValuesPanel.add(projectValuesPanel);
    }

    /**
     * Sets up the categories panel.
     */
    public void setUpCategoriesPanel() {
        categoriesPanel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        categoryLabel = new JLabel("Kategorien");
        categoryLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        categoryColumns = new String[]{"Name", "Bewilligte Mittel"};
        DefaultTableModel categoriesModel = new DefaultTableModel(null, categoryColumns);
        categoriesModel.setRowCount(4);
        categoriesTable = new JTable(categoriesModel);
        categoriesTable.getColumn("Bewilligte Mittel").setCellEditor(new CellEditor(new SalaryVerifier()));
        categoriesTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        categoriesTable.getTableHeader().setReorderingAllowed(false);
        categoriesTable.setRowHeight(26);
        categoriesTable.setValueAt("WiMi", 0, 0);
        categoriesTable.setValueAt("HiWi", 1, 0);
        categoriesTable.setValueAt("Reise Inland", 2, 0);
        categoriesTable.setValueAt("Reise Ausland", 3, 0);
        JScrollPane categoriesScrollpane = new JScrollPane(categoriesTable);
        categoriesScrollpane.setVisible(true);
        categoriesScrollpane.setPreferredSize(new Dimension(categoriesTable.getWidth(), categoriesTable.getRowHeight() * categoriesTable.getRowCount() + 2));
        categoriesPanel.add(categoriesScrollpane, BorderLayout.CENTER);
        northPanel.add(categoryLabel);
        northPanel.add(Box.createHorizontalStrut(14));
        northPanel.add(Box.createHorizontalStrut(15));
        JButton addRowButton = new JButton("Neue Zeile hinzufügen");
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] emptyString = new String[2];
                emptyString[0] = null;
                emptyString[1] = null;
                categoriesModel.addRow(emptyString);
            }
        });
        northPanel.add(addRowButton);
        categoriesPanel.add(northPanel, BorderLayout.NORTH);
    }

    /**
     * Sets up the Panel for the sum label of the categories.
     */
    public void setUpCategorySumLabel() {
        categoriesSumPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        categoriesSum = new JLabel("Summe: ");
        categoriesSumPanel.add(categoriesSum);
        categoriesPanel.add(categoriesSumPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets up the edit categories panel.
     *
     * @param data the data to be displayed in the table.
     */
    public void setUpEditCategoriesPanel(String[][] data) {
        categoriesPanel.removeAll();
        categoriesSumPanel.removeAll();
        categoriesPanel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        categoryLabel = new JLabel("Kategorien");
        categoryLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        DefaultTableModel categoriesModel = new DefaultTableModel(null, categoryColumnWithID);
        //categoriesModel.setRowCount(10);
        categoriesTable = new JTable(categoriesModel);
        categoriesTable.getColumn("Bewilligte Mittel").setCellEditor(new CellEditor(new SalaryVerifier()));
        categoriesTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        categoriesTable.getTableHeader().setReorderingAllowed(false);
        categoriesTable.setRowHeight(26);
        categoriesModel.setDataVector(data, categoryColumnWithID);
        categoriesTable.setModel(categoriesModel);
        JScrollPane categoriesScrollpane = new JScrollPane(categoriesTable);
        categoriesScrollpane.setVisible(true);
        categoriesScrollpane.setPreferredSize(new Dimension(categoriesTable.getWidth(), categoriesTable.getRowHeight() * categoriesTable.getRowCount() + 2));
        categoriesPanel.add(categoriesScrollpane, BorderLayout.CENTER);
        northPanel.add(categoryLabel);
        northPanel.add(Box.createHorizontalStrut(14));
        northPanel.add(Box.createHorizontalStrut(15));
        JButton addRowButton = new JButton("Neue Zeile hinzufügen");
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] emptyString = new String[4];
                emptyString[0] = null;
                emptyString[1] = null;
                emptyString[2] = null;
                emptyString[3] = null;
                categoriesModel.addRow(emptyString);
            }
        });
        northPanel.add(addRowButton);
        categoriesPanel.add(northPanel, BorderLayout.NORTH);
        setUpCategorySumLabel();
    }

    /**
     * Sets up the project funder panel.
     */
    public void setUpProjectFunderPanel() {
        projectFunderPanel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        funderLabel = new JLabel("Projektträger");
        funderLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        funderColumns = new String[]{"Name des Projektträgers", "Förderkennzeichen", "Projektnummer der TU Darmstadt"};
        DefaultTableModel funderModel = new DefaultTableModel(null, funderColumns);
        funderModel.setRowCount(1);
        projectFunderTable = new JTable(funderModel);
        projectFunderTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        projectFunderTable.getTableHeader().setReorderingAllowed(false);
        projectFunderTable.setRowHeight(26);
        JScrollPane funderScrollpane = new JScrollPane(projectFunderTable);
        funderScrollpane.setVisible(true);
        funderScrollpane.setPreferredSize(new Dimension(projectFunderTable.getWidth(), projectFunderTable.getRowHeight() * projectFunderTable.getRowCount() + 2));
        projectFunderPanel.add(funderScrollpane, BorderLayout.CENTER);
        northPanel.add(funderLabel);
        northPanel.add(Box.createHorizontalStrut(6));
        northPanel.add(Box.createHorizontalStrut(15));
        JButton addRowButton = new JButton("Neue Zeile hinzufügen");
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] emptyString = new String[3];
                emptyString[0] = null;
                emptyString[1] = null;
                emptyString[2] = null;
                funderModel.addRow(emptyString);
            }
        });
        northPanel.add(addRowButton);
        projectFunderPanel.add(northPanel, BorderLayout.NORTH);
    }

    /**
     * Sets up the edit project funder panel.
     *
     * @param data the data to be displayed in the table.
     */
    public void setUpEditProjectFunderPanel(String[][] data) {
        projectFunderPanel.removeAll();
        projectFunderPanel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        funderLabel = new JLabel("Projektträger");
        funderLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        DefaultTableModel funderModel = new DefaultTableModel(null, funderColumnWithID);
        //funderModel.setRowCount(10);
        projectFunderTable = new JTable(funderModel);
        projectFunderTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        projectFunderTable.getTableHeader().setReorderingAllowed(false);
        projectFunderTable.setRowHeight(26);
        funderModel.setDataVector(data, funderColumnWithID);
        projectFunderTable.setModel(funderModel);
        JScrollPane funderScrollpane = new JScrollPane(projectFunderTable);
        funderScrollpane.setVisible(true);
        funderScrollpane.setPreferredSize(new Dimension(projectFunderTable.getWidth(), projectFunderTable.getRowHeight() * projectFunderTable.getRowCount() + 2));
        projectFunderPanel.add(funderScrollpane, BorderLayout.CENTER);
        northPanel.add(funderLabel);
        northPanel.add(Box.createHorizontalStrut(6));
        northPanel.add(Box.createHorizontalStrut(15));
        JButton addRowButton = new JButton("Neue Zeile hinzufügen");
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] emptyString = new String[5];
                emptyString[0] = null;
                emptyString[1] = null;
                emptyString[2] = null;
                emptyString[3] = null;
                emptyString[4] = null;
                funderModel.addRow(emptyString);
            }
        });
        northPanel.add(addRowButton);
        projectFunderPanel.add(northPanel, BorderLayout.NORTH);
    }

    /**
     * Sets up the project participation panel.
     */
    public void setUpProjectParticipationPanel() {
        projectParticipationPanel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        participationLabel = new JLabel("Mitarbeiter*innen");
        participationLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        participationColumns = new String[]{"Name", "Beschäftigungsumfang", "Projektmitarbeitsbeginn", "Projektmitarbeitsende"};
        DefaultTableModel participationModel = new DefaultTableModel(null, participationColumns);
        participationModel.setRowCount(10);
        projectParticipationTable = new JTable(participationModel);
        projectParticipationTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        projectParticipationTable.getTableHeader().setReorderingAllowed(false);
        setUpNameSelection(projectParticipationTable);
        setUpDateSelection(projectParticipationTable);
        JScrollPane participationScrollpane = new JScrollPane(projectParticipationTable);
        participationScrollpane.setVisible(true);
        projectParticipationPanel.add(participationScrollpane, BorderLayout.CENTER);
        northPanel.add(participationLabel);
        northPanel.add(Box.createHorizontalStrut(16));
        northPanel.add(Box.createHorizontalStrut(15));
        JButton addRowButton = new JButton("Neue Zeile hinzufügen");
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] emptyString = new String[3];
                emptyString[0] = null;
                emptyString[1] = null;
                emptyString[2] = null;
                participationModel.addRow(emptyString);
            }
        });
        northPanel.add(addRowButton);
        projectParticipationPanel.add(northPanel, BorderLayout.NORTH);
    }

    /**
     * Sets up the edit project participation panel.
     *
     * @param data the data to be displayed in the table.
     */
    public void setUpEditProjectParticipationPanel(String[][] data) {
        projectParticipationPanel.removeAll();
        projectParticipationPanel.setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        participationLabel = new JLabel("Mitarbeiter*innen");
        participationLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        DefaultTableModel participationModel = new DefaultTableModel(null, participationColumnsWithID);
        participationModel.setRowCount(data[1].length);
        projectParticipationTable = new JTable(participationModel);
        projectParticipationTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        projectParticipationTable.getTableHeader().setReorderingAllowed(false);
        setUpEditNameSelection(projectParticipationTable, data[1]);
        setUpEditDateSelection(projectParticipationTable, data[3], 3);
        setUpEditDateSelection(projectParticipationTable, data[4], 4);
        for (int i = 0; i < data[0].length; i++) {
            projectParticipationTable.setValueAt(data[0][i], i, 0);
            projectParticipationTable.setValueAt(data[2][i], i, 2);
        }
        JScrollPane participationScrollpane = new JScrollPane(projectParticipationTable);
        participationScrollpane.setVisible(true);
        projectParticipationPanel.add(participationScrollpane, BorderLayout.CENTER);
        northPanel.add(participationLabel);
        northPanel.add(Box.createHorizontalStrut(16));
        northPanel.add(Box.createHorizontalStrut(15));
        JButton addRowButton = new JButton("Neue Zeile hinzufügen");
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] emptyString = new String[4];
                emptyString[0] = null;
                emptyString[1] = null;
                emptyString[2] = null;
                emptyString[3] = null;
                participationModel.addRow(emptyString);
            }
        });
        northPanel.add(addRowButton);
        projectParticipationPanel.add(northPanel, BorderLayout.NORTH);
    }

    /**
     * Sets up the name selection in the table.
     *
     * @param table the table to be edited.
     * @param names the names to be displayed in the table.
     */
    public void setUpEditNameSelection(JTable table, String[] names) {
        TableColumn nameColumn = table.getColumnModel().getColumn(1);

        ArrayList<String> employeeNames = new ArrayList<String>();
        employeeNames.add("");
        employeeNames.addAll(List.of(employeeDataManager.getAllEmployeesNameList()));
        JComboBox nameCombobox = new JComboBox(employeeNames.toArray());
        nameCombobox.setBackground(Color.WHITE);
        DefaultCellEditor defaultCellEditor = new DefaultCellEditor(nameCombobox);
        nameColumn.setCellEditor(defaultCellEditor);

        for (int i = 0; i < names.length; i++) {
            table.setValueAt(names[i], i, 1);
        }
    }

    /**
     * Sets up the name selection in the table.
     *
     * @param table       the table to be edited.
     * @param dates       the dates to be displayed in the table.
     * @param columnIndex the column index of the date.
     */
    public void setUpEditDateSelection(JTable table, String[] dates, int columnIndex) {
        TableColumn dateColumn = table.getColumnModel().getColumn(columnIndex);
        table.setDefaultEditor(LocalDate.class, new DateTableEditor());
        table.setDefaultRenderer(LocalDate.class, new DateTableEditor());
        dateColumn.setCellEditor(table.getDefaultEditor(LocalDate.class));
        dateColumn.setCellRenderer(table.getDefaultRenderer(LocalDate.class));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date;
        for (int i = 0; i < dates.length; i++) {
            date = LocalDate.parse(dates[i], formatter);
            if (columnIndex == 4) {
                date = date.plusMonths(1).minusDays(1);
            }
            table.setValueAt(date, i, columnIndex);
        }
    }

    /**
     * Sets up the name selection in the table.
     *
     * @param table the corresponding table.
     */
    public void setUpNameSelection(JTable table) {
        TableColumn nameColumn = table.getColumnModel().getColumn(0);
        ArrayList<String> employeeNames = new ArrayList<String>();
        employeeNames.add("");
        employeeNames.addAll(List.of(employeeDataManager.getAllEmployeesNameList()));
        JComboBox nameCombobox = new JComboBox(employeeNames.toArray());
        nameCombobox.setBackground(Color.WHITE);
        nameColumn.setCellEditor(new DefaultCellEditor(nameCombobox));
    }

    /**
     * Sets up the date selection in the table.
     *
     * @param table the corresponding table.
     */
    public void setUpDateSelection(JTable table) {
        TableColumn dateColumn = table.getColumnModel().getColumn(2);
        table.setDefaultEditor(LocalDate.class, new DateTableEditor());
        table.setDefaultRenderer(LocalDate.class, new DateTableEditor());
        dateColumn.setCellEditor(table.getDefaultEditor(LocalDate.class));
        dateColumn.setCellRenderer(table.getDefaultRenderer(LocalDate.class));

        dateColumn = table.getColumnModel().getColumn(3);
        dateColumn.setCellEditor(table.getDefaultEditor(LocalDate.class));
        dateColumn.setCellRenderer(table.getDefaultRenderer(LocalDate.class));
    }

    public void setActionListener(ActionListener l) {
        submitAndReset.addActionListener(l);
        submitAndClose.addActionListener(l);
        reset.addActionListener(l);
        cancel.addActionListener(l);
    }

    public JLabel getCategoriesSum() {
        return categoriesSum;
    }

    public String[] getCategoryColumns() {
        return categoryColumns;
    }

    public String[] getFunderColumns() {
        return funderColumns;
    }

    public String[] getParticipationColumns() {
        return participationColumns;
    }

    public String[] getCategoryColumnWithID() {
        return categoryColumnWithID;
    }

    public String[] getFunderColumnWithID() {
        return funderColumnWithID;
    }

    public JButton getSubmitAndReset() {
        return submitAndReset;
    }

    public JButton getSubmitAndClose() {
        return submitAndClose;
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

    /**
     * This class is used to verify the input of the user in the table.
     */
    private class CellEditor extends DefaultCellEditor {
        InputVerifier verifier = null;

        public CellEditor(InputVerifier verifier) {
            super(new JTextField());
            this.verifier = verifier;
        }

        @Override
        public boolean stopCellEditing() {
            return verifier.verify(editorComponent) && super.stopCellEditing();
        }

    }
}

