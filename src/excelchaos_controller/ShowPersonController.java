package excelchaos_controller;

import excelchaos_view.ShowPersonView;

public class ShowPersonController {
    private ShowPersonView personView;

    public ShowPersonController() {
        personView = new ShowPersonView();
        personView.init();
    }

    public ShowPersonView getPersonView(){
        return personView;
    }
}
