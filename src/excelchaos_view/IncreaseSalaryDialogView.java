package excelchaos_view;

import com.github.lgooddatepicker.components.DatePicker;
import excelchaos_model.database.ContractDataManager;
import excelchaos_model.database.EmployeeDataManager;
import excelchaos_model.inputVerifier.SalaryIncreasePercentageVerifier;
import excelchaos_model.inputVerifier.SalaryVerifier;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_model.utility.TableColumnAdjuster;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * IncreaseSalaryDialogView is a dialog to increase the salary of the employee. It supports both one or multiple employees' salary increase.
 * The view is made visible when user clicks either on the toolbar in SalaryListView for multiple employees or on the toolbar in SalaryIncreaseView for one employee
 */

public class IncreaseSalaryDialogView extends JDialog {
    private JPanel mainPanel, tablePanel, inputPanel,increaseTypePanel, increaseOptionPanel,increaseOptionButtonPanel,increaseOptionTextFieldPanel,datePanel,commentPanel, buttonPanel, westButtonPanel, eastButtonPanel;
    private JTable salaryTable;
    private JLabel percentLabel,euroLabel;
    private JButton saveAndExitButton, projectButton, cancelButton;
    private ButtonGroup optionRadioButtonGroup, increaseOptionRadioButtonGroup;
    private JRadioButton salaryIncreaseRadioButton, bonusRadioButton;
    private JRadioButton absoluteRadioButton, relativeRadioButton, mixedRadioButton;
    private JTextField absoluteTextField,relativeTextField,commentTextField;

    private Border loweretched;
    private TitledBorder inputPanelBorder, increaseTypeBorder,increaseOptionBorder,dateBorder,commentBorder;

    private DatePicker startDate;
    private ArrayList<Integer> employeeIDList;
    private String[] columns = {"Name", "Gehaltskosten", "Sonderzahlung", "Gehaltkosten am Startdatum", "Erhöhte Gehaltskosten","Differenz"};

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();

