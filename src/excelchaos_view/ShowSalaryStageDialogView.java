package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ShowSalaryStageDialogView extends JDialog {
    private JButton okayButton;
    private JButton closeButton;
    private DatePicker datePicker;

    private JLabel dateLabel;

    private JPanel buttonPanel;
    private JPanel datePanel;


    public void init() {
        setLayout(new BorderLayout());
        setTitle("Gehaltsprojektion");
        dateLabel = new JLabel("Datum auswählen");
        okayButton = new JButton("Ok");
        closeButton = new JButton("Abbrechen");
        datePicker = new DatePicker();
        datePicker.setDateToToday();


        datePanel = new JPanel();
        datePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        datePanel.add(dateLabel, constraints);
        constraints.gridy = 1;
        constraints.insets.bottom = 10;
        constraints.insets.top = 10;
        datePanel.add(datePicker, constraints);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okayButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);


        add(datePanel, BorderLayout.CENTER);

        setSize(new Dimension(300, 200));
        setLocationRelativeTo(getParent());
        setAlwaysOnTop(true);
        setResizable(false);
        setVisible(true);

    }

    public void setActionListener(ActionListener l) {
        okayButton.addActionListener(l);
        closeButton.addActionListener(l);
    }

    public JButton getOkayButton() {
        return okayButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
}
