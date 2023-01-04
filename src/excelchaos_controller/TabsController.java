package excelchaos_controller;

import excelchaos_view.DnDCloseButtonTabbedPane;
import excelchaos_view.MainFrame;
import excelchaos_view.TabCloseButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TabsController  {
    private DnDCloseButtonTabbedPane tabs;
    private MainFrameController frameController;

    private String showPersonTab = "Personalstammdaten";
    private String salaryTab = "Gehaltsliste";
    private String salaryHistoryTab = "Gehaltshistorie";

    public TabsController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        tabs = new DnDCloseButtonTabbedPane(frameController.getWindow());

    }

    public DnDCloseButtonTabbedPane getTabs() {
        return tabs;
    }


    /*@Override
    public void actionPerformed(ActionEvent e) {
        TabCloseButton buttonInQuestion = (TabCloseButton) e.getSource();
        if(buttonInQuestion.getTabName().equals(showPersonTab)){
            frameController.getWindow().remove(frameController.getShowPersonalData().getToolbarShowPerson().getToolbar());
            frameController.getWindow().revalidate();
            frameController.getWindow().repaint();
        } else if(buttonInQuestion.getTabName().equals(salaryTab)){
            frameController.getWindow().remove(frameController.getSalaryListController().getToolbarSalary().getToolbar());
            frameController.getWindow().revalidate();
            frameController.getWindow().repaint();
        } else if(buttonInQuestion.getTabName().equals(salaryHistoryTab)){
            frameController.getWindow().remove(frameController.getSalaryHistoryController().getToolbarSalaryHistory().getToolbar());
            frameController.getWindow().revalidate();
            frameController.getWindow().repaint();
    }



    }*/
}
