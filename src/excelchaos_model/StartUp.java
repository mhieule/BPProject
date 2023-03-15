package excelchaos_model;

import excelchaos_model.database.*;
import excelchaos_model.export.CSVExporter;
import excelchaos_view.layoutmanager.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.prefs.Preferences;

public class StartUp {
    public static final String LAST_USED_DATABASE = "KeyForLastDatabasePath";
    public static final String LAST_USED_SNAPSHOT_FOLDER = "KeyForLastSnapshotFolder";

    public static final String DatabaseAlreadyChosen = "KeyForDataBaseAlreadyChosen";

    public static final String SnapShotFolderChosen = "KeyForSnapShotPathAlreadyChosen";


    public static String chooseDatabasePath() {
        UIManager.put("FileChooser.openButtonText", "Öffnen");
        UIManager.put("FileChooser.cancelButtonText", "Abbrechen");
        UIManager.put("FileChooser.saveButtonText", "Speichern");

        UIManager.put("FileChooser.cancelButtonToolTipText", "Abbrechen der Auswahl");
        UIManager.put("FileChooser.saveButtonToolTipText", "Ausgewählte Datei speichern");
        UIManager.put("FileChooser.openButtonToolTipText", "Ausgewählte Datei öffnen");
        UIManager.put("FileChooser.upFolderToolTipText", "Eine Ebene höher");
        UIManager.put("FileChooser.homeFolderToolTipText", "Home");
        UIManager.put("FileChooser.newFolderToolTipText", "Neuen Ordner erstellen");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Liste");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Details");

        UIManager.put("FileChooser.lookInLabelText", "Suchen in:");
        UIManager.put("FileChooser.fileNameLabelText", "Dateiname:");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Dateityp:");

        UIManager.put("FileChooser.acceptAllFileFilterText", "Alle Dateien (*.*)");

        Preferences prefs = Preferences.userRoot().node(CSVExporter.class.getName());
        JFileChooser databaseChooser = new JFileChooser(prefs.get(LAST_USED_DATABASE,
                new File(".").getAbsolutePath()));
        databaseChooser.setDialogTitle("Datenbank auswählen");

