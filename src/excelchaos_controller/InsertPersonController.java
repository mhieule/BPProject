package excelchaos_controller;

import excelchaos_model.calculations.SalaryTableLookUp;
import excelchaos_model.database.Contract;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.Employee;
import excelchaos_model.inputVerifier.WorkScopeSHKVerifier;
import excelchaos_model.inputVerifier.WorkScopeVerifier;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.InsertPersonView;
import excelchaos_model.database.EmployeeDataManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InsertPersonController implements ActionListener {

    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();

    private ContractDataManager contractDataManager = ContractDataManager.getInstance();
    private InsertPersonView insertPersonView;
    private MainFrameController frameController;

    private String addPersonTab = "Person hinzufügen";

    private SalaryTableLookUp salaryTableLookUp = new SalaryTableLookUp();

    private int currentlyEditedEmployeeID = 0;


    public InsertPersonController(MainFrameController mainFrameController) {
        insertPersonView = new InsertPersonView();
        insertPersonView.init();
        insertPersonView.setActionListener(this);
        frameController = mainFrameController;
    }

    public InsertPersonView getInsertPersonView() {
        return insertPersonView;
    }

    public void showInsertPersonView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(addPersonTab) == -1) {
            mainFrameController.getTabs().addTab(addPersonTab, insertPersonView);
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addPersonTab));
        } else
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addPersonTab));
    }

    private void setNationalityCheckboxVisible() {
        insertPersonView.getNationalitySecond().setVisible(true);
        insertPersonView.getNationalityPickList2().setVisible(true);
    }

    private void setNationalityCheckboxInVisible() {
        insertPersonView.getNationalitySecond().setVisible(false);
        insertPersonView.getNationalityPickList2().setVisible(false);
    }

    private void setVisRequiredCheckboxVisible() {
        insertPersonView.getVisaValidUntil().setVisible(true);
        insertPersonView.getTfVisaValidUntil().setVisible(true);
    }

    private void setVisRequiredCheckboxInVisible() {
        insertPersonView.getVisaValidUntil().setVisible(false);
        insertPersonView.getTfVisaValidUntil().setVisible(false);
    }

    public boolean updateData(int id) {
        Employee employee = employeeDataManager.getEmployee(id);
        Contract contract = contractDataManager.getContract(id);

        String surname = insertPersonView.getTfVorname().getText();
        String name = insertPersonView.getTfName().getText();
        String email_private = insertPersonView.getTfPrivatEmail().getText();
        String phone_private = insertPersonView.getTfPrivateTelefonnummer().getText();
        String citizenship_1 = insertPersonView.getNationalityPickList().getSelectedItem().toString();
        String citizenship_2 = null;
        if (insertPersonView.getNationalityPickList2().getSelectedItem().toString() != null) {
            citizenship_2 = insertPersonView.getNationalityPickList2().getSelectedItem().toString();
        }
        String employeeNumber = insertPersonView.getTfPersonalnummer().getText();
        String tu_id = insertPersonView.getTfTuid().getText();
        boolean visa_required = insertPersonView.getVisaRequiredCheckBox().isSelected();
        String status = insertPersonView.getStatusPicklist().getSelectedItem().toString();
        String transponder_number = insertPersonView.getTfTranspondernummer().getText();
        String office_number = insertPersonView.getTfBueronummer().getText();
        LocalDate localDate = insertPersonView.getTfSalaryPlannedUntil().getDate();
        Date salaryPlannedUntil;
        if (localDate == null) {
            salaryPlannedUntil = null;
        } else {
            salaryPlannedUntil = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        Date visaExpiration = null;
        if (visa_required) {
            LocalDate visaExpirationDate = insertPersonView.getTfVisaValidUntil().getDate();
            visaExpiration = Date.from(visaExpirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        String phone_tuda = insertPersonView.getTfTelefonnummerTUDA().getText();
        LocalDate dateOfBirthDate = insertPersonView.getTfGeburtsdatum().getDate();
        Date dateOfBirth;
        if (dateOfBirthDate == null) {
            dateOfBirth = null;
        } else {
            dateOfBirth = Date.from(dateOfBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        String houseNumber = insertPersonView.getTfHausnummer().getText();
        String street = insertPersonView.getTfStrasse().getText();
        String zip_code = insertPersonView.getTfPLZ().getText();
        String additional_address = insertPersonView.getTfAdresszusatz().getText();
        String city = insertPersonView.getTfStadt().getText();

        String typeOfJob = insertPersonView.getTypeOfJobPicklist().getSelectedItem().toString();
        String hiwiTypeOfPayment = insertPersonView.getHiwiTypeOfPaymentList().getSelectedItem().toString();
        String payGrade = insertPersonView.getPayGroupList().getSelectedItem().toString();
        String payLevel = insertPersonView.getPayLevelList().getSelectedItem().toString();
        boolean vbl = false;
        if (insertPersonView.getVblList().getSelectedItem().toString().equals("Pflichtig")) {
            vbl = true;
        }
        LocalDate workStartDate = insertPersonView.getTfWorkStart().getDate();
        Date workStart;
        if (workStartDate == null) {
            workStart = null;
        } else {
            workStart = Date.from(workStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        LocalDate workEndDate = insertPersonView.getTfWorkEnd().getDate();
        Date workEnd;
        if (workEndDate == null) {
            workEnd = null;
        } else {
            workEnd = Date.from(workEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        BigDecimal scope = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope(insertPersonView.getTfWorkScope().getText());

        if (surname.equals("") || name.equals("") || salaryPlannedUntil == null || workStart == null || workEnd == null || typeOfJob.equals("Nicht ausgewählt")) {
            insertPersonView.markMustBeFilledTextFields();
            return true;
        }
        if (typeOfJob.equals("SHK")) {
            if (hiwiTypeOfPayment.equals("Nicht ausgewählt")) {
                insertPersonView.markMustBeFilledTextFields();
                return true;
            }
        }
        if (typeOfJob.equals("WiMi") || typeOfJob.equals("ATM")) {
            if (payGrade.equals("Nicht ausgewählt") || payLevel.equals("Nicht ausgewählt") || insertPersonView.getVblList().getSelectedItem().toString().equals("Nicht ausgewählt")) {
                insertPersonView.markMustBeFilledTextFields();
                return true;
            }

        }

        employee.setSurname(surname);
        employee.setName(name);
        employee.setEmail_private(email_private);
        employee.setPhone_private(phone_private);
        employee.setCitizenship_1(citizenship_1);
        employee.setCitizenship_2(citizenship_2);
        employee.setEmployee_number(employeeNumber);
        employee.setTu_id(tu_id);
        employee.setVisa_required(visa_required);
        employee.setStatus(status);
        employee.setTransponder_number(transponder_number);
        employee.setOffice_number(office_number);
        employee.setPhone_tuda(phone_tuda);
        employee.setSalary_planned_until(salaryPlannedUntil);
        employee.setVisa_expiration(visaExpiration);
        employee.setDate_of_birth(dateOfBirth);
        employee.setHouse_number(houseNumber);
        employee.setStreet(street);
        employee.setZip_code(zip_code);
        employee.setAdditional_address(additional_address);
        employee.setCity(city);

        if(hiwiTypeOfPayment.equals("Nicht ausgewählt")){
            hiwiTypeOfPayment = "";
        }

        if(payGrade.equals("Nicht ausgewählt")){
            payGrade = "";
        }

        if(payLevel.equals("Nicht ausgewählt")){
            payLevel = "";
        }

        employee.setStatus(typeOfJob);
        contract.setPaygrade(payGrade);
        contract.setPaylevel(payLevel);
        contract.setStart_date(workStart);
        contract.setEnd_date(workEnd);
        contract.setShk_hourly_rate(hiwiTypeOfPayment);
        contract.setScope(scope);
        contract.setVbl_status(vbl); //TODO Unterscheidung bei der Berechnung SHK und Rest und an aktuelles Gehalt anpassen also nicht anhand der PayRateTable berechnen
        BigDecimal[] newCost = salaryTableLookUp.getCurrentPayRateTableEntry(contract);
        contract.setRegular_cost(newCost[0]);
        contract.setBonus_cost(newCost[1].multiply(new BigDecimal(12)));
        employeeDataManager.updateEmployee(employee);
        contractDataManager.updateContract(contract);

        return false;

    }

    public void fillFields(String id) {
        Employee employee = employeeDataManager.getEmployee(Integer.parseInt(id));
        currentlyEditedEmployeeID = employee.getId();
        insertPersonView.getTfVorname().setText(employee.getSurname());
        insertPersonView.getTfName().setText(employee.getName());
        insertPersonView.getTfStrasse().setText(employee.getStreet());
        insertPersonView.getTfPrivatEmail().setText(employee.getEmail_private());
        insertPersonView.getTfPrivateTelefonnummer().setText(employee.getPhone_private());
        insertPersonView.getTfPersonalnummer().setText(employee.getEmployee_number());
        insertPersonView.getTfTuid().setText(employee.getTu_id());
        insertPersonView.getTfTranspondernummer().setText(employee.getTransponder_number());
        insertPersonView.getTfBueronummer().setText(employee.getOffice_number());
        insertPersonView.getTfSalaryPlannedUntil().setDate(employee.getSalary_planned_until().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        insertPersonView.getTfTelefonnummerTUDA().setText(employee.getPhone_tuda());
        insertPersonView.getTfHausnummer().setText(employee.getHouse_number());
        insertPersonView.getTfPLZ().setText(employee.getZip_code());
        insertPersonView.getTfStadt().setText(employee.getCity());
        insertPersonView.getTfHausnummer().setText(employee.getHouse_number());
        insertPersonView.getTfAdresszusatz().setText(employee.getAdditional_address());
        insertPersonView.getNationalityPickList().setSelectedItem(employee.getCitizenship_1());
        if (!(employee.getCitizenship_2().equals("") || employee.getCitizenship_2() == null || employee.getCitizenship_2().equals("Keine"))) {
            insertPersonView.getNationalityCheckBox().setVisible(true);
            insertPersonView.getNationalityCheckBox().setSelected(true);
            insertPersonView.getNationalityPickList2().setSelectedItem(employee.getCitizenship_2());
            insertPersonView.getNationalityPickList2().setVisible(true);
            insertPersonView.getNationalitySecond().setVisible(true);
        }
        Date date = employee.getVisa_expiration();
        if (date != null) {
            insertPersonView.getTfVisaValidUntil().setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            insertPersonView.getVisaRequiredCheckBox().setSelected(true);
            insertPersonView.getVisaRequiredCheckBox().setVisible(true);
            insertPersonView.getTfVisaValidUntil().setVisible(true);
            insertPersonView.getVisaValidUntil().setVisible(true);
        }
        date = employee.getDate_of_birth();
        if (date != null) {
            insertPersonView.getTfGeburtsdatum().setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }

        Contract contract = contractDataManager.getContract(Integer.parseInt(id));
        insertPersonView.getTypeOfJobPicklist().setSelectedItem(employee.getStatus());
        if (employee.getStatus().equals("SHK")) {
            insertPersonView.getHiwiTypeOfPayment().setVisible(true);
            insertPersonView.getHiwiTypeOfPaymentList().setVisible(true);
            insertPersonView.getHiwiTypeOfPaymentList().setSelectedItem(contract.getShk_hourly_rate());
        } else {
            insertPersonView.getTfWorkScope().setText(StringAndBigDecimalFormatter.formatPercentageToStringForScope(contract.getScope()));
            insertPersonView.getPayGroupOnHiring().setVisible(true);
            insertPersonView.getPayGroupList().setSelectedItem(contract.getPaygrade());
            insertPersonView.getPayGroupList().setVisible(true);
            insertPersonView.getPayLevelOnHiring().setVisible(true);
            insertPersonView.getPayLevelList().setVisible(true);
            insertPersonView.getPayLevelList().setSelectedItem(contract.getPaylevel());
            insertPersonView.getVblList().setSelectedItem("Befreit");
            if (contract.getVbl_status()) {
                insertPersonView.getVblList().setSelectedItem("Pflichtig");
            }
            insertPersonView.getVblList().setVisible(true);
            insertPersonView.getVblstate().setVisible(true);
        }

        date = contract.getStart_date();
        insertPersonView.getTfWorkStart().setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        date = contract.getEnd_date();
        insertPersonView.getTfWorkEnd().setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public void resetInputs() {
        currentlyEditedEmployeeID = 0;
        insertPersonView.getTfName().setText(null);
        insertPersonView.getTfVorname().setText(null);
        insertPersonView.getTfStrasse().setText(null);
        insertPersonView.getTfPrivatEmail().setText(null);
        insertPersonView.getTfPrivateTelefonnummer().setText(null);
        insertPersonView.getTfPersonalnummer().setText(null);
        insertPersonView.getTfTuid().setText(null);
        insertPersonView.getTfTranspondernummer().setText(null);
        insertPersonView.getTfBueronummer().setText(null);
        insertPersonView.getTfSalaryPlannedUntil().setText(null);
        insertPersonView.getTfTelefonnummerTUDA().setText(null);
        insertPersonView.getTfHausnummer().setText(null);
        insertPersonView.getTfPLZ().setText(null);
        insertPersonView.getTfAdresszusatz().setText(null);
        insertPersonView.getTfStadt().setText(null);
        insertPersonView.getTfHausnummer().setText(null);
        insertPersonView.getTfAdresszusatz().setText(null);
        insertPersonView.getTfWorkScope().setText(null);
        insertPersonView.getTypeOfJobPicklist().setSelectedItem("Nicht ausgewählt");
        insertPersonView.getNationalityPickList().setSelectedItem("Afghanistan");
        insertPersonView.getNationalityPickList2().setSelectedItem("Keine");
        insertPersonView.getNationalityCheckBox().setSelected(false);
        insertPersonView.getVisaRequiredCheckBox().setSelected(false);
        setNationalityCheckboxInVisible();
        setVisRequiredCheckboxInVisible();
        insertPersonView.getPayGroupOnHiring().setVisible(false);
        insertPersonView.getPayGroupList().setSelectedItem("Nicht ausgewählt");
        insertPersonView.getPayGroupList().setVisible(false);
        insertPersonView.getPayLevelOnHiring().setVisible(false);
        insertPersonView.getPayLevelList().setVisible(false);
        insertPersonView.getPayLevelList().setSelectedItem("Nicht ausgewählt");
        insertPersonView.getVblList().setSelectedItem("Nicht ausgewählt");
        insertPersonView.getHiwiTypeOfPaymentList().setSelectedItem("Nicht ausgewählt");
        insertPersonView.getVblList().setVisible(false);
        insertPersonView.getVblstate().setVisible(false);
        insertPersonView.getHiwiTypeOfPayment().setVisible(false);
        insertPersonView.getHiwiTypeOfPaymentList().setVisible(false);
        insertPersonView.getTfGeburtsdatum().setText(null);
        insertPersonView.getTfVisaValidUntil().setText(null);
        insertPersonView.getTfWorkEnd().setText(null);
        insertPersonView.getTfWorkStart().setText(null);
    }

    //TODO fallunterscheidung ob neuer employee erstellt wird oder bearbeitet wird
    public Employee safeData() {
        int id = employeeDataManager.getNextID();
        String surname = insertPersonView.getTfVorname().getText();
        String name = insertPersonView.getTfName().getText();
        String email_private = insertPersonView.getTfPrivatEmail().getText();
        String phone_private = insertPersonView.getTfPrivateTelefonnummer().getText();
        String citizenship_1 = insertPersonView.getNationalityPickList().getSelectedItem().toString();
        String citizenship_2 = null;
        if (insertPersonView.getNationalityPickList2().getSelectedItem().toString() != null) {
            citizenship_2 = insertPersonView.getNationalityPickList2().getSelectedItem().toString();
        }
        String employeeNumber = insertPersonView.getTfPersonalnummer().getText();
        String tu_id = insertPersonView.getTfTuid().getText();
        boolean visa_required = insertPersonView.getVisaRequiredCheckBox().isSelected();
        String status = insertPersonView.getStatusPicklist().getSelectedItem().toString();
        String transponder_number = insertPersonView.getTfTranspondernummer().getText();
        String office_number = insertPersonView.getTfBueronummer().getText();
        //TODO als Date abfragen und speichern

        LocalDate localDate = insertPersonView.getTfSalaryPlannedUntil().getDate();
        Date salaryPlannedUntil;
        if (localDate == null) {
            salaryPlannedUntil = null;
        } else {
            salaryPlannedUntil = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        Date visaExpiration = null;
        if (visa_required) {
            LocalDate visaExpirationDate = insertPersonView.getTfVisaValidUntil().getDate();
            visaExpiration = Date.from(visaExpirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        String phone_tuda = insertPersonView.getTfTelefonnummerTUDA().getText();
        LocalDate dateOfBirthDate = insertPersonView.getTfGeburtsdatum().getDate();
        Date dateOfBirth;
        if (dateOfBirthDate == null) {
            dateOfBirth = null;
        } else {
            dateOfBirth = Date.from(dateOfBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        String houseNumber = insertPersonView.getTfHausnummer().getText();
        String street = insertPersonView.getTfStrasse().getText();
        String zip_code = insertPersonView.getTfPLZ().getText();
        String additional_address = insertPersonView.getTfAdresszusatz().getText();
        String city = insertPersonView.getTfStadt().getText();
        String typeOfJob = insertPersonView.getTypeOfJobPicklist().getSelectedItem().toString();
        String payGrade = insertPersonView.getPayGroupList().getSelectedItem().toString();
        String payLevel = insertPersonView.getPayLevelList().getSelectedItem().toString();
        String hiwiTypeOfPayment = insertPersonView.getHiwiTypeOfPaymentList().getSelectedItem().toString();
        boolean vbl = false;
        if (insertPersonView.getVblList().getSelectedItem().toString().equals("Pflichtig")) {
            vbl = true;
        }
        LocalDate workStartDate = insertPersonView.getTfWorkStart().getDate();
        Date workStart;
        if (workStartDate == null) {
            workStart = null;
        } else {
            workStart = Date.from(workStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        LocalDate workEndDate = insertPersonView.getTfWorkEnd().getDate();
        Date workEnd;
        if (workEndDate == null) {
            workEnd = null;
        } else {
            workEnd = Date.from(workEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        BigDecimal scope;
        if (insertPersonView.getTfWorkScope().getText().equals("")) {
            scope = new BigDecimal(0);
        } else {
            scope = StringAndBigDecimalFormatter.formatStringToPercentageValueForScope(insertPersonView.getTfWorkScope().getText());
        }

        if (surname.equals("") || name.equals("") || salaryPlannedUntil == null || workStart == null || workEnd == null || typeOfJob.equals("Nicht ausgewählt")) {
            insertPersonView.markMustBeFilledTextFields();
            return null;
        }
        if(typeOfJob.equals("SHK")){
            if(hiwiTypeOfPayment.equals("Nicht ausgewählt")){
                insertPersonView.markMustBeFilledTextFields();
                return null;
            }
        }
        if(typeOfJob.equals("WiMi")||typeOfJob.equals("ATM")){
            if(payGrade.equals("Nicht ausgewählt") || payLevel.equals("Nicht ausgewählt") || insertPersonView.getVblList().getSelectedItem().toString().equals("Nicht ausgewählt")){
                insertPersonView.markMustBeFilledTextFields();
                return null;
            }

        }


        Employee newEmployee = new Employee(id, name, surname, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil, visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city, street);
        employeeDataManager.addEmployee(newEmployee);

        if(hiwiTypeOfPayment.equals("Nicht ausgewählt")){
            hiwiTypeOfPayment = "";
        }

        if(payGrade.equals("Nicht ausgewählt")){
            payGrade = "";
        }

        if(payLevel.equals("Nicht ausgewählt")){
            payLevel = "";
        }

        Contract newContract = new Contract(id, payGrade, payLevel, workStart, workEnd, new BigDecimal(0), new BigDecimal(0), scope, hiwiTypeOfPayment, vbl);
        BigDecimal[] startSalary;
        startSalary = salaryTableLookUp.getCurrentPayRateTableEntry(newContract);
        BigDecimal regular_cost = startSalary[0]; //TODO Berechnung für SHK ändern
        BigDecimal bonus_cost = startSalary[1].multiply(new BigDecimal(12));
        newContract = new Contract(id, payGrade, payLevel, workStart, workEnd, regular_cost, bonus_cost, scope, hiwiTypeOfPayment, vbl);
        contractDataManager.addContract(newContract);
        return newEmployee;


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertPersonView.getNationalityCheckBox()) {
            if (insertPersonView.getNationalityCheckBox().isSelected()) {
                setNationalityCheckboxVisible();
            } else {
                setNationalityCheckboxInVisible();
            }
        }
        if (e.getSource() == insertPersonView.getVisaRequiredCheckBox()) {
            if (insertPersonView.getVisaRequiredCheckBox().isSelected()) {
                setVisRequiredCheckboxVisible();
            } else {
                setVisRequiredCheckboxInVisible();
            }
        }
        if (e.getSource() == insertPersonView.getSubmit()) {
            Employee checkEmployee = null;
            boolean test = false;
            if (currentlyEditedEmployeeID != 0) {
                test = updateData(currentlyEditedEmployeeID);
                if (test) {
                    JOptionPane.showConfirmDialog(null, "Bitte füllen Sie die markierten Spalten aus um fortzufahren.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                checkEmployee = safeData();
                if (checkEmployee == null) {
                    JOptionPane.showConfirmDialog(null, "Bitte füllen Sie die markierten Spalten aus um fortzufahren.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            resetInputs();
            insertPersonView.revalidate();
            insertPersonView.repaint();
            ShowPersonController showPersonController = frameController.getShowPersonalData();
            showPersonController.updateData(showPersonController.getEmployeeDataFromDataBase());
            SalaryListController salaryListController = frameController.getSalaryListController();
            salaryListController.updateData(salaryListController.getSalaryDataFromDataBase());
            frameController.getUpdater().nameListUpdate();
        }
        if (e.getSource() == insertPersonView.getSalaryEntry()) {
            boolean test = false;
            InsertSalaryController insertSalaryController = new InsertSalaryController(frameController);
            if (currentlyEditedEmployeeID != 0) {
                test = updateData(currentlyEditedEmployeeID);
                if (test) {
                    JOptionPane.showConfirmDialog(null, "Bitte füllen Sie die markierten Spalten aus um fortzufahren.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                insertSalaryController.fillFields(currentlyEditedEmployeeID);
            } else {
                Employee newEmployee = safeData();
                if (newEmployee == null) {
                    JOptionPane.showConfirmDialog(null, "Bitte füllen Sie die markierten Spalten aus um fortzufahren.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return;
                }
                insertSalaryController.fillFields(newEmployee.getId());
            }
            resetInputs();
            insertPersonView.revalidate();
            insertPersonView.repaint();
            ShowPersonController showPersonController = frameController.getShowPersonalData();
            showPersonController.updateData(showPersonController.getEmployeeDataFromDataBase());
            SalaryListController salaryListController = frameController.getSalaryListController();
            salaryListController.updateData(salaryListController.getSalaryDataFromDataBase());
            insertSalaryController.showInsertSalaryView(frameController);
            frameController.getUpdater().nameListUpdate();
            frameController.getTabs().removeTabNewWindow(insertPersonView);
        }
        if (e.getSource() == insertPersonView.getReset()) {
            resetInputs();
        }
        if (e.getSource() == insertPersonView.getCancel()) {
            resetInputs();
            frameController.getTabs().removeTabNewWindow(insertPersonView);
        }
        if (e.getSource() == insertPersonView.getTypeOfJobPicklist()) {
            if(insertPersonView.getTypeOfJobPicklist().getSelectedItem().toString().equals("Nicht ausgewählt")){
                insertPersonView.getTfWorkScope().setInputVerifier(null);
                insertPersonView.getTfWorkScope().setText(null);
                insertPersonView.getPayGroupOnHiring().setVisible(false);
                insertPersonView.getPayGroupList().setVisible(false);
                insertPersonView.getPayLevelOnHiring().setVisible(false);
                insertPersonView.getPayLevelList().setVisible(false);
                insertPersonView.getVblstate().setVisible(false);
                insertPersonView.getVblList().setVisible(false);
                insertPersonView.getHiwiTypeOfPayment().setVisible(false);
                insertPersonView.getHiwiTypeOfPaymentList().setVisible(false);
            }
            if (insertPersonView.getTypeOfJobPicklist().getSelectedItem().toString().equals("WiMi") || insertPersonView.getTypeOfJobPicklist().getSelectedItem().toString().equals("ATM")) {
                insertPersonView.getTfWorkScope().setInputVerifier(null);
                insertPersonView.getTfWorkScope().setText(null);
                insertPersonView.getPayGroupOnHiring().setVisible(true);
                insertPersonView.getPayGroupList().setVisible(true);
                insertPersonView.getPayLevelOnHiring().setVisible(true);
                insertPersonView.getPayLevelList().setVisible(true);
                insertPersonView.getVblstate().setVisible(true);
                insertPersonView.getVblList().setVisible(true);
                insertPersonView.getTfWorkScope().setInputVerifier(new WorkScopeVerifier());
            } else {
                insertPersonView.getPayGroupOnHiring().setVisible(false);
                insertPersonView.getPayGroupList().setVisible(false);
                insertPersonView.getPayLevelOnHiring().setVisible(false);
                insertPersonView.getPayLevelList().setVisible(false);
                insertPersonView.getVblstate().setVisible(false);
                insertPersonView.getVblList().setVisible(false);
            }
            if (insertPersonView.getTypeOfJobPicklist().getSelectedItem().toString().equals("SHK")) {
                insertPersonView.getTfWorkScope().setInputVerifier(null);
                insertPersonView.getTfWorkScope().setText(null);
                insertPersonView.getHiwiTypeOfPayment().setVisible(true);
                insertPersonView.getHiwiTypeOfPaymentList().setVisible(true);
                insertPersonView.getTfWorkScope().setInputVerifier(new WorkScopeSHKVerifier());
            } else {
                insertPersonView.getHiwiTypeOfPayment().setVisible(false);
                insertPersonView.getHiwiTypeOfPaymentList().setVisible(false);
            }
        }
    }

}
