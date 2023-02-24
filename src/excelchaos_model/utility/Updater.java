package excelchaos_model.utility;

import excelchaos_controller.MainFrameController;
import excelchaos_model.EmployeeDataManager;

import javax.swing.*;

public class Updater {
    public MainFrameController frameController;

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    public Updater(MainFrameController mainFrameController){
        frameController = mainFrameController;
    }

    public void update(){
        frameController.getManualSalaryEntryController().update();

    }
}
