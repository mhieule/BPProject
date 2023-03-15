package excelchaos_view.sidepanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class SideMenuPanelTables extends JPanel {
    private JButton personenliste;
    private JButton projektliste;
    private JButton gehaltsliste;
   // private JButton gehaltshistorie;
    private JButton salaryIncrease;
    private JButton manualSalary;
    private JButton showE13Tables;
    private JButton showE14Tables;
    private JButton showSHKTables;

    private JButton createSnapshot;
    private JButton changeSnapShotSaveFolder;
    private JButton changeUsedDatabaseAndCloseApplication;
    private JToggleButton payRatesToogleButton;
    private JToggleButton salaryToggleButton;
    private JToggleButton databaseOperationsToggleButton;
    private JPanel topPanel, centerpanel;

    private JPanel databaseOperationPanel, dataBaseOperationToggleButtonPanel, databaseOperationButtonPanel;
    private JPanel salaryPanel, salaryToggleButtonPanel, salaryTableButtonPanel;
    private JPanel payRatePanel, payRateToggleButtonPanel, payRateTableButtonPanel;
    private JLabel navi, payRateTextLabel, payRateOpenArrowLabel, payRateCloseArrowLabel, salaryOpenArrowLabel, salaryCloseArrowLabel, salaryTextLabel, databaseOperationTextLabel, databaseOperationOpenArrowLabel, databaseOperationCloseArrowLabel;
    private BasicArrowButton arrowButtonWest;
    private ImageIcon CloseArrow = new ImageIcon(getClass().getClassLoader().getResource("images/arrow_right_mini.png"));
    private ImageIcon OpenArrow = new ImageIcon(getClass().getClassLoader().getResource("images/arrow_down_mini.png"));

    private Border raisedetchedBorder;

    private final int RegularButtonWidth = 140;

    private final int RegularButtonHeigt = 25;

    public void init() {
        personenliste = new JButton("Personenliste");
        projektliste = new JButton("Projektliste");

        personenliste.setPreferredSize(new Dimension(RegularButtonWidth, RegularButtonHeigt));
        projektliste.setPreferredSize(new Dimension(RegularButtonWidth, RegularButtonHeigt));


        arrowButtonWest = new BasicArrowButton(BasicArrowButton.WEST);
        topPanel = new JPanel();
        centerpanel = new JPanel();
        navi = new JLabel("Navigationsleiste");

        topPanel.setPreferredSize(new Dimension(150, 30));
        topPanel.add(navi);
        topPanel.add(arrowButtonWest);
        add(topPanel, BorderLayout.PAGE_START);
        centerpanel.setPreferredSize(new Dimension(150, 550));
        centerpanel.add(personenliste);
        centerpanel.add(projektliste);

        initSalaryExpander();
        centerpanel.add(salaryPanel);
        initPayRateExpander();
        centerpanel.add(payRatePanel);
        initDatabaseOperationExpander();
        centerpanel.add(databaseOperationPanel);

        add(centerpanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(150, 100));

    }


    public void payRateCloseArrowLabelVisible() {
        payRateOpenArrowLabel.setVisible(false);
        payRateCloseArrowLabel.setVisible(true);
    }

    public void payRateOpenArrowLabelVisible() {
        payRateCloseArrowLabel.setVisible(false);
        payRateOpenArrowLabel.setVisible(true);
    }

    public void salaryCloseArrowLabelVisible() {
        salaryOpenArrowLabel.setVisible(false);
        salaryCloseArrowLabel.setVisible(true);
    }

    public void salaryOpenArrowLabelVisible() {
        salaryCloseArrowLabel.setVisible(false);
        salaryOpenArrowLabel.setVisible(true);
    }

    public void databaseOperationCloseArrowLabelVisible() {
        databaseOperationOpenArrowLabel.setVisible(false);
        databaseOperationCloseArrowLabel.setVisible(true);
    }

    public void databaseOperationOpenArrowLabelVisible() {
        databaseOperationCloseArrowLabel.setVisible(false);
        databaseOperationOpenArrowLabel.setVisible(true);
    }

    private void initSalaryExpander() {
        salaryPanel = new JPanel();
        salaryToggleButtonPanel = new JPanel();
        salaryTableButtonPanel = new JPanel();
        raisedetchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        salaryToggleButtonPanel.setPreferredSize(new Dimension(150, 45));
        salaryTableButtonPanel.setPreferredSize(new Dimension(150, 60));
        salaryToggleButtonPanel.setBorder(raisedetchedBorder);
        salaryPanel.setLayout(new BorderLayout());
        salaryPanel.setPreferredSize(new Dimension(150, 45));
        salaryCloseArrowLabel = new JLabel(CloseArrow);
        salaryOpenArrowLabel = new JLabel(OpenArrow);
        salaryTextLabel = new JLabel("Gehälter");
        salaryToggleButton = new JToggleButton();
        salaryToggleButton.setPreferredSize(new Dimension(RegularButtonWidth, 30));
        salaryToggleButton.setLayout(new BoxLayout(salaryToggleButton, BoxLayout.X_AXIS));
        salaryToggleButton.add(Box.createHorizontalStrut(20));
        salaryToggleButton.add(salaryTextLabel);
        salaryToggleButton.add(Box.createHorizontalStrut(15));
        salaryToggleButton.add(salaryCloseArrowLabel);
        salaryCloseArrowLabelVisible();
        salaryToggleButton.add(salaryOpenArrowLabel);
        salaryToggleButtonPanel.add(salaryToggleButton);
        salaryPanel.add(salaryToggleButtonPanel, BorderLayout.NORTH);

        gehaltsliste = new JButton("Gehaltsprojektion");
        manualSalary = new JButton("Gehaltsanpassung SAP");
        manualSalary.setMargin(new Insets(2,0,2,0));
        salaryIncrease = new JButton("Gehaltserhöhung");
      //  gehaltshistorie = new JButton("Gehaltshistorie");

        gehaltsliste.setPreferredSize(new Dimension(140, RegularButtonHeigt));
        manualSalary.setPreferredSize(new Dimension(140, RegularButtonHeigt));
        salaryIncrease.setPreferredSize(new Dimension(140, RegularButtonHeigt));
       // gehaltshistorie.setPreferredSize(new Dimension(140, RegularButtonHeigt));

        salaryTableButtonPanel.add(gehaltsliste);
        salaryTableButtonPanel.add(manualSalary);
        salaryTableButtonPanel.add(salaryIncrease);
        //salaryTableButtonPanel.add(gehaltshistorie);

        salaryTableButtonPanel.setVisible(false);
        salaryPanel.add(salaryTableButtonPanel, BorderLayout.CENTER);
    }

    private void initPayRateExpander() {
        payRatePanel = new JPanel();
        payRateToggleButtonPanel = new JPanel();
        payRateTableButtonPanel = new JPanel();
        raisedetchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        payRateToggleButtonPanel.setPreferredSize(new Dimension(150, 45));
        payRateTableButtonPanel.setPreferredSize(new Dimension(150, 80));
        payRateToggleButtonPanel.setBorder(raisedetchedBorder);
        //payRatePanel.setLayout(new BoxLayout(payRatePanel,BoxLayout.Y_AXIS));
        payRatePanel.setLayout(new BorderLayout());
        payRatePanel.setPreferredSize(new Dimension(150, 45));

        payRateCloseArrowLabel = new JLabel(CloseArrow);
        payRateOpenArrowLabel = new JLabel(OpenArrow);
        payRateTextLabel = new JLabel("Entgelttabellen");
        payRatesToogleButton = new JToggleButton();
        payRatesToogleButton.setPreferredSize(new Dimension(RegularButtonWidth, 30));
        payRatesToogleButton.setLayout(new BoxLayout(payRatesToogleButton, BoxLayout.X_AXIS));
        payRatesToogleButton.add(payRateTextLabel);
        payRatesToogleButton.add(payRateCloseArrowLabel);
        payRateCloseArrowLabelVisible();
        payRatesToogleButton.add(payRateOpenArrowLabel);

        payRateToggleButtonPanel.add(payRatesToogleButton);

        payRatePanel.add(payRateToggleButtonPanel, BorderLayout.NORTH);

        showE13Tables = new JButton("E13 Tabellen");
        showE14Tables = new JButton("E14 Tabellen");
        showSHKTables = new JButton("SHK Tabelle");

        showE13Tables.setPreferredSize(new Dimension(120, 20));
        showE14Tables.setPreferredSize(new Dimension(120, 20));
        showSHKTables.setPreferredSize(new Dimension(120, 20));

        payRateTableButtonPanel.add(showE13Tables);
        payRateTableButtonPanel.add(showE14Tables);
        payRateTableButtonPanel.add(showSHKTables);
        payRateTableButtonPanel.setVisible(false);

        payRatePanel.add(payRateTableButtonPanel, BorderLayout.CENTER);
    }

    private void initDatabaseOperationExpander() {
        databaseOperationPanel = new JPanel();
        dataBaseOperationToggleButtonPanel = new JPanel();
        databaseOperationButtonPanel = new JPanel();
        raisedetchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        dataBaseOperationToggleButtonPanel.setPreferredSize(new Dimension(150, 45));
        databaseOperationButtonPanel.setPreferredSize(new Dimension(150, 60));
        dataBaseOperationToggleButtonPanel.setBorder(raisedetchedBorder);
        databaseOperationPanel.setLayout(new BorderLayout());
        databaseOperationPanel.setPreferredSize(new Dimension(150, 45));

        databaseOperationCloseArrowLabel = new JLabel(CloseArrow);
        databaseOperationOpenArrowLabel = new JLabel(OpenArrow);
        databaseOperationTextLabel = new JLabel("Datenbank");
        databaseOperationsToggleButton = new JToggleButton();
        databaseOperationsToggleButton.setPreferredSize(new Dimension(RegularButtonWidth, 30));
        databaseOperationsToggleButton.setLayout(new BoxLayout(databaseOperationsToggleButton, BoxLayout.X_AXIS));
        databaseOperationsToggleButton.add(Box.createHorizontalStrut(10));
        databaseOperationsToggleButton.add(databaseOperationTextLabel);
        databaseOperationsToggleButton.add(Box.createHorizontalStrut(13));
        databaseOperationsToggleButton.add(databaseOperationCloseArrowLabel);
        databaseOperationCloseArrowLabelVisible();
        databaseOperationsToggleButton.add(databaseOperationOpenArrowLabel);

        dataBaseOperationToggleButtonPanel.add(databaseOperationsToggleButton);
        databaseOperationPanel.add(dataBaseOperationToggleButtonPanel, BorderLayout.NORTH);

        createSnapshot = new JButton("Snapshot erstellen");
        changeSnapShotSaveFolder = new JButton("Speicherort ändern");

        changeUsedDatabaseAndCloseApplication = new JButton("Datenbank ändern");

        createSnapshot.setPreferredSize(new Dimension(140,RegularButtonHeigt));
        createSnapshot.setMargin(new Insets(2,7,2,7));
        changeSnapShotSaveFolder.setPreferredSize(new Dimension(140, RegularButtonHeigt));
        changeSnapShotSaveFolder.setMargin(new Insets(2,7,2,7));
        changeUsedDatabaseAndCloseApplication.setPreferredSize(new Dimension(140, RegularButtonHeigt));

        databaseOperationButtonPanel.add(createSnapshot);
        databaseOperationButtonPanel.add(changeSnapShotSaveFolder);
        databaseOperationButtonPanel.add(changeUsedDatabaseAndCloseApplication);
        databaseOperationButtonPanel.setVisible(false);

        databaseOperationPanel.add(databaseOperationButtonPanel, BorderLayout.CENTER);
    }

    public JPanel getPayRateToggleButtonPanel() {
        return payRateToggleButtonPanel;
    }

    public Border getRaisedetchedBorder() {
        return raisedetchedBorder;
    }

    public JPanel getPayRatePanel() {
        return payRatePanel;
    }

    public JPanel getPayRateTableButtonPanel() {
        return payRateTableButtonPanel;
    }

    public BasicArrowButton getArrowButtonWest() {
        return arrowButtonWest;
    }

    public JButton getPersonenliste() {
        return personenliste;
    }

    public JButton getProjektliste() {
        return projektliste;
    }

    public JButton getGehaltsliste() {
        return gehaltsliste;
    }

   /* public JButton getGehaltshistorie() {
        return gehaltshistorie;
    }*/

    public JToggleButton getPayRates() {
        return payRatesToogleButton;
    }

    public JButton getManualSalary() {
        return manualSalary;
    }

    public JButton getSalaryIncrease() {
        return salaryIncrease;
    }

    public JPanel getSalaryPanel() {
        return salaryPanel;
    }

    public JButton getCreateSnapshot() {
        return createSnapshot;
    }

    public JToggleButton getSalaryToggleButton() {
        return salaryToggleButton;
    }

    public JPanel getSalaryTableButtonPanel() {
        return salaryTableButtonPanel;
    }

    public JPanel getSalaryToggleButtonPanel() {
        return salaryToggleButtonPanel;
    }

    public JButton getShowE13Tables() {
        return showE13Tables;
    }

    public JButton getShowE14Tables() {
        return showE14Tables;
    }

    public JButton getShowSHKTables() {
        return showSHKTables;
    }

    public JPanel getCenterpanel() {
        return centerpanel;
    }

    public JButton getChangeSnapShotSaveFolder() {
        return changeSnapShotSaveFolder;
    }

    public JButton getChangeUsedDatabaseAndCloseApplication() {
        return changeUsedDatabaseAndCloseApplication;
    }

    public JPanel getDatabaseOperationButtonPanel() {
        return databaseOperationButtonPanel;
    }

    public JPanel getDatabaseOperationPanel() {
        return databaseOperationPanel;
    }


    public JPanel getDataBaseOperationToggleButtonPanel() {
        return dataBaseOperationToggleButtonPanel;
    }

    public JToggleButton getDatabaseOperationsToggleButton() {
        return databaseOperationsToggleButton;
    }

    public void setActionListener(ActionListener l) {
        personenliste.addActionListener(l);
        projektliste.addActionListener(l);
        gehaltsliste.addActionListener(l);
        manualSalary.addActionListener(l);
        salaryIncrease.addActionListener(l);
        //gehaltshistorie.addActionListener(l);
        arrowButtonWest.addActionListener(l);
        showE13Tables.addActionListener(l);
        showE14Tables.addActionListener(l);
        showSHKTables.addActionListener(l);
        createSnapshot.addActionListener(l);
        changeSnapShotSaveFolder.addActionListener(l);
        changeUsedDatabaseAndCloseApplication.addActionListener(l);
    }

    public void setItemListener(ItemListener l) {
        payRatesToogleButton.addItemListener(l);
        salaryToggleButton.addItemListener(l);
        databaseOperationsToggleButton.addItemListener(l);
    }

}
