package excelchaos_view;

import excelchaos_model.MultiLineTableCellRenderer;
import excelchaos_model.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class InsertPayRateTableView extends JPanel {
    private JPanel topPanel;

    private JLabel nameOfTable;

    private JLabel wageTypeLongtext;

    //JLabels for column names
    private JLabel percentage, e13St1VBLbefreit, e13St1VBLpflichtig, e13St2VBLbefreit, e13St2VBLpflichtig, e13St3VBLbefreit, e13St3VBLpflichtig, e13St4VBLbefreit, e13St4VBLpflichtig,
            e13St5VBLbefreit, e13St5VBLpflichtig, e13St6VBLbefreit, e13St6VBLpflichtig;
    //JLabels for row names
    private JLabel grundentgelt, AV_AG_Anteil_lfd_Entgelt, KV_AG_Anteil_lfd_Entgelt, ZusBei_AG_lfd_Entgelt, PV_AG_Anteil_lfd_Entgelt, RV_AG_Anteil_lfd_Entgelt, SV_Umlage_U2, Steuern_AG,
            ZV_Sanierungsbeitrag, ZV_Umlage_allgemein, VBL_Wiss_4_AG_Buchung, mtl_Kosten_ohne_JSZ, JSZ_als_monatliche_Zulage, mtl_Kosten_mit_JSZ,
            Jaehrliche_Arbeitgeberbelastung_inklusive_Jahressonderzahlung;

    private JTextField tfNameOfTable;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JTable table;
    private JScrollPane scrollPane;

    private JButton cancelButton;

    private JButton calculateCells;

    private JButton insertBaseMoney;

    private JButton insertMonthlyBonusMoney;

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
        tfNameOfTable = new JTextField();
        tfNameOfTable.setPreferredSize(new Dimension(300, 30));
        topPanel.add(nameOfTable);
        topPanel.add(tfNameOfTable);
        add(topPanel, BorderLayout.NORTH);
    }
    private void bottomPanelInit(){
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
        JPanel leftbuttons = new JPanel(new FlowLayout());
        JPanel rightbuttons = new JPanel(new FlowLayout());

        cancelButton = new JButton("Abbrechen");
        calculateCells = new JButton("Zellen berechnen");
        insertBaseMoney = new JButton("Grundentgelt einfügen");
        insertMonthlyBonusMoney = new JButton("JSZ als monatliche Zulage einfügen");
        saveAndExit = new JButton("Entgelttabelle speichern und Verlassen");

        rightbuttons.add(cancelButton);
        leftbuttons.add(insertBaseMoney);
        leftbuttons.add(insertMonthlyBonusMoney);
        leftbuttons.add(calculateCells);
        leftbuttons.add(saveAndExit);

        bottomPanel.add(leftbuttons);
        bottomPanel.add(Box.createHorizontalGlue());
        bottomPanel.add(rightbuttons);

        constraints.gridy = 4;
        constraints.insets.bottom = 20;
        constraints.weighty = 0.2;
        constraints.weightx = 0.2;
        centerPanel.add(bottomPanel,constraints);


    }

    public JButton getCalculateCells() {
        return calculateCells;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getSaveAndExit() {
        return saveAndExit;
    }

    public JButton getInsertBaseMoney() {
        return insertBaseMoney;
    }

    public JButton getInsertMonthlyBonusMoney() {
        return insertMonthlyBonusMoney;
    }

    public JTextField getTfNameOfTable() {
        return tfNameOfTable;
    }

    public JTable getTable() {
        return table;
    }

    public void setActionListener(ActionListener l){
        cancelButton.addActionListener(l);
        calculateCells.addActionListener(l);
        saveAndExit.addActionListener(l);
        insertBaseMoney.addActionListener(l);
        insertMonthlyBonusMoney.addActionListener(l);
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
        //init Column JLabels
        /*percentage = new JLabel("%-Satz");
        e13St1VBLbefreit = new JLabel("E13 St. 1 VBL-befreit");
        e13St1VBLpflichtig = new JLabel("E13 St. 1 VBL-pflichtig");
        e13St2VBLbefreit = new JLabel("E13 St. 2 VBL-befreit");
        e13St2VBLpflichtig = new JLabel("E13 St. 2 VBL-pflichtig");
        e13St3VBLbefreit = new JLabel("E13 St. 3 VBL-befreit");
        e13St3VBLpflichtig = new JLabel("E13 St. 3 VBL-pflichtig");
        e13St4VBLbefreit = new JLabel("E13 St. 4 VBL-befreit");
        e13St4VBLpflichtig = new JLabel("E13 St. 4 VBL-pflichtig");
        e13St5VBLbefreit = new JLabel("E13 St. 5 VBL-befreit");
        e13St5VBLpflichtig = new JLabel("E13 St. 5 VBL-pflichtig");
        e13St6VBLbefreit = new JLabel("E13 St. 6 VBL-befreit");
        e13St6VBLpflichtig = new JLabel("E13 St. 6 VBL-pflichtig");
        //init row JLabels
        grundentgelt = new JLabel("Grundentgelt");
        AV_AG_Anteil_lfd_Entgelt = new JLabel("AV-AG-Anteil, lfd. Entgelt");
        KV_AG_Anteil_lfd_Entgelt = new JLabel("KV-AG-Anteil, lfd. Entgelt");
        ZusBei_AG_lfd_Entgelt = new JLabel("ZusBei AG lfd. Entgelt");
        PV_AG_Anteil_lfd_Entgelt = new JLabel("PV-AG-Anteil, lfd. Entgelt");
        RV_AG_Anteil_lfd_Entgelt = new JLabel("RV-AG-Anteil, lfd. Entgelt");
        SV_Umlage_U2 = new JLabel("SV-Umlage U2");
        Steuern_AG = new JLabel("Steuern AG");
        ZV_Sanierungsbeitrag = new JLabel("ZV-Sanierungsbeitrag");
        ZV_Umlage_allgemein = new JLabel("ZV-Umlage, allgemein");
        VBL_Wiss_4_AG_Buchung = new JLabel("VBL Wiss 4% AG Buchung");
        mtl_Kosten_ohne_JSZ = new JLabel("mtl. Kosten ohne JSZ");
        JSZ_als_monatliche_Zulage = new JLabel("JSZ als monatliche Zulage");
        mtl_Kosten_mit_JSZ = new JLabel("mtl. Kosten mit JSZ");
        Jaehrliche_Arbeitgeberbelastung_inklusive_Jahressonderzahlung = new JLabel("<html>Jährliche Arbeitgeberbelastung <br>" +
                "inklusive Jahressonderzahlung</html>");
*/
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
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
        scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(headerTable);
    }



}
