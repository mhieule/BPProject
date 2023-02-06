package excelchaos_controller;

import excelchaos_model.SalaryTableManager;
import excelchaos_view.PayRateTablesView;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PayRateTablesController extends MouseAdapter implements ActionListener {
    private PayRateTablesView payRateTablesView;
    private MainFrameController frameController;
    private ToolbarPayRateTablesController toolbarPayRateTables;
    private String title;

    private SalaryTableManager manager;

    public PayRateTablesController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        manager = new SalaryTableManager();


    }


    public void showPayRatesView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            //SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
            payRateTablesView = new PayRateTablesView();
            toolbarPayRateTables = new ToolbarPayRateTablesController(frameController, this);
            payRateTablesView.init();
            payRateTablesView.add(toolbarPayRateTables.getToolbar(), BorderLayout.NORTH);
            fillListWithPayRateTableNames();
            mainFrameController.addTab(title, payRateTablesView);
            payRateTablesView.setMouseListener(this);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
            //SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
        }
    }

    public void setTitle(String name) {
        title = name;
    }

    public String getTitle() {
        return title;
    }

    public void fillListWithPayRateTableNames() {
        //payRateTablesView.getCenterPanel().removeAll();
        String paygrade = getPayGradeFromTitle(); //PayGrade ist Gruppe/Klasse
        int numberOfTables = manager.getNumOfTables(paygrade);
        //JPanel buttonPanel = new JPanel();
        String[] tableNames = new String[numberOfTables];
        //buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
        for (int i = 0; i < numberOfTables; i++) {
            tableNames[i] = manager.getDistinctTableNames(paygrade).get(i);

        }
        payRateTablesView.getPayRateTableList().setListData(tableNames);

    }

    private String getPayGradeFromTitle() {
        String result = "";
        if (title.equals("E13 Entgelttabellen")) {
            result = "E13";
        } else if (title.equals("E14 Entgelttabellen")) {
            result = "E14";
        } else if (title.equals("SHK Entgelttabellen")) {
            result = "SHK";
        }
        return result;
    }

    public void updateview() {
        fillListWithPayRateTableNames();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JList list = (JList) e.getSource();
        Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex());
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (e.getClickCount() == 2) {
                if (r != null && r.contains(e.getPoint())) {
                    ShowPayRateTableController payRateTableController = new ShowPayRateTableController(frameController, manager, (String) list.getSelectedValue(), getPayGradeFromTitle());
                    payRateTableController.insertValuesInTable();
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        JList list = (JList) e.getSource();
        if (SwingUtilities.isRightMouseButton(e)) {
            list.setSelectedIndex(list.locationToIndex(e.getPoint()));
            showPopUp(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            showPopUp(e);
        }

    }

    private void showPopUp(MouseEvent e) {
        JList list = (JList) e.getSource();
        Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex());
        if (list.getSelectedValue() != null) {
            if (e.isPopupTrigger()) {
                if (r != null && r.contains(e.getPoint())) {
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem openItem = new JMenuItem();
                    openItem.setText("Entgelttabelle bearbeiten");
                    openItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ShowPayRateTableController payRateTableController = new ShowPayRateTableController(frameController, manager, (String) list.getSelectedValue(), getPayGradeFromTitle());
                            payRateTableController.insertValuesInTable();
                        }
                    });
                    menu.add(openItem);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }
    }
}
