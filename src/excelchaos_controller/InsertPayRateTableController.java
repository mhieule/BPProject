package excelchaos_controller;

import excelchaos_model.datamodel.payratetablesdataoperations.PayRateTableCalculationModel;
import excelchaos_model.datamodel.payratetablesdataoperations.PayRateTablesDataInserter;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.InsertPayRateTableView;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class InsertPayRateTableController extends MouseAdapter implements ActionListener {
    private PayRateTablesDataInserter payRateTablesDataInserter;
    private InsertPayRateTableView insertPayRateTableView;
    private MainFrameController frameController;
    private String title;
    private String tableName;
    private PayRateTableCalculationModel model;
    private PayRateTablesController payRateController;

    /**
     * Constructor for the InsertPayRateTableController. Initializes the following attributes.
     *
     * @param mainFrameController     the MainFrameController
     * @param payRateTablesController the PayRateTablesController
     * @param name                    the title of the tab
     * @param columnNames             the column names of the table
     * @param typeOfTable             the type of the table
     */
    public InsertPayRateTableController(MainFrameController mainFrameController, PayRateTablesController payRateTablesController, String name, String[] columnNames, boolean typeOfTable) {
        frameController = mainFrameController;
        payRateTablesDataInserter = new PayRateTablesDataInserter();
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

    /**
     * Adds the insertPayRateTableView as a tab.
     *
     * @param mainFrameController the MainFrameController
     */
    public void showInsertPayRateTableView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, insertPayRateTableView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    /**
     * determines the form of the table
     *
     * @param typeOfTable boolean true if the table is a table with 1A and 1B, false if the table is a table with only 1
     */
    private void determineTableName(boolean typeOfTable) {
        if (typeOfTable) {
            tableName = "Entgelttabelle " + determinePayGrade() + " mit Stufe 1A und 1B";
        } else {
            tableName = "Entgelttabelle " + determinePayGrade() + " mit Stufe 1";
        }
    }

    /**
     * getter Method for title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Depending on the performed action e cancels or saves and exits the insertPayRateTableView.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertPayRateTableView.getCancelButton()) {
            frameController.getTabs().removeTabNewWindow(insertPayRateTableView);
        } else if (e.getSource() == insertPayRateTableView.getSaveAndExit()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String tableName = insertPayRateTableView.getTfNameOfTable().getText() + "_" + insertPayRateTableView.getDatePicker().getDate().format(dateTimeFormatter);
            String paygrade = determinePayGrade();
            insertValuesInDatabase(tableName, paygrade, prepareTableForDatabaseInsertion());
            payRateController.updateview();
            frameController.getUpdater().salaryUpDate();
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

    /**
     * Sets selection interval depending on row and column of the mouse event.
     *
     * @param e the Mous Event to be processed
     */
    protected void highlightRow(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        Point point = e.getPoint();
        int row = table.rowAtPoint(point);
        int col = table.columnAtPoint(point);

        table.setRowSelectionInterval(row, row);
        table.setColumnSelectionInterval(col, col);
    }

    /**
     * Implements the JPopupMenu for the right click.
     *
     * @param mouseEvent the mouse event to be processed
     */
    private void showPopUp(MouseEvent mouseEvent) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem pasteRow = new JMenuItem();
        JMenuItem pasteTable = new JMenuItem();
        pasteTable.setText("Tabelle einfügen");
        pasteRow.setText("Zeile einfügen");
        pasteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String clipboardCopy;
                try {
                    clipboardCopy = (String) clipboard.getData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException | IOException ex) {
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
                    }
                    if (row == 10 && columnCounter % 2 == 0 && columnCounter != 0) {
                        columnCounter++;
                        i--;
                        continue;
                    } else
                        insertPayRateTableView.getTable().setValueAt(resultString[i], row, columnCounter);
                    columnCounter++;
                }
            }
        });
        pasteTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String clipboardCopy;
                try {
                    clipboardCopy = (String) clipboard.getData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                String[] tableValues = model.pasteWholeTable(clipboardCopy);
                for (int row = 0; row < tableValues.length; row++) {
                    String[] resultString = model.prepareInsertionString(tableValues[row]);
                    int index = 0;
                    for (int column = 0; column < insertPayRateTableView.getTable().getColumnCount(); column++) {
                        if ((row == 0 || row == 11 || row == 12 || row == 13 || row == 14) && column == 0) {
                            continue;
                        }
                        if ((row == 8 || row == 9) && column % 2 == 1) {
                            continue;
                        }
                        if (row == 10 && column % 2 == 0 && column != 0) {
                            continue;
                        }
                        insertPayRateTableView.getTable().setValueAt(resultString[index], row, column);
                        index++;

                    }
                }
            }
        });
        menu.add(pasteRow);
        menu.add(pasteTable);
        menu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
    }

    /**
     * Prepares the table for the database insertion.
     *
     * @return BigDecimal[][] with the values of the table
     */
    private BigDecimal[][] prepareTableForDatabaseInsertion() {
        BigDecimal[][] result = new BigDecimal[insertPayRateTableView.getTable().getRowCount()][insertPayRateTableView.getTable().getRowCount()];
        for (int row = 0; row < insertPayRateTableView.getTable().getRowCount(); row++) {
            for (int column = 0; column < insertPayRateTableView.getTable().getColumnCount(); column++) {
                if (insertPayRateTableView.getTable().getValueAt(row, column) == null || insertPayRateTableView.getTable().getValueAt(row, column).equals("")) {
                    result[row][column] = new BigDecimal(0);
                } else {
                    result[row][column] = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency((String) insertPayRateTableView.getTable().getValueAt(row, column));
                }
            }
        }
        return result;
    }

    /**
     * inserts the values into the database.
     *
     * @param name     String name of salary table
     * @param paygrade String paygrade of salary table
     * @param values   BigDecimal[][] values of salary table
     */
    private void insertValuesInDatabase(String name, String paygrade, BigDecimal[][] values) {
        payRateTablesDataInserter.insertNewSalaryTable(name, paygrade, values);
    }

    /**
     * Determines the paygrade of the salary table.
     *
     * @return paygrade as String
     */
    private String determinePayGrade() {
        String result;
        if (title.contains("E13")) {
            result = "E13";
        } else {
            result = "E14";
        }
        return result;
    }
}
