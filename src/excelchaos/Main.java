package excelchaos;


import excelchaos_controller.MainFrameController;
import excelchaos_model.BackEndUpdates;
import excelchaos_model.StartUp;


public class Main {
    public static void main(String[] args) {
        StartUp.performStartUp();
        BackEndUpdates.calculationsOnStartUp();
        new MainFrameController();

    }
}
