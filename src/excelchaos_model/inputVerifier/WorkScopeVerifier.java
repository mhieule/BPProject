package excelchaos_model.inputVerifier;

import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;

import javax.swing.*;

public class WorkScopeVerifier extends InputVerifier {
    StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();

    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        if (text.matches("")) {
            return true;
        } else if (text.matches("\\d{1}") || text.matches("\\d{2}")) {
            ((JTextField) input).setText(text + "%");
            return true;
        } else if (text.matches("\\d{1}?%") || text.matches("\\d{2}?%")) {
            return true;
        } else if (text.matches("\\d{3}")) {
            double testDouble = Double.parseDouble(text);
            if (testDouble > 100) {
                JOptionPane.showConfirmDialog(null, "Der Beschäftigungsumfang darf nicht größer als 100% sein. Bitte passen Sie den Wert an.", "Beschäftigungsumfang zu groß", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                ((JTextField) input).setText(text + "%");
                return true;
            }
        } else if (text.matches("\\d{3}?%")) {
            if (transformer.formatStringToPercentageValueForScope(text) > 1) {
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
