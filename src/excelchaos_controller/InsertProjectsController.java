package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_view.InsertPersonView;
import excelchaos_view.InsertProjectsView;

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


    public void resetInputs(){
        insertProjectsView.getTfName().setText(null);
        insertProjectsView.getTfApproval().setText(null);
        insertProjectsView.getTfDuration().setText(null);
        insertProjectsView.getTfStart().setText(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertProjectsView.getSubmit()) {
            ProjectManager projectManager = new ProjectManager();

            String name  = insertProjectsView.getTfName().getText();
            String duration = insertProjectsView.getTfDuration().getText();

            Calendar calendar = Calendar.getInstance();
            LocalDate approval = insertProjectsView.getTfApproval().getDate();
            calendar.set(approval.getYear(), approval.getMonth().getValue(), approval.getDayOfMonth());
            Date dateOfApproval = calendar.getTime();

            LocalDate start = insertProjectsView.getTfStart().getDate();
            calendar.set(start.getYear(), start.getMonth().getValue(), start.getDayOfMonth());
            Date dateOfStart = calendar.getTime();

//            Project project = new Project(name, duration, dateOfApproval, dateOfStart);
//            projectManager.addProject(project);

            resetInputs();
            insertProjectsView.revalidate();
            insertProjectsView.repaint();
            frameController.getShowProjectsController().updateData();
        }
        if(e.getSource() == insertProjectsView.getReset()){
            resetInputs();
        }
        if(e.getSource()==insertProjectsView.getCancel()){
            frameController.getTabs().removeTabNewWindow(insertProjectsView);
        }
    }
}
