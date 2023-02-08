package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_view.InsertPersonView;
import excelchaos_view.InsertSalaryView;
import excelchaos_view.SideMenuPanelActionLogView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class InsertSalaryController implements ActionListener {
    private InsertSalaryView insertSalaryView;
    private MainFrameController frameController;

    private String addSalaryTab = "Gehaltseintrag bearbeiten";

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

    //wandelt String in Calendar um
    public GregorianCalendar getCalendarFromString (String date){ //setzt muster voraus: 2001.05.12
        System.out.println("startdate: " + date);

        int year = Integer.parseInt(new StringBuilder().append(date.charAt(6)).append(date.charAt(7)).append(date.charAt(8)).append(date.charAt(9)).toString());
        int month = Integer.parseInt(new StringBuilder().append(date.charAt(3)).append(date.charAt(4)).toString());
        int day = Integer.parseInt(new StringBuilder().append(date.charAt(0)).append(date.charAt(1)).toString());
        return new GregorianCalendar(year, month, day);
    }

    // noch unterscheiden ob 1A oder 1 Tabelle
    //returns String of salaryTable which is closest to startDate of contract
    public String getCorrectSalaryTable(String group, String startDate){
        SalaryTableManager salaryTableManager = new SalaryTableManager();
        List<String> salaryTableList = salaryTableManager.getDistinctTableNames(group);
        GregorianCalendar startOfContract = getCalendarFromString(startDate);
        String correctTable = null;

        int diffYear = 100;
        int diffMonth = 100;
        int diffDay = 100;

        for (String tableName: salaryTableList) {
            List<SalaryTable> salaryTable = salaryTableManager.getSalaryTable(tableName);
            GregorianCalendar startOfSalaryList = getCalendarFromString(salaryTableManager.getSalaryTable(tableName).get(0).getDate());
            if(startOfSalaryList.after(startOfContract)){
                int tempDiffYear = startOfSalaryList.get(Calendar.YEAR) - startOfContract.get(Calendar.YEAR);
                int tempDiffMonth = startOfSalaryList.get(Calendar.MONTH) - startOfContract.get(Calendar.MONTH);
                int tempDiffDay = startOfSalaryList.get(Calendar.DAY_OF_MONTH) - startOfContract.get(Calendar.DAY_OF_MONTH);

                if(diffYear > tempDiffYear){
                    correctTable = salaryTable.get(0).getTable_name();
                    diffYear = tempDiffYear;
                    diffMonth = tempDiffMonth;
                    diffDay = tempDiffDay;
                }
                else if(diffYear == tempDiffYear){
                    if(diffMonth > tempDiffMonth){
                        correctTable = salaryTable.get(0).getTable_name();
                        diffYear = tempDiffYear;
                        diffMonth = tempDiffMonth;
                        diffDay = tempDiffDay;
                    }
                    else if(diffMonth == tempDiffMonth){
                        if(diffDay > tempDiffDay) {
                            correctTable = salaryTable.get(0).getTable_name();
                            diffYear = tempDiffYear;
                            diffMonth = tempDiffMonth;
                            diffDay = tempDiffDay;
                        }
                    }
                }
            }
        }
        return correctTable;
    }

    //returns correct salary and extraCost
    public String[] getSalaryFromTable(String group, String startDate, String level, boolean vblFree){
        SalaryTableManager salaryTableManager = new SalaryTableManager();
        List<SalaryTable> salaryTable = salaryTableManager.getSalaryTable(getCorrectSalaryTable(group, startDate));
        double salary = 0;
        double extraCost = 0;

        if(level.equals("1A") && vblFree){
            salary = salaryTable.get(0).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(0).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("1A") && !vblFree){
            salary = salaryTable.get(1).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(1).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("1B") && vblFree){
            salary = salaryTable.get(2).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(2).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("1B") && !vblFree){
            salary = salaryTable.get(3).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(3).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("2") && vblFree){
            salary = salaryTable.get(4).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(4).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("2") && !vblFree){
            salary = salaryTable.get(5).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(5).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("3") && vblFree){
            salary = salaryTable.get(6).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(6).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("3") && !vblFree){
            salary = salaryTable.get(7).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(7).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("4") && vblFree){
            salary = salaryTable.get(8).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(8).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("4") && !vblFree){
            salary = salaryTable.get(9).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(9).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("5") && vblFree){
            salary = salaryTable.get(10).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(10).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("5") && !vblFree){
            salary = salaryTable.get(11).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(11).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("6") && vblFree){
            salary = salaryTable.get(12).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(12).getJsz_als_monatliche_zulage();
        }
        else if(level.equals("6") && !vblFree){
            salary = salaryTable.get(13).getMtl_kosten_ohne_jsz();
            extraCost = salaryTable.get(13).getJsz_als_monatliche_zulage();
        }
        return new String[]{Double.toString(salary), Double.toString(extraCost)};
    }

    //funktioniert derzeit nur für WIMI und ATM mit 1A und 1B Tabelle
    public String[] getSalary(String typeOfJob, String group, String startDate, String level, boolean vblFree){
        if(typeOfJob.equals("WiMi") || typeOfJob.equals("ATM")){
            return getSalaryFromTable(group, startDate, level, vblFree);
        }
        //methode schreiben die gehalt von "SHK" returned
        else{
            return null;
        }
    }

    public void fillFields(String currentName, String typeOfJob, String group, String startDate, String level, boolean vblFree){
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        String[] names = employeeDataManager.getAllEmployeesNameList();

        insertSalaryView.getNamePickList().setModel(new DefaultComboBoxModel<>(names));
        insertSalaryView.getNamePickList().setSelectedItem(currentName);
        insertSalaryView.getNamePickList().setEnabled(false);
        insertSalaryView.getTfGruppe().setText(group);
        insertSalaryView.getPlStufe().setSelectedItem(level);

        String salary = getSalary(typeOfJob, group, startDate, level, vblFree)[0];
        String extraCost = getSalary(typeOfJob, group, startDate, level, vblFree)[1];
        insertSalaryView.getTfGehalt().setText(salary);
        insertSalaryView.getTfSonderzahlung().setText(extraCost);
    }

    public void resetInputs(){
        insertSalaryView.getTfGruppe().setText(null);
        insertSalaryView.getPlStufe().setSelectedItem("Nicht ausgewählt");
        insertSalaryView.getTfGehalt().setText(null);
        insertSalaryView.getTfSonderzahlung().setText(null);
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

            contractDataManager.updateContract(contract);

            insertSalaryView.revalidate();
            insertSalaryView.repaint();
            SideMenuPanelActionLogView.model.addElement("Eintrag eingefügt!");
            frameController.getSalaryListController().updateData();
            resetInputs();
            frameController.getTabs().removeTabNewWindow(insertSalaryView);
        }
        if(e.getSource() == insertSalaryView.getReset()){
            System.out.println("resetting");
            resetInputs();
        }

    }
}