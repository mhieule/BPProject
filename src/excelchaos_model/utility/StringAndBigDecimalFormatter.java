package excelchaos_model.utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

public class StringAndBigDecimalFormatter {

    public double formatStringToDouble(String tableValue) {
        String value = tableValue;
        NumberFormat euroTransformer = NumberFormat.getCurrencyInstance();
        euroTransformer.setCurrency(Currency.getInstance(Locale.GERMANY));
        double result;
        if (tableValue.contains("%")) {
            value = value.replaceAll("%", "");
        } else {
            try {
                Locale locale = Locale.GERMANY;
                tableValue = tableValue.replace("€", "");
                NumberFormat numberFormat = NumberFormat.getInstance(locale);
                result = numberFormat.parse(tableValue).doubleValue();
                return result;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        result = Double.parseDouble(value);
        return result;
    }

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

    public static BigDecimal formatStringToPercentageValueForScope(String valueToFormat) {
        BigDecimal result;

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setParseBigDecimal(true);
        Number temporary = null;
        valueToFormat = valueToFormat.replaceAll("%", "");
        try {
            temporary = decimalFormat.parse(valueToFormat);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        result = new BigDecimal(temporary.toString());
        return result;
    }

    public static String formatPercentageToStringForScope(BigDecimal valueToFormat) {
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


    public static String formatBigDecimalCurrencyToString(BigDecimal valueToFormat) {
        NumberFormat euroTransformer = NumberFormat.getCurrencyInstance();
        euroTransformer.setCurrency(Currency.getInstance(Locale.GERMANY));
        return euroTransformer.format(valueToFormat);
    }

    public static BigDecimal formatStringToBigDecimalCurrency(String tableValue) {
        Number number;
        try {
            Locale locale = Locale.GERMANY;
            tableValue = tableValue.replace("€", "");
            NumberFormat numberFormat = NumberFormat.getInstance(locale);
            number = numberFormat.parse(tableValue).doubleValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new BigDecimal(number.toString());
    }
}
