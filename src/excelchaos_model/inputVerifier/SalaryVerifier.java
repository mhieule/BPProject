package excelchaos_model.inputVerifier;

import excelchaos_model.utility.StringAndBigDecimalFormatter;

import javax.swing.*;
import java.math.BigDecimal;

public class SalaryVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        if (text.matches("") || text.matches("\\d+\\.\\d{3},\\d{2}\\h€") || text.matches("\\d+,\\d{2}\\h€")) {
            return true;
        }
        else if(text.matches("\\d+") || text.matches("\\d+\\h€") || text.matches("\\d+\\.\\d{3}") || text.matches("\\d+\\.\\d{3},\\d{1,2}") || text.matches("\\d+,\\d{1,2}")){
            ((JTextField) input).setText(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(text)));
            return true;
        } else if (text.matches("\\d+\\.\\d{3}\\.\\d{1,2}\\h€") || text.matches("\\d+\\.\\d{3}\\.\\d{1,2}") || text.matches("\\d+\\.\\d{1,2}\\h€") || text.matches("\\d+\\.\\d{1,2}")) {
            //replaceFirst written to replaceLast
            ((JTextField) input).setText(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(text.replaceFirst("(?s)(.*)" + "\\.","$1" + ","))));
            return true;
        }
        JOptionPane.showConfirmDialog(null, "Bitte geben Sie einen Geldbetrag ein und trennen Sie falls nötig die Nachkommastellen mit \",\" ab.", "Falsches Eingabe Format", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
