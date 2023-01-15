package excelchaos_controller;

import excelchaos_view.SalaryListView;
import excelchaos_view.ToolbarSalaryListView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ToolbarSalaryListController implements ActionListener, ItemListener {
    private ToolbarSalaryListView toolbar;
    private SalaryListController salaryListController;
    private SalaryListView salaryListView;
    private MainFrameController frameController;

    private ShowSalaryStageDialogController showSalaryStageDialogController;

    public ToolbarSalaryListController(MainFrameController mainFrameController, SalaryListView salaryView, SalaryListController salaryController) {
        frameController = mainFrameController;
        salaryListController = salaryController;
        salaryListView = salaryView;
        toolbar = new ToolbarSalaryListView();
        toolbar.init();
        toolbar.setActionListener(this);
        toolbar.setItemListener(this);
    }

    public ToolbarSalaryListView getToolbar() {
        return toolbar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getSalaryStageOn()) {
            showSalaryStageDialogController = new ShowSalaryStageDialogController();
        } else if (e.getSource() == toolbar.getRemoveAdditionalSalaryStage()) {
        }
        else if (e.getSource() == toolbar.getInsertEntry()){
            frameController.getInsertSalaryController().showInsertSalaryView(frameController);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {
            salaryListView.showPayGradeIncrease();
            salaryListView.add(toolbar, BorderLayout.NORTH);
        } else {
            salaryListView.init();
            salaryListView.add(toolbar, BorderLayout.NORTH);
        }
    }
}
