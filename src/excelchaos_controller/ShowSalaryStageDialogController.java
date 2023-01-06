package excelchaos_controller;

import excelchaos_view.ShowSalaryStageDialogView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowSalaryStageDialogController implements ActionListener {
    private ShowSalaryStageDialogView showSalaryStageDialogView;

    public ShowSalaryStageDialogController(){
        showSalaryStageDialogView = new ShowSalaryStageDialogView();
        showSalaryStageDialogView.init();
        showSalaryStageDialogView.setActionListener(this);

    }

    public ShowSalaryStageDialogView getShowSalaryStageDialogView() {
        return showSalaryStageDialogView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == showSalaryStageDialogView.getCloseButton()){
            showSalaryStageDialogView.dispose();
        }
    }
}
