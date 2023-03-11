package excelchaos_model.export;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.util.prefs.Preferences;

public class CSVExporter {
    public static final String LAST_USED_FOLDER = "KeyForLastPath";

    //TODO Genau absprechen welche Formate hier und dann in Excel benötigt werden
    private static boolean writeToCSV(JTable tableToExport,
                                      String pathToExportTo) {

        try {

            TableModel model = tableToExport.getModel();
            BufferedWriter csv = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathToExportTo), "ISO-8859-1")); //TODO Ändern

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
            if (fileName.equals("") || fileName == null) {
                return;
            }
            fileName = fileName + ".csv";
            String fullPathName = prefs.get(LAST_USED_FOLDER,
                    new File(".").getAbsolutePath()) + "\\" + fileName;
            System.out.println(fullPathName);
            writeToCSV(tableToExport, fullPathName);

        }
    }


}
