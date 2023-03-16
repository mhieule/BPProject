package excelchaos_model.inputVerifier;

import excelchaos_model.utility.StringAndBigDecimalFormatter;

import javax.swing.*;
import java.math.BigDecimal;

public class SalaryIncreasePercentageVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        if (text.matches("")) {
            return true;
        } else if (text.matches("\\d{1,2}") || text.matches("\\d{1,2},\\d{1,3}")) {
            ((JTextField) input).setText(StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseInsertion(new BigDecimal(text.replaceAll(",", "."))));
            return true;
        } else if (text.matches("\\d{1,2}%") || text.matches("\\d{1,2},\\d{1,3}%")) {
            ((JTextField) input).setText(StringAndBigDecimalFormatter.formatPercentageToStringSalaryIncreaseInsertion(new BigDecimal(text.replaceAll(",", ".").replace("%", ""))));
            return true;
        } else {
            JOptionPane.showConfirmDialog(null, "Bitte geben Sie eine Zahl oder einen Prozentwert ein.", "Falsches Eingabe Format", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
