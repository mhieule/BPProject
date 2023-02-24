package excelchaos;


import excelchaos_controller.MainFrameController;
import excelchaos_model.BackEndUpdates;


public class Main {
    public static void main(String[] args) {
        BackEndUpdates backEndUpdates = new BackEndUpdates();
        backEndUpdates.calculationsOnStartUp();
        new MainFrameController();

    }
}
