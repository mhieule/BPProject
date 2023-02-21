package excelchaos_view;

import excelchaos_model.*;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ShowProjectsView extends JPanel {
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

    public void addData() {
        removeAll();
        setLayout(new BorderLayout());

        String columns[] = {"ID", "Name", "Bewilligungsdatum", "Anfangsdatum", "Enddatum"};
        ProjectManager projectManager = new ProjectManager();
        int lines = projectManager.getRowCount();
        String resultData[][] = new String[lines][];
        int currentIndex = 0;
        List<Project> projects = projectManager.getAllProjects();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (Project project : projects) {
            String Id = Integer.toString(project.getProject_id());
            String name = project.getProject_name();
            String approval = dateFormat.format(project.getApproval_date());
            String start = dateFormat.format(project.getStart_date());
            String duration = dateFormat.format(project.getDuration());

            String[] values = {Id, name, approval, start, duration};
            resultData[currentIndex] = values;
            currentIndex++;
        }

        jt = new CustomTable(resultData, columns);
        jt.getColumnModel().getColumn(1).setMinWidth(0);
        jt.getColumnModel().getColumn(1).setMaxWidth(0);
        jt.getColumnModel().getColumn(1).setWidth(0);
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
