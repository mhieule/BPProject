package excelchaos_view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SalaryListView extends JPanel {

    public void init(){
        removeAll();
        setLayout(new BorderLayout());
        String column[] = {
                "Name", "Vorname", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung"
        };
        File f = new File("src/salaryData");
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
        }
    }

    public void showPayGradeIncrease(){
        removeAll();
        setLayout(new BorderLayout());
        String column[] = {
                "Name", "Vorname", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung", "Höherstufung 1 ab", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung",
                "Höherstufung 2 ab", "Gruppe", "Stufe", "Gehaltskosten", "Kosten Jahressonderzahlung"
        };
        File f = new File("src/moreSalaryData");
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
            System.out.println("Testing in View");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
