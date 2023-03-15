package excelchaos_view.components;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {
    private JTextField searchField;
    private JLabel searchLabel;

    public SearchPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);
        searchLabel = new JLabel("Suchen:");
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(150, 30));
        add(searchLabel);
        add(searchField);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JLabel getSearchLabel() {
        return searchLabel;
    }


}
