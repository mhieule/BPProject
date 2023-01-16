package excelchaos_model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PayRateTableCalculationModel {

    private String[] stringMoneyValues;
    private String[] stringPercentageValues;
    private BigDecimal[] moneyValues;
    private BigDecimal[] percentageValues;

    public PayRateTableCalculationModel(){

    }

    public PayRateTableCalculationModel(String[] money, String[] percentages){
        stringMoneyValues = money;
        stringPercentageValues = percentages;
    }
    public void doStringOperations(){
        stringPercentageValues = modifyPercentageValuesForCalculation(stringPercentageValues);
        stringMoneyValues = modifyMoneyValuesForCalculation(stringMoneyValues);
        percentageValues = convertPercentageStringsToBigDecimal(stringPercentageValues);
        moneyValues = convertMoneyStringToBigDecimal(stringMoneyValues);

    }

    public String[][] calculateResults(){
        BigDecimal[][] numberResults = new BigDecimal[percentageValues.length][moneyValues.length];
        String[][] stringResults = new String[percentageValues.length][moneyValues.length];
        for (int i = 0; i < percentageValues.length;i++){
            for (int j = 0; j < moneyValues.length;j++){
                numberResults[i][j] = moneyValues[j].multiply(percentageValues[i]);
                numberResults[i][j] = numberResults[i][j].setScale(2, RoundingMode.HALF_EVEN);
                stringResults[i][j]=numberResults[i][j].toString();

            }
        }
        return stringResults;
    }

    public String[] prepareString(String text) {
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
            System.out.println(values[i]);
            System.out.println("Here");
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
