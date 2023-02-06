package excelchaos_view;

import excelchaos_model.PayRateTableListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

public class PayRateTablesView extends JPanel {
    private JPanel centerPanel;

    private JList payRateTableList;
    public void init(){
        setLayout(new BorderLayout());
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipadx = 700;
        constraints.ipady = 500;

        payRateTableList = new JList();
        payRateTableList.setCellRenderer(new PayRateTableListCellRenderer());
        payRateTableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        payRateTableList.setLayoutOrientation(JList.VERTICAL);
        payRateTableList.setFixedCellHeight(40);
        PayRateTableListCellRenderer renderer = (PayRateTableListCellRenderer) payRateTableList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setFont(new Font("Arial",Font.PLAIN,30));
        JScrollPane listScroller = new JScrollPane(payRateTableList);
        centerPanel.add(listScroller,constraints);
        add(centerPanel,BorderLayout.CENTER);

    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JList getPayRateTableList() {
        return payRateTableList;
    }

    public void setMouseListener(MouseAdapter l){
        payRateTableList.addMouseListener(l);
    }
}
