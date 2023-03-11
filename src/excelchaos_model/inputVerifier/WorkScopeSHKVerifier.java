package excelchaos_model.inputVerifier;

import javax.swing.*;

public class WorkScopeSHKVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        if (text.matches("")) {
            return true;
        }
        else if (text.matches("\\d{1}") || text.matches("\\d{2}")) {
            ((JTextField) input).setText(text + " Stunden");
            return true;
        } else if (text.matches("\\d{1}?h") || text.matches("\\d{2}?h")) {
            text = text.replaceAll("h"," Stunden");
            ((JTextField) input).setText(text);
            return true;
        } else if (text.matches("\\d{1}? Stunden") || text.matches("\\d{2}? Stunden")) {
            return true;
        }
        JOptionPane.showConfirmDialog(null, "Bitte geben Sie eine Zahl oder ein Wert im Format 20h.", "Falsches Eingabe Format", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        return false;
    }
}