        if (databaseChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            prefs.put(LAST_USED_DATABASE, databaseChooser.getSelectedFile().getAbsolutePath());

            return prefs.get(LAST_USED_DATABASE,
                    new File(".").getAbsolutePath());
        } else return null;
    }

    public static boolean changeDatabasePath(){
        String newPath = chooseDatabasePath();
        if(newPath == null){
            return false;
        } else {
            ContractDataManager.setDatabaseURL(newPath);
            EmployeeDataManager.setDatabaseURL(newPath);
            ManualSalaryEntryManager.setDatabaseURL(newPath);
            SalaryIncreaseHistoryManager.setDatabaseURL(newPath);
            SalaryTableManager.setDatabaseURL(newPath);
            ProjectManager.setDatabaseURL(newPath);
            ProjectCategoryManager.setDatabaseURL(newPath);
            ProjectFunderManager.setDatabaseURL(newPath);
            ProjectParticipationManager.setDatabaseURL(newPath);
            SHKSalaryTableManager.setDatabaseURL(newPath);
            return true;
        }


    }

    public static String selectSnapshotFolder() {
        Preferences prefs = Preferences.userRoot().node(CSVExporter.class.getName());
        JFileChooser snapShotFolderChooser = new JFileChooser(prefs.get(LAST_USED_SNAPSHOT_FOLDER,
                new File(".").getAbsolutePath()));
        snapShotFolderChooser.setDialogTitle("Snapshots Speicherort festlegen");
        snapShotFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        snapShotFolderChooser.setAcceptAllFileFilterUsed(false);

        if (snapShotFolderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            prefs.put(LAST_USED_SNAPSHOT_FOLDER, snapShotFolderChooser.getSelectedFile().getAbsolutePath());
            return prefs.get(LAST_USED_SNAPSHOT_FOLDER,
                    new File(".").getAbsolutePath());
        } else return null;
    }

    private static void createSnapshotOfCurrentDatabase(String currentDBPath, String snapShotFolderPath) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm");
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedDateTime = formatter.format(localDateTime);
        String snapshotName = "Excelchaos " + formattedDateTime + ".db";
        snapShotFolderPath = snapShotFolderPath + "\\" + snapshotName;
        try {
            if (currentDBPath == null) {
                JOptionPane.showConfirmDialog(null, "Bitte starten Sie das Programm neu und wählen eine korrekte Datenbank!", "Fehler! Keine gültige Datenbank ausgewählt!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            if (snapShotFolderPath == null) {
                JOptionPane.showConfirmDialog(null, "Bitte starten Sie das Programm neu und wählen einen korrekten Snapshot Speicherort!", "Fehler! Keinen gültigen Snapshot Speicherort ausgewählt!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            Files.copy(Path.of(currentDBPath), Path.of(snapShotFolderPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createSnapshot() {
        Preferences prefs = Preferences.userRoot().node(CSVExporter.class.getName());
        String currentDBPath = prefs.get(LAST_USED_DATABASE,
                new File(".").getAbsolutePath());
        String snapShotFolderPath = prefs.get(LAST_USED_SNAPSHOT_FOLDER,
                new File(".").getAbsolutePath());
        File databaseExists = new File(currentDBPath);
        if (!databaseExists.exists() && !databaseExists.isDirectory()) {
            currentDBPath = chooseDatabasePath();
            prefs.put(DatabaseAlreadyChosen, "true");
        }
        File snapShotFolderExists = new File(snapShotFolderPath);
        if (!snapShotFolderExists.exists() && !snapShotFolderExists.isDirectory()) {
            snapShotFolderPath = selectSnapshotFolder();
            prefs.put(SnapShotFolderChosen, "true");
        }
        createSnapshotOfCurrentDatabase(currentDBPath, snapShotFolderPath);
    }


    public static void performExistingDatabaseStartUp() {
        Preferences prefs = Preferences.userRoot().node(CSVExporter.class.getName());
        String currentDBPath;

        if (!prefs.get(DatabaseAlreadyChosen, "false").equals("true")) {
            currentDBPath = chooseDatabasePath();
            prefs.put(DatabaseAlreadyChosen, "true");
        } else {
            currentDBPath = prefs.get(LAST_USED_DATABASE,
                    new File(".").getAbsolutePath());
        }

        String snapShotFolderPath;
        if (!prefs.get(SnapShotFolderChosen, "false").equals("true")) {
            snapShotFolderPath = selectSnapshotFolder();
            prefs.put(SnapShotFolderChosen, "true");
        } else {
            snapShotFolderPath = prefs.get(LAST_USED_SNAPSHOT_FOLDER,
                    new File(".").getAbsolutePath());
        }
        if (currentDBPath == null) {
            JOptionPane.showConfirmDialog(null, "Bitte starten Sie das Programm neu und wählen eine korrekte Datenbank", "Fehler! Keine gültige Datenbank ausgewählt!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        File databaseExists = new File(currentDBPath);
        if (!databaseExists.exists() && !databaseExists.isDirectory()) {
            currentDBPath = chooseDatabasePath();
            prefs.put(DatabaseAlreadyChosen, "true");
        }
        if (snapShotFolderPath == null) {
            JOptionPane.showConfirmDialog(null, "Bitte starten Sie das Programm neu und wählen einen korrekten Snapshot Speicherort!", "Fehler! Keinen gültigen Snapshot Speicherort ausgewählt!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        File snapShotFolderExists = new File(snapShotFolderPath);
        if (!snapShotFolderExists.exists() && !snapShotFolderExists.isDirectory()) {
            snapShotFolderPath = selectSnapshotFolder();
            prefs.put(SnapShotFolderChosen, "true");
        }

        if (currentDBPath == null) {
            JOptionPane.showConfirmDialog(null, "Bitte starten Sie das Programm neu und wählen eine korrekte Datenbank", "Fehler! Keine gültige Datenbank ausgewählt!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        if (snapShotFolderPath == null) {
            JOptionPane.showConfirmDialog(null, "Bitte starten Sie das Programm neu und wählen einen korrekten Snapshot Speicherort!", "Fehler! Keinen gültigen Snapshot Speicherort ausgewählt!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        //createSnapshotOfCurrentDatabase(currentDBPath, snapShotFolderPath); //TODO Wenn Snapshots durchgeführt werden sollen den Kommentar wieder entfernen.
        ContractDataManager.setDatabaseURL(currentDBPath);
        EmployeeDataManager.setDatabaseURL(currentDBPath);
        ManualSalaryEntryManager.setDatabaseURL(currentDBPath);
        SalaryIncreaseHistoryManager.setDatabaseURL(currentDBPath);
        SalaryTableManager.setDatabaseURL(currentDBPath);
        ProjectManager.setDatabaseURL(currentDBPath);
        ProjectCategoryManager.setDatabaseURL(currentDBPath);
        ProjectFunderManager.setDatabaseURL(currentDBPath);
        ProjectParticipationManager.setDatabaseURL(currentDBPath);
        SHKSalaryTableManager.setDatabaseURL(currentDBPath);
    }

    public static void performStartUpNewDatabase() {
        Preferences prefs = Preferences.userRoot().node(CSVExporter.class.getName());
        String currentDBPath;
        if (!prefs.get(DatabaseAlreadyChosen, "false").equals("true")) {
            currentDBPath = chooseDatabasePath();

            prefs.put(DatabaseAlreadyChosen, "true");
        } else {
            currentDBPath = prefs.get(LAST_USED_DATABASE,
                    new File(".").getAbsolutePath());
        }

        String snapShotFolderPath;
        if (!prefs.get(SnapShotFolderChosen, "false").equals("true")) {
            snapShotFolderPath = selectSnapshotFolder();
            prefs.put(SnapShotFolderChosen, "true");
        } else {
            snapShotFolderPath = prefs.get(LAST_USED_SNAPSHOT_FOLDER,
                    new File(".").getAbsolutePath());
        }
        if (currentDBPath == null) {
            JOptionPane.showConfirmDialog(null, "Bitte starten Sie das Programm neu und wählen eine korrekte Datenbank", "Fehler! Keine gültige Datenbank ausgewählt!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        File databaseExists = new File(currentDBPath);
        if (!databaseExists.exists() && !databaseExists.isDirectory()) {
            currentDBPath = chooseDatabasePath();
            prefs.put(DatabaseAlreadyChosen, "true");
        }
        if (snapShotFolderPath == null) {
            JOptionPane.showConfirmDialog(null, "Bitte starten Sie das Programm neu und wählen einen korrekten Snapshot Speicherort!", "Fehler! Keinen gültigen Snapshot Speicherort ausgewählt!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        File snapShotFolderExists = new File(snapShotFolderPath);
        if (!snapShotFolderExists.exists() && !snapShotFolderExists.isDirectory()) {
            snapShotFolderPath = selectSnapshotFolder();
            prefs.put(SnapShotFolderChosen, "true");
        }

        //createSnapshotOfCurrentDatabase(currentDBPath, snapShotFolderPath); //TODO Wenn Snapshots durchgeführt werden sollen den Kommentar wieder entfernen.

    }

    private static void setupDatabase(String databasePath) {
        ContractDataManager.setDatabaseURL(databasePath);
        EmployeeDataManager.setDatabaseURL(databasePath);
        ManualSalaryEntryManager.setDatabaseURL(databasePath);
        SalaryIncreaseHistoryManager.setDatabaseURL(databasePath);
        SalaryTableManager.setDatabaseURL(databasePath);
        ProjectManager.setDatabaseURL(databasePath);
        ProjectCategoryManager.setDatabaseURL(databasePath);
        ProjectFunderManager.setDatabaseURL(databasePath);
        ProjectParticipationManager.setDatabaseURL(databasePath);
        SHKSalaryTableManager.setDatabaseURL(databasePath);
    }

    public static String selectDatabasePath() {
        Preferences prefs = Preferences.userRoot().node(CSVExporter.class.getName());
        JFileChooser databaseFolderChooser = new JFileChooser(prefs.get(LAST_USED_DATABASE,
                new File(".").getAbsolutePath()));
        databaseFolderChooser.setDialogTitle("Datenbank Speicherort festlegen");
        databaseFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        databaseFolderChooser.setAcceptAllFileFilterUsed(false);

        if (databaseFolderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            prefs.put(LAST_USED_DATABASE, databaseFolderChooser.getSelectedFile().getAbsolutePath());
            return prefs.get(LAST_USED_DATABASE,
                    new File(".").getAbsolutePath());
        } else return null;
    }



    public static void showStartActionsDialog() {
        Preferences prefs = Preferences.userRoot().node(CSVExporter.class.getName());
        String[] options = {"Neue Datenbank erstellen", "Bestehende Datenbank öffnen", "Beenden"};
        int option = JOptionPane.showOptionDialog(null, "Verwaltungswerkzeug für Fachgebiete", "Parallel Programming", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        if (option == 0) {
            String folderPath = selectDatabasePath();
            if (folderPath == null || folderPath.equals("")) {
                JOptionPane.showConfirmDialog(null, "Der Dateipfad war ungültig, das Programm wird beendet.", "Es wurde kein korrekter Pfad festgelegt!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            String fileName = JOptionPane.showInputDialog(null, "Bitte benenne die Datei:", "Eingabe eines Dateinamens", JOptionPane.QUESTION_MESSAGE);
            if (fileName == null || fileName.equals("")) {
                JOptionPane.showConfirmDialog(null, "Der Dateiname war ungültig, das Programm wird beendet.", "Es wurde kein Name festgelegt!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            fileName = fileName +".db";
            String fullPathName = folderPath + "\\" + fileName;
            prefs.put(DatabaseAlreadyChosen, "true");
            prefs.put(LAST_USED_DATABASE, fullPathName);
            setupDatabase(fullPathName);
            performStartUpNewDatabase();
            return;
        }
        else if (option == 1) {
            performExistingDatabaseStartUp();
            return;
        }
        else if (option == 2) {
            System.exit(0);
        } else {
            System.exit(0);
        }


    }

}
