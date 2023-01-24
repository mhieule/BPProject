package excelchaos_controller;

import excelchaos_model.SalaryTableManager;
import excelchaos_view.PayRateTablesView;
import excelchaos_view.SideMenuPanelActionLogView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayRateTablesController implements ActionListener {
    private PayRateTablesView payRateTablesView;
    private MainFrameController frameController;
    private ToolbarPayRateTablesController toolbarPayRateTables;
    private String title;

    private SalaryTableManager manager;

    public PayRateTablesController(MainFrameController mainFrameController) {
        frameController = mainFrameController;
        manager = new SalaryTableManager();


    }


    public void showPayRatesView(MainFrameController mainFrameController) {
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            //SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
            payRateTablesView = new PayRateTablesView();
            toolbarPayRateTables = new ToolbarPayRateTablesController(frameController, this);
            payRateTablesView.init();
            payRateTablesView.add(toolbarPayRateTables.getToolbar(), BorderLayout.NORTH);
            initButtons();
            mainFrameController.addTab(title, payRateTablesView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
            //SideMenuPanelActionLogView.model.addElement("Einträge anzeigen");
        }
    }

    public void setTitle(String name) {
        title = name;
    }

    public String getTitle() {
        return title;
    }

    public void initButtons() {
        payRateTablesView.getCenterPanel().removeAll();
        String paygrade = getPayGradeFromTitle(); //PayGrade ist Gruppe/Klasse
        int temporary = manager.getNumOfTables(paygrade);
        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
        for(int i = 0; i< temporary;i++){
            String distinctTableName = manager.getDistinctTableNames(paygrade).get(i);
            JButton button = new JButton(distinctTableName);
            button.setPreferredSize(new Dimension(400,30));
            JPanel panel = new JPanel();
            panel.add(button);
            buttonPanel.add(panel);
            buttonPanel.add(Box.createVerticalStrut(10));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ShowPayRateTableController payRateTableController = new ShowPayRateTableController(frameController,manager,distinctTableName,paygrade);
                    payRateTableController.insertValuesInTable();

                }
            });
        }
        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setVisible(true);
        payRateTablesView.getCenterPanel().add(scrollPane);
    }

    private String getPayGradeFromTitle() {
        String result = "";
        if (title.equals("E13 Entgelttabellen")) {
            result = "E13";
        } else if (title.equals("E14 Entgelttabellen")) {
            result = "E14";
        } else if (title.equals("SHK Entgelttabellen")) {
            result = "SHK";
        }
        return result;
    }

    public void updateview(){
        initButtons();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
