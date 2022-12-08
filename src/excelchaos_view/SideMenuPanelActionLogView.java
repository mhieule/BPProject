package excelchaos_view;

import javax.swing.*;
import java.awt.*;

public class SideMenuPanelActionLogView extends JPanel {
    public static DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> list = new JList<>(model);

    public void init() {
        setBackground(Color.white);
        setLayout(new BorderLayout());
        model.addElement("Historie");
        add(list, BorderLayout.CENTER);
        setPreferredSize(new Dimension(150, 100));
    }


}
