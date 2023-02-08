package excelchaos_controller;

import excelchaos_model.PayRateTableCalculationModel;
import excelchaos_model.SalaryTable;
import excelchaos_model.SalaryTableManager;
import excelchaos_model.StringAndDoubleTransformationForDatabase;
import excelchaos_view.InsertPayRateTableView;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class InsertPayRateTableController extends MouseAdapter implements ActionListener {

    private InsertPayRateTableView insertPayRateTableView;
    private MainFrameController frameController;
    private String title;

    private String tableName;

    private PayRateTableCalculationModel model;

    private PayRateTablesController payRateController;


    public InsertPayRateTableController(MainFrameController mainFrameController, PayRateTablesController payRateTablesController, String name, String[] columnNames, boolean typeOfTable) {
        frameController = mainFrameController;
        insertPayRateTableView = new InsertPayRateTableView();
        payRateController = payRateTablesController;
        title = name;
        insertPayRateTableView.init(columnNames);
        insertPayRateTableView.setActionListener(this);
        insertPayRateTableView.setMouseListener(this);
        model = new PayRateTableCalculationModel();
        determineTableName(typeOfTable);
        insertPayRateTableView.getTfNameOfTable().setText(tableName);

    }

    public void showInsertPayRateTableView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, insertPayRateTableView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    private void determineTableName(boolean typeOfTable){
        if(typeOfTable){
            tableName = "Entgelttabelle " + determinePayGrade() + " mit Stufe 1A und 1B";
        } else {
            tableName = "Entgelttabelle " + determinePayGrade() + " mit Stufe 1";
        }
    }


    public String getTitle() {
        return title;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertPayRateTableView.getCancelButton()) {
            frameController.getTabs().removeTabNewWindow(insertPayRateTableView);

        } else if (e.getSource() == insertPayRateTableView.getSaveAndExit()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String tableName = insertPayRateTableView.getTfNameOfTable().getText() + "_" +insertPayRateTableView.getDatePicker().getDate().format(dateTimeFormatter);
            String paygrade = determinePayGrade();
            insertValuesInDatabase(tableName, paygrade, prepareTableForDatabaseInsertion());
            payRateController.updateview();
            frameController.getTabs().removeTabNewWindow(insertPayRateTableView);

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            if (e.isPopupTrigger()) {
                highlightRow(e);
                showPopUp(e);
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            if (e.isPopupTrigger()) {
                highlightRow(e);
                showPopUp(e);
            }

        }

    }

    protected void highlightRow(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        int col = table.columnAtPoint(point);

        table.setRowSelectionInterval(row, row);
        table.setColumnSelectionInterval(col, col);
    }

    private void showPopUp(MouseEvent mouseEvent) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem pasteRow = new JMenuItem();
        pasteRow.setText("Zeile einfügen");
        pasteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String clipboardCopy;
                try {
                    clipboardCopy = (String) clipboard.getData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                String[] resultString = model.prepareInsertionString(clipboardCopy);
                int row = insertPayRateTableView.getTable().getSelectedRow();
                int columnCounter = 0;
                if (row == 0 || row == 11 || row == 12 || row == 13 || row == 14) {
                    columnCounter = 1;
                }
                for (int i = 0; i < resultString.length; i++) {
                    if ((row == 8 || row == 9) && columnCounter % 2 == 1) {
                        columnCounter++;
                        i--;
                        continue;
                    }if (row == 10 && columnCounter % 2 == 0 && columnCounter != 0) {
                        columnCounter++;
                        i--;
                        continue;
                    } else
                        insertPayRateTableView.getTable().setValueAt(resultString[i], row, columnCounter);

                    columnCounter++;
                }

            }
        });
        menu.add(pasteRow);
        menu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
    }


    public InsertPayRateTableView getInsertPayRateTableView() {
        return insertPayRateTableView;
    }


    private double[][] prepareTableForDatabaseInsertion() {
        double[][] result = new double[insertPayRateTableView.getTable().getRowCount()][insertPayRateTableView.getTable().getRowCount()];
        StringAndDoubleTransformationForDatabase converter = new StringAndDoubleTransformationForDatabase();
        for (int row = 0; row < insertPayRateTableView.getTable().getRowCount(); row++) {
            for (int column = 0; column < insertPayRateTableView.getTable().getColumnCount(); column++) {
                if (insertPayRateTableView.getTable().getValueAt(row, column) == null || insertPayRateTableView.getTable().getValueAt(row, column).equals("")) {
                    System.out.println(insertPayRateTableView.getTable().getValueAt(row, column) + "Nullwerte");
                    result[row][column] = 0;
                } else {
                    System.out.println(insertPayRateTableView.getTable().getValueAt(row, column) + "Tabellenwerte");
                    result[row][column] = converter.transformStringToDouble((String) insertPayRateTableView.getTable().getValueAt(row, column));
                }
            }
        }
        return result;
    }

    private void insertValuesInDatabase(String name, String paygrade, double[][] values) {
        SalaryTableManager manager = new SalaryTableManager();
        for (int column = 0; column < values[0].length; column++) {
            String tableName = name;
            double grundentgelt = values[0][column];
            double av_ag_anteil_lfd_entgelt = values[1][column];
            double kv_ag_anteil_lfd_entgelt = values[2][column];
            double zusbei_af_lfd_entgelt = values[3][column];
            double pv_ag_anteil_lfd_entgelt = values[4][column];
            double rv_ag_anteil_lfd_entgelt = values[5][column];
            double sv_umlage_u2 = values[6][column];
            double steuern_ag = values[7][column];
            double zv_Sanierungsbeitrag = values[8][column];
            double zv_umlage_allgemein = values[9][column];
            double vbl_wiss_4perc_ag_buchung = values[10][column];
            double mtl_kosten_ohne_jsz = values[11][column];
            double jsz_als_monatliche_zulage = values[12][column];
            double mtl_kosten_mit_jsz = values[13][column];
            double jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung = values[14][column];
            String grade = paygrade;

            SalaryTable salaryTable = new SalaryTable(tableName, grundentgelt, av_ag_anteil_lfd_entgelt, kv_ag_anteil_lfd_entgelt, zusbei_af_lfd_entgelt, pv_ag_anteil_lfd_entgelt, rv_ag_anteil_lfd_entgelt, sv_umlage_u2, steuern_ag, zv_Sanierungsbeitrag, zv_umlage_allgemein, vbl_wiss_4perc_ag_buchung, mtl_kosten_ohne_jsz, jsz_als_monatliche_zulage, mtl_kosten_mit_jsz, jaehrliche_arbeitgeberbelastung_inklusive_jaehressonderzahlung, grade);
            manager.addSalaryTable(salaryTable);
        }
    }


    private String determinePayGrade() {
        String result;
        if (title.contains("E13")) {
            result = "E13";
            return result;
        } else {
            result = "E14";
            return result;
        }
    }
}
