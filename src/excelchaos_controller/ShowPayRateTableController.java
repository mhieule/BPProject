package excelchaos_controller;

import excelchaos_model.database.SalaryTable;
import excelchaos_model.datamodel.payratetablesdataoperations.PayRateTablesDataAccess;
import excelchaos_model.datamodel.payratetablesdataoperations.PayRateTablesDataInserter;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_model.utility.PayRateTableNameStringEditor;
import excelchaos_view.ShowPayRateTableView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowPayRateTableController implements ActionListener {

    private PayRateTablesDataAccess payRateTablesDataAccess;
    private PayRateTablesDataInserter payRateTablesDataInserter;
    private ShowPayRateTableView showPayRateTableView;
    private MainFrameController mainFrameController;
    private PayRateTablesController payRateTablesController;
    private String tableTitle, paygrade;

    private final String[] columns13WithAAndB = {
            "%-Satz", "E13 St. 1A VBL-befreit", "E13 St. 1A VBL-pflichtig", "E13 St. 1B VBL-befreit", "E13 St. 1B VBL-pflichtig", "E13 St. 2 VBL-befreit", "E13 St. 2 VBL-pflichtig", "E13 St. 3 VBL-befreit", "E13 St. 3 VBL-pflichtig",
            "E13 St. 4 VBL-befreit", "E13 St. 4 VBL-pflichtig", "E13 St. 5 VBL-befreit", "E13 St. 5 VBL-pflichtig", "E13 St. 6 VBL-befreit", "E13 St. 6 VBL-pflichtig"
    };
    private final String[] columns13WithoutAAndB = {
            "%-Satz", "E13 St. 1 VBL-befreit", "E13 St. 1 VBL-pflichtig", "E13 St. 2 VBL-befreit", "E13 St. 2 VBL-pflichtig", "E13 St. 3 VBL-befreit", "E13 St. 3 VBL-pflichtig",
            "E13 St. 4 VBL-befreit", "E13 St. 4 VBL-pflichtig", "E13 St. 5 VBL-befreit", "E13 St. 5 VBL-pflichtig", "E13 St. 6 VBL-befreit", "E13 St. 6 VBL-pflichtig"
    };
    private final String[] columns14WithAAndB = {
            "%-Satz", "E14 St. 1A VBL-befreit", "E14 St. 1A VBL-pflichtig", "E14 St. 1B VBL-befreit", "E14 St. 1B VBL-pflichtig", "E14 St. 2 VBL-befreit", "E14 St. 2 VBL-pflichtig", "E14 St. 3 VBL-befreit", "E14 St. 3 VBL-pflichtig",
            "E14 St. 4 VBL-befreit", "E14 St. 4 VBL-pflichtig", "E14 St. 5 VBL-befreit", "E14 St. 5 VBL-pflichtig", "E14 St. 6 VBL-befreit", "E14 St. 6 VBL-pflichtig"
    };
    private final String[] columns14WithoutAAndB = {
            "%-Satz", "E14 St. 1 VBL-befreit", "E14 St. 1 VBL-pflichtig", "E14 St. 2 VBL-befreit", "E14 St. 2 VBL-pflichtig", "E14 St. 3 VBL-befreit", "E14 St. 3 VBL-pflichtig",
            "E14 St. 4 VBL-befreit", "E14 St. 4 VBL-pflichtig", "E14 St. 5 VBL-befreit", "E14 St. 5 VBL-pflichtig", "E14 St. 6 VBL-befreit", "E14 St. 6 VBL-pflichtig"
    };

    /**
     * Constructor for ShowPayRateTableController
     * @param mainFrameController mainFrameController
     * @param tableName name of the table
     * @param actualPaygrade paygrade of the table
     * @param payRateTablesController payRateTablesController
     */
    public ShowPayRateTableController(MainFrameController mainFrameController, String tableName, String actualPaygrade, PayRateTablesController payRateTablesController) {
        this.mainFrameController = mainFrameController;
        this.payRateTablesController = payRateTablesController;
        payRateTablesDataAccess = new PayRateTablesDataAccess();
        payRateTablesDataInserter = new PayRateTablesDataInserter();
        showPayRateTableView = new ShowPayRateTableView();
        tableTitle = PayRateTableNameStringEditor.revertToCorrectTableName(tableName);
        paygrade = actualPaygrade;
        showPayRateTableView.init(determineTableColumns());
        showPayRateTableView.setActionListener(this);
        showPayRateTableView(this.mainFrameController);
    }

    /**
     * Adds showPayRateTableView to mainFrameController
     * @param mainFrameController mainFrameController
     */
    public void showPayRateTableView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(tableTitle) == -1) {
            mainFrameController.addTab(tableTitle, showPayRateTableView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(tableTitle));
        }
    }

    /**
     * returns column names for the table
     * @return String[] with the column names
     */
    private String[] determineTableColumns() {
        if (paygrade.equals("E13")) {
            if (hasAAndB()) {
                String[] result = columns13WithAAndB;
                return result;
            } else {
                String[] result = columns13WithoutAAndB;
                return result;
            }
        } else {
            if (hasAAndB()) {
                String[] result = columns14WithAAndB;
                return result;
            } else {
                String[] result = columns14WithoutAAndB;
                return result;
            }
        }
    }

    /**
     * inserts values in the table
     */
    public void insertValuesInTable() {
        List<SalaryTable> salaryTables = payRateTablesDataAccess.getSalaryTable(tableTitle);
        showPayRateTableView.insertPayRateValuesInTable(salaryTables);
    }

    /**
     * prepares the values of the table to be inserted in the database
     * @return BigDecimal[][] with the values of the table to be inserted in the database
     */
    private BigDecimal[][] prepareDatabaseInsertion() {
        BigDecimal[][] values = new BigDecimal[showPayRateTableView.getShowPayRatesTable().getRowCount()][showPayRateTableView.getShowPayRatesTable().getColumnCount()];
        for (int row = 0; row < showPayRateTableView.getShowPayRatesTable().getRowCount(); row++) {
            for (int column = 0; column < showPayRateTableView.getShowPayRatesTable().getColumnCount(); column++) {
                if (showPayRateTableView.getShowPayRatesTable().getValueAt(row, column) == null) {
                    values[row][column] = new BigDecimal(0);
                } else {
                    values[row][column] = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency((String) showPayRateTableView.getShowPayRatesTable().getValueAt(row, column));
                }
            }
        }
        return values;
    }

    /**
     * saves the edited values in the database
     */
    private void saveEditedValues() {
        BigDecimal[][] values = prepareDatabaseInsertion();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String tableName = showPayRateTableView.getTfNameOfTable().getText() + "_" + showPayRateTableView.getDatePicker().getDate().format(dateTimeFormatter);
        int border = showPayRateTableView.getShowPayRatesTable().getColumnCount() - 1;
        payRateTablesDataInserter.updateSalaryTable(values, tableTitle, tableName, border);
    }

    /**
     * checks if the table contains A and B
     * @return
     */
    private boolean hasAAndB() {
        if (tableTitle.contains("1A") || tableTitle.contains("1B")) {
            return true;
        } else return false;
    }

    /**
     * Depending on the source of the event, the corresponding method is called
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showPayRateTableView.getCancelButton()) {
            mainFrameController.getTabs().removeTabNewWindow(showPayRateTableView);
        } else if (e.getSource() == showPayRateTableView.getSaveAndExit()) {
            saveEditedValues();
            mainFrameController.getTabs().removeTabNewWindow(showPayRateTableView);
            payRateTablesController.updateview();
            mainFrameController.getUpdater().salaryUpDate();
        }

    }
}
