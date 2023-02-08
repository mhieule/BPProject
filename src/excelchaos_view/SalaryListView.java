package excelchaos_view;

import excelchaos_model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SalaryListView extends JPanel {
    private CustomTable jt;
    public void init(){
//        removeAll();
//        setLayout(new BorderLayout());
//        String column[] = {
//                "Name", "Vorname", "Geburtsdatum", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung"
//        };
//        File f = new File("src/salaryData");
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(f));
//            int lines = 0;
//            while (br.readLine() != null) lines++;
//            br.close();
//            String resultData[][] = new String[lines][];
//
//
//            BufferedReader reader = new BufferedReader(new FileReader(f));
//            String line = null;
//            int currentIndex = 0;
//            while ((line = reader.readLine()) != null) {
//                String[] values = line.split(",");
//                resultData[currentIndex] = values;
//                currentIndex++;
//            }
//            jt = new CustomTable(resultData, column);
//            //jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//            //TableColumnAdjuster tca = new TableColumnAdjuster(jt);
//            //tca.adjustColumns();
//            JScrollPane sp = new JScrollPane(jt);
//            sp.setVisible(true);
//
//            add(sp);
//            revalidate();
//            repaint();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        addData();
    }

    public void addData(){
        removeAll();
        setLayout(new BorderLayout());

        String column[] = {
                "Name", "Vorname", "Geburtsdatum", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung"
        };
        ContractDataManager contractDataManager = new ContractDataManager();
        int lines  = contractDataManager.getRowCount();
        String resultData[][] = new String[lines][];
        int currentIndex = 0;
        List<Contract> contracts = contractDataManager.getAllContracts();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (Contract contract : contracts){
            EmployeeDataManager employeeDataManager = new EmployeeDataManager();
            Employee employee = employeeDataManager.getEmployee(contract.getId());

            String name = employee.getName();
            String surname = employee.getSurname();
            String dateOfBirth = dateFormat.format(employee.getDate_of_birth());
            String group = contract.getPaygrade();
            String stufe = contract.getPaylevel();
            String gehalt = Double.toString(contract.getRegular_cost());
            String sonderzahlungen = Double.toString(contract.getBonus_cost());

            String[] values = {surname, name, dateOfBirth, group, stufe, gehalt, sonderzahlungen};
            resultData[currentIndex] = values;
            currentIndex++;
        }

        jt = new CustomTable(resultData, column);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(jt);
        tca.adjustColumns();
        JScrollPane sp = new JScrollPane(jt);
        sp.setVisible(true);

        add(sp);
        revalidate();
        repaint();
    }


    public void showPayGradeIncrease(){
        removeAll();
        setLayout(new BorderLayout());
        String column[] = {
                "Name", "Vorname", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung", "Höherstufung 1 ab", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung",
                "Höherstufung 2 ab", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung"
        };
        File f = new File("src/moreSalaryData");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            int lines = 0;
            while (br.readLine() != null) lines++;
            br.close();
            String resultData[][] = new String[lines][];


            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line = null;
            int currentIndex = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                resultData[currentIndex] = values;
                currentIndex++;
            }
            jt = new CustomTable(resultData, column);
            jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            TableColumnAdjuster tca = new TableColumnAdjuster(jt);
            tca.adjustColumns();
            JScrollPane sp = new JScrollPane(jt);
            sp.setVisible(true);

            add(sp);
            revalidate();
            repaint();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomTable getTable() {
        return jt;
    }
}
