package excelchaos_controller;

import excelchaos_model.datamodel.payratetablesdataoperations.PayRateTablesDataAccess;
import excelchaos_model.datamodel.payratetablesdataoperations.PayRateTablesDataDeleter;
import excelchaos_model.utility.PayRateTableNameStringEditor;
import excelchaos_view.PayRateTablesView;
import excelchaos_view.toolbarviews.ToolbarPayRateTablesView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class PayRateTablesController extends MouseAdapter implements ListSelectionListener, ActionListener {

    private PayRateTablesDataAccess payRateTablesDataAccess;
    private PayRateTablesDataDeleter payRateTablesDataDeleter;
    private PayRateTablesView payRateTablesView;
    private PayRateStageTypeDialogController payRateStageTypeDialogController;
    private ToolbarPayRateTablesView toolbar;
    private MainFrameController mainFrameController;
    private String title;


    public PayRateTablesController(MainFrameController mainFrameController) {
        this.mainFrameController = mainFrameController;
        payRateTablesDataAccess = new PayRateTablesDataAccess();
        payRateTablesDataDeleter = new PayRateTablesDataDeleter();
    }


    public void showPayRatesView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            payRateTablesView = new PayRateTablesView();
            payRateTablesView.init();
            toolbar = new ToolbarPayRateTablesView();
            toolbar.init();
            toolbar.setActionListener(this);
            payRateTablesView.add(toolbar, BorderLayout.NORTH);
            fillListWithPayRateTableNames();
            mainFrameController.addTab(title, payRateTablesView);
            payRateTablesView.setMouseListener(this);
            payRateTablesView.getPayRateTableList().getSelectionModel().addListSelectionListener(this);
            toolbar.getEditExistingPayRateTable().setEnabled(false);
            toolbar.getDeleteExistingPayRateTable().setEnabled(false);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    public void setTitle(String name) {
        title = name;
    }

    public String getTitle() {
        return title;
    }

    public void fillListWithPayRateTableNames() {
        String paygrade = getPayGradeFromTitle(); //PayGrade ist Gruppe/Klasse
        Vector<String> presentVector = payRateTablesDataAccess.getPayRateTablesNameVectorForList(paygrade);
        payRateTablesView.getPayRateTableList().setListData(presentVector);

    }

    public String getPayGradeFromTitle() {
        String result = "";
        if (title.equals("E13 Entgelttabellen")) {
            result = "E13";
        } else if (title.equals("E14 Entgelttabellen")) {
            result = "E14";
        }
        return result;
    }

    public void deletePayRateTable(String tableName) {
        String revertedTableName = PayRateTableNameStringEditor.revertToCorrectTableName(tableName);
        payRateTablesDataDeleter.deletePayRateTable(revertedTableName);
        updateview();
        mainFrameController.getUpdater().salaryUpDate();
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
                    ShowPayRateTableController payRateTableController = new ShowPayRateTableController(mainFrameController, (String) list.getSelectedValue(), getPayGradeFromTitle(), this);
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
                            ShowPayRateTableController payRateTableController = new ShowPayRateTableController(mainFrameController, (String) list.getSelectedValue(), getPayGradeFromTitle(), PayRateTablesController.this);
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
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        if (e.getValueIsAdjusting()) {
            if (lsm.getSelectedItemsCount() == 0) {
                toolbar.getEditExistingPayRateTable().setEnabled(false);
                toolbar.getDeleteExistingPayRateTable().setEnabled(false);
            } else {
                toolbar.getEditExistingPayRateTable().setEnabled(true);
                toolbar.getDeleteExistingPayRateTable().setEnabled(true);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == toolbar.getInsertNewPayRateTable()) {
            if (title == "E13 Entgelttabellen") {
                payRateStageTypeDialogController = new PayRateStageTypeDialogController(mainFrameController, this);
            } else if (title == "E14 Entgelttabellen") {
                payRateStageTypeDialogController = new PayRateStageTypeDialogController(mainFrameController, this);
            }

        } else if (e.getSource() == toolbar.getEditExistingPayRateTable()) {
            ShowPayRateTableController payRateTableController = new ShowPayRateTableController(mainFrameController, payRateTablesView.getPayRateTableList().getSelectedValue().toString(), getPayGradeFromTitle(), this);
            payRateTableController.insertValuesInTable();
            payRateTablesView.getPayRateTableList().clearSelection();
            toolbar.getEditExistingPayRateTable().setEnabled(false);
            toolbar.getDeleteExistingPayRateTable().setEnabled(false);
        } else if (e.getSource() == toolbar.getDeleteExistingPayRateTable()) {
            Object[] options = {"Ok", "Abbrechen"};
            int joptionResult = JOptionPane.showOptionDialog(null, "Sind Sie sicher, dass die ausgewählte Entgelttabelle gelöscht werden soll?", "Warnung", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (joptionResult == 0) {
                deletePayRateTable(payRateTablesView.getPayRateTableList().getSelectedValue().toString());
            }
            toolbar.getEditExistingPayRateTable().setEnabled(false);
            toolbar.getDeleteExistingPayRateTable().setEnabled(false);
        }
    }
}
