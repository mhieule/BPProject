package excelchaos_view;

import excelchaos_model.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.jar.JarEntry;

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

    private GridBagConstraints constraints;

    private final int TEXT_FIELD_WIDTH = 100;
    private final int TEXT_FIELD_HEIGHT = 25;

    public void init() {
        setLayout(new BorderLayout());
        topPanel = new JPanel();
        centerPanel = new JPanel();
        bottomPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        centerPanel.setLayout(new BorderLayout());


        topPanelInit();
        initJLables();
        initTable();
        centerPanel.add(scrollPane);


        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void topPanelInit() {
        nameOfTable = new JLabel("Name der Tabelle");
        tfNameOfTable = new JTextField();
        tfNameOfTable.setPreferredSize(new Dimension(150, 30));
        topPanel.add(nameOfTable);
        topPanel.add(tfNameOfTable);
    }
    private void initJLables(){
        wageTypeLongtext = new JLabel("Lohnart-Langtext");
        //init Column JLabels
        percentage = new JLabel("%-Satz");
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
        Jaehrliche_Arbeitgeberbelastung_inklusive_Jahressonderzahlung = new JLabel("Jährliche Arbeitgeberbelastung \n" +
                "inklusive Jahressonderzahlung");

    }
    public void initTable(){
        String[] columns = {
                "%-Satz","E13 St. 1 VBL-befreit","E13 St. 1 VBL-pflichtig","E13 St. 2 VBL-befreit","E13 St. 2 VBL-pflichtig","E13 St. 3 VBL-befreit","E13 St. 3 VBL-pflichtig",
                "E13 St. 4 VBL-befreit","E13 St. 4 VBL-pflichtig","E13 St. 5 VBL-befreit","E13 St. 5 VBL-pflichtig","E13 St. 6 VBL-befreit","E13 St. 6 VBL-pflichtig"
        };
        DefaultTableModel test = new DefaultTableModel(columns,14);
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
        headerTable.setValueAt(grundentgelt.getText(),0,0);
        headerTable.setValueAt(AV_AG_Anteil_lfd_Entgelt.getText(),1,0);
        headerTable.setValueAt(KV_AG_Anteil_lfd_Entgelt.getText(),2,0);
        headerTable.setValueAt(ZusBei_AG_lfd_Entgelt.getText(),3,0);
        headerTable.setValueAt(PV_AG_Anteil_lfd_Entgelt.getText(),4,0);
        headerTable.setValueAt(RV_AG_Anteil_lfd_Entgelt.getText(),5,0);
        headerTable.setValueAt(SV_Umlage_U2.getText(),6,0);
        headerTable.setValueAt(Steuern_AG.getText(),7,0);
        headerTable.setValueAt(ZV_Sanierungsbeitrag.getText(),8,0);
        headerTable.setValueAt(ZV_Umlage_allgemein.getText(),9,0);
        headerTable.setValueAt(VBL_Wiss_4_AG_Buchung.getText(),10,0);
        headerTable.setValueAt(mtl_Kosten_ohne_JSZ.getText(),11,0);
        headerTable.setValueAt(JSZ_als_monatliche_Zulage.getText(),12,0);
        headerTable.setValueAt(mtl_Kosten_mit_JSZ.getText(),13,0);
        headerTable.setShowGrid(false);
        //headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        headerTable.setPreferredScrollableViewportSize(new Dimension(180, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        headerTable.setRowHeight(30);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable x, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                boolean selected = table.getSelectionModel().isSelectedIndex(row);
                Component component = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, value, false, false, -1, -2);
                ((JLabel) component).setHorizontalAlignment(SwingConstants.LEFT);
                if (selected) {
                } else {
                    component.setFont(component.getFont().deriveFont(Font.PLAIN));
                }
                return component;
            }
        });
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(30);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();
        scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(headerTable);
    }

}
