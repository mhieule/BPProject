package excelchaos_view.components;

import javax.swing.*;

public class TabCloseButton extends JButton {
    private String tabName;

    public TabCloseButton(ImageIcon icon) {
        super(icon);
    }

    public void setTabName(String title) {
        tabName = title;
    }

    public String getTabName() {
        return tabName;
    }
}
