package excelchaos_controller;

import excelchaos_view.MainFrame;
import excelchaos_view.MainMenuPanel;

public class MainFrameController {
    private MainFrame mainFrame;
    MainMenuPanelController mainMenu;

    public MainFrameController(){
        MainFrame window = new MainFrame();
        mainMenu = new MainMenuPanelController();
        window.init();
        window.add(mainMenu.getMainMenu());
        window.setVisible(true);

    }
}
