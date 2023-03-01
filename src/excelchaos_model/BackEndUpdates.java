package excelchaos_model;

import excelchaos_model.calculations.AutomaticPayLevelIncrease;
import excelchaos_model.calculations.SalaryCalculation;
import excelchaos_model.database.ContractDataManager;

import java.text.ParseException;

public class BackEndUpdates {

    public static void calculationsOnStartUp() {
        AutomaticPayLevelIncrease startUpPayLevelIncrease = new AutomaticPayLevelIncrease();
        SalaryCalculation contractSalary = new SalaryCalculation();

        startUpPayLevelIncrease.performPayLevelIncrease();
        contractSalary.determineCurrentSalary();
    }

    public void updateAll() {

    }
}
