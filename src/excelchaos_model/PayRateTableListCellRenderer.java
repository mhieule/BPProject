package excelchaos_model;

import javax.swing.*;
import java.awt.*;

public class PayRateTableListCellRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
        Font font = new Font("Dialog", Font.PLAIN, 20);
        label.setFont(font);
        label.setBorder(BorderFactory.createMatteBorder(0,
                0, 1, 0, Color.GRAY));
        return label;
    }
}
