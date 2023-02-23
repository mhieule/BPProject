package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.IncreaseSalaryDialogView;
import excelchaos_view.SalaryListView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class IncreaseSalaryDialogController implements ActionListener {
    private MainFrameController frameController;
    private IncreaseSalaryDialogView increaseSalaryDialogView;
    private SalaryListView salaryListView;
    private boolean isProjectedColumnOpened;
    private String employeeName;
    private StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();



    public IncreaseSalaryDialogController(MainFrameController frameController, String name){
        this.frameController = frameController;
        this.salaryListView = frameController.getSalaryListController().getSalaryListView();
        this.isProjectedColumnOpened=false;
        this.employeeName = name;
        increaseSalaryDialogView = new IncreaseSalaryDialogView();
        increaseSalaryDialogView.init(employeeName);
        increaseSalaryDialogView.setActionListener(this);
    }
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==increaseSalaryDialogView.getCloseButton()){
            increaseSalaryDialogView.setVisible(false);
        } else if(e.getSource()==increaseSalaryDialogView.getProjectButton()){
            //instruct the view to add a new column to the current table which contains projected salary after increase
            increaseSalaryDialogView.setProjectionColumnVisible();
            if(increaseSalaryDialogView.getAbsoluteRadioButton().isSelected()){
                increaseSalaryDialogView.setProjectionView(IncreaseSalaryOption.ABSOLUTE);
            } else if (increaseSalaryDialogView.getRelativeRadioButton().isSelected()){
                increaseSalaryDialogView.setProjectionView(IncreaseSalaryOption.RELATIVE);
            } else if (increaseSalaryDialogView.getMixedRadioButton().isSelected() && increaseSalaryDialogView.getMixedMinRadioButton().isSelected()){
                increaseSalaryDialogView.setProjectionView(IncreaseSalaryOption.MIXED_MIN);
            } else if (increaseSalaryDialogView.getMixedRadioButton().isSelected() && increaseSalaryDialogView.getMixedMaxRadioButton().isSelected()){
                increaseSalaryDialogView.setProjectionView(IncreaseSalaryOption.MIXED_MAX);
            }

        } else if(e.getSource()==increaseSalaryDialogView.getOkayButton()) {
            //save change to the database
            if(!increaseSalaryDialogView.getSalaryIncreaseRadioButton().isSelected() && !increaseSalaryDialogView.getBonusRadioButton().isSelected()){
                increaseSalaryDialogView.noIncreaseTypeSelected();
            } else if(!increaseSalaryDialogView.getAbsoluteRadioButton().isSelected() && !increaseSalaryDialogView.getRelativeRadioButton().isSelected() && !increaseSalaryDialogView.getMixedRadioButton().isSelected()){
                increaseSalaryDialogView.noIncreaseOptionSelected();
            } else if(increaseSalaryDialogView.getMixedRadioButton().isSelected() && !increaseSalaryDialogView.getMixedMinRadioButton().isSelected() && !increaseSalaryDialogView.getMixedMaxRadioButton().isSelected()){
                increaseSalaryDialogView.noMinMaxSelected();
            } else {
                if(increaseSalaryDialogView.getAbsoluteRadioButton().isSelected()){
                    writeDataToDB(IncreaseSalaryOption.ABSOLUTE);
                } else if(increaseSalaryDialogView.getRelativeRadioButton().isSelected()){
                    writeDataToDB(IncreaseSalaryOption.RELATIVE);
                } else if (increaseSalaryDialogView.getMixedRadioButton().isSelected() && increaseSalaryDialogView.getMixedMinRadioButton().isSelected()) {
                    writeDataToDB(IncreaseSalaryOption.MIXED_MIN);
                } else if(increaseSalaryDialogView.getMixedRadioButton().isSelected() && increaseSalaryDialogView.getMixedMaxRadioButton().isSelected()){
                    writeDataToDB(IncreaseSalaryOption.MIXED_MAX);
                } else {}
                increaseSalaryDialogView.setVisible(false);

            }

        } else if(e.getSource()==increaseSalaryDialogView.getAbsoluteRadioButton()){
            increaseSalaryDialogView.setAbsoluteView();
            isProjectedColumnOpened=!isProjectedColumnOpened;
        } else if(e.getSource()==increaseSalaryDialogView.getRelativeRadioButton()){
            increaseSalaryDialogView.setRelativeView();
            isProjectedColumnOpened=!isProjectedColumnOpened;
        } else if(e.getSource()==increaseSalaryDialogView.getMixedRadioButton()){
            increaseSalaryDialogView.setMixedView();
            isProjectedColumnOpened=!isProjectedColumnOpened;
        }

    }
    //TODO migrate to model
    public void writeDataToDB(IncreaseSalaryOption option){
        //DB connection
        SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();
        ContractDataManager contractDataManager = new ContractDataManager();
        EmployeeDataManager employeeDataManager = new EmployeeDataManager();
        Employee tempEmployee = employeeDataManager.getEmployeeByName(employeeName);

        //Information about this employee
        int employeeID = tempEmployee.getId();
        double currentSalary = contractDataManager.getContract(employeeID).getRegular_cost();

        //Salary Increase Type (normal or bonus)
        boolean isBonus = (increaseSalaryDialogView.getBonusRadioButton().isSelected())? true:false;

        //New salary calculation
        double finalSalary = 0.0;
        if(option == IncreaseSalaryOption.ABSOLUTE){
            finalSalary = currentSalary+Double.parseDouble(increaseSalaryDialogView.getTextFieldAbsolute().getText());
        } else if(option == IncreaseSalaryOption.RELATIVE){
            finalSalary = currentSalary+Double.parseDouble(increaseSalaryDialogView.getTextFieldRelative().getText())*currentSalary/100;
        } else {
            double finalAbsoluteSalary = currentSalary + Double.parseDouble(increaseSalaryDialogView.getMixedAbsolute().getText());
            double finalRelativeSalary = currentSalary + Double.parseDouble(increaseSalaryDialogView.getMixedRelative().getText()) * currentSalary / 100;
            if(option == IncreaseSalaryOption.MIXED_MIN){
                finalSalary = Math.min(finalAbsoluteSalary, finalRelativeSalary);
            } else if(option == IncreaseSalaryOption.MIXED_MAX){
                finalSalary = Math.max(finalAbsoluteSalary, finalRelativeSalary);
            }
        }

        //Start date transformation
        Calendar calendar = Calendar.getInstance();
        LocalDate startLocalDate = increaseSalaryDialogView.getStartDate().getDate();
        calendar.set(startLocalDate.getYear(), startLocalDate.getMonth().getValue(), startLocalDate.getDayOfMonth());
        Date startDate = calendar.getTime();

        //Comment
        String comment = increaseSalaryDialogView.getTextFieldComment().getText();

        //DB write
        SalaryIncreaseHistory salaryIncreaseHistory = new SalaryIncreaseHistory(employeeID, finalSalary, startDate, comment, isBonus);
        salaryIncreaseHistoryManager.addSalaryIncreaseHistory(salaryIncreaseHistory);
    }
}
