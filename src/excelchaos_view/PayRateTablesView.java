package excelchaos_view;

import excelchaos_view.components.tablecellrenderer.PayRateTableListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class PayRateTablesView extends JPanel {
    private JPanel centerPanel;

    private JList payRateTableList;

    /**
     * Initializes this gui component.
     */
    public void init() {
        setLayout(new BorderLayout());
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout());

        payRateTableList = new JList();
        payRateTableList.setCellRenderer(new PayRateTableListCellRenderer());
        payRateTableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        payRateTableList.setLayoutOrientation(JList.VERTICAL);
        payRateTableList.setFixedCellHeight(35);
        PayRateTableListCellRenderer renderer = (PayRateTableListCellRenderer) payRateTableList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        JScrollPane listScroller = new JScrollPane(payRateTableList);
        centerPanel.add(listScroller);
        add(centerPanel, BorderLayout.CENTER);

    }

    public JList getPayRateTableList() {
        return payRateTableList;
    }

    public void setMouseListener(MouseAdapter l) {
        payRateTableList.addMouseListener(l);
    }
}
