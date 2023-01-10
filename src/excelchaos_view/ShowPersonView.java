package excelchaos_view;

import excelchaos_model.Employee;
import excelchaos_model.EmployeeDataManager;
import excelchaos_model.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShowPersonView extends JPanel {
    private JTable jt;
    public void init() {
        setLayout(new BorderLayout());
        System.out.println("SEEING ITEMS");

        String column[] = {"Name", "Vorname","Haunsummer", "PLZ","Stadt","Addresszusatz",
                "E-Mail Privat", "Telefon Privat",
                "Geburtsdatum", "Staatsangehörigkeit 1", "Staatsangehörigkeit 2", "Personal.Nr", "TUID",
                "Status", "Gehalt Eingeplant bis", "Transponder.Nr", "Büro.Nr", "Telefon TUDA"
        };

        /*File f = new File("src/data");
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
            JTable jt = new JTable(resultData, column);

            jt.setBounds(30, 40, 200, 300);
            JScrollPane sp = new JScrollPane(jt);

            add(sp);
            revalidate();
            repaint();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        int lines  = employeeDataManager.getRowCount();
        String resultData[][] = new String[lines][];
        int currentIndex = 0;
        List<Employee> employees = employeeDataManager.getAllEmployees();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (Employee employee : employees){
            String name = employee.getName();
            String surname = employee.getSurname();
            String email_private = employee.getEmail_private();
            String phone_private = employee.getPhone_private();
            String citizenship_1 = employee.getCitizenship_1();
            String citizenship_2 = employee.getCitizenship_2();
            String employeeNumber = employee.getEmployee_number();
            String tu_id = employee.getTu_id();
            String visa_required = String.valueOf(employee.getVisa_required());
            String status = employee.getStatus();
            String transponder_number = employee.getTransponder_number();
            String office_number = employee.getOffice_number();
            String salaryPlannedUntil = employee.getSalary_planned_until();
            Date visaExpiration = employee.getVisa_expiration();
            String phone_tuda = employee.getPhone_tuda();
            String dateOfBirth = dateFormat.format(employee.getDate_of_birth());
            String house_number = employee.getHouse_number();
            String zipCode = employee.getZip_code();
            String additionalAddress = employee.getAdditional_address();
            String city = employee.getCity();

            String[] values = {surname, name, house_number, zipCode, city, additionalAddress, email_private, phone_private,
            dateOfBirth, citizenship_1, citizenship_2, employeeNumber, tu_id, status, salaryPlannedUntil, transponder_number,
            office_number, phone_tuda};
            resultData[currentIndex] = values;
            currentIndex++;
        }
        jt = new JTable(resultData, column);

        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(jt);
        tca.adjustColumns();
        JScrollPane sp = new JScrollPane(jt);
        sp.setVisible(true);

        add(sp);
        revalidate();
        repaint();
    }

    public JTable getTable() {
        return jt;
    }
}
