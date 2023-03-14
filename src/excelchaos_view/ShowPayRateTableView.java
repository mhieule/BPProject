package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import excelchaos_model.database.SalaryTable;
import excelchaos_model.inputVerifier.PayRateTablePercentVerifier;
import excelchaos_model.inputVerifier.SalaryVerifier;
import excelchaos_model.utility.PayRateTableNameDateSeperator;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.components.tablecellrenderer.MultiLineTableCellRenderer;
import excelchaos_model.utility.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowPayRateTableView extends JPanel {
    private JPanel topPanel;

    private JLabel nameOfTable, startDate;

    private JLabel wageTypeLongtext;

    private JTextField tfNameOfTable;

    private DatePicker datePicker;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JTable showPayRatesTable;
    private JScrollPane scrollPane;

    private JButton cancelButton;
    private JButton saveAndExit;

    private GridBagConstraints constraints;

    public void init(String[] columns) {
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

    private void centerPanelInit(String[] columns) {
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
        constraints.ipadx = 2000;
        constraints.ipady = 462;
        centerPanel.add(scrollPane, constraints);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void initJLables() {
        wageTypeLongtext = new JLabel("             Lohnart-Langtext");
    }

    public void initTable(String[] columns) {

        DefaultTableModel defaultTableModel = new DefaultTableModel(columns, 15);
        showPayRatesTable = new JTable(defaultTableModel);
        showPayRatesTable.setDefaultEditor(Object.class, new CellEditor(new SalaryVerifier()));
        showPayRatesTable.getColumn("%-Satz").setCellEditor(new CellEditor(new PayRateTablePercentVerifier()));
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
                return showPayRatesTable.getRowCount();
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
                "inklusive Jahressonderzahlung</html>", 14, 0);
        headerTable.setShowGrid(false);
        headerTable.setPreferredScrollableViewportSize(new Dimension(180, 0));
        headerTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        headerTable.setRowHeight(30);
        headerTable.getColumnModel().getColumn(0).setCellRenderer(new MultiLineTableCellRenderer());

        showPayRatesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        showPayRatesTable.setRowHeight(30);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) showPayRatesTable.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnAdjuster tca = new TableColumnAdjuster(showPayRatesTable);
        tca.adjustColumns();
        scrollPane = new JScrollPane(showPayRatesTable);
        scrollPane.setRowHeaderView(headerTable);
    }

    public void insertPayRateValuesInTable(List<SalaryTable> salaryTables) {
        int column = 0;
        int row = 0;
        // columncount = 13, rowCount = 15
        for (SalaryTable salaryTable : salaryTables) {
            if (salaryTable.getGrundendgeld().compareTo(new BigDecimal(0)) == 0) {
                showPayRatesTable.setValueAt(null, 0, column);
            } else
                showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getGrundendgeld(), column), 0, column);
            showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getAv_ag_anteil_lfd_entgelt(), column), 1, column);
            showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getKv_ag_anteil_lfd_entgelt(), column), 2, column);
            showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getZusbei_af_lfd_entgelt(), column), 3, column);
            showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getPv_ag_anteil_lfd_entgelt(), column), 4, column);
            showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getRv_ag_anteil_lfd_entgelt(), column), 5, column);
            showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getSv_umlage_u2(), column), 6, column);
            showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getSteuern_ag(), column), 7, column);
            if (salaryTable.getZv_Sanierungsbeitrag().compareTo(new BigDecimal(0)) == 0) {
                showPayRatesTable.setValueAt(null, 8, column);
            } else
                showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getZv_Sanierungsbeitrag(), column), 8, column);
            if (salaryTable.getZv_umlage_allgemein().compareTo(new BigDecimal(0)) == 0) {
                showPayRatesTable.setValueAt(null, 9, column);
            } else
                showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getZv_umlage_allgemein(), column), 9, column);
            if (salaryTable.getVbl_wiss_4perc_ag_buchung().compareTo(new BigDecimal(0)) == 0) {
                showPayRatesTable.setValueAt(null, 10, column);
            } else
                showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getVbl_wiss_4perc_ag_buchung(), column), 10, column);
            if (salaryTable.getMtl_kosten_ohne_jsz().compareTo(new BigDecimal(0)) == 0) {
                showPayRatesTable.setValueAt(null, 11, column);
            } else
                showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getMtl_kosten_ohne_jsz(), column), 11, column);
            if (salaryTable.getJsz_als_monatliche_zulage().compareTo(new BigDecimal(0)) == 0) {
                showPayRatesTable.setValueAt(null, 12, column);
            } else
                showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getJsz_als_monatliche_zulage(), column), 12, column);
            if (salaryTable.getMtl_kosten_mit_jsz().compareTo(new BigDecimal(0)) == 0) {
                showPayRatesTable.setValueAt(null, 13, column);
            } else
                showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getMtl_kosten_mit_jsz(), column), 13, column);
            if (salaryTable.getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung().compareTo(new BigDecimal(0)) == 0) {
                showPayRatesTable.setValueAt(null, 14, column);
            } else
                showPayRatesTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(salaryTable.getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung(), column), 14, column);
            if (column < showPayRatesTable.getColumnCount() - 1) {
                column++;
            } else {
                PayRateTableNameDateSeperator nameDateSeperator = new PayRateTableNameDateSeperator();
                getTfNameOfTable().setText(nameDateSeperator.seperateName(salaryTable.getTable_name()));
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate date = LocalDate.parse(nameDateSeperator.seperateDateAsString(salaryTable.getTable_name()), dateTimeFormatter);
                getDatePicker().setDate(date);
                break;
            }
            if (row < showPayRatesTable.getRowCount()) {
                row++;
            } else break;


        }

    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getSaveAndExit() {
        return saveAndExit;
    }

    public JTable getShowPayRatesTable() {
        return showPayRatesTable;
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
