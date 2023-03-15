package excelchaos_model.backendoperations;

import excelchaos_controller.MainFrameController;
import excelchaos_model.database.EmployeeDataManager;

public class Updater {
    public MainFrameController frameController;

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    public Updater(MainFrameController mainFrameController) {
        frameController = mainFrameController;
    }

    /**
     * Updates the name lists in the GUI components for manual salary entry and salary increase.
     */
    public void nameListUpdate() {
        frameController.getManualSalaryEntryController().update();
        frameController.getSalaryIncreaseController().update();

    }

    /**
     * Updates the salary data in the application.
     * This method calls the calculationsOnStartUp() method from the BackEndUpdates class to update the salary calculations
     * and then calls the updateData() method from the SalaryListController class to update the displayed salary list.
     */
    public void salaryUpDate() {
        BackEndUpdates.calculationsOnStartUp();
        frameController.getSalaryListController().updateData();
    }
}
