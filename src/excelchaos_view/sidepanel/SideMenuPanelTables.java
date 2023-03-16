package excelchaos_view.sidepanel;

import excelchaos_view.ShowPersonView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 * SideMenuPanelTables is a class which represents the view of the SideMenu as a panel, which can be seen from the left side of the main frame on start-up.
 * It includes all the buttons to open various views of the program. Moreover, it also supports hiding/unhiding certain groups of buttons (salary group, payrate group, database group).
 * Details about the buttons and which views they are associated with are put in the comment below
 */
public class SideMenuPanelTables extends JPanel {
    /**
     * @see excelchaos_view.ShowPersonView
     */
    private JButton personenliste;
    /**
     * @see excelchaos_view.ShowProjectsView
     */
    private JButton projektliste;
    /**
     * @see excelchaos_view.SalaryListView
     */
    private JButton gehaltsliste;
   // private JButton gehaltshistorie;
    /**
     * @see excelchaos_view.SalaryIncreaseView
     */
    private JButton salaryIncrease;
    /**
    @see excelchaos_view.ManualSalaryEntryView
     */
    private JButton manualSalary;
    /**
     * @see excelchaos_view.ShowPayRateTableView
     */
    private JButton showE13Tables;
    /**
     * @see excelchaos_view.ShowPayRateTableView
     */
    private JButton showE14Tables;
    /**
     * @see excelchaos_view.ShowPayRateTableView
     */
    private JButton showSHKTables;

    private JButton createSnapshot;
    private JButton changeSnapShotSaveFolder;
    private JButton changeUsedDatabaseAndCloseApplication;
    /**
     * Button to toggle the view of the payrate-related buttons
     */
    private JToggleButton payRatesToogleButton;
    /**
     * Button to toggle the view of the salary-related buttons
     */
    private JToggleButton salaryToggleButton;
    /**
     * Button to toggle the view of the DB operations-related buttons
     */
    private JToggleButton databaseOperationsToggleButton;

    //Some panels to group and organize the component in this view
    private JPanel topPanel, centerpanel;
    private JPanel databaseOperationPanel, dataBaseOperationToggleButtonPanel, databaseOperationButtonPanel;
    private JPanel salaryPanel, salaryToggleButtonPanel, salaryTableButtonPanel;
    private JPanel payRatePanel, payRateToggleButtonPanel, payRateTableButtonPanel;
    private JLabel navi, payRateTextLabel, payRateOpenArrowLabel, payRateCloseArrowLabel, salaryOpenArrowLabel, salaryCloseArrowLabel, salaryTextLabel, databaseOperationTextLabel, databaseOperationOpenArrowLabel, databaseOperationCloseArrowLabel;
    private BasicArrowButton arrowButtonWest;

    //Load the image icons for the close arrow and open arrow
    private ImageIcon CloseArrow = new ImageIcon(getClass().getClassLoader().getResource("images/arrow_right_mini.png"));
    private ImageIcon OpenArrow = new ImageIcon(getClass().getClassLoader().getResource("images/arrow_down_mini.png"));

    //Some constraints for the buttons in the sidemenu panel (border, width, height)
    private Border raisedetchedBorder;

    private final int RegularButtonWidth = 140;

    private final int RegularButtonHeigt = 25;

    /**
     * This method init() initializes the GUI components of the SideMenuPanelTable and sets their preferred sizes,
     * adds the components to the appropriate panels, and sets the preferred size of the frame or panel. It's done on startup to initialize the SideMenu
     */
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

    /**
     * Make the payRateCloseArrowLabel visible and the payRateOpenArrowLabel invisible
     */
    public void payRateCloseArrowLabelVisible() {
        payRateOpenArrowLabel.setVisible(false);
        payRateCloseArrowLabel.setVisible(true);
    }

    /**
     * Make the payRateOpenArrowLabel visible and the payRateCloseArrowLabel invisible
     */
    public void payRateOpenArrowLabelVisible() {
        payRateCloseArrowLabel.setVisible(false);
        payRateOpenArrowLabel.setVisible(true);
    }

    /**
     * Make the salaryCloseArrowLabel visible and the salaryOpenArrowLabel invisible
     */
    public void salaryCloseArrowLabelVisible() {
        salaryOpenArrowLabel.setVisible(false);
        salaryCloseArrowLabel.setVisible(true);
    }
    /**
     * Make the salaryOpenArrowLabel visible and the salaryCloseArrowLabel invisible
     */
    public void salaryOpenArrowLabelVisible() {
        salaryCloseArrowLabel.setVisible(false);
        salaryOpenArrowLabel.setVisible(true);
    }
    /**
     * Make the databaseOperationCloseArrowLabel visible and the databaseOperationOpenArrowLabel invisible
     */
    public void databaseOperationCloseArrowLabelVisible() {
        databaseOperationOpenArrowLabel.setVisible(false);
        databaseOperationCloseArrowLabel.setVisible(true);
    }
    /**
     * Make the databaseOperationOpenArrowLabel visible and the databaseOperationCloseArrowLabel invisible
     */
    public void databaseOperationOpenArrowLabelVisible() {
        databaseOperationCloseArrowLabel.setVisible(false);
        databaseOperationOpenArrowLabel.setVisible(true);
    }

