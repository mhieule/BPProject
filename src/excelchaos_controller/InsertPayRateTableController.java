package excelchaos_controller;

import excelchaos_model.PayRateTableCalculationModel;
import excelchaos_model.SalaryTable;
import excelchaos_model.SalaryTableManager;
import excelchaos_model.StringAndDoubleTransformationForDatabase;
import excelchaos_view.InsertPayRateTableView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertPayRateTableController implements ActionListener {

    private InsertPayRateTableView insertPayRateTableView;
    private MainFrameController frameController;
    private InsertBaseMoneyDialogController insertBaseMoneyDialogController;
    private String title;

    private PayRateTableCalculationModel model;

    private PayRateTablesController payRateController;
    private String[] baseMoneyStringValues;

    private String[] bonusMoneyStringValues;


    public InsertPayRateTableController(MainFrameController mainFrameController,PayRateTablesController payRateTablesController, String name, String[] columnNames, boolean typeOfTable) {
        frameController = mainFrameController;
        insertPayRateTableView = new InsertPayRateTableView();
        payRateController = payRateTablesController;
        title = name;
        insertPayRateTableView.init(columnNames);
        insertPayRateTableView.setActionListener(this);

    }

    public void showInsertPayRateTableView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            //SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
            mainFrameController.addTab(title, insertPayRateTableView);
            //mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
            //SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
        }
    }


    public String getTitle() {
        return title;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertPayRateTableView.getCancelButton()) {
            frameController.getTabs().removeTabNewWindow(insertPayRateTableView);
        } else if (e.getSource() == insertPayRateTableView.getCalculateCells()) {
            insertResultsInTable();
            insertMonthlyCostWithoutYearBonus();
            insertMonthlyCostWithYearBonus();
            insertLastRow();
        } else if (e.getSource() == insertPayRateTableView.getInsertBaseMoney()) {
            insertBaseMoneyDialogController = new InsertBaseMoneyDialogController(frameController, this, insertPayRateTableView.getInsertBaseMoney());
        } else if (e.getSource() == insertPayRateTableView.getInsertMonthlyBonusMoney()) {
            insertBaseMoneyDialogController = new InsertBaseMoneyDialogController(frameController, this, insertPayRateTableView.getInsertMonthlyBonusMoney());
        } else if (e.getSource() == insertPayRateTableView.getSaveAndExit()) {
            String tableName = insertPayRateTableView.getTfNameOfTable().getText();
            String paygrade = determinePayGrade();
            insertValuesInDatabase(tableName,paygrade,prepareTableForDatabaseInsertion());
            payRateController.updateview();
            frameController.getTabs().removeTabNewWindow(insertPayRateTableView);

        }
    }

    private void insertMonthlyCostWithoutYearBonus() {
        model.calculateMonthlyCostWithoutYearBonus();
        String[] monthlyCostWithoutBonusString = model.convertMonthlyCostWithoutYearBonusToString();
        for (int columns = 0; columns < insertPayRateTableView.getTable().getColumnCount() - 1; columns++) {
            insertPayRateTableView.getTable().setValueAt(monthlyCostWithoutBonusString[columns], 11, columns + 1);
        }
    }


    private void insertMonthlyCostWithYearBonus() {
        model.calculateMonthlyCostWithYearBonus();
        String[] monthlyCostWithBonusString = model.convertMonthlyCostWithYearBonusToString();
        for (int columns = 0; columns < insertPayRateTableView.getTable().getColumnCount() - 1; columns++) {
            insertPayRateTableView.getTable().setValueAt(monthlyCostWithBonusString[columns], 13, columns + 1);
        }
    }

    private void insertLastRow() {
        model.calculateLastRow();
        String[] lastRow = model.convertLastRowResultsToString();
        for (int columns = 0; columns < insertPayRateTableView.getTable().getColumnCount() - 1; columns++) {
            insertPayRateTableView.getTable().setValueAt(lastRow[columns], 14, columns + 1);
        }
    }

    private void insertResultsInTable() {
        String[] percentageStringValues = getPercentageStringFromTable();
        model = new PayRateTableCalculationModel(baseMoneyStringValues, percentageStringValues, bonusMoneyStringValues);
        model.doStringOperations();
        String[][] stringResults = model.calculateResults();
        stringResults = model.rearrangeStringFormat(stringResults);
        for (int k = 0; k < insertPayRateTableView.getTable().getRowCount() - 5; k++) {
            for (int l = 0; l < insertPayRateTableView.getTable().getColumnCount() - 1; l++) {
                insertPayRateTableView.getTable().setValueAt(stringResults[k][l], k + 1, l + 1);
                if ((((l + 1) % 2) == 0) && (k + 1 == 10)) {
                    insertPayRateTableView.getTable().setValueAt("", k + 1, l + 1);
                } else if ((((l + 1) % 2) == 1) && ((k + 1 == 9) || (k + 1 == 8))) {
                    insertPayRateTableView.getTable().setValueAt("", k + 1, l + 1);
                }
            }
        }
    }


    public String[] getPercentageStringFromTable() {
        String[] result = new String[10];
        for (int i = 0; i < insertPayRateTableView.getTable().getRowCount() - 5; i++) {
            result[i] = insertPayRateTableView.getTable().getValueAt(i + 1, 0).toString();

        }
        return result;
    }

    public void insertBaseMoneyInTable(String[] values) {
        baseMoneyStringValues = values;
        for (int i = 0; i < values.length; i++) {
            insertPayRateTableView.getTable().setValueAt(values[i], 0, i + 1);

        }
    }

    public void insertBonusMoneyInTable(String[] values) {
        bonusMoneyStringValues = values;
        for (int i = 0; i < values.length; i++) {
            insertPayRateTableView.getTable().setValueAt(values[i], 12, i + 1);

        }
    }


    public InsertPayRateTableView getInsertPayRateTableView() {
        return insertPayRateTableView;
    }


    private double[][] prepareTableForDatabaseInsertion() {
        double[][] result = new double[insertPayRateTableView.getTable().getRowCount()][insertPayRateTableView.getTable().getRowCount()];
        StringAndDoubleTransformationForDatabase converter = new StringAndDoubleTransformationForDatabase();
        for (int row = 0; row < insertPayRateTableView.getTable().getRowCount(); row++) {
            for (int column = 0; column < insertPayRateTableView.getTable().getColumnCount(); column++) {
                if (insertPayRateTableView.getTable().getValueAt(row, column) == null || insertPayRateTableView.getTable().getValueAt(row,column).equals("")) {
                    System.out.println(insertPayRateTableView.getTable().getValueAt(row,column) + "Nullwerte");
                    result[row][column] = 0;
                } else {
                    System.out.println(insertPayRateTableView.getTable().getValueAt(row,column) + "Tabellenwerte");
                    result[row][column] = converter.transformStringToDouble((String)insertPayRateTableView.getTable().getValueAt(row,column));
                }
            }
        }
        return result;
    }

    private void insertValuesInDatabase(String name,String paygrade,double[][] values){
        SalaryTableManager manager = new SalaryTableManager();
        for(int column = 0; column < values[0].length;column++){
            String tableName = name;
            double grundentgelt = values[0][column];
            System.out.println(grundentgelt + "grundentgelt");
            double av_ag_anteil_lfd_entgelt = values[1][column];
            System.out.println(av_ag_anteil_lfd_entgelt + "avag");
            double kv_ag_anteil_lfd_entgelt = values[2][column];
            System.out.println(kv_ag_anteil_lfd_entgelt+"kvag");
            double zusbei_af_lfd_entgelt = values[3][column];
            System.out.println(zusbei_af_lfd_entgelt+"zusb");
            double pv_ag_anteil_lfd_entgelt = values[4][column];
            System.out.println(pv_ag_anteil_lfd_entgelt+"pvag");
            double rv_ag_anteil_lfd_entgelt = values[5][column];
            System.out.println(rv_ag_anteil_lfd_entgelt+"rvag");
            double sv_umlage_u2 = values[6][column];
            double steuern_ag = values[7][column];
            double zv_Sanierungsbeitrag = values[8][column];
            double zv_umlage_allgemein = values[9][column];
            double vbl_wiss_4perc_ag_buchung = values[10][column];
            double mtl_kosten_ohne_jsz = values[11][column];
            double jsz_als_monatliche_zulage = values[12][column];
            double mtl_kosten_mit_jsz = values[13][column];
            double jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung = values[14][column];
            System.out.println(jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung+"jaerliche....");
            String grade = paygrade;

            SalaryTable salaryTable = new SalaryTable(tableName,grundentgelt,av_ag_anteil_lfd_entgelt,kv_ag_anteil_lfd_entgelt,zusbei_af_lfd_entgelt,pv_ag_anteil_lfd_entgelt,rv_ag_anteil_lfd_entgelt,sv_umlage_u2,steuern_ag,zv_Sanierungsbeitrag,zv_umlage_allgemein,vbl_wiss_4perc_ag_buchung,mtl_kosten_ohne_jsz,jsz_als_monatliche_zulage,mtl_kosten_mit_jsz,jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung,grade);
            manager.addSalaryTable(salaryTable);
        }
    }



    private String determinePayGrade(){
        String result;
        if (title.contains("E13")){
            result = "E13";
            return result;
        } else {
            result = "E14";
            return result;
        }
    }
}
