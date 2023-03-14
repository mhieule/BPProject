package excelchaos_model.export;

import excelchaos_model.calculations.NewAndImprovedSalaryCalculation;
import excelchaos_model.database.Contract;
import excelchaos_model.database.Employee;
import excelchaos_model.datamodel.employeedataoperations.EmployeeDataAccess;
import excelchaos_model.utility.StringAndBigDecimalFormatter;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

public class CSVExporter {
    public static final String LAST_USED_FOLDER = "KeyForLastPath";

    private static boolean writeToCSV(JTable tableToExport,
                                      String pathToExportTo) {

        try {
            TableModel model = tableToExport.getModel();
            BufferedWriter csv = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToExportTo), StandardCharsets.UTF_8));
            for (int i = 0; i < model.getColumnCount(); i++) {
                if (i == 0 || i == 1) {
                } else {
                    csv.write(model.getColumnName(i) + ";");
                }

            }

            csv.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    if (j == 0 || j == 1) {
                    } else if (model.getValueAt(i, j) == null) {
                        csv.write(";");
                    } else if (((String) model.getValueAt(i, j)).contains("€")) {
                        String editString = (String) model.getValueAt(i, j);
                        editString = editString.replaceAll("€", "");
                        csv.write(editString + ";");
                    } else {
                        csv.write(model.getValueAt(i, j).toString() + ";");
                    }

                }
                csv.write("\n");
            }

            csv.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void createCSV(JTable tableToExport, String fileName) {
        //TODO Filechooser Deutsche Labels geben


        Preferences prefs = Preferences.userRoot().node(CSVExporter.class.getName());
        JFileChooser chooser = new JFileChooser(prefs.get(LAST_USED_FOLDER,
                new File(".").getAbsolutePath()));
        chooser.setDialogTitle("Öffnen");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            prefs.put(LAST_USED_FOLDER, chooser.getSelectedFile().getAbsolutePath());

            String fullPathName = prefs.get(LAST_USED_FOLDER,
                    new File(".").getAbsolutePath()) + "\\" + fileName;
            System.out.println(fullPathName);
            writeToCSV(tableToExport, fullPathName);

        }
    }

    public static void createCSVVariableName(JTable tableToExport) {
        //TODO Filechooser Deutsche Labels geben

        Preferences prefs = Preferences.userRoot().node(CSVExporter.class.getName());
        JFileChooser chooser = new JFileChooser(prefs.get(LAST_USED_FOLDER,
                new File(".").getAbsolutePath()));
        chooser.setDialogTitle("Öffnen");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            prefs.put(LAST_USED_FOLDER, chooser.getSelectedFile().getAbsolutePath());
            String fileName = JOptionPane.showInputDialog(null, "Bitte benenne die Datei:", "Eingabe eines Dateinamens", JOptionPane.QUESTION_MESSAGE);
            if (fileName == null || fileName.equals("")) {
                return;
            } //TODO Was passiert wenn auf cancel gedrückt wird?
            fileName = fileName + ".csv";
            String fullPathName = prefs.get(LAST_USED_FOLDER,
                    new File(".").getAbsolutePath()) + "\\" + fileName;
            writeToCSV(tableToExport, fullPathName);

        }
    }

    public static void createCSVSalaryProjection() {
        Preferences prefs = Preferences.userRoot().node(CSVExporter.class.getName());
        JFileChooser chooser = new JFileChooser(prefs.get(LAST_USED_FOLDER,
                new File(".").getAbsolutePath()));
        chooser.setDialogTitle("Öffnen");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            prefs.put(LAST_USED_FOLDER, chooser.getSelectedFile().getAbsolutePath());
            String fileName = JOptionPane.showInputDialog(null, "Bitte benenne die Datei:", "Eingabe eines Dateinamens", JOptionPane.QUESTION_MESSAGE);
            if (fileName == null || fileName.equals("")) {
                return;
            } //TODO Was passiert wenn auf cancel gedrückt wird?
            fileName = fileName + ".csv";
            String fullPathName = prefs.get(LAST_USED_FOLDER,
                    new File(".").getAbsolutePath()) + "\\" + fileName;
            writeSalaryProjectionCSV(fullPathName);
        }
    }

    private static void writeSalaryProjectionCSV(String pathToExportTo) {
        EmployeeDataAccess employeeDataAccess = new EmployeeDataAccess();
        List<Employee> allEmployees = employeeDataAccess.getAllEmployees();
        NewAndImprovedSalaryCalculation salaryCalculation = new NewAndImprovedSalaryCalculation();
        DateFormat format = new SimpleDateFormat("MMMM-yyyy");
        try {
            BufferedWriter csv = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToExportTo), StandardCharsets.ISO_8859_1));
            csv.write("Name" + ";");
            csv.write("Monat" + ";");
            csv.write("Gehaltskosten" + ";");
            csv.write("Beschäftigungsumfang" + ";");
            csv.write("\n");

            for (Employee employee : allEmployees){
                Contract contract = employeeDataAccess.getContract(employee.getId());
                LocalDate startDate = contract.getStart_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endDate = contract.getEnd_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String name = employee.getSurname() + " " + employee.getName();
                for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusMonths(1)){
                    csv.write(name + ";");
                    csv.write(format.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))+";");
                    if(employee.getStatus().equals("SHK")){
                        csv.write(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(salaryCalculation.projectSalaryToGivenMonth(employee.getId(), Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())))+";");
                        csv.write(StringAndBigDecimalFormatter.formatBigDecimalToHours(contract.getScope())+";");
                    } else {
                        csv.write(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(salaryCalculation.projectSalaryToGivenMonth(employee.getId(), Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())))+";");
                        csv.write(StringAndBigDecimalFormatter.formatPercentageToStringForScope(contract.getScope())+";");
                    }

                    csv.write("\n");
                }
            }
            csv.close();
            JOptionPane.showConfirmDialog(null, "Die Datei wurde erfolgreich erstellt!", "Aktion war erfolgreich!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
