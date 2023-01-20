package excelchaos_controller;

import excelchaos_model.SalaryTable;
import excelchaos_model.SalaryTableManager;
import excelchaos_model.StringAndDoubleTransformationForDatabase;
import excelchaos_view.ShowPayRateTableView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowPayRateTableController implements ActionListener {

    private ShowPayRateTableView showPayRateTableView;

    private MainFrameController frameController;

    private SalaryTableManager manager;

    private String tableTitle, paygrade;

    private final String[] columns13WithAAndB = {
            "%-Satz", "E13 St. 1A VBL-befreit", "E13 St. 1A VBL-pflichtig", "E13 St. 1B VBL-befreit", "E13 St. 1B VBL-pflichtig", "E13 St. 2 VBL-befreit", "E13 St. 2 VBL-pflichtig", "E13 St. 3 VBL-befreit", "E13 St. 3 VBL-pflichtig",
            "E13 St. 4 VBL-befreit", "E13 St. 4 VBL-pflichtig", "E13 St. 5 VBL-befreit", "E13 St. 5 VBL-pflichtig", "E13 St. 6 VBL-befreit", "E13 St. 6 VBL-pflichtig"
    };
    private final String[] columns13WithoutAAndB = {
            "%-Satz", "E13 St. 1 VBL-befreit", "E13 St. 1 VBL-pflichtig", "E13 St. 2 VBL-befreit", "E13 St. 2 VBL-pflichtig", "E13 St. 3 VBL-befreit", "E13 St. 3 VBL-pflichtig",
            "E13 St. 4 VBL-befreit", "E13 St. 4 VBL-pflichtig", "E13 St. 5 VBL-befreit", "E13 St. 5 VBL-pflichtig", "E13 St. 6 VBL-befreit", "E13 St. 6 VBL-pflichtig"
    };
    private final String[] columns14WithAAndB = {
            "%-Satz", "E14 St. 1A VBL-befreit", "E14 St. 1A VBL-pflichtig", "E14 St. 1B VBL-befreit", "E14 St. 1B VBL-pflichtig", "E14 St. 2 VBL-befreit", "E14 St. 2 VBL-pflichtig", "E14 St. 3 VBL-befreit", "E14 St. 3 VBL-pflichtig",
            "E14 St. 4 VBL-befreit", "E14 St. 4 VBL-pflichtig", "E14 St. 5 VBL-befreit", "E14 St. 5 VBL-pflichtig", "E14 St. 6 VBL-befreit", "E14 St. 6 VBL-pflichtig"
    };
    private final String[] columns14WithoutAAndB = {
            "%-Satz", "E14 St. 1 VBL-befreit", "E14 St. 1 VBL-pflichtig", "E14 St. 2 VBL-befreit", "E14 St. 2 VBL-pflichtig", "E14 St. 3 VBL-befreit", "E14 St. 3 VBL-pflichtig",
            "E14 St. 4 VBL-befreit", "E14 St. 4 VBL-pflichtig", "E14 St. 5 VBL-befreit", "E14 St. 5 VBL-pflichtig", "E14 St. 6 VBL-befreit", "E14 St. 6 VBL-pflichtig"
    };

    public ShowPayRateTableController(MainFrameController mainFrameController, SalaryTableManager salaryManager, String tableName, String actualPaygrade) {
        frameController = mainFrameController;
        manager = salaryManager;
        showPayRateTableView = new ShowPayRateTableView();
        tableTitle = tableName;
        paygrade = actualPaygrade;
        showPayRateTableView.init(determineTableColumns());
        showPayRateTableView.setActionListener(this);
        showPayRateTableView(frameController);
    }

    public void showPayRateTableView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(tableTitle) == -1) {
            //SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
            mainFrameController.addTab(tableTitle, showPayRateTableView);
            //mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(tableTitle));
            //SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
        }
    }

    private String[] determineTableColumns() {
        if (paygrade.equals("E13")) {
            if (hasAAndB()) {
                String[] result = columns13WithAAndB;
                return result;
            } else {
                String[] result = columns13WithoutAAndB;
                return result;
            }
        } else {
            if (hasAAndB()) {
                String[] result = columns14WithAAndB;
                return result;
            } else {
                String[] result = columns13WithoutAAndB;
                return result;
            }
        }
    }

    public void insertValuesInTable() {
        StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
        List<SalaryTable> salaryTables = manager.getSalaryTable(tableTitle);
        int column = 0;
        int row = 0;
        // columncount = 13, rowCount = 15
        for (SalaryTable salaryTable : salaryTables) {
            if (salaryTable.getGrundendgeld() == 0) {
                showPayRateTableView.getTable().setValueAt(null, 0, column);
            } else showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getGrundendgeld(),column), 0, column);
            showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getAv_ag_anteil_lfd_entgelt(),column), 1, column);
            showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getKv_ag_anteil_lfd_entgelt(),column), 2, column);
            showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getZusbei_af_lfd_entgelt(),column), 3, column);
            showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getPv_ag_anteil_lfd_entgelt(),column), 4, column);
            showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getRv_ag_anteil_lfd_entgelt(),column), 5, column);
            showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getSv_umlage_u2(),column), 6, column);
            showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getSteuern_ag(),column), 7, column);
            if (salaryTable.getZv_Sanierungsbeitrag() == 0) {
                showPayRateTableView.getTable().setValueAt(null, 8, column);
            } else showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getZv_Sanierungsbeitrag(),column), 8, column);
            if (salaryTable.getZv_umlage_allgemein() == 0) {
                showPayRateTableView.getTable().setValueAt(null, 9, column);
            } else showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getZv_umlage_allgemein(),column), 9, column);
            if (salaryTable.getVbl_wiss_4perc_ag_buchung() == 0) {
                showPayRateTableView.getTable().setValueAt(null, 10, column);
            } else showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getVbl_wiss_4perc_ag_buchung(),column), 10, column);
            if (salaryTable.getMtl_kosten_ohne_jsz() == 0) {
                showPayRateTableView.getTable().setValueAt(null, 11, column);
            } else showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getMtl_kosten_ohne_jsz(),column), 11, column);
            if (salaryTable.getJsz_als_monatliche_zulage() == 0) {
                showPayRateTableView.getTable().setValueAt(null, 12, column);
            } else showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getJsz_als_monatliche_zulage(),column), 12, column);
            if (salaryTable.getMtl_kosten_mit_jsz() == 0) {
                showPayRateTableView.getTable().setValueAt(null, 13, column);
            } else showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getMtl_kosten_mit_jsz(),column), 13, column);
            if (salaryTable.getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung() == 0) {
                showPayRateTableView.getTable().setValueAt(null, 14, column);
            } else showPayRateTableView.getTable().setValueAt(transformer.formatDoubleToString(salaryTable.getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung(),column), 14, column);
            if (column < showPayRateTableView.getTable().getColumnCount() - 1) {
                column++;
            } else {
                showPayRateTableView.getTfNameOfTable().setText(salaryTable.getTable_name());
                break;
            }
            if (row < showPayRateTableView.getTable().getRowCount()) {
                row++;
            } else break;


        }
    }

    private double[][] prepareDatabaseInsertion(){
        StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
        double[][] values = new double[showPayRateTableView.getTable().getRowCount()][showPayRateTableView.getTable().getColumnCount()];
        for (int row = 0; row < showPayRateTableView.getTable().getRowCount(); row++){
            for (int column = 0; column < showPayRateTableView.getTable().getColumnCount(); column++) {
                if(showPayRateTableView.getTable().getValueAt(row,column) == null){
                    values[row][column] = 0;
                } else {
                    values[row][column] = transformer.transformStringToDouble((String) showPayRateTableView.getTable().getValueAt(row,column));
                }
            }
        }
        return values;
    }

    private void saveEditedValues(){
        double[][] values = prepareDatabaseInsertion();
        List<SalaryTable> salaryTables = manager.getSalaryTable(tableTitle);
        int column = 0;
        for (SalaryTable salaryTable : salaryTables) {
            salaryTable.setGrundendgeld(values[0][column]);
            salaryTable.setAv_ag_anteil_lfd_entgelt(values[1][column]);
            salaryTable.setKv_ag_anteil_lfd_entgelt(values[2][column]);
            salaryTable.setZusbei_af_lfd_entgelt(values[3][column]);
            salaryTable.setPv_ag_anteil_lfd_entgelt(values[4][column]);
            salaryTable.setRv_ag_anteil_lfd_entgelt(values[5][column]);
            salaryTable.setSv_umlage_u2(values[6][column]);
            salaryTable.setSteuern_ag(values[7][column]);
            salaryTable.setZv_Sanierungsbeitrag(values[8][column]);
            salaryTable.setZv_umlage_allgemein(values[9][column]);
            salaryTable.setVbl_wiss_4perc_ag_buchung(values[10][column]);
            salaryTable.setMtl_kosten_ohne_jsz(values[11][column]);
            salaryTable.setJsz_als_monatliche_zulage(values[12][column]);
            salaryTable.setMtl_kosten_mit_jsz(values[13][column]);
            salaryTable.setJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung(values[14][column]);
            System.out.println("Here");
            if (column < showPayRateTableView.getTable().getColumnCount() - 1) {
                column++;
            } else {
                salaryTable.setTable_name(showPayRateTableView.getTfNameOfTable().getText());
                break;
            }
        /*SalaryTableManager manager = new SalaryTableManager();
        for(int column = 0; column < values[0].length;column++){
            String tableName = tableTitle;
            double grundentgelt = values[0][column];
            double av_ag_anteil_lfd_entgelt = values[1][column];
            double kv_ag_anteil_lfd_entgelt = values[2][column];
            double zusbei_af_lfd_entgelt = values[3][column];
            double pv_ag_anteil_lfd_entgelt = values[4][column];
            double rv_ag_anteil_lfd_entgelt = values[5][column];
            double sv_umlage_u2 = values[6][column];
            double steuern_ag = values[7][column];
            double zv_Sanierungsbeitrag = values[8][column];
            double zv_umlage_allgemein = values[9][column];
            double vbl_wiss_4perc_ag_buchung = values[10][column];
            double mtl_kosten_ohne_jsz = values[11][column];
            double jsz_als_monatliche_zulage = values[12][column];
            double mtl_kosten_mit_jsz = values[13][column];
            double jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung = values[14][column];
            String grade = paygrade;
            SalaryTable salaryTable = new SalaryTable(tableName,grundentgelt,av_ag_anteil_lfd_entgelt,kv_ag_anteil_lfd_entgelt,zusbei_af_lfd_entgelt,pv_ag_anteil_lfd_entgelt,rv_ag_anteil_lfd_entgelt,sv_umlage_u2,steuern_ag,zv_Sanierungsbeitrag,zv_umlage_allgemein,vbl_wiss_4perc_ag_buchung,mtl_kosten_ohne_jsz,jsz_als_monatliche_zulage,mtl_kosten_mit_jsz,jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung,grade);
            manager.addSalaryTable(salaryTable);*/
        }
    }

    private boolean hasAAndB() {
        if (tableTitle.contains("1A") || tableTitle.contains("1B")) {
            return true;
        } else return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showPayRateTableView.getCancelButton()) {
            frameController.getTabs().removeTabNewWindow(showPayRateTableView);
        } else if (e.getSource() == showPayRateTableView.getSaveAndExit()) {
            saveEditedValues();
            frameController.getTabs().removeTabNewWindow(showPayRateTableView);
        }

    }
}
