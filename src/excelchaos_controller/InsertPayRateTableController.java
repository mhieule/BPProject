package excelchaos_controller;

import excelchaos_model.PayRateTableCalculationModel;
import excelchaos_view.InsertPayRateTableView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
            insertResultsInTable();
        } else if (e.getSource() == insertPayRateTableView.getInsertBaseMoney()) {
            insertBaseMoneyDialogController = new InsertBaseMoneyDialogController(frameController, this);
        }
    }

    private void insertResultsInTable(){
        String[] percentageStringValues = getPercentageStringFromTable();
        PayRateTableCalculationModel model = new PayRateTableCalculationModel(moneyStringValues, percentageStringValues);
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
