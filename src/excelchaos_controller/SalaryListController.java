package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.SalaryListView;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class SalaryListController implements TableModelListener {
    private SalaryListView salaryListView;
    private ToolbarSalaryListController toolbarSalaryList;
    private MainFrameController frameController;

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();


    private String title = "Gehaltsprojektion";

    private String columns[] = {
            "ID","Name", "Vorname", "Geburtsdatum", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung"
    };

    private String next2PayLevelIncreaseColumns[] = {
            "ID","Name", "Vorname", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung", "Höherstufung 1 ab", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung",
            "Höherstufung 2 ab", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung"
    };

    private String salaryLevelIncreaseBasedOnChosenDateColumns[] = {
            "ID","Name", "Vorname", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung", "Gewähltes Datum", "Stufe zum gewählten Zeitpunkt",
            "Gehaltskosten zum gewählten Zeitpunkt", "Jahressonderzahlung"
    };

    private StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();

    private SearchAndFilterModel searchAndFilterModel;
    public SalaryListController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        salaryListView = new SalaryListView();
        salaryListView.init();
        createTableWithData(getSalaryDataFromDataBase());
        toolbarSalaryList = new ToolbarSalaryListController(frameController, salaryListView,this);
        salaryListView.add(toolbarSalaryList.getToolbar(),BorderLayout.NORTH);
        searchAndFilterModel = new SearchAndFilterModel(salaryListView.getTable(),toolbarSalaryList.getToolbar().getSearchField());
    }

    public void showSalaryView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1){
            mainFrameController.addTab(title,salaryListView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    public String[][] getSalaryDataFromDataBase(){
        int lines  = contractDataManager.getRowCount();
        String[][] resultData = new String[lines][];
        int currentIndex = 0;
        List<Contract> contracts = contractDataManager.getAllContracts();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Contract contract : contracts){
            Employee employee = employeeDataManager.getEmployee(contract.getId());
            String id = String.valueOf(employee.getId());
            String name = employee.getName();
            String surname = employee.getSurname();
            String dateOfBirth = dateFormat.format(employee.getDate_of_birth());
            String group = contract.getPaygrade();
            String stufe = contract.getPaylevel();
            String gehalt = transformer.formatDoubleToString(contract.getRegular_cost(),1);
            String sonderzahlungen = transformer.formatDoubleToString(contract.getBonus_cost(),1);

            String[] values = {id,surname, name, dateOfBirth, group, stufe, gehalt, sonderzahlungen};
            resultData[currentIndex] = values;
            currentIndex++;
        }
        return resultData;
    }


    private void createTableWithData(String[][] tableData){
        salaryListView.createSalaryTable(tableData,columns);
        salaryListView.getTable().getModel().addTableModelListener(this);
    }


    public void updateData(String [][] tableData){
        CustomTableModel customTableModel = new CustomTableModel(tableData,columns);
        salaryListView.getTable().setModel(customTableModel);
        salaryListView.getTable().getColumnModel().getColumn(1).setMinWidth(0);
        salaryListView.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
        salaryListView.getTable().getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(salaryListView.getTable());
        tca.adjustColumns();
        customTableModel.addTableModelListener(this);
        searchAndFilterModel = new SearchAndFilterModel(salaryListView.getTable(),toolbarSalaryList.getToolbar().getSearchField());
        toolbarSalaryList.getToolbar().getEditEntry().setEnabled(false);

    }

    public void buildFuturePayLevelTable(String[][] tableData){
        CustomTableModel customTableModel = new CustomTableModel(tableData,next2PayLevelIncreaseColumns);
        salaryListView.getTable().setModel(customTableModel);
        salaryListView.getTable().getColumnModel().getColumn(1).setMinWidth(0);
        salaryListView.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
        salaryListView.getTable().getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(salaryListView.getTable());
        tca.adjustColumns();
        customTableModel.addTableModelListener(this);
        searchAndFilterModel = new SearchAndFilterModel(salaryListView.getTable(),toolbarSalaryList.getToolbar().getSearchField());
        toolbarSalaryList.getToolbar().getEditEntry().setEnabled(false);
    }

    public void buildPayLevelTableBasedOnChosenDate(String[][] tableData){
        CustomTableModel customTableModel = new CustomTableModel(tableData,salaryLevelIncreaseBasedOnChosenDateColumns);
        salaryListView.getTable().setModel(customTableModel);
        salaryListView.getTable().getColumnModel().getColumn(1).setMinWidth(0);
        salaryListView.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
        salaryListView.getTable().getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(salaryListView.getTable());
        tca.adjustColumns();
        customTableModel.addTableModelListener(this);
        searchAndFilterModel = new SearchAndFilterModel(salaryListView.getTable(),toolbarSalaryList.getToolbar().getSearchField());
        toolbarSalaryList.getToolbar().getEditEntry().setEnabled(false);
    }

    public ToolbarSalaryListController getToolbarSalary() {
        return toolbarSalaryList;
    }

    public SalaryListView getSalaryListView(){return salaryListView;};

    @Override
    public void tableChanged(TableModelEvent e) {
        int numberOfSelectedRows = salaryListView.getTable().getNumberOfSelectedRows();
        if(e.getColumn() == 0){
            if(numberOfSelectedRows == 0){
                toolbarSalaryList.getToolbar().getEditEntry().setEnabled(false);
            } else if (numberOfSelectedRows == 1) {
                toolbarSalaryList.getToolbar().getEditEntry().setEnabled(true);
            } else {
                toolbarSalaryList.getToolbar().getEditEntry().setEnabled(false);
            }
        }
    }
}
