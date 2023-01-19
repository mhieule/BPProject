package excelchaos_controller;

import excelchaos_model.SalaryTable;
import excelchaos_model.SalaryTableManager;
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
        List<SalaryTable> salaryTables = manager.getSalaryTable(tableTitle);
        int column = 0;
        int row = 0;
        // columncount = 13, rowCount = 15
        System.out.println(salaryTables.size() + "Here");
        for (SalaryTable salaryTable : salaryTables) {
                System.out.println(salaryTable.getGrundendgeld() + "Grundentgeld anzeigen");
                showPayRateTableView.getTable().setValueAt(salaryTable.getGrundendgeld(), 0, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getAv_ag_anteil_lfd_entgelt(), 1, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getKv_ag_anteil_lfd_entgelt(), 2, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getZusbei_af_lfd_entgelt(), 3, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getPv_ag_anteil_lfd_entgelt(), 4, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getRv_ag_anteil_lfd_entgelt(), 5, column);
                System.out.println(salaryTable.getRv_ag_anteil_lfd_entgelt() + "Rvag");
                showPayRateTableView.getTable().setValueAt(salaryTable.getSv_umlage_u2(), 6, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getSteuern_ag(), 7, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getZv_Sanierungsbeitrag(), 8, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getZv_umlage_allgemein(), 9, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getVbl_wiss_4perc_ag_buchung(), 10, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getMtl_kosten_ohne_jsz(), 11, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getJsz_als_monatliche_zulage(), 12, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getMtl_kosten_mit_jsz(), 13, column);
                showPayRateTableView.getTable().setValueAt(salaryTable.getJaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung(), 14, column);
            System.out.println("Dont");
                if(column < showPayRateTableView.getTable().getColumnCount()-1){
                    column++;
                    System.out.println(column);
                } else {
                    break;
                }
                if (row < showPayRateTableView.getTable().getRowCount()){
                    row++;
                } else break;



        }
    }

    private boolean hasAAndB() {
        if (tableTitle.contains("1A") || tableTitle.contains("1B")) {
            return true;
        } else return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
