package excelchaos_model.utility;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

public class StringAndDoubleTransformationForDatabase {

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

    public String formatDoubleToString(double valueToFormat, int column) {
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

    public double formatStringToPercentageValueForScope(String valueToFormat) {
        double result;
        valueToFormat = valueToFormat.replaceAll("%", "");
        result = Double.parseDouble(valueToFormat);
        result = result / 100;
        return result;
    }

    public String formatPercentageToStringForScope(double valueToFormat) {
        String result;
        valueToFormat = valueToFormat * 100;
        Formatter formatter = new Formatter();
        formatter.format("%.0f", valueToFormat);
        result = formatter.toString();
        result = result.concat("%");

        return result;
    }

    public String formatDoubleToPersonenMonate(double valueToFormat) {
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
