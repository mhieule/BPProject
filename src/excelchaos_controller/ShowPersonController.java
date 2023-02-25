package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.ShowPersonView;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShowPersonController implements TableModelListener {
    private ShowPersonView showPersonView;
    private MainFrameController frameController;
    private ToolbarShowPersonController toolbarShowPerson;

    private StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
    private String title = "Personalstammdaten";

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();
    private ProjectParticipationManager participationManager = new ProjectParticipationManager();

    private ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();

    private SalaryIncreaseHistoryManager increaseHistoryManager = new SalaryIncreaseHistoryManager();

    private SearchAndFilterModel searchAndFilterModel;

    private String columns[] = {"ID","Name", "Vorname","Straße","Haunsummer","Adresszusatz", "Postleitzahl","Stadt",
            "Geburtsdatum","E-Mail Privat", "Telefon Privat", "Telefon TUDA",  "Staatsangehörigkeit 1", "Staatsangehörigkeit 2", "Visum Gültigkeit", "Personalnummer", "Transpondernummer", "Büronummer", "TU-ID",
            "Anstellungsart","Beschäftigungsbeginn","Beschäftigungsende", "Beschäftigungsumfgang","Gehaltsklasse","Gehaltsstufe","VBL-Status","SHK Stundensatz", "Gehalt Eingeplant bis"
    };


    public ShowPersonController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        showPersonView = new ShowPersonView();
        toolbarShowPerson = new ToolbarShowPersonController(frameController,this);
        showPersonView.init();
        createTableWithData(getEmployeeDataFromDataBase());
        showPersonView.add(toolbarShowPerson.getToolbar(),BorderLayout.NORTH);
        SearchAndFilterModel.setUpSearchAndFilterModel(showPersonView.getTable(),toolbarShowPerson.getToolbar());
    }

    public ShowPersonView getPersonView() {
        return showPersonView;
    }

    public void showPersonView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title,showPersonView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    public String[][] getEmployeeDataFromDataBase(){
        int lines  = employeeDataManager.getRowCount();
        String resultData[][] = new String[lines][];
        int currentIndex = 0;
        List<Employee> employees = employeeDataManager.getAllEmployees();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Employee employee : employees){
            Contract contract = contractDataManager.getContract(employee.getId());
            String id = String.valueOf(employee.getId());
            String name = employee.getName();
            String surname = employee.getSurname();
            String street = employee.getStreet();
            String houseNumber = employee.getHouse_number();
            String additionalAddress = employee.getAdditional_address();
            String zipCode = employee.getZip_code();
            String city = employee.getCity();
            String dateOfBirth = dateFormat.format(employee.getDate_of_birth());
            String emailPrivate = employee.getEmail_private();
            String phonePrivate = employee.getPhone_private();
            String phoneTuda = employee.getPhone_tuda();
            String citizenship1 = employee.getCitizenship_1();
            String citizenship2 = employee.getCitizenship_2();
            String visaExpiration;
            if(employee.getVisa_expiration() != null){
                visaExpiration = dateFormat.format(employee.getVisa_expiration());
            } else {
                visaExpiration = null;
            }
            String employeeNumber = employee.getEmployee_number();
            String transponderNumber = employee.getTransponder_number();
            String officeNumber = employee.getOffice_number();
            String tuId = employee.getTu_id();
            String status = employee.getStatus();
            Date startDate = contract.getStart_date();
            String startDateString = dateFormat.format(startDate);
            Date endDate = contract.getEnd_date();
            String endDateString = dateFormat.format(endDate);
            String extend = transformer.formatPercentageToStringForScope(contract.getScope()); //arbeitsumfang
            String payGrade = contract.getPaygrade();
            String payLevel = contract.getPaylevel();
            String vblStatus;
            if(contract.getVbl_status()){
                vblStatus = "Pflichtig";
            } else vblStatus = "Befreit";
            String shkHourlyRate = contract.getShk_hourly_rate();
            Date salaryPlannedUntil = employee.getSalary_planned_until();
            String salaryPlannedUntilString = dateFormat.format(salaryPlannedUntil);

            String[] values = {id,name, surname, street, houseNumber, additionalAddress, zipCode, city, dateOfBirth,  emailPrivate, phonePrivate, phoneTuda,
                    citizenship1, citizenship2, visaExpiration, employeeNumber, transponderNumber, officeNumber, tuId, status, startDateString, endDateString, extend, payGrade, payLevel, vblStatus, shkHourlyRate, salaryPlannedUntilString};
            resultData[currentIndex] = values;
            currentIndex++;
        }
        return resultData;
    }

    private void createTableWithData(String[][] tableData){
        showPersonView.createEmployeeTable(tableData,columns);
        showPersonView.getTable().getModel().addTableModelListener(this);
    }

    public void updateData(String [][] tableData){
        CustomTableModel customTableModel = new CustomTableModel(tableData,columns);
        showPersonView.getTable().setModel(customTableModel);
        showPersonView.getTable().getColumnModel().getColumn(1).setMinWidth(0);
        showPersonView.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
        showPersonView.getTable().getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(showPersonView.getTable());
        tca.adjustColumns();
        customTableModel.addTableModelListener(this);
        SearchAndFilterModel.setUpSearchAndFilterModel(showPersonView.getTable(),toolbarShowPerson.getToolbar());
        toolbarShowPerson.getToolbar().getEditPerson().setEnabled(false);
        toolbarShowPerson.getToolbar().getDeletePerson().setEnabled(false);
    }

    public void deleteData(int[] employeeIds){
        for (int i = 0; i < employeeIds.length; i++) {
            employeeDataManager.removeEmployee(employeeIds[i]);
            contractDataManager.removeContract(employeeIds[i]);
            manualSalaryEntryManager.removeAllManualSalaryEntryForEmployee(employeeIds[i]);
            increaseHistoryManager.removeAllSalaryIncreaseHistoryForEmployee(employeeIds[i]);
            participationManager.removeProjectParticipationBasedOnEmployeeId(employeeIds[i]);
        }
        updateData(getEmployeeDataFromDataBase());
    }


    public ToolbarShowPersonController getToolbarShowPerson() {
        return toolbarShowPerson;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int numberOfSelectedRows = showPersonView.getTable().getNumberOfSelectedRows();
        if(e.getColumn() == 0){
            if(numberOfSelectedRows == 0){
                toolbarShowPerson.getToolbar().getEditPerson().setEnabled(false);
                toolbarShowPerson.getToolbar().getDeletePerson().setEnabled(false);
            } else if (numberOfSelectedRows == 1) {
                toolbarShowPerson.getToolbar().getEditPerson().setEnabled(true);
                toolbarShowPerson.getToolbar().getDeletePerson().setEnabled(true);
            } else {
                toolbarShowPerson.getToolbar().getEditPerson().setEnabled(false);
                toolbarShowPerson.getToolbar().getDeletePerson().setEnabled(true);
            }

        }
    }
}
