package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import excelchaos_model.MultiLineTableCellRenderer;
import excelchaos_model.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class ShowPayRateTableView extends JPanel {
    private JPanel topPanel;

    private JLabel nameOfTable,startDate;

    private JLabel wageTypeLongtext;

    private JTextField tfNameOfTable;

    private DatePicker datePicker;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JTable table;
    private JScrollPane scrollPane;

    private JButton cancelButton;
    private JButton saveAndExit;

    private GridBagConstraints constraints;

    public void init(String [] columns) {
        setLayout(new BorderLayout());
        topPanelInit();
        centerPanelInit(columns);
        bottomPanelInit();
    }
    private void topPanelInit() {
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        nameOfTable = new JLabel("Name der Tabelle");
        startDate = new JLabel("Gültig ab");
        datePicker = new DatePicker();
        tfNameOfTable = new JTextField();
        tfNameOfTable.setPreferredSize(new Dimension(300, 30));
        topPanel.add(nameOfTable);
        topPanel.add(tfNameOfTable);
        topPanel.add(startDate);
        topPanel.add(datePicker);
        add(topPanel, BorderLayout.NORTH);
    }
    private void bottomPanelInit() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        JPanel leftbuttons = new JPanel(new FlowLayout());
        JPanel rightbuttons = new JPanel(new FlowLayout());

        cancelButton = new JButton("Abbrechen");
        saveAndExit = new JButton("Entgelttabelle speichern und Verlassen");

        rightbuttons.add(cancelButton);
        leftbuttons.add(saveAndExit);
        bottomPanel.add(leftbuttons);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(rightbuttons);

        constraints.gridy = 4;
        constraints.insets.bottom = 20;
        constraints.weighty = 0.2;
        constraints.weightx = 0.2;
        centerPanel.add(bottomPanel, constraints);
    }
    private void centerPanelInit(String[] columns){
        centerPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        centerPanel.setLayout(layout);
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 0;
        initJLables();
        initTable(columns);
        constraints.insets.top = 10;
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        centerPanel.add(wageTypeLongtext, constraints);
        constraints.ipadx=2000;
        constraints.ipady=462;
        centerPanel.add(scrollPane,constraints);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void initJLables() {
        wageTypeLongtext = new JLabel("             Lohnart-Langtext");
    }

    public void initTable(String[] columns) {

        DefaultTableModel test = new DefaultTableModel(columns, 15);
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
        headerTable.setValueAt("Grundentgelt", 0, 0);
        headerTable.setValueAt("AV-AG-Anteil, lfd. Entgelt", 1, 0);
        headerTable.setValueAt("KV-AG-Anteil, lfd. Entgelt", 2, 0);
        headerTable.setValueAt("ZusBei AG lfd. Entgelt", 3, 0);
        headerTable.setValueAt("PV-AG-Anteil, lfd. Entgelt", 4, 0);
        headerTable.setValueAt("RV-AG-Anteil, lfd. Entgelt", 5, 0);
        headerTable.setValueAt("SV-Umlage U2", 6, 0);
        headerTable.setValueAt("Steuern AG", 7, 0);
        headerTable.setValueAt("ZV-Sanierungsbeitrag", 8, 0);
        headerTable.setValueAt("ZV-Umlage, allgemein", 9, 0);
        headerTable.setValueAt("VBL Wiss 4% AG Buchung", 10, 0);
        headerTable.setValueAt("mtl. Kosten ohne JSZ", 11, 0);
        headerTable.setValueAt("JSZ als monatliche Zulage", 12, 0);
        headerTable.setValueAt("mtl. Kosten mit JSZ", 13, 0);
        headerTable.setValueAt("<html>Jährliche Arbeitgeberbelastung <br>" +
                "inklusive Jahressonderzahlung</html>",14,0);
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
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getSaveAndExit() {
        return saveAndExit;
    }

    public JTable getTable() {
        return table;
    }

    public JTextField getTfNameOfTable() {
        return tfNameOfTable;
    }

    public void setActionListener(ActionListener l) {
        cancelButton.addActionListener(l);
        saveAndExit.addActionListener(l);
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
}
