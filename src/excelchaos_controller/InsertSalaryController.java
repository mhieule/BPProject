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

    public void getEmployeeNameList(boolean fixed, String currentName, String paygrade, String paylevel){
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        String[] names = employeeDataManager.getAllEmployeesNameList();
        insertSalaryView.getNamePickList().setModel(new DefaultComboBoxModel<>(names));

        if(fixed){
            insertSalaryView.getNamePickList().setSelectedItem(currentName);
            insertSalaryView.getNamePickList().setEnabled(false);

            insertSalaryView.getTfGruppe().setText(paygrade);
            insertSalaryView.getTfGruppe().setEnabled(false);

            insertSalaryView.getPlStufe().setSelectedItem(paylevel);
            insertSalaryView.getPlStufe().setEnabled(false);
        }
        else{
            insertSalaryView.getNamePickList().setEnabled(true);
            insertSalaryView.getNamePickList().setSelectedItem(JComponent.getDefaultLocale());

            insertSalaryView.getTfGruppe().setEnabled(true);
            insertSalaryView.getTfGruppe().setText(null);
            insertSalaryView.getTfGruppe().setEditable(true);

            insertSalaryView.getPlStufe().setEnabled(true);
            insertSalaryView.getPlStufe().setSelectedItem(JComponent.getDefaultLocale());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertSalaryView.getSubmit()) {
            System.out.println("submitting");
            ContractDataManager contractDataManager = new ContractDataManager();
            EmployeeDataManager employeeDataManager = new EmployeeDataManager();
            Employee employee = employeeDataManager.getEmployeeByName(insertSalaryView.getNamePickList().getSelectedItem().toString());

            int id = employee.getId();
            String paygrade = insertSalaryView.getTfGruppe().getText();
            String paylevel = insertSalaryView.getPlStufe().getSelectedItem().toString();
            double gehalt = Double.parseDouble(insertSalaryView.getTfGehalt().getText());
            double sonderzahlung = Double.parseDouble(insertSalaryView.getTfSonderzahlung().getText());

            Contract contract = contractDataManager.getContract(id);

            contract.setPaygrade(paygrade);
            contract.setPaylevel(paylevel);
            contract.setRegular_cost(gehalt);
            contract.setBonus_cost(sonderzahlung);

            insertSalaryView.removeAll();
            insertSalaryView.revalidate();
            insertSalaryView.repaint();
            SideMenuPanelActionLogView.model.addElement("Eintrag eingefügt!");
        }
        if(e.getSource() == insertSalaryView.getReset()){
            System.out.println("resetting");
            insertSalaryView.getPlStufe().setSelectedItem("Nicht ausgewählt");
            insertSalaryView.getTfGehalt().setText(null);
            insertSalaryView.getTfSonderzahlung().setText(null);
            insertSalaryView.getTfGruppe().setText(null);

            if(insertSalaryView.getNamePickList().isEnabled()){
                insertSalaryView.getNamePickList().setSelectedItem(null);
            }
        }

    }
}