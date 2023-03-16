package excelchaos_view.components.tablecellrenderer;

import javax.swing.*;
import java.awt.*;

/**
 * The PayRateTableListCellRenderer class is a custom renderer for JList cells that displays
 * pay rates. It extends the DefaultListCellRenderer class and provides a customized rendering
 * of the cell component.
 */

public class PayRateTableListCellRenderer extends DefaultListCellRenderer {
    /**
     * Returns the component used for rendering the cell. This method is called each time a cell
     * in the list needs to be rendered. It creates and returns a custom component that displays
     * the pay rate with a custom font and a gray bottom border.
     *
     * @param list The JList we're painting.
     * @param value The value returned by list.getModel().getElementAt(index).
     * @param index The cells index.
     * @param isSelected True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * @return the component used for rendering the cell
     */
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        // Call the superclass method to get the default label component
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        // Set the custom font of the label
        Font font = new Font("Dialog", Font.PLAIN, 20);
        label.setFont(font);
        // Set the bottom border of the label to gray
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        // Return the customized label component
        return label;
    }
}
