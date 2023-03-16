package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import excelchaos_controller.IncreaseSalaryDialogController;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.constants.IncreaseSalaryOption;
import excelchaos_view.components.CustomTable;
import excelchaos_model.utility.StringAndBigDecimalFormatter;

import static excelchaos_model.constants.IncreaseSalaryOption.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class IncreaseSalaryDialogView extends JPanel {

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();
    private JButton okayButton;
    private JButton closeButton;
    private JButton projectButton;
    private JPanel buttonPanel, buttonWestPanel, buttonEastPanel;
    private JPanel contentPanel, formularPanel;
    private JPanel tablePanel;
    private JTable table;
    private Box textFieldAbsolutePanel;

    private JRadioButton absoluteRadioButton;
    private JRadioButton relativeRadioButton;
    private JRadioButton mixedRadioButton;
    private ButtonGroup optionRadioButtonGroup, increaseOptionRadioButtonGroup;

    private JTextField textFieldAbsolute;
    private JTextField textFieldRelative;
    private JTextField textFieldComment;
    private Box payMixedOptionPanel;
    private JTextField mixedAbsolute, mixedRelative;
    private JRadioButton mixedMinRadioButton, mixedMaxRadioButton;
    private ButtonGroup mixedOptionRadioButtonGroup;
    private JLabel currentSalary, currentBonus, dateQuery, commentQuery, increaseTypeQuery, increaseOptionQuery;
    private JRadioButton salaryIncreaseRadioButton, bonusRadioButton;

    private DatePicker startDate;
    private JComponent[] formularComponentGroup;
    private ArrayList<Integer> employeeIDList;
    private String[] columns = {"Name", "Gehaltskosten", "Sonderzahlung", "Gehalt am Startdatum", "Erhöhte Gehalt"};

    private boolean isProjectedColumnOpened;

    public void init(ArrayList<Integer> employeeIDList) {
        this.employeeIDList = employeeIDList;
        this.isProjectedColumnOpened = true;

        //Set some basic properties of the increase salary dialog view
        setLayout(new BorderLayout());
        //TODO change the name of the tab
        /*if(employeeIDList.size()==1){
            setTitle("Gehaltserhöhung "+employeeDataManager.getEmployee(employeeIDList.get(0)).getName());
        } else setTitle("Gehaltserhöhung (mehrfach)");*/


        //TODO refactor formularPanel to formularPanel+tablePanel
        // JPanel tablePanel = new JPanel(new BorderLayout());
        // tablePanel.add(this.table, BorderLayout.CENTER);

        //Prepare all the needed components for the tablePanel
        tablePanel = new JPanel();
        this.table = new JTable(getSalaryFromDB(), columns);
        setProjectionColumnInvisible();
        isProjectedColumnOpened = false;
        tablePanel.add(new JScrollPane(table));
        add(tablePanel, BorderLayout.BEFORE_LINE_BEGINS);

        //Prepare all the needed components for the increase salary formular panel. The components are organized in the order which they appear in the formular.
        //currentSalary = new JLabel("");
        //TODO possibly refactor to a separate model method to retrieve the salary information. Same goes for currentBonus
        //currentSalary.setText("Aktuelle Gehaltkosten: "+contractDataManager.getContract(employeeIDList.get(0)).getRegular_cost()+" €");
        //currentBonus = new JLabel("");
        //currentBonus.setText("Aktuelle Sonderzahlung: "+contractDataManager.getContract(employeeIDList.get(0)).getBonus_cost()+" €");

        increaseTypeQuery = new JLabel("1. Erhöhungstyp (*)");
        salaryIncreaseRadioButton = new JRadioButton("Normale Gehaltserhöhung");
        salaryIncreaseRadioButton.setSelected(true);
        bonusRadioButton = new JRadioButton("Sonderzahlung");
        increaseOptionRadioButtonGroup = new ButtonGroup();
        increaseOptionRadioButtonGroup.add(salaryIncreaseRadioButton);
        increaseOptionRadioButtonGroup.add(bonusRadioButton);

        increaseOptionQuery = new JLabel("2. Erhöhungsoption (*)");
        absoluteRadioButton = new JRadioButton("Option 1: Absolut in €");
        textFieldAbsolute = new JTextField();
        textFieldAbsolute.setText("0");
        textFieldAbsolute.setVisible(false);
        relativeRadioButton = new JRadioButton("Option 2: Relativ in %");
        relativeRadioButton.setSelected(true);
        textFieldRelative = new JTextField();
        textFieldRelative.setText("0");
        textFieldRelative.setVisible(true);
        mixedRadioButton = new JRadioButton("Option 3: Gemischt");
        optionRadioButtonGroup = new ButtonGroup();
        optionRadioButtonGroup.add(absoluteRadioButton);
        optionRadioButtonGroup.add(relativeRadioButton);
        optionRadioButtonGroup.add(mixedRadioButton);
        mixedAbsolute = new JTextField();
        mixedAbsolute.setText("0");
        mixedRelative = new JTextField();
        mixedRelative.setText("0");
        //mixedMinRadioButton = new JRadioButton("Minimum aus den beiden");
        //mixedMaxRadioButton = new JRadioButton("Maximum aus den beiden");
        //mixedOptionRadioButtonGroup = new ButtonGroup();
        //mixedOptionRadioButtonGroup.add(mixedMinRadioButton);
        //mixedOptionRadioButtonGroup.add(mixedMaxRadioButton);

        payMixedOptionPanel = Box.createVerticalBox();
        payMixedOptionPanel.add(new JLabel("Absolut in €:"));
        payMixedOptionPanel.add(mixedAbsolute);
        payMixedOptionPanel.add(new JLabel("oder relativ in %"));
        payMixedOptionPanel.add(mixedRelative);
        //payMixedOptionPanel.add(mixedMinRadioButton);
        //payMixedOptionPanel.add(mixedMaxRadioButton);
        payMixedOptionPanel.setVisible(false);
        //payMixedOptionPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));

        dateQuery = new JLabel("3. Startdatum (*)");
        startDate = new DatePicker();
        JPanel datePanel = new JPanel();
        datePanel.add(startDate);

        commentQuery = new JLabel("4. Kommentar");
        textFieldComment = new JTextField();


        //Done with the initialization of the components. The components are now added to the formularPanel
        this.formularPanel = new JPanel();
        //this.formularPanel.setLayout(new BoxLayout(this.formularPanel, 1));
        this.formularPanel.setLayout(new GridBagLayout());

        //create a group of all the components used in the formulaPanel for easy traversal
        formularComponentGroup = new JComponent[]{/*currentSalary, currentBonus,*/ increaseTypeQuery, salaryIncreaseRadioButton, bonusRadioButton, increaseOptionQuery
                , absoluteRadioButton, textFieldAbsolute, relativeRadioButton, textFieldRelative, mixedRadioButton, payMixedOptionPanel, dateQuery, datePanel, commentQuery, textFieldComment};

        //organize the component in the formularComponentGroup
        setConstraintsForComponents();

        //Initialize the button panel. The buttonPanel itself contains 2 smaller panels (buttonWestPanel to the left for 2 buttons: Projection and OK,
        //buttonEastPanel to the right for 1 button: Abbrechen)
        //TODO add projection function
        buttonPanel = new JPanel(new BorderLayout());
        buttonWestPanel = new JPanel(new FlowLayout());
        buttonEastPanel = new JPanel(new FlowLayout());

        okayButton = new JButton("OK");
        closeButton = new JButton("Abbrechen");
        projectButton = new JButton("Projizieren");
        buttonWestPanel.add(projectButton);
        buttonWestPanel.add(okayButton);
        buttonEastPanel.add(closeButton);
        buttonPanel.add(buttonWestPanel, BorderLayout.WEST);
        buttonPanel.add(buttonEastPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);


        //Add the formularPanel to the view
        add(this.formularPanel, BorderLayout.CENTER);


        //Set properties and constraints for the dialog (high-level)
        setLocation(100, 300);
        //setLocationRelativeTo(getParent());
        //setAlwaysOnTop(true);
        //setResizable(true);
        setVisible(true);
        setSize(800, 650);
        //setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //addProjectionColumn();
    }

    private void setConstraintsForComponents() {

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(6, 6, 6, 6);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < formularComponentGroup.length; i++) {
            formularPanel.add(formularComponentGroup[i], constraints);
            constraints.gridy++;
            constraints.insets.bottom = 5;
            constraints.anchor = GridBagConstraints.WEST;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
        }
        formularPanel.setPreferredSize(new Dimension(700, 500));

    }

    public void addProjectionColumn() {
        /*
        this.table.addColumn(new TableColumn());
        TableColumn projectedColumn1 = this.table.getColumnModel().getColumn(3);
        projectedColumn1.setHeaderValue("Projiziierte Gehalt");


        this.table.addColumn(new TableColumn());
        TableColumn projectedColumn2 = this.table.getColumnModel().getColumn(4);
        projectedColumn2.setHeaderValue("Erhöhte Gehalt");

        System.out.println("Table number of columns "+table.getModel().getColumnCount());
        System.out.println("Table number of columns "+table.getColumnCount());
        System.out.println("Table number of columns "+table.getColumnCount());
        System.out.println("Table number of columns "+table.getColumnCount());
        System.out.println("Table number of columns "+table.getColumnCount());
        System.out.println("Table number of columns "+table.getColumnCount());
        System.out.println("Table number of columns "+table.getColumnCount());
        System.out.println("Table number of columns "+table.getColumnCount());
        System.out.println("Table number of columns "+table.getColumnCount());
        System.out.println("Table number of columns "+table.getColumnCount());
        System.out.println("Table number of columns "+this.table.getColumnModel().getColumnCount());


         */


        /*
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            table.getModel().setValueAt("", i, table.getColumnCount() - 2); // set empty value for first new column
            table.getModel().setValueAt("", i, table.getColumnCount() - 1); // set empty value for second new column
        }

         */

/*
        System.out.println("JAAAAAAAAAAAAAAAAAAA");
        revalidate();
        isProjectedColumnOpened = true;

 */
    }

    public void setActionListener(ActionListener l) {
        okayButton.addActionListener(l);
        closeButton.addActionListener(l);
        projectButton.addActionListener(l);
        absoluteRadioButton.addActionListener(l);
        relativeRadioButton.addActionListener(l);
        mixedRadioButton.addActionListener(l);
    }

    public void setProjectionColumnInvisible() {

        if (isProjectedColumnOpened) {
            getCurrentTable().getColumnModel().getColumn(table.getColumnCount() - 1).setMinWidth(0);
            getCurrentTable().getColumnModel().getColumn(table.getColumnCount() - 1).setMaxWidth(0);
            getCurrentTable().getColumnModel().getColumn(table.getColumnCount() - 1).setPreferredWidth(0);

            getCurrentTable().getColumnModel().getColumn(table.getColumnCount() - 2).setMinWidth(0);
            getCurrentTable().getColumnModel().getColumn(table.getColumnCount() - 2).setMaxWidth(0);
            getCurrentTable().getColumnModel().getColumn(table.getColumnCount() - 2).setPreferredWidth(0);

            //isProjectedColumnOpened=!isProjectedColumnOpened;
            getCurrentTable().doLayout();
        }

    }

    public void setProjectionColumnVisible() {

        if (!isProjectedColumnOpened) {
            getCurrentTable().getColumnModel().getColumn(3).setMinWidth(100);
            getCurrentTable().getColumnModel().getColumn(3).setMaxWidth(100000);
            getCurrentTable().getColumnModel().getColumn(3).setPreferredWidth(100);

            getCurrentTable().getColumnModel().getColumn(4).setMinWidth(100);
            getCurrentTable().getColumnModel().getColumn(4).setMaxWidth(100000);
            getCurrentTable().getColumnModel().getColumn(4).setPreferredWidth(100);

            isProjectedColumnOpened = !isProjectedColumnOpened;
            getCurrentTable().doLayout();
        }
    }


    public void setProjectionView(IncreaseSalaryOption type, BigDecimal[] before, BigDecimal[] after) {
        /*
        setProjectionColumnVisible();
        for(int i=0; i<table.getRowCount();i++) {
            double currentSalary = Double.parseDouble(String.valueOf(table.getValueAt(i, 5)));
            double finalSalary = 0;
            if (type == ABSOLUTE) {
                finalSalary = currentSalary+Double.parseDouble(textFieldAbsolute.getText());
            } else if (type == RELATIVE) {
                finalSalary = currentSalary+Double.parseDouble(textFieldRelative.getText())*currentSalary/100;
            } else {
                double finalAbsoluteSalary = currentSalary + Double.parseDouble(mixedAbsolute.getText());
                double finalRelativeSalary = currentSalary + Double.parseDouble(mixedRelative.getText()) * currentSalary / 100;
                if (type == MIXED_MIN) {
                    finalSalary = Math.min(finalAbsoluteSalary, finalRelativeSalary);
                } else if (type == MIXED_MAX) {
                    finalSalary = Math.max(finalAbsoluteSalary, finalRelativeSalary);
                }
            }
            table.setValueAt(finalSalary, i, 7);
        }
        table.doLayout();

         */
        setProjectionColumnVisible();
        for (int i = 0; i < table.getRowCount(); i++) {
            this.table.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(before[i]), i, 3);
        }
        for (int i = 0; i < table.getRowCount(); i++) {
            this.table.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(after[i]), i, 4);
        }
        table.doLayout();
        table.revalidate();


    }

    public void setAbsoluteView() {
        textFieldAbsolute.setVisible(true);
        textFieldRelative.setVisible(false);
        payMixedOptionPanel.setVisible(false);
        setProjectionColumnInvisible();
        revalidate();

    }

    public void setRelativeView() {
        textFieldAbsolute.setVisible(false);
        textFieldRelative.setVisible(true);
        payMixedOptionPanel.setVisible(false);
        setProjectionColumnInvisible();
        revalidate();

    }

    public void setMixedView() {
        textFieldAbsolute.setVisible(false);
        textFieldRelative.setVisible(false);
        payMixedOptionPanel.setVisible(true);
        setProjectionColumnInvisible();
        revalidate();

    }

    public void noIncreaseTypeSelected() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie einen Erhöhungstyp aus.", "Keinen Erhöhungstyp ausgewählt.", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void noIncreaseOptionSelected() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie eine Erhöhungsoption aus.", "Keine Erhöhungsoption ausgewählt.", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void noMinMaxSelected() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Achtung");
        dialog.add(new JLabel("Sie haben gemischte Option ausgewählt, bitte Minimum oder Maximum auswählen"));
        dialog.setSize(new Dimension(500, 100));
        dialog.setVisible(true);
    }

    public void noStartDateSelected() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Startdatum aus.", "Kein Startdatum ausgewählt.", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void inputNotANumber() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie eine Nummer ein.", "Falsche Eingabe.", JOptionPane.ERROR_MESSAGE);
        });
    }

    //TODO mrigrate to model
    private String[][] getSalaryFromDB() {
        String[][] salaryTable = new String[employeeIDList.size()][];
        int currentIndex = 0;
        for (Integer ID : employeeIDList) {
            String employeeName = employeeDataManager.getEmployee(ID).getName() + " " + employeeDataManager.getEmployee(ID).getSurname();
            String currentSalary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contractDataManager.getContract(ID).getRegular_cost());
            String currentBonus = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contractDataManager.getContract(ID).getBonus_cost());
            String salaryProjection = "";
            String increasedSalary = "";
            String[] values = {employeeName, currentSalary, currentBonus, salaryProjection, increasedSalary};
            salaryTable[currentIndex] = values;
            currentIndex++;
        }
        return salaryTable;

    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getOkayButton() {
        return okayButton;
    }

    public JButton getProjectButton() {
        return projectButton;
    }

    public JTable getCurrentTable() {
        return table;
    }

    public JRadioButton getAbsoluteRadioButton() {
        return absoluteRadioButton;
    }

    public JTextField getTextFieldAbsolute() {
        return textFieldAbsolute;
    }

    public JTextField getTextFieldRelative() {
        return textFieldRelative;
    }

    public Box getPayMixedOptionPanel() {
        return payMixedOptionPanel;
    }

    public JRadioButton getRelativeRadioButton() {
        return relativeRadioButton;
    }

    public JRadioButton getMixedRadioButton() {
        return mixedRadioButton;
    }

    public JRadioButton getMixedMinRadioButton() {
        return mixedMinRadioButton;
    }

    public JRadioButton getMixedMaxRadioButton() {
        return mixedMaxRadioButton;
    }

    public JRadioButton getSalaryIncreaseRadioButton() {
        return salaryIncreaseRadioButton;
    }

    public JRadioButton getBonusRadioButton() {
        return bonusRadioButton;
    }

    public DatePicker getStartDate() {
        return startDate;
    }

    public JTextField getMixedAbsolute() {
        return mixedAbsolute;
    }

    public JTextField getMixedRelative() {
        return mixedRelative;
    }

    public JTextField getTextFieldComment() {
        return textFieldComment;
    }

    public boolean isProjectedColumnOpened() {
        return isProjectedColumnOpened;
    }

    public void toggleVisibility() {
        isProjectedColumnOpened = !isProjectedColumnOpened;
    }
}
