package excelchaos_view;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ShowPersonView extends JPanel {

    private boolean hasAToolbar;

    public void init() {
        setLayout(new BorderLayout());
        System.out.println("SEEING ITEMS");

        String column[] = {"Name", "Vorname", "Wohnadresse", "E-Mail Privat", "Telefon Privat",
                "Geburtsdatum", "Staatsangehörigkeit 1", "Staatsangehörigkeit 2", "Personal.Nr", "TU-ID", "Vertrag mit",
                "Status", "Gehalt Eingeplant bis", "Transponder.Nr", "Büro.Nr", "Telefon TUDA", "Inventarliste"
        };
        File f = new File("src/data");
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

    public void setHasToolbar(boolean hasToolbar) {
        this.hasAToolbar = hasToolbar;
    }
    public boolean hasToolbar(){
        return hasAToolbar;
    }
}
