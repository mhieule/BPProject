package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PayRateStageTypeDialogView extends JDialog {
    private JButton okayButton;
    private JButton closeButton;

    private JComboBox stageTypeSelecter;

    private JPanel buttonPanel;
    private JPanel pickPanel;

    /**
     * Initializes this gui component.
     */
    public void init() {
        setLayout(new BorderLayout());
        setTitle("Entgelttabelle Stufe 1 Variante auswählen");
        okayButton = new JButton("Ok");
        closeButton = new JButton("Abbrechen");

        pickPanel = new JPanel();
        buttonPanel = new JPanel();
        pickPanel.setLayout(new GridBagLayout());
        buttonPanel.setLayout(new FlowLayout());
        String[] choices = {
                "Entgeltabelle mit Stufe 1A und 1B", "Entgelttabelle mit Stufe 1"
        };
        stageTypeSelecter = new JComboBox<>(choices);
        pickPanel.add(stageTypeSelecter);


        buttonPanel.add(okayButton);
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);
        add(pickPanel, BorderLayout.CENTER);


        setSize(new Dimension(300, 200));
        setLocationRelativeTo(getParent());
        setAlwaysOnTop(true);
        setResizable(false);
        setVisible(true);
    }

    public JButton getOkayButton() {
        return okayButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JComboBox getStageTypeSelecter() {
        return stageTypeSelecter;
    }

    public void setActionListener(ActionListener l) {
        okayButton.addActionListener(l);
        closeButton.addActionListener(l);
    }
}
