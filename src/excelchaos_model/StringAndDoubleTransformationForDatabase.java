package excelchaos_model;

import java.util.Formatter;

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
        if (column == 0) {
            formatter.format("%.3f", valueToFormat);
            result = formatter.toString();
            result = result.replaceAll("\\.", ",");
            result = result.concat("%");
        } else {
            formatter.format("%.2f", valueToFormat);
            result = formatter.toString();
            result = result.replaceAll("\\.", ",");
            result = result.concat(" €");

        }
        return result;
    }
}
