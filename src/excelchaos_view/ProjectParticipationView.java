package excelchaos_view;

import excelchaos_model.calculations.SalaryCalculation;
import excelchaos_model.database.*;
import excelchaos_model.inputVerifier.WorkScopeVerifier;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_model.utility.TableColumnAdjuster;
import excelchaos_view.components.tablecellrenderer.MultiLineTableCellRenderer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static javax.swing.BorderFactory.createEmptyBorder;

public class ProjectParticipationView extends JPanel {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private ContractDataManager contractDataManager = new ContractDataManager();

    private ProjectParticipationManager participationManager = new ProjectParticipationManager();

    private SalaryCalculation salaryCalculation = new SalaryCalculation();


    private JPanel mainPanel, projectParticipationSumPanel;

    private JTable participationSumTable, participationSumHeaderTable;


    /**
     * Initializes this gui component.
     */
    public void init() {

        setLayout(new BorderLayout());
        mainPanel = new JPanel();

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVisible(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        GridLayout gridLayout = new GridLayout(0, 1, 0, 10);
        mainPanel.setLayout(gridLayout);


    }

    /**
     * Sets up the panel for displaying the total participation sum for each employee for the given months and employee names.
     *
     * @param employeeNames An array of Strings representing the names of the employees.
     * @param months        An array of Strings representing the months.
     * @param tableData     A two-dimensional array of Strings representing the participation sum data for each employee and month.
     */
    public void setUpTotalParticipationSumPanel(String[] employeeNames, String[] months, String[][] tableData) {
        projectParticipationSumPanel = new JPanel();

        GridLayout gridLayout = new GridLayout(1, 1, 0, 0);
        projectParticipationSumPanel.setLayout(gridLayout);
        DefaultTableModel participationSumTableModel = new DefaultTableModel(tableData, months);
        participationSumTable = new JTable(participationSumTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        participationSumTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(participationSumTable);
        tca.adjustColumns();
        participationSumTable.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) participationSumTable.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(JLabel.RIGHT);
        participationSumHeaderTable = setParticipationSumHeaderTable(participationSumTable, employeeNames);
        participationSumTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table,
                        value, isSelected, hasFocus, row, column);
                String name = (String) participationSumHeaderTable.getValueAt(row, 0);
                Contract contract = contractDataManager.getContract(employeeDataManager.getEmployeeByName(name).getId());
                BigDecimal updatedValue = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) value);
                if (contract.getScope().compareTo(updatedValue) < 0) {
                    comp.setBackground(new Color(255, 143, 143));
                } else {
                    comp.setBackground(Color.WHITE);
                }

                return comp;
            }
        });

        JScrollPane participationSumPane = new JScrollPane(participationSumTable);
        participationSumPane.setRowHeaderView(participationSumHeaderTable);
        participationSumPane.setPreferredSize(new Dimension(participationSumTable.getPreferredSize().width + participationSumHeaderTable.getPreferredSize().width, participationSumTable.getRowHeight() * 6));
        projectParticipationSumPanel.add(participationSumPane);
        add(projectParticipationSumPanel, BorderLayout.SOUTH);
    }


    /**
     * Sets up a header table for the participation sum table, which displays employee names and their corresponding work scopes.
     *
     * @param table the participation summary table for which the header table is being set up
     * @param rows  an array of employee names to be displayed in the header table
     * @return a {@link JTable} representing the header table for the participation sum table
     */
    private JTable setParticipationSumHeaderTable(JTable table, String[] rows) {
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @Override
            public int getRowCount() {
                return table.getRowCount();
            }

            @Override
            public Class<?> getColumnClass(int colNum) {
                switch (colNum) {
                    case 0:
                        return String.class;
                    default:
                        return super.getColumnClass(colNum);
                }
            }
        };
        JTable headerTable = new JTable(model);
        for (int row = 0; row < rows.length; row++) {
            Contract contract = contractDataManager.getContract(employeeDataManager.getEmployeeByName(rows[row]).getId());
            headerTable.setValueAt(rows[row], row, 0);
            headerTable.setValueAt(StringAndBigDecimalFormatter.formatPercentageToStringForScope(contract.getScope()), row, 1);
        }
        headerTable.setShowGrid(false);
        headerTable.setPreferredScrollableViewportSize(new Dimension(200, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(160);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new MultiLineTableCellRenderer());
        headerTable.getColumnModel().getColumn(1).setPreferredWidth(40);
        headerTable.getColumnModel().getColumn(1).setCellRenderer(new MultiLineTableCellRenderer());
        return headerTable;
    }

    /**
     * This methods creates the panel in which all participation data and employee cost for the given months is displayed
     *
     * @param projectName     The name of project the panel gets created for.
     * @param monthColumns    The runtime of the project in months.
     * @param nameRows        The names of the employees that are working on the project
     * @param tableData       The participation data and cost of each employee working on the project
     * @param summedTableData The sum of aboves data.
     * @param totalCost       The total employee cost for the project
     * @param projectId       database project id of the project
     */
    public void setUpProjectPanel(String projectName, String[] monthColumns, String[] nameRows, String[][] tableData, String[][] summedTableData, String totalCost, int projectId) {

        JPanel projectPanel = new JPanel();
        projectPanel.setLayout(new BorderLayout());

        JPanel projectNamePanel = new JPanel();
        projectNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel projectNameLabel = new JLabel(projectName);
        projectNameLabel.setFont(new Font("Dialog", Font.BOLD, 22));
        projectNamePanel.add(projectNameLabel);
        projectNamePanel.add(Box.createHorizontalStrut(100));

        JButton addPersonButton = new JButton("Person zum Projekt hinzufügen");
        JButton saveProjectChanges = new JButton("Projektplanung speichern");
        projectNamePanel.add(addPersonButton);
        projectNamePanel.add(saveProjectChanges);

        projectNamePanel.add(Box.createHorizontalStrut(100));
        JLabel totalCostLabel = new JLabel("Gesamtpersonalkosten: " + totalCost);
        totalCostLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        projectNamePanel.add(totalCostLabel);

        projectPanel.add(projectNamePanel, BorderLayout.NORTH);

        JTable mainTable = initMainTable(monthColumns, tableData);
        mainTable.getTableHeader().setReorderingAllowed(false);
        mainTable.setDefaultEditor(Object.class, new CellEditor(new WorkScopeVerifier()));
        JTable rowsMainTable = initMainTableRows(mainTable, nameRows);
        JTable sumTable = initSumTable(summedTableData, monthColumns);
        sumTable.getTableHeader().setReorderingAllowed(false);
        JTable rowsSumTable = initSumHeaderTable(sumTable);

        JScrollPane mainScrollPane = new JScrollPane(mainTable);
        mainScrollPane.setRowHeaderView(rowsMainTable);

        JScrollPane sideScrollPane = new JScrollPane(sumTable);
        sideScrollPane.setRowHeaderView(rowsSumTable);
        sideScrollPane.setBorder(createEmptyBorder());
        mainTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        mainTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getFirstRow() % 2 == 0) {
                    DateFormat format = new SimpleDateFormat("MMM-yyyy");
                    Date date;
                    try {
                        date = format.parse(mainTable.getColumnName(e.getColumn()));
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    Employee employee = employeeDataManager.getEmployeeByName((String) rowsMainTable.getValueAt(e.getFirstRow() / 2, 0));
                    BigDecimal salaryChange = salaryCalculation.projectSalaryToGivenMonth(employee.getId(), date);
                    salaryChange = salaryChange.multiply(StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) mainTable.getValueAt(e.getFirstRow(), e.getColumn())));
                    mainTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(salaryChange), e.getFirstRow() + 1, e.getColumn());

                    participationManager.removeProjectParticipationBasedOnDate(projectId, employee.getId(), date);
                    participationManager.addProjectParticipation(new ProjectParticipation(projectId, employee.getId(), StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) mainTable.getValueAt(e.getFirstRow(), e.getColumn())), date));

                    BigDecimal sumPersonenMonate = new BigDecimal(0);
                    BigDecimal sumCostPerMonth = new BigDecimal(0);
                    for (int row = 0; row < mainTable.getRowCount(); row++) {
                        if (row % 2 == 0) {
                            sumPersonenMonate = sumPersonenMonate.add(StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) mainTable.getValueAt(row, e.getColumn())));
                        } else {
                            sumCostPerMonth = sumCostPerMonth.add(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency((String) mainTable.getValueAt(row, e.getColumn())));
                        }
                    }
                    sumTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToPersonenMonate(sumPersonenMonate), 0, e.getColumn());
                    sumTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(sumCostPerMonth), 1, e.getColumn());

                    BigDecimal newTotalCost = new BigDecimal(0);
                    for (int column = 0; column < sumTable.getColumnCount(); column++) {
                        newTotalCost = newTotalCost.add(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency((String) sumTable.getValueAt(1, column)));
                    }
                    totalCostLabel.setText("Gesamtpersonalkosten: " + StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(newTotalCost));

                    //ParticipationSum Control Instance
                    int row = 0;
                    for (int i = 0; i < participationSumHeaderTable.getRowCount(); i++) {
                        if (participationSumHeaderTable.getValueAt(i, 0).equals(rowsMainTable.getValueAt(e.getFirstRow() / 2, 0))) {
                            row = i;
                            break;
                        }
                    }
                    int column = 0;
                    for (int i = 0; i < participationSumTable.getColumnCount(); i++) {
                        if (participationSumTable.getColumnName(i).equals(mainTable.getColumnName(e.getColumn()))) {
                            column = i;
                            break;
                        }
                    }
                    BigDecimal updatedScopeValue = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) mainTable.getValueAt(e.getFirstRow(), e.getColumn()));
                    List<ProjectParticipation> projectParticipations = participationManager.getProjectParticipationByPersonID(employee.getId());
                    for (ProjectParticipation projectParticipation : projectParticipations) {
                        if (date.compareTo(projectParticipation.getParticipation_period()) == 0) {
                            if (projectId != projectParticipation.getProject_id()) {
                                updatedScopeValue = updatedScopeValue.add(projectParticipation.getScope());
                            }
                        }
                    }
                    participationSumTable.setValueAt(StringAndBigDecimalFormatter.formatPercentageToStringForScope(updatedScopeValue), row, column);
                    participationSumTable.revalidate();
                    participationSumTable.repaint();
                    projectNameLabel.setForeground(Color.RED);

                }
            }
        });

        projectPanel.add(mainScrollPane, BorderLayout.CENTER);
        projectPanel.add(sideScrollPane, BorderLayout.SOUTH);

        saveProjectChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateFormat format = new SimpleDateFormat("MMM-yyyy");
                projectNameLabel.setForeground(Color.BLACK);
                for (int row = 0; row < mainTable.getRowCount(); row++) {
                    if (row % 2 == 0) {
                        int employeeID = employeeDataManager.getEmployeeByName((String) rowsMainTable.getValueAt(row / 2, 0)).getId();
                        participationManager.removeProjectParticipation(projectId, employeeID);
                        for (int column = 0; column < mainTable.getColumnCount(); column++) {
                            if (column != 0) {
                                BigDecimal scope = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) mainTable.getValueAt(row, column));
                                Date date;
                                try {
                                    date = format.parse(mainTable.getColumnName(column));
                                } catch (ParseException ex) {
                                    throw new RuntimeException(ex);
                                }

                                ProjectParticipation projectParticipation = new ProjectParticipation(projectId, employeeID, scope, date);
                                participationManager.addProjectParticipation(projectParticipation);
                            }

                        }

                    }
                }
            }
        });
        addPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpAddParticipationView(projectPanel, mainTable, rowsMainTable, sumTable, totalCost, projectId, monthColumns, projectName);
            }
        });

        mainPanel.add(projectPanel);
    }


    /**
     * Initializes the rows of a JTable object with the given strings as row values.
     *
     * @param table the JTable object to be modified
     * @param rows  an array of strings representing the values of the rows to be added to the table
     * @return the modified JTable object with the initialized rows
     */
    public JTable initMainTableRows(JTable table, String[] rows) {
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public int getColumnCount() {
                return 1;
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @Override
            public int getRowCount() {
                return table.getRowCount();
            }

            @Override
            public Class<?> getColumnClass(int colNum) {
                switch (colNum) {
                    case 0:
                        return String.class;
                    default:
                        return super.getColumnClass(colNum);
                }
            }
        };
        JTable headerTable = new JTable(model);
        for (int row = 0; row < rows.length; row++) {
            headerTable.setValueAt(rows[row], row, 0);
        }
        headerTable.setShowGrid(false);
        headerTable.setPreferredScrollableViewportSize(new Dimension(180, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        headerTable.setRowHeight(60);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new MultiLineTableCellRenderer());
        for (int row = 0; row < rows.length; row++) {
            headerTable.setRowHeight(row + rows.length, 1);
        }
        return headerTable;
    }

    /**
     * Initializes the main table with given months and data. The table is non-editable for odd rows, and alternate row colors are applied to improve visibility. The table columns are adjusted to fit the content and the first column is hidden.
     *
     * @param months An array of strings representing the month names for the table header.
     * @param data   A 2D array of strings containing the data to be displayed in the table.
     * @return A JTable object representing the initialized main table with data.
     */


    public JTable initMainTable(String[] months, String[][] data) {
        JTable table;
        DefaultTableModel mainTableModel = new DefaultTableModel(data, months);
        table = new JTable(mainTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (row % 2 == 0)
                    return true;
                else
                    return false;
            }
        };


        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(30);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                return c;
            }
        });
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        return table;

    }

    /**
     * Initializes a JTable to display the sum header information for the given table.
     *
     * @param table the JTable to which the sum header table corresponds
     * @return the JTable object containing the sum header table with the specified properties
     */

    public JTable initSumHeaderTable(JTable table) {
        DefaultTableModel model = new DefaultTableModel() {

            @Override
            public int getColumnCount() {
                return 1;
            }

            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }

            @Override
            public int getRowCount() {
                return table.getRowCount();
            }

            @Override
            public Class<?> getColumnClass(int colNum) {
                switch (colNum) {
                    case 0:
                        return String.class;
                    default:
                        return super.getColumnClass(colNum);
                }
            }
        };
        JTable headerTable = new JTable(model);
        headerTable.setValueAt("Personenmonate", 0, 0);
        headerTable.setValueAt("Gesamtkosten pro Monat", 1, 0);
        headerTable.setShowGrid(false);
        headerTable.setPreferredScrollableViewportSize(new Dimension(180, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        headerTable.setRowHeight(30);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new MultiLineTableCellRenderer());
        return headerTable;
    }

    /**
     * Creates a new JTable object to display the summed table data with the given months array as column headers.
     * The table is initialized with the given summed table data and is not editable. The table rows are sized
     * to fit the data and are right-aligned. The first column is hidden.
     *
     * @param summedTableData a two-dimensional array of Strings containing the summed table data
     * @param months          an array of Strings containing the column headers for the table
     * @return a new JTable object initialized with the given summed table data and column headers
     */
    public JTable initSumTable(String[][] summedTableData, String[] months) {
        JTable table;
        DefaultTableModel mainTableModel = new DefaultTableModel(summedTableData, months);
        table = new JTable(mainTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(30);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        return table;
    }

    /**
     * This method is continuation of setupProjectPanel and is called when new Employees are added to a project
     *
     * @param projectPanel  The originally setup projectpanel
     * @param mainTable     The maintable containing the original data
     * @param rowsMainTable The rows of the original table containing all employee names
     * @param sumTable      The original sum table
     * @param totalCost     The original total cost
     * @param projectId     database project id of the project
     * @param monthColumns  The runtime of the project in months.
     * @param projectName   The name of project the panel gets created for.
     */
    private void setUpAddParticipationView(JPanel projectPanel, JTable mainTable, JTable rowsMainTable, JTable sumTable, String totalCost, int projectId, String[] monthColumns, String projectName) {
        JDialog participationDialog = new JDialog();

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new GridLayout(0, 1, 0, 20));

        JPanel chosePanel = new JPanel();
        chosePanel.setLayout(new FlowLayout());
        JLabel chosePersonLabel = new JLabel("Person hinzfügen: ");

        ArrayList<String> employeeNames = new ArrayList<String>(List.of(employeeDataManager.getAllEmployeesNameList()));
        ArrayList<String> names = new ArrayList<String>();
        names.add("Keine Auswahl");
        names.addAll(employeeNames);
        JComboBox<String> nameComboBox = new JComboBox(names.toArray());
        nameComboBox.setBackground(Color.WHITE);
        nameComboBox.setMaximumRowCount(8);

        chosePanel.add(chosePersonLabel);
        chosePanel.add(nameComboBox);

        comboBoxPanel.add(chosePanel);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton closeButton = new JButton("Abbrechen");
        JButton addPersonSelection = new JButton("Person hinzufügen");
        JButton submitButton = new JButton("Eingaben hinzufügen");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                participationDialog.dispose();
            }
        });

        addPersonSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel newChosePanel = new JPanel();
                newChosePanel.setLayout(new FlowLayout());
                JLabel newChosePersonLabel = new JLabel("Person hinzfügen: ");
                JComboBox<String> newNameComboBox = new JComboBox(names.toArray());
                newNameComboBox.setBackground(Color.WHITE);
                newNameComboBox.setMaximumRowCount(8);

                newChosePanel.add(newChosePersonLabel);
                newChosePanel.add(newNameComboBox);

                comboBoxPanel.add(newChosePanel);

                participationDialog.pack();
                participationDialog.revalidate();
                participationDialog.repaint();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashSet<String> newlySelectedNames = new HashSet<>();
                for (int i = 0; i < comboBoxPanel.getComponentCount(); i++) {
                    if (comboBoxPanel.getComponent(i) instanceof JPanel) {
                        JPanel insideComboBoxPanel = (JPanel) comboBoxPanel.getComponent(i);
                        if (insideComboBoxPanel.getComponent(1) instanceof JComboBox) {
                            if (!((String) ((JComboBox) insideComboBoxPanel.getComponent(1)).getSelectedItem()).equals("Keine Auswahl")) {
                                newlySelectedNames.add((String) ((JComboBox) insideComboBoxPanel.getComponent(1)).getSelectedItem());
                            }

                        }
                    }
                }


                String[] oldNames = new String[rowsMainTable.getRowCount() / 2];
                for (int row = 0; row < oldNames.length; row++) {
                    oldNames[row] = (String) rowsMainTable.getValueAt(row, 0);
                }

                LinkedHashSet<String> oldSumTableEmployeeNames = new LinkedHashSet<>();
                for (int row = 0; row < participationSumHeaderTable.getRowCount(); row++) {
                    oldSumTableEmployeeNames.add((String) participationSumHeaderTable.getValueAt(row, 0));
                }

                DefaultTableModel participationSumTableModel = (DefaultTableModel) participationSumTable.getModel();
                String[][] oldParticipationSumTableData = new String[participationSumTable.getRowCount()][participationSumTable.getColumnCount()];
                String[] monthsArray = new String[participationSumTable.getColumnCount()];
                for (int row = 0; row < oldParticipationSumTableData.length; row++) {
                    for (int column = 0; column < oldParticipationSumTableData[0].length; column++) {
                        if (row == 0) {
                            monthsArray[column] = participationSumTable.getColumnName(column);
                        }
                        oldParticipationSumTableData[row][column] = (String) participationSumTableModel.getValueAt(row, column);
                    }
                }

                LinkedHashSet<String> combiningNames = new LinkedHashSet<>(List.of(oldNames));
                combiningNames.addAll(newlySelectedNames);
                String[] newNames = combiningNames.toArray(new String[0]);
                DateFormat testFormat = new SimpleDateFormat("MMM-yyyy");

                for (int i = 0; i < newNames.length; i++) {
                    if (oldSumTableEmployeeNames.contains(newNames[i])) {

                    } else {
                        oldSumTableEmployeeNames.add(newNames[i]);
                    }
                }

                String[][] newParticipationSumTableData = new String[oldSumTableEmployeeNames.size()][monthsArray.length];
                Date checkingDate;
                String[] addedEmployeeNames = oldSumTableEmployeeNames.toArray(new String[0]);
                for (int row = 0; row < newParticipationSumTableData.length; row++) {
                    BigDecimal sumToFormat = new BigDecimal(0);
                    for (int column = 0; column < newParticipationSumTableData[0].length; column++) {
                        if (row < oldParticipationSumTableData.length) {
                            newParticipationSumTableData[row][column] = oldParticipationSumTableData[row][column];
                        } else {
                            Employee addedEmployee = employeeDataManager.getEmployeeByName(addedEmployeeNames[row]);
                            List<ProjectParticipation> projectParticipations = participationManager.getProjectParticipationByPersonID(addedEmployee.getId());
                            try {
                                checkingDate = testFormat.parse(monthsArray[column]);
                            } catch (ParseException ex) {
                                throw new RuntimeException(ex);
                            }
                            for (ProjectParticipation participation : projectParticipations) {
                                if (participation.getParticipation_period().compareTo(checkingDate) == 0) {
                                    sumToFormat = sumToFormat.add(participation.getScope());
                                }
                            }
                            newParticipationSumTableData[row][column] = StringAndBigDecimalFormatter.formatPercentageToStringForScope(sumToFormat);
                        }
                    }
                }
                projectParticipationSumPanel.removeAll();
                remove(projectParticipationSumPanel);
                setUpTotalParticipationSumPanel(addedEmployeeNames, monthsArray, newParticipationSumTableData);


                String[][] oldMainTableData = new String[mainTable.getRowCount()][mainTable.getColumnCount()];
                for (int row = 0; row < mainTable.getRowCount(); row++) {
                    for (int column = 0; column < mainTable.getColumnCount(); column++) {
                        oldMainTableData[row][column] = (String) mainTable.getValueAt(row, column);
                    }
                }

                String[][] newTableData = new String[newNames.length * 2][monthColumns.length];
                for (int row = 0; row < newTableData.length; row++) {
                    for (int column = 0; column < newTableData[0].length; column++) {
                        if (row >= oldMainTableData.length) {
                            if (row % 2 == 0) {
                                newTableData[row][column] = "0%";
                            } else {
                                newTableData[row][column] = "0 €";
                            }
                        } else {
                            newTableData[row][column] = oldMainTableData[row][column];
                        }
                    }
                }

                String[][] oldSumTableData = new String[sumTable.getRowCount()][sumTable.getColumnCount()];
                for (int row = 0; row < sumTable.getRowCount(); row++) {
                    for (int column = 0; column < sumTable.getColumnCount(); column++) {
                        oldSumTableData[row][column] = (String) sumTable.getValueAt(row, column);
                    }
                }

                JPanel projectNamePanel = new JPanel();
                projectNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

                JLabel projectNameLabel = new JLabel(projectName);
                projectNameLabel.setFont(new Font("Dialog", Font.BOLD, 22));
                projectNameLabel.setForeground(Color.RED);
                projectNamePanel.add(projectNameLabel);
                projectNamePanel.add(Box.createHorizontalStrut(100));

                JButton addPersonButton = new JButton("Person zum Projekt hinzufügen");
                JButton saveProjectChanges = new JButton("Projektplanung speichern");
                projectNamePanel.add(addPersonButton);
                projectNamePanel.add(saveProjectChanges);

                projectNamePanel.add(Box.createHorizontalStrut(100));
                JLabel totalCostLabel = new JLabel("Gesamtpersonalkosten: " + totalCost);
                totalCostLabel.setFont(new Font("Dialog", Font.BOLD, 16));
                projectNamePanel.add(totalCostLabel);

                JTable newMainTable = initMainTable(monthColumns, newTableData);
                newMainTable.getTableHeader().setReorderingAllowed(false);
                JTable newRowsMainTable = initMainTableRows(newMainTable, newNames);
                JTable newSumTable = initSumTable(oldSumTableData, monthColumns);
                newSumTable.getTableHeader().setReorderingAllowed(false);
                JTable rowsSumTable = initSumHeaderTable(newSumTable);

                newMainTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
                newMainTable.getModel().addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        if (e.getFirstRow() % 2 == 0) {
                            DateFormat format = new SimpleDateFormat("MMM-yyyy");
                            Date date;
                            try {
                                date = format.parse(newMainTable.getColumnName(e.getColumn()));
                            } catch (ParseException ex) {
                                throw new RuntimeException(ex);
                            }
                            Employee employee = employeeDataManager.getEmployeeByName((String) newRowsMainTable.getValueAt(e.getFirstRow() / 2, 0));
                            BigDecimal salaryChange = salaryCalculation.projectSalaryToGivenMonth(employeeDataManager.getEmployeeByName((String) newRowsMainTable.getValueAt(e.getFirstRow() / 2, 0)).getId(), date);
                            salaryChange = salaryChange.multiply(StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) newMainTable.getValueAt(e.getFirstRow(), e.getColumn())));
                            newMainTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(salaryChange), e.getFirstRow() + 1, e.getColumn());

                            BigDecimal sumPersonenMonate = new BigDecimal(0);
                            BigDecimal sumCostPerMonth = new BigDecimal(0);
                            for (int row = 0; row < newMainTable.getRowCount(); row++) {
                                if (row % 2 == 0) {
                                    sumPersonenMonate = sumPersonenMonate.add(StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) newMainTable.getValueAt(row, e.getColumn())));
                                } else {
                                    sumCostPerMonth = sumCostPerMonth.add(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency((String) newMainTable.getValueAt(row, e.getColumn())));
                                }
                            }
                            newSumTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToPersonenMonate(sumPersonenMonate), 0, e.getColumn());
                            newSumTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(sumCostPerMonth), 1, e.getColumn());

                            BigDecimal newTotalCost = new BigDecimal(0);
                            for (int column = 0; column < newSumTable.getColumnCount(); column++) {
                                newTotalCost = newTotalCost.add(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency((String) newSumTable.getValueAt(1, column)));
                            }
                            totalCostLabel.setText("Gesamtpersonalkosten: " + StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(newTotalCost));


                            //ParticipationSum Control Instance
                            int row = 0;
                            for (int i = 0; i < participationSumHeaderTable.getRowCount(); i++) {
                                if (participationSumHeaderTable.getValueAt(i, 0).equals(newRowsMainTable.getValueAt(e.getFirstRow() / 2, 0))) {
                                    row = i;
                                    break;
                                }
                            }
                            int column = 0;
                            for (int i = 0; i < participationSumTable.getColumnCount(); i++) {
                                if (participationSumTable.getColumnName(i).equals(newMainTable.getColumnName(e.getColumn()))) {
                                    column = i;
                                    break;
                                }
                            }
                            BigDecimal updatedScopeValue = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) newMainTable.getValueAt(e.getFirstRow(), e.getColumn()));
                            List<ProjectParticipation> projectParticipations = participationManager.getProjectParticipationByPersonID(employee.getId());
                            for (ProjectParticipation projectParticipation : projectParticipations) {
                                if (date.compareTo(projectParticipation.getParticipation_period()) == 0) {
                                    if (projectId != projectParticipation.getProject_id()) {
                                        updatedScopeValue = updatedScopeValue.add(projectParticipation.getScope());
                                    }
                                }
                            }
                            participationSumTable.setValueAt(StringAndBigDecimalFormatter.formatPercentageToStringForScope(updatedScopeValue), row, column);
                            participationSumTable.revalidate();
                            participationSumTable.repaint();
                            projectNameLabel.setForeground(Color.RED);

                        }

                    }
                });
                newMainTable.setDefaultEditor(Object.class, new CellEditor(new WorkScopeVerifier()));
                JScrollPane newMainScrollPane = new JScrollPane(newMainTable);
                newMainScrollPane.setRowHeaderView(newRowsMainTable);

                JScrollPane newSideScrollPane = new JScrollPane(newSumTable);
                newSideScrollPane.setRowHeaderView(rowsSumTable);
                newSideScrollPane.setBorder(createEmptyBorder());

                saveProjectChanges.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DateFormat format = new SimpleDateFormat("MMM-yyyy");
                        for (int row = 0; row < newMainTable.getRowCount(); row++) {
                            if (row % 2 == 0) {
                                int employeeID = employeeDataManager.getEmployeeByName((String) newRowsMainTable.getValueAt(row / 2, 0)).getId();
                                participationManager.removeProjectParticipation(projectId, employeeID);
                                for (int column = 0; column < newMainTable.getColumnCount(); column++) {
                                    if (column != 0) {
                                        BigDecimal scope = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) newMainTable.getValueAt(row, column));
                                        Date date;
                                        try {
                                            date = format.parse(newMainTable.getColumnName(column));
                                        } catch (ParseException ex) {
                                            throw new RuntimeException(ex);
                                        }

                                        ProjectParticipation projectParticipation = new ProjectParticipation(projectId, employeeID, scope, date);
                                        participationManager.addProjectParticipation(projectParticipation);
                                    }

                                }
                                projectNameLabel.setForeground(Color.BLACK);

                            }
                        }
                    }
                });

                addPersonButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setUpAddParticipationView(projectPanel, newMainTable, newRowsMainTable, newSumTable, totalCost, projectId, monthColumns, projectName);
                    }
                });

                projectPanel.removeAll();
                projectPanel.add(projectNamePanel, BorderLayout.NORTH);
                projectPanel.add(newMainScrollPane, BorderLayout.CENTER);
                projectPanel.add(newSideScrollPane, BorderLayout.SOUTH);

                projectPanel.revalidate();
                projectPanel.repaint();
                mainPanel.revalidate();
                mainPanel.repaint();
                participationDialog.dispose();
                projectParticipationSumPanel.revalidate();
                projectParticipationSumPanel.repaint();
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(addPersonSelection);

        buttonPanel.add(closeButton);


        participationDialog.setLayout(new

                BorderLayout());
        participationDialog.add(comboBoxPanel, BorderLayout.CENTER);
        participationDialog.add(buttonPanel, BorderLayout.SOUTH);
        participationDialog.setName("Mitarbeiter hinzufügen");
        participationDialog.setTitle("Mitarbeiter hinzufügen");
        participationDialog.pack();
        participationDialog.setLocationRelativeTo(null);
        participationDialog.setAlwaysOnTop(true);
        participationDialog.setVisible(true);


    }

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