    /**
     * Add all of the visual components to the view
     * @param employeeIDList list of IDs of the employees whose salaries to be increased
     */
    public void init(ArrayList<Integer> employeeIDList){
        this.employeeIDList = employeeIDList;

        setLayout(new BorderLayout());


        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        tablePanel = new JPanel();

        salaryTable = new JTable(getSalaryFromDB(),columns);
        salaryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(salaryTable);
        tca.adjustColumns();
        JScrollPane salaryTableScrollPane = new JScrollPane(salaryTable);
        salaryTableScrollPane.setPreferredSize(new Dimension(120+salaryTable.getPreferredSize().width/2,salaryTable.getRowHeight()*8));
        tablePanel.add(salaryTableScrollPane);


        loweretched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        inputPanel = new JPanel();
        inputPanelBorder = BorderFactory.createTitledBorder(loweretched,"Einstellungen",TitledBorder.CENTER,TitledBorder.ABOVE_TOP);
        inputPanel.setBorder(inputPanelBorder);
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));

        increaseTypePanel = new JPanel();
        increaseTypePanel.setLayout(new FlowLayout());
        increaseTypeBorder = BorderFactory.createTitledBorder(loweretched,"1. Erhöhungstyp (*)",TitledBorder.LEFT,TitledBorder.ABOVE_TOP);
        increaseTypePanel.setBorder(increaseTypeBorder);
        salaryIncreaseRadioButton = new JRadioButton("Normale Gehaltserhöhung");
        salaryIncreaseRadioButton.setSelected(true);
        bonusRadioButton = new JRadioButton("Sonderzahlung");
        increaseOptionRadioButtonGroup = new ButtonGroup();
        increaseOptionRadioButtonGroup.add(salaryIncreaseRadioButton);
        increaseOptionRadioButtonGroup.add(bonusRadioButton);

        increaseTypePanel.add(salaryIncreaseRadioButton);
        increaseTypePanel.add(bonusRadioButton);

        increaseOptionPanel = new JPanel();
        increaseOptionPanel.setLayout(new GridLayout(2,1));
        increaseOptionBorder = BorderFactory.createTitledBorder(loweretched,"2. Erhöhungsoption (*)",TitledBorder.LEFT,TitledBorder.ABOVE_TOP);
        increaseOptionPanel.setBorder(increaseOptionBorder);

        increaseOptionButtonPanel = new JPanel();
        increaseOptionButtonPanel.setLayout(new FlowLayout());
        absoluteRadioButton = new JRadioButton("Option 1: Absolut in €");
        relativeRadioButton = new JRadioButton("Option 2: Relativ in %");
        relativeRadioButton.setSelected(true);
        mixedRadioButton = new JRadioButton("Option 3: Gemischt");
        optionRadioButtonGroup = new ButtonGroup();
        optionRadioButtonGroup.add(absoluteRadioButton);
        optionRadioButtonGroup.add(relativeRadioButton);
        optionRadioButtonGroup.add(mixedRadioButton);
        increaseOptionButtonPanel.add(absoluteRadioButton);
        increaseOptionButtonPanel.add(relativeRadioButton);
        increaseOptionButtonPanel.add(mixedRadioButton);


        increaseOptionTextFieldPanel = new JPanel();
        increaseOptionTextFieldPanel.setLayout(new FlowLayout());
        absoluteTextField = new JTextField();
        absoluteTextField.setInputVerifier(new SalaryVerifier());
        absoluteTextField.setPreferredSize(new Dimension(80,20));
        absoluteTextField.setEnabled(false);
        absoluteTextField.setBackground(Color.LIGHT_GRAY);
        relativeTextField = new JTextField();
        relativeTextField.setInputVerifier(new SalaryIncreasePercentageVerifier());
        relativeTextField.setPreferredSize(new Dimension(80,20));
        percentLabel = new JLabel("Prozentzahl:");
        euroLabel = new JLabel("Betrag:");

        increaseOptionTextFieldPanel.add(euroLabel);
        increaseOptionTextFieldPanel.add(absoluteTextField);
        increaseOptionTextFieldPanel.add(Box.createHorizontalStrut(10));
        increaseOptionTextFieldPanel.add(percentLabel);
        increaseOptionTextFieldPanel.add(relativeTextField);

        increaseOptionPanel.add(increaseOptionButtonPanel);
        increaseOptionPanel.add(increaseOptionTextFieldPanel);

        datePanel = new JPanel();
        dateBorder = BorderFactory.createTitledBorder(loweretched,"3. Startdatum (*)",TitledBorder.LEFT,TitledBorder.ABOVE_TOP);
        datePanel.setBorder(dateBorder);
        startDate = new DatePicker();
        datePanel.add(startDate);

        commentPanel = new JPanel();
        commentBorder = BorderFactory.createTitledBorder(loweretched,"4. Kommentar",TitledBorder.LEFT,TitledBorder.ABOVE_TOP);
        commentPanel.setBorder(commentBorder);
        commentTextField = new JTextField();
        commentTextField.setPreferredSize(new Dimension(350,25));
        commentPanel.add(commentTextField);

        inputPanel.add(increaseTypePanel);
        inputPanel.add(increaseOptionPanel);
        inputPanel.add(datePanel);
        inputPanel.add(commentPanel);

        buttonPanel = new JPanel(new BorderLayout());
        westButtonPanel = new JPanel(new FlowLayout());
        eastButtonPanel = new JPanel(new FlowLayout());

        saveAndExitButton = new JButton("Auswahl bestätigen");
        cancelButton = new JButton("Abbrechen");
        projectButton = new JButton("Projizieren");

        westButtonPanel.add(saveAndExitButton);
        westButtonPanel.add(projectButton);
        eastButtonPanel.add(cancelButton);
        buttonPanel.add(westButtonPanel,BorderLayout.WEST);
        buttonPanel.add(eastButtonPanel,BorderLayout.EAST);

        mainPanel.add(tablePanel,BorderLayout.NORTH);
        mainPanel.add(inputPanel,BorderLayout.CENTER);

        add(mainPanel);
        add(buttonPanel,BorderLayout.SOUTH);



        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("Gehaltserhöhung");
        setVisible(true);

    }

    /**
     * Get salary of the employees whose salaries are to be increased in this view
     * @return 2d String array which represents the names, current salary, current bonus of all employees. The salaryOnIncreaseDate, increaseSalary, salaryDifference are
     * made blank, because they are used to project the salary later on.
     */
    private String[][] getSalaryFromDB() {
        String[][] salaryTableData = new String[employeeIDList.size()][];
        int currentIndex = 0;
        for (Integer ID : employeeIDList) {
            String employeeName = employeeDataManager.getEmployee(ID).getName() + " " + employeeDataManager.getEmployee(ID).getSurname();
            String currentSalary = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contractDataManager.getContract(ID).getRegular_cost());
            String currentBonus = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(contractDataManager.getContract(ID).getBonus_cost());
            String salaryOnIncreaseDate = "";
            String increasedSalary = "";
            String salaryDifference = "";

            String[] values = {employeeName, currentSalary, currentBonus, salaryOnIncreaseDate, increasedSalary,salaryDifference};
            salaryTableData[currentIndex] = values;
            currentIndex++;
        }
        return salaryTableData;

    }

    /**
     * After the user has chosen the increase options, this method sets up the projection view (last 3 columns in the view's table)
     * @param before array of the salary of the employee on the date of increase as chosen by the user
     * @param after array of the salary of the employee after the increase
     */
    public void setProjectionView(BigDecimal[] before, BigDecimal[] after) {

        for (int row = 0; row < salaryTable.getRowCount(); row++) {
           salaryTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(before[row]), row, 3);
           salaryTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(after[row]), row, 4);
           salaryTable.setValueAt(StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(after[row].subtract(before[row])),row,5);
        }
        salaryTable.revalidate();
        salaryTable.repaint();
    }

    /**
     * Change the view when the Absolute option is selected by the user
     */
    public void setAbsoluteRadioButtonSelected(){
        absoluteTextField.setBackground(Color.WHITE);
        absoluteTextField.setEnabled(true);
        relativeTextField.setText(null);
        relativeTextField.setEnabled(false);
        relativeTextField.setBackground(Color.LIGHT_GRAY);
        revalidate();
        repaint();
    }

    /**
     * Change the view when the Relative option is selected by the user
     */
    public void setRelativeRadioButtonSelected(){
        relativeTextField.setBackground(Color.WHITE);
        relativeTextField.setEnabled(true);
        absoluteTextField.setText(null);
        absoluteTextField.setEnabled(false);
        absoluteTextField.setBackground(Color.LIGHT_GRAY);
        revalidate();
        repaint();
    }
    /**
     * Change the view when the Mixed option is selected by the user
     */
    public void setMixedRadioButtonSelected(){
        relativeTextField.setText(null);
        absoluteTextField.setText(null);
        relativeTextField.setBackground(Color.WHITE);
        relativeTextField.setEnabled(true);
        absoluteTextField.setBackground(Color.WHITE);
        absoluteTextField.setEnabled(true);
        revalidate();
        repaint();
    }


    /**
     * Add same action listener for all buttons in the view, allowing them to share the same controller class which handles the action events.
     * @param l the action lister to set
     */
    public void setActionListener(ActionListener l){
        saveAndExitButton.addActionListener(l);
        cancelButton.addActionListener(l);
        projectButton.addActionListener(l);
        absoluteRadioButton.addActionListener(l);
        relativeRadioButton.addActionListener(l);
        mixedRadioButton.addActionListener(l);
    }

    /**
     * @return the cancel button
     */
    public JButton getCancelButton() {
        return cancelButton;
    }
    /**
     * @return the projection button
     */
    public JButton getProjectButton() {
        return projectButton;
    }
    /**
     * @return the save and exit button
     */
    public JButton getSaveAndExitButton() {
        return saveAndExitButton;
    }
    /**
     * @return the absoluteRadioButton
     */
    public JRadioButton getAbsoluteRadioButton() {
        return absoluteRadioButton;
    }
    /**
     * @return the selected start date by user
     */
    public DatePicker getStartDate() {
        return startDate;
    }
    /**
     * @return the inputed absolute amount when the user chooses the absolute option
     */
    public JTextField getAbsoluteTextField() {
        return absoluteTextField;
    }
    /**
     * @return the inputed comment given by the user
     */
    public JTextField getCommentTextField() {
        return commentTextField;
    }
    /**
     * @return the inputed relative amount when the user chooses the relative option
     */
    public JTextField getRelativeTextField() {
        return relativeTextField;
    }
    /**
     * @return the bonus radio button
     */
    public JRadioButton getBonusRadioButton() {
        return bonusRadioButton;
    }
    /**
     * @return the mixed radio button
     */
    public JRadioButton getMixedRadioButton() {
        return mixedRadioButton;
    }
    /**
     * @return the relative radio button
     */
    public JRadioButton getRelativeRadioButton() {
        return relativeRadioButton;
    }
    /**
     * @return the salary increase radio button
     */
    public JRadioButton getSalaryIncreaseRadioButton() {
        return salaryIncreaseRadioButton;
    }
    /**
     * @return the current salary table
     */
    public JTable getSalaryTable() {
        return salaryTable;
    }

}
