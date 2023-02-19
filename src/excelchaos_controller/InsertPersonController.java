package excelchaos_controller;

import excelchaos_model.Contract;
import excelchaos_model.ContractDataManager;
import excelchaos_model.Employee;
import excelchaos_view.InsertPersonView;
import excelchaos_model.EmployeeDataManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class InsertPersonController implements ActionListener {
    private InsertPersonView insertPersonView;
    private MainFrameController frameController;

    private String addPersonTab = "Person hinzufügen";

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

    private void setNationalityCheckboxVisible(){
        insertPersonView.getNationalitySecond().setVisible(true);
        insertPersonView.getNationalityPickList2().setVisible(true);
    }

    private void setNationalityCheckboxInVisible(){
        insertPersonView.getNationalitySecond().setVisible(false);
        insertPersonView.getNationalityPickList2().setVisible(false);
    }

    private void setVisRequiredCheckboxVisible(){
        insertPersonView.getVisaValidUntil().setVisible(true);
        insertPersonView.getTfVisaValidUntil().setVisible(true);
    }

    private void setVisRequiredCheckboxInVisible(){
        insertPersonView.getVisaValidUntil().setVisible(false);
        insertPersonView.getTfVisaValidUntil().setVisible(false);
    }

    public void resetInputs(){
        insertPersonView.getTfName().setText(null);
        insertPersonView.getTfVorname().setText(null);
        insertPersonView.getTfStrasse().setText(null);
        insertPersonView.getTfPrivatEmail().setText(null);
        insertPersonView.getTfPrivateTelefonnummer().setText(null);
        insertPersonView.getTfPersonalnummer().setText(null);
        insertPersonView.getTfTuid().setText(null);
        insertPersonView.getTfTranspondernummer().setText(null);
        insertPersonView.getTfBueronummer().setText(null);
        insertPersonView.getTfGehaltEingeplanntBis().setText(null);
        insertPersonView.getTfTelefonnummerTUDA().setText(null);
        insertPersonView.getTfHausnummer().setText(null);
        insertPersonView.getTfPLZ().setText(null);
        insertPersonView.getTfAdresszusatz().setText(null);
        insertPersonView.getTfStadt().setText(null);
        insertPersonView.getTfHausnummer().setText(null);
        insertPersonView.getTfAdresszusatz().setText(null);
        insertPersonView.getTfPLZ().setText(null);
        insertPersonView.getTfStadt().setText(null);
        insertPersonView.getTfWorkScope().setText(null);
        insertPersonView.getTypeOfJobPicklist().setSelectedItem("Nicht ausgewählt");
        insertPersonView.getNationalityPickList().setSelectedItem("Afghanistan");
        insertPersonView.getNationalityPickList2().setSelectedItem(null);
        insertPersonView.getNationalityCheckBox().setSelected(false);
        insertPersonView.getVisaRequiredCheckBox().setSelected(false);
        setNationalityCheckboxInVisible();
        setVisRequiredCheckboxInVisible();
        insertPersonView.getPayGroupOnHiring().setVisible(false);
        insertPersonView.getPayGroupList().setVisible(false);
        insertPersonView.getPayGradeOnHiring().setVisible(false);
        insertPersonView.getPayGradeList().setVisible(false);
        insertPersonView.getVblList().setVisible(false);
        insertPersonView.getVblstate().setVisible(false);
        insertPersonView.getHiwiTypeOfPayment().setVisible(false);
        insertPersonView.getHiwiTypeOfPaymentList().setVisible(false);
        insertPersonView.getTfGeburtsdatum().setText(null);
        insertPersonView.getTfVisaValidUntil().setText(null);
        insertPersonView.getTfGehaltEingeplanntBis().setText(null);
        insertPersonView.getTfWorkEnd().setText(null);
        insertPersonView.getTfWorkStart().setText(null);
    }

    //TODO VBL Status Speichern (wenn Feld in DB verfügbar ist)
    public Employee safeData(){
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        ContractDataManager contractDataManager = new ContractDataManager();
        int id = employeeDataManager.getNextID();
        String surname = insertPersonView.getTfName().getText();
        String name  = insertPersonView.getTfVorname().getText();
        String email_private = insertPersonView.getTfPrivatEmail().getText();
        String phone_private = insertPersonView.getTfPrivateTelefonnummer().getText();
        String citizenship_1 = insertPersonView.getNationalityPickList().getSelectedItem().toString();
        String citizenship_2 = insertPersonView.getNationalityPickList2().getSelectedItem().toString();
        String employeeNumber = insertPersonView.getTfPersonalnummer().getText();
        String tu_id = insertPersonView.getTfTuid().getText();
        boolean visa_required = insertPersonView.getVisaRequiredCheckBox().isSelected();
        String status = insertPersonView.getStatusPicklist().getSelectedItem().toString();
        String transponder_number = insertPersonView.getTfTranspondernummer().getText();
        String office_number = insertPersonView.getTfBueronummer().getText();
        String salaryPlannedUntil = insertPersonView.getTfGehaltEingeplanntBis().getText();
        Date visaExpiration = null;
        Calendar calendar = Calendar.getInstance();
        if(visa_required) {
            LocalDate visaExpirationDate = insertPersonView.getTfVisaValidUntil().getDate();
            calendar.set(visaExpirationDate.getYear(), visaExpirationDate.getMonth().getValue(), visaExpirationDate.getDayOfMonth());
            visaExpiration = calendar.getTime();
        }
        String phone_tuda = insertPersonView.getTfTelefonnummerTUDA().getText();
        LocalDate dateOfBirthDate = insertPersonView.getTfGeburtsdatum().getDate();
        calendar.set(dateOfBirthDate.getYear(), dateOfBirthDate.getMonth().getValue(), dateOfBirthDate.getDayOfMonth());
        Date dateOfBirth = calendar.getTime();
        String houseNumber = insertPersonView.getTfHausnummer().getText();
        String zip_code = insertPersonView.getTfPLZ().getText();
        String additional_address = insertPersonView.getTfAdresszusatz().getText();
        String city = insertPersonView.getTfStadt().getText();
        String payGrade = insertPersonView.getPayGroupList().getSelectedItem().toString();
        String payLevel = insertPersonView.getPayGradeList().getSelectedItem().toString();

        //TODO muss in Datenbank als Date gespeichert werden und nicht als String
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        LocalDate workStartDate = insertPersonView.getTfWorkStart().getDate();
        calendar.set(workStartDate.getYear(), workStartDate.getMonth().getValue(), workStartDate.getDayOfMonth());
        String workStart = dateFormat.format(calendar.getTime());
        LocalDate workEndDate = insertPersonView.getTfWorkEnd().getDate();
        calendar.set(workEndDate.getYear(), workEndDate.getMonth().getValue(), workEndDate.getDayOfMonth());
        String workEnd = dateFormat.format(calendar.getTime());

        Employee newEmployee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city);
        employeeDataManager.addEmployee(newEmployee);
        Contract newContract = new Contract(id, payGrade, payLevel, workStart, workEnd, 0, 0);
        contractDataManager.addContract(newContract);
        return newEmployee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertPersonView.getNationalityCheckBox()) {
            if (insertPersonView.getNationalityCheckBox().isSelected()){
                setNationalityCheckboxVisible();
            }else{
                setNationalityCheckboxInVisible();
            }
        }
        if (e.getSource() == insertPersonView.getVisaRequiredCheckBox()){
            if (insertPersonView.getVisaRequiredCheckBox().isSelected()){
                setVisRequiredCheckboxVisible();
            }else {
                setVisRequiredCheckboxInVisible();
            }
        }
        if (e.getSource() == insertPersonView.getSubmit()) {
            safeData();
            resetInputs();
            insertPersonView.revalidate();
            insertPersonView.repaint();
            frameController.getShowPersonalData().updateData();
            frameController.getSalaryListController().updateData();
        }
        if(e.getSource() == insertPersonView.getSalaryEntry()){
            Employee newEmployee = safeData();
            frameController.getInsertSalaryController().fillFields(newEmployee.getSurname() + " " + newEmployee.getName());
            resetInputs();
            insertPersonView.revalidate();
            insertPersonView.repaint();
            frameController.getShowPersonalData().updateData();
            frameController.getSalaryListController().updateData();
            frameController.getInsertSalaryController().showInsertSalaryView(frameController);
            frameController.getTabs().removeTabNewWindow(insertPersonView);
        }
        if(e.getSource() == insertPersonView.getReset()){
            resetInputs();
        }
        if(e.getSource() == insertPersonView.getCancel()){
            resetInputs();
            frameController.getTabs().removeTabNewWindow(insertPersonView);
        }
        if (e.getSource() == insertPersonView.getTypeOfJobPicklist()){
            if (insertPersonView.getTypeOfJobPicklist().getSelectedItem().toString().equals("WiMi") || insertPersonView.getTypeOfJobPicklist().getSelectedItem().toString().equals("ATM")){
                insertPersonView.getPayGroupOnHiring().setVisible(true);
                insertPersonView.getPayGroupList().setVisible(true);
                insertPersonView.getPayGradeOnHiring().setVisible(true);
                insertPersonView.getPayGradeList().setVisible(true);
                insertPersonView.getVblstate().setVisible(true);
                insertPersonView.getVblList().setVisible(true);
            } else {
                insertPersonView.getPayGroupOnHiring().setVisible(false);
                insertPersonView.getPayGroupList().setVisible(false);
                insertPersonView.getPayGradeOnHiring().setVisible(false);
                insertPersonView.getPayGradeList().setVisible(false);
                insertPersonView.getVblstate().setVisible(false);
                insertPersonView.getVblList().setVisible(false);
            }
            if (insertPersonView.getTypeOfJobPicklist().getSelectedItem().toString().equals("SHK")){
                insertPersonView.getHiwiTypeOfPayment().setVisible(true);
                insertPersonView.getHiwiTypeOfPaymentList().setVisible(true);
            } else {
                insertPersonView.getHiwiTypeOfPayment().setVisible(false);
                insertPersonView.getHiwiTypeOfPaymentList().setVisible(false);
            }
        }
    }

}
