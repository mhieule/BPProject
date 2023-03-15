package excelchaos_model.backendoperations;

import excelchaos_model.calculations.AutomaticPayLevelIncrease;
import excelchaos_model.calculations.SalaryCalculation;

public class BackEndUpdates {

    /**
     * This method performs necessary calculations on start up. It creates an instance of AutomaticPayLevelIncrease
     * and calls its method performPayLevelIncrease to perform any automatic pay level increases. Then it creates an
     * instance of SalaryCalculation and calls its method updateCurrentSalaries to update the current salaries of all
     * employees. This method should be called at the beginning of the program to ensure that pay level increases and
     * salary calculations are up to date.
     */
    public static void calculationsOnStartUp() {
        AutomaticPayLevelIncrease startUpPayLevelIncrease = new AutomaticPayLevelIncrease();
        SalaryCalculation salaryCalculation = new SalaryCalculation();

        startUpPayLevelIncrease.performPayLevelIncrease();
        salaryCalculation.updateCurrentSalaries();
    }

}
