package excelchaos_model.inputVerifier;

import excelchaos_model.utility.StringAndBigDecimalFormatter;

import javax.swing.*;
import java.math.BigDecimal;

public class PayRateTablePercentVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        if (text.matches("")) {
            return true;
        } else if (text.matches("\\d{1,2}") || text.matches("\\d{1,2},\\d{1,3}")) {
            ((JTextField) input).setText(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(new BigDecimal(text.replaceAll(",", ".")),0));
            return true;
        } else if (text.matches("\\d{1,2}%") || text.matches("\\d{1,2},\\d{1,3}%")) {
            ((JTextField) input).setText(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(new BigDecimal(text.replaceAll(",", ".").replace("%","")),0));
            return true;
        } else if (text.matches("\\d{3}")) {
            double testDouble = Double.parseDouble(text);
            if (testDouble > 100) {
                JOptionPane.showConfirmDialog(null, "Der Beschäftigungsumfang darf nicht größer als 100% sein. Bitte passen Sie den Wert an.", "Beschäftigungsumfang zu groß", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                ((JTextField) input).setText(StringAndBigDecimalFormatter.formatBigDecimalToStringPayRateTable(new BigDecimal(text.replaceAll(",", ".")),0));
                return true;
            }
        } else if (text.matches("\\d{3}%")) {
            if (StringAndBigDecimalFormatter.formatStringToPercentageValueForPayRate(text).compareTo(new BigDecimal(1)) >0 ) {
                JOptionPane.showConfirmDialog(null, "Der Beschäftigungsumfang darf nicht größer als 100% sein. Bitte passen Sie den Wert an.", "Beschäftigungsumfang zu groß", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                return true;
            }
        } else {
            JOptionPane.showConfirmDialog(null, "Bitte geben Sie eine Zahl oder einen Prozentwert zwischen 0 und 100 ein.", "Falsches Eingabe Format", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }
}
