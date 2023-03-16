package excelchaos_view.components.tablecellrenderer;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * The MultiLineTableCellRenderer class is a custom renderer for JTable cells that allows
 * multi-line rendering of text. It extends the JList class and implements the TableCellRenderer
 * interface.
 */
public class MultiLineTableCellRenderer extends JList<String> implements TableCellRenderer {

    /**Returns the component used for rendering the cell. This method is called each time a cell
     * in the table needs to be rendered. It creates and returns a custom component that can display
     * multi-line text.
     *
     * @param table           the <code>JTable</code> that is asking the renderer to draw; can be <code>null</code>
     * @param value           the value of the cell to be rendered.  It is up to the specific renderer to interpret and draw the value.  For example, if <code>value</code>
     *                        is the string "true", it could be rendered as a string or it could be rendered as a checkbox that is checked.  <code>null</code> is a valid value.
     * @param isSelected      true if the cell is to be rendered with the selection highlighted; otherwise false
     * @param hasFocus        if true, render cell appropriately.  For example, put a special border on the cell, if the cell can be edited, render in the color used
     *                        to indicate editing
     * @param row             the row index of the cell being drawn.  When drawing the header, the value of <code>row</code> is -1
     * @param column          the column index of the cell being drawn
     *
     * @return the component which is used for rendering the cell
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Check if the current row is selected
        boolean selected = table.getSelectionModel().isSelectedIndex(row);
        // Retrieve the default table cell renderer component for the column header from the table
        Component component = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, value, false, false, -1, -2);
        // Set the horizontal alignment of the component to left
        ((JLabel) component).setHorizontalAlignment(SwingConstants.LEFT);
        // If the current row is selected, do nothing
        if (selected) {
            //Do nothing
        } else {
            // Set the font of the component to plain
            component.setFont(component.getFont().deriveFont(Font.PLAIN));
        }
        //Return the component
        return component;
    }
}


