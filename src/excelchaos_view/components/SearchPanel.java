package excelchaos_view.components;

import javax.swing.*;
import java.awt.*;

/**
 * The SearchPanel class is a custom JPanel that displays a search field and a search label.
 * It provides methods to access the search field and the search label.
 */

public class SearchPanel extends JPanel {
    private JTextField searchField;
    private JLabel searchLabel;

    /**
     * Constructs a new SearchPanel with a left-aligned FlowLayout and a white background.
     * It creates a search label and a search field, and adds them to the panel.
     */
    public SearchPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);
        searchLabel = new JLabel("Suchen:");
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150, 30));
        add(searchLabel);
        add(searchField);
    }

    /**
     * Returns the search field component of the panel.
     *
     * @return the search field component of the panel
     */

    public JTextField getSearchField() {
        return searchField;
    }

    /**
     * Returns the search label component of the panel.
     *
     * @return the search label component of the panel
     */
    public JLabel getSearchLabel() {
        return searchLabel;
    }


}
