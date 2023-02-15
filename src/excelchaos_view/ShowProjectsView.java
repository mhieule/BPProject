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

    public void addData(){
        removeAll();
        setLayout(new BorderLayout());

        String columns[] = {"Name", "Bewilligungsdatum","Anfangsdatum", "Laufzeit"};
        ProjectManager projectManager = new ProjectManager();
        int lines  = projectManager.getRowCount();
        String resultData[][] = new String[lines][];
        int currentIndex = 0;
        List<Project> projects = projectManager.getAllProjects();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for (Project project : projects){
            String name = project.getProject_name();
            String date = dateFormat.format(project.getApproval_date());
            String start = dateFormat.format(project.getStart_date());
            String duration = String.valueOf(project.getDuration());

            String[] values = {name, date, start, duration};
            resultData[currentIndex] = values;
            currentIndex++;
        }
        jt = new CustomTable(resultData, columns);
        jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(jt);
        tca.adjustColumns();
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
