package excelchaos_view;

import com.github.lgooddatepicker.tableeditors.DateTableEditor;
import excelchaos_model.*;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_model.utility.TableColumnAdjuster;

import javax.swing.*;
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

    private JPanel mainPanel;

    private ProjectParticipationDataModel projectParticipationDataModel = new ProjectParticipationDataModel();


    public void init(){

        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVisible(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane,BorderLayout.CENTER);
        GridLayout gridLayout = new GridLayout(0,1,0,10);
        mainPanel.setLayout(gridLayout);

    }

    public void setUpProjectPanel(String projectName, String[] monthColumns, String[] nameRows, String[][] tableData, String[][] summedTableData, String totalCost,int projectId){

        JPanel projectPanel = new JPanel();
        projectPanel.setLayout(new BorderLayout());

        JPanel projectNamePanel = new JPanel();
        projectNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel projectNameLabel = new JLabel(projectName);
        projectNameLabel.setFont(new Font("Dialog", Font.BOLD, 22));
        projectNamePanel.add(projectNameLabel);
        projectNamePanel.add(Box.createHorizontalStrut(100));

        JButton addPersonButton = new JButton("Person zum Projekt hinzufügen");
        projectNamePanel.add(addPersonButton);

        projectNamePanel.add(Box.createHorizontalStrut(500));
        JLabel totalCostLabel = new JLabel("Gesamtpersonalkosten: " + totalCost);
        totalCostLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        projectNamePanel.add(totalCostLabel);

        projectPanel.add(projectNamePanel,BorderLayout.NORTH);

        JScrollPane mainScrollPane = initMainTable(monthColumns,tableData,nameRows);
        JScrollPane sideScrollPane = initSumTable(summedTableData,monthColumns);

        projectPanel.add(mainScrollPane,BorderLayout.CENTER);
        projectPanel.add(sideScrollPane,BorderLayout.SOUTH);

        addPersonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpAddParticipationView(projectPanel,projectId,monthColumns,mainScrollPane,sideScrollPane);
            }
        });

        mainPanel.add(projectPanel);
    }

    public void setUpTotalWorkPanel(String[] monthColumnsForAllProjects, String[] summedWork, String[] namesInSelectedProjects){

    }

    public JScrollPane initMainTable(String[] months, String[][] data, String[] rows) {
        JTable table;
        JScrollPane scrollPane;
        DefaultTableModel mainTableModel = new DefaultTableModel(data,months);
        table = new JTable(mainTableModel);
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
            headerTable.setValueAt(rows[row],row,0);
        }
        headerTable.setShowGrid(false);
        headerTable.setPreferredScrollableViewportSize(new Dimension(180, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        headerTable.setRowHeight(60);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new MultiLineTableCellRenderer());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(30);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment( JLabel.RIGHT );
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(headerTable);
        return scrollPane;

    }


    public JScrollPane initSumTable(String[][] summedTableData,String[] months){
        JTable table;
        JScrollPane scrollPane;
        DefaultTableModel mainTableModel = new DefaultTableModel(summedTableData,months);
        table = new JTable(mainTableModel);
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
        headerTable.setValueAt("Personenmonate",0,0);
        headerTable.setValueAt("Gesamtkosten pro Monat",1,0);
        headerTable.setShowGrid(false);
        headerTable.setPreferredScrollableViewportSize(new Dimension(180, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        headerTable.setRowHeight(30);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new MultiLineTableCellRenderer());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(30);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment( JLabel.RIGHT );
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(headerTable);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        scrollPane.setBorder(createEmptyBorder());
        return scrollPane;
    }


    private void setUpAddParticipationView(JPanel projectPanel,int projectId,String[] monthColumns,JScrollPane oldMain,JScrollPane oldSide){
        JDialog participationDialog = new JDialog();

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        String[] participationColumns= new String[]{"Name", "Beschäftigungsumfang", "Datum"};
        DefaultTableModel participationModel = new DefaultTableModel(null, participationColumns);
        participationModel.setRowCount(40);
        JTable projectParticipationTable = new JTable(participationModel);
        setUpNameSelection(projectParticipationTable);
        setUpDateSelection(projectParticipationTable);
        JScrollPane participationScrollpane = new JScrollPane(projectParticipationTable);
        participationScrollpane.setVisible(true);
        participationScrollpane.setViewportView(projectParticipationTable);
        tablePanel.add(participationScrollpane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton closeButton = new JButton("Abbrechen");
        JButton submitButton = new JButton("Eingaben hinzufügen");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                participationDialog.dispose();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectParticipationManager projectParticipationManager = new ProjectParticipationManager();
                EmployeeDataManager employeeDataManager = new EmployeeDataManager();
                String[][] tableValues = getParticipationTableValues(projectParticipationTable);
                ProjectParticipation projectParticipation;
                StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
                DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                for (int row = 0; row < tableValues.length; row++) {  //TODO Umwandlungsmethoden für SHK Angestellte implementieren
                    if (tableValues[row][0] != null && tableValues[row][1] != null && tableValues[row][2] != null) {
                        int personId = employeeDataManager.getEmployeeByName(tableValues[row][0]).getId();
                        Date date = null;
                        try {
                            date = format.parse(tableValues[row][2]);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        projectParticipation = new ProjectParticipation(projectId, personId, transformer.formatStringToPercentageValueForScope(tableValues[row][1]),date);
                        projectParticipationManager.addProjectParticipation(projectParticipation);
                    }

                }

                String[] newNames = projectParticipationDataModel.getPersonNamesForProject(projectId);
                String[][] summedTableDate = null;
                String[][] newTableData = null;
                try {
                    newTableData = projectParticipationDataModel.getTableData(projectId,newNames.length,monthColumns,newNames);
                    summedTableDate = projectParticipationDataModel.getSummedTableData(projectId, newNames.length,monthColumns);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                JScrollPane newMainScrollPane = initMainTable(monthColumns,newTableData,newNames);
                JScrollPane newSideScrollPane = initSumTable(summedTableDate,monthColumns);

                projectPanel.remove(oldMain);
                projectPanel.remove(oldSide);

                projectPanel.add(newMainScrollPane,BorderLayout.CENTER);
                projectPanel.add(newSideScrollPane,BorderLayout.SOUTH);

                projectPanel.revalidate();
                projectPanel.repaint();
                mainPanel.revalidate();
                mainPanel.repaint();
                participationDialog.dispose();
            }
        });

        buttonPanel.add(submitButton);

        buttonPanel.add(closeButton);




        participationDialog.setLayout(new BorderLayout());
        participationDialog.add(tablePanel,BorderLayout.CENTER);
        participationDialog.add(buttonPanel,BorderLayout.SOUTH);
        participationDialog.setName("Mitarbeiter hinzufügen");
        participationDialog.pack();
        participationDialog.setLocationRelativeTo(null);
        participationDialog.setAlwaysOnTop(true);
        participationDialog.setVisible(true);




    }
    private void setUpDateSelection(JTable table){
        TableColumn dateColumn = table.getColumnModel().getColumn(2);
        table.setDefaultEditor(LocalDate.class, new DateTableEditor());
        table.setDefaultRenderer(LocalDate.class, new DateTableEditor());
        dateColumn.setCellEditor(table.getDefaultEditor(LocalDate.class));
        dateColumn.setCellRenderer(table.getDefaultRenderer(LocalDate.class));
    }
    private void setUpNameSelection(JTable table){
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
                    LocalDate temporaryDate = (LocalDate) participationTable.getValueAt(row,column);
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
