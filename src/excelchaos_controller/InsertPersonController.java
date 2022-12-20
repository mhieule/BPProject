package excelchaos_controller;

import excelchaos_model.Employee;
import excelchaos_view.InsertPersonView;
import excelchaos_view.SideMenuPanelActionLogView;
import excelchaos_model.EmployeeDataManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InsertPersonController implements ActionListener {
    private InsertPersonView insertPersonView;
    private MainFrameController frameController;

    private String addPersonTab = "Person hinzufügen";

    public InsertPersonController(MainFrameController mainFrameController) {
        insertPersonView = new InsertPersonView();
        insertPersonView.init();
        insertPersonView.setHasToolbar(false);
        insertPersonView.setActionListener(this);
        frameController = mainFrameController;

    }

    public InsertPersonView getInsertPersonView() {
        return insertPersonView;
    }

    public void showInsertPersonView(MainFrameController mainFrameController) {
        SideMenuPanelActionLogView.model.addElement("Eintrag einfügen");
        if (mainFrameController.getTabs().indexOfTab(addPersonTab) == -1) {
            mainFrameController.getTabs().addTab(addPersonTab, insertPersonView);
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addPersonTab));
        } else
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addPersonTab));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertPersonView.getNationalityCheckBox()) {
            insertPersonView.getNationalitySecond().setVisible(true);
            insertPersonView.getNationalityPickList2().setVisible(true);
        }
        if (e.getSource() == insertPersonView.getSubmit()) {
            System.out.println("submitting");
            EmployeeDataManager employeeDataManager = new EmployeeDataManager();
            int id = employeeDataManager.getNextID();
            String surname = insertPersonView.getTfName().getText();
            String name  = insertPersonView.getTfVorname().getText();
            String email_private = insertPersonView.getTfPrivatEmail().getText();
            String phone_private = insertPersonView.getTfPrivateTelefonnummer().getText();
            String citizenship_1 = insertPersonView.getNationalityPickList().getSelectedItem().toString();
            String citizenship_2 = insertPersonView.getNationalityPickList2().getSelectedItem().toString();
            String employeeNumber = insertPersonView.getTfPersonalnummer().getText();
            String tu_id = insertPersonView.getTfTuid().getText();
            boolean visa_required = false;
            String status = insertPersonView.getStatusPicklist().toString();
            String transponder_number = insertPersonView.getTfTranspondernummer().getText();
            String office_number = insertPersonView.getTfBueronummer().getText();
            String salaryPlannedUntil = insertPersonView.getTfGehaltEingeplanntBis().getText();
            String visaExpiration = "01.01.2222";
            String phone_tuda = "1234556";
            String dateOfBirth = insertPersonView.getTfGeburtsdatum().getText();
            String houseNumber = insertPersonView.getTfHausnummer().getText();
            String zip_code = insertPersonView.getTfPersonalnummer().getText();
            String additional_address = insertPersonView.getTfAdresszusatz().getText();
            String city = insertPersonView.getTfStadt().getText();
            Employee newEmployee = new Employee(id, surname, name, email_private, phone_private, citizenship_1,
                    citizenship_2, employeeNumber, tu_id, visa_required, status, transponder_number, office_number, phone_tuda,
                    salaryPlannedUntil,visaExpiration, dateOfBirth, houseNumber, zip_code, additional_address, city);
            employeeDataManager.addEmployee(newEmployee);
            insertPersonView.removeAll();
            insertPersonView.revalidate();
            insertPersonView.repaint();
            SideMenuPanelActionLogView.model.addElement("Eintrag eingefügt!");
        }
    }
}
