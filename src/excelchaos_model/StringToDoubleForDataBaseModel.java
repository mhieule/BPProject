package excelchaos_model;

public class StringToDoubleForDataBaseModel {

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
}
