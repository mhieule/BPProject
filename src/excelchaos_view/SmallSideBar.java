package excelchaos_view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;

public class SmallSideBar extends JPanel {

    private BasicArrowButton arrowButtonEast;
    private String s;


    public void init() {
        arrowButtonEast = new BasicArrowButton(BasicArrowButton.EAST);

        add(arrowButtonEast, BorderLayout.PAGE_START);
        setBackground(Color.white);
        setPreferredSize(new Dimension(25, 100));
    }

    public BasicArrowButton getArrowButtonEast() {
        return arrowButtonEast;
    }

    public void setActionListener(ActionListener l) {
        arrowButtonEast.addActionListener(l);
    }
}
