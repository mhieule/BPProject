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
import java.util.List;

public class InsertProjectsController implements ActionListener {
    private InsertProjectsView insertProjectsView;
    private MainFrameController frameController;

    private ProjectManager projectManager = new ProjectManager();
    private StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();

    private ProjectCategoryManager projectCategoryManager = new ProjectCategoryManager();

    private ProjectFunderManager projectFunderManager = new ProjectFunderManager();

    private ProjectParticipationManager participationManager = new ProjectParticipationManager();

    private int currentlyEditingProjectId = 0;

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
        currentlyEditingProjectId = 0;
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
        result.setRowCount(10);
        return result;
    }

    public void fillFields(String projectID) {
        Project project = projectManager.getProject(Integer.parseInt(projectID));
        currentlyEditingProjectId = project.getProject_id();
        insertProjectsView.getTfName().setText(project.getProject_name());
        Date date = project.getApproval_date();
        if (date != null) {
            insertProjectsView.getTfApproval().setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        date = project.getStart_date();
        if (date != null) {
            insertProjectsView.getTfStart().setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        date = project.getDuration();
        if (date != null) {
            insertProjectsView.getTfDuration().setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        List<ProjectCategory> projectCategoryList = projectCategoryManager.getAllProjectCategoriesForProject(project.getProject_id());

        String[][] categoryData = new String[4][projectCategoryList.size()];
        System.out.println(projectCategoryList.size());
        int categoryIndex = 0;
        for (ProjectCategory projectCategory : projectCategoryList) {
            categoryData[0][categoryIndex] = String.valueOf(projectCategory.getCategory_id());
            categoryData[1][categoryIndex] = String.valueOf(projectCategory.getProject_id());
            categoryData[2][categoryIndex] = projectCategory.getCategory_name();
            categoryData[3][categoryIndex] = transformer.formatDoubleToString(projectCategory.getApproved_funds(), 1);
            categoryIndex++;
        }


        List<ProjectFunder> projectFunderList = projectFunderManager.getAllProjectFundersForProject(project.getProject_id());

        String[][] funderData = new String[projectFunderList.size()][5];
        int funderIndex = 0;
        for (ProjectFunder projectFunder : projectFunderList) {
            funderData[funderIndex][0] = String.valueOf(projectFunder.getProject_funder_id());
            funderData[funderIndex][1] = String.valueOf(projectFunder.getProject_id());
            funderData[funderIndex][2] = projectFunder.getProject_funder_name();
            funderData[funderIndex][3] = projectFunder.getFunding_id();
            funderData[funderIndex][4] = projectFunder.getProject_number();
            funderIndex++;
        }


        List<ProjectParticipation> participationList = participationManager.getProjectParticipationByProjectID(project.getProject_id());
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String[][] participationData = new String[5][participationList.size()];
        int index = 0;
        for (ProjectParticipation projectParticipation : participationList) {
            participationData[0][index] = String.valueOf(projectParticipation.getProject_id());
            participationData[1][index] = employeeDataManager.getEmployee(projectParticipation.getPerson_id()).getSurname() + " " + employeeDataManager.getEmployee(projectParticipation.getPerson_id()).getName();
            participationData[2][index] = transformer.formatPercentageToStringForScope(projectParticipation.getScope());
            date = projectParticipation.getParticipation_period();
            participationData[3][index] = format.format(date);
            index++;
        }

        insertProjectsView.setUpEditCategoriesPanel(categoryData);
        insertProjectsView.setUpEditProjectFunderPanel(funderData);
        insertProjectsView.setUpEditProjectParticipationPanel(participationData);


        insertProjectsView.getCategoriesTable().getColumnModel().getColumn(0).setMinWidth(0);
        insertProjectsView.getCategoriesTable().getColumnModel().getColumn(0).setMaxWidth(0);
        insertProjectsView.getCategoriesTable().getColumnModel().getColumn(0).setWidth(0);
        insertProjectsView.getCategoriesTable().getColumnModel().getColumn(1).setMinWidth(0);
        insertProjectsView.getCategoriesTable().getColumnModel().getColumn(1).setMaxWidth(0);
        insertProjectsView.getCategoriesTable().getColumnModel().getColumn(1).setWidth(0);
        insertProjectsView.getProjectFunderTable().getColumnModel().getColumn(0).setMinWidth(0);
        insertProjectsView.getProjectFunderTable().getColumnModel().getColumn(0).setMaxWidth(0);
        insertProjectsView.getProjectFunderTable().getColumnModel().getColumn(0).setWidth(0);

        insertProjectsView.getProjectFunderTable().getColumnModel().getColumn(1).setMinWidth(0);
        insertProjectsView.getProjectFunderTable().getColumnModel().getColumn(1).setMaxWidth(0);
        insertProjectsView.getProjectFunderTable().getColumnModel().getColumn(1).setWidth(0);
        insertProjectsView.getProjectParticipationTable().getColumnModel().getColumn(0).setMinWidth(0);
        insertProjectsView.getProjectParticipationTable().getColumnModel().getColumn(0).setMaxWidth(0);
        insertProjectsView.getProjectParticipationTable().getColumnModel().getColumn(0).setWidth(0);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertProjectsView.getSubmitAndReset()) {

            if (currentlyEditingProjectId != 0) { //Update Fall
                Project project = projectManager.getProject(currentlyEditingProjectId);
                String name = insertProjectsView.getTfName().getText();
                LocalDate approval = insertProjectsView.getTfApproval().getDate();
                LocalDate startDate = insertProjectsView.getTfStart().getDate();
                LocalDate endLocalDate = insertProjectsView.getTfDuration().getDate();
                if (name == null || name.equals("") || approval == null || startDate == null || endLocalDate == null) {
                    insertProjectsView.markMustBeFilledTextFields();
                    JOptionPane.showConfirmDialog(null, "Bitte füllen Sie die Felder \"Projektname\", \"Bewilligungsdatum\", \"Startdatum\" und \"Enddatum\" aus.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                } else {
                    Date dateOfApproval = Date.from(approval.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date dateOfStart = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    project.setProject_name(name);
                    project.setApproval_date(dateOfApproval);
                    project.setStart_date(dateOfStart);
                    project.setDuration(endDate);

                    String[][] categoryData = getUpdatedCategoryData();
                    String[][] funderData = getUpdatedFunderData();
                    String[][] participationData = getUpdatedParticipationData();

                    for (int i = 0; i < categoryData.length; i++) {
                        if (categoryData[i][0] != null) {
                            int categoryId = Integer.parseInt(categoryData[i][0]);
                            String categoryName = categoryData[i][2];
                            double approvedFunds = transformer.formatStringToPercentageValueForScope(categoryData[i][3]);
                            ProjectCategory update = projectCategoryManager.getProject(categoryId);
                            update.setApproved_funds(approvedFunds);
                            update.setCategory_name(categoryName);
                            projectCategoryManager.updateProjectCategory(update);
                        } else {
                            String categoryName = categoryData[i][2];
                            double approvedFunds = transformer.formatStringToPercentageValueForScope(categoryData[i][3]);
                            ProjectCategory projectCategory = new ProjectCategory(currentlyEditingProjectId, projectCategoryManager.getNextID(), categoryName, approvedFunds);
                            projectCategoryManager.addProjectCategory(projectCategory);
                        }
                    }

                    for (int i = 0; i < funderData.length; i++) {
                        if (funderData[i][0] != null) {
                            int funderId = Integer.parseInt(funderData[i][0]);
                            String funderName = funderData[i][2];
                            String förderkennzeichen = funderData[i][3];
                            String projectSign = funderData[i][4];
                            ProjectFunder update = projectFunderManager.getProjectFunder(funderId);
                            update.setProject_funder_name(funderName);
                            update.setFunding_id(förderkennzeichen);
                            update.setProject_number(projectSign);
                            projectFunderManager.updateProjectFunder(update);
                        } else {
                            String funderName = funderData[i][2];
                            String förderkennzeichen = funderData[i][3];
                            String projectSign = funderData[i][4];
                            ProjectFunder projectFunder = new ProjectFunder(currentlyEditingProjectId, projectFunderManager.getNextID(), funderName, förderkennzeichen, projectSign);
                            projectFunderManager.addProjectFunder(projectFunder);
                        }
                    }
                    DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    for (int i = 0; i < participationData.length; i++) {
                        System.out.println(participationData[i][1]);
                        if(participationData[i][1] != null){
                            int employeeID = employeeDataManager.getEmployeeByName(participationData[i][1]).getId();
                            participationManager.removeProjectParticipation(currentlyEditingProjectId, employeeID);
                            double scope = transformer.formatStringToPercentageValueForScope(participationData[i][2]);
                            Date date;
                            try {
                                date = format.parse(participationData[i][3]);
                            } catch (ParseException ex) {
                                throw new RuntimeException(ex);
                            }

                            ProjectParticipation projectParticipation = new ProjectParticipation(currentlyEditingProjectId, employeeID, scope, date);
                            participationManager.addProjectParticipation(projectParticipation);
                        }

                    }

                    frameController.getShowProjectsController().updateData(frameController.getShowProjectsController().getProjectsDataFromDataBase());
                    resetInputs();


                }
            } else {


                int id = projectManager.getNextID();
                String name = insertProjectsView.getTfName().getText();


                LocalDate approval = insertProjectsView.getTfApproval().getDate();
                LocalDate startDate = insertProjectsView.getTfStart().getDate();
                LocalDate endLocalDate = insertProjectsView.getTfDuration().getDate();
                Project project = null;
                if (name == null || name.equals("") || approval == null || startDate == null || endLocalDate == null) {
                    insertProjectsView.markMustBeFilledTextFields();
                    JOptionPane.showConfirmDialog(null, "Bitte füllen Sie die Felder \"Projektname\", \"Bewilligungsdatum\", \"Startdatum\" und \"Enddatum\" aus.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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
                    frameController.getShowProjectsController().updateData(frameController.getShowProjectsController().getProjectsDataFromDataBase());
                    resetInputs();
                }


                insertProjectsView.revalidate();
                insertProjectsView.repaint();
            }
        }
        if (e.getSource() == insertProjectsView.getSubmitAndClose()) {

            if (currentlyEditingProjectId != 0) {
                Project project = projectManager.getProject(currentlyEditingProjectId);
                String name = insertProjectsView.getTfName().getText();
                LocalDate approval = insertProjectsView.getTfApproval().getDate();
                LocalDate startDate = insertProjectsView.getTfStart().getDate();
                LocalDate endLocalDate = insertProjectsView.getTfDuration().getDate();
                if (name == null || name.equals("") || approval == null || startDate == null || endLocalDate == null) {
                    insertProjectsView.markMustBeFilledTextFields();
                    JOptionPane.showConfirmDialog(null, "Bitte füllen Sie die Felder \"Projektname\", \"Bewilligungsdatum\", \"Startdatum\" und \"Enddatum\" aus.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                } else {
                    Date dateOfApproval = Date.from(approval.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date dateOfStart = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date endDate = Date.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    project.setProject_name(name);
                    project.setApproval_date(dateOfApproval);
                    project.setStart_date(dateOfStart);
                    project.setDuration(endDate);

                    String[][] categoryData = getUpdatedCategoryData();
                    String[][] funderData = getUpdatedFunderData();
                    String[][] participationData = getUpdatedParticipationData();

                    for (int i = 0; i < categoryData.length; i++) {
                        if (categoryData[i][0] != null) {
                            int categoryId = Integer.parseInt(categoryData[i][0]);
                            String categoryName = categoryData[i][2];
                            double approvedFunds = transformer.transformStringToDouble(categoryData[i][3]);
                            ProjectCategory update = projectCategoryManager.getProject(categoryId);
                            update.setApproved_funds(approvedFunds);
                            update.setCategory_name(categoryName);
                            projectCategoryManager.updateProjectCategory(update);
                        } else {
                            if (categoryData[i][2] != null && categoryData[i][3] != null) {
                                String categoryName = categoryData[i][2];
                                double approvedFunds = transformer.transformStringToDouble(categoryData[i][3]);
                                ProjectCategory projectCategory = new ProjectCategory(currentlyEditingProjectId, projectCategoryManager.getNextID(), categoryName, approvedFunds);
                                projectCategoryManager.addProjectCategory(projectCategory);
                            }
                        }
                    }

                    for (int i = 0; i < funderData.length; i++) {
                        if (funderData[i][0] != null) {
                            int funderId = Integer.parseInt(funderData[i][0]);
                            String funderName = funderData[i][2];
                            String förderkennzeichen = funderData[i][3];
                            String projectSign = funderData[i][4];
                            ProjectFunder update = projectFunderManager.getProjectFunder(funderId);
                            update.setProject_funder_name(funderName);
                            update.setFunding_id(förderkennzeichen);
                            update.setProject_number(projectSign);
                            projectFunderManager.updateProjectFunder(update);
                        } else {
                            String funderName = funderData[i][2];
                            String förderkennzeichen = funderData[i][3];
                            String projectSign = funderData[i][4];
                            ProjectFunder projectFunder = new ProjectFunder(currentlyEditingProjectId, projectFunderManager.getNextID(), funderName, förderkennzeichen, projectSign);
                            projectFunderManager.addProjectFunder(projectFunder);
                        }
                    }
                    DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                    for (int i = 0; i < participationData.length; i++) {
                        System.out.println(participationData[i][1]);
                        if(participationData[i][1] != null){
                            int employeeID = employeeDataManager.getEmployeeByName(participationData[i][1]).getId();
                            participationManager.removeProjectParticipation(currentlyEditingProjectId, employeeID);
                            double scope = transformer.formatStringToPercentageValueForScope(participationData[i][2]);
                            Date date;
                            try {
                                date = format.parse(participationData[i][3]);
                            } catch (ParseException ex) {
                                throw new RuntimeException(ex);
                            }

                            ProjectParticipation projectParticipation = new ProjectParticipation(currentlyEditingProjectId, employeeID, scope, date);
                            participationManager.addProjectParticipation(projectParticipation);
                        }

                    }

                    frameController.getShowProjectsController().updateData(frameController.getShowProjectsController().getProjectsDataFromDataBase());
                    frameController.getTabs().removeTabNewWindow(insertProjectsView);


                }
            } else {


                ProjectManager projectManager = new ProjectManager();

                int id = projectManager.getNextID();
                String name = insertProjectsView.getTfName().getText();


                LocalDate approval = insertProjectsView.getTfApproval().getDate();
                LocalDate startDate = insertProjectsView.getTfStart().getDate();
                LocalDate endLocalDate = insertProjectsView.getTfDuration().getDate();
                Project project = null;

                if (name == null || name.equals("") || approval == null || startDate == null || endLocalDate == null) {
                    insertProjectsView.markMustBeFilledTextFields();
                    JOptionPane.showConfirmDialog(null, "Bitte füllen Sie die Spalten \"Projektname\", \"Bewilligungsdatum\", \"Startdatum\" und \"Enddatum\" aus.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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
                    frameController.getShowProjectsController().updateData(frameController.getShowProjectsController().getProjectsDataFromDataBase());
                    frameController.getTabs().removeTabNewWindow(insertProjectsView);
                }
            }

        }
        if (e.getSource() == insertProjectsView.getReset()) {
            resetInputs();
        }
        if (e.getSource() == insertProjectsView.getCancel()) {
            frameController.getTabs().removeTabNewWindow(insertProjectsView);
        }
    }

    private String[][] getUpdatedCategoryData() {
        String[][] tableValues = getUpdatedCategoryTableValues(insertProjectsView.getCategoriesTable());
        return tableValues;
    }

    private String[][] getUpdatedFunderData() {
        String[][] tableValues = getUpdatedFunderTableValues(insertProjectsView.getProjectFunderTable());
        return tableValues;
    }

    private String[][] getUpdatedParticipationData() {
        String[][] tableValues = getUpdatedParticipationTableValues(insertProjectsView.getProjectParticipationTable());
        return tableValues;
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
                projectParticipation = new ProjectParticipation(projectId, personId, transformer.formatStringToPercentageValueForScope(tableValues[row][1]), date);
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
                    LocalDate temporaryDate = (LocalDate) participationTable.getValueAt(row, column);
                    String temporaryString = temporaryDate.format(dateTimeFormatter);
                    tableValues[row][column] = temporaryString;
                } else {
                    tableValues[row][column] = (String) participationTable.getValueAt(row, column);
                }
            }
        }
        return tableValues;
    }

    private String[][] getUpdatedParticipationTableValues(JTable participationTable) {
        String[][] tableValues = new String[participationTable.getRowCount()][participationTable.getColumnCount()];
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (int row = 0; row < participationTable.getRowCount(); row++) {
            for (int column = 0; column < participationTable.getColumnCount(); column++) {
                if (participationTable.getValueAt(row, column) == null || participationTable.getValueAt(row, column).equals("")) {
                    break;
                }
                if (column == 3) {
                    LocalDate temporaryDate = (LocalDate) participationTable.getValueAt(row, column);
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

    private String[][] getUpdatedFunderTableValues(JTable givenTable) {
        String[][] tableValues = new String[givenTable.getRowCount()][givenTable.getColumnCount()];
        for (int row = 0; row < givenTable.getRowCount(); row++) {
            for (int column = 0; column < givenTable.getColumnCount(); column++) {
                if (givenTable.getValueAt(row, 2) == null || givenTable.getValueAt(row, 2).equals("") || givenTable.getValueAt(row, 3) == null || givenTable.getValueAt(row, 3).equals("")
                        || givenTable.getValueAt(row, 4) == null || givenTable.getValueAt(row, 4).equals("")) {
                    break;
                }
                tableValues[row][column] = (String) givenTable.getValueAt(row, column);
            }
        }
        return tableValues;
    }

    private String[][] getUpdatedCategoryTableValues(JTable givenTable) {
        String[][] tableValues = new String[givenTable.getRowCount()][givenTable.getColumnCount()];
        for (int row = 0; row < givenTable.getRowCount(); row++) {
            for (int column = 0; column < givenTable.getColumnCount(); column++) {
                if (givenTable.getValueAt(row, column) == null || givenTable.getValueAt(row, column).equals("")) {
                    if (column == 2) {
                        break;
                    }
                    if (column == 3) {
                        break;
                    }

                }
                System.out.println(givenTable.getValueAt(row, column) + "Here");
                tableValues[row][column] = (String) givenTable.getValueAt(row, column);
            }
        }
        return tableValues;
    }

}
