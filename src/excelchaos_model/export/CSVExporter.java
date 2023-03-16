package excelchaos_model.export;

import excelchaos_model.calculations.SalaryCalculation;
import excelchaos_model.database.Contract;
import excelchaos_model.database.Employee;
import excelchaos_model.datamodel.employeedataoperations.EmployeeDataAccess;
import excelchaos_model.utility.StringAndBigDecimalFormatter;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
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

    /**
     * Writes the data of the specified JTable to a CSV file at the given path.
     *
     * @param tableToExport  The JTable to export to CSV
     * @param pathToExportTo The file path to export the CSV file to
     * @return true if the CSV file is successfully written, false otherwise
     */
    private static boolean writeToCSV(JTable tableToExport,
                                      String pathToExportTo) {

        try {
            TableModel model = tableToExport.getModel();
            File csvFile = new File(pathToExportTo);
            BufferedWriter csv = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8));
            csv.write("\ufeff");
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


    /**
     * Creates a CSV file containing the data from the given JTable and saves it to the user-selected directory.
     * The file name is specified by the fileName parameter. If the user cancels the file selection dialog, the method does nothing.
     *
     * @param tableToExport The JTable containing the data to be exported to the CSV file.
     * @param fileName      The desired name of the CSV file to be created.
     */
    public static void createCSV(JTable tableToExport, String fileName) {
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
            writeToCSV(tableToExport, fullPathName);
        }
    }

    /**
     * Prompts the user to choose a directory to save a CSV file, and then allows the user to input a filename.
     * If the user inputs a valid filename, the method will create a CSV file with the given filename and save it in the selected directory.
     * If the user cancels either the directory selection or the filename input, the method returns without doing anything.
     *
     * @param tableToExport the JTable to be exported as a CSV file
     */
    public static void createCSVVariableName(JTable tableToExport) {

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
            }
            fileName = fileName + ".csv";
            String fullPathName = prefs.get(LAST_USED_FOLDER,
                    new File(".").getAbsolutePath()) + "\\" + fileName;
            writeToCSV(tableToExport, fullPathName);

        }
    }

    /**
     * Creates a CSV file containing salary projection information for employees.
     * The user is prompted to select a directory and enter a file name for the new CSV file.
     * The salary projection data is obtained from the database and written to the CSV file.
     * The file is saved to the selected directory with the specified file name.
     * If the user cancels the file selection or enters an empty file name, no file is created.
     */
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
            }
            fileName = fileName + ".csv";
            String fullPathName = prefs.get(LAST_USED_FOLDER,
                    new File(".").getAbsolutePath()) + "\\" + fileName;
            writeSalaryProjectionCSV(fullPathName);
        }
    }

    /**
     * Generates a CSV file with the projected salary costs for all employees over a period of 6 years and writes it to the specified path.
     * The file includes the employee name, month, salary costs, and employment scope.
     *
     * @param pathToExportTo the file path to export the CSV file to
     * @throws RuntimeException if the file is not found or an IO error occurs
     */
    private static void writeSalaryProjectionCSV(String pathToExportTo) {
        EmployeeDataAccess employeeDataAccess = new EmployeeDataAccess();
        List<Employee> allEmployees = employeeDataAccess.getAllEmployees();
        SalaryCalculation salaryCalculation = new SalaryCalculation();
        DateFormat format = new SimpleDateFormat("MMMM-yyyy");
        File csvFile = new File(pathToExportTo);
        try {
            BufferedWriter csv = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8));
            csv.write("\ufeff");
            csv.write("Name" + ";");
            csv.write("Monat" + ";");
            csv.write("Gehaltskosten" + ";");
            csv.write("Beschäftigungsumfang" + ";");
            csv.write("\n");

            for (Employee employee : allEmployees) {
                Contract contract = employeeDataAccess.getContract(employee.getId());
                LocalDate startDate = contract.getStart_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endDate = startDate.plusYears(6);
                String name = employee.getSurname() + " " + employee.getName();
                for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusMonths(1)) {
                    csv.write(name + ";");
                    csv.write(format.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())) + ";");
                    if (employee.getStatus().equals("SHK")) {
                        csv.write(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(salaryCalculation.projectSalaryToGivenMonth(employee.getId(), Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))) + ";");
                        csv.write(StringAndBigDecimalFormatter.formatBigDecimalToHours(contract.getScope()) + ";");
                    } else {
                        csv.write(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(salaryCalculation.projectSalaryToGivenMonth(employee.getId(), Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))) + ";");
                        csv.write(StringAndBigDecimalFormatter.formatPercentageToStringForScope(contract.getScope()) + ";");
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
