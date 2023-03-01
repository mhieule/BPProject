package excelchaos_model.utility;

import excelchaos_controller.MainFrameController;
import excelchaos_model.BackEndUpdates;
import excelchaos_model.database.EmployeeDataManager;

public class Updater {
    public MainFrameController frameController;

    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();

    public Updater(MainFrameController mainFrameController) {
        frameController = mainFrameController;
    }

    public void nameListUpdate() {
        frameController.getManualSalaryEntryController().update();
        frameController.getSalaryIncreaseController().update();

    }

    //TODO Add SalaryHistorie Update wenn vorhanden
    public void salaryUpDate() {
        BackEndUpdates.calculationsOnStartUp();
        frameController.getSalaryListController().updateData(frameController.getSalaryListController().getSalaryDataFromDataBase());
    }
}
