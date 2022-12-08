package excelchaos_controller;

import excelchaos_view.MainMenuPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanelController implements ActionListener {
    private MainMenuPanel mainMenu;

    public MainMenuPanelController(){
        mainMenu = new MainMenuPanel();
        mainMenu.init();
        mainMenu.setActionListener(this);
    }

    public MainMenuPanel getMainMenu(){
        return mainMenu;
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == mainMenu.getPersonenlisteButton()){
            mainMenu.setBackground(Color.GREEN);
            System.out.println("Test");
        }
    }

}
