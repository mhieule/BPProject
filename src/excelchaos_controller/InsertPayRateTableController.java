package excelchaos_controller;

import excelchaos_view.InsertPayRateTableView;
import excelchaos_view.SideMenuPanelActionLogView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertPayRateTableController implements ActionListener {

    private InsertPayRateTableView insertPayRateTableView;
    private MainFrameController frameController;
    private InsertBaseMoneyDialogController insertBaseMoneyDialogController;
    private String title;


    public InsertPayRateTableController(MainFrameController mainFrameController, String name,String[] columnNames,boolean typeOfTable) {
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
        if (e.getSource()==insertPayRateTableView.getCancelButton()){
            frameController.getTabs().removeTabNewWindow(insertPayRateTableView);
        } else if (e.getSource() == insertPayRateTableView.getCalculateCells()) {
            for (int i = 1; i < insertPayRateTableView.getTable().getColumnCount();i++){
                for (int j = 1; j < insertPayRateTableView.getTable().getRowCount();j++){
                    if (insertPayRateTableView.getTable().getValueAt(j,0) == null){
                        insertPayRateTableView.getTable().setValueAt(0,j,0);
                    } else if (insertPayRateTableView.getTable().getValueAt(0,i) == null){
                        insertPayRateTableView.getTable().setValueAt(0,0,j);
                    }
                    insertPayRateTableView.getTable().setValueAt(calculateValue(insertPayRateTableView.getTable().getValueAt(j,0).toString(),insertPayRateTableView.getTable().getValueAt(0,i).toString()),j,i);
                }
            }


        } else if (e.getSource() == insertPayRateTableView.getInsertBaseMoney()){
            insertBaseMoneyDialogController = new InsertBaseMoneyDialogController(frameController,this);
        }
    }
    public double calculateValue(String percent,String baseValue){
        double result;
        double percentValue = Double.parseDouble(percent);
        double numberValue = Double.parseDouble(baseValue);
        result = numberValue*(percentValue/100);

        return result;
    }
    public void insertValueInTable(String[] values){
        for (int i = 0; i <values.length;i++){
            insertPayRateTableView.getTable().setValueAt(values[i],0,i+1);
        }
    }


    public InsertPayRateTableView getInsertPayRateTableView() {
        return insertPayRateTableView;
    }
}
