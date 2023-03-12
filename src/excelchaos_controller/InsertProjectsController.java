package excelchaos_controller;

import excelchaos_model.database.*;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.InsertProjectsView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class InsertProjectsController implements ActionListener, TableModelListener {
    private ProjectManager projectManager = ProjectManager.getInstance();
    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();

    private ProjectCategoryManager projectCategoryManager = ProjectCategoryManager.getInstance();

    private ProjectFunderManager projectFunderManager = ProjectFunderManager.getInstance();

    private ProjectParticipationManager projectParticipationManager = ProjectParticipationManager.getInstance();
    private InsertProjectsView insertProjectsView;
    private MainFrameController frameController;


    private int currentlyEditingProjectId = 0;

    private String addProjectsTab = "Projekt hinzufügen";

    public InsertProjectsController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        insertProjectsView = new InsertProjectsView();
        insertProjectsView.init();
        insertProjectsView.setActionListener(this);
        insertProjectsView.getCategoriesTable().getModel().addTableModelListener(this);

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
        insertProjectsView.getCategoriesTable().setModel(resetCategoriesTable(insertProjectsView.getCategoryColumns()));
        insertProjectsView.getCategoriesSum().setText("Summe: ");
        insertProjectsView.getProjectFunderTable().setModel(resetFunderTable(insertProjectsView.getFunderColumns()));
        insertProjectsView.getProjectParticipationTable().setModel(resetPaticipationTable(insertProjectsView.getParticipationColumns()));
        insertProjectsView.setUpNameSelection(insertProjectsView.getProjectParticipationTable());
        insertProjectsView.setUpDateSelection(insertProjectsView.getProjectParticipationTable());
        insertProjectsView.getCategoriesTable().getModel().addTableModelListener(this);

    }

    public DefaultTableModel resetPaticipationTable(String[] newColumns) {
        DefaultTableModel result = new DefaultTableModel(null, newColumns);
        result.setRowCount(10);
        return result;
    }

    private DefaultTableModel resetCategoriesTable(String[] newColumns){
        String[][] categoriesData = new String[4][2];
        categoriesData[0][0] = "WiMi";
        categoriesData[1][0] = "HiWi";
        categoriesData[2][0] = "Reise Inland";
        categoriesData[3][0] = "Reise Ausland";
        DefaultTableModel result = new DefaultTableModel(categoriesData,newColumns);
        return result;
    }

    private DefaultTableModel resetFunderTable(String[] newColumns){
        DefaultTableModel result = new DefaultTableModel(null,newColumns);
        result.setRowCount(1);
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

        String[][] categoryData = new String[projectCategoryList.size()][4];
        int categoryIndex = 0;
        BigDecimal categoriesSum = new BigDecimal(0);
        for (ProjectCategory projectCategory : projectCategoryList) {
            categoryData[categoryIndex][0] = String.valueOf(projectCategory.getCategory_id());
            categoryData[categoryIndex][1] = String.valueOf(projectCategory.getProject_id());
            categoryData[categoryIndex][2] = projectCategory.getCategory_name();
            categoryData[categoryIndex][3] = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(projectCategory.getApproved_funds());
            categoriesSum = categoriesSum.add(projectCategory.getApproved_funds());
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


        List<ProjectParticipation> participationList = projectParticipationManager.getProjectParticipationByProjectID(project.getProject_id());
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String changedEmployee = "";
        int arraySize = 0;
        HashSet<Integer> employeeIds = new HashSet<>();
        BigDecimal oldScope = new BigDecimal(0);
        for (ProjectParticipation projectParticipation : participationList) {
            String name = employeeDataManager.getEmployee(projectParticipation.getPerson_id()).getSurname() + " " + employeeDataManager.getEmployee(projectParticipation.getPerson_id()).getName();
            BigDecimal scope = projectParticipation.getScope();
            employeeIds.add(projectParticipation.getPerson_id());
            if (!name.equals(changedEmployee)) {
                changedEmployee = name;
                oldScope = projectParticipation.getScope();
                arraySize++;
            } else if (oldScope.compareTo(scope) != 0) {
                if (!(scope.compareTo(new BigDecimal(0)) == 0)) {
                    oldScope = scope;
                    arraySize++;
                }
            }
        }

        String[][] participationData = new String[5][arraySize];

        Integer[] employeeIDs = new Integer[employeeIds.size()];
        int i = 0;
        for (Integer id : employeeIds) {
            employeeIDs[i++] = id;
        }
        int index = 0;
        oldScope = new BigDecimal(0);
        Date lastCheckedDate = null;
        for (int j = 0; j < employeeIDs.length; j++) {
            int counter = 0;
            List<ProjectParticipation> employeeParticipationList = projectParticipationManager.getProjectParticipationByProjectIDAndPersonID(Integer.parseInt(projectID), employeeIDs[j]);
            for (ProjectParticipation participation : employeeParticipationList) {
                String name = employeeDataManager.getEmployee(participation.getPerson_id()).getSurname() + " " + employeeDataManager.getEmployee(participation.getPerson_id()).getName();
                if (counter == 0) {
                    participationData[0][index] = String.valueOf(participation.getProject_id());
                    participationData[1][index] = name;
                    participationData[2][index] = StringAndBigDecimalFormatter.formatPercentageToStringForScope(participation.getScope());
                    Date participationStart = participation.getParticipation_period();
                    participationData[3][index] = format.format(participationStart);
                    oldScope = participation.getScope();
                }
                if (oldScope.compareTo(participation.getScope()) != 0) {
                    participationData[4][index] = format.format(lastCheckedDate);
                    if (index + 1 < arraySize) {
                        index++;
                        participationData[0][index] = String.valueOf(participation.getProject_id());
                        participationData[1][index] = name;
                        participationData[2][index] = StringAndBigDecimalFormatter.formatPercentageToStringForScope(participation.getScope());
                        Date participationStart = participation.getParticipation_period();
                        participationData[3][index] = format.format(participationStart);
                        oldScope = participation.getScope();
                    }
                }


                if (counter == employeeParticipationList.size() - 1) {
                    participationData[4][index] = format.format(participation.getParticipation_period());
                }
                lastCheckedDate = participation.getParticipation_period();
                counter++;

            }
            index++;
        }

        insertProjectsView.setUpEditCategoriesPanel(categoryData);
        insertProjectsView.getCategoriesSum().setText("Summe: " + StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(categoriesSum));
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

        insertProjectsView.getCategoriesTable().getModel().addTableModelListener(this);
    }


    private void updateProjectDataInDatabase(Project project, String name, LocalDate approval, LocalDate startDate, LocalDate endLocalDate) {
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
                BigDecimal approvedFunds = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(categoryData[i][3]);
                ProjectCategory update = projectCategoryManager.getProject(categoryId);
                update.setApproved_funds(approvedFunds);
                update.setCategory_name(categoryName);
                projectCategoryManager.updateProjectCategory(update);
            } else {
                if (categoryData[i][2] != null && categoryData[i][3] != null) {
                    String categoryName = categoryData[i][2];
                    BigDecimal approvedFunds = StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(categoryData[i][3]);
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
                if (funderData[i][2] != null && funderData[i][3] != null && funderData[i][4] != null) {
                    String funderName = funderData[i][2];
                    String förderkennzeichen = funderData[i][3];
                    String projectSign = funderData[i][4];
                    ProjectFunder projectFunder = new ProjectFunder(currentlyEditingProjectId, projectFunderManager.getNextID(), funderName, förderkennzeichen, projectSign);
                    projectFunderManager.addProjectFunder(projectFunder);
                }
            }
        }

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        for (int i = 0; i < participationData.length; i++) {
            if (participationData[i][1] != null && participationData[i][2] != null && participationData[i][3] != null && participationData[i][4] != null) {
                int employeeID = employeeDataManager.getEmployeeByName(participationData[i][1]).getId();
                projectParticipationManager.removeProjectParticipation(currentlyEditingProjectId, employeeID);
                Date participationStartDate;
                Date participationEndDate;
                try {
                    participationStartDate = format.parse(participationData[i][3]);
                    participationEndDate = format.parse(participationData[i][4]);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                LocalDate participationLocalStartDate = participationStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate participationLocalEndDate = participationEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                for (LocalDate date = participationLocalStartDate; date.isBefore(participationLocalEndDate); date = date.plusMonths(1)) {
                    ProjectParticipation projectParticipation = new ProjectParticipation(project.getProject_id(), employeeID, StringAndBigDecimalFormatter.formatStringToPercentageValueForScope(participationData[i][2]), Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    projectParticipationManager.addProjectParticipation(projectParticipation);
                }

            }

        }
    }

    private void insertNewProjectDataInDatabase(Project project, int id, String name, LocalDate approval, LocalDate startDate, LocalDate endLocalDate) {
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
                    updateProjectDataInDatabase(project, name, approval, startDate, endLocalDate);
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
                    insertNewProjectDataInDatabase(project, id, name, approval, startDate, endLocalDate);
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
                    System.out.println("Here");
                    updateProjectDataInDatabase(project, name, approval, startDate, endLocalDate);
                    frameController.getShowProjectsController().updateData(frameController.getShowProjectsController().getProjectsDataFromDataBase());
                    frameController.getTabs().removeTabNewWindow(insertProjectsView);
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
                    JOptionPane.showConfirmDialog(null, "Bitte füllen Sie die Spalten \"Projektname\", \"Bewilligungsdatum\", \"Startdatum\" und \"Enddatum\" aus.", "Spalten nicht vollständig ausgefüllt", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                } else {
                    insertNewProjectDataInDatabase(project, id, name, approval, startDate, endLocalDate);
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
        JTable categoriesTable = insertProjectsView.getCategoriesTable();
        String[][] tableValues = getTableValues(categoriesTable);
        ProjectCategory projectCategory;
        for (int row = 0; row < tableValues.length; row++) {
            if (tableValues[row][0] != null && tableValues[row][1] != null) {
                projectCategory = new ProjectCategory(projectId, projectCategoryManager.getNextID(), tableValues[row][0], StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency(tableValues[row][1]));
                projectCategoryManager.addProjectCategory(projectCategory);
            }

        }
    }

    private void insertFunderValuesDB(int projectId) {
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
        JTable participationTable = insertProjectsView.getProjectParticipationTable();
        String[][] tableValues = getParticipationTableValues(participationTable);
        ProjectParticipation projectParticipation;
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        for (int row = 0; row < tableValues.length; row++) {  //TODO Umwandlungsmethoden für SHK Angestellte implementieren
            if (tableValues[row][0] != null && tableValues[row][1] != null && tableValues[row][2] != null && tableValues[row][3] != null) {
                Date startDate = format.parse(tableValues[row][2]);
                Date endDate = format.parse(tableValues[row][3]);
                LocalDate participationStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate participationEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                for (LocalDate date = participationStartDate; date.isBefore(participationEndDate); date = date.plusMonths(1)) {
                    int personId = employeeDataManager.getEmployeeByName(tableValues[row][0]).getId();
                    projectParticipation = new ProjectParticipation(projectId, personId, StringAndBigDecimalFormatter.formatStringToPercentageValueForScope(tableValues[row][1]), Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    projectParticipationManager.addProjectParticipation(projectParticipation);
                }

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
                if (column == 2 || column == 3) {
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
                    if (column == 1) {
                        break;
                    }
                    if (column == 2) {
                        break;
                    }
                    if (column == 3) {
                        break;
                    }
                    if(column == 4){
                        break;
                    }
                }
                if (column == 3 || column == 4) {
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
                if (givenTable.getValueAt(row, column) == null) {
                    if (column == 2) {
                        break;
                    }
                    if (column == 3) {
                        break;
                    }
                    if (column == 4) {
                        break;
                    }


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
                tableValues[row][column] = (String) givenTable.getValueAt(row, column);
            }
        }
        return tableValues;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        BigDecimal sum = new BigDecimal(0);
        if(e.getSource() == insertProjectsView.getCategoriesTable().getModel()){
            if(e.getColumn() == 1){
                for (int i = 0; i < insertProjectsView.getCategoriesTable().getRowCount(); i++) {
                    if(insertProjectsView.getCategoriesTable().getValueAt(i,1) != null){
                        sum = sum.add(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency((String)insertProjectsView.getCategoriesTable().getValueAt(i,1)));
                    }
                }
                insertProjectsView.getCategoriesSum().setText("Summe: " + StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(sum));
            }
            if(e.getColumn() == 3){
                for (int i = 0; i < insertProjectsView.getCategoriesTable().getRowCount(); i++) {
                    if(insertProjectsView.getCategoriesTable().getValueAt(i,3) != null){
                        sum = sum.add(StringAndBigDecimalFormatter.formatStringToBigDecimalCurrency((String)insertProjectsView.getCategoriesTable().getValueAt(i,3)));
                    }
                }
                insertProjectsView.getCategoriesSum().setText("Summe: " + StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(sum));
            }
        }

    }
}
