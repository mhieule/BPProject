package excelchaos_controller;

import excelchaos_view.InsertPayRateTableView;
import excelchaos_view.SideMenuPanelActionLogView;

public class InsertPayRateTableController {

    private InsertPayRateTableView insertPayRateTableView;
    private MainFrameController frameController;
    private String title;

    public InsertPayRateTableController(MainFrameController mainFrameController, String name) {
        frameController = mainFrameController;
        setTitle(name);
        insertPayRateTableView = new InsertPayRateTableView();
        insertPayRateTableView.init();

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

    public void setTitle(String name) {
        if (name.equals("E13 Entgelttabellen")) {
            title = "E13 Entgelttabelle hinzufügen";
        } else if (name.equals("E14 Entgelttabellen")) {
            title = "E14 Entgelttabelle hinzufügen";
        } else if (name.equals("SHK Entgelttabellen")) {
            title = "SHK Entgelttabelle hinzufügen";
        }
    }
}
