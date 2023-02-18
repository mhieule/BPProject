package excelchaos_model;

import excelchaos_model.calculations.AutomaticPayLevelIncrease;
import excelchaos_model.calculations.ContractSalary;

import java.text.ParseException;

public class BackEndUpdates {

    public void calculationsOnStartUp(){
        AutomaticPayLevelIncrease startUpPayLevelIncrease = new AutomaticPayLevelIncrease();
        ContractSalary contractSalary = new ContractSalary();

        try {
            startUpPayLevelIncrease.performPayLevelIncrease();
            contractSalary.determineCurrentSalary();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }

    public void updateAll(){

    }
}
