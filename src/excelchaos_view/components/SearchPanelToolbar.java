package excelchaos_view.components;

import javax.swing.*;

/**
 * The SearchPanelToolbar class is a custom JToolBar that displays a SearchPanel.
 * It provides methods to set up the SearchPanel and access it.
 */
public class SearchPanelToolbar extends JToolBar {

    private SearchPanel searchPanel;

    /**
     * Sets up the SearchPanel and adds it to the toolbar.
     */
    public void setUpSearchPanel() {
        searchPanel = new SearchPanel();
        add(searchPanel);
    }

    /**
     * Returns the SearchPanel component of the toolbar.
     * @return the SearchPanel component of the toolbar
     */

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }


}
