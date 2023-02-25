package excelchaos_view.components;

import javax.swing.*;

public class SearchPanelToolbar extends JToolBar {

    private SearchPanel searchPanel;

    public void setUpSearchPanel(){
        searchPanel = new SearchPanel();
        add(searchPanel);
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }



}
