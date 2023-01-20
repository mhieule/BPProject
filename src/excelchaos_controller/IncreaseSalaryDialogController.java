package excelchaos_controller;

import excelchaos_model.IncreaseSalaryType;
import excelchaos_view.IncreaseSalaryDialogView;
import excelchaos_view.SalaryListView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IncreaseSalaryDialogController implements ActionListener {
    private MainFrameController frameController;
    private IncreaseSalaryDialogView increaseSalaryDialogView;
    private SalaryListView salaryListView;
    private boolean isProjectedColumnOpened;


    public IncreaseSalaryDialogController(MainFrameController frameController){
        this.frameController = frameController;
        this.salaryListView = frameController.getSalaryListController().getSalaryListView();
        this.isProjectedColumnOpened=false;

        increaseSalaryDialogView = new IncreaseSalaryDialogView();
        increaseSalaryDialogView.init(salaryListView.getTable().getCurrentSelectedRowAsTable());
        increaseSalaryDialogView.setActionListener(this);
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==increaseSalaryDialogView.getCloseButton()){
            increaseSalaryDialogView.setVisible(false);
        } else if(e.getSource()==increaseSalaryDialogView.getProjectButton()){
            //instruct the view to add a new column to the current table which contains projected salary after increase
            increaseSalaryDialogView.setProjectionColumnVisible();
            if(increaseSalaryDialogView.getAbsolute().isSelected()){
                increaseSalaryDialogView.setProjectionView(IncreaseSalaryType.ABSOLUTE);
            } else if (increaseSalaryDialogView.getRelative().isSelected()){
                increaseSalaryDialogView.setProjectionView(IncreaseSalaryType.RELATIVE);
            } else if (increaseSalaryDialogView.getMixed().isSelected() && increaseSalaryDialogView.getMixedMin().isSelected()){
                increaseSalaryDialogView.setProjectionView(IncreaseSalaryType.MIXED_MIN);
            } else if (increaseSalaryDialogView.getMixed().isSelected() && increaseSalaryDialogView.getMixedMax().isSelected()){
                increaseSalaryDialogView.setProjectionView(IncreaseSalaryType.MIXED_MAX);
            }

        } else if(e.getSource()==increaseSalaryDialogView.getOkayButton()) {
            //save change to the database
        } else if(e.getSource()==increaseSalaryDialogView.getAbsolute()){
            increaseSalaryDialogView.setAbsoluteView();
            isProjectedColumnOpened=!isProjectedColumnOpened;
        } else if(e.getSource()==increaseSalaryDialogView.getRelative()){
            increaseSalaryDialogView.setRelativeView();
            isProjectedColumnOpened=!isProjectedColumnOpened;
        } else if(e.getSource()==increaseSalaryDialogView.getMixed()){
            increaseSalaryDialogView.setMixedView();
            isProjectedColumnOpened=!isProjectedColumnOpened;
        }

    }
}
