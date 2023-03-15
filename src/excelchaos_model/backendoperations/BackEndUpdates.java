package excelchaos_model.backendoperations;

import excelchaos_model.calculations.AutomaticPayLevelIncrease;
import excelchaos_model.calculations.SalaryCalculation;

public class BackEndUpdates {

    public static void calculationsOnStartUp() {
        AutomaticPayLevelIncrease startUpPayLevelIncrease = new AutomaticPayLevelIncrease();
        SalaryCalculation newAndImprovedSalaryCalculation = new SalaryCalculation();

        startUpPayLevelIncrease.performPayLevelIncrease();
        newAndImprovedSalaryCalculation.updateCurrentSalaries();
    }

}
