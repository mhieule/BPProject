package excelchaos_controller;

import excelchaos_view.InsertManualSalaryEntryView;
import excelchaos_view.ManualSalaryEntryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertManualSalaryEntryController implements ActionListener {

    private String title = "Gehaltseintrag hinzufügen";

    private InsertManualSalaryEntryView insertManualSalaryEntryView;

    private MainFrameController frameController;

    public InsertManualSalaryEntryController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        insertManualSalaryEntryView = new InsertManualSalaryEntryView();
        insertManualSalaryEntryView.init();
        insertManualSalaryEntryView.setActionListener(this);

    }

    public void showInsertManualSalaryEntryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            //ActionLogEintrag
            mainFrameController.addTab(title, insertManualSalaryEntryView);
            //mainFrameController.setChangeListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertManualSalaryEntryView.getCancel()){
            frameController.getTabs().removeTabNewWindow(insertManualSalaryEntryView);
        }
    }
}
