package excelchaos_controller;

import excelchaos_model.PayRateTableStringOperationModel;
import excelchaos_view.InsertPayRateTableView;
import excelchaos_view.SideMenuPanelActionLogView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class InsertPayRateTableController implements ActionListener {

    private InsertPayRateTableView insertPayRateTableView;
    private MainFrameController frameController;
    private InsertBaseMoneyDialogController insertBaseMoneyDialogController;
    private String title;

    private String[] moneyStringValues;


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
            String[] temporaryPercentages = getPercentageStringFromTable();
            PayRateTableStringOperationModel model = new PayRateTableStringOperationModel();
            temporaryPercentages =  model.modifyPercentageValuesForCalculation(temporaryPercentages);
            BigDecimal[] percentages = model.convertPercentageStringsToBigDecimal(temporaryPercentages);
            moneyStringValues = model.modifyMoneyValuesForCalculation(moneyStringValues);
            BigDecimal[] moneyValues = model.convertStringToBigDecimal(moneyStringValues);
            BigDecimal[][] results = new BigDecimal[percentages.length][moneyValues.length];
            String[][] stringResults = new String[percentages.length][moneyValues.length];
            for (int i = 0; i < percentages.length;i++){
                for (int j = 0; j < moneyValues.length;j++){
                    results[i][j] = moneyValues[j].multiply(percentages[i]);
                    results[i][j] = results[i][j].setScale(2,RoundingMode.HALF_EVEN);
                    stringResults[i][j]=results[i][j].toString();

                }
            }
            for (int k = 0; k < insertPayRateTableView.getTable().getRowCount()-5;k++){
                System.out.println(k);
                for (int l = 0; l< insertPayRateTableView.getTable().getColumnCount()-1;l++){
                    System.out.println(l);
                    insertPayRateTableView.getTable().setValueAt(stringResults[k][l],k+1,l+1);

                }
            }


        } else if (e.getSource() == insertPayRateTableView.getInsertBaseMoney()) {
            insertBaseMoneyDialogController = new InsertBaseMoneyDialogController(frameController, this);
        }
    }

    public double calculateValue(String percent, String baseValue) {
        double result;
        double percentValue = Double.parseDouble(percent);
        double numberValue = Double.parseDouble(baseValue);
        result = numberValue * (percentValue / 100);

        return result;
    }

    public String[] getPercentageStringFromTable() {
        String[] result = new String[10];
        for (int i = 0; i < insertPayRateTableView.getTable().getRowCount() - 5; i++) {
            result[i] = insertPayRateTableView.getTable().getValueAt(i+1,0).toString();

        }
        return result;
    }

    public void insertValueInTable(String[] values) {
        moneyStringValues = values;
        for (int i = 0; i < values.length; i++) {
            insertPayRateTableView.getTable().setValueAt(values[i], 0, i + 1);

        }
    }


    public InsertPayRateTableView getInsertPayRateTableView() {
        return insertPayRateTableView;
    }
}
