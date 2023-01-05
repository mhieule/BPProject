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

    public ToolbarSalaryListController(SalaryListView salaryView,SalaryListController salaryController){
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

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if(e.getStateChange() == ItemEvent.SELECTED){
            System.out.println("Here");
            salaryListView.showPayGradeIncrease();
            salaryListView.add(toolbar, BorderLayout.NORTH);
        } else {
            salaryListView.init();
            salaryListView.add(toolbar,BorderLayout.NORTH);
        }
    }
}
