package excelchaos_view;

import excelchaos_view.components.tablecellrenderer.MultiLineTableCellRenderer;
import excelchaos_model.utility.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class InsertSHKPayRateTableView extends JPanel {
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JLabel nameOfTable;
    private JTextField tfNameOfTable;

    private JLabel hourlyPayLabel = new JLabel("Stundensätze");

    private JTable table;
    private JScrollPane scrollPane;
    private JButton cancelButton;
    private JButton saveAndExit;
    private GridBagConstraints constraints;

    private final String[] columnNames = {"Stundensatz bis 31.03.2022", "Stundensatz ab 01.04.2022", "Stundensatz ab 01.10.2022","Stundensatz ab 01.08.2023"};

    public void init(){
        setLayout(new BorderLayout());
        topPanelInit();
        centerPanelInit();
        bottomPanelInit();
    }


    private void topPanelInit() {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        nameOfTable = new JLabel("Name der Tabelle");
        tfNameOfTable = new JTextField();
        tfNameOfTable.setPreferredSize(new Dimension(150, 30));
        topPanel.add(nameOfTable);
        topPanel.add(tfNameOfTable);
        add(topPanel, BorderLayout.NORTH);
    }
    private void bottomPanelInit(){
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        cancelButton = new JButton("Abbrechen");
        saveAndExit = new JButton("Entgelttabelle speichern und Verlassen");
        bottomPanel.add(cancelButton);
        bottomPanel.add(saveAndExit);
        constraints.gridy = 1;
        constraints.insets.bottom = 10;
        constraints.weighty = 1.0;
        constraints.weightx = 0.2;
        centerPanel.add(bottomPanel,constraints);
    }

    private void centerPanelInit(){
        centerPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        centerPanel.setLayout(layout);
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        initTable(columnNames);
        constraints.insets.top = 10;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        centerPanel.add(hourlyPayLabel, constraints);
        //constraints.weightx = 0.2;
        constraints.ipadx=2000;
        constraints.ipady=87;
        centerPanel.add(scrollPane,constraints);
        add(centerPanel, BorderLayout.CENTER);
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getSaveAndExit() {
        return saveAndExit;
    }

    public void setActionListener(ActionListener l){
        cancelButton.addActionListener(l);
        saveAndExit.addActionListener(l);
    }

    public void initTable(String[] columns) {

        DefaultTableModel test = new DefaultTableModel(columns, 3);
        table = new JTable(test);
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
        headerTable.setValueAt("SHK Basisvergütung",0,0);
        headerTable.setValueAt("SHK erhöhter Stundensatz",1,0);
        headerTable.setValueAt("WHK",2,0);
        headerTable.setShowGrid(false);
        headerTable.setPreferredScrollableViewportSize(new Dimension(180, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        headerTable.setRowHeight(30);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new MultiLineTableCellRenderer());

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(30);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
        scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(headerTable);
    }
}
