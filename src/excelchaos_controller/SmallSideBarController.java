package excelchaos_controller;

import excelchaos_view.SmallSideBar;

public class SmallSideBarController {
    private SmallSideBar smallSideBar;

    public SmallSideBarController(){
        smallSideBar = new SmallSideBar();
        smallSideBar.init();
    }

    public SmallSideBar getSmallSideBar() {
        return smallSideBar;
    }
}
