package excelchaos_model;

import excelchaos_model.calculations.AutomaticPayLevelIncrease;
import excelchaos_model.calculations.NewAndImprovedSalaryCalculation;
import excelchaos_model.database.ContractDataManager;

import java.text.ParseException;

public class BackEndUpdates {

    public static void calculationsOnStartUp() {
        AutomaticPayLevelIncrease startUpPayLevelIncrease = new AutomaticPayLevelIncrease();
        NewAndImprovedSalaryCalculation newAndImprovedSalaryCalculation = new NewAndImprovedSalaryCalculation();

        startUpPayLevelIncrease.performPayLevelIncrease();
        newAndImprovedSalaryCalculation.updateCurrentSalaries();
    }

    public void updateAll() {

    }
}
