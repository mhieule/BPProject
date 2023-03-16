package excelchaos_view.sidepanel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

/**
 * The SmallSideBar represents the small side panel when the SideMenuPanelTables is hidden in the view.
 * This class extends the JPanel class and contains a BasicArrowButton and a custom painting of text.
 * The class also provides methods to initialize the side panel, get the arrow button, set an action listener for the arrow button and custom paint the panel
 *
 * @see SideMenuPanelTables
 */
public class SmallSideBar extends JPanel {

    private BasicArrowButton arrowButtonEast;

    /**
     * Initializes the side panel with an arrow button and sets the preferred size and background color.
     */
    public void init() {
        arrowButtonEast = new BasicArrowButton(BasicArrowButton.EAST);

        add(arrowButtonEast, BorderLayout.PAGE_START);
        setBackground(Color.white);
        setPreferredSize(new Dimension(25, 100));
    }

    /**
     * Returns the BasicArrowButton used in the side panel.
     *
     * @return the BasicArrowButton used in the side panel
     */
    public BasicArrowButton getArrowButtonEast() {
        return arrowButtonEast;
    }

    /**
     * Sets the ActionListener for the arrow button in the side panel.
     * The ActionListener is responsible for handling user interactions with the arrow button. When a user
     * interacts with the button, the corresponding action event is triggered, and the ActionListener
     * is notified.
     * @see excelchaos_controller.sidepanelcontroller.SmallSideBarController
     *
     * @param l the ActionListener to set for the arrow button
     */
    public void setActionListener(ActionListener l) {
        arrowButtonEast.addActionListener(l);
    }

    /**
     * Custom paints the side panel with a rotated text.
     *
     * @param g the Graphics object to paint on
     */
    public void paint(Graphics g) {


        super.paint(g);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.setColor(Color.BLACK);

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform at = new AffineTransform();
        at.rotate(-Math.PI / 2);
        g2d.setTransform(at);
        g2d.drawString("Navigationsleiste", -225, 17);


    }
}
