package excelchaos_model.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

public class StringAndBigDecimalFormatter {


    public static String formatBigDecimalToStringPayRateTable(BigDecimal valueToFormat, int column) {
        String result;
        Formatter formatter = new Formatter();
        NumberFormat euroTransformer = NumberFormat.getCurrencyInstance();
        euroTransformer.setCurrency(Currency.getInstance(Locale.GERMANY));
        if (column == 0) {
            formatter.format("%.3f", valueToFormat);
            result = formatter.toString();
            result = result.replaceAll("\\.", ",");
            result = result.concat("%");
        } else {
            result = euroTransformer.format(valueToFormat);

        }
        return result;
    }

    public static String formatPercentageToStringForScope(BigDecimal valueToFormat) {
        valueToFormat = valueToFormat.multiply(new BigDecimal(100));
        String result;
        Formatter formatter = new Formatter();
        formatter.format("%.0f", valueToFormat);
        result = formatter.toString();
        result = result.concat("%");

        return result;
    }


    public static String formatBigDecimalToPersonenMonate(BigDecimal valueToFormat){
        String result;
        result = String.valueOf(valueToFormat);
        result = result.replaceAll("\\.", ",");
        return result;
    }

    public static String formatBigDecimalToHours(BigDecimal valueToFormat){
        String result;
        Formatter formatter = new Formatter();
        formatter.format("%.0f", valueToFormat);
        result = formatter.toString();
        result = result.concat(" Stunden");
        return result;
    }


    public static String formatBigDecimalCurrencyToString(BigDecimal valueToFormat) {
        NumberFormat euroTransformer = NumberFormat.getCurrencyInstance();
        euroTransformer.setCurrency(Currency.getInstance(Locale.GERMANY));
        return euroTransformer.format(valueToFormat);
    }

    public static BigDecimal formatStringToPercentageValueForScope(String valueToFormat) {
        BigDecimal result;
        valueToFormat = valueToFormat.replaceAll("%", "");
        valueToFormat = valueToFormat.replaceAll(",",".");
        result = new BigDecimal(valueToFormat);
        result = result.divide(new BigDecimal(100),2,RoundingMode.HALF_EVEN);
        return result;
    }

    public static BigDecimal formatHoursStringToBigDecimal(String valueToFormat){
        BigDecimal result;
        valueToFormat = valueToFormat.split(" ")[0];
        result = new BigDecimal(valueToFormat);
        return result;
    }



    public static BigDecimal formatStringToBigDecimalCurrency(String valueToFormat) {
        if(valueToFormat.contains("%")){
            valueToFormat = valueToFormat.replaceAll("%","");
            valueToFormat = valueToFormat.replaceAll(",",".");
            return new BigDecimal(valueToFormat);
        }
        Number number;
        try {
            Locale locale = Locale.GERMANY;
            valueToFormat = valueToFormat.replace("€", "");
            NumberFormat numberFormat = NumberFormat.getInstance(locale);
            number = numberFormat.parse(valueToFormat).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new BigDecimal(number.toString());
    }
}
