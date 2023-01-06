package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ShowSalaryStageDialogView extends JDialog {
    private JButton okayButton;
    private JButton closeButton;
    private DatePicker datePicker;

    private JPanel buttonPanel;
    private JPanel datePanel;


    public void init() {
        setLayout(new BorderLayout());
        setTitle("Datum auswählen");
        okayButton = new JButton("Okay");
        closeButton = new JButton("Abbrechen");
        datePicker = new DatePicker();
        datePicker.setDateToToday();



        datePanel = new JPanel();
        //datePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        datePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        datePanel.add(datePicker,c);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okayButton);
        buttonPanel.add(closeButton);
        add(buttonPanel,BorderLayout.SOUTH);


        add(datePanel,BorderLayout.CENTER);

        setSize(new Dimension(300, 200));
        setLocationRelativeTo(getParent());
        setResizable(false);
        setVisible(true);

    }
    public void setActionListener(ActionListener l){
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
