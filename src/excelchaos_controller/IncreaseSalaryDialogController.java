package excelchaos_controller;

import excelchaos_model.*;
import excelchaos_model.calculations.SalaryCalculation;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;
import excelchaos_view.IncreaseSalaryDialogView;
import excelchaos_view.SalaryListView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class IncreaseSalaryDialogController implements ActionListener {
    private MainFrameController frameController;
    private IncreaseSalaryDialogView increaseSalaryDialogView;
    private SalaryListView salaryListView;
    private boolean isProjectedColumnOpened;
    private String employeeName;
    private StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();

    private SalaryCalculation salaryCalculation = new SalaryCalculation();


    public IncreaseSalaryDialogController(MainFrameController frameController, String name){
        this.frameController = frameController;
        this.salaryListView = frameController.getSalaryListController().getSalaryListView();
        this.isProjectedColumnOpened=false;
        this.employeeName = name;
        increaseSalaryDialogView = new IncreaseSalaryDialogView();
        increaseSalaryDialogView.init(employeeName);
        increaseSalaryDialogView.setActionListener(this);
        increaseSalaryDialogView.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

    }
    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==increaseSalaryDialogView.getCloseButton()){
            increaseSalaryDialogView.dispose();
       /* } else if(e.getSource()==increaseSalaryDialogView.getProjectButton()){
            //instruct the view to add a new column to the current table which contains projected salary after increase
            increaseSalaryDialogView.setProjectionColumnVisible();*/
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
                increaseSalaryDialogView.dispose();

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
        //Start date transformation
        LocalDate startLocalDate = increaseSalaryDialogView.getStartDate().getDate();
        Date startDate = Date.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        //New salary calculation
        double projectedSalary = salaryCalculation.determineSalaryOfGivenMonth(employeeID,startDate);
        double finalSalary = 0.0;
        if(option == IncreaseSalaryOption.ABSOLUTE){
            finalSalary = projectedSalary +Double.parseDouble(increaseSalaryDialogView.getTextFieldAbsolute().getText()); //TODO Eingabevalidierung
        } else if(option == IncreaseSalaryOption.RELATIVE){
            finalSalary = projectedSalary+Double.parseDouble(increaseSalaryDialogView.getTextFieldRelative().getText())*projectedSalary/100;
        } else {
            double finalAbsoluteSalary = projectedSalary + Double.parseDouble(increaseSalaryDialogView.getMixedAbsolute().getText());
            double finalRelativeSalary = projectedSalary + Double.parseDouble(increaseSalaryDialogView.getMixedRelative().getText()) * projectedSalary / 100;
            if(option == IncreaseSalaryOption.MIXED_MIN){
                finalSalary = Math.min(finalAbsoluteSalary, finalRelativeSalary);
            } else if(option == IncreaseSalaryOption.MIXED_MAX){
                finalSalary = Math.max(finalAbsoluteSalary, finalRelativeSalary);
            }
        }



        //Comment
        String comment = increaseSalaryDialogView.getTextFieldComment().getText();

        //DB write
        SalaryIncreaseHistory salaryIncreaseHistory = new SalaryIncreaseHistory(employeeID, finalSalary, startDate, comment, isBonus);
        salaryIncreaseHistoryManager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        SalaryIncreaseController salaryIncreaseController = frameController.getSalaryIncreaseController();
        salaryIncreaseController.setTableData(salaryIncreaseController.getDataFromDB(employeeDataManager.getEmployee(employeeID)));
        frameController.getUpdater().salaryUpDate();
    }
}
