package excelchaos_view;

import com.github.lgooddatepicker.tableeditors.DateTableEditor;
import excelchaos_model.*;
import excelchaos_model.calculations.SalaryCalculation;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_model.utility.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import static javax.swing.BorderFactory.createEmptyBorder;

public class ProjectParticipationView extends JPanel {

    private SalaryCalculation salaryCalculation = new SalaryCalculation();


    private JPanel mainPanel;

    private StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private ProjectParticipationManager participationManager = new ProjectParticipationManager();


    private ProjectParticipationDataModel projectParticipationDataModel = new ProjectParticipationDataModel();


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
                    DateFormat format = new SimpleDateFormat("MMMM-yyyy");
                    Date date;
                    try {
                        date = format.parse(mainTable.getColumnName(e.getColumn()));
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    double salaryChange = salaryCalculation.determineSalaryOfGivenMonth(employeeDataManager.getEmployeeByName((String) rowsMainTable.getValueAt(e.getFirstRow() / 2, 0)).getId(), date);
                    System.out.println(e.getFirstRow());
                    salaryChange = salaryChange * transformer.formatStringToPercentageValueForScope((String) mainTable.getValueAt(e.getFirstRow(), e.getColumn()));
                    mainTable.setValueAt(transformer.formatDoubleToString(salaryChange, 1), e.getFirstRow() + 1, e.getColumn());

                    double sumPersonenMonate = 0;
                    double sumCostPerMonth = 0;
                    for (int row = 0; row < mainTable.getRowCount(); row++) {
                        if (row % 2 == 0) {
                            sumPersonenMonate += transformer.formatStringToPercentageValueForScope((String) mainTable.getValueAt(row, e.getColumn()));
                        } else {
                            sumCostPerMonth += transformer.formatStringToDouble((String) mainTable.getValueAt(row, e.getColumn()));
                        }
                    }
                    sumTable.setValueAt(transformer.formatDoubleToPersonenMonate(sumPersonenMonate), 0, e.getColumn());
                    sumTable.setValueAt(transformer.formatDoubleToString(sumCostPerMonth, 1), 1, e.getColumn());

                    double newTotalCost = 0;
                    for (int column = 0; column < sumTable.getColumnCount(); column++) {
                        newTotalCost += transformer.formatStringToDouble((String) sumTable.getValueAt(1, column));
                    }
                    totalCostLabel.setText("Gesamtpersonalkosten: " + transformer.formatDoubleToString(newTotalCost, 1));
                }
            }
        });

        projectPanel.add(mainScrollPane, BorderLayout.CENTER);
        projectPanel.add(sideScrollPane, BorderLayout.SOUTH);

        saveProjectChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateFormat format = new SimpleDateFormat("MMMM-yyyy");
                for (int row = 0; row < mainTable.getRowCount(); row++) {
                    if (row % 2 == 0) {
                        int employeeID = employeeDataManager.getEmployeeByName((String) rowsMainTable.getValueAt(row / 2, 0)).getId();
                        participationManager.removeProjectParticipation(projectId, employeeID);
                        for (int column = 0; column < mainTable.getColumnCount(); column++) {
                            if (column != 0) {
                                double scope = transformer.formatStringToPercentageValueForScope((String) mainTable.getValueAt(row, column));
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

    public void setUpTotalWorkPanel(String[] monthColumnsForAllProjects, String[] summedWork, String[] namesInSelectedProjects) {

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
            System.out.println(rows[row]);
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
                                System.out.println("Here");
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
                            DateFormat format = new SimpleDateFormat("MMMM-yyyy");
                            Date date;
                            try {
                                date = format.parse(newMainTable.getColumnName(e.getColumn()));
                            } catch (ParseException ex) {
                                throw new RuntimeException(ex);
                            }
                            double salaryChange = salaryCalculation.determineSalaryOfGivenMonth(employeeDataManager.getEmployeeByName((String) newRowsMainTable.getValueAt(e.getFirstRow() / 2, 0)).getId(), date);
                            salaryChange = salaryChange * transformer.formatStringToPercentageValueForScope((String) newMainTable.getValueAt(e.getFirstRow(), e.getColumn()));
                            newMainTable.setValueAt(transformer.formatDoubleToString(salaryChange, 1), e.getFirstRow() + 1, e.getColumn());

                            double sumPersonenMonate = 0;
                            double sumCostPerMonth = 0;
                            for (int row = 0; row < newMainTable.getRowCount(); row++) {
                                if (row % 2 == 0) {
                                    sumPersonenMonate += transformer.formatStringToPercentageValueForScope((String) newMainTable.getValueAt(row, e.getColumn()));
                                } else {
                                    sumCostPerMonth += transformer.formatStringToDouble((String) newMainTable.getValueAt(row, e.getColumn()));
                                }
                            }
                            newSumTable.setValueAt(transformer.formatDoubleToPersonenMonate(sumPersonenMonate), 0, e.getColumn());
                            newSumTable.setValueAt(transformer.formatDoubleToString(sumCostPerMonth, 1), 1, e.getColumn());

                            double newTotalCost = 0;
                            for (int column = 0; column < newSumTable.getColumnCount(); column++) {
                                newTotalCost += transformer.formatStringToDouble((String) newSumTable.getValueAt(1, column));
                            }
                            totalCostLabel.setText("Gesamtpersonalkosten: " + transformer.formatDoubleToString(newTotalCost, 1));
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
                        DateFormat format = new SimpleDateFormat("MMMM-yyyy");
                        for (int row = 0; row < newMainTable.getRowCount(); row++) {
                            if (row % 2 == 0) {
                                int employeeID = employeeDataManager.getEmployeeByName((String) newRowsMainTable.getValueAt(row / 2, 0)).getId();
                                participationManager.removeProjectParticipation(projectId, employeeID);
                                for (int column = 0; column < newMainTable.getColumnCount(); column++) {
                                    if (column != 0) {
                                        double scope = transformer.formatStringToPercentageValueForScope((String) newMainTable.getValueAt(row, column));
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

    private void setUpDateSelection(JTable table) {
        TableColumn dateColumn = table.getColumnModel().getColumn(2);
        table.setDefaultEditor(LocalDate.class, new DateTableEditor());
        table.setDefaultRenderer(LocalDate.class, new DateTableEditor());
        dateColumn.setCellEditor(table.getDefaultEditor(LocalDate.class));
        dateColumn.setCellRenderer(table.getDefaultRenderer(LocalDate.class));
    }

    private void setUpNameSelection(JTable table) {
        TableColumn nameColumn = table.getColumnModel().getColumn(0);
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        ArrayList<String> employeeNames = new ArrayList<String>(List.of(employeeDataManager.getAllEmployeesNameList()));
        JComboBox nameCombobox = new JComboBox(employeeNames.toArray());
        nameCombobox.setBackground(Color.WHITE);
        nameColumn.setCellEditor(new DefaultCellEditor(nameCombobox));
    }

    private String[][] getParticipationTableValues(JTable participationTable) {
        String[][] tableValues = new String[participationTable.getRowCount()][participationTable.getColumnCount()];
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (int row = 0; row < participationTable.getRowCount(); row++) {
            for (int column = 0; column < participationTable.getColumnCount(); column++) {
                if (participationTable.getValueAt(row, column) == null || participationTable.getValueAt(row, column).equals("")) {
                    break;
                }
                if (column == 2) {
                    LocalDate temporaryDate = (LocalDate) participationTable.getValueAt(row, column);
                    String temporaryString = temporaryDate.format(dateTimeFormatter);
                    tableValues[row][column] = temporaryString;
                } else {
                    tableValues[row][column] = (String) participationTable.getValueAt(row, column);
                }
            }
        }
        return tableValues;
    }


}
