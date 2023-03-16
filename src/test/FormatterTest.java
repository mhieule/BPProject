package test;

import excelchaos_model.utility.StringAndBigDecimalFormatter;
import org.junit.Test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FormatterTest {

    @Test
    public void testFormatBigDecimalToStringPayRateTable_column0() {
        BigDecimal valueToFormat = new BigDecimal("0.345");
        String formattedValue = StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(valueToFormat, 0);
        assertEquals("0,345%", formattedValue);
    }

    @Test
    public void testFormatBigDecimalToStringPayRateTable_column1() {
        BigDecimal valueToFormat = new BigDecimal("1234.56");
        String formattedValue = StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(valueToFormat, 1);
        formattedValue = formattedValue.replaceAll("\\h", " ");
        assertEquals("1.234,56 €", formattedValue);
    }

    @Test
    public void testFormatBigDecimalToStringPayRateTable_negativeValue() {
        BigDecimal valueToFormat = new BigDecimal("-987.65");
        String formattedValue = StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(valueToFormat, 1);
        formattedValue = formattedValue.replaceAll("\\h", " ");
        assertEquals("-987,65 €", formattedValue);
    }


    @Test
    public void testFormatPercentageToStringForScopeWithZero() {
        BigDecimal valueToFormat = new BigDecimal("0");
        String expectedResult = "0%";
        String actualResult = StringAndBigDecimalFormatter.formatPercentageToStringForScope(valueToFormat);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFormatPercentageToStringForScopeWithPositiveNumber() {
        BigDecimal valueToFormat = new BigDecimal("0.75");
        String expectedResult = "75%";
        String actualResult = StringAndBigDecimalFormatter.formatPercentageToStringForScope(valueToFormat);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFormatPercentageToStringForScopeWithNegativeNumber() {
        BigDecimal valueToFormat = new BigDecimal("-0.75");
        String expectedResult = "-75%";
        String actualResult = StringAndBigDecimalFormatter.formatPercentageToStringForScope(valueToFormat);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFormatPercentageToStringForScopeWithBigNumber() {
        BigDecimal valueToFormat = new BigDecimal("123456789.123456789");
        String expectedResult = "12345678912%";
        String actualResult = StringAndBigDecimalFormatter.formatPercentageToStringForScope(valueToFormat);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFormatPercentageToStringSalaryIncreaseInsertion() {
        BigDecimal value1 = new BigDecimal("12.34");
        assertEquals("12,34%", StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseInsertion(value1));

        BigDecimal value2 = new BigDecimal("-12.34");
        assertEquals("-12,34%", StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseInsertion(value2));

        BigDecimal value3 = new BigDecimal("123.45");
        assertEquals("123,45%", StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseInsertion(value3));

        BigDecimal value4 = new BigDecimal("-123.45");
        assertEquals("-123,45%", StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseInsertion(value4));
    }

    @Test
    public void testFormatPercentageToStringSalaryIncreaseReading() {
        BigDecimal value1 = new BigDecimal("0.05");
        String expected1 = "5,00%";
        String result1 = StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseReading(value1);
        assertEquals(expected1, result1);

        BigDecimal value2 = new BigDecimal("0.123456789");
        String expected2 = "12,35%";
        String result2 = StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseReading(value2);
        assertEquals(expected2, result2);

        BigDecimal value3 = new BigDecimal("0");
        String expected3 = "0,00%";
        String result3 = StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseReading(value3);
        assertEquals(expected3, result3);

        BigDecimal value4 = new BigDecimal("-0.05");
        String expected4 = "-5,00%";
        String result4 = StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseReading(value4);
        assertEquals(expected4, result4);

        BigDecimal value5 = new BigDecimal("0.005");
        String expected5 = "0,50%";
        String result5 = StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseReading(value5);
        assertEquals(expected5, result5);
    }

    @Test
    public void testFormatBigDecimalToHoursWithZero() {
        BigDecimal zero = new BigDecimal(0);
        String formattedZero = StringAndBigDecimalFormatter.formatBigDecimalToHours(zero);
        assertEquals("0 Stunden", formattedZero);
    }

    @Test
    public void testFormatBigDecimalToHoursWithWholeNumber() {
        BigDecimal wholeNumber = new BigDecimal(5);
        String formattedWholeNumber = StringAndBigDecimalFormatter.formatBigDecimalToHours(wholeNumber);
        assertEquals("5 Stunden", formattedWholeNumber);
    }

    @Test
    public void testFormatBigDecimalToHoursWithDecimal() {
        BigDecimal decimal = new BigDecimal("7.5");
        String formattedDecimal = StringAndBigDecimalFormatter.formatBigDecimalToHours(decimal);
        assertEquals("8 Stunden", formattedDecimal);
    }

    @Test
    public void testFormatBigDecimalToHoursWithNegativeNumber() {
        BigDecimal negativeNumber = new BigDecimal("-3.7");
        String formattedNegativeNumber = StringAndBigDecimalFormatter.formatBigDecimalToHours(negativeNumber);
        assertEquals("-4 Stunden", formattedNegativeNumber);
    }

    @Test
    public void testFormatBigDecimalCurrencyToString_withPositiveValue() {
        BigDecimal valueToFormat = new BigDecimal("1234.56");
        String expected = "1.234,56 €";
        String actual = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(valueToFormat);
        actual = actual.replaceAll("\\h", " ");
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatBigDecimalCurrencyToString_withNegativeValue() {
        BigDecimal valueToFormat = new BigDecimal("-1234.56");
        String expected = "-1.234,56 €";
        String actual = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(valueToFormat);
        actual = actual.replaceAll("\\h", " ");
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatBigDecimalCurrencyToString_withZeroValue() {
        BigDecimal valueToFormat = new BigDecimal("0");
        String expected = "0,00 €";
        String actual = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(valueToFormat);
        actual = actual.replaceAll("\\h", " ");
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatStringToPercentageValueForScope_withIntegerValue() {
        BigDecimal expected = new BigDecimal("0.25");
        BigDecimal actual = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope("25");
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatStringToPercentageValueForScope_withFloatValue() {
        BigDecimal expected = new BigDecimal("0.10");
        BigDecimal actual = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope("10,5%");
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatStringToPercentageValueForScope_withCommaSeparatedValue() {
        BigDecimal expected = new BigDecimal("0.12");
        BigDecimal actual = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope("12,5%");
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatStringToPercentageValueForSalaryIncrease_withValidValue_shouldReturnCorrectResult() {
        // Arrange
        String valueToFormat = "5,5%";
        BigDecimal expectedValue = new BigDecimal("0.055");

        // Act
        BigDecimal result = StringAndBigDecimalFormatter.formatStringToPercentageValueForSalaryIncrease(valueToFormat);

        // Assert
        assertEquals(expectedValue, result);
    }

    @Test
    public void testFormatStringToPercentageValueForSalaryIncrease_withValidValueAndNoDecimalPart_shouldReturnCorrectResult() {
        // Arrange
        String valueToFormat = "10%";
        BigDecimal expectedValue = new BigDecimal("0.1");

        // Act
        BigDecimal result = StringAndBigDecimalFormatter.formatStringToPercentageValueForSalaryIncrease(valueToFormat);

        // Assert
        assertEquals(expectedValue, result);
    }

    @Test
    public void testFormatStringToPercentageValueForSalaryIncrease_withInvalidValue_shouldThrowException() {
        // Arrange
        String valueToFormat = "invalid";

        // Act and assert
        assertThrows(NumberFormatException.class, () -> StringAndBigDecimalFormatter.formatStringToPercentageValueForSalaryIncrease(valueToFormat));
    }

    @Test
    public void testFormatStringToPercentageValueForPayRate() {
        // Test normal value
        String input = "12.345%";
        BigDecimal expectedOutput = new BigDecimal("0.123").setScale(3, RoundingMode.HALF_EVEN);
        assertEquals(expectedOutput, StringAndBigDecimalFormatter.formatStringToPercentageValueForPayRate(input));

        // Test input with different format
        input = "12,345%";
        expectedOutput = new BigDecimal("0.123").setScale(3, RoundingMode.HALF_EVEN);
        assertEquals(expectedOutput, StringAndBigDecimalFormatter.formatStringToPercentageValueForPayRate(input));

        // Test input with no percentage sign
        input = "12.345";
        expectedOutput = new BigDecimal("0.123").setScale(3, RoundingMode.HALF_EVEN);
        assertEquals(expectedOutput, StringAndBigDecimalFormatter.formatStringToPercentageValueForPayRate(input));

        // Test input with no decimal places
        input = "12%";
        expectedOutput = new BigDecimal("0.120").setScale(3, RoundingMode.HALF_EVEN);
        assertEquals(expectedOutput, StringAndBigDecimalFormatter.formatStringToPercentageValueForPayRate(input));

        // Test input with more than 3 decimal places
        input = "12.3456%";
        expectedOutput = new BigDecimal("0.123").setScale(3, RoundingMode.HALF_EVEN);
        assertEquals(expectedOutput, StringAndBigDecimalFormatter.formatStringToPercentageValueForPayRate(input));
    }
    @Test
    public void testFormatStringToBigDecimalCurrency_withEuroSymbol() {
        String valueToFormat = "€1.234,56";
        BigDecimal expected = new BigDecimal("1234.56");
        assertEquals(expected, StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(valueToFormat));
    }

    @Test
    public void testFormatStringToBigDecimalCurrency_withoutEuroSymbol() {
        String valueToFormat = "1.234,56";
        BigDecimal expected = new BigDecimal("1234.56");
        assertEquals(expected, StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(valueToFormat));
    }

    @Test
    public void testFormatStringToBigDecimalCurrency_withPercentageSymbol() {
        String valueToFormat = "12,34%";
        BigDecimal expected = new BigDecimal("12.34");
        assertEquals(expected, StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(valueToFormat));
    }
}

