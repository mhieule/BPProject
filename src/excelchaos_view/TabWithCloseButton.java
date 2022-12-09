package excelchaos_view;

import javax.swing.*;


public class TabWithCloseButton extends JTabbedPane {

    public TabWithCloseButton(int a, int b){
        super(a,b);
    }

    public void addCloseButton(int index){
        this.setTabComponentAt(index, new ButtonTabComponent(this));
    }
}
