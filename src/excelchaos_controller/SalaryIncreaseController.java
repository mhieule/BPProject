package excelchaos_controller;

import excelchaos_model.customcomponentmodels.CustomTableModel;
import excelchaos_model.database.Employee;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.database.SalaryIncreaseHistory;
import excelchaos_model.database.SalaryIncreaseHistoryManager;
import excelchaos_model.utility.SearchAndFilterModel;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.SalaryIncreaseView;

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

public class SalaryIncreaseController implements ItemListener, TableModelListener {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();

    private SalaryIncreaseView salaryIncreaseView;

    private ToolbarSalaryIncreaseController toolbarSalaryIncreaseController;

    private MainFrameController frameController;

    private CustomTableModel customTableModel;

    private String columns[] = {"ID", "Gültig ab", "Neues Gehalt", "Absolute Erhöhung in €", "Relative Erhöhung in %", "Kommentar", "Sonderzahlung"};
    private String nullColumns[] = {"Gültig ab", "Neues Gehalt", "Absolute Erhöhung in €", "Relative Erhöhung in %", "Kommentar", "Sonderzahlung"};
    private String title = "Gehaltserhöhung";

    public SalaryIncreaseController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        salaryIncreaseView = new SalaryIncreaseView();
        toolbarSalaryIncreaseController = new ToolbarSalaryIncreaseController(frameController, this);
        toolbarSalaryIncreaseController.getToolbar().setItemListener(this);
        salaryIncreaseView.init();
        salaryIncreaseView.add(toolbarSalaryIncreaseController.getToolbar(), BorderLayout.NORTH);
    }

    public void showManualSalaryEntryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, salaryIncreaseView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    private void createTableWithData(String[][] data) {
        salaryIncreaseView.createTable(data, columns);
        salaryIncreaseView.getTable().getModel().addTableModelListener(this);
        salaryIncreaseView.getTable().getColumnModel().getColumn(1).setMinWidth(0);
        salaryIncreaseView.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
        salaryIncreaseView.getTable().getColumnModel().getColumn(1).setWidth(0);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryIncreaseView.getTable(), toolbarSalaryIncreaseController.getToolbar());
    }

    public void setTableData(String[][] data) {
        customTableModel = new CustomTableModel(data, columns);
        salaryIncreaseView.getTable().setModel(customTableModel);
        salaryIncreaseView.getTable().getModel().addTableModelListener(this);
        salaryIncreaseView.getTable().getColumnModel().getColumn(1).setMinWidth(0);
        salaryIncreaseView.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
        salaryIncreaseView.getTable().getColumnModel().getColumn(1).setWidth(0);
        //  toolbarSalaryIncreaseController.getToolbar().getEditSalaryEntry().setEnabled(false);
        SearchAndFilterModel.setUpSearchAndFilterModel(salaryIncreaseView.getTable(), toolbarSalaryIncreaseController.getToolbar());
        toolbarSalaryIncreaseController.getToolbar().getDeleteSalaryEntry().setEnabled(false);
    }

    public void update() {
        salaryIncreaseView.remove(toolbarSalaryIncreaseController.getToolbar());
        salaryIncreaseView.revalidate();
        salaryIncreaseView.repaint();
        toolbarSalaryIncreaseController.update();
        toolbarSalaryIncreaseController.getToolbar().setItemListener(this);
        salaryIncreaseView.add(toolbarSalaryIncreaseController.getToolbar(), BorderLayout.NORTH);
    }

    public String[][] getDataFromDB(Employee temporaryEmployee) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        int id = temporaryEmployee.getId();
        int rowCount = salaryIncreaseHistoryManager.getRowCount(id);
        String resultData[][] = new String[rowCount][];
        int currentIndex = 0;

        List<SalaryIncreaseHistory> salaryIncreaseHistory = salaryIncreaseHistoryManager.getSalaryIncreaseHistory(id);
        for (SalaryIncreaseHistory entry : salaryIncreaseHistory) {
            String salary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(entry.getNew_salary());
            String usageDate = dateFormat.format(entry.getStart_date());
            String absoluteValue = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(entry.getAbsoluteIncreaseValue());
            String relativeValue = StringAndBigDecimalFormatter.formatPercentageToStringForScope(entry.getPercentIncreaseValue());
            String comment = entry.getComment();
            String specialPayment;
            if (entry.getIs_additional_payment()) {
                specialPayment = "Ja";
            } else specialPayment = "Nein";
            String[] values = {String.valueOf(id), usageDate, salary, comment, specialPayment};
            resultData[currentIndex] = values;
            currentIndex++;

        }

        return resultData;


    }

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
            salaryIncreaseHistoryManager.removeSalaryIncreaseHistory(id, date);

        }
        setTableData(getDataFromDB(employeeDataManager.getEmployee(id)));
        frameController.getUpdater().salaryUpDate();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getItem().equals("Keine Auswahl")) {
                toolbarSalaryIncreaseController.getToolbar().getDoSalaryIncrease().setEnabled(false);
                //  toolbarSalaryIncreaseController.getToolbar().getEditSalaryEntry().setEnabled(false);
                toolbarSalaryIncreaseController.getToolbar().getDeleteSalaryEntry().setEnabled(false);
                toolbarSalaryIncreaseController.getToolbar().getExportToCSV().setEnabled(false);
                if (salaryIncreaseView.getTable() != null) {
                    salaryIncreaseView.getTable().setModel(new DefaultTableModel(null, nullColumns));
                }
            } else {
                toolbarSalaryIncreaseController.getToolbar().getDoSalaryIncrease().setEnabled(true);
                toolbarSalaryIncreaseController.getToolbar().getExportToCSV().setEnabled(true);
                if (!((String) e.getItem()).equals("Keine Auswahl")) {
                    Employee temporaryEmployee = employeeDataManager.getEmployeeByName((String) e.getItem());
                    String[][] data = getDataFromDB(temporaryEmployee);
                    if (salaryIncreaseView.getTable() == null) {
                        createTableWithData(data);
                    } else {
                        setTableData(data);
                    }
                }
            }

        }


    }

    public SalaryIncreaseView getSalaryIncreaseView() {
        return salaryIncreaseView;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int numberOfSelectedRows = salaryIncreaseView.getTable().getNumberOfSelectedRows();
        if (e.getColumn() == 0) {
            if (numberOfSelectedRows == 0) {
                // toolbarSalaryIncreaseController.getToolbar().getEditSalaryEntry().setEnabled(false);
                toolbarSalaryIncreaseController.getToolbar().getDeleteSalaryEntry().setEnabled(false);
            } else if (numberOfSelectedRows == 1) {
                //  toolbarSalaryIncreaseController.getToolbar().getEditSalaryEntry().setEnabled(true);
                toolbarSalaryIncreaseController.getToolbar().getDeleteSalaryEntry().setEnabled(true);
            } else {
                //  toolbarSalaryIncreaseController.getToolbar().getEditSalaryEntry().setEnabled(false);
                toolbarSalaryIncreaseController.getToolbar().getDeleteSalaryEntry().setEnabled(true);
            }
        }
    }
}
