package excelchaos;


import excelchaos_controller.MainFrameController;
import excelchaos_model.backendoperations.BackEndUpdates;
import excelchaos_model.backendoperations.StartUp;


public class Main {
    public static void main(String[] args) {

        StartUp.showStartActionsDialog();
        BackEndUpdates.calculationsOnStartUp();
        new MainFrameController();

    }
}
