package excelchaos_view;

import excelchaos_model.calculations.NewAndImprovedSalaryCalculation;
import excelchaos_model.database.*;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_model.utility.TableColumnAdjuster;
import excelchaos_view.components.tablecellrenderer.MultiLineTableCellRenderer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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

    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();

    private ContractDataManager contractDataManager = ContractDataManager.getInstance();

    private ProjectParticipationManager participationManager = ProjectParticipationManager.getInstance();

    private NewAndImprovedSalaryCalculation salaryCalculation = new NewAndImprovedSalaryCalculation();


    private JPanel mainPanel, projectParticipationSumPanel;

    private JTable participationSumTable, participationSumHeaderTable;


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

    public void setUpParticipationSumPanel(String[] employeeNames, String[] months, String[][] tableData) {
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
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column) {
                Component comp = super.getTableCellRendererComponent(table,
                        value, isSelected, hasFocus, row, column);
                String name = (String) participationSumHeaderTable.getValueAt(row,0);
                Contract contract = contractDataManager.getContract(employeeDataManager.getEmployeeByName(name).getId());
                BigDecimal updatedValue = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope((String) value);
                if(contract.getScope().compareTo(updatedValue) < 0){
                    comp.setBackground(Color.RED);
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
        JTable rowsMainTable = initMainTableRows(mainTable, nameRows);
        JTable sumTable = initSumTable(summedTableData, monthColumns);
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


                }
            }
        });

        projectPanel.add(mainScrollPane, BorderLayout.CENTER);
        projectPanel.add(sideScrollPane, BorderLayout.SOUTH);

        saveProjectChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateFormat format = new SimpleDateFormat("MMM-yyyy");
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
                LinkedHashSet<String> combiningNames = new LinkedHashSet<>(List.of(oldNames));
                combiningNames.addAll(newlySelectedNames);
                String[] newNames = combiningNames.toArray(new String[0]);

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
                JTable newRowsMainTable = initMainTableRows(newMainTable, newNames);
                JTable newSumTable = initSumTable(oldSumTableData, monthColumns);
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
                        }

                    }
                });

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
            }
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(addPersonSelection);

        buttonPanel.add(closeButton);


        participationDialog.setLayout(new BorderLayout());
        participationDialog.add(comboBoxPanel, BorderLayout.CENTER);
        participationDialog.add(buttonPanel, BorderLayout.SOUTH);
        participationDialog.setName("Mitarbeiter hinzufügen");
        participationDialog.setTitle("Mitarbeiter hinzufügen");
        participationDialog.pack();
        participationDialog.setLocationRelativeTo(null);
        participationDialog.setAlwaysOnTop(true);
        participationDialog.setVisible(true);


    }


}
