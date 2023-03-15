package excelchaos_controller.sidepanelcontroller;

import excelchaos_controller.MainFrameController;
import excelchaos_view.sidepanel.SmallSideBar;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SmallSideBarController implements ActionListener {
    private SmallSideBar smallSideBar;
    private MainFrameController frameController;
    private SideMenuPanelTablesController sideMenuTables;

    /**
     * Constructor for the SmallSideBarController
     * @param mainFrameController the MainFrameController
     */
    public SmallSideBarController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        smallSideBar = new SmallSideBar();
        smallSideBar.init();
        smallSideBar.setActionListener(this);
    }

    /**
     * getter Method for the SmallSideBar
     * @return the SmallSideBar
     */
    public SmallSideBar getSmallSideBar() {
        return smallSideBar;
    }

    /**
     * Depending on e calls Method eastArrowButtonPressed
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == smallSideBar.getArrowButtonEast()) {
            eastArrowButtonPressed(frameController);
        }
    }

    /**
     * Removes the SmallSideBar and adds the SideMenuPanelTablesController
     * @param mainFrameController the MainFrameController
     */
    private void eastArrowButtonPressed(MainFrameController mainFrameController){
        mainFrameController.getWindow().remove(smallSideBar);
        sideMenuTables = new SideMenuPanelTablesController(mainFrameController);
        mainFrameController.getWindow().add(sideMenuTables.getSideTable(), BorderLayout.WEST);
        mainFrameController.getWindow().revalidate();
        mainFrameController.getWindow().repaint();
    }
}
