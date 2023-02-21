package excelchaos_view;

import excelchaos_model.MultiLineTableCellRenderer;
import excelchaos_model.utility.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;

public class ProjectParticipationView extends JPanel {

    private JPanel mainPanel;

    public void init(){

        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVisible(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane,BorderLayout.CENTER);
        GridLayout gridLayout = new GridLayout(0,1,0,10);
        mainPanel.setLayout(gridLayout);
        /*String[] months = {"Februar 23", "März 23", "April 23", "Mai 23", "Juni 23", "Juli 23", "August 23", "September 23", "Oktober 23","November 23","Dezember 23", "Gesamtkosten pro Person"};
        String[] names = {"wdawadwwad wdadw", "adwdawadaad", "Max Mustermann", "Fabian siuuu", "Alex rex", "wdawdw wdadwa", "Test boy","Troolol"};
        String[][] tableData = new String[names.length*2][months.length];
        String[][] summedTableData = new String[2][months.length];
        String[] projectCostPerson = new String[names.length];
        String totalcost = "235555€";
        setUpProjectPanel("Test",months,names,tableData,summedTableData,totalcost);
        setUpProjectPanel("Test",months,names,tableData,summedTableData,totalcost);
        setUpProjectPanel("Test",months,names,tableData,summedTableData,totalcost);*/
    }

    public void setUpProjectPanel(String projectName, String[] monthColumns, String[] nameRows, String[][] tableData, String[][] summedTableData, String totalCost){

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

        projectPanel.add(initMainTable(monthColumns,tableData,nameRows),BorderLayout.CENTER);

        projectPanel.add(initSumTable(summedTableData,monthColumns),BorderLayout.SOUTH);
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
        scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(headerTable);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        scrollPane.setBorder(createEmptyBorder());
        return scrollPane;
    }

}
