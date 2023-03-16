package excelchaos_view.components;

import javax.swing.*;

/**
 * The TabCloseButton class is a custom JButton that represents the close button to a tab. It displays an icon and provides a way to set and get the name of a tab.
 */
public class TabCloseButton extends JButton {
    private String tabName;

    /**
     * Constructs a new TabCloseButton with the specified icon.
     *
     * @param icon the icon to display on the button
     */
    public TabCloseButton(ImageIcon icon) {
        super(icon);
    }

    /**
     * Sets the name of the tab associated with this button.
     *
     * @param title the name of the tab
     */
    public void setTabName(String title) {
        tabName = title;
    }

    /**
     * Returns the name of the tab associated with this button.
     *
     * @return the name of the tab
     */
    public String getTabName() {
        return tabName;
    }
}
