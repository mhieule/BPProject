package excelchaos_model.utility;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

public class StringAndDoubleTransformationForDatabase {

    public double transformStringToDouble(String tableValue) {
        String value = tableValue;
        double result;
        if (tableValue.contains("%")) {
            value = value.replaceAll("%", "");
        } else {
            value = value.replaceAll("€","");
            value = value.replaceAll(" ","");
            value = value.replaceAll("\\.","");
        }
        value = value.replaceAll(",",".");
        result = Double.parseDouble(value);
        return result;
    }

    public String formatDoubleToString(double valueToFormat,int column) {
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

    public double formatStringToPercentageValueForScope(String valueToFormat){
        double result;
        valueToFormat = valueToFormat.replaceAll("%","");
        result = Double.parseDouble(valueToFormat);
        result = result/100;
        return result;
    }

    public String formatPercentageToStringForScope(double valueToFormat){
        String result;
        valueToFormat = valueToFormat*100;
        Formatter formatter = new Formatter();
        formatter.format("%.0f",valueToFormat);
        result = formatter.toString();
        result = result.concat("%");

        return result;
    }
}
