package excelchaos_view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class SmallSideBar extends JPanel {

    private BasicArrowButton arrowButtonEast;


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

    public void paint(Graphics g){


        super.paint(g);
        System.out.println("Here");
        g.setFont(new Font("Arial",Font.BOLD,18));
        g.setColor(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform at = new AffineTransform();
        at.rotate(Math.PI/2);
        g2d.setTransform(at);
        g2d.drawString("Navigationsleiste",75,-5);


    }
}
