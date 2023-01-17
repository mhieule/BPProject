package excelchaos_controller;

import excelchaos_model.PayRateTableCalculationModel;
import excelchaos_view.InsertPayRateTableView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertPayRateTableController implements ActionListener {

    private InsertPayRateTableView insertPayRateTableView;
    private MainFrameController frameController;
    private InsertBaseMoneyDialogController insertBaseMoneyDialogController;
    private String title;

    private PayRateTableCalculationModel model;
    private String[] baseMoneyStringValues;

    private String[] bonusMoneyStringValues;


    public InsertPayRateTableController(MainFrameController mainFrameController, String name, String[] columnNames, boolean typeOfTable) {
        frameController = mainFrameController;
        insertPayRateTableView = new InsertPayRateTableView();
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
        } else if (e.getSource() == insertPayRateTableView.getInsertMonthlyBonusMoney()){
            insertBaseMoneyDialogController = new InsertBaseMoneyDialogController(frameController,this,insertPayRateTableView.getInsertMonthlyBonusMoney());
        }
    }

    private void insertMonthlyCostWithoutYearBonus(){
        model.calculateMonthlyCostWithoutYearBonus();
        String[] monthlyCostWithoutBonusString = model.convertMonthlyCostWithoutYearBonusToString();
        for (int columns = 0; columns<insertPayRateTableView.getTable().getColumnCount()-1;columns++){
            insertPayRateTableView.getTable().setValueAt(monthlyCostWithoutBonusString[columns],11,columns+1);
        }
    }


    private void insertMonthlyCostWithYearBonus(){
        model.calculateMonthlyCostWithYearBonus();
        String[] monthlyCostWithBonusString = model.convertMonthlyCostWithYearBonusToString();
        for (int columns = 0; columns<insertPayRateTableView.getTable().getColumnCount()-1;columns++){
            insertPayRateTableView.getTable().setValueAt(monthlyCostWithBonusString[columns],13,columns+1);
        }
    }
    private void insertLastRow(){
        model.calculateLastRow();
        String[] lastRow = model.convertLastRowResultsToString();
        for (int columns = 0; columns<insertPayRateTableView.getTable().getColumnCount()-1;columns++){
            insertPayRateTableView.getTable().setValueAt(lastRow[columns],14,columns+1);
        }
    }

    private void insertResultsInTable(){
        String[] percentageStringValues = getPercentageStringFromTable();
        model = new PayRateTableCalculationModel(baseMoneyStringValues, percentageStringValues,bonusMoneyStringValues);
        model.doStringOperations();
        String[][] stringResults = model.calculateResults();
        stringResults = model.rearrangeStringFormat(stringResults);
        for (int k = 0; k < insertPayRateTableView.getTable().getRowCount() - 5; k++) {
            for (int l = 0; l < insertPayRateTableView.getTable().getColumnCount() - 1; l++) {
                insertPayRateTableView.getTable().setValueAt(stringResults[k][l], k + 1, l + 1);
                if ((((l + 1) % 2) == 0) && (k + 1 == 10) ){
                    insertPayRateTableView.getTable().setValueAt("",k+1,l+1);
                } else if ((((l+1) % 2) == 1) && ((k+1 == 9) || (k+1 == 8) )){
                    insertPayRateTableView.getTable().setValueAt("",k+1,l+1);
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
    public void insertBonusMoneyInTable(String[] values){
        bonusMoneyStringValues = values;
        for (int i = 0; i < values.length; i++) {
            insertPayRateTableView.getTable().setValueAt(values[i], 12, i + 1);

        }
    }


    public InsertPayRateTableView getInsertPayRateTableView() {
        return insertPayRateTableView;
    }
}
