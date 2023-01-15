package excelchaos_model;

import java.math.BigDecimal;

public class PayRateTableStringOperationModel {

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

    public BigDecimal[] convertStringToBigDecimal(String[] values) {
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
