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


    /**
     * This method formats a BigDecimal value to a string based on the column parameter passed in.
     * If column is equal to 0, it formats the value as a percentage string with 3 decimal places and
     * the percentage symbol at the end. If column is not equal to 0, it formats the value as a currency
     * string in euro format.
     *
     * @param valueToFormat the BigDecimal value to be formatted
     * @param column        the column number to determine the formatting type
     * @return a formatted string of the BigDecimal value
     */
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

    /**
     * Formats a BigDecimal value representing a percentage into a String formatted as a percentage string.
     *
     * @param valueToFormat the BigDecimal value representing a percentage to be formatted
     * @return the String formatted as a percentage string, for example, "75%"
     */
    public static String formatPercentageToStringForScope(BigDecimal valueToFormat) {
        valueToFormat = valueToFormat.multiply(new BigDecimal(100));
        String result;
        Formatter formatter = new Formatter();
        formatter.format("%.0f", valueToFormat);
        result = formatter.toString();
        result = result.concat("%");

        return result;
    }


    /**
     * Formats a BigDecimal value representing a duration in months to a String with comma as decimal separator.
     *
     * @param valueToFormat the BigDecimal value to format
     * @return a String representing the duration in months with comma as decimal separator
     */
    public static String formatBigDecimalToPersonenMonate(BigDecimal valueToFormat) {
        String result;
        result = String.valueOf(valueToFormat);
        result = result.replaceAll("\\.", ",");
        return result;
    }

    /**
     * Formats a BigDecimal value to a string representation in hours.
     *
     * @param valueToFormat the {@code BigDecimal} value to format
     * @return a string representation of the {@code valueToFormat} in hours, appended with the unit "Stunden"
     */
    public static String formatBigDecimalToHours(BigDecimal valueToFormat) {
        String result;
        Formatter formatter = new Formatter();
        formatter.format("%.0f", valueToFormat);
        result = formatter.toString();
        result = result.concat(" Stunden");
        return result;
    }


    /**
     * Formats a BigDecimal value as a currency string in the German format.
     *
     * @param valueToFormat the BigDecimal value to format
     * @return the formatted currency string
     */
    public static String formatBigDecimalCurrencyToString(BigDecimal valueToFormat) {
        NumberFormat euroTransformer = NumberFormat.getCurrencyInstance();
        euroTransformer.setCurrency(Currency.getInstance(Locale.GERMANY));
        return euroTransformer.format(valueToFormat);
    }

    /**
     * Parses a percentage string value (e.g. "45%") to a BigDecimal value representing a decimal percentage
     * (e.g. 0.45). The string is first cleaned by removing any percentage signs and replacing commas with periods.
     * The resulting value is then divided by 100 to convert it to the decimal form of the percentage.
     *
     * @param valueToFormat the string value to parse
     * @return a BigDecimal value representing the decimal percentage
     */
    public static BigDecimal formatStringToPercentageValueForScope(String valueToFormat) {
        BigDecimal result;
        valueToFormat = valueToFormat.replaceAll("%", "");
        valueToFormat = valueToFormat.replaceAll(",", ".");
        result = new BigDecimal(valueToFormat);
        result = result.divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN);
        return result;
    }

    /**
     * This method takes a string value containing a percentage and formats it to a BigDecimal value
     * for use in the pay rate calculation. The string is first stripped of any percentage symbols and any comma
     * separators are replaced with a decimal point. The resulting string is then parsed into a BigDecimal value.
     * The BigDecimal value is then divided by 100 with a scale of 3 and a rounding mode of HALF_EVEN applied.
     * The resulting BigDecimal value is returned.
     *
     * @param valueToFormat a string value containing a percentage to format to a BigDecimal value
     * @return the BigDecimal value of the formatted percentage
     */
    public static BigDecimal formatStringToPercentageValueForPayRate(String valueToFormat) {
        BigDecimal result;
        valueToFormat = valueToFormat.replaceAll("%", "");
        valueToFormat = valueToFormat.replaceAll(",", ".");
        result = new BigDecimal(valueToFormat);
        result = result.divide(new BigDecimal(100), 3, RoundingMode.HALF_EVEN);
        return result;
    }

    /**
     * Converts a string value in the format "X Stunden" to a BigDecimal value of X.
     *
     * @param valueToFormat the string value to be formatted
     * @return the BigDecimal value obtained from the string
     */
    public static BigDecimal formatHoursStringToBigDecimal(String valueToFormat) {
        BigDecimal result;
        valueToFormat = valueToFormat.split(" ")[0];
        result = new BigDecimal(valueToFormat);
        return result;
    }


    /**
     * Converts a string representing a monetary value into a {@code BigDecimal}.
     * If the string contains a percentage sign, it is first converted to a decimal.
     * The method uses the German locale and can handle currency symbols and thousands separators.
     *
     * @param valueToFormat the string representing the monetary value to be converted
     * @return a {@code BigDecimal} representation of the monetary value
     * @throws RuntimeException if the string cannot be parsed
     */
    public static BigDecimal formatStringToBigDecimalCurrency(String valueToFormat) {
        if (valueToFormat.contains("%")) {
            valueToFormat = valueToFormat.replaceAll("%", "");
            valueToFormat = valueToFormat.replaceAll(",", ".");
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
