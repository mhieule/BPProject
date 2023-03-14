package excelchaos_model.inputVerifier;

import excelchaos_model.utility.StringAndBigDecimalFormatter;

import javax.swing.*;
import java.math.BigDecimal;

public class SalaryVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        if (text.matches("")) {
            return true;
        } else if(text.matches("\\d+\\.*\\d*")) {
            ((JTextField) input).setText(text + ",00" + " €");
            return true;
        } else if (text.matches("\\d+\\.*\\d*,\\d{2}") || text.matches("\\d+\\.*\\d*\\.\\d{2}")) {
            ((JTextField) input).setText(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(text)));
            //((JTextField) input).setText(text.replaceFirst("(?s)(.*)" + "\\.","$1" + ",") + " €"); //replaceFirst written to replaceLast
            return true;
        } else if (text.matches("\\d+\\.*\\d*\\.\\d{1}")) {
            ((JTextField) input).setText(text.replaceFirst("(?s)(.*)" + "\\.","$1" + ",0") + " €");
            return true;
        } else if (text.matches("\\d+\\.*\\d*,\\d{1}")) {
            ((JTextField) input).setText(text + ",0 €");
            return true;
        } else if (text.matches("\\d+\\.*\\d*\\.\\d{2}\\h€")) {
            ((JTextField) input).setText(text.replaceFirst("(?s)(.*)" + "\\.","$1" + ",0") + " €");
            return true;
        } else if (text.matches("\\d+\\.*\\d*,\\d{2}\\h€")) {
            return true;
        } else {
            JOptionPane.showConfirmDialog(null, "Bitte geben Sie einen Geldwert ein und trennen Sie falls nötig die Nachkommastellen mit \",\" ab.", "Falsches Eingabe Format", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