    /**
     * Initialize the salary expander, the salary group is closed on start-up
     */
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

    /**
     * Initialize the payrate expander, the payrate group is closed on start-up
     */
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

    /**
     * Initialize the databaseOperation expander, the DB operation group is closed on start-up
     */
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

    /**
     * @return {@link #payRateToggleButtonPanel}
     */
    public JPanel getPayRateToggleButtonPanel() {
        return payRateToggleButtonPanel;
    }

    /**
     * @return {@link #raisedetchedBorder}
     */
    public Border getRaisedetchedBorder() {
        return raisedetchedBorder;
    }
    /**
     * @return {@link #payRatePanel}
     */
    public JPanel getPayRatePanel() {
        return payRatePanel;
    }
    /**
     * @return {@link #payRateTableButtonPanel}
     */
    public JPanel getPayRateTableButtonPanel() {
        return payRateTableButtonPanel;
    }
    /**
     * @return {@link #arrowButtonWest}
     */
    public BasicArrowButton getArrowButtonWest() {
        return arrowButtonWest;
    }
    /**
     * @return {@link #personenliste}
     */
    public JButton getPersonenliste() {
        return personenliste;
    }
    /**
     * @return {@link #projektliste}
     */
    public JButton getProjektliste() {
        return projektliste;
    }
    /**
     * @return {@link #gehaltsliste}
     */
    public JButton getGehaltsliste() {
        return gehaltsliste;
    }

   /* public JButton getGehaltshistorie() {
        return gehaltshistorie;
    }*/
    /**
     * @return {@link #payRatesToogleButton}
     */
    public JToggleButton getPayRates() {
        return payRatesToogleButton;
    }
    /**
     * @return {@link #manualSalary}
     */
    public JButton getManualSalary() {
        return manualSalary;
    }
    /**
     * @return {@link #salaryIncrease}
     */
    public JButton getSalaryIncrease() {
        return salaryIncrease;
    }
    /**
     * @return {@link #salaryPanel}
     */
    public JPanel getSalaryPanel() {
        return salaryPanel;
    }
    /**
     * @return {@link #createSnapshot}
     */
    public JButton getCreateSnapshot() {
        return createSnapshot;
    }
    /**
     * @return {@link #salaryToggleButton}
     */
    public JToggleButton getSalaryToggleButton() {
        return salaryToggleButton;
    }
    /**
     * @return {@link #salaryTableButtonPanel}
     */
    public JPanel getSalaryTableButtonPanel() {
        return salaryTableButtonPanel;
    }
    /**
     * @return {@link #salaryToggleButtonPanel}
     */
    public JPanel getSalaryToggleButtonPanel() {
        return salaryToggleButtonPanel;
    }
    /**
     * @return {@link #showE13Tables}
     */
    public JButton getShowE13Tables() {
        return showE13Tables;
    }
    /**
     * @return {@link #showE14Tables}
     */
    public JButton getShowE14Tables() {
        return showE14Tables;
    }
    /**
     * @return {@link #showSHKTables}
     */
    public JButton getShowSHKTables() {
        return showSHKTables;
    }
    /**
     * @return {@link #centerpanel}
     */
    public JPanel getCenterpanel() {
        return centerpanel;
    }
    /**
     * @return {@link #changeSnapShotSaveFolder}
     */
    public JButton getChangeSnapShotSaveFolder() {
        return changeSnapShotSaveFolder;
    }
    /**
     * @return {@link #changeUsedDatabaseAndCloseApplication}
     */
    public JButton getChangeUsedDatabaseAndCloseApplication() {
        return changeUsedDatabaseAndCloseApplication;
    }
    /**
     * @return {@link #databaseOperationButtonPanel}
     */
    public JPanel getDatabaseOperationButtonPanel() {
        return databaseOperationButtonPanel;
    }
    /**
     * @return {@link #databaseOperationPanel}
     */
    public JPanel getDatabaseOperationPanel() {
        return databaseOperationPanel;
    }

    /**
     * @return {@link #dataBaseOperationToggleButtonPanel}
     */
    public JPanel getDataBaseOperationToggleButtonPanel() {
        return dataBaseOperationToggleButtonPanel;
    }
    /**
     * @return {@link #databaseOperationsToggleButton}
     */
    public JToggleButton getDatabaseOperationsToggleButton() {
        return databaseOperationsToggleButton;
    }

    /** Set the same ActionListener for all buttons in the view, allowing them to share the same event handling controller
     *
     * @param l the ActionListener to set for the UI elements
     */
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

    /**
     * Set same the ItemListener for the toggle buttons in the view, allowing them to share the same event handling controller
     * @param l
     */
    public void setItemListener(ItemListener l) {
        payRatesToogleButton.addItemListener(l);
        salaryToggleButton.addItemListener(l);
        databaseOperationsToggleButton.addItemListener(l);
    }

}
