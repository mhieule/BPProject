package excelchaos_view;

import javax.swing.*;
import java.awt.*;

public class PayRateTablesView extends JPanel {
    private JPanel centerPanel;
    private JScrollPane scrollPane;
    public void init(){
        setLayout(new BorderLayout());
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane();
        centerPanel.add(scrollPane);




        add(centerPanel,BorderLayout.CENTER);

    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
