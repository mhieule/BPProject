package excelchaos_controller;

import excelchaos_model.SalaryTableManager;
import excelchaos_model.utility.PayRateTableNameDateSeperator;
import excelchaos_model.utility.PayRateTableNameStringEditor;
import excelchaos_view.PayRateTablesView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Vector;

public class PayRateTablesController extends MouseAdapter implements ListSelectionListener {
    private PayRateTablesView payRateTablesView;
    private MainFrameController frameController;
    private ToolbarPayRateTablesController toolbarPayRateTables;
    private String title;
    PayRateTableNameStringEditor stringEditor = new PayRateTableNameStringEditor();

    private SalaryTableManager manager;

    public PayRateTablesController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        manager = new SalaryTableManager();


    }


    public void showPayRatesView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            payRateTablesView = new PayRateTablesView();
            toolbarPayRateTables = new ToolbarPayRateTablesController(frameController, this);
            payRateTablesView.init();
            payRateTablesView.add(toolbarPayRateTables.getToolbar(), BorderLayout.NORTH);
            fillListWithPayRateTableNames();
            mainFrameController.addTab(title, payRateTablesView);
            payRateTablesView.setMouseListener(this);
            payRateTablesView.getPayRateTableList().getSelectionModel().addListSelectionListener(this);
            toolbarPayRateTables.getToolbar().getEditExistingPayRateTable().setEnabled(false);
            toolbarPayRateTables.getToolbar().getDeleteExistingPayRateTable().setEnabled(false);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    public PayRateTablesView getPayRateTablesView() {
        return payRateTablesView;
    }

    public void setTitle(String name) {
        title = name;
    }

    public String getTitle() {
        return title;
    }

    public void fillListWithPayRateTableNames() {
        String paygrade = getPayGradeFromTitle(); //PayGrade ist Gruppe/Klasse
        PayRateTableNameDateSeperator payRateTableNameDateSeperator = new PayRateTableNameDateSeperator();

        int numberOfTables = manager.getNumOfTables(paygrade);
        String[] tableNames = new String[numberOfTables];
        Vector<String> stringVector = new Vector<>();
        for (int i = 0; i < numberOfTables; i++) {

            tableNames[i] = manager.getDistinctTableNames(paygrade).get(i);
            stringVector.add(tableNames[i]);

        }
        stringVector.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                LocalDate l1 = payRateTableNameDateSeperator.seperateDateAsDate(o1);
                LocalDate l2 = payRateTableNameDateSeperator.seperateDateAsDate(o2);
                return l2.compareTo(l1);
            }
        });
        Vector<String> presentVector = new Vector<>();
        for (String string : stringVector){
            presentVector.add(stringEditor.createReadableTableNameForView(string));
        }
        payRateTablesView.getPayRateTableList().setListData(presentVector);

    }

    public String getPayGradeFromTitle() {
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

    public void deletePayRateTable(String tableName){
        String revertedTableName = stringEditor.revertToCorrectTableName(tableName);
        manager.removeSalaryTable(revertedTableName);
        updateview();
    }

    public void updateview() {
        fillListWithPayRateTableNames();
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        JList list = (JList) e.getSource();
        Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex());
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (e.getClickCount() == 2) {
                if (r != null && r.contains(e.getPoint())) {
                    ShowPayRateTableController payRateTableController = new ShowPayRateTableController(frameController, manager, (String) list.getSelectedValue(), getPayGradeFromTitle(),this);
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
                            ShowPayRateTableController payRateTableController = new ShowPayRateTableController(frameController, manager, (String) list.getSelectedValue(), getPayGradeFromTitle(),PayRateTablesController.this);
                            payRateTableController.insertValuesInTable();
                        }
                    });
                    menu.add(openItem);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        if(e.getValueIsAdjusting()){
            if(lsm.getSelectedItemsCount() == 0){
                toolbarPayRateTables.getToolbar().getEditExistingPayRateTable().setEnabled(false);
                toolbarPayRateTables.getToolbar().getDeleteExistingPayRateTable().setEnabled(false);
            } else {
                toolbarPayRateTables.getToolbar().getEditExistingPayRateTable().setEnabled(true);
                toolbarPayRateTables.getToolbar().getDeleteExistingPayRateTable().setEnabled(true);
            }
        }

    }
}
