package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.InsertProjectsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class InsertProjectsController implements ActionListener {
    private InsertProjectsView insertProjectsView;
    private MainFrameController frameController;

    private String addProjectsTab = "Projekt hinzufügen";

    public InsertProjectsController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        insertProjectsView = new InsertProjectsView();
        insertProjectsView.init();
        insertProjectsView.setActionListener(this);

    }

    public InsertProjectsView getInsertProjectsView() {
        return insertProjectsView;
    }

    public void showInsertProjectsView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(addProjectsTab) == -1) {
            mainFrameController.getTabs().addTab(addProjectsTab, insertProjectsView);
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addProjectsTab));
        } else
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(addProjectsTab));
    }


    public void resetInputs() {
        insertProjectsView.getTfName().setText(null);
        insertProjectsView.getTfApproval().setText(null);
        insertProjectsView.getTfDuration().setText(null);
        insertProjectsView.getTfStart().setText(null);
        insertProjectsView.getCategoriesTable().setModel(resetTable(insertProjectsView.getCategoryColumns()));
        insertProjectsView.getProjectFunderTable().setModel(resetTable(insertProjectsView.getFunderColumns()));
        insertProjectsView.getProjectParticipationTable().setModel(resetTable(insertProjectsView.getParticipationColumns()));
        insertProjectsView.setUpNameSelection(insertProjectsView.getProjectParticipationTable());
        insertProjectsView.setUpDateSelection(insertProjectsView.getProjectParticipationTable());
    }

    public DefaultTableModel resetTable(String[] newColumns) {
        DefaultTableModel result = new DefaultTableModel(null, newColumns);
        result.setRowCount(40);
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertProjectsView.getSubmitAndReset()) {
            ProjectManager projectManager = new ProjectManager();

            int id = projectManager.getNextID();
            String name = insertProjectsView.getTfName().getText();


            LocalDate approval = insertProjectsView.getTfApproval().getDate();
            LocalDate startDate = insertProjectsView.getTfStart().getDate();
            LocalDate endLocalDate = insertProjectsView.getTfDuration().getDate();
            Project project = null;
            if(name == null || name.equals("") || approval == null || startDate == null|| endLocalDate == null){
                JOptionPane.showConfirmDialog(null,"Bitte füllen Sie die Felder \"Projektname\", \"Bewilligungsdatum\", \"Startdatum\" und \"Enddatum\" aus.","Spalten nicht vollständig ausgefüllt",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
           } else {
                Date dateOfApproval = Date.from(approval.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date dateOfStart = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                project = new Project(id, name, dateOfStart, dateOfApproval, endDate);
                projectManager.addProject(project);
                insertCategoryValuesDB(project.getProject_id());
                insertFunderValuesDB(project.getProject_id());
                try {
                    insertParticipationValuesDB(project.getProject_id());
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                frameController.getShowProjectsController().updateData();
                resetInputs();
            }


            insertProjectsView.revalidate();
            insertProjectsView.repaint();

        }
        if(e.getSource() == insertProjectsView.getSubmitAndClose()){
            ProjectManager projectManager = new ProjectManager();

            int id = projectManager.getNextID();
            String name = insertProjectsView.getTfName().getText();


            LocalDate approval = insertProjectsView.getTfApproval().getDate();
            LocalDate startDate = insertProjectsView.getTfStart().getDate();
            LocalDate endLocalDate = insertProjectsView.getTfDuration().getDate();
            Project project = null;

            if(name == null || name.equals("") || approval == null || startDate == null|| endLocalDate == null){
                JOptionPane.showConfirmDialog(null,"Bitte füllen Sie die Spalten \"Projektname\", \"Bewilligungsdatum\", \"Startdatum\" und \"Enddatum\" aus.","Spalten nicht vollständig ausgefüllt",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
            } else {
                Date dateOfApproval = Date.from(approval.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date dateOfStart = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                project = new Project(id, name, dateOfStart, dateOfApproval, endDate);
                projectManager.addProject(project);
                insertCategoryValuesDB(project.getProject_id());
                insertFunderValuesDB(project.getProject_id());
                try {
                    insertParticipationValuesDB(project.getProject_id());
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                frameController.getShowProjectsController().updateData();
                frameController.getTabs().removeTabNewWindow(insertProjectsView);
            }

        }
        if (e.getSource() == insertProjectsView.getReset()) {
            resetInputs();
        }
        if (e.getSource() == insertProjectsView.getCancel()) {
            frameController.getTabs().removeTabNewWindow(insertProjectsView);
        }
    }

    private void insertCategoryValuesDB(int projectId) {
        ProjectCategoryManager projectCategoryManager = new ProjectCategoryManager();
        JTable categoriesTable = insertProjectsView.getCategoriesTable();
        String[][] tableValues = getTableValues(categoriesTable);
        ProjectCategory projectCategory;
        StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
        for (int row = 0; row < tableValues.length; row++) {
            if (tableValues[row][0] != null && tableValues[row][1] != null) {
                projectCategory = new ProjectCategory(projectId, projectCategoryManager.getNextID(), tableValues[row][0], transformer.transformStringToDouble(tableValues[row][1]));
                projectCategoryManager.addProjectCategory(projectCategory);
            }

        }
    }

    private void insertFunderValuesDB(int projectId) {
        ProjectFunderManager projectFunderManager = new ProjectFunderManager();
        JTable funderTable = insertProjectsView.getProjectFunderTable();
        String[][] tableValues = getTableValues(funderTable);
        ProjectFunder projectFunder;
        for (int row = 0; row < tableValues.length; row++) {
            if (tableValues[row][0] != null && tableValues[row][1] != null && tableValues[row][2] != null) {
                projectFunder = new ProjectFunder(projectId, projectFunderManager.getNextID(), tableValues[row][0], tableValues[row][1], tableValues[row][2]);
                projectFunderManager.addProjectFunder(projectFunder);
            }

        }
    }

    //TODO Wenn nur für einen Monat der Beschäftigungsumfang eingetragen wurde, dann erstelle automatisch für alle weiteren. Wenn mehrere dann erstelle wenn welche fehlen für den letzten Monat die verbleibenden Einträge
    private void insertParticipationValuesDB(int projectId) throws ParseException {
        ProjectParticipationManager projectParticipationManager = new ProjectParticipationManager();
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        JTable participationTable = insertProjectsView.getProjectParticipationTable();
        String[][] tableValues = getParticipationTableValues(participationTable);
        ProjectParticipation projectParticipation;
        StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        for (int row = 0; row < tableValues.length; row++) {  //TODO Umwandlungsmethoden für SHK Angestellte implementieren
            if (tableValues[row][0] != null && tableValues[row][1] != null && tableValues[row][2] != null) {
                int personId = employeeDataManager.getEmployeeByName(tableValues[row][0]).getId();
                Date date = format.parse(tableValues[row][2]);
                projectParticipation = new ProjectParticipation(projectId, personId, transformer.formatStringToPercentageValueForScope(tableValues[row][1]),date);
                projectParticipationManager.addProjectParticipation(projectParticipation);
            }

        }
    }

    private String[][] getParticipationTableValues(JTable participationTable) {
        String[][] tableValues = new String[participationTable.getRowCount()][participationTable.getColumnCount()];
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (int row = 0; row < participationTable.getRowCount(); row++) {
            for (int column = 0; column < participationTable.getColumnCount(); column++) {
                if (participationTable.getValueAt(row, column) == null || participationTable.getValueAt(row, column).equals("")) {
                    break;
                }
                if (column == 2) {
                    LocalDate temporaryDate = (LocalDate) participationTable.getValueAt(row,column);
                    String temporaryString = temporaryDate.format(dateTimeFormatter);
                    tableValues[row][column] = temporaryString;
                } else {
                    tableValues[row][column] = (String) participationTable.getValueAt(row, column);
                }
            }
        }
        return tableValues;
    }

    private String[][] getTableValues(JTable givenTable) {
        String[][] tableValues = new String[givenTable.getRowCount()][givenTable.getColumnCount()];
        for (int row = 0; row < givenTable.getRowCount(); row++) {
            for (int column = 0; column < givenTable.getColumnCount(); column++) {
                if (givenTable.getValueAt(row, column) == null || givenTable.getValueAt(row, column).equals("")) {
                    break;
                }
                tableValues[row][column] = (String) givenTable.getValueAt(row, column);
            }
        }
        return tableValues;
    }
}
