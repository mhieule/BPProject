package excelchaos_view;

import javax.swing.*;
import java.awt.*;

public class PayRateTablesView extends JPanel {
    private JPanel centerPanel;

    public void init(){
        setLayout(new BorderLayout());
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLUE);



        add(centerPanel,BorderLayout.CENTER);

    }
}
