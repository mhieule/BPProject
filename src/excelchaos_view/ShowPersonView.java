package excelchaos_view;

import excelchaos_model.*;

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
    private CustomTable jt;
    public void init() {

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
        addData();
    }

    public void addData(){
        removeAll();
        setLayout(new BorderLayout());
        String columns[] = {"Name", "Vorname","Straße","Haunsummer","Adresszusatz", "Postleitzahl","Stadt",
                "Geburtsdatum","E-Mail Privat", "Telefon Privat", "Telefon TUDA",  "Staatsangehörigkeit 1", "Staatsangehörigkeit 2", "Visum Gültigkeit", "Personalnummer", "Transpondernummer", "Büronummer", "TU-ID",
                "Anstellungsart","Beschäftigungsbeginn","Beschäftigungsende", "Beschäftigungsumfgang","Gehaltsklasse","Gehaltsstufe","VBL-Status","SHK Stundensatz", "Gehalt Eingeplant bis"
        };
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        ContractDataManager contractDataManager = new ContractDataManager();
        int lines  = employeeDataManager.getRowCount();
        String resultData[][] = new String[lines][];
        int currentIndex = 0;
//        employeeDataManager.removeAllEmployees();
//        contractDataManager.removeAllContracts();
        List<Employee> employees = employeeDataManager.getAllEmployees();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (Employee employee : employees){
            Contract contract = contractDataManager.getContract(employee.getId());
            String name = employee.getName();
            String surname = employee.getSurname();
            //TODO füllen wenn vorhanden
            String street = null;
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
            //TODO in Date umwandeln wenn in DB
            Date startDate = contract.getStart_date();
            Date endDate = contract.getEnd_date();
            //TODO füllen wenn vorhanden (contract)
            String extend = String.valueOf(contract.getScope()); //arbeitsumfang
            String payGrade = contract.getPaygrade();
            String payLevel = contract.getPaylevel();
            //TODO füllen wenn vorhanden (contract)
            String vblStatus = String.valueOf(contract.getVbl_status());
            String shkHourlyRate = contract.getShk_hourly_rate();
            String salaryPlannedUntil = employee.getSalary_planned_until();

            String[] values = {name, surname, street, houseNumber, zipCode, city, additionalAddress, emailPrivate, phonePrivate, phoneTuda,
                    dateOfBirth, citizenship1, citizenship2, visaExpiration, employeeNumber, transponderNumber, officeNumber, tuId, status, startDate.toString(), endDate.toString(), extend, payGrade, payLevel, vblStatus, shkHourlyRate, salaryPlannedUntil};
            resultData[currentIndex] = values;
            currentIndex++;
        }
        jt = new CustomTable(resultData, columns);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(jt);
        tca.adjustColumns();
        JScrollPane sp = new JScrollPane(jt);
        sp.setVisible(true);

        add(sp);
        revalidate();
        repaint();
    }

    public CustomTable getTable() {
        return jt;
    }
}
