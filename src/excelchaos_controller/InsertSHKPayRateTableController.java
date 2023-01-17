package excelchaos_controller;

import excelchaos_view.InsertSHKPayRateTableView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertSHKPayRateTableController implements ActionListener {
    private InsertSHKPayRateTableView insertSHKPayRateTableView;
    private MainFrameController frameController;

    private String title = "SHK Entgelttabelle hinzufügen";

    public InsertSHKPayRateTableController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        insertSHKPayRateTableView = new InsertSHKPayRateTableView();
        insertSHKPayRateTableView.init();
        insertSHKPayRateTableView.setActionListener(this);
    }

    public void showInsertSHKPayRateTableView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title,insertSHKPayRateTableView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==insertSHKPayRateTableView.getCancelButton()) {
            frameController.getTabs().removeTabNewWindow(insertSHKPayRateTableView);
        }
    }
}
