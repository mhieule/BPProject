package excelchaos_controller;

import excelchaos_view.InsertPersonView;
import excelchaos_view.SideMenuPanelActionLogView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InsertPersonController implements ActionListener {
    private InsertPersonView insertPersonView;
    private MainFrameController frameController;

    public InsertPersonController(MainFrameController mainFrameController) {
        insertPersonView = new InsertPersonView();
        insertPersonView.init();
        insertPersonView.setActionListener(this);
        frameController = mainFrameController;

    }

    public InsertPersonView getInsertPersonView() {
        return insertPersonView;
    }

    public void showInsertPersonView(MainFrameController mainFrameController) {
        SideMenuPanelActionLogView.model.addElement("Eintrag einfügen");
        if (mainFrameController.getTabs().indexOfTab("Person hinzufügen") == -1) {
            mainFrameController.getTabs().addTab("Person hinzufügen", insertPersonView);
            mainFrameController.getTabs().setActionListener(this);
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab("Person hinzufügen"));
        } else
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab("Person hinzufügen"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertPersonView.getNationalityCheckBox()) {
            insertPersonView.getNationalitySecond().setVisible(true);
            insertPersonView.getNationalityPickList2().setVisible(true);
        }
        if (e.getSource() == insertPersonView.getSubmit()) {
            System.out.println("submitting");
            File file = new File("src/data");
            FileWriter fr = null;
            try {
                fr = new FileWriter(file, true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            BufferedWriter br = new BufferedWriter(fr);
            try {
                br.write(insertPersonView.getTfName().getText() + "," + insertPersonView.getTfVorname().getText() + "," + insertPersonView.getTfStrasse().getText() + ","
                        + insertPersonView.getTfPrivatEmail().getText() + "," + insertPersonView.getTfPrivateTelefonnummer().getText() + "," + insertPersonView.getTfGeburtsdatum().getText() + ","
                        + insertPersonView.getNationalityPickList().getSelectedItem().toString() + "," + insertPersonView.getNationalityPickList2().getSelectedItem().toString() + "," + insertPersonView.getTfPersonalnummer().getText() + ","
                        + insertPersonView.getTfTuid().getText() + "," + insertPersonView.getTfVertragMit().getText() + "," + insertPersonView.getStatusPicklist().getSelectedItem().toString() + ","
                        + insertPersonView.getTfGehaltEingeplanntBis().getText() + "," + insertPersonView.getTfTranspondernummer().getText() + "," + insertPersonView.getTfBueronummer().getText() + ","
                        + insertPersonView.getTfTelefonnummerTUDA().getText() + "," + insertPersonView.getTfInventarList().getText() + "\n"
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try {
                br.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                fr.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            insertPersonView.removeAll();
            insertPersonView.revalidate();
            insertPersonView.repaint();
            SideMenuPanelActionLogView.model.addElement("Eintrag eingefügt!");
        }
    }
}
