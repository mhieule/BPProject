package excelchaos_model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PayRateTableCalculationModel {

    private String[] stringMoneyValues;
    private String[] stringPercentageValues;

    private String[] stringBonusMoneyValues;
    private BigDecimal[] moneyValues;

    private BigDecimal[] bonusMoneyValues;
    private BigDecimal[] percentageValues;

    private BigDecimal[] monthlyCostWithYearBonus;
    private BigDecimal[] monthlyCostWithYearBonusRounded;

    private BigDecimal[] monthlyCostWithoutYearBonus;
    private BigDecimal[] monthlyCostWithoutYearBonusRounded;

    private BigDecimal[] lastRow;
    private BigDecimal[][] numberResults;

    private BigDecimal[][] roundedResults;



    public PayRateTableCalculationModel() {

    }

    public PayRateTableCalculationModel(String[] baseMoney, String[] percentages,String[] bonusMoney) {
        stringMoneyValues = baseMoney;
        stringBonusMoneyValues = bonusMoney;
        stringPercentageValues = percentages;
    }

    public void doStringOperations() {
        stringPercentageValues = modifyPercentageValuesForCalculation(stringPercentageValues);
        stringMoneyValues = modifyMoneyValuesForCalculation(stringMoneyValues);
        stringBonusMoneyValues = modifyMoneyValuesForCalculation(stringBonusMoneyValues);
        percentageValues = convertPercentageStringsToBigDecimal(stringPercentageValues);
        moneyValues = convertMoneyStringToBigDecimal(stringMoneyValues);
        bonusMoneyValues = convertMoneyStringToBigDecimal(stringBonusMoneyValues);

    }

    public String[][] calculateResults() {
        numberResults = new BigDecimal[percentageValues.length][moneyValues.length];
        roundedResults = new BigDecimal[percentageValues.length][moneyValues.length];
        monthlyCostWithoutYearBonus = new BigDecimal[moneyValues.length];
        monthlyCostWithYearBonus = new BigDecimal[moneyValues.length];
        String[][] stringResults = new String[percentageValues.length][moneyValues.length];
        for (int i = 0; i < percentageValues.length; i++) {
            for (int j = 0; j < moneyValues.length; j++) {
                numberResults[i][j] = moneyValues[j].multiply(percentageValues[i]);
                roundedResults[i][j] = numberResults[i][j].setScale(2, RoundingMode.HALF_EVEN);
                stringResults[i][j] = roundedResults[i][j].toString();

            }
        }
        return stringResults;
    }

    private void initMonthlyCost() {
        for (int i = 0; i < monthlyCostWithoutYearBonus.length; i++) {
            monthlyCostWithoutYearBonus[i] = new BigDecimal(0);

        }
    }

    private void removeMissingValuesFromArray() {
        BigDecimal zero = new BigDecimal(0);
        System.out.println(percentageValues.length);
        System.out.println(moneyValues.length);
        for (int rows = 0; rows < percentageValues.length; rows++) {
            for (int columns = 0; columns < moneyValues.length; columns++) {
                if ((columns % 2 == 0) && ((rows == 7) || (rows == 8))) {
                    numberResults[rows][columns] = zero;

                } else if ((columns % 2 == 1) && (rows == 9)) {
                    numberResults[rows][columns] = zero;
                } else {

                }

            }


        }
    }

    public void calculateMonthlyCostWithoutYearBonus() {
        initMonthlyCost();
        removeMissingValuesFromArray();
        for (int columns = 0; columns < roundedResults[0].length; columns++) {
            for (int rows = 0; rows < roundedResults.length; rows++) {
                monthlyCostWithoutYearBonus[columns] = monthlyCostWithoutYearBonus[columns].add(numberResults[rows][columns]);
            }
            monthlyCostWithoutYearBonus[columns] = monthlyCostWithoutYearBonus[columns].add(moneyValues[columns]);

        }
    }

    public void calculateMonthlyCostWithYearBonus(){
        for(int i = 0; i< monthlyCostWithYearBonus.length;i++){
            monthlyCostWithYearBonus[i] = monthlyCostWithoutYearBonus[i].add(bonusMoneyValues[i]);
        }
    }

    public void calculateLastRow(){
        lastRow = new BigDecimal[monthlyCostWithYearBonus.length];
        BigDecimal multiplicand = new BigDecimal(12);
        for (int i = 0; i < lastRow.length; i++){
            lastRow[i] = monthlyCostWithYearBonus[i].multiply(multiplicand);
            lastRow[i] = lastRow[i].setScale(2, RoundingMode.HALF_EVEN);
        }
    }

    public String[] convertLastRowResultsToString(){
        String[] stringResult = new String[lastRow.length];
        for (int i = 0; i < monthlyCostWithoutYearBonus.length; i++) {
            stringResult[i] = lastRow[i].toString();
            stringResult[i] = stringResult[i].replaceAll("\\.", ",");
            stringResult[i] = stringResult[i].concat(" €");
        }
        return stringResult;
    }

    public String[] convertMonthlyCostWithoutYearBonusToString() {
        monthlyCostWithoutYearBonusRounded = new BigDecimal[monthlyCostWithoutYearBonus.length];
        String[] stringResult = new String[monthlyCostWithoutYearBonus.length];
        for (int i = 0; i < monthlyCostWithoutYearBonus.length; i++) {
            monthlyCostWithoutYearBonusRounded[i] = monthlyCostWithoutYearBonus[i].setScale(2, RoundingMode.HALF_EVEN);
            stringResult[i] = monthlyCostWithoutYearBonusRounded[i].toString();
            stringResult[i] = stringResult[i].replaceAll("\\.", ",");
            stringResult[i] = stringResult[i].concat(" €");
        }
        return stringResult;
    }

    public String[] convertMonthlyCostWithYearBonusToString(){
        monthlyCostWithYearBonusRounded = new BigDecimal[monthlyCostWithYearBonus.length];
        String[] stringResult = new String[monthlyCostWithYearBonus.length];
        for (int i = 0; i < monthlyCostWithYearBonus.length; i++){
            monthlyCostWithYearBonusRounded[i] = monthlyCostWithYearBonus[i].setScale(2, RoundingMode.HALF_EVEN);
            stringResult[i] = monthlyCostWithYearBonusRounded[i].toString();
            stringResult[i] = stringResult[i].replaceAll("\\.", ",");
            stringResult[i] = stringResult[i].concat(" €");
        }
        return stringResult;
    }

    public String[][] rearrangeStringFormat(String[][] stringResults) {
        for (int i = 0; i < stringResults.length; i++) {
            for (int j = 0; j < stringResults[i].length; j++) {
                stringResults[i][j] = stringResults[i][j].replaceAll("\\.", ",");
                stringResults[i][j] = stringResults[i][j].concat(" €");
            }
        }

        return stringResults;
    }

    public String[] prepareInsertionString(String text) {
        String[] resultString;
        text.replaceAll("\\s+", "");
        resultString = text.split("(?<=€)");
        return resultString;
    }

    public String[] modifyMoneyValuesForCalculation(String[] values) {
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].replaceAll("€", "");
            values[i] = values[i].replaceAll("\\.", "");
            values[i] = values[i].replaceAll(",", ".");
            values[i] = values[i].replaceAll(" ", "");
            result[i] = values[i];
        }
        return result;
    }

    public BigDecimal[] convertMoneyStringToBigDecimal(String[] values) {
        BigDecimal[] result = new BigDecimal[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = new BigDecimal(values[i]);
        }
        return result;
    }

    public String[] modifyPercentageValuesForCalculation(String[] values) {
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].replaceAll("%", "");
            values[i] = values[i].replaceAll(",", ".");
            result[i] = values[i];
        }
        return result;
    }

    public BigDecimal[] convertPercentageStringsToBigDecimal(String[] values) {
        BigDecimal[] result = new BigDecimal[values.length];
        BigDecimal divisor = new BigDecimal(100);
        for (int i = 0; i < values.length; i++) {
            result[i] = new BigDecimal(values[i]);
            result[i] = result[i].divide(divisor);
        }
        return result;
    }


}
