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
        JLabel label = (JLabel)super.getListCellRendererComponent(
                list,value,index,isSelected,cellHasFocus);
        Font font = new Font("Arial", Font.PLAIN, 25);
        label.setFont(font);
        return label;
    }
}
