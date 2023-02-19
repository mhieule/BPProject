package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.InsertPersonView;
import excelchaos_view.InsertProjectsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class InsertProjectsController implements ActionListener {
    private InsertProjectsView insertProjectsView;
    private MainFrameController frameController;

    private String addProjectsTab = "Projekt hinzufügen";

    public InsertProjectsController(MainFrameController mainFrameController) {
        insertProjectsView = new InsertProjectsView();
        insertProjectsView.init();
        insertProjectsView.setActionListener(this);
        frameController = mainFrameController;
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
    }

    public DefaultTableModel resetTable(String[] newColumns) {
        DefaultTableModel result = new DefaultTableModel(null, newColumns);
        result.setRowCount(40);
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertProjectsView.getSubmit()) {
            ProjectManager projectManager = new ProjectManager();

            int id = projectManager.getNextID();
            String name = insertProjectsView.getTfName().getText();

            Calendar calendar = Calendar.getInstance();
            LocalDate approval = insertProjectsView.getTfApproval().getDate();
            calendar.set(approval.getYear(), approval.getMonth().getValue(), approval.getDayOfMonth());
            Date dateOfApproval = calendar.getTime();

            LocalDate start = insertProjectsView.getTfStart().getDate();
            calendar.set(start.getYear(), start.getMonth().getValue(), start.getDayOfMonth());
            Date dateOfStart = calendar.getTime();

            LocalDate duration = insertProjectsView.getTfDuration().getDate();
            calendar.set(duration.getYear(), duration.getMonth().getValue(), duration.getDayOfMonth());
            Date endDate = calendar.getTime();

            Project project = new Project(id, name, dateOfStart, dateOfApproval, endDate);

            projectManager.addProject(project);

            resetInputs();
            insertProjectsView.revalidate();
            insertProjectsView.repaint();
            frameController.getShowProjectsController().updateData();
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
            projectCategory = new ProjectCategory(projectId, projectCategoryManager.getNextID(), tableValues[row][0], transformer.transformStringToDouble(tableValues[row][1]));
            projectCategoryManager.addProjectCategory(projectCategory);
        }
    }

    private void insertFunderValuesDB(int projectId) {
        ProjectFunderManager projectFunderManager = new ProjectFunderManager();
        JTable funderTable = insertProjectsView.getProjectFunderTable();
        String[][] tableValues = getTableValues(funderTable);
        ProjectFunder projectFunder;
        for (int row = 0; row < tableValues.length; row++) {
            projectFunder = new ProjectFunder(projectId, projectFunderManager.getNextID(), tableValues[row][0], tableValues[row][1], tableValues[row][2]);
            projectFunderManager.addProjectFunder(projectFunder);
        }
    }

    private void insertParticipationValuesDB(int projectId) {
        ProjectParticipationManager projectParticipationManager = new ProjectParticipationManager();
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        JTable participationTable = insertProjectsView.getProjectParticipationTable();
        String[][] tableValues = getTableValues(participationTable);
        ProjectParticipation projectParticipation;
        StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
        for (int row = 0; row < tableValues.length; row++) { //TODO Umwandlungsmethoden für SHK Angestellte implementieren
            int personId = employeeDataManager.getEmployeeByName(tableValues[row][0]).getId();
            //projectParticipation = new ProjectParticipation(projectId, personId, transformer.formatStringToPercentageValueForScope(tableValues[row][1]), )
        }
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
