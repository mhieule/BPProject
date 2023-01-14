package excelchaos_controller;

import excelchaos_model.Contract;
import excelchaos_model.ContractDataManager;
import excelchaos_model.Employee;
import excelchaos_view.InsertPersonView;
import excelchaos_view.InsertSalaryView;
import excelchaos_view.SideMenuPanelActionLogView;
import excelchaos_model.EmployeeDataManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertSalaryController implements ActionListener {
    private InsertSalaryView insertSalaryView;
    private MainFrameController frameController;

    private String addSalaryTab = "Gehaltseintrag hinzufügen";

    public InsertSalaryController(MainFrameController mainFrameController) {
        insertSalaryView = new InsertSalaryView();
        insertSalaryView.init();
        insertSalaryView.setActionListener(this);
        frameController = mainFrameController;

    }

    public InsertSalaryView getInsertSalaryView() {
        return insertSalaryView;
    }

    public void showInsertSalaryView(MainFrameController mainFrameController) {
        SideMenuPanelActionLogView.model.addElement("Eintrag einfügen");
        if (mainFrameController.getTabs().indexOfTab(addSalaryTab) == -1) {
            mainFrameController.getTabs().addTab(addSalaryTab, insertSalaryView);
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addSalaryTab));
        } else
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addSalaryTab));
    }

    public void getEmployeeNameList(boolean fixedName, String currentName){
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        String[] names = employeeDataManager.getAllEmployeesNameList();
        insertSalaryView.getNamePickList().setModel(new DefaultComboBoxModel<>(names));

        if(fixedName){
            insertSalaryView.getNamePickList().setEditable(true);
            insertSalaryView.getNamePickList().setSelectedItem(currentName);
            insertSalaryView.getNamePickList().setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertSalaryView.getSubmit()) {
            System.out.println("submitting");
            ContractDataManager contractDataManager = new ContractDataManager();
            int id = 1;
            String status = insertSalaryView.getStatus().getText();
            String stufe = insertSalaryView.getStufe().getText();
            String gehalt = insertSalaryView.getGehalt().getText();
            String Sonderzahlung = insertSalaryView.getSonderzahlung().getText();

            Contract newContract = new Contract(id, status, stufe, "startdate", "enddate", Double.parseDouble(gehalt), Double.parseDouble(Sonderzahlung));
            contractDataManager.addContract(newContract);
            insertSalaryView.removeAll();
            insertSalaryView.revalidate();
            insertSalaryView.repaint();
            SideMenuPanelActionLogView.model.addElement("Eintrag eingefügt!");
        }

    }
}
