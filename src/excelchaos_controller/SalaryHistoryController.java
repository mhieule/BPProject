package excelchaos_controller;

import excelchaos_model.database.*;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.SalaryHistoryView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*public class SalaryHistoryController implements ItemListener {
    private ManualSalaryEntryManager manualSalaryEntryManager = ManualSalaryEntryManager.getInstance();
    private ContractDataManager contractDataManager = ContractDataManager.getInstance();
    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();
    private SalaryHistoryView salaryHistoryView;
    private ToolbarSalaryHistoryController toolbarSalaryHistory;
    private MainFrameController frameController;

    private JTable jTable;

    String columns[] = {"Jahr", "Monat", "Gehalt", "Anmerkung"};
    private String title = "Gehaltshistorie";

    public SalaryHistoryController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        salaryHistoryView = new SalaryHistoryView();
        toolbarSalaryHistory = new ToolbarSalaryHistoryController(frameController);
        toolbarSalaryHistory.getToolbar().setItemListener(this);
        salaryHistoryView.init();
        salaryHistoryView.add(toolbarSalaryHistory.getToolbar(), BorderLayout.NORTH);
    }

    public void showSalaryHistoryView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, salaryHistoryView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    public LocalDate[] getMonths(Employee employee) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(contractDataManager.getContract(employee.getId()).getStart_date().toString(), formatter).minusMonths(0).withDayOfMonth(1);
        LocalDate currentDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        int numberOfMonths = Period.between(startDate, currentDate).getYears() * 12 + Period.between(startDate, currentDate).getMonths() + 1;
        LocalDate[] months = new LocalDate[numberOfMonths];
        for (int i = 0; i < numberOfMonths; i++) {
            months[i] = currentDate;
            currentDate = currentDate.minusMonths(1);
        }
        return months;
    }

    public String[][] getDataFromDB(Employee temporaryEmployee) {
        LocalDate[] months = getMonths(temporaryEmployee);
        StringAndBigDecimalFormatter transformer = new StringAndBigDecimalFormatter();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        int id = temporaryEmployee.getId();
        int rowCount = months.length;
        String resultData[][] = new String[rowCount][];


        String year = months[rowCount - 1].format(DateTimeFormatter.ofPattern("yyyy"));
        String month = months[rowCount - 1].format(DateTimeFormatter.ofPattern("MMMM"));
        String salary = Double.toString(contractDataManager.getContract(id).getRegular_cost());
        String comment = null;
        String[] values = {year, month, salary, comment};
        resultData[rowCount - 1] = values;

        List<ManualSalaryEntry> manualSalaryEntryList = manualSalaryEntryManager.getManualSalaryEntry(id);
        boolean found;

        for (int i = rowCount - 2; i > -1; i--) {
            LocalDate currDate = months[i];
            found = false;
            for (ManualSalaryEntry entry : manualSalaryEntryList) {
                LocalDate changeDate = LocalDate.parse(dateFormat.format(entry.getStart_date()), formatter);
                if (currDate.getYear() == changeDate.getYear() && currDate.getMonth() == changeDate.getMonth()) {
                    year = months[i].format(DateTimeFormatter.ofPattern("yyyy"));
                    month = months[i].format(DateTimeFormatter.ofPattern("MMMM"));
                    salary = transformer.formatDoubleToString(entry.getNew_salary(), 1);
                    comment = entry.getComment();
                    values = new String[]{year, month, salary, comment};
                    resultData[i] = values;
                    found = true;
                }
            }
            if (!found) {
                year = months[i].format(DateTimeFormatter.ofPattern("yyyy"));
                month = months[i].format(DateTimeFormatter.ofPattern("MMMM"));
                salary = resultData[i + 1][2];
                comment = null;
                values = new String[]{year, month, salary, comment};
                resultData[i] = values;
            }
        }
        return resultData;
    }

    private void createTableWithData(String[][] data) {
        salaryHistoryView.createTable(data, columns);
    }

    private void setTableData(String[][] data) {
        jTable = new JTable(data, columns);
        salaryHistoryView.getTable().setModel(jTable.getModel());
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (!((String) e.getItem()).equals("Keine Auswahl")) {
            Employee temporaryEmployee = employeeDataManager.getEmployeeByName((String) e.getItem());
            String[][] data = getDataFromDB(temporaryEmployee);
            if (salaryHistoryView.getTable() == null) {
                createTableWithData(data);
                frameController.getTabs().setLabel(title + " " + temporaryEmployee.getSurname() + " " + temporaryEmployee.getName());
            } else {
                setTableData(data);
                frameController.getTabs().setLabel(title + " " + temporaryEmployee.getSurname() + " " + temporaryEmployee.getName());
            }
        } else {
            if (salaryHistoryView.getTable() != null) {
                salaryHistoryView.getTable().setModel(new DefaultTableModel(null, columns));
                frameController.getTabs().setLabel(title);
            }

        }
    }
}*/
