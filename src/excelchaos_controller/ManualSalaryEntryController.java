package excelchaos_controller;

import excelchaos_model.customcomponentmodels.CustomTableModel;
import excelchaos_model.database.Employee;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.database.ManualSalaryEntry;
import excelchaos_model.database.ManualSalaryEntryManager;
import excelchaos_model.utility.SearchAndFilterModel;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.ManualSalaryEntryView;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ManualSalaryEntryController implements ItemListener, TableModelListener {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();
    private ManualSalaryEntryView salaryEntryView;
    private ToolbarManualSalaryEntryController toolbarManualSalaryEntry;
    private MainFrameController frameController;

    private CustomTableModel customTableModel;

    private String columns[] = {"ID", "Gültig ab", "Gehalt", "Kommentar"};
    private String nullColumns[] = {"Gültig ab", "Gehalt", "Kommentar"};
    private String title = "Gehaltsanpassung SAP";

    /**
     * Constructor for the ManualSalaryController
     * @param mainFrameController the main frame controller
     */

    public ManualSalaryEntryController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        salaryEntryView = new ManualSalaryEntryView();
        toolbarManualSalaryEntry = new ToolbarManualSalaryEntryController(frameController, this);
        toolbarManualSalaryEntry.getToolbar().setItemListener(this);
        salaryEntryView.init();
        salaryEntryView.add(toolbarManualSalaryEntry.getToolbar(), BorderLayout.NORTH);
    }

    /**
     * Enables ManualSalaryEntry view in the main frame
     * @param mainFrameController the main frame controller
     */

    public void showManualSalaryEntryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, salaryEntryView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    /**
     * Receives data from database for employee with specified id and returns it's data
     * @param temporaryEmployee dummy employee to get id from
     * @return employee's data
     */

    public String[][] getDataFromDB(Employee temporaryEmployee) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        int id = temporaryEmployee.getId();
        int rowCount = manualSalaryEntryManager.getRowCount(id);
        String resultData[][] = new String[rowCount][];
        int currentIndex = 0;

        List<ManualSalaryEntry> manualSalaryEntryList = manualSalaryEntryManager.getManualSalaryEntry(id);
        for (ManualSalaryEntry entry : manualSalaryEntryList) {
            String salary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(entry.getNew_salary());
            String usageDate = dateFormat.format(entry.getStart_date());
            String comment = entry.getComment();
            String[] values = {String.valueOf(id), usageDate, salary, comment};
            resultData[currentIndex] = values;
            currentIndex++;

        }

        return resultData;


    }

    /**
     * Creates a table with the given data
     * @param data data for the table
     */

    private void createTableWithData(String[][] data) {
        salaryEntryView.createTable(data, columns);
        salaryEntryView.getTable().getModel().addTableModelListener(this);
        salaryEntryView.getTable().getColumnModel().getColumn(1).setMinWidth(0);
        salaryEntryView.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
        salaryEntryView.getTable().getColumnModel().getColumn(1).setWidth(0);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryEntryView.getTable(), toolbarManualSalaryEntry.getToolbar());
    }

    /**
     * Sets table data for costum table model
     * @param data data for the table
     */

    public void setTableData(String[][] data) {
        customTableModel = new CustomTableModel(data, columns);
        salaryEntryView.getTable().setModel(customTableModel);
        salaryEntryView.getTable().getModel().addTableModelListener(this);
        salaryEntryView.getTable().getColumnModel().getColumn(1).setMinWidth(0);
        salaryEntryView.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
        salaryEntryView.getTable().getColumnModel().getColumn(1).setWidth(0);
        toolbarManualSalaryEntry.getToolbar().getEditSalaryEntry().setEnabled(false);
        toolbarManualSalaryEntry.getToolbar().getDeleteSalaryEntry().setEnabled(false);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryEntryView.getTable(), toolbarManualSalaryEntry.getToolbar());

    }

    /**
     * Updates the table
     */

    public void update() {
        salaryEntryView.remove(toolbarManualSalaryEntry.getToolbar());
        salaryEntryView.revalidate();
        salaryEntryView.repaint();
        toolbarManualSalaryEntry.update();
        toolbarManualSalaryEntry.getToolbar().setItemListener(this);
        salaryEntryView.add(toolbarManualSalaryEntry.getToolbar(), BorderLayout.NORTH);
    }

    /**
     * Deletes an entry at specific dates from the table
     * @param dates dates
     */

    public void deleteEntries(String[][] dates) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        int id = 0;
        for (int i = 0; i < dates.length; i++) {
            id = Integer.parseInt(dates[0][0]);
            try {
                date = format.parse(dates[i][1]);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            manualSalaryEntryManager.removeManualSalaryEntry(id, date);

        }
        setTableData(getDataFromDB(employeeDataManager.getEmployee(id)));
        frameController.getUpdater().salaryUpDate();
    }

    /**
     * Checks if item state has changed
     * @param e ItemEvent
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getItem().equals("Keine Auswahl")) {
                toolbarManualSalaryEntry.getToolbar().getAddSalaryEntry().setEnabled(false);
                toolbarManualSalaryEntry.getToolbar().getEditSalaryEntry().setEnabled(false);
                toolbarManualSalaryEntry.getToolbar().getDeleteSalaryEntry().setEnabled(false);
                toolbarManualSalaryEntry.getToolbar().getExportToCSV().setEnabled(false);
                if (salaryEntryView.getTable() != null) {
                    salaryEntryView.getTable().setModel(new DefaultTableModel(null, nullColumns));
                    frameController.getTabs().setLabel(title);
                }
            } else {
                toolbarManualSalaryEntry.getToolbar().getAddSalaryEntry().setEnabled(true);
                toolbarManualSalaryEntry.getToolbar().getExportToCSV().setEnabled(true);
                Employee temporaryEmployee = employeeDataManager.getEmployeeByName((String) e.getItem());
                String[][] data = getDataFromDB(temporaryEmployee);
                if (salaryEntryView.getTable() == null) {
                    createTableWithData(data);
                } else {
                    setTableData(data);
                }

            }

        }


    }

    public ManualSalaryEntryView getSalaryEntryView() {
        return salaryEntryView;
    }

    public ToolbarManualSalaryEntryController getToolbarManualSalaryEntry() {
        return toolbarManualSalaryEntry;
    }

    /**
     * Checks if the table has changed
     * @param e TableModelEvent
     */
    @Override
    public void tableChanged(TableModelEvent e) {
        int numberOfSelectedRows = salaryEntryView.getTable().getNumberOfSelectedRows();
        if (e.getColumn() == 0) {
            if (numberOfSelectedRows == 0) {
                toolbarManualSalaryEntry.getToolbar().getEditSalaryEntry().setEnabled(false);
                toolbarManualSalaryEntry.getToolbar().getDeleteSalaryEntry().setEnabled(false);
            } else if (numberOfSelectedRows == 1) {
                toolbarManualSalaryEntry.getToolbar().getEditSalaryEntry().setEnabled(true);
                toolbarManualSalaryEntry.getToolbar().getDeleteSalaryEntry().setEnabled(true);
            } else {
                toolbarManualSalaryEntry.getToolbar().getEditSalaryEntry().setEnabled(false);
                toolbarManualSalaryEntry.getToolbar().getDeleteSalaryEntry().setEnabled(true);
            }
        }
    }
}
